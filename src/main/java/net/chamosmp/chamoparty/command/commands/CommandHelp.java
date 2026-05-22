package net.chamosmp.chamoparty.command.commands;

import net.chamosmp.chamoparty.ZVotePartyPlugin;
import net.chamosmp.chamoparty.api.enums.Message;
import net.chamosmp.chamoparty.api.enums.Permission;
import net.chamosmp.chamoparty.command.VCommand;
import net.chamosmp.chamoparty.zcore.utils.commands.CommandType;

public class CommandHelp extends VCommand {

	public CommandHelp(ZVotePartyPlugin plugin) {
		super(plugin);
		this.setDescription(Message.DESCRIPTION_HELP);
		this.addSubCommand("help", "aide", "?");
		this.setPermission(Permission.ZVOTEPARTY_HELP);
	}

	@Override
	protected CommandType perform(ZVotePartyPlugin plugin) {

		this.parent.getSubVCommands().forEach(command -> {
			if (command.getPermission() == null || this.sender.hasPermission(command.getPermission())) {
				messageWO(this.sender, Message.COMMAND_SYNTAX_HELP, "%syntax%", command.getSyntax(), "%description%",
						command.getDescription());
			}
		});

		return CommandType.SUCCESS;
	}

}
