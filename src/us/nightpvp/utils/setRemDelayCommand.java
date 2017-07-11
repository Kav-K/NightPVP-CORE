package us.nightpvp.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setRemDelayCommand implements CommandExecutor {
	NightUtils plugin;
	public setRemDelayCommand(NightUtils passedPlugin) {
		this.plugin = passedPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		Player player = (Player) sender;
		if (player.hasPermission("frozenheart.setremdelay")) {
			if (StringUtils.isNumeric(args[0])) {
				long delay = Integer.parseInt(args[0]);
				
				
				plugin.remdelay = delay;
				

				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &5&lSolar&7&lPvP", "", "&7The delay for block rollbacks in the Reminiscence world has been set to &5"+delay+" seconds",
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
