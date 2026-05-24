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
		
		message(sender, "§aVersion du plugin§7: §2" + plugin.getPluginMeta().getVersion());
		message(sender, "§aAuteur§7: §2Maxlego08");
		message(sender, "§aDiscord§7: §2http://discord.groupez.dev/");
		message(sender, "§aDownload now§7: §2https://groupez.dev/resources/124");
		message(sender, "§aServeur Minecraft Vote§7: §fhttps://serveur-minecraft-vote.fr/");
		message(sender, "§aSponsor§7: §chttps://serveur-minecraft-vote.fr/?ref=345");
		
		return CommandType.SUCCESS;
	}

}
