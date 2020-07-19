package me.ghit.zenonguilds.handlers;

import dev.dbassett.skullcreator.SkullCreator;
import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.serializers.UserSerializer;
import me.ghit.zenonguilds.utils.Levels;
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

public class GuildHandler {

    private static final FileConfiguration guilds = ZenonGuilds.getGuilds().getConfig();
    private static final ZenonGuilds plugin = ZenonGuilds.getInstance();
    private static final ConfigurationSection guildSection = guilds.getConfigurationSection("guilds");

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

        Levels.setGuildRank(player);
    }

    public static String getGuild(Player player) {
        String guild = "";

        for (String _guild : guildSection.getKeys(false)) {
            if (guilds.getStringList("guilds." + _guild + ".members").stream().anyMatch(key -> key.startsWith(player.getUniqueId().toString())))
                guild = _guild;
        }

        return guild;
    }

    public static String getGuild(OfflinePlayer player) {
        String guild = "";

        for (String _guild : guildSection.getKeys(false)) {
            if (guilds.getStringList("guilds." + _guild + ".members").stream().anyMatch(key -> key.startsWith(player.getUniqueId().toString())))
                guild = _guild;
        }

        return guild;
    }

    public static void removeFromGuild(Player player) {
        Levels.runLeaveCommands(player); // removes their ranks and stuff
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
        getMembers(guild).forEach(member -> {
            if (GuildHandler.isLeader(Bukkit.getOfflinePlayer(member))) {
                memberHeads.add(Menu.renameItem(SkullCreator.itemFromUuid(member), Chat.toColor("&d" + Bukkit.getOfflinePlayer(member).getName()), Chat.toColor("&6&lLEADER")));
            } else {
                memberHeads.add(Menu.renameItem(SkullCreator.itemFromUuid(member), Chat.toColor("&d" + Bukkit.getOfflinePlayer(member).getName()), Chat.toColor("&7Level: " + getUserLevel(Bukkit.getOfflinePlayer(member)))));
            }
        });
        return memberHeads;
    }

    public static int getTotalBalance(String guild) {
        return plugin.getBalance(guild);
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

    public static int getUserLevel(OfflinePlayer player) {
        List<String> members = guilds.getStringList("guilds." + getGuild(player) + ".members");

        for (String member : members) {
            if (member.startsWith(player.getUniqueId().toString())) {
                return Integer.parseInt(UserSerializer.deserialize(member)[1]);
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

        Levels.rankupGrantUser(player, level);
        System.out.println("[ZENONGUILDS] Player: " + player.getName() + " current up to " + level);
        guilds.set("guilds." + getGuild(player) + ".members", members);
        ZenonGuilds.getGuilds().saveConfig();
    }

    public static void setLeader(Player player) {
        guilds.set("guilds." + getGuild(player) + ".leader", player.getUniqueId().toString());
        setUserLevel(player, 10);
        ZenonGuilds.getGuilds().saveConfig();
    }

    public static boolean isLeader(Player player) {
        if (getGuild(player).equals("")) return false;
        if (!guilds.getConfigurationSection("guilds." + getGuild(player)).contains("leader")) return false;

        return guilds.getString("guilds." + getGuild(player) + ".leader").equals(player.getUniqueId().toString());
    }

    public static boolean isLeader(OfflinePlayer player) {
        if (getGuild(player).equals("")) return false;
        if (!guilds.getConfigurationSection("guilds." + getGuild(player)).contains("leader")) return false;

        return guilds.getString("guilds." + getGuild(player) + ".leader").equals(player.getUniqueId().toString());
    }

    public static void removeLeader(Player player) {
        if (!isLeader(player)) return;
        guilds.set("guilds." + getGuild(player) + ".leader", "");
        setUserLevel(player, 1);
        ZenonGuilds.getGuilds().saveConfig();
    }

    public static ArrayList<String> getMemberNames(String guild) {
        ArrayList<String> memberNames = new ArrayList<>();
        getMembers(guild).forEach(member -> memberNames.add(Bukkit.getOfflinePlayer(member).getName()));
        return memberNames;
    }

    public static ArrayList<String> getOnlineMemberNames(String guild) {
        ArrayList<String> onlineMemberNames = new ArrayList<>();
        getOnlineMembers(guild).forEach(member -> onlineMemberNames.add(member.getName()));
        return onlineMemberNames;
    }

    public static OfflinePlayer getLeader(String guild) {
        return Bukkit.getOfflinePlayer(UUID.fromString(guilds.getString("guilds." + guild + ".leader")));
    }
}
