package com.github.Syaaddd.playerHideLobby.listeners;

import com.github.Syaaddd.playerHideLobby.PlayerHideLobby;
import com.github.Syaaddd.playerHideLobby.managers.ToggleItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class InventoryLockListener implements Listener {

    private final PlayerHideLobby plugin;

    public InventoryLockListener(PlayerHideLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        
        Player player = (Player) event.getWhoClicked();
        
        // Cek jika yang diklik adalah slot toggle item
        if (event.getRawSlot() == ToggleItemManager.TOGGLE_SLOT + 36) { // Convert ke inventory slot
            event.setCancelled(true);
            player.updateInventory();
            return;
        }
        
        // Cek jika item yang diklik adalah toggle item
        if (event.getCurrentItem() != null && 
            plugin.getToggleItemManager().isToggleItem(event.getCurrentItem())) {
            event.setCancelled(true);
            player.updateInventory();
            return;
        }
        
        // Cek jika item yang di-cursor adalah toggle item
        if (event.getCursor() != null && 
            plugin.getToggleItemManager().isToggleItem(event.getCursor())) {
            event.setCancelled(true);
            player.updateInventory();
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        
        if (event.getOldCursor() != null && 
            plugin.getToggleItemManager().isToggleItem(event.getOldCursor())) {
            event.setCancelled(true);
            ((Player) event.getWhoClicked()).updateInventory();
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (plugin.getToggleItemManager().isToggleItem(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
            event.getPlayer().updateInventory();
        }
    }
}
