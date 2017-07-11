package us.nightpvp.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class bypassCommand implements CommandExecutor {
NightUtils plugin;
public bypassCommand(NightUtils passedPlugin) {
	this.plugin = passedPlugin;
}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		Player player = (Player) sender;
		if (player.hasPermission("frozenheart.bypass")) {
			if (NightUtils.bypass.containsKey(player)) {
				NightUtils.bypass.remove(player, player);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &7You have been taken out of bypass mode!"));
			} else {
				NightUtils.bypass.put(player, player);
				
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &7You have been put into bypass mode"));
			}
			
			
			
			
		}
		
		
		
		
		return true;
	}

}
