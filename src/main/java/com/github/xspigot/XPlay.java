package com.github.xspigot;

import com.github.xspigot.commands.CommandAdmin;
import com.github.xspigot.commands.CommandHub;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class XPlay extends JavaPlugin {
    public static XPlay plugin;
    public Economy eco;

    public boolean developer = false;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        plugin = this;

        setUpCommands();

        getLogger().log(Level.INFO, "XPLAY");
        getLogger().log(Level.INFO, "From: " + getDescription().getAuthors());
        getLogger().log(Level.INFO, "Running: " + getDescription().getVersion());
        getLogger().log(Level.INFO, "SpigotMC Release");
        getLogger().log(Level.INFO, "Dependencies: " + getDescription().getDepend());
        getLogger().log(Level.INFO, "API Version: " + getDescription().getAPIVersion());

        developer = getConfig().getBoolean("tools.dev");
        if (developer) {
            getLogger().log(Level.WARNING, "Running Snapshot [DEV RELEASE]");
            getLogger().log(Level.WARNING, "Developer Releases Are Not Stable - Use At Your Own Risk");
        }

        if (!setupEconomy()) {
            getLogger().log(Level.SEVERE, "No Economy Plugin Found - Disabling Plugin");
            getLogger().log(Level.INFO, "If This Error Persists, Check If Vault Is Correctly Installed");
            getServer().getPluginManager().disablePlugin(this);
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

    private void setUpCommands() {
        CommandAdmin adminCommand = new CommandAdmin();
        setUpCommandWithExecutor("xplay", adminCommand, adminCommand);

        setUpCommand("setlobby", new CommandHub());
        setUpCommand("lobby", new CommandHub());
    }

    private void setUpCommandWithExecutor(String command, CommandExecutor executor, TabCompleter completer) {
        PluginCommand pluginCommand = this.getCommand(command);

        if (pluginCommand == null)
            throw new NullPointerException("Command not found: " + command + ".");
        pluginCommand.setExecutor(executor);
        pluginCommand.setTabCompleter(completer);
    }

    private void setUpCommand(String command, CommandExecutor executor) {
        PluginCommand pluginCommand = this.getCommand(command);

        if (pluginCommand == null)
            throw new NullPointerException("Command not found: " + command + ".");
        pluginCommand.setExecutor(executor);
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economy != null) {
            eco = economy.getProvider();
        }
        return (eco != null);
    }
}
