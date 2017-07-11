package us.nightpvp.utils;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class wildCommand implements CommandExecutor {
	NightUtils plugin;
	public wildCommand(NightUtils passedPlugin) {
		this.plugin = passedPlugin;
	}
	
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		Player player = (Player) sender;
		calculate(player.getLocation().getWorld().getName(), player);
		
		
		
		
		
		
		
		
		
		return true;
	}
   private void calculate(String world, Player player) {
	   if (plugin.getConfig().getString(player.getName()+"randomtp") == null) {
		   
	   
	   
	   
	   
	   if (world.equals("Reminiscence")) {
		   player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cYou may not randomly TP in this world!"));
	   }
	   if (world.equals("Map")) {
		  
	   Random random = new Random();
		
		
		
		int x = random.nextInt(4000);
		int y = 35;
		int z = -random.nextInt(5000);
		int fifty = random.nextInt(2) +1;
		if (fifty == 1) {
			z = +z;
		} else {
			x = -x;
		}
		
		
		
		Location loc = new Location(Bukkit.getWorld("Map"),x,y,z);
		if (Bukkit.getWorld("Map").getBlockAt(x,y-22,z).getType().equals(Material.STATIONARY_WATER)) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cFAILED, Unsafe chunk! Retrying.."));
			calculate("Map",player);
		} else
		if (Bukkit.getWorld("Map").getBlockAt(x,y-22,z).getType().equals(Material.WATER)) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cFAILED, Unsafe chunk! Retrying.."));
			calculate("Map",player);
		} else
		if (Bukkit.getWorld("Map").getBlockAt(x,y-22,z).getType() == Material.WATER) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cFAILED, Unsafe chunk! Retrying.."));
			calculate("Map",player);
		} else
		if (Bukkit.getWorld("Map").getBlockAt(x,y-22,z).getType() == Material.STATIONARY_WATER) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cFAILED, Unsafe chunk! Retrying.."));
			calculate("Map",player);
		} else
		if (Bukkit.getWorld("Map").getBlockAt(loc).getType().isSolid()) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cFAILED, Unsafe chunk! Retrying.."));
			calculate("Map",player);
			
		} else {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cLoading chunk..."));
			Bukkit.getWorld("Map").loadChunk(x,z);
			player.teleport(loc);
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &aYou have successfully been teleported to &5"+ x+", "+y+", " + z ));
			plugin.getConfig().set(player.getName()+"randomtp", "yes");
            plugin.saveConfig();
            new BukkitRunnable() {
            	@Override
            	public void run() {
            		plugin.getConfig().set(player.getName()+"randomtp", null);
            		plugin.saveConfig();
            	}
            }.runTaskLater(plugin, 20*30);
		}
		
		
		
		
	   } else if (world.equals("Normal")) {
			  
		   Random random = new Random();
			
			
			
			int x = random.nextInt(5500);
			int y = 74;
			int z = random.nextInt(6000);
			int fifty = random.nextInt(2) +1;
			if (fifty == 1) {
				z = +z;
				x = -x;
			} else {
				z= -x;
				z = +z;
			}
			
			
			
			Location loc = new Location(Bukkit.getWorld("Normal"),x,y,z);
			if (Bukkit.getWorld("Normal").getBlockAt(x,y-22,z).getType().equals(Material.STATIONARY_WATER)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cFAILED, Unsafe chunk! Retrying.."));
				calculate("Normal",player);
			} else
			if (Bukkit.getWorld("Normal").getBlockAt(x,y-22,z).getType().equals(Material.WATER)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cFAILED, Unsafe chunk! Retrying.."));
				calculate("Normal",player);
			} else
			if (Bukkit.getWorld("Map").getBlockAt(x,y-22,z).getType() == Material.WATER) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cFAILED, Unsafe chunk! Retrying.."));
				calculate("Normal",player);
			} else
			if (Bukkit.getWorld("Map").getBlockAt(x,y-22,z).getType() == Material.STATIONARY_WATER) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cFAILED, Unsafe chunk! Retrying.."));
				calculate("Normal",player);
			} else
			if (Bukkit.getWorld("Normal").getBlockAt(loc).getType().isSolid()) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cFAILED, Unsafe chunk! Retrying.."));
				calculate("Normal",player);
				
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cLoading chunk..."));
				Bukkit.getWorld("Normal").loadChunk(x,z);
				player.teleport(loc);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &aYou have successfully been teleported to &5"+ x+", "+y+", " + z ));
	            plugin.getConfig().set(player.getName()+"randomtp", "yes");
	            plugin.saveConfig();
	            new BukkitRunnable() {
	            	@Override
	            	public void run() {
	            		plugin.getConfig().set(player.getName()+"randomtp", null);
	            		plugin.saveConfig();
	            	}
	            }.runTaskLater(plugin, 20*30);
			}
   }
	   } else {
		   player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cYou are still on delay from your last random teleport! You may not use this again for 30 seconds!"));
	   }
   }
}
