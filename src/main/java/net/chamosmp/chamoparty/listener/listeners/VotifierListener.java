package net.chamosmp.chamoparty.listener.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import net.chamosmp.chamoparty.ZVotePartyPlugin;
import net.chamosmp.chamoparty.api.VotePartyManager;

public class VotifierListener implements Listener {

	private final ZVotePartyPlugin plugin;

	/**
	 * @param plugin
	 */
	public VotifierListener(ZVotePartyPlugin plugin) {
		super();
		this.plugin = plugin;
	}

	@EventHandler
	public void onVote(VotifierEvent event) {
		VotePartyManager manager = this.plugin.getManager();
		
		Vote vote = event.getVote();
		manager.vote(vote.getUsername(), vote.getServiceName(), true);
	}

}
