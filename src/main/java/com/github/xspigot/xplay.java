package com.github.xspigot;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;


public final class xplay extends JavaPlugin {

    public Economy eco;
    private static Economy econ = null;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        getLogger().log(Level.INFO, "XPLAY");
        getLogger().log(Level.INFO, "From XSpigot");
        getLogger().log(Level.INFO, "Running: " + getDescription().getVersion());
        getLogger().log(Level.INFO, "SpigotMC Release");
        getLogger().log(Level.INFO, "Dependencies - Vault");
        getLogger().log(Level.INFO, "Soft Dependencies (Recommended) - None");
        getLogger().log(Level.WARNING, "Running Snapshot [DEV RELEASE]");
        getLogger().log(Level.WARNING, "Developer Releases Are Not Stable - Use At Your Own Risk");
        if (!setupEconomy()) {
            getLogger().log(Level.INFO, "No Economy Plugin Found, Enabling Built In Economy");
            getLogger().log(Level.INFO, "If This Error Persists, Please Check If You Have Vault Installed");
            return;
        }
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "XPLAY");
        getLogger().log(Level.INFO, "From XSpigot");
        getLogger().log(Level.INFO, "Running: " + getDescription().getVersion());
        getLogger().log(Level.INFO, "Disabling Plugin");
        getLogger().log(Level.INFO, "Error? (Coming Soon) bit.ly/xplayerror");
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("xplay")) {
            if (!sender.hasPermission("xplay.main")) {
                //No Permission
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("messages.noperms")));
            }
            if (args.length == 0) {
                //Plugin Information
                for (String i : this.getConfig().getStringList("messages.info")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i));
                }
            }
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    //Reload Configuration
                    this.reloadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("messages.reload")));
                }
            }
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("help")) {
                    //Help Page
                    for (String i : this.getConfig().getStringList("messages.help")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i));
                    }
                }
            }
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("vault")) {
                    //Help Page
                    for (String i : this.getConfig().getStringList("messages.vault")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i));
                    }
                }
            }
        }
        if (label.equalsIgnoreCase("setlobby")) {
            if (!sender.hasPermission("xplay.lobby")) {
                //No Permission
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("messages.noperms")));
            }
            if(sender instanceof Player) {
                if (args.length == 0){
                    //Set The Lobby At Player's Location
                    Player player = (Player) sender;
                    int LocXS = (int) player.getLocation().getX();
                    int LocYS = (int) player.getLocation().getY();
                    int LocZS = (int) player.getLocation().getZ();
                    player.getServer().getWorld("world").setSpawnLocation(LocXS, LocYS, LocZS);
                    player.getServer().getWorld("world").setGameRule(GameRule.SPAWN_RADIUS, 1);
                    player.getServer().getWorld("world").setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("messages.lobby-set")));
                    return true;
                }
                if (args.length > 0) {
                    if (args.length == 3 ) {
                        if (IntCheck(args[0])) {
                            //Set Lobby At Specified Location
                            Player player = (Player) sender;
                            int LocX = (int) Integer.parseInt(args[0]);
                            int LocY = (int) Integer.parseInt(args[1]);
                            int LocZ = (int) Integer.parseInt(args[2]);
                            player.getServer().getWorld("world").setSpawnLocation(LocX,LocY,LocZ);
                            player.getServer().getWorld("world").setGameRule(GameRule.SPAWN_RADIUS, 1);
                            player.getServer().getWorld("world").setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("messages.lobby-set")));
                            return true;
                        }
                    }
                }
            }
            else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("messages.console-restricted")));
            }
            }
        return false;
    }
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economy != null)
            eco = economy.getProvider();
        return (eco != null);
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
