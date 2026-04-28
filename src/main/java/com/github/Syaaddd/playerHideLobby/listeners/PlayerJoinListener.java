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

        // Hide langsung — tidak ada delay
        plugin.getPlayerHiderManager().setPlayerHidden(player, true);
        plugin.getPlayerHiderManager().updateHiddenForNewPlayer(player);

        // Item diberikan setelah DeluxeHub selesai mengatur hotbar
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline()) {
                    plugin.getToggleItemManager().giveToggleItem(player, true);
                }
            }
        }.runTaskLater(plugin, 20L);
    }
}
