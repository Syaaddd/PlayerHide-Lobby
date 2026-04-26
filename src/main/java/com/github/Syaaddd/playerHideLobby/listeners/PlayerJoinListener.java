package com.github.Syaaddd.playerHideLobby.listeners;

import com.github.Syaaddd.playerHideLobby.PlayerHideLobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinListener implements Listener {

    private final PlayerHideLobby plugin;

    public PlayerJoinListener(PlayerHideLobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Delay 1 detik untuk menghindari konflik dengan DeluxeHub
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline()) {
                    // Berikan toggle item ke slot 8 (terakhir hotbar)
                    plugin.getToggleItemManager().giveToggleItem(player, true);

                    // Default: Hide player ON saat join
                    plugin.getPlayerHiderManager().setPlayerHidden(player, true);

                    // Update pemain lain yang sedang hide untuk juga hide pemain baru ini
                    plugin.getPlayerHiderManager().updateHiddenForNewPlayer(player);
                }
            }
        }.runTaskLater(plugin, 20L); // 20 ticks = 1 detik
    }
}
