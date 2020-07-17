package me.ghit.zenonguilds.utils;

import me.ghit.zenonguilds.menusystem.Menu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopSerializer {

    public static String[] deserialize(String raw) {
        return raw.split(";");
    }

    public static ItemStack getItem(String raw, String... lore) {
        String[] info = deserialize(raw);
        return Menu.makeItem(Material.getMaterial(info[0].toUpperCase()), Chat.toColor(info[1]), lore);
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
}
