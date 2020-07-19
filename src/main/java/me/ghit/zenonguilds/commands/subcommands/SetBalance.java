package me.ghit.zenonguilds.commands.subcommands;

import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.commands.SubCommand;
import me.ghit.zenonguilds.handlers.GuildHandler;
import me.ghit.zenonguilds.utils.Messages;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SetBalance extends SubCommand {

    private ZenonGuilds plugin = ZenonGuilds.getInstance();

    @Override
    public String getName() {
        return "setbalance";
    }

    @Override
    public String getDescription() {
        return "Sets the balance of a guild";
    }

    @Override
    public String getSyntax() {
        return "/guild setbalance <guild> <amount>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (!player.hasPermission("guilds.setbalance") || !player.hasPermission("guilds.*")) {
            player.sendMessage(Messages.noPermission);
            return;
        }

        if (args.length <= 1) {
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
        int amount = Integer.parseInt(args[2]);

        plugin.setBalance(guild, amount);
        player.sendMessage(Messages.setBalance
                .replaceAll("%guild%", guild)
                .replaceAll("%balance%", String.valueOf(plugin.getBalance(guild))));
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        if (args.length == 2) {
            return GuildHandler.getGuilds();
        } else if (args.length == 3) {
            return Arrays.asList("100", "500", "1000", "2500", "5000");
        }

        return null;
    }
}
