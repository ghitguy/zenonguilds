package me.ghit.zenonguilds.menusystem.menu;

import dev.dbassett.skullcreator.SkullCreator;
import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.handlers.GuildHandler;
import me.ghit.zenonguilds.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class RankupUserMenu extends Menu {
    public RankupUserMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }
    private ZenonGuilds plugin = ZenonGuilds.getInstance();

    @Override
    public String getMenuName() {
        return "Levels";
    }

    @Override
    public int getSlots() {
        return 9 * 4;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) return;
        if (!currentItem.hasItemMeta()) return;
        if (!currentItem.getItemMeta().hasDisplayName()) return;
        if (currentItem.getItemMeta().getDisplayName().equals(" ")) return;
        int currentLevel = GuildHandler.getUserLevel(playerMenuUtility.getOwner());

        // Locked Level
        if (currentItem.getItemMeta().getDisplayName().startsWith(Chat.toColor("&8Level"))) {
            int levelClicked = Integer.parseInt(currentItem.getItemMeta().getDisplayName().replace(Chat.toColor("&8Level: "), ""));
            if (levelClicked != currentLevel + 1) {
                player.sendMessage(Chat.toColor("&cYou are not able to level up to that level!"));
            } else { // Is the next level up
                int requirement = plugin.getLevelRequirements().getInt(GuildHandler.getGuild(player) + ".user-levels." + (currentLevel + 1) + ".cost");

                if ((currentLevel + 1) == 10) {
                    player.sendMessage(Messages.lockedLevel);
                    return;
                }

                if (plugin.getEconomy().getBalance(player) >= requirement) {
                    // Has more than or equal to the right amount of money
                    System.out.println("RANKUP USER");

                    plugin.getEconomy().withdrawPlayer(player, requirement);

                    currentLevel += 1;
                    GuildHandler.setUserLevel(player, currentLevel);
                    player.sendMessage(Chat.toColor("&aYou levelled up to " + currentLevel));
                    inventory.setItem(event.getSlot(), makeItem(Material.BUBBLE_CORAL, Chat.toColor("&7Level: &d" + currentLevel)));
                } else { // Don't have enough money
                    player.sendMessage(Chat.toColor("&cYou do not have enough money to rank up, requirement to level up to " + currentLevel + ": &a$" + requirement));
                }
            }
        } else if (ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()).equals("Return")) {
            player.closeInventory();
            new GuildMenu(playerMenuUtility).open();
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        int availableLevel = 1;
        int currentLevel = GuildHandler.getUserLevel(playerMenuUtility.getOwner());

        ArrayList<Integer> levelSlots = new ArrayList<>(Arrays.asList(11, 12, 13, 14, 15, 20, 21, 22, 23, 24));

        for (int i : levelSlots) {
            if (availableLevel <= currentLevel) {
                inventory.setItem(i, makeItem(Material.BUBBLE_CORAL, Chat.toColor("&7Level: &d" + availableLevel)));
            } else {
                inventory.setItem(i, makeItem(Material.DEAD_BUBBLE_CORAL, Chat.toColor("&8Level: " + availableLevel)));
            }
            availableLevel += 1;
        }

        inventory.setItem(0, renameItem(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ViZjkwNzQ5NGE5MzVlOTU1YmZjYWRhYjgxYmVhZmI5MGZiOWJlNDljNzAyNmJhOTdkNzk4ZDVmMWEyMyJ9fX0="), Chat.toColor("&7Return")));
    }
}