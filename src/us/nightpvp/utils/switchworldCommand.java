package us.nightpvp.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class switchworldCommand implements CommandExecutor {
	static NightUtils plugin;

	public switchworldCommand(NightUtils passedPlugin) {
		this.plugin = passedPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!(plugin.isSwitching.contains(player))){
			if (player.hasPermission("frozenheart.switchworld")) {

				/* TODO SWITCH WORLD MAIN HERE */
				player.openInventory(NightUtils.switcherinv);
				
			
				
				
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cNo permissions!"));
			}

		} else {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cYou are already in the midst of switching worlds!"));
		}
		}
		return true;
	}
	 public static void Animate() {
	     ItemStack color1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10);
	     ItemStack color2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
	     ItemMeta color1meta = color1.getItemMeta();
	     color1meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &7&lTransporter"));
	     color1.setItemMeta(color1meta);
	     ItemMeta color2meta = color2.getItemMeta();
	     color2meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &7&lTransporter"));
	    
	new BukkitRunnable() {
		@Override
		public void run() {
	 	NightUtils.switcherinv.setItem(0, color1);
		NightUtils.switcherinv.setItem(1, color2);
		NightUtils.switcherinv.setItem(2, color1);
		NightUtils.switcherinv.setItem(3, color2);
		NightUtils.switcherinv.setItem(4, color1);
		NightUtils.switcherinv.setItem(5, color2);
		NightUtils.switcherinv.setItem(6, color1);
		NightUtils.switcherinv.setItem(7, color2);
		NightUtils.switcherinv.setItem(8, color1);
		NightUtils.switcherinv.setItem(17, color2);
		NightUtils.switcherinv.setItem(26, color1);
		NightUtils.switcherinv.setItem(25, color2);
		NightUtils.switcherinv.setItem(24, color1);
		NightUtils.switcherinv.setItem(23, color2);
		NightUtils.switcherinv.setItem(22, color1);
		NightUtils.switcherinv.setItem(21, color2);
		NightUtils.switcherinv.setItem(20, color1);
		NightUtils.switcherinv.setItem(19, color2);
		NightUtils.switcherinv.setItem(18, color1);
		NightUtils.switcherinv.setItem(9, color2);
		new BukkitRunnable() {
			@Override
			public void run() {
				NightUtils.switcherinv.setItem(0, color2);
				NightUtils.switcherinv.setItem(1, color1);
				NightUtils.switcherinv.setItem(2, color2);
				NightUtils.switcherinv.setItem(3, color1);
				NightUtils.switcherinv.setItem(4, color2);
				NightUtils.switcherinv.setItem(5, color1);
				NightUtils.switcherinv.setItem(6, color2);
				NightUtils.switcherinv.setItem(7, color1);
				NightUtils.switcherinv.setItem(8, color2);
				NightUtils.switcherinv.setItem(17, color1);
				NightUtils.switcherinv.setItem(26, color2);
				NightUtils.switcherinv.setItem(25, color1);
				NightUtils.switcherinv.setItem(24, color2);
				NightUtils.switcherinv.setItem(23, color1);
				NightUtils.switcherinv.setItem(22, color2);
				NightUtils.switcherinv.setItem(21, color1);
				NightUtils.switcherinv.setItem(20, color2);
				NightUtils.switcherinv.setItem(19, color1);
				NightUtils.switcherinv.setItem(18, color2);
				NightUtils.switcherinv.setItem(9, color1);
			}
		}.runTaskLater(plugin, 5);
		}
	}.runTaskTimer(plugin, 9, 9);
	     
			
			
			
			
		}
	 public static void Animate2() {
	     ItemStack color1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10);
	     ItemStack color2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
	     ItemMeta color1meta = color1.getItemMeta();
	     color1meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &7&lTransporter"));
	     color1.setItemMeta(color1meta);
	     ItemMeta color2meta = color2.getItemMeta();
	     color2meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &7&lTransporter"));
	    
	new BukkitRunnable() {
		@Override
		public void run() {
	 	NightUtils.firstjoinswitcher.setItem(0, color1);
		NightUtils.firstjoinswitcher.setItem(1, color2);
		NightUtils.firstjoinswitcher.setItem(2, color1);
		NightUtils.firstjoinswitcher.setItem(3, color2);
		NightUtils.firstjoinswitcher.setItem(4, color1);
		NightUtils.firstjoinswitcher.setItem(5, color2);
		NightUtils.firstjoinswitcher.setItem(6, color1);
		NightUtils.firstjoinswitcher.setItem(7, color2);
		NightUtils.firstjoinswitcher.setItem(8, color1);
		NightUtils.firstjoinswitcher.setItem(17, color2);
		NightUtils.firstjoinswitcher.setItem(26, color1);
		NightUtils.firstjoinswitcher.setItem(25, color2);
		NightUtils.firstjoinswitcher.setItem(24, color1);
		NightUtils.firstjoinswitcher.setItem(23, color2);
		NightUtils.firstjoinswitcher.setItem(22, color1);
		NightUtils.firstjoinswitcher.setItem(21, color2);
		NightUtils.firstjoinswitcher.setItem(20, color1);
		NightUtils.firstjoinswitcher.setItem(19, color2);
		NightUtils.firstjoinswitcher.setItem(18, color1);
		NightUtils.firstjoinswitcher.setItem(9, color2);
		new BukkitRunnable() {
			@Override
			public void run() {
				NightUtils.firstjoinswitcher.setItem(0, color2);
				NightUtils.firstjoinswitcher.setItem(1, color1);
				NightUtils.firstjoinswitcher.setItem(2, color2);
				NightUtils.firstjoinswitcher.setItem(3, color1);
				NightUtils.firstjoinswitcher.setItem(4, color2);
				NightUtils.firstjoinswitcher.setItem(5, color1);
				NightUtils.firstjoinswitcher.setItem(6, color2);
				NightUtils.firstjoinswitcher.setItem(7, color1);
				NightUtils.firstjoinswitcher.setItem(8, color2);
				NightUtils.firstjoinswitcher.setItem(17, color1);
				NightUtils.firstjoinswitcher.setItem(26, color2);
				NightUtils.firstjoinswitcher.setItem(25, color1);
				NightUtils.firstjoinswitcher.setItem(24, color2);
				NightUtils.firstjoinswitcher.setItem(23, color1);
				NightUtils.firstjoinswitcher.setItem(22, color2);
				NightUtils.firstjoinswitcher.setItem(21, color1);
				NightUtils.firstjoinswitcher.setItem(20, color2);
				NightUtils.firstjoinswitcher.setItem(19, color1);
				NightUtils.firstjoinswitcher.setItem(18, color2);
				NightUtils.firstjoinswitcher.setItem(9, color1);
			}
		}.runTaskLater(plugin, 5);
		}
	}.runTaskTimer(plugin, 9, 9);
	     
			
			
			
			
		}
}
