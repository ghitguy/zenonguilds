package me.ghit.zenonguilds.utils;

import me.ghit.zenonguilds.ZenonGuilds;
import org.bukkit.configuration.file.FileConfiguration;

public class Messages {

    private static final FileConfiguration messages = ZenonGuilds.getMessages();

    private static final String errorPrefix = Chat.toColor(messages.getString("errorPrefix"));
    private static final String successPrefix = Chat.toColor(messages.getString("successPrefix"));

    public static String tooFewArgs = Chat.toColor(messages.getString("tooFewArgs")).replaceAll("%error%", errorPrefix);
    public static String offlineOrNotAPlayer = Chat.toColor(messages.getString("offlineOrNotAPlayer")).replaceAll("%error%", errorPrefix);
    public static String noPermission = Chat.toColor(messages.getString("noPermission")).replaceAll("%error%", errorPrefix);
    public static String invalidGuild = Chat.toColor(messages.getString("invalidGuild")).replaceAll("%error%", errorPrefix);
    public static String invalidNumber = Chat.toColor(messages.getString("invalidNumber")).replaceAll("%error%", errorPrefix);
    public static String notInGuild = Chat.toColor(messages.getString("notInGuild")).replaceAll("%error%", errorPrefix);
    public static String playerNotInGuild = Chat.toColor(messages.getString("playerNotInGuild")).replaceAll("%error%", errorPrefix);
    public static String invalidBoolean = Chat.toColor(messages.getString("invalidBoolean"));
    public static String lockedLevel = Chat.toColor(messages.getString("lockedLevel")).replaceAll("%error%", errorPrefix);
    public static String invalidMaterial = Chat.toColor(messages.getString("invalidMaterial")).replaceAll("%error%", errorPrefix);

    public static String removedLeader = Chat.toColor(messages.getString("removedLeader")).replaceAll("%success%", successPrefix);
    public static String setGuild = Chat.toColor(messages.getString("setGuild")).replaceAll("%success%", successPrefix);
    public static String setLevel = Chat.toColor(messages.getString("setLevel")).replaceAll("%success%", successPrefix);
    public static String setLeader = Chat.toColor(messages.getString("setLeader")).replaceAll("%success%", successPrefix);
    public static String setGuildLevel = Chat.toColor(messages.getString("setGuildLevel")).replaceAll("%success%", successPrefix);
    public static String setBalance = Chat.toColor(messages.getString("setBalance")).replaceAll("%success%", successPrefix);
    public static String resetStock = Chat.toColor(messages.getString("resetStock")).replaceAll("%success%", successPrefix);
}
