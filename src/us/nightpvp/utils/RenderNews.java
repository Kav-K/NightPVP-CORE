package us.nightpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

public class RenderNews extends MapRenderer {

	@Override
	public void render(MapView map, MapCanvas canvas, Player player) {
		canvas.drawText(10, 10, MinecraftFont.Font, "test");
		try {
		player.sendMap(map);
		} catch (Exception e) {
			Bukkit.broadcastMessage(e.toString() + e.getCause() + e.getMessage() + e.getStackTrace());
		}
		
	}

}
