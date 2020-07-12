package me.ghit.zenonguilds.utils;

import org.bukkit.ChatColor;

public class Chat {

    public static String toColor(String raw) {
        return ChatColor.translateAlternateColorCodes('&', raw);
    }

}
