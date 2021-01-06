package com.github.xspigot;

import com.github.xspigot.admin.cmd;
import com.github.xspigot.tools.tabcomplete;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class xplay extends JavaPlugin {
    public static xplay plugin;
    public Economy eco;
    @Override
    public void onEnable() {
        //Variables
        boolean Developer = true;
        //Initialize Config
        this.saveDefaultConfig();
        //Declare Config
        plugin = this;
        //Setup Commands
        this.getCommand("xplay").setExecutor(new cmd());
        this.getCommand("setlobby").setExecutor(new com.github.xspigot.hub.cmd());
        this.getCommand("lobby").setExecutor(new com.github.xspigot.hub.cmd());
        //Setup Tab-Completer
        this.getCommand("xplay").setTabCompleter(new tabcomplete());
        //Plugin Information
        //Tells Title, Author, Version, Dependencies, Release Version, Api Version
        getLogger().log(Level.INFO, "XPLAY");
        getLogger().log(Level.INFO, "From: " + getDescription().getAuthors());
        getLogger().log(Level.INFO, "Running: " + getDescription().getVersion());
        getLogger().log(Level.INFO, "SpigotMC Release");
        getLogger().log(Level.INFO, "Dependencies: " + getDescription().getDepend());
        getLogger().log(Level.INFO, "API Version: " + getDescription().getAPIVersion());
        //Print Warnings If Developer Release
        if (Developer) {
            getLogger().log(Level.WARNING, "Running Snapshot [DEV RELEASE]");
            getLogger().log(Level.WARNING, "Developer Releases Are Not Stable - Use At Your Own Risk");
        }
        if (!setupEconomy()) {
            getLogger().log(Level.SEVERE, "No Economy Plugin Found - Disabling Plugin");
            getLogger().log(Level.INFO, "If This Error Persists, Check If Vault Is Correctly Installed");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }
    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "XPLAY");
        getLogger().log(Level.INFO, "Disabling Plugin");
        getLogger().log(Level.INFO, "Running: " + getDescription().getVersion());
        getLogger().log(Level.INFO, "SpigotMC Release");
        getLogger().log(Level.INFO, "Error? (Coming Soon) bit.ly/xplayerror");
    }
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economy != null) {
            eco = economy.getProvider();
        }
        return (eco != null);
    }
}
