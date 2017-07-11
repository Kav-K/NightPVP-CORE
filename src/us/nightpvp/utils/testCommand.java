package us.nightpvp.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class testCommand implements CommandExecutor {
	NightUtils plugin;

	public testCommand(NightUtils passedPlugin) {
		this.plugin = passedPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		Player player = (Player) sender;
		ItemStack is = player.getItemInHand();
		ItemMeta meta = is.getItemMeta();
		List<String> metalist = new ArrayList<String>();
		metalist.add(meta.getDisplayName());
		for (int i= 0;i<meta.getLore().size();i++) {
			metalist.add(meta.getLore().get(i));
		}
		
		
		
		Iterable<String> iterable = metalist;
		
		
		
		//String playerinfo = new FancyMessage("Hover Over Me!").color(ChatColor.RED).tooltip(iterable).toJSONString();
		///for (Player p :Bukkit.getOnlinePlayers()){
	 //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " "+ playerinfo);
	
	//}
		
		
		return true;
		
	}
	 public void sendShowString(Player sender, ItemStack is, String prefix, String message, Player p) {
	        if (message.contains("@i@") && is != null && is.getType() != Material.AIR) {
	            String aprefix = prefix;
	            String[] split = message.split("@i@");
	            String after = "";
	            String before = "";
	            if (split.length > 0)
	                before = split[0];
	            if (split.length > 1)
	                after = split[1];

	            ItemStack stack = is;

	            List<String> hoveredChat = new ArrayList<>();
	            ItemMeta meta = stack.getItemMeta();
	            hoveredChat.add((meta.hasDisplayName() ? meta.getDisplayName() : stack.getType().name()));
	            if (meta.hasLore())
	                hoveredChat.addAll(meta.getLore());
	             JSONMessage normal = new JSONMessage(aprefix);
	            before = sender.getDisplayName() + ": " + ChatColor.WHITE + before;
	            normal.addText(before + "");
	            normal.addHoverText(hoveredChat, ChatColor.BOLD + ChatColor.UNDERLINE.toString() + "SHOW");
	            normal.addText(after);
				PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(normal.toString()));
				((CraftPlayer) p).getHandle().playerConnection.sendPacket( packet);
	        }
	    }
	

}
