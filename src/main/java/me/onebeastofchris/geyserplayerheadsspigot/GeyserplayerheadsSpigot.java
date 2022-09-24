package me.onebeastofchris.geyserplayerheadsspigot;

import me.onebeastofchris.geyserplayerheadsspigot.events.OnDeath;
import me.onebeastofchris.geyserplayerheadsspigot.events.PlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class GeyserplayerheadsSpigot extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("GeyserPlayerHeads-Spigot starting up now!");
        registerEvents();

    }
    @EventHandler
    private void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnDeath(), this);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("GeyserPlayerHeads-Spigot stopped. Goodbye!");
    }
}
