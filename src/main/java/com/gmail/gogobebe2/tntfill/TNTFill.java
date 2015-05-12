package com.gmail.gogobebe2.tntfill;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TNTFill extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Starting up TNTFill. If you need me to update this plugin, email at gogobebe2@gmail.com");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling TNTFill. If you need me to update this plugin, email at gogobebe2@gmail.com");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("tntfill")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Error! You have to be a player to use this command!");
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "Error! Correct usage: " + ChatColor.BLUE + "/tntfill <radius>");
                return true;
            }

            

            return true;
        }
        return false;
    }
}
