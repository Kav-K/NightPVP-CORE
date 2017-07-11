package us.nightpvp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class vpnAppeal implements CommandExecutor {
	public static ArrayList<String> appealed= new ArrayList<String>();
	NightUtils plugin;
	public vpnAppeal(NightUtils passedPlugin) {
		this.plugin = passedPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		Player player = (Player) sender;
		if (Listeners.vpn.contains(player)) {
			if (!(appealed.contains(player.getName()))) {
				String hostname = Listeners.hostnames.get(player.getName());
				String asn = Listeners.asns.get(player.getName());
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &aSuccessfully appealed! If any staff are online, they will get to your request shortly!"));
				appealed.add(player.getName());
				for (Player p: Bukkit.getOnlinePlayers()) {
					if (p.hasPermission("frozenheart.notifyvpnappeal")) {
						List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
								"                      &5&lSolar&7&lConnector", "",
								"&5&lSolar&7&lPvP &cA player has appealed his VPN connection!", "", 
								"&5Player: &c"+player.getName(),
								"&5Hostname: &c" + hostname,
								"&5ASN: &c" + asn,
								"",
								"", "&8&l&m-------------------------------------");
						for (int i = 0; i < runes.size(); i++) {
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
						}
					}
					new BukkitRunnable() {
						@Override
						public void run(){
							appealed.remove(player.getName());
						}
					}.runTaskLater(plugin, 300);
				}
				
				
			} else {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cYou already appealed! Please wait another 10 minutes to appeal again"));
			}
		} 
		
		
		
		return true;
	}

}
