package net.chamosmp.chamoparty.command.commands;

import net.chamosmp.chamoparty.ZVotePartyPlugin;
import net.chamosmp.chamoparty.api.enums.Permission;
import net.chamosmp.chamoparty.command.VCommand;
import net.chamosmp.chamoparty.zcore.utils.commands.CommandType;

public class CommandIndex extends VCommand {

	public CommandIndex(ZVotePartyPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.ZVOTEPARTY_USE);
		this.addSubCommand(new CommandVersion(plugin));
		this.addSubCommand(new CommandReload(plugin));
		this.addSubCommand(new CommandHelp(plugin));
		this.addSubCommand(new CommandAdd(plugin));
		this.addSubCommand(new CommandRemove(plugin));
		this.addSubCommand(new CommandConfig(plugin));
		this.addSubCommand(new CommandStartParty(plugin));
	}

	@Override
	protected CommandType perform(ZVotePartyPlugin plugin) {
		
		this.manager.sendNeedVote(this.sender);
		
		return CommandType.SUCCESS;
	}

}
