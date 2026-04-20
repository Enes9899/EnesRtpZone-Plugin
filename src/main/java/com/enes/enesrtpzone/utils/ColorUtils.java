package com.enes.enesrtpzone.utils;

import org.bukkit.ChatColor;

public class ColorUtils {
    public static String format(String text) {
        if (text == null) return "";
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
