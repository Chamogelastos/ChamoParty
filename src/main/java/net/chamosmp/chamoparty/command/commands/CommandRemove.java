package net.chamosmp.chamoparty.command.commands;

import org.bukkit.OfflinePlayer;

import net.chamosmp.chamoparty.ZVotePartyPlugin;
import net.chamosmp.chamoparty.api.enums.Message;
import net.chamosmp.chamoparty.api.enums.Permission;
import net.chamosmp.chamoparty.command.VCommand;
import net.chamosmp.chamoparty.zcore.utils.commands.CommandType;

public class CommandRemove extends VCommand {

	public CommandRemove(ZVotePartyPlugin plugin) {
		super(plugin);
		this.setDescription(Message.DESCRIPTION_REMOVE);
		this.addSubCommand("remove");
		this.setPermission(Permission.ZVOTEPARTY_REMOVE);
		this.addRequireArg("player");
	}

	@Override
	protected CommandType perform(ZVotePartyPlugin plugin) {
		
		OfflinePlayer player = this.argAsOfflinePlayer(0);
		this.plugin.getManager().removeVote(this.sender, player);
		
		return CommandType.SUCCESS;
	}

}
