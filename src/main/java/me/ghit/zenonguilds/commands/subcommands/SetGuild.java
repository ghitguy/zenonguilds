package me.ghit.zenonguilds.commands.subcommands;

import me.ghit.zenonguilds.commands.SubCommand;
import me.ghit.zenonguilds.handlers.GuildHandler;
import me.ghit.zenonguilds.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetGuild extends SubCommand {
    @Override
    public String getName() {
        return "setguild";
    }

    @Override
    public String getDescription() {
        return "Sets the guild of a user";
    }

    @Override
    public String getSyntax() {
        return "/guild setguild <player> <guild>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("guilds.setguild") || !player.hasPermission("guilds.*")) {
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

        if (!GuildHandler.getGuilds().contains(args[2].toLowerCase())) {
            player.sendMessage(Messages.invalidGuild);
            return;
        }

        Player mentionedPlayer = Bukkit.getPlayer(args[1]);
        String guild = args[2].toLowerCase();

        GuildHandler.removeFromGuild(mentionedPlayer);
        GuildHandler.setGuild(mentionedPlayer, guild);
        player.sendMessage(Messages.setGuild.replaceAll("%player%", mentionedPlayer.getName()).replaceAll("%guild%", guild));
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
            return GuildHandler.getGuilds();
        }

        return null;
    }
}
