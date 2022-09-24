package me.onebeastofchris.geyserplayerheadsspigot;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.onebeastofchris.geyserplayerheadsspigot.utils.FloodgateUser;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public class TextureApplier {
    private final String textureID;
    private final String encoded;
    private final String playerName;
    private final UUID uuid;

    public TextureApplier(Player player) {
        if (player.getDisplayName().startsWith(".") || FloodgateUser.isFloodgatePlayer(player.getUniqueId())) {
            playerName = player.getDisplayName().replace(FloodgateUser.FloodgatePrefix(), "");
            long xuid = getXuid(playerName);
            textureID = getTextureId(xuid);
            String toBeEncoded = "{\"textures\":{\"SKIN\":{\"url\":\"https://textures.minecraft.net/texture/" + getTextureID() + "\"}}}";
            encoded = Base64.getEncoder().encodeToString(toBeEncoded.getBytes());
            uuid = player.getUniqueId();
            getLogger().info(playerName);
            getLogger().info(String.valueOf(xuid));
            getLogger().info(encoded);

        } else {
            playerName = player.getDisplayName();
            textureID = null;
            encoded = null;
            uuid = player.getUniqueId();
            getLogger().info(playerName);
        }
    }

    public long getXuid(String playerName) {
        JsonObject xuid = new ServerRequest().webRequest("https://api.geysermc.org/v2/xbox/xuid/" + playerName);
        if (xuid.has("xuid")) {
            return xuid.get("xuid").getAsLong();
        } else if (xuid.has("message")) {
            return 0;
        }
        return -1;
    }

    public String getTextureId(long playerXUID) {
        if (playerXUID > 0) {
            JsonObject getJson = new ServerRequest().webRequest("https://api.geysermc.org/v2/skin/" + playerXUID);
            return getJson.get("texture_id").getAsString();
        } else return null;
        // if message gets returned = player wasn't found
    }

    public ItemStack getBedrockSkull(String attacker){
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getNameWithPrefix()));
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", getEncoded()));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        if (attacker != null) {
            List<String> lore = Arrays.asList("killed by "+ attacker);
            headMeta.setLore(lore);
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public ItemStack getJavaSkull(String attacker){
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getPlayerName()));
        GameProfile profile = new GameProfile(uuid, getPlayerName());
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        if (attacker != null) {
            List<String> lore = Arrays.asList("killed by "+ attacker);
            headMeta.setLore(lore);
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public String getTextureID() {
        return this.textureID;
    }

    public String getNameWithPrefix() {
        return FloodgateUser.FloodgatePrefix() + this.playerName;
    }

    public String getEncoded() {
        return this.encoded;
    }

    public String getPlayerName() {
        return this.playerName;
    }
}