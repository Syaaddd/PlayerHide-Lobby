package com.github.Syaaddd.playerHideLobby.listeners;

import com.github.Syaaddd.playerHideLobby.PlayerHideLobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final PlayerHideLobby plugin;

    public PlayerJoinListener(PlayerHideLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Berikan toggle item ke slot 4 (tengah hotbar)
        plugin.getToggleItemManager().giveToggleItem(player, true);
        
        // Default: Hide player ON saat join
        plugin.getPlayerHiderManager().setPlayerHidden(player, true);
        
        // Update pemain lain yang sedang hide untuk juga hide pemain baru ini
        plugin.getPlayerHiderManager().updateHiddenForNewPlayer(player);
    }
}
