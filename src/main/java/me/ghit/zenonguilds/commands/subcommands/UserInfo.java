package me.ghit.zenonguilds.commands.subcommands;

import me.ghit.zenonguilds.commands.SubCommand;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.handlers.GuildHandler;
import me.ghit.zenonguilds.utils.Messages;
import me.ghit.zenonguilds.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UserInfo extends SubCommand {

    @Override
    public String getName() {
        return "userinfo";
    }

    @Override
    public String getDescription() {
        return "View the information about a user";
    }

    @Override
    public String getSyntax() {
        return "/guild userinfo <player>";
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

        if (Bukkit.getOnlinePlayers().stream().noneMatch(mentionedPlayer -> mentionedPlayer.getName().equalsIgnoreCase(args[1]))) {
            player.sendMessage(Messages.offlineOrNotAPlayer);
            return;
        }

        Player mentionedPlayer = Bukkit.getPlayer(args[0]);

        if (GuildHandler.getGuild(mentionedPlayer).equals(" ")) {
            player.sendMessage(Messages.notInGuild);
            return;
        }

        String guild = GuildHandler.getGuild(mentionedPlayer);

        player.sendMessage(Chat.toColor("&7User Information: &6" + mentionedPlayer.getName()));

        player.sendMessage(Chat.toColor("&7User Level: " + GuildHandler.getUserLevel(mentionedPlayer)));

        if (GuildHandler.isLeader(mentionedPlayer)) {
            player.sendMessage(Chat.toColor("&7 - &6&lLEADER"));
        }

        player.sendMessage(Chat.toColor("&7 - Guild: " + TextUtils.convertToPascal(guild)));
        player.sendMessage(Chat.toColor("&7   - Level: " + GuildHandler.getGuildLevel(guild)));
        player.sendMessage(Chat.toColor("&7   - Balance: " + GuildHandler.getTotalBalance(guild)));
        player.sendMessage(Chat.toColor("&7   - Members: " + GuildHandler.getMembers(guild)));
        player.sendMessage(Chat.toColor("&7   - Online Members: " + GuildHandler.getOnlineMembers(guild)));
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
