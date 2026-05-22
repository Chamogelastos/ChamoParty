package net.chamosmp.chamoparty.command.commands;

import net.chamosmp.chamoparty.ZVotePartyPlugin;
import net.chamosmp.chamoparty.api.enums.Message;
import net.chamosmp.chamoparty.api.enums.Permission;
import net.chamosmp.chamoparty.command.VCommand;
import net.chamosmp.chamoparty.zcore.utils.commands.CommandType;

public class CommandReload extends VCommand {

	public CommandReload(ZVotePartyPlugin plugin) {
		super(plugin);
		this.setDescription(Message.DESCRIPTION_RELOAD);
		this.addSubCommand("reload", "rl");
		this.setPermission(Permission.ZVOTEPARTY_RELOAD);
	}

	@Override
	protected CommandType perform(ZVotePartyPlugin plugin) {
		
		this.manager.reload(this.sender);
		
		return CommandType.SUCCESS;
	}

}
