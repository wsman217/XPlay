package com.github.xspigot.commands;

import com.github.xspigot.Utils;
import com.github.xspigot.XPlay;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandAdmin implements CommandExecutor, TabCompleter{

    private final List<String> arguments = Arrays.asList("reload", "help", "dev", "vault", "gui");
    private final XPlay plugin = XPlay.plugin;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("xplay.main")) {
            //Give Player No Permission Error
            sender.sendMessage(Utils.getMessageFromConfig("errors.permission"));
            return true;
        }

        if (args.length == 0) {
            //Plugin Information
            //Tells Author, Supported Versions, API Version, Plugin Version, And Vault Version
            sender.sendMessage(ChatColor.DARK_RED + "XPLAY");
            sender.sendMessage(ChatColor.DARK_RED + "AUTHOR " + ChatColor.WHITE + plugin.getDescription().getAuthors());
            sender.sendMessage(ChatColor.DARK_RED + "SUPPORTED VERSIONS " + ChatColor.WHITE + "1.8.x And Above");
            sender.sendMessage(ChatColor.DARK_RED + "API " + ChatColor.WHITE + plugin.getDescription().getAPIVersion());
            sender.sendMessage(ChatColor.DARK_RED + "PLUGIN VERSION " + ChatColor.WHITE + plugin.getDescription().getVersion());
            return true;
        }

        //Check If Arguments Are Listed
        if (args[0].equalsIgnoreCase("reload")) {
            //Reloads Config
            plugin.reloadConfig();
            sender.sendMessage(Utils.getMessageFromConfig("configs.reload"));
        }

        if (args[0].equalsIgnoreCase("dev")) {
            //Enabled Dev Mode
            sender.sendMessage(ChatColor.DARK_RED + "Dev Mode Enabled");
            plugin.getConfig().set("tools.dev", true);
            plugin.reloadConfig();
            plugin.developer = true;
        }

        if (args[0].equalsIgnoreCase("vault")) {
            //Gives Vault Information
            /*TODO I'm going to let you figure out what to do with this but this is a very bad practice.
             * Spigot already has something built in for checking plugin versions and seeing the plugin descriptions
             * why would you make the same thing? Also you are giving a static 3.7 what happens if they are running
             * a newer version before you update your plugin?
             */
            sender.sendMessage(ChatColor.DARK_RED + "VAULT API " + ChatColor.WHITE + "3.7");
            sender.sendMessage(ChatColor.DARK_RED + "VAULT VERSION " + ChatColor.WHITE + Bukkit.getServer().getPluginManager().getPlugin("Vault").getDescription().getVersion());
        }

        if (args[0].equalsIgnoreCase("gui")) {
            //Opens GUI If Executor Is Player
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.getMessageFromConfig("errors.consolerestrict"));
                return true;
            }
            Player player = (Player) sender;
            //Open GUI (WIP)
            player.sendMessage("This Feature Is Not Yet Ready");

        }

        if (args[0].equalsIgnoreCase("help")) {
            sendHelpMessage(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("?")) {
            //Gives Usage
            sender.sendMessage(ChatColor.DARK_PURPLE + "Usage: " + ChatColor.AQUA + "/xplay " + ChatColor.DARK_RED + "{help, dev, vault, reload, or gui}");
        }
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_RED + "XDEV " + ChatColor.WHITE + "Developer Tools (/xdev, /xplay dev)");
        sender.sendMessage(ChatColor.DARK_RED + "XGUI " + ChatColor.WHITE + "GUI Menu (/xgui, /xplay gui)");
        sender.sendMessage(ChatColor.DARK_RED + "XVAULT " + ChatColor.WHITE + "Vault Information (/xvault, xplay vault)");
        sender.sendMessage(ChatColor.DARK_RED + "XRELOAD " + ChatColor.WHITE + "Reload Configurations (/xreload, xplay reload)");
        sender.sendMessage(ChatColor.DARK_RED + "XHELP " + ChatColor.WHITE + "Help Page (/xhelp, /xplay help)");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> result = new ArrayList<>();
        if (args.length == 1) {
            arguments.forEach((argument) -> {
                if (argument.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(argument);
            });
            Collections.sort(result);
            return result;
        }
        return null;
    }
}
