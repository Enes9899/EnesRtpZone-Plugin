package com.enes.enesrtpzone.utils;

import com.enes.enesrtpzone.EnesRTPZone;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.Random;

public class RTPUtils {

    private static final Random random = new Random();

    public static void teleportSafely(EnesRTPZone plugin, Player player) {
        int max = plugin.getConfig().getInt("rtp.max-radius", 10000);
        int min = plugin.getConfig().getInt("rtp.min-radius", 1000);
        World world = player.getWorld();

        // Check if Folia's getRegionScheduler exists
        boolean isFolia = false;
        try {
            Bukkit.class.getMethod("getRegionScheduler");
            isFolia = true;
        } catch (NoSuchMethodException e) {
            isFolia = false;
        }

        if (isFolia) {
            plugin.getLogger().info("Folia detected, using async chunk loading for " + player.getName());
            teleportAsyncFolia(plugin, player, world, max, min, 0);
        } else {
            plugin.getLogger().info("Using Bukkit scheduler for " + player.getName());
            Bukkit.getScheduler().runTask(plugin, () -> {
                performTeleport(plugin, player, world, max, min);
            });
        }
    }

    private static void teleportAsyncFolia(EnesRTPZone plugin, Player player, World world, int max, int min, int attempt) {
        if (attempt > 15) {
            plugin.getLogger().warning("Failed to find safe location after 16 attempts");
            player.sendMessage(ColorUtils.format(plugin.getConfig().getString("messages.prefix") + "&cGuvenli alan bulunamadi, yeniden deneyin."));
            return;
        }

        int x = (random.nextInt(max - min) + min) * (random.nextBoolean() ? 1 : -1);
        int z = (random.nextInt(max - min) + min) * (random.nextBoolean() ? 1 : -1);

        
        scheduleTeleport(plugin, player, world, x, z, max, min, attempt);
    }

    private static void scheduleTeleport(EnesRTPZone plugin, Player player, World world, int x, int z, int max, int min, int attempt) {
        try {
            Method getRegionScheduler = Bukkit.class.getMethod("getRegionScheduler");
            Object scheduler = getRegionScheduler.invoke(null);

            Method execute = null;
            for (Method m : scheduler.getClass().getMethods()) {
                if (m.getName().equals("execute") && m.getParameterCount() == 5) {
                    execute = m;
                    break;
                }
            }

            if (execute != null) {
                execute.invoke(scheduler, plugin, world, x, z, (Runnable) () -> {
                    try {
                        
                        Chunk chunk = world.getChunkAt(x, z);
                        
                        
                        int y = world.getMaxHeight() - 1;
                        Block block = null;
                        Material type = Material.AIR;
                        
                        while (y > world.getMinHeight() + 5) {
                            block = chunk.getBlock(x & 0xF, y, z & 0xF);
                            type = block.getType();
                            if (type.isSolid() || type == Material.WATER || type == Material.LAVA) {
                                break;
                            }
                            y--;
                        }
                        
                        
                        if (type == Material.LAVA || type == Material.WATER || type == Material.CACTUS || type == Material.MAGMA_BLOCK || y <= world.getMinHeight() + 5) {
                            y += 10; 
                        }
                        
                        Location loc = new Location(world, x + 0.5, y + 1, z + 0.5);
                        
                        Method teleportAsync = Player.class.getMethod("teleportAsync", Location.class);
                        teleportAsync.invoke(player, loc);
                        player.sendMessage(ColorUtils.format(plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.teleporting")));
                        plugin.getLogger().info("SUCCESS: Teleported to " + loc);
                    } catch (Exception teleEx) {
                        plugin.getLogger().severe("Teleport failed: " + teleEx.getMessage());
                        
                        teleportAsyncFolia(plugin, player, world, max, min, attempt + 1);
                    }
                });
            } else {
                
                Location loc = new Location(world, x + 0.5, 100, z + 0.5);
                player.teleport(loc);
            }
        } catch (Exception ex) {
            plugin.getLogger().severe("Schedule teleport failed: " + ex.getMessage());
        }
    }

    private static void performTeleport(EnesRTPZone plugin, Player player, World world, int max, int min) {
        for (int attempt = 0; attempt <= 15; attempt++) {
            int x = (random.nextInt(max - min) + min) * (random.nextBoolean() ? 1 : -1);
            int z = (random.nextInt(max - min) + min) * (random.nextBoolean() ? 1 : -1);

            try {
                Chunk chunk = world.getChunkAt(x, z);

                
                int y = world.getMaxHeight() - 1;
                Block block = null;
                Material type = Material.AIR;

                while (y > world.getMinHeight() + 5) {
                    block = chunk.getBlock(x & 0xF, y, z & 0xF);
                    type = block.getType();
                    if (type.isSolid() || type == Material.WATER || type == Material.LAVA) {
                        break;
                    }
                    y--;
                }

                if (type.isSolid() && type != Material.LAVA && type != Material.WATER &&
                    type != Material.MAGMA_BLOCK && type != Material.CACTUS && y > world.getMinHeight() + 5) {

                    Location loc = new Location(world, x + 0.5, y + 1, z + 0.5);
                    player.teleport(loc);
                    player.sendMessage(ColorUtils.format(plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.teleporting")));
                    plugin.getLogger().info("Paper/Purpur teleport success to " + loc);
                    return;
                }
            } catch (Exception ex) {
                plugin.getLogger().warning("Paper attempt " + attempt + " error: " + ex.getMessage());
            }
        }

        player.sendMessage(ColorUtils.format(plugin.getConfig().getString("messages.prefix") + "&cGuvenli alan bulunamadi, yeniden deneyin."));
    }
}
