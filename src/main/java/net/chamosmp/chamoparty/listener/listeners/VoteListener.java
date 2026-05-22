package net.chamosmp.chamoparty.listener.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import net.chamosmp.chamoparty.ZVotePartyPlugin;
import net.chamosmp.chamoparty.api.VotePartyManager;
import net.chamosmp.chamoparty.listener.ListenerAdapter;

public class VoteListener extends ListenerAdapter {

	private final ZVotePartyPlugin plugin;

	/**
	 * @param plugin
	 */
	public VoteListener(ZVotePartyPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	@Override
	protected void onConnect(PlayerJoinEvent event, Player player) {
		VotePartyManager manager = this.plugin.getManager();
		manager.giveVotes(player);
	}
	
}
