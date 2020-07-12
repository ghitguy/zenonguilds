package me.ghit.zenonguilds.menusystem.menu;

import me.ghit.zenonguilds.menusystem.PaginatedMenu;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.utils.GuildHandler;
import me.ghit.zenonguilds.utils.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MembersMenu extends PaginatedMenu {
    public MembersMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    ArrayList<ItemStack> memberHeads = GuildHandler.getMemberHeads(GuildHandler.getGuild(playerMenuUtility.getOwner()));

    @Override
    public String getMenuName() {
        return TextUtil.convertToPascal(GuildHandler.getGuild(playerMenuUtility.getOwner())) + " Members";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem().getType().equals(Material.BARRIER)) {

            //close inventory
            player.closeInventory();

        } else if(event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
            if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Left")) {
                if (page == 0) {
                    player.sendMessage(ChatColor.GRAY + "You are already on the first page.");
                } else {
                    page = page - 1;
                    super.open();
                }
            } else if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Right")) {
                if (!((index + 1) >= memberHeads.size())) {
                    page = page + 1;
                    super.open();
                } else {
                    player.sendMessage(ChatColor.GRAY + "You are on the last page.");
                }
            }
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        for (int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if (index >= memberHeads.size()) break;
            if (memberHeads.get(index) != null) {
                inventory.addItem(memberHeads.get(index));
            }
        }
    }
}
