package net.chamosmp.chamoparty.command.commands;

import net.chamosmp.chamoparty.ZVotePartyPlugin;
import net.chamosmp.chamoparty.api.enums.Message;
import net.chamosmp.chamoparty.api.enums.Permission;
import net.chamosmp.chamoparty.command.VCommand;
import net.chamosmp.chamoparty.zcore.enums.EnumInventory;
import net.chamosmp.chamoparty.zcore.utils.commands.CommandType;

public class CommandConfig extends VCommand {

	public CommandConfig(ZVotePartyPlugin plugin) {
		super(plugin);
		this.setDescription(Message.DESCRIPTION_CONFIG);
		this.addSubCommand("config");
		this.setPermission(Permission.ZVOTEPARTY_CONFIG);
		this.setConsoleCanUse(false);
	}

	@Override
	protected CommandType perform(ZVotePartyPlugin plugin) {
		
		this.createInventory(plugin, this.player, EnumInventory.INVENTORY_CONFIG);
		
		return CommandType.SUCCESS;
	}

}
