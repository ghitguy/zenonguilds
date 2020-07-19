package me.ghit.zenonguilds.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.handlers.GuildHandler;
import org.bukkit.entity.Player;

public class GuildPlaceholders extends PlaceholderExpansion {

    private ZenonGuilds plugin;

    public GuildPlaceholders(ZenonGuilds plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor() {
        return "ghit";
    }

    @Override
    public String getIdentifier() {
        return "guilds";
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null){
            return "";
        }

        // If player is not in guild
        if (GuildHandler.getGuild(player).equals("")) {
            return "";
        }

        // %guilds_level%
        if(identifier.equals("level")){
            return String.valueOf(GuildHandler.getUserLevel(player));
        }

        // %guilds_guild%
        if(identifier.equals("guild")){
            return TextUtils.convertToPascal(GuildHandler.getGuild(player));
        }

        // %guilds_guildlevel%
        if(identifier.equals("guildlevel")){
            return String.valueOf(GuildHandler.getGuildLevel(GuildHandler.getGuild(player)));
        }

        // %guilds_guildbalance%
        if(identifier.equals("guildbalance")){
            return String.valueOf(GuildHandler.getTotalBalance(GuildHandler.getGuild(player)));
        }

        // %guilds_guildleader%
        if(identifier.equals("guildleader")){
            return GuildHandler.getLeader(GuildHandler.getGuild(player)).getName();
        }

        // We return null if an invalid placeholder (f.e. %guilds_placeholder3%)
        // was provided
        return null;
    }
}
