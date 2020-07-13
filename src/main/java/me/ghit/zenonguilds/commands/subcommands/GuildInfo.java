package me.ghit.zenonguilds.commands.subcommands;

import me.ghit.zenonguilds.commands.SubCommand;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.utils.GuildHandler;
import me.ghit.zenonguilds.utils.Messages;
import me.ghit.zenonguilds.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GuildInfo extends SubCommand {

    @Override
    public String getName() {
        return "guildinfo";
    }

    @Override
    public String getDescription() {
        return "View the information about a guild";
    }

    @Override
    public String getSyntax() {
        return "/guild guildinfo <guild>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("guilds.userinfo") || !player.hasPermission("guilds.*")) {
            player.sendMessage(Messages.noPermission);
            return;
        }

        if (args.length <= 1) {
            player.sendMessage(Messages.tooFewArgs);
            return;
        }

        System.out.println(args[1]);

        if (!GuildHandler.getGuilds().contains(args[1].toLowerCase())) {
            player.sendMessage(Messages.invalidGuild);
            return;
        }

        String guild = args[1].toLowerCase();

        player.sendMessage(Chat.toColor("&6 " + TextUtil.convertToPascal(guild) + " &7Guild Information"));

        player.sendMessage(Chat.toColor("&7 - Guild: " + TextUtil.convertToPascal(guild)));
        player.sendMessage(Chat.toColor("&7   - Level: " + GuildHandler.getGuildLevel(guild)));
        player.sendMessage(Chat.toColor("&7   - Balance: " + GuildHandler.getTotalBalance(guild)));
        player.sendMessage(Chat.toColor("&7   - Members: " + GuildHandler.getMemberNames(guild)));
        player.sendMessage(Chat.toColor("&7   - Online Members: " + GuildHandler.getOnlineMemberNames(guild)));
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2){
            return GuildHandler.getGuilds();
        }

        return null;
    }

}
