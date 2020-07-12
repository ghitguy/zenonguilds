package me.ghit.zenonguilds.utils.icons;

import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.utils.GuildHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Icons {

    public static ItemStack getIcon(String guild) {
        if (!GuildHandler.getGuilds().contains(guild))
            return null;

        // Mining Guild
        ItemStack miningGuild = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta miningGuildMeta = miningGuild.getItemMeta();
        miningGuildMeta.setDisplayName(Chat.toColor("&fMining &7Guild"));
        miningGuildMeta.setLore(Arrays.asList(Chat.toColor("&8Left click to join this guild")));
        miningGuild.setItemMeta(miningGuildMeta);

        // Alchemy Guild
        ItemStack alchemyGuild = new ItemStack(Material.POTION, 1);
        ItemMeta alchemyGuildMeta = alchemyGuild.getItemMeta();
        alchemyGuildMeta.setDisplayName(Chat.toColor("&dAlchemy &7Guild"));
        alchemyGuildMeta.setLore(Arrays.asList(Chat.toColor("&8Left click to join this guild")));
        alchemyGuild.setItemMeta(alchemyGuildMeta);

        // Blacksmith Guild
        ItemStack blacksmithGuild = new ItemStack(Material.ANVIL, 1);
        ItemMeta blacksmithGuildMeta = blacksmithGuild.getItemMeta();
        blacksmithGuildMeta.setDisplayName(Chat.toColor("&8Blacksmith &7Guild"));
        blacksmithGuildMeta.setLore(Arrays.asList(Chat.toColor("&8Left click to join this guild")));
        blacksmithGuild.setItemMeta(blacksmithGuildMeta);

        // Mercenary Guild
        ItemStack mercenaryGuild = new ItemStack(Material.FEATHER, 1);
        ItemMeta mercenaryGuildMeta = mercenaryGuild.getItemMeta();
        mercenaryGuildMeta.setDisplayName(Chat.toColor("&cMercenary &7Guild"));
        mercenaryGuildMeta.setLore(Arrays.asList(Chat.toColor("&8Left click to join this guild")));
        mercenaryGuild.setItemMeta(mercenaryGuildMeta);

        // Farming Guild
        ItemStack farmingGuild = new ItemStack(Material.GOLDEN_HOE, 1);
        ItemMeta farmingGuildMeta = farmingGuild.getItemMeta();
        farmingGuildMeta.setDisplayName(Chat.toColor("&aFarming &7Guild"));
        farmingGuildMeta.setLore(Arrays.asList(Chat.toColor("&8Left click to join this guild")));
        farmingGuild.setItemMeta(farmingGuildMeta);

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
