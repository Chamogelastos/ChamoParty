package net.chamosmp.chamoparty.command.commands;

import net.chamosmp.chamoparty.ZVotePartyPlugin;
import net.chamosmp.chamoparty.api.enums.Message;
import net.chamosmp.chamoparty.command.VCommand;
import net.chamosmp.chamoparty.zcore.utils.commands.CommandType;

public class CommandVersion extends VCommand {

	public CommandVersion(ZVotePartyPlugin plugin) {
		super(plugin);
		this.setDescription(Message.DESCRIPTION_VERSION);
		this.addSubCommand("version", "ver", "v");
	}

	@Override
	protected CommandType perform(ZVotePartyPlugin plugin) {

		message(sender, "<green>Version<gray>: <dark_green>" + plugin.getPluginMeta().getVersion());
		message(sender, "<green>Organization<gray>: <dark_green>SQD Studios");
		message(sender, "<green>Download<gray>: <dark_green>https://modrinth.com/project/chamoparty");
		
		return CommandType.SUCCESS;
	}

}
