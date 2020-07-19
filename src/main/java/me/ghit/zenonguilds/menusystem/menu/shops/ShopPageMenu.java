package me.ghit.zenonguilds.menusystem.menu.shops;

import dev.dbassett.skullcreator.SkullCreator;
import me.ghit.zenonguilds.handlers.GuildHandler;
import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.menusystem.menu.GuildControlPanel;
import me.ghit.zenonguilds.menusystem.menu.GuildMenu;
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

        player.closeInventory();
        switch (ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()).toLowerCase()) {
            case "return":
                if (GuildHandler.isLeader(player)) {
                    new GuildControlPanel(playerMenuUtility).open();
                    break;
                }
                new GuildMenu(playerMenuUtility).open();
                break;
            case "sell":
                new GuildSellMenu(playerMenuUtility).open();
                break;
            case "edit prices":
                new GuildEditPricesMenu(playerMenuUtility).open();
                break;
            case "buy":
                new GuildSelectBuyMenu(playerMenuUtility).open();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();

        inventory.setItem(0, renameItem(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ViZjkwNzQ5NGE5MzVlOTU1YmZjYWRhYjgxYmVhZmI5MGZiOWJlNDljNzAyNmJhOTdkNzk4ZDVmMWEyMyJ9fX0="), Chat.toColor("&7Return")));
        inventory.setItem(12, makeItem(Material.ENDER_PEARL, Chat.toColor("&3Buy"), Chat.toColor("&8Buy items from guilds")));

        if (GuildHandler.isLeader(playerMenuUtility.getOwner())) {
            inventory.setItem(13, makeItem(Material.WRITABLE_BOOK, Chat.toColor("&aEdit Prices"), Chat.toColor("&8Right click to edit the prices in your shop")));
        }

        inventory.setItem(14, makeItem(Material.ENDER_EYE, Chat.toColor("&3Sell"), Chat.toColor("&8Sell items to your guild")));
    }
}
