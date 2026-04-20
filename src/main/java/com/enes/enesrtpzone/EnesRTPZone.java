package com.enes.enesrtpzone;

import com.enes.enesrtpzone.commands.ZoneCommand;
import com.enes.enesrtpzone.listeners.ZoneListener;
import com.enes.enesrtpzone.managers.ZoneManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EnesRTPZone extends JavaPlugin {

    private ZoneManager zoneManager;
    private int globalCountdown;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        printBanner();

        this.zoneManager = new ZoneManager(this);
        this.globalCountdown = getConfig().getInt("rtp.countdown", 30);

        if (getCommand("rtpzone") != null) {
            getCommand("rtpzone").setExecutor(new ZoneCommand(this));
        }
        getServer().getPluginManager().registerEvents(new ZoneListener(this), this);

        // Global sayac - herkes icin ayni
        Bukkit.getGlobalRegionScheduler().runAtFixedRate(this, task -> {
            if (globalCountdown <= 1) {
                globalCountdown = getConfig().getInt("rtp.countdown", 30);
            } else {
                globalCountdown--;
            }
        }, 20L, 20L);
    }

    private void printBanner() {
        String RESET = "\u001B[0m";
        String CYAN = "\u001B[36m";
        String GREEN = "\u001B[32m";
        String WHITE = "\u001B[97m";

        getLogger().info(CYAN  + " ██████╗  ████████╗ ██████╗  ███████╗  ██████╗  ███╗   ██╗ ███████╗" + RESET);
        getLogger().info(WHITE + " ██╔══██╗ ╚══██╔══╝ ██╔══██╗ ╚══███╔╝ ██╔═══██╗ ████╗  ██║ ██╔════╝" + RESET);
        getLogger().info(CYAN  + " ██████╔╝    ██║    ██████╔╝   ███╔╝  ██║   ██║ ██╔██╗ ██║ █████╗  " + RESET);
        getLogger().info(WHITE + " ██╔══██╗    ██║    ██╔═══╝   ███╔╝   ██║   ██║ ██║╚██╗██║ ██╔══╝  " + RESET);
        getLogger().info(CYAN  + " ██║  ██║    ██║    ██║      ███████╗ ╚██████╔╝ ██║ ╚████║ ███████╗" + RESET);
        getLogger().info(WHITE + " ╚═╝  ╚═╝    ╚═╝    ╚═╝      ╚══════╝  ╚═════╝  ╚═╝  ╚═══╝ ╚══════╝" + RESET);
        
        getLogger().info("");
        getLogger().info(CYAN + "╔══════════════════════════════════════════════════════════════╗" + RESET);
        getLogger().info(CYAN + "║" + WHITE + "                    RTPZONE AKTIF!                            " + CYAN + "║" + RESET);
        getLogger().info(CYAN + "╠══════════════════════════════════════════════════════════════╣" + RESET);
        getLogger().info(CYAN + "║ " + WHITE + "Durum: Aktif                                                 " + CYAN + "║" + RESET);
        getLogger().info(CYAN + "║ " + CYAN + "Developer: Enes                                            " + CYAN + "║" + RESET);
        getLogger().info(CYAN + "╚══════════════════════════════════════════════════════════════╝" + RESET);
    }

    public ZoneManager getZoneManager() {
        return zoneManager;
    }

    public int getGlobalCountdown() {
        return globalCountdown;
    }
}
