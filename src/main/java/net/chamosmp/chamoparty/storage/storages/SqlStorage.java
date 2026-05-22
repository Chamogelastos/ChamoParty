package net.chamosmp.chamoparty.storage.storages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.chamosmp.chamoparty.ZVotePartyPlugin;
import net.chamosmp.chamoparty.api.PlayerVote;
import net.chamosmp.chamoparty.api.Reward;
import net.chamosmp.chamoparty.api.Vote;
import net.chamosmp.chamoparty.api.storage.IConnection;
import net.chamosmp.chamoparty.api.storage.IStorage;
import net.chamosmp.chamoparty.api.storage.Script;
import net.chamosmp.chamoparty.api.storage.Storage;
import net.chamosmp.chamoparty.implementations.ZPlayerVote;
import net.chamosmp.chamoparty.storage.utils.ScriptRunner;
import net.chamosmp.chamoparty.storage.utils.ZConnection;
import net.chamosmp.chamoparty.zcore.enums.Folder;
import net.chamosmp.chamoparty.zcore.logger.Logger;
import net.chamosmp.chamoparty.zcore.logger.Logger.LogType;
import net.chamosmp.chamoparty.zcore.utils.ZUtils;
import net.chamosmp.chamoparty.zcore.utils.storage.Persist;

public class SqlStorage extends ZUtils implements IStorage {

	protected final ZVotePartyPlugin plugin;
	protected final Storage storage;

	protected IConnection iConnection;

	protected transient final Map<UUID, PlayerVote> players = new HashMap<UUID, PlayerVote>();
	protected long voteCount = 1;

	/**
	 * @param plugin
	 * @param storage
	 */
	public SqlStorage(ZVotePartyPlugin plugin, Storage storage) {
		super();
		this.plugin = plugin;
		this.storage = storage;
	}

	@Override
	public void load(Persist persist) {

		Logger.info("Load SQL...");
		String user = plugin.getConfig().getString("sql.user");
		String password = plugin.getConfig().getString("sql.password");
		String host = plugin.getConfig().getString("sql.host");
		String dataBase = plugin.getConfig().getString("sql.database");
		int port = plugin.getConfig().getInt("sql.port");
		this.iConnection = new ZConnection(storage, user, password, host, dataBase, port);

		ZVotePartyPlugin.getScheduler().runAsync(task -> {

			try {
				this.iConnection.connect();
				Logger.info("Database connect to " + host);
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}

			try {

				for (Script script : Script.values()) {
					File file = new File(plugin.getDataFolder(), "scripts/" + script.name().toLowerCase() + ".sql");
					ScriptRunner runner = new ScriptRunner(iConnection.getConnection());
					Reader reader = new BufferedReader(new FileReader(file));
					runner.runScript(reader);
					Logger.info("Script " + script.name() + " successfuly run", LogType.SUCCESS);
					reader.close();
				}

				this.iConnection.fetchVotes(this);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		});

	}

	@Override
	public void save(Persist persist) {
		// TODO Auto-generated method stub

	}

	@Override
	public PlayerVote createPlayer(OfflinePlayer offlinePlayer) {
		return this.createPlayer(offlinePlayer.getUniqueId());
	}

	@Override
	public File getFolder() {
		return new File(this.plugin.getDataFolder(), Folder.PLAYERS.toFolder());
	}

	@Override
	public Map<UUID, PlayerVote> getPlayers() {
		return this.players;
	}

	@Override
	public long getVoteCount() {
		return this.voteCount;
	}

	@Override
	public void addVoteCount(long amount) {
		this.voteCount += amount;
		this.iConnection.updateVoteCount(this.voteCount);
	}

	@Override
	public void removeVoteCount(long amount) {
		this.voteCount -= amount;
		this.iConnection.updateVoteCount(this.voteCount);
	}

	@Override
	public void setVoteCount(long amount) {
		this.voteCount = amount;
		this.iConnection.updateVoteCount(this.voteCount);
	}

	@Override
	public void getPlayer(OfflinePlayer offlinePlayer, Consumer<Optional<PlayerVote>> consumer, boolean forceDatabaseUpdate) {
		this.getPlayer(offlinePlayer.getUniqueId(), consumer, forceDatabaseUpdate);
	}

	@Override
	public Optional<PlayerVote> getSyncPlayer(Player player) {
		if (this.players.containsKey(player.getUniqueId())) {
			return Optional.of(this.players.get(player.getUniqueId()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void insertVote(PlayerVote playerVote, Vote vote, Reward reward) {
		this.iConnection.asyncInsert(playerVote, vote, reward);
	}

	@Override
	public void performCustomVoteAction(String username, String serviceName, UUID uuid) {
		Logger.info("Impossible to find the player " + username, LogType.WARNING);
	}

	@Override
	public void startVoteParty() {
		this.setVoteCount(0);
	}

	@Override
	public void getPlayer(UUID uuid, Consumer<Optional<PlayerVote>> consumer, boolean forceDatabaseUpdate) {
		if (this.players.containsKey(uuid) && !forceDatabaseUpdate) {
			consumer.accept(Optional.of(this.players.get(uuid)));
		} else {
			this.iConnection.asyncFetchPlayer(uuid, consumer, this);
		}
	}

	@Override
	public PlayerVote createPlayer(UUID uuid) {
		PlayerVote playerVote = new ZPlayerVote(uuid);
		players.put(uuid, playerVote);
		return playerVote;
	}

	@Override
	public void updateRewards(UUID uniqueId) {
		this.iConnection.updateRewards(uniqueId);
	}

	@Override
	public void createPlayer(PlayerVote playerVote) {
		this.players.put(playerVote.getUniqueId(), playerVote);
	}

}
