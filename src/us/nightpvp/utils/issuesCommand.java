package us.nightpvp.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class issuesCommand implements CommandExecutor {
	NightUtils plugin;
	public issuesCommand(NightUtils passedPlugin) {
		this.plugin = passedPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		Player player = (Player) sender;
		try {
			plugin.isSwitching.remove(player.getName());
			Listeners.firstjoin.remove(player.getName());
			Listeners.normaldialog.remove(player.getName());
			Listeners.moonmasterdialog.remove(player.getName());
			Listeners.masterdialog.remove(player.getName());
			Listeners.hellvendorlist.remove(player.getName());
			Listeners.remasterdialog.remove(player.getName());
			Listeners.repairerlist.remove(player.getName());
			Listeners.nointeract.remove(player);
			Listeners.toteleport.remove(player.getName());
		} catch (Exception e) {
			try {
				Listeners.nointeract.remove(player);
				plugin.isSwitching.remove(player.getName());
				Listeners.firstjoin.remove(player.getName());
			} catch (Exception e2) {
				
			}
		}
		
		
		
		
		player.kickPlayer("Please wait 10 seconds then relog and your issues shall be fixed");
		return true;
	}

}
