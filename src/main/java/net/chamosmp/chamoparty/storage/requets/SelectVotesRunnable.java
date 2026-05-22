package net.chamosmp.chamoparty.storage.requets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import net.chamosmp.chamoparty.api.PlayerVote;
import net.chamosmp.chamoparty.api.Reward;
import net.chamosmp.chamoparty.api.Vote;
import net.chamosmp.chamoparty.api.storage.IConnection;
import net.chamosmp.chamoparty.api.storage.IStorage;
import net.chamosmp.chamoparty.implementations.ZPlayerVote;
import net.chamosmp.chamoparty.implementations.ZReward;
import net.chamosmp.chamoparty.implementations.ZVote;
import net.chamosmp.chamoparty.save.Config;
import net.chamosmp.chamoparty.zcore.logger.Logger;
import net.chamosmp.chamoparty.zcore.logger.Logger.LogType;
import net.chamosmp.chamoparty.zcore.utils.ZUtils;

public class SelectVotesRunnable extends ZUtils implements Runnable {

	private final IConnection iConnection;
	private final UUID uniqueId;
	private final Consumer<Optional<PlayerVote>> consumer;
	private final IStorage iStorage;
	private int tryAmount = 0;

	/**
	 * @param storage
	 * @param iConnection
	 * @param uniqueId
	 * @param consumer
	 * @param iStorage
	 */
	public SelectVotesRunnable(IConnection iConnection, UUID uniqueId, Consumer<Optional<PlayerVote>> consumer,
			IStorage iStorage) {
		super();
		this.iConnection = iConnection;
		this.uniqueId = uniqueId;
		this.consumer = consumer;
		this.iStorage = iStorage;
	}

	@Override
	public void run() {
		try {
			Connection connection = this.iConnection.getConnection();

			List<Vote> votes = new ArrayList<>();

			String request = "SELECT * FROM zvoteparty_votes WHERE player_uuid = ?";
			PreparedStatement statement = connection.prepareStatement(request);
			statement.setString(1, this.uniqueId.toString());
			ResultSet resultSet = statement.executeQuery();

			if (!connection.getAutoCommit()) {
				connection.commit();
			}

			while (resultSet.next()) {

				String serviceName = resultSet.getString("service_name");
				boolean isRewardGive = resultSet.getBoolean("is_reward_give");
				double rewardPercent = resultSet.getDouble("reward_percent");
				String commandsAsString = resultSet.getString("commands");
				boolean needOnline = resultSet.getBoolean("need_online");
				long createdAt = resultSet.getLong("created_at");

				List<String> commands = Arrays.asList(commandsAsString.split(";"));
				Reward reward = new ZReward(rewardPercent, commands, needOnline, new ArrayList<>());
				Vote vote = new ZVote(serviceName, createdAt, reward, isRewardGive);
				votes.add(vote);

			}

			statement.close();
			PlayerVote playerVote = new ZPlayerVote(this.uniqueId, votes);
			this.iStorage.createPlayer(playerVote);
			this.consumer.accept(Optional.of(playerVote));

		} catch (SQLException e) {
			this.tryAmount++;
			if (this.tryAmount < Config.maxSqlRetryAmoun) {
				try {
					this.iConnection.disconnect();
					this.iConnection.connect();
					this.run();
				} catch (SQLException e1) {
					this.consumer.accept(Optional.empty());
					Logger.info("Impossible to use MySQL storage!", LogType.ERROR);
					e1.printStackTrace();
				}
			} else {
				e.printStackTrace();
			}
		}
	}

}
