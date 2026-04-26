package com.github.Syaaddd.playerHideLobby.managers;

import com.github.Syaaddd.playerHideLobby.PlayerHideLobby;
import com.github.Syaaddd.playerHideLobby.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ToggleItemManager {

    private final PlayerHideLobby plugin;
    private final NamespacedKey toggleKey;
    public static final int TOGGLE_SLOT = 8;

    public ToggleItemManager(PlayerHideLobby plugin) {
        this.plugin = plugin;
        this.toggleKey = new NamespacedKey(plugin, "toggle_item");
    }

    public void giveToggleItem(Player player, boolean hidden) {
        player.getInventory().setItem(TOGGLE_SLOT, createToggleItem(hidden));
    }

    public ItemStack createToggleItem(boolean hidden) {
        Material material = hidden ? Material.LIME_DYE : Material.GRAY_DYE;
        String name = hidden ? "§a§lHIDE PLAYERS §7(Click to Disable)" : "§7§lHIDE PLAYERS §7(Click to Enable)";
        String[] lore = {
            "",
            "§7Status: " + (hidden ? "§a§lENABLED" : "§7§lDISABLED"),
            "",
            "§eClick untuk toggle!"
        };

        ItemStack item = new ItemBuilder(material).setName(name).setLore(lore).build();
        // Gunakan PDC sebagai identifier — lebih efisien dari cek lore string
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(toggleKey, PersistentDataType.BYTE, (byte) 1);
        item.setItemMeta(meta);
        return item;
    }

    public boolean isToggleItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(toggleKey, PersistentDataType.BYTE);
    }

    public void updateToggleItem(Player player, boolean hidden) {
        player.getInventory().setItem(TOGGLE_SLOT, createToggleItem(hidden));
    }
}
