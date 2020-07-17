package me.ghit.zenonguilds.menusystem.menu.shops;

import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.menusystem.menu.GuildSelectMenu;
import me.ghit.zenonguilds.utils.Chat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ShopPageMenu extends Menu {
    public ShopPageMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Guilds Shop";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) return;
        if (!currentItem.hasItemMeta()) return;
        if (!currentItem.getItemMeta().hasDisplayName()) return;
        if (currentItem.getItemMeta().getDisplayName().equals(" ")) return;

        switch (ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()).toLowerCase()) {
            case "sell":
                player.closeInventory();
                new GuildSellMenu(playerMenuUtility).open();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        inventory.setItem(12, makeItem(Material.ENDER_PEARL, Chat.toColor("&3Buy"), Chat.toColor("&8Buy items from guilds")));
        inventory.setItem(14, makeItem(Material.ENDER_EYE, Chat.toColor("&3Sell"), Chat.toColor("&8Sell items to your guild")));
    }
}
