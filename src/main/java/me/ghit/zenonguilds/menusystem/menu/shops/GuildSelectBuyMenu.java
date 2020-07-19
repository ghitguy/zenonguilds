package me.ghit.zenonguilds.menusystem.menu.shops;

import dev.dbassett.skullcreator.SkullCreator;
import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.utils.Icons;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuildSelectBuyMenu extends Menu {
    public GuildSelectBuyMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Choose a shop to buy from";
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
        switch (ChatColor.stripColor(currentItem.getItemMeta().getDisplayName())) {
            case "Return":
                new ShopPageMenu(playerMenuUtility).open();
                break;
            case "Mining Guild":
                new GuildBuyMenu(playerMenuUtility, "mining").open();
                break;
            case "Alchemy Guild":
                new GuildBuyMenu(playerMenuUtility, "alchemy").open();
                break;
            case "Blacksmith Guild":
                new GuildBuyMenu(playerMenuUtility, "blacksmith").open();
                break;
            case "Mercenary Guild":
                new GuildBuyMenu(playerMenuUtility, "mercenary").open();
                break;
            case "Farming Guild":
                new GuildBuyMenu(playerMenuUtility, "farming").open();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();

        inventory.setItem(0, renameItem(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ViZjkwNzQ5NGE5MzVlOTU1YmZjYWRhYjgxYmVhZmI5MGZiOWJlNDljNzAyNmJhOTdkNzk4ZDVmMWEyMyJ9fX0="), Chat.toColor("&7Return")));
        inventory.setItem(12, Icons.getIcon("mining"));
        inventory.setItem(13, Icons.getIcon("alchemy"));
        inventory.setItem(14, Icons.getIcon("blacksmith"));
        inventory.setItem(15, Icons.getIcon("mercenary"));
        inventory.setItem(16, Icons.getIcon("farming"));
    }
}
