package me.ghit.zenonguilds;

import me.ghit.zenonguilds.commands.CommandHandler;
import me.ghit.zenonguilds.handlers.GuildHandler;
import me.ghit.zenonguilds.listeners.MenuListener;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.serializers.ShopSerializer;
import me.ghit.zenonguilds.utils.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.logging.Logger;

public final class ZenonGuilds extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    private final Path stockDataPath = getDataFolder().toPath().resolve("stock.dat");
    private final HashMap<Material, Integer> stock = Kryogenic.thaw(stockDataPath, HashMap::new);

    private final Path guildBalanceDataPath = getDataFolder().toPath().resolve("guild_balances.dat");
    private final HashMap<String, Integer> guildBalances = Kryogenic.thaw(guildBalanceDataPath, HashMap::new);

    private static Config guilds;
    private static Config messages;
    private static Config levels;
    private static Config config;
    private static Economy econ = null;

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
        config = new Config(this, "config.yml");
        plugin = this;

        // If stock is empty, add every material and 0 stock
        if (stock.size() == 0) {
             config.getConfig().getConfigurationSection("shop-items").getKeys(false).forEach(key -> {
                 for (String raw : config.getConfig().getStringList("shop-items." + key)) {
                     stock.put(ShopSerializer.getMaterial(raw), 0);
                 }
             });
        }

        // If guild balances are empty, add every guild and $0
        if (guildBalances.size() == 0) {
            GuildHandler.getGuilds().forEach(key -> guildBalances.put(key, 0));
        }

        getCommand("guild").setExecutor(new CommandHandler());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    @Override
    public void onDisable() {
        Kryogenic.freeze(stockDataPath, stock);
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

    public static Economy getEconomy() {
        return econ;
    }
    public static ZenonGuilds getInstance() {
        return plugin;
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
    public FileConfiguration getLevelRequirements() {
        return levels.getConfig();
    }

    public int getStock(Material material) {
        return stock.get(material);
    }

    public void setStock(Material material, int number) {
        stock.put(material, number);
    }

    public int getBalance(String guild) {
        return guildBalances.get(guild);
    }

    public void setBalance(String guild, int amount) {
        guildBalances.put(guild, amount);
    }
}
