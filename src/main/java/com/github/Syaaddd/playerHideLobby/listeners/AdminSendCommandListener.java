package com.github.Syaaddd.playerHideLobby.listeners;

import com.github.Syaaddd.playerHideLobby.PlayerHideLobby;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class AdminSendCommandListener implements Listener {

    private final PlayerHideLobby plugin;

    public AdminSendCommandListener(PlayerHideLobby plugin) {
        this.plugin = plugin;
    }

    /**
     * Intercept "/send current <server>" agar semua player (termasuk yang di-hide)
     * ikut dikirim. Tanpa ini, beberapa implementasi /send hanya mengirim player
     * yang terlihat oleh command sender karena efek hidePlayer().
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSendCommand(PlayerCommandPreprocessEvent event) {
        if (!event.getPlayer().hasPermission("playerhide.admin")) return;

        String[] parts = event.getMessage().trim().split("\\s+");
        if (parts.length < 3) return;
        if (!parts[0].equalsIgnoreCase("/send")) return;
        if (!parts[1].equalsIgnoreCase("current")) return;

        String targetServer = parts[2];
        event.setCancelled(true);

        int count = 0;
        for (Player target : plugin.getServer().getOnlinePlayers()) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(targetServer);
            target.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
            count++;
        }

        event.getPlayer().sendMessage(
            "§a[PlayerHide] §fMengirim §a" + count + " §fplayer ke server §a" + targetServer
        );
    }
}
