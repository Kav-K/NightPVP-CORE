package us.nightpvp.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class weatherCommand implements CommandExecutor {
NightUtils plugin;
public weatherCommand(NightUtils passedPlugin) {
	this.plugin = passedPlugin;
}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		sender.sendMessage(ChatColor.RED+"This command has been temporarily disabled!");
		return true;
	}

}
