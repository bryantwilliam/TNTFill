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
    
    private List<Block> findDispensers(int radius) {
        
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
            
            int radius;
            try {
                radius = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException exc) {
                player.sendMessage(ChatColor.RED + "Error! " + ChatColour.DARK_RED + args[0] + ChatColor.RED + " is not an number!");
                return true;
            }
            
            if (radius > 10) {
                radius = 10;
                player.sendMessage(ChatColor.PURPLE + "Maximum radius is 10. Using 10 as your radius....")
            }
            findDispensers(radius);
            return true;
        }
        return false;
    }
}
