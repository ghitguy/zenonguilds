package me.ghit.zenonguilds.serializers;

import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.utils.Chat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopSerializer {

    private static ZenonGuilds plugin;

    private ShopSerializer(ZenonGuilds plugin) {
        ShopSerializer.plugin = plugin;
    }

    public static String[] deserialize(String raw) {
        return raw.split(";");
    }

    public static ItemStack getItem(String raw, String... lore) {
        String[] info = deserialize(raw);
        return Menu.makeItem(Material.getMaterial(info[0].toUpperCase()), Chat.toColor("&f" + info[1]), lore);
    }

    public static ItemStack getItem(String[] raw, String... lore) {
        return Menu.makeItem(Material.getMaterial(raw[0].toUpperCase()), Chat.toColor(raw[1]), lore);
    }

    public static int getBuyCost(String raw) {
        String[] info = deserialize(raw);
        return Integer.parseInt(info[2]);
    }

    public static int getSellCost(String raw) {
        String[] info = deserialize(raw);
        return Integer.parseInt(info[3]);
    }

    public static Material getMaterial(String raw) {
        String[] info = deserialize(raw);
        return Material.getMaterial(info[0].toUpperCase());
    }

    // Get information about item

    // yes, i know this is horrible
    public static int getSellPrice(ItemStack itemStack) {
        return Integer.parseInt(ChatColor.stripColor(itemStack.getItemMeta().getLore().get(1).replaceAll("Value: ", "")));
    }

    public static int getStock(ItemStack itemStack) {
        return plugin.getStock(itemStack.getType());
    }

    // Price editing
    public static void setPrice(Material material, int price) {

    }
}
