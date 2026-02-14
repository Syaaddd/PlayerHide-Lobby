package com.github.Syaaddd.playerHideLobby.listeners;

import com.github.Syaaddd.playerHideLobby.PlayerHideLobby;
import com.github.Syaaddd.playerHideLobby.managers.ToggleItemManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ToggleInteractListener implements Listener {

    private final PlayerHideLobby plugin;

    public ToggleInteractListener(PlayerHideLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Hanya proses main hand untuk mencegah double trigger
        if (event.getHand() != EquipmentSlot.HAND) return;
        
        Player player = event.getPlayer();
        
        // Cek jika action adalah RIGHT_CLICK_AIR atau RIGHT_CLICK_BLOCK
        if (event.getAction() != Action.RIGHT_CLICK_AIR && 
            event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        
        // Cek jika item di tangan adalah toggle item
        if (event.getItem() == null || 
            !plugin.getToggleItemManager().isToggleItem(event.getItem())) {
            return;
        }
        
        // Cancel event agar tidak trigger block interaction
        event.setCancelled(true);
        
        // Toggle status
        boolean currentlyHidden = plugin.getPlayerHiderManager().isPlayerHidden(player);
        boolean newStatus = !currentlyHidden;
        
        // Update player hide status
        plugin.getPlayerHiderManager().setPlayerHidden(player, newStatus);
        
        // Update toggle item
        plugin.getToggleItemManager().updateToggleItem(player, newStatus);
        
        // Kirim pesan ke player
        if (newStatus) {
            player.sendMessage("§a§l✔ §aPlayer telah disembunyikan!");
        } else {
            player.sendMessage("§c§l✖ §cPlayer telah ditampilkan!");
        }
    }
}
