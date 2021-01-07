package com.github.xspigot;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Utils {

    private static final FileConfiguration config = XPlay.plugin.getConfig();

    public static String getMessageFromConfig(String location) {
        String value = config.getString(location);
        if (value == null)
            return ChatColor.RED + "Message in location \"" + location + "\" has not been found.";
        return ChatColor.translateAlternateColorCodes('&', value);
    }
}
