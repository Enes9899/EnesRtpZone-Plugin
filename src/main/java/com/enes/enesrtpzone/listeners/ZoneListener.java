package com.enes.enesrtpzone.listeners;

import com.enes.enesrtpzone.EnesRTPZone;
import com.enes.enesrtpzone.utils.ColorUtils;
import com.enes.enesrtpzone.utils.RTPUtils;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ZoneListener implements Listener {

    private final EnesRTPZone plugin;
    public static final Map<UUID, Location> pos1 = new HashMap<>();
    public static final Map<UUID, Location> pos2 = new HashMap<>();
    private final Map<UUID, ScheduledTask> rtpTasks = new HashMap<>();

    public ZoneListener(EnesRTPZone plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null || event.getItem().getType() != Material.WOODEN_AXE) return;
        if (!event.getItem().hasItemMeta()) return;
        String displayName = event.getItem().getItemMeta().getDisplayName();
        if (displayName == null || !displayName.contains("ᴡᴀɴᴅ")) return;
        if (!player.hasPermission("rtpzone.admin")) return;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK && event.getClickedBlock() != null) {
            event.setCancelled(true);
            pos1.put(player.getUniqueId(), event.getClickedBlock().getLocation());
            player.sendMessage(ColorUtils.format(plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.pos1-set")));
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
            event.setCancelled(true);
            pos2.put(player.getUniqueId(), event.getClickedBlock().getLocation());
            player.sendMessage(ColorUtils.format(plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.pos2-set")));
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getTo() == null) return;
        if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        boolean isIn = plugin.getZoneManager().isInAnyZone(event.getTo());

        if (isIn && !rtpTasks.containsKey(uuid)) {
            
            player.sendMessage(ColorUtils.format(plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.entered-zone")));
            startGlobalCountdownDisplay(player);
        } else if (!isIn && rtpTasks.containsKey(uuid)) {
            
            stopSyncTask(player);
        }
    }

    private void startGlobalCountdownDisplay(Player player) {
        UUID uuid = player.getUniqueId();

        ScheduledTask task = player.getScheduler().runAtFixedRate(plugin, (t) -> {
            int timeLeft = plugin.getGlobalCountdown();

            if (timeLeft <= 0) {
                
                player.sendTitle("", "", 0, 1, 0);
                
                if (plugin.getZoneManager().isInAnyZone(player.getLocation())) {
                    RTPUtils.teleportSafely(plugin, player);
                }
                stopSyncTask(player);
                return;
            }

            
            String title = ColorUtils.format(plugin.getConfig().getString("messages.countdown-title").replace("%time%", String.valueOf(timeLeft)));
            String sub = ColorUtils.format(plugin.getConfig().getString("messages.countdown-subtitle"));
            player.sendTitle(title, sub, 0, 25, 0);

        }, null, 1L, 20L);

        rtpTasks.put(uuid, task);
    }

    private void stopSyncTask(Player player) {
        UUID uuid = player.getUniqueId();
        ScheduledTask task = rtpTasks.remove(uuid);
        if (task != null) task.cancel();
        player.sendTitle("", "", 0, 5, 0);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        stopSyncTask(event.getPlayer());
    }
}
