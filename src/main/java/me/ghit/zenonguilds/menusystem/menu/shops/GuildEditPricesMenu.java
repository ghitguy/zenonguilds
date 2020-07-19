package me.ghit.zenonguilds.menusystem.menu.shops;

import dev.dbassett.skullcreator.SkullCreator;
import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.handlers.GuildHandler;
import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.serializers.ShopSerializer;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.utils.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class GuildEditPricesMenu extends Menu {
    private final FileConfiguration config = ZenonGuilds.getConfiguration().getConfig();
    private final ZenonGuilds plugin = ZenonGuilds.getInstance();

    public GuildEditPricesMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Edit Shop Prices";
    }

    @Override
    public int getSlots() {
        return 9 * 4;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        String guild = GuildHandler.getGuild(player);

        if (currentItem == null) return;
        if (!currentItem.hasItemMeta()) return;
        if (!currentItem.getItemMeta().hasDisplayName()) return;
        if (currentItem.getItemMeta().getDisplayName().equals(" ")) return;

        if (ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Return")) {
            player.closeInventory();
            new ShopPageMenu(playerMenuUtility).open();
            return;
        }

        // Check if player has enough items
        if (player.getInventory().contains(currentItem.getType(), currentItem.getMaxStackSize())) {
            int price = ShopSerializer.getSellPrice(currentItem);

            // Check if guild has enough money
            if (GuildHandler.getTotalBalance(GuildHandler.getGuild(player)) < price) {
                player.sendMessage("&cThe guild does not have enough money for you to sell this item!");
                return;
            }

            player.getInventory().removeItem(new ItemStack(currentItem.getType(), currentItem.getMaxStackSize()));
            ZenonGuilds.getEconomy().depositPlayer(player, price);
            plugin.setBalance(guild, plugin.getBalance(guild) - price);

            plugin.setStock(currentItem.getType(), plugin.getStock(currentItem.getType()) + currentItem.getMaxStackSize());
            reloreItem(currentItem, Chat.toColor("&8Left click to sell " + currentItem.getMaxStackSize() + " items"),
                    Chat.toColor("&7Value: &a" + price),
                    Chat.toColor("&7Stock: &d" + plugin.getStock(currentItem.getType())));
            player.sendMessage(Chat.toColor("&aYou sold x64 " + TextUtils.convertToPascal(currentItem.getType().name().toLowerCase()) + " to gain $" + price + "!"));
        } else {
            player.sendMessage(Chat.toColor("&cYou do not have enough items to sell! Required: " + currentItem.getMaxStackSize()));
        }
    }

    @Override
    public void setMenuItems() {
        String guild = GuildHandler.getGuild(playerMenuUtility.getOwner());

        setFillerGlass();
        inventory.setItem(0, renameItem(SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2ViZjkwNzQ5NGE5MzVlOTU1YmZjYWRhYjgxYmVhZmI5MGZiOWJlNDljNzAyNmJhOTdkNzk4ZDVmMWEyMyJ9fX0="), Chat.toColor("&7Return")));

        ArrayList<Integer> itemSlots = new ArrayList<>(Arrays.asList(11, 12, 13, 14, 15, 21, 22, 23));
        ArrayList<ItemStack> shopItems = new ArrayList<>(8);

        if (config.getStringList("shop-items." + guild).size() == 0) {
            System.out.println(Chat.toColor("&cInvalid Shop Setup"));
        }

        // Get shop items
        for (String rawItem : config.getStringList("shop-items." + guild)) {
            ItemStack item = ShopSerializer.getItem(rawItem,
                    Chat.toColor("&8Left click to edit price"),
                    Chat.toColor("&7Value: &a" + ShopSerializer.getSellCost(rawItem)),
                    Chat.toColor("&7Stock: &d" + plugin.getStock(ShopSerializer.getMaterial(rawItem))));
            shopItems.add(item);
        }

        int itemToGet = 0;

        for (int i : itemSlots) {
            inventory.setItem(i, shopItems.get(itemToGet));
            itemToGet += 1;
        }
    }
}
