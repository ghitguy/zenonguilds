package me.ghit.zenonguilds.utils;

import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.handlers.GuildHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Icons {

    public static ItemStack getIcon(String guild) {
        if (!GuildHandler.getGuilds().contains(guild))
            return null;

        // Mining Guild
        ItemStack miningGuild = Menu.makeItem(Material.DIAMOND_PICKAXE, Chat.toColor("&fMining &7Guild"), Chat.toColor("&8Left click to join this guild"));

        // Alchemy Guild
        ItemStack alchemyGuild = Menu.makeItem(Material.POTION, Chat.toColor("&dAlchemy &7Guild"), Chat.toColor("&8Left click to join this guild"));

        // Blacksmith Guild
        ItemStack blacksmithGuild = Menu.makeItem(Material.ANVIL, Chat.toColor("&8Blacksmith &7Guild"), Chat.toColor("&8Left click to join this guild"));

        // Mercenary Guild
        ItemStack mercenaryGuild = Menu.makeItem(Material.FEATHER, Chat.toColor("&cMercenary &7Guild"), Chat.toColor("&8Left click to join this guild"));

        // Farming Guild
        ItemStack farmingGuild = Menu.makeItem(Material.GOLDEN_HOE, Chat.toColor("&aFarming &7Guild"), Chat.toColor("&8Left click to join this guild"));

        switch (guild) {
            case "mining":
                return miningGuild;
            case "alchemy":
                return alchemyGuild;
            case "blacksmith":
                return blacksmithGuild;
            case "mercenary":
                return mercenaryGuild;
            case "farming":
                return farmingGuild;
            default:
                return null;
        }
    }

    public static ItemStack getStatsIcon(String guild, Player player) {
        ItemStack icon = getIcon(guild);

        if (GuildHandler.getGuild(player).equals(guild)) {
            return Menu.reloreItem(icon,
                    Chat.toColor("&6Guild Level: &f" + GuildHandler.getGuildLevel(guild)),
                    Chat.toColor("&6Guild Balance: &f" + GuildHandler.getTotalBalance(guild)),
                    Chat.toColor("&6Members: &f" + GuildHandler.getMembers(guild).size()),
                    Chat.toColor("&6Online Members: &f" + GuildHandler.getOnlineMembers(guild).size()));
        } else {
            return Menu.reloreItem(icon,
                    Chat.toColor("&6Guild Level: &f" + GuildHandler.getGuildLevel(guild)),
                    Chat.toColor("&6Guild Balance: &f" + GuildHandler.getTotalBalance(guild)),
                    Chat.toColor("&6Members: &f" + GuildHandler.getMembers(guild).size()));
        }
    }

}
