package net.chamosmp.chamoparty.loader;

import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import net.chamosmp.chamoparty.api.Reward;
import net.chamosmp.chamoparty.implementations.ZReward;
import net.chamosmp.chamoparty.zcore.utils.loader.Loader;

public class RewardLoader implements Loader<Reward> {

	@Override
	public Reward load(YamlConfiguration configuration, String path, Object... args) {

		double percent = configuration.getDouble(path + "percent", 10);
		List<String> commands = configuration.getStringList(path + "commands");
		boolean needToBeOnline = configuration.getBoolean(path + "needToBeOnline", false);		
		List<String> messages = configuration.getStringList(path + "broadcast");
		
		return new ZReward(percent, commands, needToBeOnline, messages);
	}

	@Override
	public void save(Reward object, YamlConfiguration configuration, String path) {
		// TODO Auto-generated method stub

	}

}
