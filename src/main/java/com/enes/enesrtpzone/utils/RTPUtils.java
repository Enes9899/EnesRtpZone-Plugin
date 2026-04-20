package com.enes.enesrtpzone.utils;

import com.enes.enesrtpzone.EnesRTPZone;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Random;

public class RTPUtils {

    private static final Random random = new Random();

    public static void teleportSafely(EnesRTPZone plugin, Player player, int attempt) {
        if (attempt > 15) {
            player.sendMessage(ColorUtils.format(plugin.getConfig().getString("messages.prefix") + "&cGuvenli alan bulunamadi, yeniden deneyin."));
            return;
        }

        int max = plugin.getConfig().getInt("rtp.max-radius", 10000);
        int min = plugin.getConfig().getInt("rtp.min-radius", 1000);
        World world = player.getWorld();

        int x = (random.nextInt(max - min) + min) * (random.nextBoolean() ? 1 : -1);
        int z = (random.nextInt(max - min) + min) * (random.nextBoolean() ? 1 : -1);

        // Chunk'i async yukle ve sonra islem yap
        world.getChunkAtAsync(x >> 4, z >> 4).thenAccept(chunk -> {
            if (chunk == null) {
                teleportSafely(plugin, player, attempt + 1);
                return;
            }
            
            int y = world.getHighestBlockYAt(x, z);
            Block highestBlock = world.getBlockAt(x, y, z);
            Material type = highestBlock.getType();

            if (!type.isSolid() || type == Material.LAVA || type == Material.WATER || 
                type == Material.MAGMA_BLOCK || type == Material.CACTUS || y <= world.getMinHeight() + 5) {
                teleportSafely(plugin, player, attempt + 1);
                return;
            }

            Location safeLoc = new Location(world, x + 0.5, y + 1, z + 0.5);
            player.teleportAsync(safeLoc).thenAccept(success -> {
                if (success) {
                    player.sendMessage(ColorUtils.format(plugin.getConfig().getString("messages.prefix") + plugin.getConfig().getString("messages.teleporting")));
                }
            });
        }).exceptionally(ex -> {
            teleportSafely(plugin, player, attempt + 1);
            return null;
        });
    }
}
