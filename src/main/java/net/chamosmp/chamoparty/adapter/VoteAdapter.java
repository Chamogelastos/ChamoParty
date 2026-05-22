package net.chamosmp.chamoparty.adapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import net.chamosmp.chamoparty.api.Reward;
import net.chamosmp.chamoparty.api.Vote;
import net.chamosmp.chamoparty.implementations.ZReward;
import net.chamosmp.chamoparty.implementations.ZVote;
import net.chamosmp.chamoparty.zcore.ZPlugin;

public class VoteAdapter extends TypeAdapter<Vote> {

	private final ZPlugin plugin;

	private final Type seriType = new TypeToken<Map<String, Object>>() {
	}.getType();

	private final String SERVICENAME = "servicename";
	private final String REWARD = "reward";
	private final String CREATED_AT = "created_at";
	private final String IS_GIVE = "is_give";

	/**
	 * @param plugin
	 */
	public VoteAdapter(ZPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vote read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}

		String raw = reader.nextString();

		Map<String, Object> keys = this.plugin.getGson().fromJson(raw, this.seriType);

		Number createdAt = (Number) keys.get(this.CREATED_AT);

		Map<String, Object> rewardMap = (Map<String, Object>) keys.get(this.REWARD);
		List<String> commands = (List<String>) rewardMap.get("commands");
		List<String> messages = (List<String>) rewardMap.get("messages");
		Number percent = (Number) rewardMap.get("percent");

		Reward reward = new ZReward(percent.doubleValue(), commands, false, messages);

		String serviceName = (String) keys.get(this.SERVICENAME);
		boolean isGive = (boolean) keys.get(this.IS_GIVE);

		return new ZVote(serviceName, createdAt.longValue(), reward, isGive);
	}

	@Override
	public void write(JsonWriter writer, Vote vote) throws IOException {

		if (vote == null) {
			writer.nullValue();
			return;
		}

		Map<String, Object> serial = new HashMap<String, Object>();

		serial.put(this.SERVICENAME, vote.getServiceName());
		serial.put(this.REWARD, vote.getReward());
		serial.put(this.IS_GIVE, vote.rewardIsGive());
		serial.put(this.CREATED_AT, vote.getCreatedAt());

		writer.value(this.plugin.getGson().toJson(serial));
	}

}
