package us.nightpvp.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class rewarderCommand implements CommandExecutor {
	NightUtils plugin;
	public rewarderCommand(NightUtils passedPlugin) {
		this.plugin = passedPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
Player player = (Player) sender;
player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &aOpening the rewarder menu!"));
player.openInventory(plugin.getRewarderInventory());
		
		
		return true;
	}

}
