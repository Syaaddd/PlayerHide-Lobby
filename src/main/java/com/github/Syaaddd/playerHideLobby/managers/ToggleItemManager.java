package com.github.Syaaddd.playerHideLobby.managers;

import com.github.Syaaddd.playerHideLobby.PlayerHideLobby;
import com.github.Syaaddd.playerHideLobby.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ToggleItemManager {

    private final PlayerHideLobby plugin;
    public static final int TOGGLE_SLOT = 4; // Slot tengah hotbar
    public static final String ITEM_KEY = "§7§k§r§7§k§r§7§k§r"; // Hidden identifier

    public ToggleItemManager(PlayerHideLobby plugin) {
        this.plugin = plugin;
    }

    public void giveToggleItem(Player player, boolean hidden) {
        ItemStack item = createToggleItem(hidden);
        player.getInventory().setItem(TOGGLE_SLOT, item);
    }

    public ItemStack createToggleItem(boolean hidden) {
        Material material = hidden ? Material.GREEN_DYE : Material.RED_DYE;
        String name = hidden ? "§a§lHIDE PLAYERS §7(Click to Show)" : "§c§lSHOW PLAYERS §7(Click to Hide)";
        String[] lore = new String[]{
            "",
            "§7Status: " + (hidden ? "§a§lHIDDEN" : "§c§lVISIBLE"),
            "",
            "§eClick untuk toggle!",
            ITEM_KEY
        };

        return new ItemBuilder(material)
                .setName(name)
                .setLore(lore)
                .build();
    }

    public boolean isToggleItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        if (!item.getItemMeta().hasLore()) return false;
        
        for (String lore : item.getItemMeta().getLore()) {
            if (lore.contains(ITEM_KEY)) return true;
        }
        return false;
    }

    public void updateToggleItem(Player player, boolean hidden) {
        ItemStack item = createToggleItem(hidden);
        player.getInventory().setItem(TOGGLE_SLOT, item);
    }
}
