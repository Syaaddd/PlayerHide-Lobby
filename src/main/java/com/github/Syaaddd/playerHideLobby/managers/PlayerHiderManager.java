package com.github.Syaaddd.playerHideLobby.managers;

import com.github.Syaaddd.playerHideLobby.PlayerHideLobby;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
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
                // Re-add ke tab list agar economy plugin tetap bisa menemukan player
                restoreTabListEntry(player, onlinePlayer);
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
                restoreTabListEntry(hidingPlayer, newPlayer);
            }
        }
    }

    public void handlePlayerQuit(Player player) {
        hiddenPlayers.remove(player.getUniqueId());
    }

    /**
     * Mengembalikan entry tab list player yang di-hide agar economy plugin
     * masih bisa resolve nama player tersebut.
     * hidePlayer() menghapus player dari tab list client — ini mengembalikannya
     * tanpa membatalkan visual hide.
     */
    private void restoreTabListEntry(Player viewer, Player target) {
        try {
            ServerPlayer nmsTarget = ((CraftPlayer) target).getHandle();
            ClientboundPlayerInfoUpdatePacket packet =
                ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(List.of(nmsTarget));
            ((CraftPlayer) viewer).getHandle().connection.send(packet);
        } catch (Exception e) {
            plugin.getLogger().warning("[PlayerHide] Gagal restore tab list: " + e.getMessage());
        }
    }
}
