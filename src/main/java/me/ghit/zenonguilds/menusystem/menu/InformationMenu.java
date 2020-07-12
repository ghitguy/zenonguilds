package me.ghit.zenonguilds.menusystem.menu;

import dev.dbassett.skullcreator.SkullCreator;
import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.utils.GuildHandler;
import me.ghit.zenonguilds.utils.icons.Icons;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InformationMenu extends Menu {
    public InformationMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Guilds Information";
    }

    @Override
    public int getSlots() {
        return 5 * 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) return;
        if (!currentItem.hasItemMeta()) return;
        if (!currentItem.getItemMeta().hasDisplayName()) return;
        if (currentItem.getItemMeta().getDisplayName().equals(" ")) return;

        switch (ChatColor.stripColor(currentItem.getItemMeta().getDisplayName())) {
            case "Members":
                player.closeInventory();
                new MembersMenu(ZenonGuilds.getPlayerMenuUtility(player)).open();
                break;
            case "Return":
                player.closeInventory();

                if (!GuildHandler.isLeader(player)) {
                    new GuildMenu(ZenonGuilds.getPlayerMenuUtility(player)).open();
                } else {
                    new GuildControlPanel(ZenonGuilds.getPlayerMenuUtility(player)).open();
                }
                break;
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();

        ItemStack returnItem = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ViZjkwNzQ5NGE5MzVlOTU1YmZjYWRhYjgxYmVhZmI5MGZiOWJlNDljNzAyNmJhOTdkNzk4ZDVmMWEyMyJ9fX0=");
        inventory.setItem(0, renameItem(returnItem, Chat.toColor("&7Return")));

        ItemStack membersItem = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTMxODY3YTY5ODgwZGMxMjRjZjE3MzViZGVlZDI3ODhiZWM0ZjI2YjUyYjdkMzFhZWY5NjAxYzgwZjA4ODhlIn19fQ==");
        inventory.setItem(13, renameItem(membersItem, Chat.toColor("&9Members"), Chat.toColor("&8Left click to view the members of your guild")));

        Player player = playerMenuUtility.getOwner();

        inventory.setItem(29, Icons.getStatsIcon("mining", player));
        inventory.setItem(30, Icons.getStatsIcon("alchemy", player));
        inventory.setItem(31, Icons.getStatsIcon("blacksmith", player));
        inventory.setItem(32, Icons.getStatsIcon("mercenary", player));
        inventory.setItem(33, Icons.getStatsIcon("farming", player));
    }
}
