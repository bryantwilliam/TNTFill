package com.gmail.gogobebe2.tntfill;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class TNTFill extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Starting up TNTFill. If you need me to update this plugin, email at gogobebe2@gmail.com");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling TNTFill. If you need me to update this plugin, email at gogobebe2@gmail.com");
    }

    private List<Dispenser> findDispensers(Location location, int radius) {
        List<Dispenser> dispensers = new ArrayList<>();
        for (int x = (radius * -1); x <= radius; x++) {
            for (int y = (radius * -1); y <= radius; y++) {
                for (int z = (radius * -1); z <= radius; z++) {
                    Block block = location.getWorld().getBlockAt(location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z);
                    if (block.getState() instanceof Dispenser) dispensers.add((Dispenser) block.getState());
                }
            }
        }

        return dispensers;
    }

    private void fillWithTNT(List<Dispenser> dispensers, List<ItemStack> tnt) {
        Dispenser dispenser = dispensers.get(0);
        int count = dispensers.size() - 1;
        for (int TNTindex = 0; TNTindex < tnt.size(); TNTindex++) {
            dispenser.getInventory().addItem(tnt.get(TNTindex));
            if (TNTindex == count) {
                count *= 2;
                dispenser = dispensers.get(0);
            }
            else {
                dispenser = dispensers.get(dispensers.indexOf(dispenser) + 1);
            }
        }
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
            } catch (NumberFormatException exc) {
                player.sendMessage(ChatColor.RED + "Error! " + ChatColor.DARK_RED + args[0] + ChatColor.RED + " is not an number!");
                return true;
            }

            Inventory inventory = player.getInventory();
            if (!inventory.contains(Material.TNT)) {
                player.sendMessage(ChatColor.DARK_PURPLE + "No TNT in your inventory!");
                return true;
            }

            if (radius > 10) {
                radius = 10;
                player.sendMessage(ChatColor.GOLD + "Maximum radius is 10. Using 10 as your radius....");
            }
            List<Dispenser> dispensers = findDispensers(player.getLocation(), radius);
            if (dispensers.isEmpty()) {
                player.sendMessage(ChatColor.DARK_PURPLE + "No dispensers in a " + radius + " block radius of you.");
                return true;
            }
            List<ItemStack> tnt = new ArrayList<>();
            for (int slot : inventory.all(Material.TNT).keySet()) {
                ItemStack tntStack = inventory.getItem(slot);
                for (int i = 0; i < tntStack.getAmount(); i++) {
                    tnt.add(new ItemStack(Material.TNT, 1));
                }
                tntStack.setAmount(0);
                player.updateInventory();
            }
            fillWithTNT(dispensers, tnt);
            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Filled " + dispensers.size() + " dispensers with " + tnt.size() + " tnt");
            return true;
        }
        return false;
    }
}
