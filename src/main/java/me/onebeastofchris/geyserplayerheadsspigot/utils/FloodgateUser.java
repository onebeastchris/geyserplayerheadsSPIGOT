package me.onebeastofchris.geyserplayerheadsspigot.utils;

import org.geysermc.floodgate.api.FloodgateApi;

import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public class FloodgateUser {
    /**
     * Determines if a player is a bedrock player
     *
     * @param uuid the UUID to determine
     * @return true if the player is from floodgate
     */
    public static boolean isFloodgatePlayer(UUID uuid) {
        if (FloodgateApi.getInstance() == null) {
            getLogger().info("Floodgate is not installed! We will check the bedrock . prefix.");
            return false;
        }
        return FloodgateApi.getInstance().isFloodgatePlayer(uuid);
    }
    public static String FloodgatePrefix() {
        if (FloodgateApi.getInstance() == null) {
            getLogger().info("Floodgate is not installed! We will check the bedrock . prefix.");
            return ".";
        }
        return FloodgateApi.getInstance().getPlayerPrefix();
    }
}
