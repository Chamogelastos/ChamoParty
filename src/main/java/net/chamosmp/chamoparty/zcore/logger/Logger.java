package net.chamosmp.chamoparty.zcore.logger;


import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

public class Logger{

	private final String prefix;
	private static Logger logger;

	public Logger(String prefix) {
		this.prefix = prefix;
		logger = this;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void info(String message, LogType type){
		getLogger().log(message, type);
	}

	public static void info(String message){
		getLogger().log(message, LogType.INFO);
	}

	public String getPrefix() {
		return prefix;
	}

	public enum LogType{
		ERROR("<dark_red>"),
		INFO("<yellow>"),
		WARNING("<red>"),
		SUCCESS("<green>");

		private final String color;

		private LogType(String color) {
			this.color = color;
		}

		public String getColor() {
			return color;
		}
	}

	public void log(String message, LogType type){
		MiniMessage mm = MiniMessage.miniMessage();
		Bukkit.getConsoleSender().sendMessage(mm.deserialize(prefix + "| " + type.getColor() + message));
	}

	public void log(String message){
		MiniMessage mm = MiniMessage.miniMessage();
		Bukkit.getConsoleSender().sendMessage(mm.deserialize(prefix + "| " + message));

		//Bukkit.getConsoleSender().sendMessage("§8[§e"+prefix+"§8] §e" + getColoredMessage(message));
	}

	public void log(String message, Object... args){
		log(String.format(message, args));
	}

	public void log(String message,  LogType type, Object... args){
		log(String.format(message, args), type);
	}

	public void log(String[] messages, LogType type){
		for(String message : messages){
			log(message, type);
		}
	}

	/**
	 *
	 * @param message The message to initialize and convert to legacy
	 * @return The legacy compatible message
	 * @deprecated Please use {@link Logger#legacyToMiniMessage(String)}. To support MiniMessage we had to give up on legacy soo
	 */
	@Deprecated(since = "0.0.1", forRemoval = true)
	public String getColoredMessage(String message){
		return message.replace("<&>", "§");
	}

	/**
	 * What are these weird things I do for YOU
	 * @apiNote It's not finished yet so write it in MiniMessage to begin with
	 * @param message The message to make the Legacy to minimessage
	 * @return The minimessage from legacy String
	 */
	public String legacyToMiniMessage(String message) {
		String mmMessage = message.replace("&0", "<black>") + message.replace("&1", "<dark_blue>") + message.replace("&2", "<dark_green>") + message.replace("&3", "<dark_aqua>") + message.replace("&4", "<dark_red>");
		return mmMessage;
	}
}
