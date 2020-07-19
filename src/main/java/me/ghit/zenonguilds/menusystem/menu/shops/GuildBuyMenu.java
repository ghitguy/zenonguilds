package me.ghit.zenonguilds.menusystem.menu.shops;

import dev.dbassett.skullcreator.SkullCreator;
import me.ghit.zenonguilds.ZenonGuilds;
import me.ghit.zenonguilds.menusystem.Menu;
import me.ghit.zenonguilds.menusystem.PlayerMenuUtility;
import me.ghit.zenonguilds.utils.Chat;
import me.ghit.zenonguilds.serializers.ShopSerializer;
import me.ghit.zenonguilds.utils.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class GuildBuyMenu extends Menu {

    private FileConfiguration config = ZenonGuilds.getConfiguration().getConfig();
    private ZenonGuilds plugin = ZenonGuilds.getInstance();
    private String guild;

    public GuildBuyMenu(PlayerMenuUtility playerMenuUtility, String guild) {
        super(playerMenuUtility);
        this.guild = guild;
    }

    @Override
    public String getMenuName() {
        return TextUtils.convertToPascal(guild) + " Guild's Shop";
    }

    @Override
    public int getSlots() {
        return 4 * 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) return;
        if (!currentItem.hasItemMeta()) return;
        if (!currentItem.getItemMeta().hasDisplayName()) return;
        if (currentItem.getItemMeta().getDisplayName().equals(" ")) return;

        if (ChatColor.stripColor(currentItem.getItemMeta().getDisplayName()).equalsIgnoreCase("Return")) {
            player.closeInventory();
            new GuildSelectBuyMenu(playerMenuUtility).open();
            return;
        }

        int price = ShopSerializer.getSellPrice(currentItem);

        // Check if player has enough money
        if (plugin.getEconomy().getBalance(player) >= price) {
            if (plugin.getStock(currentItem.getType()) < currentItem.getMaxStackSize()) {
                player.sendMessage(Chat.toColor("&cThere are not enough of these items in this guild's stock to buy!"));
                return;
            }

            ZenonGuilds.getEconomy().withdrawPlayer(player, price);
            player.getInventory().addItem(new ItemStack(currentItem.getType(), currentItem.getMaxStackSize()));
            plugin.setBalance(guild, plugin.getBalance(guild) + price);

            plugin.setStock(currentItem.getType(), plugin.getStock(currentItem.getType()) - currentItem.getMaxStackSize());
            reloreItem(currentItem, Chat.toColor("&8Left click to buy " + currentItem.getMaxStackSize() + " items"),
                    Chat.toColor("&7Value: &a" + price),
                    Chat.toColor("&7Stock: &d" + plugin.getStock(currentItem.getType())));
            player.sendMessage(Chat.toColor("&aYou bought x64 " + TextUtils.convertToPascal(currentItem.getType().name().toLowerCase()) + " for $" + price));
        } else {
            player.sendMessage(Chat.toColor("&cYou do not have &a$" + price + " &cto pay for these items!"));
        }
    }

    @Override
    public void setMenuItems() {
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
                    Chat.toColor("&8Left click to buy " + new ItemStack(ShopSerializer.getMaterial(rawItem)).getMaxStackSize() + " items"),
                    Chat.toColor("&7Value: &a" + ShopSerializer.getBuyCost(rawItem)),
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
