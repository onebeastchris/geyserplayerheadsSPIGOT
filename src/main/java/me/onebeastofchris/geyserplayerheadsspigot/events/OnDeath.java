package me.onebeastofchris.geyserplayerheadsspigot.events;

import me.onebeastofchris.geyserplayerheadsspigot.utils.FloodgateUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Bukkit.getLogger;

public class OnDeath implements Listener {
    @EventHandler
    public void dropHead(PlayerDeathEvent playerDeathEvent){
        getLogger().info(playerDeathEvent.getEntity().getDisplayName() + "dieeeeed");
        Player player = playerDeathEvent.getEntity();
        String killerName = playerDeathEvent.getEntity().getKiller().getDisplayName();
        getLogger().info(playerDeathEvent.getEntity().getDisplayName() +"died at"+ playerDeathEvent.getEntity().getLocation().toString());
        if (player.getDisplayName().startsWith(".") || FloodgateUser.isFloodgatePlayer(player.getUniqueId())) {
            ItemStack a = PlayerJoinEvent.getTextureID().get(player.getUniqueId()).getBedrockSkull(playerDeathEvent.getEntity().getKiller().getPlayer().getDisplayName());
            playerDeathEvent.getDrops().add(a);
        } else {
            ItemStack b = PlayerJoinEvent.getTextureID().get(player.getUniqueId()).getJavaSkull(playerDeathEvent.getEntity().getKiller().getPlayer().getDisplayName());
            playerDeathEvent.getDrops().add(b);
        }
    }
}
