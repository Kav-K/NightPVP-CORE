package us.nightpvp.utils;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class chatdelayCommand implements CommandExecutor {
NightUtils plugin;
public chatdelayCommand(NightUtils passedPlugin) {
	this.plugin = passedPlugin;
}
	@Override
	
		public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
			if (arg0.hasPermission("frozenheart.chatdelay")) {
				if (arg3.length == 1) {
					if (StringUtils.isNumeric(arg3[0])) {
						
						plugin.delay = Integer.parseInt(arg3[0]);
						Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
						Bukkit.broadcastMessage("                         "+ChatColor.DARK_PURPLE +ChatColor.BOLD.toString() + "Chat" +"                                                  ");
						Bukkit.broadcastMessage(ChatColor.DARK_GRAY + ChatColor.BOLD.toString() + ">>" + ChatColor.GRAY
								+ " Chat delay has been set to " + ChatColor.DARK_PURPLE + plugin.delay + ChatColor.GRAY + " by " + ChatColor.DARK_PURPLE + arg0.getName());
						Bukkit.broadcastMessage("");
					
						
						Bukkit.broadcastMessage("                         "+ChatColor.DARK_PURPLE +ChatColor.BOLD.toString() + "Chat" +"                                                  ");
						Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
					} else {
						if (arg3[0].equals("off")) {
							plugin.delay = 0;
							Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
							Bukkit.broadcastMessage("                         "+ChatColor.DARK_PURPLE+ChatColor.BOLD.toString() + "Chat" +"                                                  ");
							Bukkit.broadcastMessage(ChatColor.DARK_GRAY + ChatColor.BOLD.toString() + ">>" + ChatColor.GRAY
									+ " Chat delay has been turned off by " + ChatColor.DARK_PURPLE + arg0.getName());
							Bukkit.broadcastMessage("");
						
							
							Bukkit.broadcastMessage("                         "+ChatColor.DARK_PURPLE +ChatColor.BOLD.toString() + "Chat" +"                                                  ");
							Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
						}
					}
				}
			}
		return true;
	}

}
