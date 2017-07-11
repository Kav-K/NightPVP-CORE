package us.nightpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class alertCommand implements CommandExecutor {
NightUtils plugin;
public alertCommand(NightUtils passedPlugin) {
	this.plugin = passedPlugin;
}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
         if (sender.hasPermission("frozenheart.alert")) {
        	if (args[0].isEmpty() || args[1].isEmpty()){
        		sender.sendMessage(ChatColor.RED+"Invalid Syntax");
        		return true;
        	} 
        	
        	String message = "";
        	for (int i =0; i<args.length;i++) {
        		message += " " + args[i];
        	}
        	message = message.replace(args[0], "");
         	 CheckIP.messages.put(args[0], message);
         	 sender.sendMessage(ChatColor.GREEN+"Player alerted if exists");
         	 
        	 
        	 
         }

		
		return true;
	}

}
