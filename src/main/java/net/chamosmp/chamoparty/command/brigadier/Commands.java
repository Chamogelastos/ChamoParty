package net.chamosmp.chamoparty.command.brigadier;


import net.chamosmp.chamoparty.api.VotePartyManager;
import net.chamosmp.chamoparty.api.enums.Message;
import net.chamosmp.chamoparty.save.Config;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.paper.Description;
import net.strokkur.commands.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static io.papermc.paper.command.brigadier.MessageComponentSerializer.message;

public class Commands {


    @Command("vote")
    @Description("Open the vote gui")
    class vote {

        @Permission("chamoparty.vote")
        @Executes
        void onExecute(Player player) {
            VotePartyManager.openVote(player);
        }


    }
}
