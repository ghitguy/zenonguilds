package me.ghit.zenonguilds.utils;

import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.handlers.GuildHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Levels {

    private static final ZenonGuilds plugin = ZenonGuilds.getInstance();

    // When a user ranks up, run this method as it runs the command
    public static void rankupGrantUser(Player player, int level) {
        String guild = GuildHandler.getGuild(player);
        String commandToRun = plugin.getLevelRequirements().getString(guild + ".user-levels." + level + ".command").replaceAll("%player%", player.getName());
        System.out.println("Command to run : " + commandToRun);

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandToRun);
    }

    public static void setGuildRank(Player player) {
        String guild = GuildHandler.getGuild(player);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plugin.getLevelRequirements().getString(guild + ".command").replaceAll("%player%", player.getName()));
    }

    public static void runLeaveCommands(Player player) {
        String guild = GuildHandler.getGuild(player);
        List<String> commandsToRun = plugin.getLevelRequirements().getStringList(guild + ".leave-commands");
        commandsToRun.forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%", player.getName())));
    }
}
