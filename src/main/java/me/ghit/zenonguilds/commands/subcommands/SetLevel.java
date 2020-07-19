package me.ghit.zenonguilds.commands.subcommands;

import me.ghit.zenonguilds.commands.SubCommand;
import me.ghit.zenonguilds.handlers.GuildHandler;
import me.ghit.zenonguilds.utils.Messages;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetLevel extends SubCommand {
    @Override
    public String getName() {
        return "setlevel";
    }

    @Override
    public String getDescription() {
        return "Sets the level of a user";
    }

    @Override
    public String getSyntax() {
        return "/guild setlevel <player> <level>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("guilds.setlevel") || !player.hasPermission("guilds.*")) {
            player.sendMessage(Messages.noPermission);
            return;
        }

        if (args.length <= 2) {
            player.sendMessage(Messages.tooFewArgs);
            return;
        }

        if (Bukkit.getOnlinePlayers().stream().noneMatch(mentionedPlayer -> mentionedPlayer.getName().equalsIgnoreCase(args[1]))) {
            player.sendMessage(Messages.offlineOrNotAPlayer);
            return;
        }

        if (GuildHandler.getGuild(player).equals("")) {
            player.sendMessage(Messages.notInGuild.replaceAll("%player%", player.getName()));
            return;
        }

        if (!NumberUtils.isNumber(args[2])) {
            player.sendMessage(Messages.invalidNumber);
            return;
        }

        Player mentionedPlayer = Bukkit.getPlayer(args[1]);
        int level = Integer.parseInt(args[2]);

        GuildHandler.setUserLevel(mentionedPlayer, level);
        player.sendMessage(Messages.setLevel.replaceAll("%player%", mentionedPlayer.getName()).replaceAll("%level%", String.valueOf(GuildHandler.getUserLevel(mentionedPlayer))));
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2){
            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (Player value : players) {
                playerNames.add(value.getName());
            }
            return playerNames;
        }

        return null;
    }
}
