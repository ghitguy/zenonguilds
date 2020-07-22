package me.ghit.zenonguilds;

import me.ghit.zenonguilds.commands.CommandHandler;
import me.ghit.zenonguilds.handlers.GuildHandler;
import me.ghit.zenonguilds.listeners.MenuListener;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.*;
import java.util.logging.Logger;

public final class ZenonGuilds extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    private final Path shopDataPath = getDataFolder().toPath().resolve("shop-data.dat");
    private final HashMap<String, String> shopData = Kryogenic.thaw(shopDataPath, HashMap::new);

    private final Path guildBalanceDataPath = getDataFolder().toPath().resolve("guild-balances.dat");
    private final HashMap<String, Integer> guildBalances = Kryogenic.thaw(guildBalanceDataPath, HashMap::new);

    private Config guilds;
    private Config messages;
    private Config levels;
    private Config shopItemsConfig;
    private Economy econ = null;

    private static ZenonGuilds plugin;

    @Override
    public void onEnable() {
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Small check to make sure that PlaceholderAPI is installed
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new GuildPlaceholders(this).register();
        }

        guilds = new Config(this, "guilds.yml");
        levels = new Config(this, "levels.yml");
        messages = new Config(this, "messages.yml");
        shopItemsConfig = new Config(this, "shop-items.yml");
        plugin = this;

        // If guild balances are empty, add every guild and $0
        if (guildBalances.size() == 0) {
            GuildHandler.getGuilds().forEach(key -> guildBalances.put(key, 0));
        }

        getCommand("guild").setExecutor(new CommandHandler());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    @Override
    public void onDisable() {
        Kryogenic.freeze(shopDataPath, shopData);
        Kryogenic.freeze(guildBalanceDataPath, guildBalances);
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

    public Economy getEconomy() {
        return econ;
    }
    public static ZenonGuilds getInstance() {
        return plugin;
    }
    public Config getGuilds() {
        return guilds;
    }
    public FileConfiguration getMessages() {
        return messages.getConfig();
    }
    public FileConfiguration getLevelRequirements() {
        return levels.getConfig();
    }

    public int getBalance(String guild) {
        return guildBalances.get(guild);
    }

    public void setBalance(String guild, int amount) {
        guildBalances.put(guild, amount);
    }
}
