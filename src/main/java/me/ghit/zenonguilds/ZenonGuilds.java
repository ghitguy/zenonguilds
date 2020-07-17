package me.ghit.zenonguilds;

import me.ghit.zenonguilds.commands.CommandHandler;
import me.ghit.zenonguilds.listeners.MenuListener;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.utils.Config;
import me.ghit.zenonguilds.utils.GuildPlaceholders;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Logger;

public final class ZenonGuilds extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    private static Config guilds;
    private static Config messages;
    private static Config levels;
    private static Config config;
    private static Economy econ = null;

    @Override
    public void onEnable() {
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Small check to make sure that PlaceholderAPI is installed
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new GuildPlaceholders(this).register();
        }

        guilds = new Config(this, "guilds.yml");
        levels = new Config(this, "levels.yml");
        messages = new Config(this, "messages.yml");
        config = new Config(this, "config.yml");
        getCommand("guild").setExecutor(new CommandHandler());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player player) {
        PlayerMenuUtility playerMenuUtility;

        if (playerMenuUtilityMap.containsKey(player)) {
            return playerMenuUtilityMap.get(player);
        } else {
            playerMenuUtility = new PlayerMenuUtility(player);
            playerMenuUtilityMap.put(player, playerMenuUtility);
            return playerMenuUtility;
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Config getGuilds() {
        return guilds;
    }
    public static Config getConfiguration() {
        return config;
    }
    public static FileConfiguration getMessages() {
        return messages.getConfig();
    }
    public static FileConfiguration getLevelRequirements() {
        return levels.getConfig();
    }
    public static Economy getEconomy() {
        return econ;
    }

}
