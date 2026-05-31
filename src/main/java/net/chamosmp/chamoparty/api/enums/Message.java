package net.chamosmp.chamoparty.api.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import net.chamosmp.chamoparty.zcore.utils.nms.NMSUtils;

public enum Message {

	PREFIX("<aqua>ChamoParty</aqua>| "),

	INVENTORY_NULL("<red>Impossible to find the inventory with the id <gold>%id%<red>."),
	INVENTORY_CLONE_NULL("<red>The inventory clone is null!"),
	INVENTORY_OPEN_ERROR("<red>An error occurred with the opening of the inventory <gold>%id%<red>."),
	INVENTORY_BUTTON_PREVIOUS("<white>§ <gray>Previous page"),
	INVENTORY_BUTTON_NEXT("<white>§ <gray>Next page"),

	TIME_DAY("%02d days(s) %02d hour(s) %02d minute(s) %02d second(s)"),
	TIME_HOUR("%02d hours(s) %02d minute(s) %02d second(s)"),
	TIME_HOUR_SIMPLE("%02d:%02d:%02d"),
	TIME_MINUTE("%02d minute(s) %02d second(s)"),
	TIME_SECOND("%02d second(s)"),

	COMMAND_SYNTAX_ERROR("<red>You must execute the command like this<gray>: <green>%syntax%"),
	COMMAND_NO_PERMISSION("<red>You do not have permission to run this command."),
	COMMAND_NO_CONSOLE("<red>Only one player can execute this command."),
	COMMAND_NO_ARG("<red>Impossible to find the command with its arguments."),
	COMMAND_SYNTAX_HELP("<green>%syntax% <aqua>§ <gray>%description%"),

	DESCRIPTION_VERSION("Show plugin version"),
	DESCRIPTION_RELOAD("Reload configurations"),
	DESCRIPTION_CONFIG("Change configuration"),
	DESCRIPTION_ADD("Add a vote to a player."),
	DESCRIPTION_REMOVE("Remove a vote to a player."),
	DESCRIPTION_STARTPARTY("Force launch a Vote Party"),
	DESCRIPTION_HELP("Show commands"),
	DESCRIPTION_VOTE("Allows you to open the voting inventory"),

	RELOAD_SUCCESS("<green>You have just reloaded the configuration."),
	RELOAD_ERROR("<red>An error has occurred, go to the console."),

	VOTE_INFORMATIONS(MessageType.CENTER,
			"<dark_gray><st>-+------------------------------+-",
			"",
			"<gray>Vote for the server <dark_purple>Server Name<gray> !",
			"",
			"<dark_gray><st>-+------------------------------+-"),

	VOTE_BROADCAST_ACTION(MessageType.ACTION, "<white>%player% <gray>has just voted <dark_gray>(<aqua>%zvoteparty_votes_recorded%<gray>/<green>%zvoteparty_votes_required_total%<dark_gray>)"),
	VOTE_BROADCAST_TCHAT("<white>%player% <gray>has just voted <dark_gray>(<aqua>%zvoteparty_votes_recorded%<gray>/<green>%zvoteparty_votes_required_total%<dark_gray>)"),
	VOTE_MESSAGE("<gray>You have just voted for the server <dark_purple>Server name<gray>."),
	VOTE_LATER("<gray>You have just received <aqua>%amount% <gray>votes."),
	VOTE_SEND("<gray>You just gave a vote to <white>%player%<gray>"),
	VOTE_REMOVE_SUCCESS("<green>You have just removed a yours from the <white>%player%<green>."),
	VOTE_REMOVE_ERROR("<red>Impossible to remove a vote from the <white>%player%<red>, the player has no vote."),
	VOTE_NEEDED(
			"<aqua>%zvoteparty_votes_required_party% <white>votes <gray>needed for the next party !",
			"<gray>Vote<dark_gray>: <white>(Your Vote Link)"
	),

	VOTE_PARTY_START(MessageType.CENTER,
			"<dark_gray><st>-+------------------------------+-",
			"",
			"<gray>Launch of the voting party!",
			"",
			"<dark_gray><st>-+------------------------------+-"
	),
	NOT_ELIGIBLE_PARTY(MessageType.CENTER,
			"<dark_gray><st>-+------------------------------+-",
			"",
			"<red>You didn’t vote, so you aren’t eligible for the Vote Party rewards!",
			"",
			"<dark_gray><st>-+------------------------------+-"
	),
	VOTE_STARTPARTY("<green>You just launched the voting party.");

	;

	private List<String> messages;
	private String message;
	private Map<String, Object> titles = new HashMap<>();
	private boolean use = true;
	private MessageType type = MessageType.TCHAT;

	private ItemStack itemStack;

	/**
	 *
	 * @param message
	 */
	private Message(String message) {
		this.message = message;
		this.use = true;
	}

	/**
	 *
	 * @param title
	 * @param subTitle
	 * @param a
	 * @param b
	 * @param c
	 */
	private Message(String title, String subTitle, int a, int b, int c) {
		this.use = true;
		this.titles.put("title", title);
		this.titles.put("subtitle", subTitle);
		this.titles.put("start", a);
		this.titles.put("time", b);
		this.titles.put("end", c);
		this.titles.put("isUse", true);
		this.type = MessageType.TITLE;
	}

	/**
	 *
	 * @param message
	 */
	private Message(String... message) {
		this.messages = Arrays.asList(message);
		this.use = true;
	}

	/**
	 *
	 * @param message
	 */
	private Message(MessageType type, String... message) {
		this.messages = Arrays.asList(message);
		this.use = true;
		this.type = type;
	}

	/**
	 *
	 * @param message
	 */
	private Message(MessageType type, String message) {
		this.message = message;
		this.use = true;
		this.type = type;
	}

	/**
	 *
	 * @param message
	 * @param use
	 */
	private Message(String message, boolean use) {
		this.message = message;
		this.use = use;
	}

	public String getMessage() {
		return message;
	}

	public String toMsg() {
		return message;
	}

	public String msg() {
		return message;
	}

	public boolean isUse() {
		return use;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getMessages() {
		return messages == null ? Arrays.asList(message) : messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public boolean isMessage() {
		return messages != null && messages.size() > 1;
	}

	public String getTitle() {
		return (String) titles.get("title");
	}

	public Map<String, Object> getTitles() {
		return titles;
	}

	public void setTitles(Map<String, Object> titles) {
		this.titles = titles;
	}

	public String getSubTitle() {
		return (String) titles.get("subtitle");
	}

	public boolean isTitle() {
		return titles.containsKey("title");
	}

	public int getStart() {
		return ((Number) titles.get("start")).intValue();
	}

	public int getEnd() {
		return ((Number) titles.get("end")).intValue();
	}

	public int getTime() {
		return ((Number) titles.get("time")).intValue();
	}

	public boolean isUseTitle() {
		return (boolean) titles.getOrDefault("isUse", "true");
	}

	public String replace(String a, String b) {
		return message.replace(a, b);
	}

	public MessageType getType() {
		return type.equals(MessageType.ACTION) && NMSUtils.isVeryOldVersion() ? MessageType.TCHAT : type;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

}

