package us.nightpvp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.earth2me.essentials.Kit;
import com.earth2me.essentials.User;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class Listeners implements Listener {
	NightUtils plugin;

	public Listeners(NightUtils passedPlugin) {
		this.plugin = passedPlugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	// most of the important shit is in here
	public static ArrayList<Player> repairerlist = new ArrayList<Player>();
	public static ArrayList<Player> healerlist = new ArrayList<Player>();
	public static ArrayList<Player> jumped = new ArrayList<Player>();
	public static ArrayList<Player> shuttle = new ArrayList<Player>();
	public static ArrayList<Player> toolvendorlist = new ArrayList<Player>();
	public static ArrayList<String> masterdialog = new ArrayList<>();
	public static ArrayList<String> adventureshuttle = new ArrayList<String>();
	public static HashMap<String, Location> locations = new HashMap<String, Location>();
	public static ArrayList<String> moonmasterdialog = new ArrayList<String>();
	public static ArrayList<String> toteleport = new ArrayList<String>();
	public static ArrayList<String> remasterdialog = new ArrayList<String>();
	public static ArrayList<Block> placed = new ArrayList<Block>();
	public static ArrayList<String> normaldialog = new ArrayList<String>();
	public static ArrayList<Player> vpn = new ArrayList<Player>();
	public static HashMap<String, String> hostnames = new HashMap<String, String>();
	public static HashMap<String, String> asns = new HashMap<String, String>();
	public static ArrayList<Player> nointeract = new ArrayList<Player>();
	public static HashMap<String, Location> respawnlocs = new HashMap<String, Location>();
	public static HashMap<String, String> messaged = new HashMap<String, String>();
	public static boolean tokick;
	public static boolean messagedhell = false;;
	public static HashMap<Player, Boolean> hell = new HashMap<Player, Boolean>();
	public static ArrayList<Player> hellvendorlist = new ArrayList<Player>();
	public static HashMap<Player, Boolean> moon = new HashMap<Player, Boolean>();
	public static ArrayList<Player> moonvendorlist = new ArrayList<Player>();
	public static ArrayList<Player> firstjoin = new ArrayList<Player>();
	public static HashMap<String, String> advertise = new HashMap<String, String>();
	public static HashMap<String, String> playerips = new HashMap<String, String>();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		if (NightUtils.confirm.contains(player.getName())) {
			event.setCancelled(true);
			if (message.equalsIgnoreCase("I confirm my account")) {
				
				new BukkitRunnable() {
					@Override
					public void run() {
						try {
							NightUtils.databaseVerify(player.getName());
							NightUtils.confirm.remove(player.getName());
							player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&5&lSolar&7&lPvP &8&l> &aYour link with the client has been confirmed! Please wait 10-30 seconds for your client to update"));
						} catch (Exception e) {
							NightUtils.confirm.remove(player.getName());
							player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&5&lSolar&7&lPvP &8&l> &cYou could not be verified at the moment. Please try again later."));
							NightUtils.databaseUnVerify(player.getName());
						}
					}
				}.runTaskAsynchronously(plugin);
				
			} else if (message.equalsIgnoreCase("cancel")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						NightUtils.databaseUnVerify(player.getName());
					}
				}.runTaskAsynchronously(plugin);
				
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&5&lSolar&7&lPvP &8&l> &cYour link with the client has been cancelled! Please wait 10-30 seconds for your client to update"));
			    NightUtils.confirm.remove(player.getName());
			} else {
				
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&5&lSolar&7&lPvP &8&l> &cThere is a pending confirmation request for your account with the NightPvP Client! Please type 'I confirm my account' to confirm or type 'cancel' to cancel!"));
			}
			
			
		}
		
		
		if (repairerlist.contains(player)) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setCancelled(true);
				}
			}.run();
			if (message.equalsIgnoreCase("yes") || message.equalsIgnoreCase("ye") || message.equalsIgnoreCase("yas")
					|| message.equalsIgnoreCase("yuh") || message.equalsIgnoreCase("ya")) {

				if (NightUtils.econ.getBalance(player.getName()) >= 20000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 20000);

					for (ItemStack i : player.getInventory().getContents()) {
						try {
							i.setDurability((short) 0);

						} catch (Exception e) {

						}
					}
					repairerlist.remove(player);
					player.sendMessage(
							ChatColor.translateAlternateColorCodes('&', "&5&lRepairer &8&l> &aSuccessfully repaired!"));
				} else {
					player.sendMessage(
							ChatColor.translateAlternateColorCodes('&', "&5&lRepairer &8&l> &7Insufficient funds!"));
					repairerlist.remove(player);
				}
			} else if (message.equalsIgnoreCase("no") || message.equalsIgnoreCase("noo")
					|| message.equalsIgnoreCase("na") || message.equalsIgnoreCase("nu")) {

				repairerlist.remove(player);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lRepairer &8&l> &7Your current request with the repairer has been cancelled"));
			}

		}

		if (masterdialog.contains(player.getName())) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setCancelled(true);
				}
			}.run();
			if (message.equalsIgnoreCase("yes") || message.equalsIgnoreCase("ye") || message.equalsIgnoreCase("yas")
					|| message.equalsIgnoreCase("yuh") || message.equalsIgnoreCase("ya")) {

				if (NightUtils.econ.getBalance(player.getName()) >= 1000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 1000);

					// TODO TP
					Location loc = new Location(Bukkit.getWorld("Hub"), 1662.416, 67, 367.448);
					player.teleport(loc);

					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lWorld Master &8&l> &aBe prepared to be teleported!"));
					player.teleport(loc);
					try {
						new BukkitRunnable() {
							@Override
							public void run() {
								player.setAllowFlight(true);
								player.setFlying(true);
							}
						}.runTask(plugin);

					} catch (Exception e) {
						Bukkit.broadcastMessage(e.toString());
					}
					player.setVelocity(new Vector(player.getVelocity().getX(), 3, player.getVelocity().getZ()));
					adventureshuttle.add(player.getName());
					try {
						masterdialog.remove(player.getName());
					} catch (Exception e) {

					}
					new BukkitRunnable() {
						@Override
						public void run() {

							adventureshuttle.remove(player.getName());
							masterdialog.remove(player.getName());
							Location loc = new Location(Bukkit.getWorld("Map"), 3579.500, 189.00, -5403.500);
							player.teleport(loc);
							List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
									"                      &5&lAdventure World", "",
									"&5&lSolar&7&lPvP &7welcomes you to the &5&lAdventure world!", "",
									"&5You can start playing right away!",
									"&7Our server has many different custom worlds, and you're in one of them! You can freely switch between worlds with the &5/switchworld &7command or by walking through the portals at spawn! Items, Factions, Money, etc are universal through all worlds! Have fun exploring!",
									"", "&8&l&m-------------------------------------");

							for (int i = 0; i < runes.size(); i++) {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
							}

							try {
								new BukkitRunnable() {
									@Override
									public void run() {
										player.setAllowFlight(false);
										player.setFlying(false);
									}
								}.runTask(plugin);

							} catch (Exception e) {
								Bukkit.broadcastMessage(e.toString());
							}
							plugin.isSwitching.remove(player.getName());
						}
					}.runTaskLater(plugin, 160);

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lWorld Master &8&l> &cYou do not have enough money to switch worlds!"));
					masterdialog.remove(player.getName());
					Location loc = locations.get(player.getName());
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							player.teleport(loc);
						}
					}, 1);
					locations.remove(player.getName());
					plugin.isSwitching.remove(player.getName());
				}
			} else if (message.equalsIgnoreCase("no") || message.equalsIgnoreCase("noo")
					|| message.equalsIgnoreCase("na") || message.equalsIgnoreCase("nu")) {
				Location loc = locations.get(player.getName());
				try {
					masterdialog.remove(player.getName());
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {

							player.teleport(loc);

						}
					}, 1);
					locations.remove(player.getName());
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',

							"&5&lWorld Master &8&l> &7Your current request with the world master has been cancelled"));
					plugin.isSwitching.remove(player.getName());
				} catch (Exception e) {
					Bukkit.broadcastMessage(e.toString());
				}
			}

		}
		if (moonmasterdialog.contains(player.getName())) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setCancelled(true);
				}
			}.run();
			if (message.equalsIgnoreCase("yes") || message.equalsIgnoreCase("ye") || message.equalsIgnoreCase("yas")
					|| message.equalsIgnoreCase("yuh") || message.equalsIgnoreCase("ya")) {

				if (NightUtils.econ.getBalance(player.getName()) >= 5000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 5000);

					// TODO TP
					Location loc = new Location(Bukkit.getWorld("Hub"), 1601.216, 113.0, 603.502);
					player.teleport(loc);

					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&e&lWorld Master &8&l> &aBe prepared to be teleported!"));
					player.teleport(loc);

					try {
						new BukkitRunnable() {
							@Override
							public void run() {
								player.setAllowFlight(true);
								player.setFlying(true);
							}
						}.runTask(plugin);

					} catch (Exception e) {
						Bukkit.broadcastMessage(e.toString());
					}
					player.setVelocity(new Vector(player.getVelocity().getX(), 3, player.getVelocity().getZ()));
					adventureshuttle.add(player.getName());
					try {
						moonmasterdialog.remove(player.getName());
					} catch (Exception e) {

					}
					new BukkitRunnable() {
						@Override
						public void run() {

							adventureshuttle.remove(player.getName());
							moonmasterdialog.remove(player.getName());
							Location loc = new Location(Bukkit.getWorld("Moon"), -2.50, 72.0, 59.50);
							player.teleport(loc);
							try {
								plugin.isSwitching.remove(player.getName());
							} catch (Exception e) {
								Bukkit.broadcastMessage(e.toString());
							}
							List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
									"                      &e&lThe Moon", "",
									"&5&lSolar&7&lPvP &7welcomes you to &e&lThe Moon", "",
									"&7You can only mine on the moon! Ore resets every 30 minutes! Ore is found in craters around the moon!",
									"&7Our server has many different custom worlds, and you're in one of them! You can freely switch between worlds with the &e/switchworld &7command or by walking through the portals at spawn! Items, Factions, Money, etc are universal through all worlds! Have fun exploring!",
									"", "&8&l&m-------------------------------------");

							for (int i = 0; i < runes.size(); i++) {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
							}
							try {
								plugin.isSwitching.remove(player.getName());
								try {
									new BukkitRunnable() {
										@Override
										public void run() {
											player.setAllowFlight(false);
											player.setFlying(false);
										}
									}.runTask(plugin);

								} catch (Exception e) {
									Bukkit.broadcastMessage(e.toString());
								}
							} catch (Exception e) {
								Bukkit.broadcastMessage(e.toString());
							}
						}
					}.runTaskLater(plugin, 160);

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&e&lWorld Master &8&l> &cYou do not have enough money to switch worlds!"));
					moonmasterdialog.remove(player.getName());
					Location loc = locations.get(player.getName());
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							player.teleport(loc);
						}
					}, 1);
					locations.remove(player.getName());
					plugin.isSwitching.remove(player.getName());
				}
			} else if (message.equalsIgnoreCase("no") || message.equalsIgnoreCase("noo")
					|| message.equalsIgnoreCase("na") || message.equalsIgnoreCase("nu")) {

				try {
					moonmasterdialog.remove(player.getName());
					Location loc = locations.get(player.getName());
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							player.teleport(loc);
						}
					}, 1);
					locations.remove(player.getName());
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',

							"&e&lWorld Master &8&l> &7Your current request with the world master has been cancelled"));
					plugin.isSwitching.remove(player.getName());
				} catch (Exception e) {
					Bukkit.broadcastMessage(e.toString());
				}
			}

		}

		if (normaldialog.contains(player.getName())) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setCancelled(true);
				}
			}.run();
			if (message.equalsIgnoreCase("yes") || message.equalsIgnoreCase("ye") || message.equalsIgnoreCase("yas")
					|| message.equalsIgnoreCase("yuh") || message.equalsIgnoreCase("ya")) {

				if (NightUtils.econ.getBalance(player.getName()) >= 3000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 3000);

					// TODO TP
					Location loc = new Location(Bukkit.getWorld("Hub"), 1941.723, 126.0, 405.410);
					player.teleport(loc);

					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&7&lWorld Master &8&l> &aBe prepared to be teleported!"));
					player.teleport(loc);
					try {
						new BukkitRunnable() {
							@Override
							public void run() {
								player.setAllowFlight(true);
								player.setFlying(true);
							}
						}.runTask(plugin);

					} catch (Exception e) {
						Bukkit.broadcastMessage(e.toString());
					}
					player.setVelocity(new Vector(player.getVelocity().getX(), 3, player.getVelocity().getZ()));
					adventureshuttle.add(player.getName());
					try {
						normaldialog.remove(player.getName());
					} catch (Exception e) {

					}
					new BukkitRunnable() {
						@Override
						public void run() {

							adventureshuttle.remove(player.getName());
							normaldialog.remove(player.getName());
							Location loc = new Location(Bukkit.getWorld("Normal"), 598.50, 186.0, -244.50);
							player.teleport(loc);

							List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
									"                      &7&lNormal World", "",
									"&5&lSolar&7&lPvP &7welcomes you to the &7&lNormal world!", "",
									"&7You can start playing right away!",
									"&7Our server has many different custom worlds, and you're in one of them! You can freely switch between worlds with the     &7/switchworld &7command or by walking through the portals at spawn! ",
									"",
									"&7Items, Factions, Money, etc are universal through all worlds! Have fun exploring!",
									"",

									"", "&8&l&m-------------------------------------");

							for (int i = 0; i < runes.size(); i++) {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
							}
							try {
								plugin.isSwitching.remove(player.getName());
								try {
									new BukkitRunnable() {
										@Override
										public void run() {
											player.setAllowFlight(false);
											player.setFlying(false);
										}
									}.runTask(plugin);

								} catch (Exception e) {
									Bukkit.broadcastMessage(e.toString());
								}
							} catch (Exception e) {
								Bukkit.broadcastMessage(e.toString());
							}
						}
					}.runTaskLater(plugin, 160);

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&7&lWorld Master &8&l> &cYou do not have enough money to switch worlds!"));
					normaldialog.remove(player.getName());
					Location loc = locations.get(player.getName());
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							player.teleport(loc);
						}
					}, 1);
					locations.remove(player.getName());
					plugin.isSwitching.remove(player.getName());
				}
			} else if (message.equalsIgnoreCase("no") || message.equalsIgnoreCase("noo")
					|| message.equalsIgnoreCase("na") || message.equalsIgnoreCase("nu")) {

				try {
					normaldialog.remove(player.getName());
					Location loc = locations.get(player.getName());
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							player.teleport(loc);
						}
					}, 1);
					locations.remove(player.getName());
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',

							"&7&lWorld Master &8&l> &7Your current request with the world master has been cancelled"));
					plugin.isSwitching.remove(player.getName());
				} catch (Exception e) {
					Bukkit.broadcastMessage(e.toString());
				}
			}

		}
		if (remasterdialog.contains(player.getName())) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setCancelled(true);
				}
			}.run();
			if (message.equalsIgnoreCase("yes") || message.equalsIgnoreCase("ye") || message.equalsIgnoreCase("yas")
					|| message.equalsIgnoreCase("yuh") || message.equalsIgnoreCase("ya")) {

				if (NightUtils.econ.getBalance(player.getName()) >= 3000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 3000);

					// TODO TP
					Location loc = new Location(Bukkit.getWorld("Hub"), 901.565, 83.0, 1462.512);
					player.teleport(loc);

					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&d&lWorld Master &8&l> &aBe prepared to be teleported!"));
					player.teleport(loc);
					try {
						new BukkitRunnable() {
							@Override
							public void run() {
								player.setAllowFlight(true);
								player.setFlying(true);
							}
						}.runTask(plugin);

					} catch (Exception e) {
						Bukkit.broadcastMessage(e.toString());
					}
					player.setVelocity(new Vector(player.getVelocity().getX(), 3, player.getVelocity().getZ()));
					adventureshuttle.add(player.getName());
					try {
						remasterdialog.remove(player.getName());
					} catch (Exception e) {

					}
					new BukkitRunnable() {
						@Override
						public void run() {
							// TODO marker
							adventureshuttle.remove(player.getName());
							try {
								remasterdialog.remove(player.getName());
							} catch (Exception e) {

							}

							Location loc = new Location(Bukkit.getWorld("Reminiscence"), 4.475, 203.0, -914.37);
							player.teleport(loc);
							List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
									"                      &d&lReminiscence World", "",
									"&5&lSolar&7&lPvP &7welcomes you to the &d&lReminiscence world!", "",
									"&dYou can start playing right away!",
									"&7Our server has many different custom worlds, and you're in one of them! You can freely switch between worlds with the &d/switchworld &7command or by walking through the portals at spawn! ",
									"",
									"Items, Factions, Money, etc are universal through all worlds! Have fun exploring!",
									"",
									"&dBlocks in this world reset 6 hours after broken/placed! This world is mainly used for temporary events!",
									"", "&8&l&m-------------------------------------");

							for (int i = 0; i < runes.size(); i++) {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
							}
							try {
								plugin.isSwitching.remove(player.getName());
								try {
									new BukkitRunnable() {
										@Override
										public void run() {
											player.setAllowFlight(false);
											player.setFlying(false);
										}
									}.runTask(plugin);

								} catch (Exception e) {
									Bukkit.broadcastMessage(e.toString());
								}
							} catch (Exception e) {
								Bukkit.broadcastMessage(e.toString());
							}
						}
					}.runTaskLater(plugin, 160);

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&d&lWorld Master &8&l> &cYou do not have enough money to switch worlds!"));
					remasterdialog.remove(player.getName());
					Location loc = locations.get(player.getName());
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							player.teleport(loc);
						}
					}, 1);
					locations.remove(player.getName());
					plugin.isSwitching.remove(player.getName());
				}
			} else if (message.equalsIgnoreCase("no") || message.equalsIgnoreCase("noo")
					|| message.equalsIgnoreCase("na") || message.equalsIgnoreCase("nu")) {

				try {

					remasterdialog.remove(player.getName());

					Location loc = locations.get(player.getName());
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							player.teleport(loc);
						}
					}, 1);

					locations.remove(player.getName());

					player.sendMessage(ChatColor.translateAlternateColorCodes('&',

							"&d&lWorld Master &8&l> &7Your current request with the world master has been cancelled"));

					plugin.isSwitching.remove(player.getName());
				} catch (Exception e) {
					Bukkit.broadcastMessage(e.toString());
				}
			}

		}

		if (toolvendorlist.contains(player)) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setCancelled(true);
				}
			}.run();
			if (message.equalsIgnoreCase("yes") || message.equalsIgnoreCase("ye") || message.equalsIgnoreCase("yas")
					|| message.equalsIgnoreCase("yuh") || message.equalsIgnoreCase("ya")) {

				if (NightUtils.econ.getBalance(player.getName()) >= 5000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 5000);

					Random rand = new Random();
					int number = rand.nextInt(69);
					try {
						Kit esskit = new Kit("pick" + number, plugin.essentials);
						User user = plugin.essentials.getUser(player);
						esskit.expandItems(user);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					toolvendorlist.remove(player);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&e&lTool Vendor &8&l> &aSuccessfully purchased!"));
				} else {
					player.sendMessage(
							ChatColor.translateAlternateColorCodes('&', "&e&lTool Vendor &8&l> &7Insufficient funds!"));
					toolvendorlist.remove(player);
				}
			} else if (message.equalsIgnoreCase("no") || message.equalsIgnoreCase("noo")
					|| message.equalsIgnoreCase("na") || message.equalsIgnoreCase("nu")) {

				toolvendorlist.remove(player);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &7Your current request with the Tool Vendor has been cancelled"));
			}

		}
		if (hellvendorlist.contains(player)) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setCancelled(true);
				}
			}.run();
			if (message.equalsIgnoreCase("yes") || message.equalsIgnoreCase("ye") || message.equalsIgnoreCase("yas")
					|| message.equalsIgnoreCase("yuh") || message.equalsIgnoreCase("ya")) {

				if (NightUtils.econ.getBalance(player.getName()) >= 2500) {
					NightUtils.econ.withdrawPlayer(player.getName(), 2500);

					Random rand = new Random();
					int number = rand.nextInt(69);
					try {
						ItemStack is = new ItemStack(Material.DIAMOND_BOOTS);
						ItemMeta meta = is.getItemMeta();
						is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
						meta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + "Hell Boots");
						List<String> lorelist = new ArrayList<String>();
						lorelist.add(ChatColor.RED + "The protective boots of the undead");
						meta.setLore(lorelist);
						is.setItemMeta(meta);
						player.getInventory().addItem(is);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					hellvendorlist.remove(player);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&c&lHell Vendor &8&l> &aSuccessfully purchased!"));
				} else {
					player.sendMessage(
							ChatColor.translateAlternateColorCodes('&', "&c&lHell Vendor &8&l> &7Insufficient funds!"));
					hellvendorlist.remove(player);
				}
			} else if (message.equalsIgnoreCase("no") || message.equalsIgnoreCase("noo")
					|| message.equalsIgnoreCase("na") || message.equalsIgnoreCase("nu")) {

				hellvendorlist.remove(player);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &7Your current request with the Hell Vendor has been cancelled"));
			}

		}
		if (moonvendorlist.contains(player)) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setCancelled(true);
				}
			}.run();
			if (message.equalsIgnoreCase("yes") || message.equalsIgnoreCase("ye") || message.equalsIgnoreCase("yas")
					|| message.equalsIgnoreCase("yuh") || message.equalsIgnoreCase("ya")) {

				if (NightUtils.econ.getBalance(player.getName()) >= 2500) {
					NightUtils.econ.withdrawPlayer(player.getName(), 2500);

					Random rand = new Random();
					int number = rand.nextInt(69);
					try {
						ItemStack is = new ItemStack(Material.IRON_HELMET);
						ItemMeta meta = is.getItemMeta();
						is.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
						meta.setDisplayName(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Space Helmet");
						List<String> lorelist = new ArrayList<String>();
						lorelist.add(ChatColor.YELLOW + "A protective helmet forged in quartz");
						meta.setLore(lorelist);
						is.setItemMeta(meta);
						player.getInventory().addItem(is);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					moonvendorlist.remove(player);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&e&lMoon Vendor &8&l> &aSuccessfully purchased!"));
				} else {
					player.sendMessage(
							ChatColor.translateAlternateColorCodes('&', "&e&lMoon Vendor &8&l> &7Insufficient funds!"));
					moonvendorlist.remove(player);
				}
			} else if (message.equalsIgnoreCase("no") || message.equalsIgnoreCase("noo")
					|| message.equalsIgnoreCase("na") || message.equalsIgnoreCase("nu")) {

				moonvendorlist.remove(player);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &7Your current request with the Moon Vendor has been cancelled"));
			}

		}

		if (healerlist.contains(player)) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setCancelled(true);
				}
			}.run();
			if (message.equalsIgnoreCase("yes") || message.equalsIgnoreCase("ye") || message.equalsIgnoreCase("yas")
					|| message.equalsIgnoreCase("yuh") || message.equalsIgnoreCase("ya")) {

				if (NightUtils.econ.getBalance(player.getName()) >= 500) {
					NightUtils.econ.withdrawPlayer(player.getName(), 500);
					player.sendMessage(
							ChatColor.translateAlternateColorCodes('&', "&4&lHealer &8&l> &aSuccessfully healed!"));
					player.setHealth(20);
					healerlist.remove(player);

				} else {
					player.sendMessage(
							ChatColor.translateAlternateColorCodes('&', "&4&lHealer &8&l> &7Insufficient funds!"));
					healerlist.remove(player);
				}
			} else if (message.equalsIgnoreCase("no") || message.equalsIgnoreCase("noo")
					|| message.equalsIgnoreCase("na") || message.equalsIgnoreCase("nu")) {

				healerlist.remove(player);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &7Your current request with the healer has been cancelled"));
			}

		}

		if (plugin.locked || plugin.fullocked) {
			if (!(event.getPlayer().hasPermission("frozenheart.bypasslock"))) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cThe chat is currently locked!"));
				return;
			}
		}

		if (event.getMessage().contains("[item]") && event.getPlayer().getItemInHand() != null
				&& event.getPlayer().getItemInHand().getType() != Material.AIR) {
			event.setCancelled(true);
			String message2 = event.getMessage().replace("[item]", "@item@");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sudo " + event.getPlayer().getName() + " c:" + message2);

		}
		if (event.getMessage().contains("@item@") && event.getPlayer().getItemInHand() != null
				&& event.getPlayer().getItemInHand().getType() != Material.AIR) {
			event.setCancelled(true);
			String aprefix = "";
			String[] split = event.getMessage().split("@item@");
			String after = "";
			String before = "";
			if (split.length > 0)
				before = split[0];
			if (split.length > 1)
				after = split[1];

			ItemStack stack = event.getPlayer().getItemInHand();

			List<String> hoveredChat = new ArrayList<>();
			ItemMeta meta = stack.getItemMeta();
			hoveredChat.add((meta.hasDisplayName() ? meta.getDisplayName() : stack.getType().name()));

			if (meta.hasEnchant(Enchantment.DAMAGE_ALL)) {
				int level = meta.getEnchantLevel(Enchantment.DAMAGE_ALL);
				hoveredChat.add(ChatColor.GRAY + "Sharpness " + level);

			}
			if (meta.hasEnchant(Enchantment.ARROW_DAMAGE)) {
				int level = meta.getEnchantLevel(Enchantment.ARROW_DAMAGE);
				hoveredChat.add(ChatColor.GRAY + "SPower " + level);

			}
			if (meta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
				int level = meta.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
				hoveredChat.add(ChatColor.GRAY + "Protection " + level);

			}
			if (meta.hasEnchant(Enchantment.FIRE_ASPECT)) {
				int level = meta.getEnchantLevel(Enchantment.FIRE_ASPECT);
				hoveredChat.add(ChatColor.GRAY + "Fire Aspect " + level);

			}
			if (meta.hasEnchant(Enchantment.ARROW_KNOCKBACK)) {
				int level = meta.getEnchantLevel(Enchantment.ARROW_KNOCKBACK);
				hoveredChat.add(ChatColor.GRAY + "Punch " + level);

			}
			if (meta.hasEnchant(Enchantment.DIG_SPEED)) {
				int level = meta.getEnchantLevel(Enchantment.DIG_SPEED);
				hoveredChat.add(ChatColor.GRAY + "Efficiency " + level);

			}
			if (meta.hasEnchant(Enchantment.DURABILITY)) {
				int level = meta.getEnchantLevel(Enchantment.DURABILITY);
				hoveredChat.add(ChatColor.GRAY + "Durability " + level);

			}
			if (meta.hasEnchant(Enchantment.KNOCKBACK)) {
				int level = meta.getEnchantLevel(Enchantment.KNOCKBACK);
				hoveredChat.add(ChatColor.GRAY + "Knockback " + level);

			}
			if (meta.hasEnchant(Enchantment.LOOT_BONUS_MOBS)) {
				int level = meta.getEnchantLevel(Enchantment.LOOT_BONUS_MOBS);
				hoveredChat.add(ChatColor.GRAY + "Looting " + level);

			}
			if (meta.hasEnchant(Enchantment.LOOT_BONUS_BLOCKS)) {
				int level = meta.getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
				hoveredChat.add(ChatColor.GRAY + "Fortune " + level);

			}
			if (meta.hasEnchant(Enchantment.ARROW_INFINITE)) {
				int level = meta.getEnchantLevel(Enchantment.ARROW_INFINITE);
				hoveredChat.add(ChatColor.GRAY + "Infinity " + level);

			}

			if (meta.hasLore()) {
				hoveredChat.addAll(meta.getLore());
			}

			String world;
			if (event.getPlayer().getWorld().getName().equals("Map")) {
				world = ChatColor.translateAlternateColorCodes('&', "&f[&5Adventure&f]");
			} else if (event.getPlayer().getWorld().getName().equals("world_nether")) {
				world = ChatColor.translateAlternateColorCodes('&', "&f[&cHell&f]");
			} else if (event.getPlayer().getWorld().getName().equals("Moon")) {
				world = ChatColor.translateAlternateColorCodes('&', "&f[&eMoon&f]");
			} else if (event.getPlayer().getWorld().getName().equals("Reminiscence")) {
				world = ChatColor.translateAlternateColorCodes('&', "&f[&dReminiscence&f]");
			} else if (event.getPlayer().getWorld().getName().equals("Hub")) {
				world = ChatColor.translateAlternateColorCodes('&', "&f[&5Hub&f]");
			} else if (event.getPlayer().getWorld().getName().equals("Normal")) {
				world = ChatColor.translateAlternateColorCodes('&', "&f[&7Normal&f]");
			} else {
				world = "";
			}

			JSONMessage normal = new JSONMessage(world + " " + aprefix);
			before = event.getPlayer().getDisplayName() + ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + before;
			normal.addText(ChatColor.GRAY + before + "");
			normal.addHoverText(hoveredChat,
					ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + " >> "
							+ (meta.hasDisplayName() ? meta.getDisplayName() : stack.getType().name())
							+ ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + " << ");
			normal.addText(ChatColor.GRAY + after);
			PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(normal.toString()));
			for (Player p : Bukkit.getOnlinePlayers()) {
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
			}
		}

		String perms;
		perms = plugin.perms.getPrimaryGroup(event.getPlayer());
		if (perms.equalsIgnoreCase("Admin") || perms.equalsIgnoreCase("Head-Admin")
				|| perms.equalsIgnoreCase("Owner")) {
			if (event.getFormat().contains("Adventure")) {

				event.setFormat(event.getFormat().replaceAll("Adventure", ChatColor.DARK_PURPLE + "Adventure"));

			} else if (event.getFormat().contains("Hub")) {
				new BukkitRunnable() {

					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hub", ChatColor.DARK_PURPLE + "Hub"));
					}
				}.run();
			} else if (event.getFormat().contains("Arena")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Arena", ChatColor.RED + "Arena"));
					}
				}.run();
			} else if (event.getFormat().contains("Reminiscence"))

			{
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(
								event.getFormat().replaceAll("Reminiscence", ChatColor.LIGHT_PURPLE + "Reminiscence"));
					}
				}.run();
			} else if (event.getFormat().contains("Moon")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Moon", ChatColor.YELLOW + "Moon"));
					}
				}.run();
			} else if (event.getFormat().contains("Normal")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Normal", ChatColor.GRAY + "Normal"));
					}
				}.run();
			} else if (event.getFormat().contains("Hell")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hell", ChatColor.RED + "Hell"));
					}
				}.run();
			}
			event.setMessage(ChatColor.translateAlternateColorCodes('&', "&c" + event.getMessage()));
		} else if (perms.equalsIgnoreCase("Reaper")) {
			if (event.getFormat().contains("Adventure")) {

				event.setFormat(event.getFormat().replaceAll("Adventure", ChatColor.DARK_PURPLE + "Adventure"));

			} else if (event.getFormat().contains("Hub")) {
				new BukkitRunnable() {

					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hub", ChatColor.DARK_PURPLE + "Hub"));
					}
				}.run();
			} else if (event.getFormat().contains("Arena")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Arena", ChatColor.RED + "Arena"));
					}
				}.run();
			} else if (event.getFormat().contains("Reminiscence"))

			{
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(
								event.getFormat().replaceAll("Reminiscence", ChatColor.LIGHT_PURPLE + "Reminiscence"));
					}
				}.run();
			} else if (event.getFormat().contains("Moon")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Moon", ChatColor.YELLOW + "Moon"));
					}
				}.run();
			} else if (event.getFormat().contains("Normal")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Normal", ChatColor.GRAY + "Normal"));
					}
				}.run();
			} else if (event.getFormat().contains("Hell")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hell", ChatColor.RED + "Hell"));
					}
				}.run();
			}
			event.setMessage(ChatColor.translateAlternateColorCodes('&', "&5" + event.getMessage()));
		} else if (perms.equalsIgnoreCase("Demon")) {
			if (event.getFormat().contains("Adventure")) {

				event.setFormat(event.getFormat().replaceAll("Adventure", ChatColor.DARK_PURPLE + "Adventure"));

			} else if (event.getFormat().contains("Hub")) {
				new BukkitRunnable() {

					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hub", ChatColor.DARK_PURPLE + "Hub"));
					}
				}.run();
			} else if (event.getFormat().contains("Arena")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Arena", ChatColor.RED + "Arena"));
					}
				}.run();
			} else if (event.getFormat().contains("Reminiscence"))

			{
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(
								event.getFormat().replaceAll("Reminiscence", ChatColor.LIGHT_PURPLE + "Reminiscence"));
					}
				}.run();
			} else if (event.getFormat().contains("Moon")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Moon", ChatColor.YELLOW + "Moon"));
					}
				}.run();
			} else if (event.getFormat().contains("Normal")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Normal", ChatColor.GRAY + "Normal"));
					}
				}.run();
			} else if (event.getFormat().contains("Hell")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hell", ChatColor.RED + "Hell"));
					}
				}.run();
			}
			event.setMessage(ChatColor.translateAlternateColorCodes('&', "&d" + event.getMessage()));
		} else if (perms.equalsIgnoreCase("Hades")) {
			if (event.getFormat().contains("Adventure")) {

				event.setFormat(event.getFormat().replaceAll("Adventure", ChatColor.DARK_PURPLE + "Adventure"));

			} else if (event.getFormat().contains("Hub")) {
				new BukkitRunnable() {

					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hub", ChatColor.DARK_PURPLE + "Hub"));
					}
				}.run();
			} else if (event.getFormat().contains("Arena")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Arena", ChatColor.RED + "Arena"));
					}
				}.run();
			} else if (event.getFormat().contains("Reminiscence"))

			{
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(
								event.getFormat().replaceAll("Reminiscence", ChatColor.LIGHT_PURPLE + "Reminiscence"));
					}
				}.run();
			} else if (event.getFormat().contains("Moon")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Moon", ChatColor.YELLOW + "Moon"));
					}
				}.run();
			} else if (event.getFormat().contains("Normal")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Normal", ChatColor.GRAY + "Normal"));
					}
				}.run();
			} else if (event.getFormat().contains("Hell")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hell", ChatColor.RED + "Hell"));
					}
				}.run();
			}
			event.setMessage(ChatColor.translateAlternateColorCodes('&', "&4" + event.getMessage()));
		} else if (perms.equalsIgnoreCase("Moderator")) {
			if (event.getFormat().contains("Adventure")) {

				event.setFormat(event.getFormat().replaceAll("Adventure", ChatColor.DARK_PURPLE + "Adventure"));

			} else if (event.getFormat().contains("Hub")) {
				new BukkitRunnable() {

					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hub", ChatColor.DARK_PURPLE + "Hub"));
					}
				}.run();
			} else if (event.getFormat().contains("Arena")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Arena", ChatColor.RED + "Arena"));
					}
				}.run();
			} else if (event.getFormat().contains("Reminiscence"))

			{
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(
								event.getFormat().replaceAll("Reminiscence", ChatColor.LIGHT_PURPLE + "Reminiscence"));
					}
				}.run();
			} else if (event.getFormat().contains("Moon")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Moon", ChatColor.YELLOW + "Moon"));
					}
				}.run();
			} else if (event.getFormat().contains("Normal")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Normal", ChatColor.GRAY + "Normal"));
					}
				}.run();
			} else if (event.getFormat().contains("Hell")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hell", ChatColor.RED + "Hell"));
					}
				}.run();
			}
			event.setMessage(ChatColor.translateAlternateColorCodes('&', "&6" + event.getMessage()));
		} else if (perms.equalsIgnoreCase("Helper")) {
			if (event.getFormat().contains("Adventure")) {

				event.setFormat(event.getFormat().replaceAll("Adventure", ChatColor.DARK_PURPLE + "Adventure"));

			} else if (event.getFormat().contains("Hub")) {
				new BukkitRunnable() {

					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hub", ChatColor.DARK_PURPLE + "Hub"));
					}
				}.run();
			} else if (event.getFormat().contains("Arena")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Arena", ChatColor.RED + "Arena"));
					}
				}.run();
			} else if (event.getFormat().contains("Reminiscence"))

			{
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(
								event.getFormat().replaceAll("Reminiscence", ChatColor.LIGHT_PURPLE + "Reminiscence"));
					}
				}.run();
			} else if (event.getFormat().contains("Moon")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Moon", ChatColor.YELLOW + "Moon"));
					}
				}.run();
			} else if (event.getFormat().contains("Normal")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Normal", ChatColor.GRAY + "Normal"));
					}
				}.run();
			} else if (event.getFormat().contains("Hell")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hell", ChatColor.RED + "Hell"));
					}
				}.run();
			}
			event.setMessage(ChatColor.translateAlternateColorCodes('&', "&a" + event.getMessage()));
		} else if (perms.equalsIgnoreCase("Builder")) {
			if (event.getFormat().contains("Adventure")) {

				event.setFormat(event.getFormat().replaceAll("Adventure", ChatColor.DARK_PURPLE + "Adventure"));

			} else if (event.getFormat().contains("Hub")) {
				new BukkitRunnable() {

					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hub", ChatColor.DARK_PURPLE + "Hub"));
					}
				}.run();
			} else if (event.getFormat().contains("Arena")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Arena", ChatColor.RED + "Arena"));
					}
				}.run();
			} else if (event.getFormat().contains("Reminiscence"))

			{
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(
								event.getFormat().replaceAll("Reminiscence", ChatColor.LIGHT_PURPLE + "Reminiscence"));
					}
				}.run();
			} else if (event.getFormat().contains("Moon")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Moon", ChatColor.YELLOW + "Moon"));
					}
				}.run();
			} else if (event.getFormat().contains("Normal")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Normal", ChatColor.GRAY + "Normal"));
					}
				}.run();
			} else if (event.getFormat().contains("Hell")) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.setFormat(event.getFormat().replaceAll("Hell", ChatColor.RED + "Hell"));
					}
				}.run();
			}
			event.setMessage(ChatColor.translateAlternateColorCodes('&', "&3" + event.getMessage()));
		}

		if (plugin.frozen) {
			if (event.getPlayer() != plugin.controller) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "A full server freeze is in effect");
				return;
			}
		}

		if (vpn.contains(event.getPlayer())) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setCancelled(true);
				}
			}.run();

			String hostname = hostnames.get(event.getPlayer().getName());
			String asn = asns.get(event.getPlayer().getName());
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lSolar&7&lConnector", "",
					"&5&lSolar&7&lPvP &cdoes not let you play on a VPN!", "", "&5Your hostname: &c" + hostname,
					"&5Your ASN: &c" + asn, "", "&7You are getting this error because you are connecting with a VPN!",
					"&7Your hostname or ASN was unknown to us and was flagged as dangerous!",
					"&7Please log off your VPN and you will be able to play normally! If you think this is in error please do &5/vpnappeal",
					"&cYou will automatically be kicked in 60 seconds", "",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}
		}

		if (event.getFormat().contains("Adventure")) {

			event.setFormat(event.getFormat().replaceAll("Adventure", ChatColor.DARK_PURPLE + "Adventure"));

		} else if (event.getFormat().contains("Hub")) {
			new BukkitRunnable() {

				@Override
				public void run() {
					event.setFormat(event.getFormat().replaceAll("Hub", ChatColor.DARK_PURPLE + "Hub"));
				}
			}.run();
		} else if (event.getFormat().contains("Arena")) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setFormat(event.getFormat().replaceAll("Arena", ChatColor.RED + "Arena"));
				}
			}.run();
		} else if (event.getFormat().contains("Reminiscence"))

		{
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setFormat(
							event.getFormat().replaceAll("Reminiscence", ChatColor.LIGHT_PURPLE + "Reminiscence"));
				}
			}.run();
		} else if (event.getFormat().contains("Moon")) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setFormat(event.getFormat().replaceAll("Moon", ChatColor.YELLOW + "Moon"));
				}
			}.run();
		} else if (event.getFormat().contains("Normal")) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setFormat(event.getFormat().replaceAll("Normal", ChatColor.GRAY + "Normal"));
				}
			}.run();
		} else if (event.getFormat().contains("Hell")) {
			new BukkitRunnable() {
				@Override
				public void run() {
					event.setFormat(event.getFormat().replaceAll("Hell", ChatColor.RED + "Hell"));
				}
			}.run();
		}

		String message2 = event.getMessage();
		if (message2.toLowerCase().contains("i dont see npcs") || message2.toLowerCase().contains("there are no npcs")
				|| message2.toLowerCase().contains("no npcs")
				|| message2.toLowerCase().contains("why are there no npcs")
				|| message2.toLowerCase().contains("why no npcs")
				|| message2.toLowerCase().contains("where are the npcs")
				|| message2.toLowerCase().contains("where are npcs")
				|| message2.toLowerCase().contains("wheres the world master")
				|| message2.toLowerCase().contains("i dont see the world master")) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/citizens reload");
			try {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "citizens reload");
			} catch (Exception e) {

			}
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&5&lSolar&7&lPvP &8&l> &cThe NPCs have been reset! Thank you for telling us!"));
		}

		if (player.hasPermission("frozenheart.bypassadvertise")) {

		} else {

			List<String> glitchy = Arrays.asList("my inventory is glitchy", "i have glitch", "my inventory is bugged",
					"i cant move items", "my items dont move", "my books dont work", "my book doesnt work",
					"i cant right click book", "i cant right click", "book not working", "my books are glitched",
					"my book is glitched", "my inventory is broken", "wtf my inventory", "im glitched", "im stuck",
					"im glitchy");
			for (int i = 0; i < glitchy.size(); i++) {
				if (message2.toLowerCase().contains(glitchy.get(i))) {

					List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
							"                      &5&lSolar&7&lPvP",
							"&7Hey there! It seems like you're having some problems! Most problems can be solved by relogging!",
							"&7You may also type &5&l/issues&7 to automatically fix yourself!", "",
							"&8&l&m-------------------------------------");

					for (int b = 0; b < runes.size(); b++) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(b)));
					}
				}
			}

			if (!(event.getMessage().toLowerCase().contains("combat"))) {
				for (int i = 0; i < plugin.blocked.size(); i++) {

					if (message2.toLowerCase().contains(plugin.blocked.get(i))) {
						for (Player p : Bukkit.getOnlinePlayers()) {
							if (p.hasPermission("frozenheart.bypassadvertise")) {
								p.sendMessage(ChatColor.DARK_PURPLE + player.getName() + ChatColor.GRAY
										+ "Has tried to advertise the message '" + ChatColor.DARK_PURPLE
										+ event.getMessage() + ChatColor.GRAY + "'");

							}
						}

						event.setCancelled(true);
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &cYou have said a blocked word or have tried to advertise! This is your first and only warning!"));
						return;

					}

				}
				for (int i = 0; i < plugin.howtogetstaff.size(); i++) {

					if (message2.toLowerCase().contains(plugin.howtogetstaff.get(i))) {

						event.setCancelled(true);
						List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
								"                      &5&lSolar&7&lPvP", "",
								"&5Hey there! It seems that you're wondering about how to get staff!",
								"&7On this server, there is no application process to get staff! Just play, be active, and be helpful to others for staff!",
								"&7Your chances of getting staff are &aINCREASE &7if you donate as well!",
								"&7Your chances of getting staff are &cDECREASED &7if you ask about it in chat!", "",
								"&8&l&m-------------------------------------");

						for (int b = 0; b < runes.size(); b++) {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(b)));
						}
						return;

					}

				}
				for (int i = 0; i < plugin.freekeys.size(); i++) {

					if (message2.toLowerCase().contains(plugin.freekeys.get(i))) {

						event.setCancelled(true);
						List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
								"                      &5&lSolar&7&lPvP", "",
								"&5Hey there! It seems that you're wondering about how to get free keys!!",
								"&7To redeem your free keys, and other rewards, do &5&l/rewarder&7 and click the items!",
								"", "&8&l&m-------------------------------------");

						for (int b = 0; b < runes.size(); b++) {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(b)));
						}
						return;

					}

				}
			}

		}

		if (!(player.hasPermission("frozenheart.bypassdelay"))) {
			long currenttime = System.currentTimeMillis();
			long currenttimesecs = TimeUnit.MILLISECONDS.toSeconds(currenttime);
			if (plugin.delaymap.containsKey(player.getName())) {
				event.setCancelled(true);
				long timeremaining = currenttimesecs - plugin.delaymap.get(player.getName());
				int realtimeremaining = (int) (plugin.delay - timeremaining);
				player.sendMessage(ChatColor.DARK_GRAY
						+ "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
				player.sendMessage("");
				player.sendMessage(ChatColor.DARK_GRAY + ChatColor.BOLD.toString() + ">>" + ChatColor.GRAY
						+ " Time remaining before you can send another message: " + ChatColor.DARK_PURPLE
						+ realtimeremaining);
				player.sendMessage("");
				player.sendMessage(ChatColor.DARK_GRAY
						+ "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
			} else {
				plugin.delaymap.put(player.getName(), currenttimesecs);
				new BukkitRunnable() {
					@Override
					public void run() {
						plugin.delaymap.remove(player.getName());
					}
				}.runTaskLater(this.plugin, 20 * plugin.delay);
			}
		}

	}

	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

		}

	}

	@EventHandler
	public void onClick(NPCRightClickEvent event) {

		if (plugin.frozen) {
			if (event.getClicker() != plugin.controller) {
				event.setCancelled(true);
				event.getClicker().sendMessage(ChatColor.RED + "A full server freeze is in effect");
				return;
			}
		}
		if (vpn.contains(event.getClicker())) {
			event.setCancelled(true);
			event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&5&lSolar&7&lPvP &8&l> &cYou may not play on SolarPvP while on a VPN! If you think this is in error please contact staff with &4/vpnappeal!"));
		}
		if (event.getNPC().getName().equals(ChatColor.GREEN + ChatColor.BOLD.toString() + "Helper")) {
			event.getClicker().openInventory(NightUtils.getHelpInventory());
		} else if (event.getNPC().getName().equals(ChatColor.GOLD + ChatColor.BOLD.toString() + "Shop Master")) {
			event.getClicker().performCommand("shop");
		} else if (event.getNPC().getName().equals(ChatColor.RED + ChatColor.BOLD.toString() + "Rewarder")) {
			event.getClicker().openInventory(NightUtils.getRewarderInventory());
		} else if (event.getNPC().getName().equals(ChatColor.AQUA + ChatColor.BOLD.toString() + "Donate Help")) {
			event.getClicker().openInventory(NightUtils.getDonatorInventory());
		} else if (event.getNPC().getName().equals(ChatColor.GOLD + ChatColor.BOLD.toString() + "Rune Vendor")) {
			event.getClicker().openInventory(NightUtils.getRuneVendor());
		} else if (event.getNPC().getName().equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "News Man")) {
			event.getClicker().openInventory(NightUtils.getNewsMan());
		}

		if (event.getNPC().getName().equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Repairer")) {
			if (!(repairerlist.contains(event.getClicker()))) {
				repairerlist.add(event.getClicker());

				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &5&lRepairer", "", "&5Hey there! I'm the repairer!",
						"&5Would you like me to repair all your items for &a$20000?",
						"&5Everything you want repaired must be in your inventory!",
						"&7Please answer in chat with &5yes&7 or &5no", "",
						"&8&l&m-------------------------------------");

				for (int i = 0; i < runes.size(); i++) {
					event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}
				new BukkitRunnable() {
					@Override
					public void run() {
						try {
							repairerlist.remove(event.getClicker());

						} catch (Exception e) {

						}
					}
				}.runTaskLater(plugin, 20 * 30);

			} else {
				event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &7Please finish your current request!"));
			}
		}
		if (event.getNPC().getName().equals(ChatColor.RED + ChatColor.BOLD.toString() + "Hell Vendor")) {
			if (!(hellvendorlist.contains(event.getClicker()))) {
				hellvendorlist.add(event.getClicker());

				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &c&lHell Vendor", "", "&cHey there! I'm the &cHell Vendor!",
						"&7Would you like to buy a pair of &cHell Boots&7 for &a$2500?",
						"&7Please note that you need Hell Boots to survive in the hell world!", "",
						"&7Please answer in chat with &cyes&7 or &cno", "",
						"&8&l&m-------------------------------------");

				for (int i = 0; i < runes.size(); i++) {
					event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}
				new BukkitRunnable() {

					@Override
					public void run() {
						try {
							hellvendorlist.remove(event.getClicker());

						} catch (Exception e) {

						}
					}
				}.runTaskLater(plugin, 20 * 30);

			} else {
				event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &7Please finish your current request with the hell vendor!!"));
			}
		}
		if (event.getNPC().getName().equals(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Moon Vendor")) {
			if (!(moonvendorlist.contains(event.getClicker()))) {
				moonvendorlist.add(event.getClicker());

				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &e&lMoon Vendor", "", "&cHey there! I'm the &eMoon Vendor!",
						"&7Would you like to buy a &eSpace Helmet&7 for &a$2500?",
						"&7Please note that you need a &eSpace Helmet&7 to survive on the moon!", "",
						"&7Please answer in chat with &eyes&7 or &eno", "",
						"&8&l&m-------------------------------------");

				for (int i = 0; i < runes.size(); i++) {
					event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}
				new BukkitRunnable() {
					@Override
					public void run() {
						try {
							moonvendorlist.remove(event.getClicker());

						} catch (Exception e) {

						}
					}
				}.runTaskLater(plugin, 20 * 30);

			} else {
				event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &7Please finish your current request with the moon vendor!!"));
			}
		}
		if (event.getNPC().getName().equals(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Healer")) {
			if (!(healerlist.contains(event.getClicker()))) {
				healerlist.add(event.getClicker());

				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &4&lHealer", "", "&5Hey there! I'm the server healer!",
						"&5Would you like me to heal you to full health for &a$500?", "",
						"&7Please answer in chat with &5yes&7 or &5no", "",
						"&8&l&m-------------------------------------");

				for (int i = 0; i < runes.size(); i++) {
					event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}
				new BukkitRunnable() {

					@Override
					public void run() {
						try {
							healerlist.remove(event.getClicker());

						} catch (Exception e) {

						}
					}
				}.runTaskLater(plugin, 20 * 30);

			} else {
				event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &7Please finish your current request!"));
			}
		}
		if (event.getNPC().getName().equals(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Tool Vendor")) {
			if (!(toolvendorlist.contains(event.getClicker()))) {
				toolvendorlist.add(event.getClicker());

				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &e&lTool Vendor", "", "&eHey there! I'm the tool vendor!",
						"&7Would you like me to give you a random pickaxe for &a$5000?", "",
						"&7Please answer in chat with &eyes&7 or &eno", "",
						"&8&l&m-------------------------------------");

				for (int i = 0; i < runes.size(); i++) {
					event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}
				new BukkitRunnable() {
					@Override
					public void run() {
						try {
							toolvendorlist.remove(event.getClicker());

						} catch (Exception e) {

						}
					}
				}.runTaskLater(plugin, 20 * 30);

			} else {
				event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &7Please finish your current request with the Tool Vendor!"));
			}
		}
		if (event.getNPC().getName().equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "World Master")) {
			if (!(masterdialog.contains(event.getClicker().getName()))) {
				masterdialog.add(event.getClicker().getName());

				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &5&lWorld Master", "", "&7Hey there! I'm the &5World Master!",
						"&7The entry fee for this world is &5$1000&7, would you like to continue?", "",
						"&7Please answer in chat with &5yes&7 or &5no", "",
						"&8&l&m-------------------------------------");

				for (int i = 0; i < runes.size(); i++) {
					event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}

			} else {
				event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &7Please finish your current request with the &5&lWorld Master"));
			}
		}
		if (event.getNPC().getName().equals(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "World Master")) {
			if (!(remasterdialog.contains(event.getClicker().getName()))) {
				remasterdialog.add(event.getClicker().getName());

				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &d&lWorld Master", "", "&7Hey there! I'm the &dWorld Master!",
						"&7The entry fee for this world is &5$3000&7, would you like to continue?", "",
						"&7Please answer in chat with &dyes&7 or &dno", "",
						"&8&l&m-------------------------------------");

				for (int i = 0; i < runes.size(); i++) {
					event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}

			} else {
				event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &7Please finish your current request with the &d&lWorld Master"));
			}
		}
		if (event.getNPC().getName().equals(ChatColor.GRAY + ChatColor.BOLD.toString() + "World Master")) {
			if (!(normaldialog.contains(event.getClicker().getName()))) {
				normaldialog.add(event.getClicker().getName());

				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &7&lWorld Master", "", "&7Hey there! I'm the &7&lWorld Master!",
						"&7The entry fee for this world is &7&l$3000&7, would you like to continue?", "",
						"&7Please answer in chat with &7&lyes&7 or &7&lno", "",
						"&8&l&m-------------------------------------");

				for (int i = 0; i < runes.size(); i++) {
					event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}

			} else {
				event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &7Please finish your current request with the &7&lWorld Master"));
			}
		}
		if (event.getNPC().getName().equals(ChatColor.YELLOW + ChatColor.BOLD.toString() + "World Master")) {
			if (!(moonmasterdialog.contains(event.getClicker().getName()))) {
				moonmasterdialog.add(event.getClicker().getName());

				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &e&lWorld Master", "", "&7Hey there! I'm the &eWorld Master!",
						"&7The entry fee for this world is &e$5000&7, would you like to continue?", "",
						"&7Please answer in chat with &eyes&7 or &eno", "",
						"&8&l&m-------------------------------------");

				for (int i = 0; i < runes.size(); i++) {
					event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}

			} else {
				event.getClicker().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &7Please finish your current request with the &e&lWorld Master"));
			}
		}
	}

	@EventHandler
	public void onMove(InventoryClickEvent event) {

		if (event.getCurrentItem() == null) {
			return;
		}
		if (event.getWhoClicked() == null) {
			return;
		}
		if (event.getCurrentItem().getType() == Material.NETHER_STAR) {
			Inventory inventory = event.getInventory();
			Player player = (Player) event.getWhoClicked();
			ItemStack clicked = event.getCurrentItem();
			if (inventory.getName().equals(plugin.getRuneVendor().getName())) {
				event.setCancelled(true);
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Speed")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 1000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 1000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofspeed");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}

				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Strength")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 1000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 1000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofstrength");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Vampirism")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 5000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 5000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofvampirism");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Healing")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 1000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 1000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofhealing");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Repellant")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 10000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 10000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofrepellant");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Flaming Arrows")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 20000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 20000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofflamingarrows");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Haste")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 7500) {
						NightUtils.econ.withdrawPlayer(player.getName(), 7500);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofhaste");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Incineration")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 30000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 30000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofincineration");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Paralyzing")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 40000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 40000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofparalyzing");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Lightning")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 10000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 10000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeoflightning");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Blinding")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 12500) {
						NightUtils.econ.withdrawPlayer(player.getName(), 12500);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofblinding");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Barraging")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 25000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 25000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofbarraging");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Regeneration")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 20000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 20000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofregeneration");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Leaping")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 10000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 10000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofleaping");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Wither")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 25000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 25000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofwither");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				if (clicked.getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Crippling")) {
					if (NightUtils.econ.getBalance(player.getName()) >= 25000) {
						NightUtils.econ.withdrawPlayer(player.getName(), 25000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"runes give " + player.getName() + " runeofcrippling");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Successfully bought "
										+ clicked.getItemMeta().getDisplayName()));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
					}
				}
				return;
			}

		}

		if (plugin.frozen) {
			if (event.getWhoClicked() != plugin.controller) {
				event.setCancelled(true);
				event.getWhoClicked().sendMessage(ChatColor.RED + "A full server freeze is in effect");
				return;
			}
		}
		Player player = (Player) event.getWhoClicked(); // The player that
		if (nointeract.contains(player)) {
			event.setCancelled(true);
		}

		if (vpn.contains(event.getWhoClicked())) {
			event.setCancelled(true);
			String hostname = hostnames.get(event.getWhoClicked().getName());
			String asn = asns.get(event.getWhoClicked().getName());
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lSolar&7&lConnector", "",
					"&5&lSolar&7&lPvP &cdoes not let you play on a VPN!", "", "&5Your hostname: &c" + hostname,
					"&5Your ASN: &c" + asn, "", "&7You are getting this error because you are connecting with a VPN!",
					"&7Your hostname or ASN was unknown to us and was flagged as dangerous!",
					"&7Please log off your VPN and you will be able to play normally! If you think this is in error please do &5/vpnappeal",
					"&cYou will automatically be kicked in 60 seconds", "",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}
		} // clicked the item
		ItemStack clicked = event.getCurrentItem(); // The item that was clicked
		Inventory inventory = event.getInventory(); // The inventory that was
													// clicked in
		// sheep 45k, pig 45k, cow 75k, wolf 50k, blaze 850k, irongolem 1.5mil,
		// zombie 250k, zombiepig 200k, spider 75k, cavespider 150k ,skeleton
		// 300k, creeper 550k, enderman 600k,

		if (clicked.getItemMeta().getDisplayName()
				.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Adventure World!")) {
			event.setCancelled(true);
			Location loc = new Location(Bukkit.getWorld("Map"), 3579.500, 189.00, -5403.500);
			player.teleport(loc);
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lAdventure World", "",
					"&5&lSolar&7&lPvP &7welcomes you to the &5&lAdventure world!", "",
					"&5You can start playing right away!",
					"&7Our server has many different custom worlds, and you're in one of them! You can freely switch between worlds with the     &5/switchworld &7command or by walking through the portals at spawn! Items, Factions, Money, etc are universal through all worlds! Have fun exploring!",
					"", "&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}
			plugin.getConfig().set(player.getName() + "firstjoin", "yes");
			plugin.saveConfig();
			firstjoin.remove(player);
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
		} else if (clicked.getItemMeta().getDisplayName()
				.equals(ChatColor.GRAY + ChatColor.BOLD.toString() + "Normal World!")) {
			event.setCancelled(true);
			Location loc = new Location(Bukkit.getWorld("Normal"), 598.56, 186.0, -244.221);
			player.teleport(loc);
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &7&lNormal World", "",
					"&5&lSolar&7&lPvP &7welcomes you to the &7&lNormal world!", "",
					"&7You can start playing right away!",
					"&7Our server has many different custom worlds, and you're in one of them! You can freely switch between worlds with the     &7/switchworld &7command or by walking through the portals at spawn! ",
					"", "&7Items, Factions, Money, etc are universal through all worlds! Have fun exploring!", "",

					"", "&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}
			plugin.getConfig().set(player.getName() + "firstjoin", "yes");
			plugin.saveConfig();
			firstjoin.remove(player);
			player.removePotionEffect(PotionEffectType.INVISIBILITY);

		}

		if (clicked.getItemMeta().getDisplayName()
				.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Spawners")) {
			event.setCancelled(true);
			player.openInventory(plugin.getSpawnerShop());
		}

		if (inventory.getName().equals(plugin.getSpawnerShop().getName())) {
			event.setCancelled(true);
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Cow Spawner")) {
				if (plugin.econ.has(player, 75000)) {
					try {
						plugin.econ.withdrawPlayer(player, 75000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ss give " + player.getName() + " cow 1");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &aSuccessfully purchased!"));
					} catch (Exception e) {

					}

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cInsufficient funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Sheep Spawner")) {
				if (plugin.econ.has(player, 45000)) {
					try {
						plugin.econ.withdrawPlayer(player, 45000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ss give " + player.getName() + " sheep 1");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &aSuccessfully purchased!"));
					} catch (Exception e) {

					}

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cInsufficient funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Enderman Spawner")) {
				if (plugin.econ.has(player, 600000)) {
					try {
						plugin.econ.withdrawPlayer(player, 600000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"ss give " + player.getName() + " enderman 1");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &aSuccessfully purchased!"));
					} catch (Exception e) {

					}

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cInsufficient funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Creeper Spawner")) {
				if (plugin.econ.has(player, 550000)) {
					try {
						plugin.econ.withdrawPlayer(player, 550000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ss give " + player.getName() + " creeper 1");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &aSuccessfully purchased!"));
					} catch (Exception e) {

					}

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cInsufficient funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Skeleton Spawner")) {
				if (plugin.econ.has(player, 300000)) {
					try {
						plugin.econ.withdrawPlayer(player, 300000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"ss give " + player.getName() + " skeleton 1");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &aSuccessfully purchased!"));
					} catch (Exception e) {

					}

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cInsufficient funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Zombie Pigman Spawner")) {
				if (plugin.econ.has(player, 200000)) {
					try {
						plugin.econ.withdrawPlayer(player, 200000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"ss give " + player.getName() + " pigzombie 1");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &aSuccessfully purchased!"));
					} catch (Exception e) {

					}

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cInsufficient funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Zombie Spawner")) {
				if (plugin.econ.has(player, 250000)) {
					try {
						plugin.econ.withdrawPlayer(player, 250000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ss give " + player.getName() + " zombie 1");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &aSuccessfully purchased!"));
					} catch (Exception e) {

					}

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cInsufficient funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Pig Spawner")) {
				if (plugin.econ.has(player, 45000)) {
					try {
						plugin.econ.withdrawPlayer(player, 45000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ss give " + player.getName() + " pig 1");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &aSuccessfully purchased!"));
					} catch (Exception e) {

					}

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cInsufficient funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Wolf Spawner")) {
				if (plugin.econ.has(player, 50000)) {
					try {
						plugin.econ.withdrawPlayer(player, 50000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ss give " + player.getName() + " wolf 1");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &aSuccessfully purchased!"));
					} catch (Exception e) {

					}

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cInsufficient funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Blaze Spawner")) {
				if (plugin.econ.has(player, 850000)) {
					try {
						plugin.econ.withdrawPlayer(player, 850000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ss give " + player.getName() + " blaze 1");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &aSuccessfully purchased!"));
					} catch (Exception e) {

					}

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cInsufficient funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "IronGolem Spawner")) {
				if (plugin.econ.has(player, 1500000)) {
					try {
						plugin.econ.withdrawPlayer(player, 1500000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"ss give " + player.getName() + " irongolem 1");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &aSuccessfully purchased!"));
					} catch (Exception e) {

					}

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cInsufficient funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Spider Spawner")) {
				if (plugin.econ.has(player, 75000)) {
					try {
						plugin.econ.withdrawPlayer(player, 75000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ss give " + player.getName() + " spider 1");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &aSuccessfully purchased!"));
					} catch (Exception e) {

					}

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cInsufficient funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Cave Spider Spawner")) {
				if (plugin.econ.has(player, 150000)) {
					try {
						plugin.econ.withdrawPlayer(player, 150000);
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"ss give " + player.getName() + " cavespider 1");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &aSuccessfully purchased!"));
					} catch (Exception e) {

					}

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cInsufficient funds!"));
				}
			}

		}

		if (inventory.getName().equals(NightUtils.getHelpInventory().getName())) {
			event.setCancelled(true);
		}
		if (inventory.getName().equals(NightUtils.switcherinv.getName())) {
			event.setCancelled(true);
		}
		if (inventory.getName().equals(NightUtils.getRuneVendor().getName())) {
			event.setCancelled(true);
		}
		if (inventory.getName().equals(NightUtils.getRewarderInventory().getName())) {
			event.setCancelled(true);
		}

		if (inventory.getName().equals(NightUtils.getDonatorInventory().getName())) {
			event.setCancelled(true);
		}

		/*
		 * if (CheckIP.getCNNObject()) {
		 * 
		 * 
		 * Inventory inv = Bukkit.createInventory(null, 9, "CNN News");
		 * ArrayList<String> lorelist = new ArrayList<String>();
		 * 
		 * ItemStack newsone = new ItemStack(Material.BOOK); ItemMeta
		 * newsonemeta = newsone.getItemMeta();
		 * newsonemeta.setDisplayName(ChatColor.DARK_PURPLE +
		 * CheckIP.getCnnTitle(1)); lorelist.add(ChatColor.GRAY +
		 * "Click for article"); newsonemeta.setLore(lorelist);
		 * newsone.setItemMeta(newsonemeta); lorelist.clear();
		 * 
		 * ItemStack newstwo = new ItemStack(Material.BOOK); ItemMeta
		 * newstwometa = newstwo.getItemMeta();
		 * newstwometa.setDisplayName(ChatColor.DARK_PURPLE +
		 * CheckIP.getCnnTitle(2)); lorelist.add(ChatColor.GRAY +
		 * "Click for article"); newstwometa.setLore(lorelist);
		 * newstwo.setItemMeta(newstwometa); lorelist.clear();
		 * 
		 * ItemStack news3 = new ItemStack(Material.BOOK); ItemMeta news3meta =
		 * news3.getItemMeta(); news3meta.setDisplayName(ChatColor.DARK_PURPLE +
		 * CheckIP.getCnnTitle(3)); lorelist.add(ChatColor.GRAY +
		 * "Click for article"); news3meta.setLore(lorelist);
		 * news3.setItemMeta(news3meta); lorelist.clear();
		 * 
		 * ItemStack news4 = new ItemStack(Material.BOOK); ItemMeta news4meta =
		 * news4.getItemMeta(); news4meta.setDisplayName(ChatColor.DARK_PURPLE +
		 * CheckIP.getCnnTitle(4)); lorelist.add(ChatColor.GRAY +
		 * "Click for article"); news4meta.setLore(lorelist);
		 * news4.setItemMeta(news4meta); lorelist.clear();
		 * 
		 * ItemStack news5 = new ItemStack(Material.BOOK); ItemMeta news5meta =
		 * news5.getItemMeta(); news5meta.setDisplayName(ChatColor.DARK_PURPLE +
		 * CheckIP.getCnnTitle(5)); lorelist.add(ChatColor.GRAY +
		 * "Click for article"); news5meta.setLore(lorelist);
		 * news5.setItemMeta(news5meta); lorelist.clear();
		 * 
		 * ItemStack news6 = new ItemStack(Material.BOOK); ItemMeta news6meta =
		 * news6.getItemMeta(); news6meta.setDisplayName(ChatColor.DARK_PURPLE +
		 * CheckIP.getCnnTitle(6)); lorelist.add(ChatColor.GRAY +
		 * "Click for article"); news6meta.setLore(lorelist);
		 * news6.setItemMeta(news6meta); lorelist.clear();
		 * 
		 * ItemStack news7 = new ItemStack(Material.BOOK); ItemMeta news7meta =
		 * news7.getItemMeta(); news7meta.setDisplayName(ChatColor.DARK_PURPLE +
		 * CheckIP.getCnnTitle(7)); lorelist.add(ChatColor.GRAY +
		 * "Click for article"); news7meta.setLore(lorelist);
		 * news7.setItemMeta(news7meta); lorelist.clear();
		 * 
		 * ItemStack news8 = new ItemStack(Material.BOOK); ItemMeta news8meta =
		 * news8.getItemMeta(); news8meta.setDisplayName(ChatColor.DARK_PURPLE +
		 * CheckIP.getCnnTitle(8)); lorelist.add(ChatColor.GRAY +
		 * "Click for article"); news8meta.setLore(lorelist);
		 * news8.setItemMeta(news8meta); lorelist.clear();
		 * 
		 * ItemStack news9 = new ItemStack(Material.BOOK); ItemMeta news9meta =
		 * news9.getItemMeta(); news9meta.setDisplayName(ChatColor.DARK_PURPLE +
		 * CheckIP.getCnnTitle(9)); lorelist.add(ChatColor.GRAY +
		 * "Click for article"); news9meta.setLore(lorelist);
		 * news9.setItemMeta(news9meta); lorelist.clear();
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * inv.setItem(0, newsone); inv.setItem(1, newstwo); inv.setItem(2,
		 * news3); inv.setItem(3, news4); inv.setItem(4, news5); inv.setItem(5,
		 * news6); inv.setItem(6, news7); inv.setItem(7, news8); inv.setItem(8,
		 * news9); player.openInventory(inv); } else {
		 * player.sendMessage(ChatColor.RED +
		 * "This is unavailable at the moment"); event.setCancelled(true); }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * if
		 * (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE
		 * + CheckIP.getCnnTitle(1))) { List<String> runes =
		 * Arrays.asList("&8&l&m------------------------------------------",
		 * "                          &5&lSolar&7&lNews", "", ChatColor.GRAY +
		 * CheckIP.getCnnDesc(1), "", ChatColor.GRAY + CheckIP.getCnnLink(1),
		 * 
		 * "&8&l&m------------------------------------------");
		 * event.setCancelled(true); for (int i = 0; i < runes.size(); i++) {
		 * event.getWhoClicked().sendMessage(ChatColor.
		 * translateAlternateColorCodes('&', runes.get(i))); }
		 * event.setCancelled(true);
		 * 
		 * }
		 * 
		 * if
		 * (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE
		 * + CheckIP.getCnnTitle(2))) { List<String> runes =
		 * Arrays.asList("&8&l&m------------------------------------------",
		 * "                          &5&lSolar&7&lNews", "", ChatColor.GRAY +
		 * CheckIP.getCnnDesc(2), "", ChatColor.GRAY + CheckIP.getCnnLink(2),
		 * 
		 * "&8&l&m------------------------------------------");
		 * event.setCancelled(true); for (int i = 0; i < runes.size(); i++) {
		 * event.getWhoClicked().sendMessage(ChatColor.
		 * translateAlternateColorCodes('&', runes.get(i))); }
		 * event.setCancelled(true);
		 * 
		 * } if
		 * (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE
		 * + CheckIP.getCnnTitle(3))) { List<String> runes =
		 * Arrays.asList("&8&l&m-------------------------------------",
		 * "                      &5&lSolar&7&lNews", "", ChatColor.GRAY +
		 * CheckIP.getCnnDesc(3), "", ChatColor.GRAY + CheckIP.getCnnLink(3),
		 * 
		 * "&8&l&m-------------------------------------");
		 * event.setCancelled(true); for (int i = 0; i < runes.size(); i++) {
		 * event.getWhoClicked().sendMessage(ChatColor.
		 * translateAlternateColorCodes('&', runes.get(i))); }
		 * event.setCancelled(true);
		 * 
		 * } if
		 * (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE
		 * + CheckIP.getCnnTitle(4))) { List<String> runes =
		 * Arrays.asList("&8&l&m-------------------------------------",
		 * "                      &5&lSolar&7&lNews", "", ChatColor.GRAY +
		 * CheckIP.getCnnDesc(4), "", ChatColor.GRAY + CheckIP.getCnnLink(4),
		 * 
		 * "&8&l&m-------------------------------------");
		 * event.setCancelled(true); for (int i = 0; i < runes.size(); i++) {
		 * event.getWhoClicked().sendMessage(ChatColor.
		 * translateAlternateColorCodes('&', runes.get(i))); }
		 * event.setCancelled(true);
		 * 
		 * } if
		 * (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE
		 * + CheckIP.getCnnTitle(5))) { List<String> runes =
		 * Arrays.asList("&8&l&m-------------------------------------",
		 * "                      &5&lSolar&7&lNews", "", ChatColor.GRAY +
		 * CheckIP.getCnnDesc(5), "", ChatColor.GRAY + CheckIP.getCnnLink(5),
		 * 
		 * "&8&l&m-------------------------------------");event.setCancelled(
		 * true); for (int i = 0; i < runes.size(); i++) {
		 * event.getWhoClicked().sendMessage(ChatColor.
		 * translateAlternateColorCodes('&', runes.get(i))); }
		 * event.setCancelled(true);
		 * 
		 * } if
		 * (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE
		 * + CheckIP.getCnnTitle(6))) { List<String> runes =
		 * Arrays.asList("&8&l&m-------------------------------------",
		 * "                      &5&lSolar&7&lNews", "", ChatColor.GRAY +
		 * CheckIP.getCnnDesc(6), "", ChatColor.GRAY + CheckIP.getCnnLink(6),
		 * 
		 * "&8&l&m-------------------------------------");
		 * event.setCancelled(true); for (int i = 0; i < runes.size(); i++) {
		 * event.getWhoClicked().sendMessage(ChatColor.
		 * translateAlternateColorCodes('&', runes.get(i))); }
		 * event.setCancelled(true);
		 * 
		 * } if
		 * (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE
		 * + CheckIP.getCnnTitle(7))) { List<String> runes =
		 * Arrays.asList("&8&l&m-------------------------------------",
		 * "                      &5&lSolar&7&lNews", "", ChatColor.GRAY +
		 * CheckIP.getCnnDesc(7), "", ChatColor.GRAY + CheckIP.getCnnLink(7),
		 * 
		 * "&8&l&m-------------------------------------");
		 * event.setCancelled(true); for (int i = 0; i < runes.size(); i++) {
		 * event.getWhoClicked().sendMessage(ChatColor.
		 * translateAlternateColorCodes('&', runes.get(i))); }
		 * event.setCancelled(true);
		 * 
		 * } if
		 * (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE
		 * + CheckIP.getCnnTitle(8))) { List<String> runes =
		 * Arrays.asList("&8&l&m-------------------------------------",
		 * "                      &5&lSolar&7&lNews", "", ChatColor.GRAY +
		 * CheckIP.getCnnDesc(8), "", ChatColor.GRAY + CheckIP.getCnnLink(8),
		 * 
		 * "&8&l&m-------------------------------------");
		 * event.setCancelled(true); for (int i = 0; i < runes.size(); i++) {
		 * event.getWhoClicked().sendMessage(ChatColor.
		 * translateAlternateColorCodes('&', runes.get(i))); }
		 * event.setCancelled(true);
		 * 
		 * } if
		 * (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE
		 * + CheckIP.getCnnTitle(9))) { List<String> runes =
		 * Arrays.asList("&8&l&m-------------------------------------",
		 * "                      &5&lSolar&7&lNews", "", ChatColor.GRAY +
		 * CheckIP.getCnnDesc(9), "", ChatColor.GRAY + CheckIP.getCnnLink(9),
		 * 
		 * "&8&l&m-------------------------------------");
		 * event.setCancelled(true); for (int i = 0; i < runes.size(); i++) {
		 * event.getWhoClicked().sendMessage(ChatColor.
		 * translateAlternateColorCodes('&', runes.get(i))); }
		 * event.setCancelled(true);
		 * 
		 * }
		 */
		if (inventory.getName().equals(plugin.getRuneVendor().getName())) {
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Speed")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 1000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 1000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofspeed");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}

			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Strength")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 1000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 1000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofstrength");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Vampirism")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 5000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 5000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofvampirism");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Healing")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 1000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 1000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofhealing");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Repellant")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 10000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 10000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofrepellant");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Flaming Arrows")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 20000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 20000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofflamingarrows");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Haste")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 7500) {
					NightUtils.econ.withdrawPlayer(player.getName(), 7500);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofhaste");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Incineration")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 30000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 30000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofincineration");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Paralyzing")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 40000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 40000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofparalyzing");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Lightning")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 10000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 10000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeoflightning");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Blinding")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 12500) {
					NightUtils.econ.withdrawPlayer(player.getName(), 12500);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofblinding");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Barraging")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 25000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 25000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofbarraging");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Regeneration")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 20000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 20000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofregeneration");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Leaping")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 10000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 10000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofleaping");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Wither")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 25000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 25000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofwither");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Crippling")) {
				if (NightUtils.econ.getBalance(player.getName()) >= 25000) {
					NightUtils.econ.withdrawPlayer(player.getName(), 25000);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
							"runes give " + player.getName() + " runeofcrippling");
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Successfully bought " + clicked.getItemMeta().getDisplayName()));
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7Insufficient Funds!"));
				}
			}
		}

		if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + ChatColor.BOLD.toString() + "Undead Rank")) {

			List<String> undead = Arrays.asList("&8&l&m-------------------------------------",
					"                      &7&lUndead Help", "", "&7Undead is the first buyable rank on our server!",
					"&7Price: &a$10.00 USD", "&5&nHere are the commands you get::", " ",
					"&5/stack &8- &7Compact items into stacks", "&5/feed &8- &7Replenish your hunger",
					"&5/tpahere &8- &7Request teleports to you", "&5/back &8- &7Go back to your previous location",
					"&5/clear &8- &7Clear your inventory", "&5/enderchest &8- &7access your enderchest remotely",
					"&5/pv 1 &8- &7You get 1 full playervault!", "&5/nick &8- &7Nickname yourself anythin!",
					"&5&nHere are some extras you get:", " ", "&7&lSet 5 /homes", "&a&l100 McMMO Credits",
					"&a&l15k EXP", "&8&lUndead &7&lPrefix in chat/above head", "&e&l3x Vote Key", "&b&l2x Super Key",
					"&a&l$20,000 Cash", "&a&l5x Creeper Egg", "&8&l8 Stacks Obsidian",
					"&a&lAbility to silktouch spawners!", "&d&lAbility to show items in chat with @i@!",
					"&d&lList 2 items in AH", " ", "&5&nHere is the undead kit:", " ", "&7Full Diamond Armor Set",
					"&7Sharpness 1 Fire 1 Diamond Sword", "&7Efficiency 2 Unbreaking 2 Diamond Pick", "&71x Bow",
					"&716x Enderpearl", "&7128x Steak", "&7128x TnT", "&72x Elite Enchantment Book",
					"&74x Strength 2 Potions", "&74x Speed 2 Potions", "&748x Diamond", "&71x Bow", " ",
					"&7&lYou can purchase this rank now with &5&l/BUY!", "",
					"&8&l&m-------------------------------------");

			for (int i = 0; i < undead.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', undead.get(i)));
			}

		}
		if (clicked.getItemMeta().getDisplayName()
				.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Reaper Rank")) {

			List<String> undead = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lReaper Help", "", "&7Reaper is the second buyable rank on our server!",
					"&7Price: &a$20.00 USD", "&5&nHere are the commands you get::", " ",
					"&5/stack &8- &7Compact items into stacks", "&5/feed &8- &7Replenish your hunger",
					"&5/tpahere &8- &7Request teleports to you", "&5/back &8- &7Go back to your previous location",
					"&5/clear &8- &7Clear your inventory", "&5/enderchest &8- &7access your enderchest remotely",
					"&5/pv 2 &8- &7You get 2 full playervaults!", "&5/nick &8- &7Nickname yourself anything!",
					"&5/near &8- &7View players near you", "&5/tntfill &8- &7Fill nearby dispensers with TnT",
					"&5/heal &8- &7Heal yourself (with delay)", "&5/workbench &8- &7Open Virtual crafting table",
					"&5/hat &8- &7Wear items on your head", "&5&nHere are some extras you get:", " ",
					"&7&lSet 5 /homes", "&5&lReaper &7&lPrefix in chat/above head", "&a&l200 McMMO Credits",
					"&a&l30k EXP", "&e&l5x Vote Key", "&b&l4x Super Key", "&e&l1x Ultra Key", "&a&l$40,000 Cash",
					"&a&l10x Creeper Egg", "&8&l10 Stacks Obsidian", "&a&lAbility to silktouch spawners!",
					"&d&lAbility to show items in chat!", "&d&lList 4 items in AH", " ", "&5&nHere is the reaper kit:",
					"&7(You also get kits from previous ranks)", " ", "&7Prot 2 Unbreaking 1 Diamond Armor Set",
					"&7Sharpness 2 Fire 1 Diamond Sword", "&7Efficiency 2 Unbreaking 2 Diamond Pick", "&7Power 1 Bow",
					"&716x Enderpearl", "&7128x Steak", "&7128x TnT", "&73x Elite Enchantment Book",
					"&71x Ultimate Enchantment Book", "&74x Strength 2 Potions", "&74x Speed 2 Potions",
					"&748x Diamond", " ", "&7&lYou can purchase this rank now with &5&l/BUY!", "",
					"&8&l&m-------------------------------------");

			for (int i = 0; i < undead.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', undead.get(i)));
			}

		}
		if (clicked.getItemMeta().getDisplayName()
				.equals(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Demon Rank")) {

			List<String> undead = Arrays.asList("&8&l&m-------------------------------------",
					"                      &d&lDemon Help", "", "&7Reaper is the third buyable rank on our server!",
					"&7Price: &a$30.00 USD", "&5&nHere are the commands you get::", " ",
					"&5/stack &8- &7Compact items into stacks", "&5/feed &8- &7Replenish your hunger",
					"&5/tpahere &8- &7Request teleports to you", "&5/back &8- &7Go back to your previous location",
					"&5/clear &8- &7Clear your inventory", "&5/enderchest &8- &7access your enderchest remotely",
					"&5/pv 3 &8- &7You get 3 full playervaults!", "&5/nick &8- &7Nickname yourself anything!",
					"&5/near &8- &7View players near you", "&5/tntfill &8- &7Fill nearby dispensers with TnT",
					"&5/heal &8- &7Heal yourself (with delay)", "&5/workbench &8- &7Open Virtual crafting table",
					"&5/fix hand &8- &7Fix the item in your hand", "&5/ptime &8- &7Set your local time",
					"&5&l/waterwalk &8- &7Ability to walk on water!", "&5&nHere are some extras you get:", " ",
					"&7&lset 11 /homes", "&d&lDemon &7&lPrefix in chat/above head", "&a&l350 McMMO Credits",
					"&a&l50k EXP", "&e&l8x Vote Key", "&b&l5x Super Key", "&e&l3x Ultra Key", "&a&l$80,000 Cash",
					"&a&l20x Creeper Egg", "&8&l15 Stacks Obsidian", "&a&lAbility to silktouch spawners!",
					"&d&lAbility to show items in chat!", "&d&lList 6 items in AH",

					" ", "&5&nHere is the demon kit:", "&7(You also get kits from previous ranks)", " ",
					"&7Prot 3 Unbreaking 3 Diamond Armor Set", "&7Sharpness 5 Unbreaking 1 Diamond Sword",
					"&7Sharpness 4 Looting 2 Diamond Sword", "&7Efficiency 4 Unbreaking 2 Diamond Pick",
					"&7Power 4 Unbreaking 2 Bow", "&732x Enderpearl", "&7128x Steak", "&7186x TnT",
					"&73x Elite Enchantment Book", "&72x Ultimate Enchantment Book", "&71x Legendary Enchantment Book",
					"&75x Strength 2 Potions", "&75x Speed 2 Potions", "&764x Diamond", "&71x ItemNameTag", " ",
					"&7&lYou can purchase this rank now with &5&l/BUY!", "",
					"&8&l&m-------------------------------------");

			for (int i = 0; i < undead.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', undead.get(i)));
			}

		}
		if (clicked.getItemMeta().getDisplayName().equals(ChatColor.RED + ChatColor.BOLD.toString() + "Hades Rank")) {

			List<String> undead = Arrays.asList("&8&l&m-------------------------------------",
					"                      &d&lDemon Help", "", "&7Reaper is the fourth buyable rank on our server!",
					"&7Price: &a$40.00 USD", "&5&nHere are the commands you get::", " ",
					"&5/stack &8- &7Compact items into stacks", "&5/feed &8- &7Replenish your hunger",
					"&5/tpahere &8- &7Request teleports to you", "&5/back &8- &7Go back to your previous location",
					"&5/clear &8- &7Clear your inventory", "&5/enderchest &8- &7access your enderchest remotely",
					"&5/pv 3 &8- &7You get 3 full playervaults!", "&5/nick &8- &7Nickname yourself anything!",
					"&5/near &8- &7View players near you", "&5/tntfill &8- &7Fill nearby dispensers with TnT",
					"&5/heal &8- &7Heal yourself (with delay)", "&5/workbench &8- &7Open Virtual crafting table",
					"&5/fix hand &8- &7Fix the item in your hand", "&5/ptime &8- &7Set your local time",
					"&5/fix all &8- &7Fix everything in your inv", "&5&l/waterwalk &8- &7Ability to walk on water!",
					"&5&l/fly &8- &7Ability to fly!", "&5&l/speed &8- &7Set your walk/fly speed! (Not in pvp)", "",

					"&5&nHere are some extras you get:", " ", "&7&lset 15 /homes",
					"&c&lHades &7&lPrefix in chat/above head", "&a&l750 McMMO Credits", "&a&l100k EXP",
					"&e&l12x Vote Key", "&b&l7x Super Key", "&e&l5x Ultra Key", "&5&l1x Nightmare Key",
					"&a&l$250,000 Cash", "&a&l40x Creeper Egg", "&8&l15 Stacks Obsidian",
					"&a&lAbility to silktouch spawners!", "&d&lAbility to show items in chat!",
					"&6&lBetter chance to get STAFF", "&9&lJoin server when full", "&d&lList 10 items in AH",

					" ", "&5&nHere is the Hades kit:", "&7(You also get kits from previous ranks)", " ",
					"&7Prot 4 Unbreaking 3 Diamond Armor Set", "&7Sharpness 5 Unbreaking 3 FireAspect 3 Diamond Sword",
					"&7Sharpness 5 Looting 2 Diamond Sword", "&7Efficiency 4 Unbreaking 2 Diamond Pick",
					"&7Power 5 Unbreaking 3 Flame 1 Bow", "&732x Enderpearl", "&7128x Steak", "&7186x TnT",
					"&73x Elite Enchantment Book", "&72x Ultimate Enchantment Book", "&72x Legendary Enchantment Book",
					"&79x Strength 2 Potions", "&79x Speed 2 Potions", "&764x Diamond", "&71x ItemNameTag",
					"&71x Black Scroll", "&71x White Scroll", " ", "&7&lYou can purchase this rank now with &5&l/BUY!",
					"", "&8&l&m-------------------------------------");

			for (int i = 0; i < undead.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', undead.get(i)));
			}

		}
		if (clicked.getItemMeta().getDisplayName().equals(ChatColor.RED + "How to Donate?")) {

			List<String> undead = Arrays.asList("&8&l&m-------------------------------------",
					"                       &c&lHow To Donate", "",
					"&7Donating uses &5REAL LIFE MONEY!&7 You can purchase ranks, items, gkits, and more with various payment methods",
					"&7We currently offer &bVisa, MasterCard, and Paypal&7 as payment methods",
					"&7To purchase a rank, visit our webstore at &5shop.frozenheart.us&7 or type /buy and select the item you want to buy and follow the link it gives you",
					"&7After you go to our web store, fill in the checkout information, check the box of the payment method you want to use, then click checkout",
					"", "&7Our system is automatic and you will recieve what you paid for in a few minutes",
					"&7If there is a problem with your order, you can contact us directly at &5ocfinancesmc@gmail.com&7 or on skype at &5live:kaveenkk912",
					" ", "&7&lPurchase a rank today with &5&l/buy&7&l!", "",
					"&8&l&m-------------------------------------");

			for (int i = 0; i < undead.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', undead.get(i)));
			}

		}
		if (event.getInventory().getName().equals(NightUtils.getRewarderInventory().getName())) {
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Free 2k EXP")) {
				long initial = System.nanoTime();

				if (plugin.getConfig().get(player.getName().toString() + "exp") == null) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7You have recieved your reward"));
					player.giveExp(2000);
					plugin.getConfig().set(player.getName().toString() + "exp", initial);

					plugin.saveConfig();
				} else {
					plugin.reloadConfig();
					long playertime = plugin.getConfig().getLong(player.getName().toString() + "exp");
					long elapsed = (playertime - initial) / 1000000000;

					if (Math.abs(elapsed) >= 86400) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7You have recieved your reward"));
						player.giveExp(2000);
						plugin.getConfig().set(player.getName().toString() + "exp", System.nanoTime());
						plugin.saveConfig();
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &cYou have already redeemed this reward for today"));
					}
				}

			}

			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Free 5k Cash")) {
				long initial = System.nanoTime();

				if (plugin.getConfig().get(player.getName().toString() + "cash") == null) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7You have recieved your reward"));
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + player.getName() + " 5000");
					plugin.getConfig().set(player.getName().toString() + "cash", initial);

					plugin.saveConfig();
				} else {
					plugin.reloadConfig();
					long playertime = plugin.getConfig().getLong(player.getName().toString() + "cash");
					long elapsed = (playertime - initial) / 1000000000;

					if (Math.abs(elapsed) >= 86400) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7You have recieved your reward"));
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + player.getName() + " 5000");
						plugin.getConfig().set(player.getName().toString() + "cash", System.nanoTime());
						plugin.saveConfig();
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &cYou have already redeemed this reward for today"));
					}
				}

			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "2 Free Super Keys")) {
				long initial = System.nanoTime();

				if (plugin.getConfig().get(player.getName().toString() + "key") == null) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7You have recieved your reward"));
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crate givekey " + player.getName() + " Super 2");
					plugin.getConfig().set(player.getName().toString() + "key", initial);

					plugin.saveConfig();
				} else {
					plugin.reloadConfig();
					long playertime = plugin.getConfig().getLong(player.getName().toString() + "key");
					long elapsed = (playertime - initial) / 1000000000;

					if (Math.abs(elapsed) >= 172800) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7You have recieved your reward"));
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"crate givekey " + player.getName() + " Super 2");
						plugin.getConfig().set(player.getName().toString() + "key", System.nanoTime());
						plugin.saveConfig();
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &cYou have already redeemed this reward for the time period allocated"));
					}
				}

			}

			if (clicked.getItemMeta().getDisplayName()
					.equals(ChatColor.RED + ChatColor.BOLD.toString() + "Goodies Bundle")) {
				long initial = System.nanoTime();

				if (plugin.getConfig().get(player.getName().toString() + "goodies") == null) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7You have recieved your reward"));
					try {
						Kit esskit = new Kit("once", plugin.essentials);
						User user = plugin.essentials.getUser(player);
						esskit.expandItems(user);
					} catch (Exception e) {

					}
					plugin.getConfig().set(player.getName().toString() + "goodies", initial);

					plugin.saveConfig();
				} else {
					plugin.reloadConfig();
					long playertime = plugin.getConfig().getLong(player.getName().toString() + "goodies");
					long elapsed = (playertime - initial) / 1000000000;

					if (Math.abs(elapsed) >= 1209600) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7You have recieved your reward"));
						try {
							Kit esskit = new Kit("once", plugin.essentials);
							User user = plugin.essentials.getUser(player);
							esskit.expandItems(user);
						} catch (Exception e) {

						}
						plugin.getConfig().set(player.getName().toString() + "goodies", System.nanoTime());
						plugin.saveConfig();
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &cYou have already redeemed this reward for today"));
					}
				}

			}
			if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Free Enchanting Books")) {
				long initial = System.nanoTime();

				if (plugin.getConfig().get(player.getName().toString() + "books") == null) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &7You have recieved your reward"));
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit enchantsfree " + player.getName());
					plugin.getConfig().set(player.getName().toString() + "books", initial);

					plugin.saveConfig();
				} else {
					plugin.reloadConfig();
					long playertime = plugin.getConfig().getLong(player.getName().toString() + "books");
					long elapsed = (playertime - initial) / 1000000000;

					if (Math.abs(elapsed) >= 172800) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &7You have recieved your reward"));
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit enchantsfree " + player.getName());
						plugin.getConfig().set(player.getName().toString() + "books", System.nanoTime());
						plugin.saveConfig();
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &cYou have already redeemed this reward for today"));
					}
				}

			}
		}
		if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Factions Help")) {
			List<String> factions = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lFactions Help", "",
					"&7With factions, you can group up with players, claim land for your faction and build your own base. You fight over land, declare war on each other, and try to become the best faction!",
					"&7You can overclaim the land of other factions only if their land points are greater than their power points!",
					"&7&nHere are the basic commands:", " ", "&5/f create &8- &7Create a faction",
					"&5/f invite <player> &8- &7Invite a player to your faction",
					"&5/f kick <player> &8- &7Kick a player from your faction",
					"&5/f claim &8- &7Claim land for your faction", "&5/f who <faction> &8- &7Get faction info",
					"&5/f sethome &8- &7Set your faction base", "&5/f home &8- &7Return to your faction base",
					"&7More help and tons more commands can be found by doing &5/f", "",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < factions.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', factions.get(i)));
			}
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Custom Enchants Help")) {
			List<String> ce = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lEnchants Help", "",
					"&5&lSolar&7&lPvP &7has custom enchantments! However, you cannot obtain custom enchantments from the enchantment table, you must use the enchanter to get enchantment books!! For more information on the enchanter type &5/help enchanter&7. We have a large amount of custom enchants, you can view what each one does when you hover over its individual enchantment book! The custom enchantments contain various PvP and PvE perks never before seen in vanilla minecraft! Start building your god kit now using the enchanter and the tinkerer!",
					"", "&5How does it work?",
					"&7To apply custom enchantments to a piece of armor or weapon, simply drag the enchantment book on top of it and click. Enchantment books have randomized success rates which are viewable on the lore of the book. If you fail an enchant, the piece of armor will break unless protected by a white scroll!",
					"", "&8&l&m-------------------------------------");

			for (int i = 0; i < ce.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', ce.get(i)));
			}
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Tinkerer Help")) {
			List<String> tinkerer = Arrays.asList("&8&l&m-------------------------------------",
					"                     &5&lTinkerer Help", "",
					"&7The tinkerer allows you to trade unwanted custom enchantment books for dust. Access the tinkerer menu by typing &5/tinkerer &7then put in your unwanted books and click the accept trade button. You will then get primordal dust which you must right click to reveal what type of usable dust you recieved, you can then use this dust on enchantment books to increase its success rate!",
					"", "&8&l&m-------------------------------------");

			for (int i = 0; i < tinkerer.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', tinkerer.get(i)));
			}
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Enchanter Help")) {
			List<String> enchanter = Arrays.asList("&8&l&m-------------------------------------",
					"                     &5&lEnchanter Help", "",
					"&7The enchanter allows you to use your EXP to buy enchantment books. You can buy Simple, Unique, Elite, Ultimate, and Legendary enchantment books, the higher the level of the book, the more EXP you have to buy it for. After purchasing the book, you must right click it to see what enchantment you get. You can then apply these enchanted books to an armor/weapon piece to recieve its respective custom enchantment",
					"&7You can access the enchanter menu using &5/enchanter &7or by clicking on the Enchanter NPC at spawn",
					"", "&8&l&m-------------------------------------");
			for (int i = 0; i < enchanter.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', enchanter.get(i)));
			}
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Main Help")) {
			List<String> main = Arrays.asList("&8&l&m-------------------------------------",
					"                           &5&lMain Help", "",
					"&5&lSolar&7&lPvP &7is a factions server! However, we're not like other factions servers. We have several worlds for you to play in! The worlds are the nether, the overworld, and two custom worlds! All of the world borders for each world are set to 5k, it seems small but it's big considering the large amount of worlds you have to explore! Each different map has something to offer, for example, the maps Adventure and Mystical are not regular maps! They have custom generated terrain, dungeons, and tons of places for you to explore! You can freely switch between worlds at any time when you are in a safezone with &5/switchworld",
					"&7We also offer custom enchantments, lag-less gameplay, auction house, a virtual shop, voting, crate keys, and so much more!",
					"", "&7We are currenly hiring staff! You must be active, and you will be picked by us", "",
					"&7This server has &5GKits! &7You can purchase gkits from &5/buy &7or win them from crates!",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < main.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.get(i)));
			}
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Runes Help")) {
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lRunes Help", "",
					"&5&lSolar&7&lPvP &7has custom runes! Runes are special items that you can right click for specific abilities! You can view it's ability in the lore!",
					"", "&5How do they work?",
					"&7To use a rune, right click it! You may NOT use another rune while one is already active! You can get a rune from the &e&lRune Vendor &7at spawn or at &5/warp arena",
					"", "&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}

		} else if (clicked.getItemMeta().getDisplayName()
				.equals(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Adventure World")) {
			// if (!(plugin.isSwitching.contains(player.getName()))) {
			// TODO WORLDGUARD
			plugin.isSwitching.add(player.getName());
			event.setCancelled(true);
			Location old = player.getLocation();
			locations.put(player.getName(), old);

			Location newloc = new Location(Bukkit.getWorld("Hub"), 1662.416, 67, 367.448);
			player.teleport(newloc);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/citizens reload");
			try {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "citizens reload");
			} catch (Exception e) {

			}
			new BukkitRunnable() {
				@Override
				public void run() {
					List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
							"                      &5&lAdventure Shuttle", "",
							"&7Please talk to the &5&lWorld Master&7 NPC to gain access! Right click him and type 'yes' if you want to switch into the world! If you can't see the NPC, please type 'where are the npcs' in chat or click the signs!",
							"", "", "&8&l&m-------------------------------------");
					for (int i = 0; i < runes.size(); i++) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
					}

					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/citizens reload");
					try {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "citizens reload");
					} catch (Exception e) {

					}
				}
			}.runTaskLater(plugin, 60);
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lAdventure Shuttle", "",
					"&7Please talk to the &5&lWorld Master&7 to gain access into &5&lAdventure", "", "",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}

			// } else {
			// player.sendMessage(ChatColor.translateAlternateColorCodes('&',
			// "&5&lSolar&7&lPvP &8&l> &cYou are already in the midst of
			// switching worlds!"));
			// }

		} else if (clicked.getItemMeta().getDisplayName()
				.equals(ChatColor.YELLOW + ChatColor.BOLD.toString() + "The Moon")) {
			// if (!(plugin.isSwitching.contains(player.getName()))) {
			// TODO WORLDGUARD
			plugin.isSwitching.add(player.getName());
			event.setCancelled(true);
			Location old = player.getLocation();
			locations.put(player.getName(), old);

			Location newloc = new Location(Bukkit.getWorld("Hub"), 1601.216, 113.0, 603.502);
			player.teleport(newloc);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/citizens reload");
			try {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "citizens reload");
			} catch (Exception e) {

			}
			new BukkitRunnable() {
				@Override
				public void run() {
					List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
							"                      &e&lMoon Shuttle", "",
							"&7Please talk to the &e&lWorld Master&7 NPC to gain access! Right click him and type 'yes' if you want to switch into the world! If you can't see the NPC, please type 'where are the npcs' in chat or click the signs!",
							"", "", "&8&l&m-------------------------------------");
					for (int i = 0; i < runes.size(); i++) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
					}

					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/citizens reload");
					try {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "citizens reload");
					} catch (Exception e) {

					}
				}
			}.runTaskLater(plugin, 60);
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &e&lMoon Shuttle", "",
					"&7Please talk to the &e&lWorld Master&7 to gain access into &e&lThe Moon", "", "",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}

			// } else {
			// player.sendMessage(ChatColor.translateAlternateColorCodes('&',
			// "&5&lSolar&7&lPvP &8&l> &cYou are already in the midst of
			// switching worlds!"));
			// }

		} else if (clicked.getItemMeta().getDisplayName()
				.equals(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Reminiscence World")) {
			// if (!(plugin.isSwitching.contains(player.getName()))) {
			// TODO WORLDGUARD
			plugin.isSwitching.add(player.getName());
			event.setCancelled(true);
			Location old = player.getLocation();
			locations.put(player.getName(), old);

			Location newloc = new Location(Bukkit.getWorld("Hub"), 901.565, 83.0, 1462.512);
			player.teleport(newloc);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/citizens reload");
			try {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "citizens reload");
			} catch (Exception e) {

			}
			new BukkitRunnable() {
				@Override
				public void run() {
					List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
							"                      &d&lReminiscence Shuttle", "",
							"&7Please talk to the &d&lWorld Master&7 NPC to gain access! Right click him and type 'yes' if you want to switch into the world! If you can't see the NPC, please type 'where are the npcs' in chat or click the signs!",
							"", "", "&8&l&m-------------------------------------");
					for (int i = 0; i < runes.size(); i++) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
					}

					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/citizens reload");
					try {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "citizens reload");
					} catch (Exception e) {

					}
				}
			}.runTaskLater(plugin, 60);
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &d&lReminiscence Shuttle", "",
					"&7Please talk to the &d&lWorld Master&7 to gain access into the &d&lReminiscence World", "", "",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}

			// } else {
			// player.sendMessage(ChatColor.translateAlternateColorCodes('&',
			// "&5&lSolar&7&lPvP &8&l> &cYou are already in the midst of
			// switching worlds!"));
			// }
			//

		} else if (clicked.getItemMeta().getDisplayName()
				.equals(ChatColor.GRAY + ChatColor.BOLD.toString() + "Normal World")) {
			// if (!(plugin.isSwitching.contains(player.getName()))) {
			// TODO WORLDGUARD
			plugin.isSwitching.add(player.getName());
			event.setCancelled(true);
			Location old = player.getLocation();
			locations.put(player.getName(), old);

			Location newloc = new Location(Bukkit.getWorld("Hub"), 1941.723, 126.0, 405.410);
			player.teleport(newloc);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/citizens reload");
			try {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "citizens reload");
			} catch (Exception e) {

			}
			new BukkitRunnable() {
				@Override
				public void run() {
					List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
							"                      &7&lNormal Shuttle", "",
							"&7Please talk to the &7&lWorld Master&7 NPC to gain access! Right click him and type 'yes' if you want to switch into the world! If you can't see the NPC, please type 'where are the npcs' in chat or click the signs!",
							"", "", "&8&l&m-------------------------------------");
					for (int i = 0; i < runes.size(); i++) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
					}

					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/citizens reload");
					try {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "citizens reload");
					} catch (Exception e) {

					}
				}
			}.runTaskLater(plugin, 60);
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &7&lNormal Shuttle", "",
					"&7Please talk to the &7&lWorld Master&7 to gain access into the &7&lNormal World", "", "",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}

			// } else {
			// player.sendMessage(ChatColor.translateAlternateColorCodes('&',
			// "&5&lSolar&7&lPvP &8&l> &cYou are already in the midst of
			// switching worlds!"));
			// }
			//

		} else if (clicked.getItemMeta().getDisplayName()
				.equals(ChatColor.RED + ChatColor.BOLD.toString() + "Hell World")) {
			event.setCancelled(true);
			Location loc = new Location(Bukkit.getWorld("world_nether"), 0.354, 87.0, 0.526);
			player.teleport(loc);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/citizens reload");
			try {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "citizens reload");
			} catch (Exception e) {

			}
			new BukkitRunnable() {
				@Override
				public void run() {

					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/citizens reload");
					try {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "citizens reload");
					} catch (Exception e) {

					}
				}
			}.runTaskLater(plugin, 60);
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &c&lHell World", "",
					"&7You have been sent to the Hell world! This is a special world! To survive here, you must be wearing Hell boots! Buy hell boots from the nearest Boots Vendor! It is at the hell spawn!",
					"&cYou may switch out of this world anytime with /switchworld", "",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent event) {

		if (plugin.frozen) {
			if (event.getPlayer() != plugin.controller) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "A full server freeze is in effect");
				return;
			}
		}
		Block block = event.getClickedBlock();
		if (block.getState() instanceof Sign) {
			Sign sign = (Sign) block.getState();
			if (!(sign.getLines().equals(null))) {
				if (sign.getLine(0).equals(ChatColor.RED + "Click Here")) {
					try {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/citizens reload");
					} catch (Exception e) {

					}
					try {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "citizens reload");
					} catch (Exception e) {

					}

				}
				if (sign.getLine(0).equals(ChatColor.RED + "Kav_")) {

					List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
							"                      &c&lKav_", "",
							"&7Kav_ (Also known as Kaveen) is currently working on getting his Masters in software engineering. He makes and runs minecraft servers in his free time and when bored in class. He is 22 years old. His skype is live:kaveenkk912. You may not add his skype for useless purposes. His spoken languages are; English, French. Kav_ is the main owner of the server as well as the server developer, as he is fluent in Java and various other programming and scripting languages.",
							"", "&8&l&m-------------------------------------");
					for (int i = 0; i < runes.size(); i++) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
					}

				} else if (sign.getLine(0).equals(ChatColor.RED + "janmeijer97")) {
					List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
							"                      &c&lJanmeijer97", "",
							"&7Janmeijer97 is a co-owner of the server. He speaks Dutch and English, and is 20 years old. His skype is jan.meijer88.",
							"", "&8&l&m-------------------------------------");
					for (int i = 0; i < runes.size(); i++) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
					}
				} else if (sign.getLine(0).equals(ChatColor.RED + "DedicatedRam")) {
					List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
							"                      &c&lDedicatedRam", "",
							"&7DedicatedRam is a co-owner of the server, as well as a sponsor. He speaks Japanese, German, Spanish, English, Albanian and is 17 years old. His skype is undisclosed. He has also been known as Linux or LinuxKernel. Degree in electrical engineering. Loves his GMC Truck.",
							"", "&8&l&m-------------------------------------");
					for (int i = 0; i < runes.size(); i++) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
					}
				} else if (sign.getLine(0).equals(ChatColor.GOLD + "Ripsnt")) {
					List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
							"                      &6&lRipsnt", "",
							"&7Ripsnt is the head-admin of the server. He is 16 years old. Ripsnt handles most of the in-game activity as well as the arrangement of lower staff.",
							"", "&8&l&m-------------------------------------");
					for (int i = 0; i < runes.size(); i++) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
					}
				} else if (sign.getLine(0).equals(ChatColor.GREEN + "Popularplayzmc")) {
					List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
							"                      &aPopularPlayzMC", "",
							"&7PopularPlayzMC is the latest addition to our staff team. 14 years old.", "",
							"&8&l&m-------------------------------------");
					for (int i = 0; i < runes.size(); i++) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
					}
				} else if (sign.getLine(0).equals(ChatColor.DARK_PURPLE + "Swayy_")) {
					List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
							"                      &5Swayy_", "",
							"&7Swayy_ is the latest and only addition to our build team, he is 15, white, male, and considers himself to be the Nigerian Prince",
							"", "&8&l&m-------------------------------------");
					for (int i = 0; i < runes.size(); i++) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
					}
				} else if (sign.getLine(0).equals(ChatColor.GOLD + "BlueSlayer98")) {
					List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
							"                      &6BlueSlayer98", "",
							"&7'Why cant i do commands here!?!??!' - &6BlueSlayer98", "",
							"&8&l&m-------------------------------------");
					for (int i = 0; i < runes.size(); i++) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
					}
				}

			}

		}

		if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
				.equals(ChatColor.translateAlternateColorCodes('&', "&a&lBook of Help &7&o(Right Click)"))) {
			event.getPlayer().openInventory(plugin.getHelpInventory());
			return;
		}
		if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
				.equals(ChatColor.translateAlternateColorCodes('&', "&b&lDonate Help &7&o(Right Click)"))) {
			event.getPlayer().openInventory(plugin.getDonatorInventory());
			return;
		}
		if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
				.equals(ChatColor.translateAlternateColorCodes('&', "&5&lWorld Switcher &7&o(Right Click)"))) {
			if (plugin.isSwitching.contains(event.getPlayer().getName())) {
				event.getPlayer().sendMessage(ChatColor.RED + "You are already in the midst of switching worlds!");
				return;
			} else {
				event.getPlayer().openInventory(plugin.getWorldSwitcher());
				return;
			}
		}

		if (vpn.contains(event.getPlayer())) {
			event.setCancelled(true);
			String hostname = hostnames.get(event.getPlayer().getName());
			String asn = asns.get(event.getPlayer().getName());
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lSolar&7&lConnector", "",
					"&5&lSolar&7&lPvP &cdoes not let you play on a VPN!", "", "&5Your hostname: &c" + hostname,
					"&5Your ASN: &c" + asn, "", "&7You are getting this error because you are connecting with a VPN!",
					"&7Your hostname or ASN was unknown to us and was flagged as dangerous!",
					"&7Please log off your VPN and you will be able to play normally! If you think this is in error please do &5/vpnappeal",
					"&cYou will automatically be kicked in 60 seconds", "",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}
		}
		Player player = event.getPlayer();
		Inventory inventory = event.getPlayer().getInventory();
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "$5,000")) {
				ItemStack i = event.getPlayer().getItemInHand();
				if (player.getInventory().getItemInHand().getAmount() == 1) {
					inventory.removeItem(player.getInventory().getItemInHand());
				}

				player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount() - 1);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + event.getPlayer().getName() + " 5000");
			}
			if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "$10,000")) {
				ItemStack i = event.getPlayer().getItemInHand();
				if (player.getInventory().getItemInHand().getAmount() == 1) {
					inventory.removeItem(player.getInventory().getItemInHand());
				}

				player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount() - 1);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + event.getPlayer().getName() + " 10000");
			}
			if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "$15,000")) {
				ItemStack i = event.getPlayer().getItemInHand();
				if (player.getInventory().getItemInHand().getAmount() == 1) {
					inventory.removeItem(player.getInventory().getItemInHand());
				}

				player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount() - 1);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + event.getPlayer().getName() + " 15000");
			}
			if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "$20,000")) {
				ItemStack i = event.getPlayer().getItemInHand();
				if (player.getInventory().getItemInHand().getAmount() == 1) {
					inventory.removeItem(player.getInventory().getItemInHand());
				}

				player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount() - 1);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + event.getPlayer().getName() + " 20000");
			}
			if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "$50,000")) {
				ItemStack i = event.getPlayer().getItemInHand();
				if (player.getInventory().getItemInHand().getAmount() == 1) {
					inventory.removeItem(player.getInventory().getItemInHand());
				}

				player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount() - 1);
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + event.getPlayer().getName() + " 50000");
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (firstjoin.contains(event.getPlayer())) {
			List<String> runes1 = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lSolar&7&lConnector", "",
					"&7We have noticed it is your first time joining! Please select a world to start!", "",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < runes1.size(); i++) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes1.get(i)));
			}
			Location loc = new Location(Bukkit.getWorld("Hub"), 1376.495, 71, 391.448);
			event.getPlayer().teleport(loc);
			event.getPlayer().openInventory(plugin.firstjoinswitcher);
			event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&5&lSolar&7&lPvP &8&l> &cPlease choose a world first in the GUI!"));

		}

		if (plugin.frozen) {
			if (event.getPlayer() != plugin.controller) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "A full server freeze is in effect");
				return;
			}
		}
		Player player = event.getPlayer();
		if (player.getWorld().getName().equals("world_nether")) {

			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();

			if (!(x > -11.30 && x < 12.30 && z > -11.30 && z < 12.30)) {

				if (player.getInventory().getBoots() == null) {

					if (hell.get(player) == null || !(hell.get(player))) {
						hell.put(player, true);
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
						player.setFireTicks(100);

						player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&c&lHell World"), ChatColor
								.translateAlternateColorCodes('&', "&cYou are burning! Put on hell boots or die!"));

						new BukkitRunnable() {
							@Override
							public void run() {
								hell.put(player, false);
							}
						}.runTaskLater(plugin, 20 * 4);
					}

				}
				if (player.getInventory().getBoots().getItemMeta().getDisplayName().equals(null)) {
					if (hell.get(player) == null || !(hell.get(player))) {
						hell.put(player, true);
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
						player.setFireTicks(100);

						player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&c&lHell World"), ChatColor
								.translateAlternateColorCodes('&', "&cYou are burning! Put on hell boots or die!"));

						new BukkitRunnable() {

							@Override
							public void run() {
								hell.put(player, false);
							}
						}.runTaskLater(plugin, 20 * 4);
					}

				} else if (!(player.getInventory().getBoots().getItemMeta().getDisplayName()
						.equals(ChatColor.RED + ChatColor.BOLD.toString() + "Hell Boots"))) {
					if (hell.get(player) == null || !(hell.get(player))) {
						hell.put(player, true);
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
						player.setFireTicks(100);

						player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&c&lHell World"), ChatColor
								.translateAlternateColorCodes('&', "&cYou are burning! Put on hell boots or die!"));

						new BukkitRunnable() {
							@Override
							public void run() {
								hell.put(player, false);
							}
						}.runTaskLater(plugin, 20 * 4);
					}

				}

			}

		}

		if (player.getWorld().getName().equals("Moon")) {

			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();

			if (!(x < 15.196 && x > -20.737 && z > 42.209 && z < 75.529)) {

				if (player.getInventory().getHelmet() == null) {

					if (moon.get(player) == null || !(moon.get(player))) {
						moon.put(player, true);
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0));
						player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 2));
						player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 120, 1));

						player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&e&lThe Moon"), ChatColor
								.translateAlternateColorCodes('&', "&eYou are suffocating! Put on a space helmet!"));

						new BukkitRunnable() {
							@Override
							public void run() {
								moon.put(player, false);
							}
						}.runTaskLater(plugin, 20 * 4);
					}

				}
				if (player.getInventory().getHelmet().getItemMeta().getDisplayName().equals(null)) {
					if (moon.get(player) == null || !(moon.get(player))) {
						moon.put(player, true);
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0));
						player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 2));
						player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 120, 1));
						player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&e&lThe Moon"), ChatColor
								.translateAlternateColorCodes('&', "&eYou are suffocating! Put on a space helmet!"));

						new BukkitRunnable() {
							@Override
							public void run() {
								moon.put(player, false);
							}
						}.runTaskLater(plugin, 20 * 4);
					}

				} else if (!(player.getInventory().getHelmet().getItemMeta().getDisplayName()
						.equals(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Space Helmet"))) {
					if (moon.get(player) == null || !(moon.get(player))) {
						moon.put(player, true);
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0));
						player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 2));
						player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 120, 1));

						player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&e&lThe Moon"), ChatColor
								.translateAlternateColorCodes('&', "&eYou are suffocating! Put on a space helmet!"));

						new BukkitRunnable() {
							@Override
							public void run() {
								moon.put(player, false);
							}
						}.runTaskLater(plugin, 20 * 4);
					}

				}

			}

		}

		if (vpn.contains(event.getPlayer())) {
			Location loc = new Location(Bukkit.getWorld("Hub"), 1333.50, 72.0, 957.50);
			event.getPlayer().teleport(loc);
			String hostname = hostnames.get(event.getPlayer().getName());
			String asn = asns.get(event.getPlayer().getName());
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lSolar&7&lConnector", "",
					"&5&lSolar&7&lPvP &cdoes not let you play on a VPN!", "", "&5Your hostname: &c" + hostname,
					"&5Your ASN: &c" + asn, "", "&7You are getting this error because you are connecting with a VPN!",
					"&7Your hostname or ASN was unknown to us and was flagged as dangerous!",
					"&7Please log off your VPN and you will be able to play normally! If you think this is in error please do &5/vpnappeal",
					"&cYou will automatically be kicked in 60 seconds", "",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}
		}

		if (shuttle.contains(player)) {
			player.setVelocity(player.getLocation().getDirection().multiply(0.1));
			player.setVelocity(new Vector(player.getVelocity().getX(), 3, player.getVelocity().getZ()));

		}
		if (adventureshuttle.contains(player.getName())) {
			player.setVelocity(player.getLocation().getDirection().multiply(0.0));
			player.setVelocity(new Vector(player.getVelocity().getX(), 4, player.getVelocity().getZ()));
			Bukkit.getWorld("Hub").createExplosion(player.getLocation(), 1.0F);
		}
		if (plugin.isSwitching.contains(player.getName())) {
			if (player.getLocation().getY() <= 63) {
				try {
					Location loc = locations.get(player.getName());
					player.teleport(loc);
					plugin.isSwitching.remove(player.getName());
					locations.remove(player.getName());
					masterdialog.remove(player.getName());
					moonmasterdialog.remove(player.getName());
					remasterdialog.remove(player.getName());
				} catch (Exception e) {
					Bukkit.broadcastMessage(e.toString());
				}
			}

		}

		if (player.getWorld().getName().equals("Moon")) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 4));
		}

		if (player.getWorld().getName().equals("Hub")) {
			if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.REDSTONE_LAMP_ON) {
				if (!(player.getName().equals("Kav_"))) {
					player.setVelocity(player.getLocation().getDirection().multiply(4.5));
					player.setVelocity(new Vector(player.getVelocity().getX(), 1.5, player.getVelocity().getZ()));
				}
			}
			if (player.getLocation().getY() <= 26.0) {
				Location loc = new Location(Bukkit.getWorld("Hub"), 1333.634, 72.0, 957.632);
				player.teleport(loc);
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou may not leave the hub! You have to choose a world first! Walk through a portal or use the world selector in your inventory!"));
			}

			if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.BEDROCK) {
				Location loc = new Location(Bukkit.getWorld("Map"), 3579.500, 189.00, -5403.500);
				player.teleport(loc);
				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &5&lAdventure World", "",
						"&5&lSolar&7&lPvP &7welcomes you to the &5&lAdventure world!", "",
						"&5You can start playing right away!",
						"&7Our server has many different custom worlds, and you're in one of them! You can freely switch between worlds with the     &5/switchworld &7command or by walking through the portals at spawn! Items, Factions, Money, etc are universal through all worlds! Have fun exploring!",
						"", "&8&l&m-------------------------------------");
				for (int i = 0; i < runes.size(); i++) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}

			}
			if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
				Location loc = new Location(Bukkit.getWorld("Reminiscence"), 4.475, 203.0, -914.37);
				player.teleport(loc);
				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &d&lReminiscence World", "",
						"&5&lSolar&7&lPvP &7welcomes you to the &d&lReminiscence world!", "",
						"&dYou can start playing right away!",
						"&7Our server has many different custom worlds, and you're in one of them! You can freely switch between worlds with the     &d/switchworld &7command or by walking through the portals at spawn! ",
						"", "&7Items, Factions, Money, etc are universal through all worlds! Have fun exploring!", "",
						"&dBlocks in this world reset 6 hours after broken/placed! This world is mainly used for temporary events!",
						"", "&8&l&m-------------------------------------");
				for (int i = 0; i < runes.size(); i++) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}

			}
			if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.TNT) {
				Location loc = new Location(Bukkit.getWorld("Normal"), 598.56, 186.0, -244.221);
				player.teleport(loc);
				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &7&lNormal World", "",
						"&5&lSolar&7&lPvP &7welcomes you to the &7&lNormal world!", "",
						"&7You can start playing right away!",
						"&7Our server has many different custom worlds, and you're in one of them! You can freely switch between worlds with the     &7/switchworld &7command or by walking through the portals at spawn! ",
						"", "&7Items, Factions, Money, etc are universal through all worlds! Have fun exploring!", "",

						"", "&8&l&m-------------------------------------");
				for (int i = 0; i < runes.size(); i++) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}

			}
			if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SANDSTONE) {
				Location loc = new Location(Bukkit.getWorld("Hub"), 1271.44, 82, 896.30);
				player.teleport(loc);
				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &e&lThe Moon", "",
						"&7We are getting your moon shuttle ready! Please wait!", "",
						"&8&l&m-------------------------------------");
				for (int i = 0; i < runes.size(); i++) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}

				new BukkitRunnable() {

					@Override
					public void run() {
						player.setVelocity(player.getLocation().getDirection().multiply(0.1));
						player.setVelocity(new Vector(player.getVelocity().getX(), 2.0, player.getVelocity().getZ()));
						shuttle.add(player);
						try {
							player.setAllowFlight(true);
							player.setFlying(true);
						} catch (Exception e) {

						}
						List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
								"                      &e&lThe Moon", "", "&e&lBlast Off!!!!", "",
								"&8&l&m-------------------------------------");
						for (int i = 0; i < runes.size(); i++) {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
						}
						new BukkitRunnable() {
							@Override
							public void run() {
								shuttle.remove(player);
								Location loc = new Location(Bukkit.getWorld("Moon"), -2.50, 72.0, 59.50);
								player.teleport(loc);
								List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
										"                      &e&lThe Moon", "",
										"&5&lSolar&7&lPvP &7welcomes you to &e&lThe Moon", "", "",
										"&7The moon is a special world! You can only mine ore in this world, and you may not build any bases or break blocks! If you wish to do so, please visit another world to build/break with &e/switchworld! &7You need to be wearing a &e&lSpace Helmet&7 to be able to breathe on the moon! Otherwise you'll suffocate!",
										"", "&8&l&m-------------------------------------");
								try {
									player.setAllowFlight(false);
									player.setFlying(false);
								} catch (Exception e) {

								}
								for (int i = 0; i < runes.size(); i++) {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
								}

							}
						}.runTaskLater(plugin, 200);

					}

				}.runTaskLater(plugin, 100);
			}

		}

	}

	@EventHandler
	public void onSwitch(PlayerChangedWorldEvent event) {

	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if (plugin.frozen) {
			if (event.getPlayer() != plugin.controller) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "A full server freeze is in effect");
				return;
			}
		}
		if (vpn.contains(event.getPlayer())) {
			event.setCancelled(true);
			String hostname = hostnames.get(event.getPlayer().getName());
			String asn = asns.get(event.getPlayer().getName());
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lSolar&7&lConnector", "",
					"&5&lSolar&7&lPvP &cdoes not let you play on a VPN!", "", "&5Your hostname: &c" + hostname,
					"&5Your ASN: &c" + asn, "", "&7You are getting this error because you are connecting with a VPN!",
					"&7Your hostname or ASN was unknown to us and was flagged as dangerous!",
					"&7Please log off your VPN and you will be able to play normally! If you think this is in error please do &5/vpnappeal",
					"&cYou will automatically be kicked in 60 seconds", "",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}
		}
		Block block = event.getBlockPlaced();
		if (event.getPlayer().getWorld().getName().equals("Moon")) {
			if (!(event.getPlayer().getName().equals("Kav_"))) {
				event.setCancelled(true);
			}
		}
		if (event.getPlayer().getWorld().getName().equals("Hub")) {
			if (!(event.getPlayer().getName().equals("Kav_"))) {
				event.setCancelled(true);
			}
		}

		if (event.getPlayer().getWorld().getName().equals("Reminiscence")) {
			if (!(NightUtils.bypass.containsKey(event.getPlayer()))) {

				if (block.getType() == Material.SAND) {
					block.setType(Material.SANDSTONE);
				} else if (block.getType() == Material.GRAVEL) {
					block.setType(Material.CLAY);
				}
				new BukkitRunnable() {
					@Override
					public void run() {

						block.getLocation().getBlock().setType(Material.AIR);
						placed.add(block);
					}
				}.runTaskLater(plugin, 20 * plugin.remdelay);
			}
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.OBSIDIAN) {
			if (event.getPlayer().getItemInHand().getItemMeta().getLore()
					.contains(ChatColor.GREEN + "Obsidian Destroyer VIII")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.GREEN + "Obsidian Destroyer VIII")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.GREEN + "Obsidian Destroyer VII")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.GREEN + "Obsidian Destroyer VI")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.GREEN + "Obsidian Destroyer V")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.GREEN + "Obsidian Destroyer IV")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.GREEN + "Obsidian Destroyer III")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.GREEN + "Obsidian Destroyer II")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.GREEN + "Obsidian Destroyer I")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.GREEN + "Obsidian Destroyer VIIII")) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cThe Obsidian Destroyer enchantment is disabled due to a major bug!"));
			}
		}
		if (plugin.frozen) {
			if (event.getPlayer() != plugin.controller) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "A full server freeze is in effect");
				return;
			}
		}
		if (vpn.contains(event.getPlayer())) {
			event.setCancelled(true);
			String hostname = hostnames.get(event.getPlayer().getName());
			String asn = asns.get(event.getPlayer().getName());
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lSolar&7&lConnector", "",
					"&5&lSolar&7&lPvP &cdoes not let you play on a VPN!", "", "&5Your hostname: &c" + hostname,
					"&5Your ASN: &c" + asn, "", "&7You are getting this error because you are connecting with a VPN!",
					"&7Your hostname or ASN was unknown to us and was flagged as dangerous!",
					"&7Please log off your VPN and you will be able to play normally! If you think this is in error please do &5/vpnappeal",
					"&cYou will automatically be kicked in 60 seconds", "",
					"&8&l&m-------------------------------------");
			for (int i = 0; i < runes.size(); i++) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}
		}
		Block block = event.getBlock();
		Material blockm = block.getType();
		Byte data = block.getData();
		if (event.getPlayer().getWorld().getName().equals("Reminiscence")) {
			if (!(NightUtils.bypass.containsKey(event.getPlayer()))) {
				new BukkitRunnable() {
					@Override
					public void run() {
						if (!(placed.contains(event.getBlock()))) {
							if (!(blockm == Material.STAINED_CLAY || blockm == Material.WOOL
									|| blockm == Material.LEAVES || blockm == Material.WOOD_STAIRS
									|| blockm == Material.ACACIA_STAIRS || blockm == Material.BRICK_STAIRS
									|| blockm == Material.BIRCH_WOOD_STAIRS || blockm == Material.DARK_OAK_STAIRS
									|| blockm == Material.COBBLESTONE_STAIRS || blockm == Material.QUARTZ_STAIRS
									|| blockm == Material.NETHER_BRICK_STAIRS || blockm == Material.SMOOTH_STAIRS
									|| blockm == Material.RED_SANDSTONE_STAIRS || blockm == Material.LADDER
									|| blockm == Material.IRON_TRAPDOOR || blockm == Material.WOOD_DOOR
									|| blockm == Material.WOODEN_DOOR || blockm == Material.IRON_DOOR
									|| blockm == Material.ACACIA_DOOR || blockm == Material.FENCE_GATE
									|| blockm == Material.LOG || blockm == Material.LOG_2)) {

								if (blockm == Material.SAND) {
									block.getLocation().getBlock().setType(Material.SANDSTONE);
								} else if (blockm == Material.GRAVEL) {
									block.getLocation().getBlock().setType(Material.CLAY);
								} else {

									block.getLocation().getBlock().setType(blockm);
								}
							} else {

								block.getLocation().getBlock().setType(blockm);

								block.setData(data);

							}
						}
					}
				}.runTaskLater(plugin, 20 * plugin.remdelay);

			}
		}

		if (event.getPlayer().getWorld().getName().equals("Hub")) {
			if (!(event.getPlayer().getName().equals("Kav_"))) {
				event.setCancelled(true);
			}
		}
		if (event.getPlayer().getWorld().getName().equals("Moon")) {
			if (!(NightUtils.bypass.containsKey(event.getPlayer()))) {
				if (!(event.getPlayer().getName().equals("Kav"))) {
					if (!(blockm == Material.REDSTONE_BLOCK || blockm == Material.DIAMOND_ORE
							|| blockm == Material.IRON_ORE || blockm == Material.GOLD_ORE || blockm == Material.COAL_ORE
							|| blockm == Material.LAPIS_ORE || blockm == Material.EMERALD_ORE)) {
						event.setCancelled(true);
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &cYou may only mine ore on the moon!"));
					} else {

						new BukkitRunnable() {

							@Override
							public void run() {
								block.getLocation().getBlock().setType(blockm);

							}

						}.runTaskLater(plugin, plugin.moondelay * 20);

					}
				}
			}
			if (event.getPlayer().getItemInHand().getItemMeta().getLore().contains(ChatColor.YELLOW + "Detonate VIII")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.YELLOW + "Detonate VIII")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.YELLOW + "Detonate VII")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.YELLOW + "Detonate VI")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.YELLOW + "Detonate V")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.YELLOW + "Detonate IV")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.YELLOW + "Detonate III")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.YELLOW + "Detonate II")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.YELLOW + "Detonate I")
					|| event.getPlayer().getItemInHand().getItemMeta().getLore()
							.contains(ChatColor.YELLOW + "Detonate VIIII")) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cThe Detonate enchantment does not work on the moon!"));
			}
		}

	}

	@EventHandler
	public void onBoom(EntityExplodeEvent event) {
		if (event.getEntity() instanceof Creeper) {
			event.setCancelled(true);
			Entity tnt = event.getEntity().getWorld().spawn(event.getEntity().getLocation().getBlock().getLocation(),
					TNTPrimed.class);
			((TNTPrimed) tnt).setFuseTicks(0);

		}

		if (event.getLocation().getWorld().getName().equals("Moon")) {
			event.setCancelled(true);
		}
		if (event.getLocation().getWorld().getName().equals("Reminiscence")) {

			for (Block b : event.blockList()) {
				Material blockm = b.getType();
				Byte data = b.getData();

				new BukkitRunnable() {
					@Override
					public void run() {
						if (blockm != Material.TNT || blockm != Material.FIRE) {
							if (blockm != Material.SAND) {
								if (blockm != Material.GRAVEL) {
									if (!(placed.contains(b))) {
										if (!(blockm == Material.STAINED_CLAY || blockm == Material.WOOL
												|| blockm == Material.LEAVES || blockm == Material.WOOD_STAIRS
												|| blockm == Material.ACACIA_STAIRS || blockm == Material.BRICK_STAIRS
												|| blockm == Material.BIRCH_WOOD_STAIRS
												|| blockm == Material.DARK_OAK_STAIRS
												|| blockm == Material.COBBLESTONE_STAIRS
												|| blockm == Material.QUARTZ_STAIRS
												|| blockm == Material.NETHER_BRICK_STAIRS
												|| blockm == Material.SMOOTH_STAIRS
												|| blockm == Material.RED_SANDSTONE_STAIRS || blockm == Material.LADDER
												|| blockm == Material.IRON_TRAPDOOR || blockm == Material.WOOD_DOOR
												|| blockm == Material.WOODEN_DOOR || blockm == Material.IRON_DOOR
												|| blockm == Material.ACACIA_DOOR || blockm == Material.FENCE_GATE
												|| blockm == Material.LOG || blockm == Material.LOG_2)) {
											b.getLocation().getBlock().setType(blockm);
										} else {

											b.getLocation().getBlock().setType(blockm);

											b.setData(data);

										}

									}
								} else {
									b.getLocation().getBlock().setType(Material.CLAY);
								}
							} else {
								b.getLocation().getBlock().setType(Material.SANDSTONE);

							}
						}
					}
				}.runTaskLater(plugin, 20 * plugin.remdelay);
			}
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		if (event.getPlayer().hasPermission("frozenheart.quitjoinstaff")) {
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
					"&7The staff member &5" + event.getPlayer().getName() + " &7has left the game."));
		} else if (event.getPlayer().hasPermission("frozenheart.quitjoindonor")) {
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
					"&7The amazing &5" + event.getPlayer().getName() + " &7has left the game."));
		}
		if (plugin.isSwitching.contains(event.getPlayer().getName())) {
			toteleport.add(event.getPlayer().getName());
		}
		if (vpn.contains(event.getPlayer())) {
			String hostname = hostnames.get(event.getPlayer().getName());
			String asn = asns.get(event.getPlayer().getName());
			vpn.remove(event.getPlayer());
			try {
				hostnames.remove(event.getPlayer().getName(), hostname);
				asns.remove(event.getPlayer().getName(), asn);
			} catch (Exception e) {

			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {

		if (plugin.frozen) {
			if (event.getPlayer() != plugin.controller) {
				event.getPlayer().kickPlayer("Full server freeze");
				event.getPlayer().sendMessage(ChatColor.RED + "A full server freeze is in effect");
				return;
			}
		}
		event.setJoinMessage(null);

		if (event.getPlayer().hasPermission("frozenheart.quitjoinstaff")) {
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
					"&7The staff member &5" + event.getPlayer().getName() + " &7has joined the game."));
		} else if (event.getPlayer().hasPermission("frozenheart.quitjoindonor")) {
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
					"&7The amazing &5" + event.getPlayer().getName() + " &7has joined the game."));
		}

		if (toteleport.contains(event.getPlayer().getName())) {
			toteleport.remove(event.getPlayer().getName());
			event.getPlayer().teleport(locations.get(event.getPlayer().getName()));
			try {
				plugin.isSwitching.remove(event.getPlayer().getName());
				this.adventureshuttle.remove(event.getPlayer().getName());
				this.moonmasterdialog.remove(event.getPlayer().getName());
				this.masterdialog.remove(event.getPlayer().getName());
			} catch (Exception e) {
				Bukkit.broadcastMessage(e.toString());
			}
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.showPlayer(event.getPlayer());
			event.getPlayer().showPlayer(p);

		}
		String addr = event.getPlayer().getAddress().getHostString();
		new BukkitRunnable() {
			@Override
			public void run() {
				boolean bool;

				if (plugin.getConfig().getString(event.getPlayer().getName() + "firstjoin") == null) {
					bool = true;
				} else {
					bool = false;
				}

				if (bool) {
					Location loc = new Location(Bukkit.getWorld("Hub"), 1376.495, 71, 391.448);
					List<String> runes1 = Arrays.asList("&8&l&m-------------------------------------",
							"                      &5&lSolar&7&lConnector", "",
							"&7We have noticed it is your first time joining! Please select a world to start!", "",
							"&8&l&m-------------------------------------");
					for (int i = 0; i < runes1.size(); i++) {
						event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes1.get(i)));
					}
					event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000, 1));

					event.getPlayer().teleport(loc);
					new BukkitRunnable() {
						@Override
						public void run() {
							firstjoin.add(event.getPlayer());
							event.getPlayer().openInventory(NightUtils.firstjoinswitcher);
						}
					}.runTaskLater(plugin, 20);

				}

			}
		}.runTaskLater(plugin, 20);
		if (!(event.getPlayer().hasPermission("frozenheart.bypassvpn"))) {

			if (playerips.get(event.getPlayer().getName()) == null || !(playerips.get(event.getPlayer().getName())
					.equals(event.getPlayer().getAddress().getHostString()))) {

				try {
					playerips.remove(event.getPlayer().getName());
				} catch (Exception e) {

				}

				new BukkitRunnable() {
					@Override
					public void run() {
						List<String> runes1 = Arrays.asList("&8&l&m-------------------------------------",
								"                      &5&lSolar&7&lConnector", "",
								"&c&lPlease wait while we verify your connection!", "",
								"&8&l&m-------------------------------------");
						for (int i = 0; i < runes1.size(); i++) {
							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes1.get(i)));
						}
						boolean bool = CheckIP.calculate(addr);
						String hostname = CheckIP.getHostName(addr);
						String asn = CheckIP.getASN(addr);

						hostnames.put(event.getPlayer().getName(), hostname);
						asns.put(event.getPlayer().getName(), asn);

						if (bool) {
							vpn.add(event.getPlayer());
							List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
									"                      &5&lSolar&7&lConnector", "",
									"&5&lSolar&7&lPvP &cdoes not let you play on a VPN!", "",
									"&5Your hostname: &c" + hostname, "&5Your ASN: &c" + asn, "",
									"&7You are getting this error because you are connecting with a VPN!",
									"&7Your hostname or ASN was unknown to us and was flagged as dangerous!",
									"&7Please log off your VPN and you will be able to play normally! If you think this is in error please do &5/vpnappeal",
									"&cYou will automatically be kicked in 60 seconds", "",
									"&8&l&m-------------------------------------");
							for (int i = 0; i < runes.size(); i++) {
								event.getPlayer()
										.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
							}
							new BukkitRunnable() {
								@Override
								public void run() {
									List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
											"                      &5&lSolar&7&lConnector", "",
											"&5&lSolar&7&lPvP &cdoes not let you play on a VPN!", "",
											"&5Your hostname: &c" + hostname, "&5Your ASN: &c" + asn, "",
											"&7You are getting this error because you are connecting with a VPN!",
											"&7Your hostname or ASN was unknown to us and was flagged as dangerous!",
											"&7Please log off your VPN and you will be able to play normally! If you think this is in error please do &5/vpnappeal",
											"&cYou will automatically be kicked in 60 seconds", "",
											"&8&l&m-------------------------------------");
									for (int i = 0; i < runes.size(); i++) {
										event.getPlayer()
												.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
									}
								}
							}.runTaskLater(plugin, 20);

							new BukkitRunnable() {
								@Override
								public void run() {
									try {
										event.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&',
												"&5&lSolar&7&lConnector &8&l> &cYou may not log in with a VPN/Proxy! Please disable your VPN/Proxy!"));

									} catch (Exception e) {

									}
								}
							}.runTaskLater(plugin, 60 * 20);

						} else {
							List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
									"                      &5&lSolar&7&lConnector", "",
									"&aYour connection was verified! You may play now!", "",
									"&8&l&m-------------------------------------");
							for (int i = 0; i < runes.size(); i++) {
								event.getPlayer()
										.sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
							}
							try {
								playerips.put(event.getPlayer().getName(),
										event.getPlayer().getAddress().getHostString());
							} catch (Exception e) {

							}

							/*
							 * if (CheckIP.getGeoObject(event.getPlayer().
							 * getAddress(). getHostString()) &&
							 * CheckIP.getWeatherObject(event.getPlayer().
							 * getAddress ().getHostString())) { List<String>
							 * runes11 = Arrays.asList(
							 * "&8&l&m-----------------------------------------",
							 * "                      &5&lSolar&7&lConnector",
							 * "", "&7Hello &5" + event.getPlayer().getName(),
							 * "&7 We welcome you from&5 " + CheckIP.getCity() +
							 * "&7,&5 " + CheckIP.getRegion() + "&7,&5 " +
							 * CheckIP.getCountry() + "&7,&5 " +
							 * CheckIP.getContinent(),
							 * "&8&l&m-----------------------------------------",
							 * "   &5" + CheckIP.getTitle(), "",
							 * "&7Temperature: &5" + CheckIP.getTemp() + "° &7"
							 * + CheckIP.getTempUnit() + " &7or&5 " +
							 * Math.round((Long.parseLong(CheckIP.getTemp()) -
							 * 32) * 0.555555555) + "° &7C" +
							 * "        &7Description: &5" +
							 * CheckIP.getTempText(),
							 * 
							 * "&7Windchill: &5" + CheckIP.getWindChill() +
							 * CheckIP.getTempUnit() + "     &7Speed: &5" +
							 * CheckIP.getWindSpeed() + CheckIP.getSpeedUnit() +
							 * "      &7Humidity: &5" + CheckIP.getHumidity() +
							 * "%",
							 * 
							 * "",
							 * "&8&l&m-----------------------------------------"
							 * ); for (int i = 0; i < runes11.size(); i++) {
							 * event.getPlayer().sendMessage(ChatColor.
							 * translateAlternateColorCodes('&',
							 * runes11.get(i))); }
							 * 
							 * new BukkitRunnable() {
							 * 
							 * @Override public void run() {
							 * 
							 * if
							 * (plugin.getConfig().getString(event.getPlayer().
							 * getName() + "join") == null) { List<String>
							 * runes11 = Arrays.asList(
							 * "&8&l&m-----------------------------------------",
							 * "                      &5&lSolar&7&lConnector",
							 * "",
							 * "&7Hello! We have noticed that you have not joined this server before and it is your first time!"
							 * , "",
							 * "&7We are unlike other servers, to start, you must first choose a world! Walk into the portal of the world you want to play in!"
							 * , "",
							 * "&7You can freely switch between worlds any time with &5/switchworld"
							 * ,
							 * 
							 * "",
							 * "&8&l&m-----------------------------------------"
							 * ); for (int i = 0; i < runes11.size(); i++) {
							 * event.getPlayer().sendMessage(
							 * ChatColor.translateAlternateColorCodes('&',
							 * runes11.get(i))); }
							 * plugin.getConfig().set(event.getPlayer().getName(
							 * ) + "join", "yes"); plugin.saveConfig(); } }
							 * }.runTaskLater(plugin, 20 * 10);
							 * 
							 * }
							 */

						}

					}
				}.runTaskLaterAsynchronously(plugin, 40);
			}
		}
		// if (tokick) {
		// new BukkitRunnable() {
		//// @Override
		// public void run() {
		/// event.getPlayer().kickPlayer("bye");
		// }
		// }.runTaskLater(plugin, 120);
		// }

	}

	@EventHandler
	public void onOpen(InventoryOpenEvent event) {
		if (plugin.frozen) {
			if (event.getPlayer() != plugin.controller) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "A full server freeze is in effect");
				return;
			}
		}
		Player player = (Player) event.getPlayer();
		if (nointeract.contains(player)) {
			event.setCancelled(true);
		}

	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {

		if (plugin.fullocked) {
			if (!(event.getPlayer().hasPermission("frozenheart.bypasslock"))) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cChat and Commands are currently locked!"));
				return;
			}
		}

		if (plugin.frozen) {
			if (event.getPlayer() != plugin.controller) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "A full server freeze is in effect");
				return;
			}
		}

		String[] array = event.getMessage().split(" ");

		if (array[0].equalsIgnoreCase("/ban") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/banip") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/eban") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/minecraft:ban") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/maxbans:ban") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/ebanip") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/maxbans:ipban") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/maxbans:tempban") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/maxbans:tempbanip") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/tempban") && array[1].equals("Kav_")
				|| array[0].equalsIgnoreCase("/tempbanip") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/etempban") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/etempbanip") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/mute") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/muteip") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/emute") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/maxbans:mute") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/maxbans:muteip") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/kill") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/ekill") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/ipban") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/warn") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/emute") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/maxbans:ipban") && array[1].equalsIgnoreCase("99.241.78.66")
				|| array[0].equalsIgnoreCase("/ipban") && array[1].equalsIgnoreCase("99.241.78.66")
				|| array[0].equalsIgnoreCase("/banip") && array[1].equalsIgnoreCase("99.241.78.66")
				|| array[0].equalsIgnoreCase("/ebanip") && array[1].equalsIgnoreCase("99.241.78.66")
				|| array[0].equalsIgnoreCase("/essentials:banip") && array[1].equalsIgnoreCase("99.241.78.66")
				|| array[0].equalsIgnoreCase("/essentials:banip") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/essentials:kick") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/kick") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/maxbans:kick") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/ekick") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/essentials:mute") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/essentials:ban") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/essentials:kill") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/checkip") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/maxbans:checkip") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/seen") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/essentials:seen") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/eseen") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/dupeip") && array[1].equalsIgnoreCase("Kav_")
				|| array[0].equalsIgnoreCase("/stop") || array[0].equalsIgnoreCase("restart")
				|| array[0].equalsIgnoreCase("/espawn") || array[0].equalsIgnoreCase("/mangadd")
				|| array[0].equalsIgnoreCase("/mangdel") || array[0].equalsIgnoreCase("/mangaddi")
				|| array[0].equalsIgnoreCase("/manuaddp") || array[0].equalsIgnoreCase("/mangaddp")
				|| array[0].equalsIgnoreCase("/kickall") || array[0].equalsIgnoreCase("/ekickall")
				|| array[0].equalsIgnoreCase("/essentials:kickall") || array[0].equalsIgnoreCase("/e:kickall")
				|| array[0].equalsIgnoreCase("/op") || array[0].equalsIgnoreCase("/minecraft:op")
				) {

			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "Don't even think about it.");

		}

		if (event.getMessage().contains("99.241.78.66")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "No messing around with the owner's ip!");
		}

		if (event.getPlayer().getLocation().getWorld().getName().equals("Hub")) {
			if (!(plugin.bypass.containsKey(event.getPlayer()))) {
				if (!(event.getMessage().equalsIgnoreCase("/switchworld")
						|| event.getMessage().equalsIgnoreCase("/vpnappeal")
						|| event.getMessage().equalsIgnoreCase("/msg") || event.getMessage().equalsIgnoreCase("/reply")
						|| event.getMessage().equalsIgnoreCase("/r")
						|| event.getMessage().equalsIgnoreCase("/bypass"))) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cPlease choose a world first by walking into a portal, or clicking on a shard in the GUI!"));

				}
			}
		}

		if (event.getMessage().equals("/warp") || event.getMessage().equals("/warps")
				|| event.getMessage().equals("/essentials:warp")) {
			event.setCancelled(true);
			List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
					"                      &5&lSolar&7&lPvP", "",
					"&5/warp help &8- &7Donor info, basic server info/how to start!", "",
					"&5/warp crates &8- &7Crates area! Open crates!",
					"&5/warp shops &8- &7Visit the shops to buy runes or items!",
					"&5/spawn &8- &7Return to the spawn of your world",
					"&5/warp arena &8- &7Warp to the PvP Arena, you have to use switchworld to get back!", "",
					"&8&l&m-------------------------------------");

			for (int i = 0; i < runes.size(); i++) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
			}
		}

		if (event.getMessage().contains("ehome")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cPlease use /home"));
		} else if (event.getMessage().contains("essentials:home")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cPlease use /home"));
		} else if (event.getMessage().contains("essentials:ehome")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cPlease use /home"));
		} else if (event.getMessage().contains("essentials:warp")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cPlease use /warp"));
		} else if (event.getMessage().contains("ewarp")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cPlease use /warp"));
		} else if (event.getMessage().contains("essentials:ewarp")) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(
					ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cPlease use /warp"));
		}

		if (event.getMessage().equals("/warp shops")) {
			String worldname = event.getPlayer().getWorld().getName();

			if (worldname.equals("Map")) {
				event.setMessage("/warp adventureshops");

			} else if (worldname.equals("Reminiscence")) {
				event.setMessage("/warp reminiscenceshops");
			} else if (worldname.equals("Normal")) {
				event.setMessage("/warp normalshops");
			} else if (worldname.equals("Moon")) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cThat warp is not available from this world! Switch your world with /switchworld!"));
			}
		} else if (event.getMessage().equals("/warp crates")) {
			String worldname = event.getPlayer().getWorld().getName();
			if (worldname.equals("Map")) {
				event.setMessage("/warp adventurecrates");

			} else if (worldname.equals("Reminiscence")) {
				event.setMessage("/warp reminiscencecrates");
			} else if (worldname.equals("Normal")) {
				event.setMessage("/warp normalcrates");
			} else if (worldname.equals("Moon")) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cThat warp is not available from this world! Switch your world with /switchworld!"));
			}

		} else if (event.getMessage().equals("/warp help")) {
			String worldname = event.getPlayer().getWorld().getName();
			if (worldname.equals("Map")) {
				event.setMessage("/warp adventurehelp");

			} else if (worldname.equals("Reminiscence")) {
				event.setMessage("/warp reminiscencehelp");
			} else if (worldname.equals("Normal")) {
				event.setMessage("/warp normalhelp");
			} else if (worldname.equals("Moon")) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cThat warp is not available from this world! Switch your world with /switchworld!"));
			}
		} else if (event.getMessage().equals("/spawn")) {
			String worldname = event.getPlayer().getWorld().getName();
			if (worldname.equals("world_nether")) {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou may not go to spawn from this world! Use /switchworld!"));
				event.setCancelled(true);
			}
			if (worldname.equals("Map")) {
				event.setMessage("/warp adventurespawn");

			} else if (worldname.equals("Reminiscence")) {
				event.setMessage("/warp reminiscencespawn");
			} else if (worldname.equals("Normal")) {
				event.setMessage("/warp normalspawn");
			} else if (worldname.equals("Moon")) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cThat warp is not available from this world! Switch your world with /switchworld!"));
			}
		}
		String worldname = event.getPlayer().getWorld().getName();
		if (event.getMessage().equals("/warp reminiscenceshops")) {
			if (worldname.equals("Reminiscence")) {

			} else {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou must be in the same world as the warp to use it!"));
				event.setCancelled(true);
			}
		} else if (event.getMessage().equals("/warp reminiscencecrates")) {
			if (worldname.equals("Reminiscence")) {

			} else {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou must be in the same world as the warp to use it!"));
				event.setCancelled(true);
			}
		} else if (event.getMessage().equals("/warp reminiscencehelp")) {
			if (worldname.equals("Reminiscence")) {

			} else {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou must be in the same world as the warp to use it!"));
				event.setCancelled(true);
			}
		}
		if (event.getMessage().equals("/warp normalshops")) {
			if (worldname.equals("Normal")) {

			} else {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou must be in the same world as the warp to use it!"));
				event.setCancelled(true);
			}
		}
		if (event.getMessage().equals("/warp normalcrates")) {
			if (worldname.equals("Normal")) {

			} else {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou must be in the same world as the warp to use it!"));
				event.setCancelled(true);
			}
		} else if (event.getMessage().equals("/warp normalhelp")) {
			if (worldname.equals("Normal")) {

			} else {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou must be in the same world as the warp to use it!"));
				event.setCancelled(true);
			}
		}
		if (event.getMessage().equals("/warp adventureshops")) {
			if (worldname.equals("Map")) {

			} else {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou must be in the same world as the warp to use it!"));
				event.setCancelled(true);
			}
		} else if (event.getMessage().equals("/warp adventurecrates")) {
			if (worldname.equals("Map")) {

			} else {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou must be in the same world as the warp to use it!"));
				event.setCancelled(true);
			}
		} else if (event.getMessage().equals("/warp adventurehelp")) {
			if (worldname.equals("Map")) {

			} else {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou must be in the same world as the warp to use it!"));
				event.setCancelled(true);
			}
		} else if (event.getMessage().equals("/warp reminiscencespawn")) {
			if (worldname.equals("Reminiscence")) {

			} else {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou must be in the same world as the warp to use it!"));
				event.setCancelled(true);
			}
		} else if (event.getMessage().equals("/warp adventurespawn")) {
			if (worldname.equals("Map")) {

			} else {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou must be in the same world as the warp to use it!"));
				event.setCancelled(true);
			}
		} else if (event.getMessage().equals("/warp normalspawn")) {
			if (worldname.equals("Normal")) {

			} else {
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cYou must be in the same world as the warp to use it!"));
				event.setCancelled(true);
			}
		}

		if (vpn.contains(event.getPlayer())) {
			if (!(event.getMessage().equals("/vpnappeal"))) {
				event.setCancelled(true);
				String hostname = hostnames.get(event.getPlayer().getName());
				String asn = asns.get(event.getPlayer().getName());
				List<String> runes = Arrays.asList("&8&l&m-------------------------------------",
						"                      &5&lSolar&7&lConnector", "",
						"&5&lSolar&7&lPvP &cdoes not let you play on a VPN!", "", "&5Your hostname: &c" + hostname,
						"&5Your ASN: &c" + asn, "",
						"&7You are getting this error because you are connecting with a VPN!",
						"&7Your hostname or ASN was unknown to us and was flagged as dangerous!",
						"&7Please log off your VPN and you will be able to play normally! If you think this is in error please do &5/vpnappeal",
						"&cYou will automatically be kicked in 60 seconds", "",
						"&8&l&m-------------------------------------");
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.hasPermission("frozenheart.notifyvpninitiate")) {
						p.sendMessage(ChatColor.RED + event.getPlayer().getName()
								+ " is probably using a vpn! They have been blacklisted!");
					}
				}
				for (int i = 0; i < runes.size(); i++) {
					event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', runes.get(i)));
				}
			} else {

			}
		}

		if (plugin.isSwitching.contains(event.getPlayer().getName())) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "You may not type commands while switching worlds");
		}
		String x = event.getMessage();
		String[] command = x.split(" ");
		if (command.length == 2 && command[0].equals("/tpa")) {
			Player target;
			try {
				target = Bukkit.getPlayerExact(command[1]);
			} catch (Exception e) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(
						ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cInvalid player!"));
				return;
			}
			if (!(target.getLocation().getWorld().equals(event.getPlayer().getLocation().getWorld()))) {
				String homestring;
				if (target.getLocation().getWorld().getName().equals("Map")) {
					homestring = "Adventure";
				} else {
					homestring = target.getLocation().getWorld().getName();
				}
				event.setCancelled(true);
				event.getPlayer()
						.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &cYou must switch into that player's world (" + homestring
										+ ") before you can teleport to them! Use /switchworld!"));

			} else {

			}

		} else if (command.length == 2 && command[0].equals("/home")) {
			String homename = command[1];
			Location home;
			try {
				home = plugin.essentials.getUser(event.getPlayer()).getHome(homename);
			} catch (Exception e) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(
						ChatColor.translateAlternateColorCodes('&', "&5&lSolar&7&lPvP &8&l> &cInvalid home!"));
				return;
			}

			if (!(event.getPlayer().getLocation().getWorld().getName().equals(home.getWorld().getName()))) {
				String homestring;
				if (home.getWorld().getName().equals("Map")) {
					homestring = "Adventure";
				} else {
					homestring = home.getWorld().getName();
				}
				event.setCancelled(true);
				event.getPlayer()
						.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &cYou must switch into the world of your home (" + homestring
										+ ") before you can teleport to it! Use /switchworld!"));
			} else {

			}

		} else if (command.length == 1 && command[0].equals("/home")) {
			String home;
			Location homeloc;
			try {
				home = plugin.essentials.getUser(event.getPlayer()).getHomes().get(0);
				homeloc = plugin.essentials.getUser(event.getPlayer()).getHome(home);
			} catch (Exception e) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lPvP &8&l> &cInvalid home or no home set!!"));
				return;
			}

			if (!(event.getPlayer().getLocation().getWorld().getName().equals(homeloc.getWorld().getName()))) {
				String homestring;

				if (homeloc.getWorld().getName().equals("Map")) {
					homestring = "Adventure";
				} else {
					homestring = homeloc.getWorld().getName();
				}
				event.setCancelled(true);
				event.getPlayer()
						.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&5&lSolar&7&lPvP &8&l> &cYou must switch into the world of your home (" + homestring
										+ ") before you can teleport to it! Use /switchworld!"));
			} else {

			}
		}

	}

	@EventHandler
	public void onBucketEmpty(PlayerBucketEmptyEvent event) {
		if (event.getPlayer().getWorld().getName().equals("Reminiscence")) {
			if (!(NightUtils.bypass.containsKey(event.getPlayer()))) {
				new BukkitRunnable() {
					@Override
					public void run() {
						event.getBlockClicked().getRelative(event.getBlockFace()).setType(Material.AIR);
					}
				}.runTaskLater(plugin, 20 * plugin.remdelay);

			}
		}
	}

	public void setBlockFast(World world, int x, int y, int z, int blockId, byte data) {
		net.minecraft.server.v1_8_R3.World w = ((CraftWorld) world).getHandle();
		net.minecraft.server.v1_8_R3.Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
		BlockPosition bp = new BlockPosition(x, y, z);
		int combined = blockId + (data << 12);
		IBlockData ibd = net.minecraft.server.v1_8_R3.Block.getByCombinedId(combined);
		chunk.a(bp, ibd);

	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		event.setDeathMessage(null);
		Player player = event.getEntity();
		World world = player.getLocation().getWorld();

		String worldname = world.getName();

		if (worldname.equals("Map")) {
			respawnlocs.put(player.getName(), plugin.adventurespawn);

		} else if (worldname.equals("Reminiscence")) {
			respawnlocs.put(player.getName(), plugin.remspawn);

		} else if (worldname.equals("Moon")) {
			respawnlocs.put(player.getName(), plugin.moonspawn);

		} else if (worldname.equals("Normal")) {
			respawnlocs.put(player.getName(), plugin.normalspawn);

		} else if (worldname.equals("Arena")) {
			respawnlocs.put(player.getName(), plugin.adventurespawn);

		} else if (worldname.equals("world_nether")) {
			respawnlocs.put(player.getName(), new Location(Bukkit.getWorld("world_nether"), 0.500, 84, 0.500));
		}

	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {

		String player = event.getPlayer().getName();

		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					Location loc = respawnlocs.get(player);

					event.getPlayer().teleport(loc);

					respawnlocs.remove(player, loc);

					event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lPvP &8&l> &cYou have died, and have been teleported back to the world where you died!"));

				} catch (Exception e) {

				}
			}
		}.runTaskLater(plugin, 20);

	}

}
