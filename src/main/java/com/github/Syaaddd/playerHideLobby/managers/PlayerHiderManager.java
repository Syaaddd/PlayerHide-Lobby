package com.github.Syaaddd.playerHideLobby.managers;

import com.github.Syaaddd.playerHideLobby.PlayerHideLobby;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerHiderManager {

    private final PlayerHideLobby plugin;
    private final Set<UUID> hiddenPlayers;

    public PlayerHiderManager(PlayerHideLobby plugin) {
        this.plugin = plugin;
        this.hiddenPlayers = new HashSet<>();
    }

    public void setPlayerHidden(Player player, boolean hidden) {
        if (hidden) {
            hideAllPlayers(player);
            hiddenPlayers.add(player.getUniqueId());
        } else {
            showAllPlayers(player);
            hiddenPlayers.remove(player.getUniqueId());
        }
    }

    public boolean isPlayerHidden(Player player) {
        return hiddenPlayers.contains(player.getUniqueId());
    }

    public void hideAllPlayers(Player player) {
        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            if (!onlinePlayer.equals(player)) {
                player.hidePlayer(plugin, onlinePlayer);
            }
        }
    }

    public void showAllPlayers(Player player) {
        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            if (!onlinePlayer.equals(player)) {
                player.showPlayer(plugin, onlinePlayer);
            }
        }
    }

    public void updateHiddenForNewPlayer(Player newPlayer) {
        for (UUID uuid : hiddenPlayers) {
            if (uuid.equals(newPlayer.getUniqueId())) continue;
            Player hidingPlayer = plugin.getServer().getPlayer(uuid);
            if (hidingPlayer != null && hidingPlayer.isOnline()) {
                hidingPlayer.hidePlayer(plugin, newPlayer);
            }
        }
    }

    public void handlePlayerQuit(Player player) {
        hiddenPlayers.remove(player.getUniqueId());
    }
}
