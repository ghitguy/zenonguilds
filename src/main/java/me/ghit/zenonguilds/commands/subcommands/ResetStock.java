package me.ghit.zenonguilds.commands.subcommands;

import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.commands.SubCommand;
import me.ghit.zenonguilds.utils.Messages;
import me.ghit.zenonguilds.utils.TextUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class ResetStock extends SubCommand {
    @Override
    public String getName() {
        return "resetstock";
    }

    @Override
    public String getDescription() {
        return "Resets the stock of a material";
    }

    @Override
    public String getSyntax() {
        return "/guild resetstock <material>";
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

        if (Material.getMaterial(args[1].toUpperCase()) == null) {
            player.sendMessage(Messages.invalidMaterial);
            return;
        }

        Material material = Material.getMaterial(args[1].toUpperCase());

        ZenonGuilds.getInstance().setStock(material, 0);
        player.sendMessage(Messages.resetStock.replaceAll("%material%", TextUtils.convertToPascal(material.name().toLowerCase())));
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
