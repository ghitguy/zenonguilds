package me.ghit.zenonguilds.menusystem.menu.shops;

import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.utils.GuildHandler;
import me.ghit.zenonguilds.utils.ShopSerializer;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class GuildSellMenu extends Menu {
    private FileConfiguration config = ZenonGuilds.getConfiguration().getConfig();

    public GuildSellMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Sell";
    }

    @Override
    public int getSlots() {
        return 9 * 4;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

    }

    @Override
    public void setMenuItems() {
        String guild = GuildHandler.getGuild(playerMenuUtility.getOwner());

        setFillerGlass();

        ArrayList<Integer> itemSlots = new ArrayList<>(Arrays.asList(11, 12, 13, 14, 15, 21, 22, 23));

        ArrayList<ItemStack> shopItems = new ArrayList<>(8);

        // Get shop items
        for (String rawItem : config.getStringList("shop-items." + guild)) {
            ItemStack item = ShopSerializer.getItem(rawItem, Chat.toColor("&8Left click to sell"), Chat.toColor("&6Cost: " + ShopSerializer.getSellCost(rawItem)));
            shopItems.add(item);
        }

        int itemToGet = 0;

        for (int i : itemSlots) {
            inventory.setItem(i, shopItems.get(itemToGet));
            itemToGet += 1;
        }
    }
}
