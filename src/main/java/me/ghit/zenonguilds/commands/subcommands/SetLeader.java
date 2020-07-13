package me.ghit.zenonguilds.commands.subcommands;

import com.sun.org.apache.xpath.internal.operations.Bool;
import me.ghit.zenonguilds.commands.SubCommand;
import me.ghit.zenonguilds.utils.GuildHandler;
import me.ghit.zenonguilds.utils.Messages;
import org.apache.commons.lang.BooleanUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetLeader extends SubCommand {
    @Override
    public String getName() {
        return "setleader";
    }

    @Override
    public String getDescription() {
        return "Sets the leader/master of a guild";
    }

    @Override
    public String getSyntax() {
        return "/guild setleader <player> <true|false>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("guilds.setleader") || !player.hasPermission("guilds.*")) {
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

        Player mentionedPlayer = Bukkit.getPlayer(args[1]);

        if (GuildHandler.getGuild(mentionedPlayer).equals("")) {
            player.sendMessage(Messages.playerNotInGuild.replaceAll("%player%", mentionedPlayer.getName()));
            return;
        }

        if (!args[2].toLowerCase().equals("true") && !args[2].toLowerCase().equals("false")) {
            player.sendMessage(Messages.invalidBoolean);
            return;
        }

        if (args[2].toLowerCase().equals("true")) {
            GuildHandler.setLeader(mentionedPlayer);
            player.sendMessage(Messages.setLeader.replaceAll("%player%", mentionedPlayer.getName())
                    .replaceAll("%guild%", GuildHandler.getGuild(mentionedPlayer))
                    .replaceAll("%player%", mentionedPlayer.getName()));
        } else {
            GuildHandler.removeLeader(mentionedPlayer);
            player.sendMessage(Messages.removedLeader.replaceAll("%player%", mentionedPlayer.getName())
                    .replaceAll("%guild%", GuildHandler.getGuild(mentionedPlayer))
                    .replaceAll("%player%", mentionedPlayer.getName()));
        }
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
        } else if (args.length == 3) {
            return Arrays.asList("true", "false");
        }

        return null;
    }
}
