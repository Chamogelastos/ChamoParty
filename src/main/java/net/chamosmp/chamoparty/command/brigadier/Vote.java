package net.chamosmp.chamoparty.command.brigadier;


import net.chamosmp.chamoparty.zcore.utils.MessageUtils;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.paper.Description;
import net.strokkur.commands.permission.Permission;
import org.bukkit.command.CommandSender;

@Command("vote")
@Description("Open the vote gui")
public class Vote extends MessageUtils {

        @Permission("chamoparty.vote")
        @Executes
        void onExecute(CommandSender sender) {

        }


    }


