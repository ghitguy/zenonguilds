package me.ghit.zenonguilds.utils;

import me.ghit.zenonguilds.ZenonGuilds;
import org.bukkit.configuration.file.FileConfiguration;

public class Messages {

    private static final ZenonGuilds plugin = ZenonGuilds.getInstance();
    private static final FileConfiguration messages = plugin.getMessages();

    private static final String errorPrefix = Chat.toColor(messages.getString("errorPrefix"));
    private static final String successPrefix = Chat.toColor(messages.getString("successPrefix"));

    public static final String tooFewArgs = Chat.toColor(messages.getString("tooFewArgs")).replaceAll("%error%", errorPrefix);
    public static final String offlineOrNotAPlayer = Chat.toColor(messages.getString("offlineOrNotAPlayer")).replaceAll("%error%", errorPrefix);
    public static final String noPermission = Chat.toColor(messages.getString("noPermission")).replaceAll("%error%", errorPrefix);
    public static final String invalidGuild = Chat.toColor(messages.getString("invalidGuild")).replaceAll("%error%", errorPrefix);
    public static final String invalidNumber = Chat.toColor(messages.getString("invalidNumber")).replaceAll("%error%", errorPrefix);
    public static final String notInGuild = Chat.toColor(messages.getString("notInGuild")).replaceAll("%error%", errorPrefix);
    public static final String playerNotInGuild = Chat.toColor(messages.getString("playerNotInGuild")).replaceAll("%error%", errorPrefix);
    public static final String invalidBoolean = Chat.toColor(messages.getString("invalidBoolean"));
    public static final String lockedLevel = Chat.toColor(messages.getString("lockedLevel")).replaceAll("%error%", errorPrefix);

    public static final String removedLeader = Chat.toColor(messages.getString("removedLeader")).replaceAll("%success%", successPrefix);
    public static final String setGuild = Chat.toColor(messages.getString("setGuild")).replaceAll("%success%", successPrefix);
    public static final String setLevel = Chat.toColor(messages.getString("setLevel")).replaceAll("%success%", successPrefix);
    public static final String setLeader = Chat.toColor(messages.getString("setLeader")).replaceAll("%success%", successPrefix);
    public static final String setGuildLevel = Chat.toColor(messages.getString("setGuildLevel")).replaceAll("%success%", successPrefix);
    public static final String setBalance = Chat.toColor(messages.getString("setBalance")).replaceAll("%success%", successPrefix);
}
