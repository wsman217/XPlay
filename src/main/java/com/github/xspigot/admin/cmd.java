package com.github.xspigot.admin;

import com.github.xspigot.xplay;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class cmd implements CommandExecutor {
    FileConfiguration config = xplay.plugin.getConfig();
    private xplay plugin;
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("xplay")) {
            if (sender.hasPermission("xplay.main")) {
                if (args.length == 0) {
                    //Plugin Information
                    //Tells Author, Supported Versions, API Version, Plugin Version, And Vault Version
                    sender.sendMessage(ChatColor.DARK_RED + "XPLAY");
                    sender.sendMessage(ChatColor.DARK_RED + "AUTHOR " + ChatColor.WHITE + Bukkit.getServer().getPluginManager().getPlugin("XPlay").getDescription().getAuthors());
                    sender.sendMessage(ChatColor.DARK_RED + "SUPPORTED VERSIONS " + ChatColor.WHITE + "1.8.x And Above");
                    sender.sendMessage(ChatColor.DARK_RED + "API " + ChatColor.WHITE + Bukkit.getServer().getPluginManager().getPlugin("XPlay").getDescription().getAPIVersion());
                    sender.sendMessage(ChatColor.DARK_RED + "PLUGIN VERSION " + ChatColor.WHITE + Bukkit.getServer().getPluginManager().getPlugin("XPlay").getDescription().getVersion());
                    sender.sendMessage(ChatColor.DARK_RED + "VAULT " + ChatColor.WHITE + Bukkit.getServer().getPluginManager().getPlugin("Vault").getDescription().getVersion());
                    return true;
                }
                //Check If Arguments Are Listed
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        //Reloads Config
                        xplay.plugin.reloadConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("configs.reload")));
                    }
                    if (args[0].equalsIgnoreCase("dev")) {
                        //Enabled Dev Mode
                        sender.sendMessage(ChatColor.DARK_RED + "Dev Mode Enabled");
                        config.set("tools.dev", true);
                    }
                    if (args[0].equalsIgnoreCase("vault")) {
                        //Gives Vault Information
                        sender.sendMessage(ChatColor.DARK_RED + "VAULT API " + ChatColor.WHITE + "3.7");
                        sender.sendMessage(ChatColor.DARK_RED + "VAULT VERSION " + ChatColor.WHITE + Bukkit.getServer().getPluginManager().getPlugin("Vault").getDescription().getVersion());
                    }
                    if (args[0].equalsIgnoreCase("gui")) {
                        //Opens GUI If Executor Is Player
                        if (!(sender instanceof Player)) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("errors.consolerestrict")));
                            return true;
                        }
                        Player player = (Player) sender;
                        //Open GUI (WIP)
                        sender.sendMessage("This Feature Is Not Yet Ready");

                    }
                    if (args[0].equalsIgnoreCase("help")) {
                        //Gives Help Information
                        sender.sendMessage(ChatColor.DARK_RED + "XDEV " + ChatColor.WHITE + "Developer Tools (/xdev, /xplay dev)");
                        sender.sendMessage(ChatColor.DARK_RED + "XGUI " + ChatColor.WHITE + "GUI Menu (/xgui, /xplay gui)");
                        sender.sendMessage(ChatColor.DARK_RED + "XVAULT " + ChatColor.WHITE + "Vault Information (/xvault, xplay vault)");
                        sender.sendMessage(ChatColor.DARK_RED + "XRELOAD " + ChatColor.WHITE + "Reload Configurations (/xreload, xplay reload)");
                        sender.sendMessage(ChatColor.DARK_RED + "XHELP " + ChatColor.WHITE + "Help Page (/xhelp, /xplay help)");
                    }
                    if (args[0].equalsIgnoreCase("?")) {
                        //Gives Usage
                        sender.sendMessage(ChatColor.DARK_PURPLE + "Usage: " + ChatColor.AQUA + "/xplay " + ChatColor.DARK_RED + "{help, dev, vault, reload, or gui}");
                    }
                }
            } else {
                //Give Player No Permission Error
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("errors.permission")));
                return true;
            }
        }
        return false;
    }
}
