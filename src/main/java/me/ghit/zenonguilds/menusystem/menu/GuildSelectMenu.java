package me.ghit.zenonguilds.menusystem.menu;

import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.utils.GuildHandler;
import me.ghit.zenonguilds.utils.icons.Icons;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuildSelectMenu extends Menu {
    public GuildSelectMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Guilds";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) return;
        if (!currentItem.hasItemMeta()) return;
        if (!currentItem.getItemMeta().hasDisplayName()) return;
        if (currentItem.getItemMeta().getDisplayName().equals(" ")) return;

        player.sendTitle(Chat.toColor("&a&lYou Joined A Guild!"), Chat.toColor("&7Welcome To The " + currentItem.getItemMeta().getDisplayName()), 5, 25, 10);

        switch (ChatColor.stripColor(currentItem.getItemMeta().getDisplayName())) {
            case "Mining Guild":
                GuildHandler.setGuild(player, "mining");
                break;
            case "Alchemy Guild":
                GuildHandler.setGuild(player, "alchemy");
                break;
            case "Blacksmith Guild":
                GuildHandler.setGuild(player, "blacksmith");
                break;
            case "Mercenary Guild":
                GuildHandler.setGuild(player, "mercenary");
                break;
            case "Farming Guild":
                GuildHandler.setGuild(player, "farming");
                break;
        }

        player.closeInventory();
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();

        inventory.setItem(2, Icons.getIcon("mining"));
        inventory.setItem(3, Icons.getIcon("alchemy"));
        inventory.setItem(4, Icons.getIcon("blacksmith"));
        inventory.setItem(5, Icons.getIcon("mercenary"));
        inventory.setItem(6, Icons.getIcon("farming"));
    }
}
