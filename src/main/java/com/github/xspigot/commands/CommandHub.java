package com.github.xspigot.commands;

import com.github.xspigot.XPlay;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandHub implements CommandExecutor {
    FileConfiguration config = XPlay.plugin.getConfig();
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("setlobby")) {
            if (sender.hasPermission("xplay.lobby")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    int PosX = (int) player.getLocation().getX();
                    int PosY = (int) player.getLocation().getY();
                    int PosZ = (int) player.getLocation().getZ();
                    player.getServer().getWorld("world").setSpawnLocation(PosX, PosY, PosZ);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("hub.lobby")));
                    if (args.length > 0) {
                        if (args.length == 3 ) {
                            if (IntCheck(args[0])) {
                                //Set Lobby At Specified Location
                                int InpX = (int) Integer.parseInt(args[0]);
                                int InpY = (int) Integer.parseInt(args[1]);
                                int InpZ = (int) Integer.parseInt(args[2]);
                                player.getServer().getWorld("world").setSpawnLocation(InpX, InpY, InpZ);
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("hub.lobby")));
                                return true;
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("?")) {
                        //Gives Usage
                        sender.sendMessage(ChatColor.DARK_PURPLE + "Usage: " + ChatColor.AQUA + "/setlobby " + ChatColor.DARK_RED + "{position}");
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("errors.consolerestrict")));
                }
            } else {
            //Give Player No Permission Error
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("errors.permission")));
            return true;
        }
        }
        if (label.equalsIgnoreCase("lobby")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ((Player) sender).teleport(Bukkit.getWorld("world").getSpawnLocation());
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("errors.consolerestrict")));
            }
        }
        return false;
    }
    public boolean IntCheck(String str) {
        try {
            Integer.parseInt(str);
        } catch (Throwable e) {
            return false;
        }
        return true;
    }
}
