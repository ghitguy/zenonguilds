package me.ghit.zenonguilds.menusystem.menu;

import dev.dbassett.skullcreator.SkullCreator;
import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.handlers.GuildHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class RankupGuildMenu extends Menu {
    private final ZenonGuilds plugin = ZenonGuilds.getInstance();

    public RankupGuildMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Guild Levelling";
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
        int currentLevel = GuildHandler.getGuildLevel(GuildHandler.getGuild(playerMenuUtility.getOwner()));

        // Locked Level
        if (currentItem.getItemMeta().getDisplayName().startsWith(Chat.toColor("&8Level"))) {
            int levelClicked = Integer.parseInt(currentItem.getItemMeta().getDisplayName().replace(Chat.toColor("&8Level: "), ""));
            if (levelClicked != currentLevel + 1) {
                player.sendMessage(Chat.toColor("&cYou are not able to rank up to that level!"));
            } else { // Is the next level up
                // money requirement
                int requirement = plugin.getLevelRequirements().getInt("guild-levels." + (currentLevel + 1));

                if (ZenonGuilds.getEconomy().getBalance(player) >= requirement) {
                    // Has more than or equal to the right amount of money
                    ZenonGuilds.getEconomy().withdrawPlayer(player, -requirement);

                    currentLevel += 1;
                    GuildHandler.setGuildLevel(GuildHandler.getGuild(player), currentLevel);
                    player.sendMessage(Chat.toColor("&aYou ranked the guild up to " + currentLevel));
                    inventory.setItem(event.getSlot(), makeItem(Material.HONEYCOMB, Chat.toColor("&7Level: &d" + currentLevel)));
                } else { // Don't have enough money
                    player.sendMessage(Chat.toColor("&cYou do not have enough money to rank up the guild, requirement to level up to " + currentLevel + ": &a$" + requirement));
                }
            }
        } else if (ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()).equals("Return")) {
            player.closeInventory();
            new GuildControlPanel(ZenonGuilds.getPlayerMenuUtility(player)).open();
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        int availableLevel = 1;
        int currentLevel = GuildHandler.getGuildLevel(GuildHandler.getGuild(playerMenuUtility.getOwner()));

        ArrayList<Integer> levelSlots = new ArrayList<>(Arrays.asList(11, 12, 13, 14, 15, 20, 21, 22, 23, 24));

        for (int i : levelSlots) {
            if (availableLevel <= currentLevel) {
                inventory.setItem(i, makeItem(Material.HONEYCOMB, Chat.toColor("&7Level: &d" + availableLevel)));
            } else {
                inventory.setItem(i, makeItem(Material.DEAD_TUBE_CORAL, Chat.toColor("&8Level: " + availableLevel)));
            }
            availableLevel += 1;
        }

        inventory.setItem(0, renameItem(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ViZjkwNzQ5NGE5MzVlOTU1YmZjYWRhYjgxYmVhZmI5MGZiOWJlNDljNzAyNmJhOTdkNzk4ZDVmMWEyMyJ9fX0="), Chat.toColor("&7Return")));
    }
}
