package com.github.Syaaddd.playerHideLobby;

import com.github.Syaaddd.playerHideLobby.listeners.PlayerJoinListener;
import com.github.Syaaddd.playerHideLobby.listeners.InventoryLockListener;
import com.github.Syaaddd.playerHideLobby.listeners.ToggleInteractListener;
import com.github.Syaaddd.playerHideLobby.managers.PlayerHiderManager;
import com.github.Syaaddd.playerHideLobby.managers.ToggleItemManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerHideLobby extends JavaPlugin {

    private static PlayerHideLobby instance;
    private PlayerHiderManager playerHiderManager;
    private ToggleItemManager toggleItemManager;

    @Override
    public void onEnable() {
        instance = this;
        
        playerHiderManager = new PlayerHiderManager(this);
        toggleItemManager = new ToggleItemManager(this);
        
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryLockListener(this), this);
        getServer().getPluginManager().registerEvents(new ToggleInteractListener(this), this);
        
        getLogger().info("PlayerHide-Lobby telah diaktifkan!");
        getLogger().info("Default: Hide Player ON saat join");
    }

    @Override
    public void onDisable() {
        getLogger().info("PlayerHide-Lobby telah dimatikan!");
    }

    public static PlayerHideLobby getInstance() {
        return instance;
    }

    public PlayerHiderManager getPlayerHiderManager() {
        return playerHiderManager;
    }

    public ToggleItemManager getToggleItemManager() {
        return toggleItemManager;
    }
}
