package me.ghit.zenonguilds.menusystem.menu;

import dev.dbassett.skullcreator.SkullCreator;
import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.menusystem.menu.shops.ShopPageMenu;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.utils.GuildHandler;
import me.ghit.zenonguilds.utils.TextUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuildMenu extends Menu {
    public GuildMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return TextUtil.convertToPascal(GuildHandler.getGuild(playerMenuUtility.getOwner())) + " Guild";
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

        switch (ChatColor.stripColor(currentItem.getItemMeta().getDisplayName())) {
            case "Leave Guild":
                new ConfirmMenu(playerMenuUtility).open();
                break;
            case "Information":
                new InformationMenu(ZenonGuilds.getPlayerMenuUtility(player)).open();
                break;
            case "Shop":
                new ShopPageMenu(ZenonGuilds.getPlayerMenuUtility(player)).open();
                break;
        }

        if (ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()).startsWith("Your Level")) {
            // Open levels
            player.closeInventory();
            new RankupUserMenu(ZenonGuilds.getPlayerMenuUtility(player)).open();
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        inventory.setItem(11, makeItem(Material.RED_CONCRETE, Chat.toColor("&cLeave Guild"), Chat.toColor("&8Left click to leave this guild")));
        inventory.setItem(13, renameItem(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZjYzQ4NmMyYmUxY2I5ZGZjYjJlNTNkZDlhM2U5YTg4M2JmYWRiMjdjYjk1NmYxODk2ZDYwMmI0MDY3In19fQ=="), Chat.toColor("&6Shop"), Chat.toColor("&8Left click to open the shop")));
        inventory.setItem(14, renameItem(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTg5MDFmNzE0MzRkNTM5MjA3NDc2OTRmNjgyZjVlNTNiOGY3NDQ4M2YyNjljMzg0YzY5MzZiN2Q4NjU4MiJ9fX0="), Chat.toColor("&aYour Level: " + GuildHandler.getUserLevel(playerMenuUtility.getOwner())), Chat.toColor("&8Left click to view levelling")));
        inventory.setItem(15, makeItem(Material.PAPER, Chat.toColor("&9Information"), Chat.toColor("&8Left click to view information about guilds")));
    }
}
