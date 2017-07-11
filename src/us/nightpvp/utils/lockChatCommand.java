package us.nightpvp.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class lockChatCommand implements CommandExecutor {
	
	NightUtils plugin;
	public lockChatCommand(NightUtils passedPlugin) {
		this.plugin = passedPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (sender.hasPermission("frozenheart.lockchat")) {
			if (!(plugin.locked)) {
			
			if (args.length == 0) {
			plugin.locked = true;
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lSolar&7&lPvP", "", "&7The chat has been locked! Only donators may type in chat for the moment being.",
					 "",
					"&8&l&m-------------------------------------");

			for (int i = 0; i < runes.size(); i++) {
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}
			
			} else {
				if (!(plugin.fullocked)) {
 				plugin.fullocked = true;
				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &5&lSolar&7&lPvP", "", "&7The chat AND commands have been locked for the moment being by &5"+sender.getName(),
						 "",
						"&8&l&m-------------------------------------");

				for (int i = 0; i < runes.size(); i++) {
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}
				} else {
					plugin.fullocked = false;
					List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
							"                      &5&lSolar&7&lPvP", "", "&7The chat AND commands have been unlocked",
							 "",
							"&8&l&m-------------------------------------");

					for (int i = 0; i < runes.size(); i++) {
						Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
					}
				}
			}
			
		} else {
			plugin.locked = false;
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lSolar&7&lPvP", "", "&7The chat has been unlocked!",
					 "",
					"&8&l&m-------------------------------------");

			for (int i = 0; i < runes.size(); i++) {
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}
		}
		}
		return true;
	}

}
