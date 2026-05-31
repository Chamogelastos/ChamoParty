package net.chamosmp.chamoparty.command.brigadier;

import net.chamosmp.chamoparty.ZVotePartyPlugin;
import net.chamosmp.chamoparty.command.VCommand;
import net.chamosmp.chamoparty.zcore.utils.commands.CommandType;
import net.strokkur.commands.Aliases;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.permission.Permission;
import org.bukkit.command.CommandSender;

@Command("chamoparty")
@Aliases({"voteparty", "vp"})
public class Base extends VCommand {
    public Base(ZVotePartyPlugin plugin) {
        super(plugin);
    }

    // Main Command
    @Permission("chamoparty.use")
    @Executes
    void onExecuteBase(CommandSender sender) {
        this.manager.sendNeedVote(this.sender);
    }

    // Reload Subcommand
    @Permission("chamoparty.reload")
    @Executes("reload")
    void onExecuteReload(CommandSender sender) {
        this.manager.reload(this.sender);
    }

    // Version Subcommand
    @Permission("chamoparty.version")
    @Executes("version")
    void onExecuteVersion(CommandSender sender) {
        message(sender, "<green>Version<gray>: <dark_green>" + plugin.getPluginMeta().getVersion());
        message(sender, "<green>Organization<gray>: <dark_green>SQD Studios");
        message(sender, "<green>Download<gray>: <dark_green>https://modrinth.com/project/chamoparty");
    }



    @Override
    protected CommandType perform(ZVotePartyPlugin plugin) {
        return null;
    }
}
