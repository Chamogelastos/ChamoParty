package net.chamosmp.chamoparty.command.commands;

import net.chamosmp.chamoparty.ZVotePartyPlugin;
import net.chamosmp.chamoparty.api.enums.Message;
import net.chamosmp.chamoparty.api.enums.Permission;
import net.chamosmp.chamoparty.command.VCommand;
import net.chamosmp.chamoparty.zcore.utils.commands.CommandType;

public class CommandStartParty extends VCommand {

	public CommandStartParty(ZVotePartyPlugin plugin) {
		super(plugin);
		this.setDescription(Message.DESCRIPTION_STARTPARTY);
		this.addSubCommand("startparty", "sp");
		this.setPermission(Permission.ZVOTEPARTY_STARTPARTY);	
	}

	@Override
	protected CommandType perform(ZVotePartyPlugin plugin) {
		
		this.manager.forceStart(this.sender);
		
		return CommandType.SUCCESS;
	}

}
