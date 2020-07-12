package me.ghit.zenonguilds.utils;

import org.bukkit.entity.Player;

import java.util.UUID;

public class UserSerializer {
    public static String serialize(Player player, int rank) {
        return player.getUniqueId() + ";" + rank;
    }

    public static String serialize(UUID playerUUID, int rank) {
        return playerUUID + ";" + rank;
    }

    public static String[] deserialize(String serializedString) {
        return serializedString.split(";");
    }
}
