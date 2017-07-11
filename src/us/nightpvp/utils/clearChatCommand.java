package us.nightpvp.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class clearChatCommand implements CommandExecutor {
	
	NightUtils plugin;
	public clearChatCommand(NightUtils passedPlugin) {
		this.plugin = passedPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (sender.hasPermission("frozenheart.clearchat")) {
			for (int i =0;i<150;i++) {
				Bukkit.broadcastMessage("");
			}
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lSolar&7&lPvP", "", "&7The chat has been cleared by&5 "+sender.getName(),
					 "",
					"&8&l&m-------------------------------------");

			for (int i = 0; i < runes.size(); i++) {
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}
			
		}
		
		
		
		
		return true;
	}

}
