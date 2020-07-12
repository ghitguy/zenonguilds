package me.ghit.zenonguilds.utils;

import dev.dbassett.skullcreator.SkullCreator;
import me.ghit.zenonguilds.ZenonGuilds;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GuildHandler {

    private static FileConfiguration guilds = ZenonGuilds.getGuilds().getConfig();

    public static List<String> getGuilds() {
        return Arrays.asList("mining", "alchemy", "blacksmith", "mercenary", "farming");
    }

    public static void setGuild(Player player, String guild) {
        if (!getGuilds().contains(guild)) {
            System.out.println(player + " was not added to " + guild +". Report this to the plugin author!");
            return;
        }

        List<String> members = guilds.getStringList("guilds." + guild + ".members");
        members.add(UserSerializer.serialize(player, 1));

        guilds.set("guilds." + guild + ".members", members);
        ZenonGuilds.getGuilds().saveConfig();
    }

    public static String getGuild(Player player) {
        ConfigurationSection guildSection = guilds.getConfigurationSection("guilds");
        String guild = "";

        for (String _guild : guildSection.getKeys(false)) {
            if (guilds.getStringList("guilds." + _guild + ".members").stream().anyMatch(key -> key.startsWith(player.getUniqueId().toString())))
                guild = _guild;
        }

        return guild;
    }

    public static void removeFromGuild(Player player) {
        List<String> members = guilds.getStringList("guilds." + getGuild(player) + ".members");
        members.removeIf(member -> member.startsWith(player.getUniqueId().toString()));
        guilds.set("guilds." + getGuild(player) + ".members", members);
        ZenonGuilds.getGuilds().saveConfig();
    }

    /*
     * Get UUIDs of a certain guild
     */
    public static ArrayList<UUID> getMembers(String guild) {
        ArrayList<UUID> guildMembers = new ArrayList<>();
        List<String> members = guilds.getStringList("guilds." + guild + ".members");
        members.forEach(member -> guildMembers.add(UUID.fromString(UserSerializer.deserialize(member)[0])));

        return guildMembers;
    }

    /*
     * Get each player heads
     */
    public static ArrayList<ItemStack> getMemberHeads(String guild) {
        ArrayList<ItemStack> memberHeads = new ArrayList<>();
        getMembers(guild).forEach(member -> memberHeads.add(SkullCreator.itemFromUuid(member)));
        return memberHeads;
    }

    /*
     * Get the total balance of a guild
     */
    public static int getTotalBalance(String guild) {
        int totalBalance = 0;

        if (getMembers(guild).size() == 0) return totalBalance;

        for (UUID member : getMembers(guild)) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(member);
            totalBalance += ZenonGuilds.getEconomy().getBalance(player);
        }

        return totalBalance;
    }

    public static ArrayList<Player> getOnlineMembers(String guild) {
        ArrayList<Player> onlineMembers = new ArrayList<>();
        getMembers(guild).stream().filter(member -> Bukkit.getPlayer(member) != null).forEach(member -> onlineMembers.add(Bukkit.getPlayer(member)));
        return onlineMembers;
    }

    public static int getGuildLevel(String guild) {
        return guilds.getInt("guilds." + guild + ".level");
    }

    public static void setGuildLevel(String guild, int level) {
        int currentLevel = guilds.getInt("guilds." + guild + ".level");
        if (level >= 10) {
            guilds.set("guilds." + guild + ".level", 10);
        } else if (level <= 0) {
            guilds.set("guilds." + guild + ".level", 0);
        } else {
            guilds.set("guilds." + guild + ".level", level);
        }
        ZenonGuilds.getGuilds().saveConfig();
    }

    public static int getUserLevel(Player player) {
        List<String> members = guilds.getStringList("guilds." + getGuild(player) + ".members");

        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).startsWith(player.getUniqueId().toString())) {
                return Integer.parseInt(UserSerializer.deserialize(members.get(i))[1]);
            }
        }

        return -1;
    }

    public static void setUserLevel(Player player, int level) {
        if (getGuild(player).equals("")) return;

        List<String> members = guilds.getStringList("guilds." + getGuild(player) + ".members");

        int currentLevel = getUserLevel(player);
        members.remove(UserSerializer.serialize(player, currentLevel));

        if (level >= 10) {
            members.add(UserSerializer.serialize(player, 10));
        } else if (level <= 0) {
            members.add(UserSerializer.serialize(player, 0));
        } else {
            members.add(UserSerializer.serialize(player, level));
        }

        guilds.set("guilds." + getGuild(player) + ".members", members);
        ZenonGuilds.getGuilds().saveConfig();
    }

    public static void setLeader(Player player) {
        guilds.set("guilds." + getGuild(player) + "" + ".leader", player.getUniqueId().toString());
        ZenonGuilds.getGuilds().saveConfig();
    }

    public static boolean isLeader(Player player) {
        if (getGuild(player).equals("")) return false;

        return guilds.getString("guilds." + getGuild(player) + ".leader").equals(player.getUniqueId().toString());
    }
}
