package me.ghit.zenonguilds.commands.subcommands;

import me.ghit.zenonguilds.commands.SubCommand;
import me.ghit.zenonguilds.utils.GuildHandler;
import me.ghit.zenonguilds.utils.Messages;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetGuildLevel extends SubCommand {
    @Override
    public String getName() {
        return "setguildlevel";
    }

    @Override
    public String getDescription() {
        return "Sets a guild's level";
    }

    @Override
    public String getSyntax() {
        return "/guild setguildlevel <guild> <level>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("guilds.setguildlevel") || !player.hasPermission("guilds.*")) {
            player.sendMessage(Messages.noPermission);
            return;
        }

        if (args.length <= 2) {
            player.sendMessage(Messages.tooFewArgs);
            return;
        }

        if (!GuildHandler.getGuilds().contains(args[1].toLowerCase())) {
            player.sendMessage(Messages.invalidGuild);
            return;
        }

        if (!NumberUtils.isNumber(args[2])) {
            player.sendMessage(Messages.invalidNumber);
            return;
        }

        String guild = args[1].toLowerCase();
        int level = Integer.parseInt(args[2]);

        GuildHandler.setGuildLevel(guild, level);
        player.sendMessage(Messages.setGuildLevel.replaceAll("%guild%", guild).replaceAll("%level%", String.valueOf(GuildHandler.getGuildLevel(guild))));
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2){
            return GuildHandler.getGuilds();
        } else if (args.length == 3) {
            return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        }

        return null;
    }
}
