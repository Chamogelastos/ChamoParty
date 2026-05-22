package net.chamosmp.chamoparty.command.commands;

import net.chamosmp.chamoparty.ZVotePartyPlugin;
import net.chamosmp.chamoparty.api.enums.Message;
import net.chamosmp.chamoparty.api.enums.Permission;
import net.chamosmp.chamoparty.command.VCommand;
import net.chamosmp.chamoparty.zcore.utils.commands.CommandType;

public class CommandAdd extends VCommand {

	public CommandAdd(ZVotePartyPlugin plugin) {
		super(plugin);
		this.setDescription(Message.DESCRIPTION_ADD);
		this.addSubCommand("add");
		this.setPermission(Permission.ZVOTEPARTY_ADD);
		this.addRequireArg("player");
		this.addOptionalArg("party (true/false)");
	}

	@Override
	protected CommandType perform(ZVotePartyPlugin plugin) {
		
		String player = this.argAsString(0);
		boolean updateVoteParty = this.argAsBoolean(1, false);
		this.plugin.getManager().vote(this.sender, player, updateVoteParty);
		
		return CommandType.SUCCESS;
	}

}
