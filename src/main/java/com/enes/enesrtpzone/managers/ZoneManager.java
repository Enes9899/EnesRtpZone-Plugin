package com.enes.enesrtpzone.managers;

import com.enes.enesrtpzone.EnesRTPZone;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class ZoneManager {

    private final EnesRTPZone plugin;
    private final Map<String, BoundingBox> zones = new HashMap<>();

    public ZoneManager(EnesRTPZone plugin) {
        this.plugin = plugin;
        loadZones();
    }

    public void reloadZones() {
        zones.clear();
        loadZones();
    }

    private void loadZones() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("zones");
        if (section == null) return;

        for (String key : section.getKeys(false)) {
            String worldName = section.getString(key + ".world");
            if (worldName == null) continue;
            World world = plugin.getServer().getWorld(worldName);
            if (world == null) continue;
            
            zones.put(key, new BoundingBox(world, 
                section.getDouble(key + ".minX"), section.getDouble(key + ".minY"), section.getDouble(key + ".minZ"),
                section.getDouble(key + ".maxX"), section.getDouble(key + ".maxY"), section.getDouble(key + ".maxZ")));
        }
    }

    public void createZone(String name, Location pos1, Location pos2) {
        World world = pos1.getWorld();
        double minX = Math.min(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        double maxX = Math.max(pos1.getX(), pos2.getX()) + 1.0;
        double maxY = Math.max(pos1.getY(), pos2.getY()) + 1.0;
        double maxZ = Math.max(pos1.getZ(), pos2.getZ()) + 1.0;

        String path = "zones." + name + ".";
        plugin.getConfig().set(path + "world", world.getName());
        plugin.getConfig().set(path + "minX", minX); plugin.getConfig().set(path + "minY", minY); plugin.getConfig().set(path + "minZ", minZ);
        plugin.getConfig().set(path + "maxX", maxX); plugin.getConfig().set(path + "maxY", maxY); plugin.getConfig().set(path + "maxZ", maxZ);
        plugin.saveConfig();

        zones.put(name, new BoundingBox(world, minX, minY, minZ, maxX, maxY, maxZ));
    }

    public boolean isInAnyZone(Location loc) {
        for (BoundingBox box : zones.values()) {
            if (box.contains(loc)) return true;
        }
        return false;
    }

    public boolean deleteZone(String name) {
        if (!zones.containsKey(name)) return false;
        zones.remove(name);
        plugin.getConfig().set("zones." + name, null);
        plugin.saveConfig();
        return true;
    }

    public boolean zoneExists(String name) {
        return zones.containsKey(name);
    }

    public static class BoundingBox {
        private final World world;
        private final double minX, minY, minZ, maxX, maxY, maxZ;

        public BoundingBox(World world, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
            this.world = world;
            this.minX = minX; this.minY = minY; this.minZ = minZ;
            this.maxX = maxX; this.maxY = maxY; this.maxZ = maxZ;
        }

        public boolean contains(Location loc) {
            if (!loc.getWorld().getName().equals(world.getName())) return false;
            return loc.getX() >= minX && loc.getX() <= maxX && loc.getY() >= minY && loc.getY() <= maxY && loc.getZ() >= minZ && loc.getZ() <= maxZ;
        }
    }
}
