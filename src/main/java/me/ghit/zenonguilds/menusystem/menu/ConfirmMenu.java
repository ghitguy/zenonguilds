package me.ghit.zenonguilds.menusystem.menu;

import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.utils.GuildHandler;
import me.ghit.zenonguilds.utils.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ConfirmMenu extends Menu {
    public ConfirmMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Confirm: Leave " + TextUtil.convertToPascal(GuildHandler.getGuild(playerMenuUtility.getOwner())) + " Guild?";
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

        switch (ChatColor.stripColor(currentItem.getItemMeta().getDisplayName())) {
            case "Confirm":
                player.closeInventory();
                GuildHandler.removeFromGuild(player);
                player.sendTitle(Chat.toColor("&c&lYou Left Your Guild!"), Chat.toColor("&7Sorry to see you go :("), 5, 25, 10);
                break;
            case "Cancel":
                player.closeInventory();
                new GuildMenu(playerMenuUtility).open();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();

        inventory.setItem(3, makeItem(Material.RED_DYE, Chat.toColor("&cCancel"), Chat.toColor("&8Left click to cancel action")));
        inventory.setItem(5, makeItem(Material.LIME_DYE, Chat.toColor("&aConfirm"), Chat.toColor("&8Left click to &nleave guild")));
    }
}
