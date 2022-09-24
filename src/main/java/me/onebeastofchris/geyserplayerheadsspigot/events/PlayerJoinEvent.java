package me.onebeastofchris.geyserplayerheadsspigot.events;

import me.onebeastofchris.geyserplayerheadsspigot.TextureApplier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;

public class PlayerJoinEvent implements Listener {
    private static final HashMap<UUID, TextureApplier> textureMap = new HashMap<>();
    @EventHandler
    public void onSpawn(org.bukkit.event.player.PlayerJoinEvent event) {
        Player x = event.getPlayer();
        // Start new thread, so we don't lock up main thread if we get a bad server response.
        Executors.newSingleThreadExecutor().execute(() ->
                textureMap.put(x.getUniqueId(), new TextureApplier(x)));
    }

    public static HashMap<UUID, TextureApplier> getTextureID() {
        return textureMap;
    }
}
