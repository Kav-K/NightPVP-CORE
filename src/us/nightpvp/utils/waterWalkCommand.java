package us.nightpvp.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class waterWalkCommand implements CommandExecutor {
NightUtils plugin;
public waterWalkCommand(NightUtils passedPlugin) {
	this.plugin = passedPlugin;
}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		Player player = (Player) sender;
		player.sendMessage(ChatColor.RED+"This feature is still being worked on!");
		return true;
	}

}
