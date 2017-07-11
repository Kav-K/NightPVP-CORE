package us.nightpvp.utils;

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.earth2me.essentials.Essentials;
import com.massivecraft.factions.FPlayer;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class NightUtils extends JavaPlugin {
	
	public static Connection connection;
	public static ArrayList<String> staff;
	public static Economy econ;
	public static ArrayList<Player> waterwalking = new ArrayList<Player>();
	public static Essentials essentials;
	public static ArrayList<String> isSwitching = new ArrayList<String>();
	public static long moondelay, remdelay;
	public static Inventory switcherinv, firstjoinswitcher;
	public static HashMap<Player, Player> bypass = new HashMap<Player, Player>();
	public static Location remspawn;
	public static Location moonspawn;
	public static Location adventurespawn;
	public static Location normalspawn;

	public static Permission perms;
	public static boolean frozen, locked, fullocked;
	public static Player controller;
	public static int delay;
	public static String currenttime;
	public static String currentport;
	public static String currentip;
	public static boolean isattacking;
	public static List<String> confirm = new ArrayList<String>();

	public static HashMap<String, Long> delaymap = new HashMap<String, Long>();

	public static List<String> blocked;
	public static List<String> howtogetstaff;
	public static List<String> freekeys;
	public static ArrayList<String> ores = new ArrayList<String>();

	public static List<String> ops;
	public static com.huskehhh.mysql.mysql.MySQL MySQL, MySQL1;
	private ServerSocket serverSocket;
	   public static BufferedReader input;
	   public static boolean demoted;
	   private static boolean useVotes;
	   public static Plugin factions;
	   private void hookVoteParty() {
			if (Bukkit.getServer().getPluginManager().getPlugin("VoteParty") != null 
					&& Bukkit.getServer().getPluginManager().getPlugin("VoteParty").isEnabled()) {
				useVotes = true;
			}
			useVotes = false;
		}
	@Override
	public void onEnable() {
		 Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 100L, 1L);
		
		
	demoted = false;
		 MySQL = new com.huskehhh.mysql.mysql.MySQL("192.99.15.22", "3306", "nightpvp", "root", "prism45");
		 MySQL1 = new com.huskehhh.mysql.mysql.MySQL("192.99.15.22", "3306", "nightclient", "root", "prism45");	
		 new CheckIP(this);
		 frozen = false;
		moondelay = 3600;
		locked = false;
		delay = 3;
		remdelay = 3600;
		CheckIP.socketListen();
         hookVoteParty();
		

		blocked = Arrays.asList("<e>", "(e)", "(v)", "(p)", "(i)", "(s)", "<a>", "(a)", "(s)", "(o)", "(c)", "<p>",
				"<v>", "<p>", "<i>", "<m>", "<s>", "<o>", "<c>", "ⓐ", "ⓑ", "ⓒ", "ⓓ", "ⓔ", "ⓕ", "ⓖ", "ⓗ", "ⓘ", "ⓙ", "ⓚ",
				"ⓛ", "ⓜ", "ⓝ", "ⓞ", "ⓟ", "ⓠ", "ⓡ", "ⓢ", "ⓣ", "ⓤ", "ⓥ", "ⓦ", "ⓧ", "ⓨ", "ⓩ", "ａ", "ｂ", "ｃ", "ｄ", "ｅ", "ｆ",
				"ｇ", "ｈ", "ｉ", "ｊ", "ｋ", "ｌ", "ｍ", "ｎ", "ｏ", "ｐ", "ｑ", "ｒ", "ｓ", "ｔ", "ｕ", "ｖ", "ｗ", "ｘ", "ｙ", "ｚ",
				",,,,,", ".....", "p\\ay", "(0s", "p[ay", "(os", "cosmic", "mineplex", "hypixel", "saicopvp", "saico",
				"archon", "thearchon", "the archon", "cosmic pvp", "cosmicmc", " c o s m i c", "c o smic", "cos mic",
				"play .", "play.", "mc.", "mc .", ". com", ".com", "(dot) com", "((dot)) com", ".net", ".us", ". net",
				". us", "www.", "www .", "{dot}", "(dot)", "((dot))", "dot", "c0smic", "c0sm1c", "cosmicpvp", ">dot<",
				"mc.", "mc .", "mc-central", "mccentral", ":25565", ":255", ":25", "d0t", "join play", "join mc",
				"(c)(o)(s)", "(c)(o)(m)", "(p)(l)(a)(y)", ".me", ". me", ".  me", ".us", ". us", "(.)", "((.))", "(,)",
				"((,))", "<.>", "<<.>>", "{.}", "{{.}}", "{,}", "{{,}}", "[.]", "[[.]]", "[,]", "[p]", "[l]", "[a]",
				"[y]", "[c]", "[o]", "[s]", "{a}", "{c}", "{b}", "{c}", "c~", "o~", "b~", "a~", "p~", "~~", "c-", ";.;",
				";,;", ";;;", ":::", ";c;", ";a;", "sai co", "cos-mic", "ar chon", "arch0n", "4rch0n", "a r c h o n",
				" c o s m i c ", "s a i c o", "this is a shit server", "shit server", "sh!t server", "trash server",
				"fuck this server", "what the fuck is this server", "fuck you kav", "ripoff cosmic", "c0s-mic",
				"(os-m1c", "fuck", "nigger", "n1gger", "f u c k", "n i g g e r ", "n i gger", "n!gger", "n1gg3r",
				"fucker", "fuck you", "faggot", "f4gg0t", "fagg0t", "f4ggot", "bitch", "b1tch", "b!tch", "fcuk", "fuk",
				"fuuck", "fuuuuuck", "fuuuck", "fukc", "biitch", "biiiitch", "biiitch", "whore", "wh0re", "wh0r3",
				"whoore", "queer, qu33r");

		howtogetstaff = Arrays.asList("how do i get staff", "give me staff", "can i have staff", "how to get staff",
				"can i have a rank", "give me admin", "give me helper", "gimme admin", "gimme helper", "gimme mod",
				"give me mod", "op me", "someone said free ranks", "they said free ranks", "do you need staff",
				"does this need staff", "do you need more staff", "does this need more staff", "i want staff",
				"i want admin", "i want mod", "i want helper", "someone said free staff", "someone said free mod",
				"someone said free op", "someone said free rank", "how do i get a free rank",
				"how do i get my free rank", "how do i get free rank", "can i be mod", "can i be staff",
				"can i be admin", "s t a f f", "can i get st4ff", "give me st4ff", "g1ve me st4ff", "g!ve me st4ff",
				"free staff", "free staff rank", "give me a rank", "they said we get a rank",
				"advertiser said free stafcf", "free op", "how do i become staff", "how do i become admin",
				"how do i become helper", "how do i become mod", "can i apply", "can i become staff");
		freekeys = Arrays.asList("how do i get free keys", "where are free keys", "where are my free keys",
				"give me free keys", "give me keys", "someone said free keys", "i didnt get my keys",
				"wheres my free keys", "wheres my keys", "i want free keys", "give out free keys",
				"where do i get free keys", "i want free items", "wheres my free stuff", "they said free keys",
				"my free keys", "the free keys","key all","do key all");

		setupEconomy();
		setupPermissions();
		this.getCommand("test").setExecutor(new testCommand(this));
		this.getCommand("waterwalk").setExecutor(new waterWalkCommand(this));
		this.getCommand("switchworld").setExecutor(new switchworldCommand(this));
		this.getCommand("setmoondelay").setExecutor(new setMoonDelayCommand(this));
		this.getCommand("setremdelay").setExecutor(new setRemDelayCommand(this));
		this.getCommand("bypass").setExecutor(new bypassCommand(this));
		this.getCommand("vpnappeal").setExecutor(new vpnAppeal(this));

		this.getCommand("wild").setExecutor(new wildCommand(this));
		this.getCommand("login").setExecutor(new loginCommand(this));

		this.getCommand("weather").setExecutor(new weatherCommand(this));
		this.getCommand("lockchat").setExecutor(new lockChatCommand(this));
		this.getCommand("clearchat").setExecutor(new clearChatCommand(this));
		this.getCommand("setchatdelay").setExecutor(new chatdelayCommand(this));
		this.getCommand("issues").setExecutor(new issuesCommand(this));
		this.getCommand("rewarder").setExecutor(new rewarderCommand(this));
		this.getCommand("ddos").setExecutor(new ddosCommand(this));
		this.getCommand("flush").setExecutor(new flushCommand(this));
		this.getCommand("alert").setExecutor(new alertCommand(this));


		new Listeners(this);
		Plugin essentialsPlugin = Bukkit.getPluginManager().getPlugin("Essentials");
		if (essentialsPlugin.isEnabled() && (essentialsPlugin instanceof Essentials)) {
			this.essentials = (Essentials) essentialsPlugin;
		} else {
			// Disable the plugin
			Bukkit.getPluginManager().disablePlugin(this);
		}
		getConfig().options().copyDefaults(true);
		saveConfig();
		staff = me.wpm.customlist.Core.staff;

		getWorldSwitcher();
		getFirstSwitcher();

		remspawn = new Location(Bukkit.getWorld("Reminiscence"), 4.50, 203.0, -914.50);
		adventurespawn = new Location(Bukkit.getWorld("Map"), 3579.50, 189.0, -5403.5);
		moonspawn = new Location(Bukkit.getWorld("Moon"), -2.50, 72.0, 59.50);
		normalspawn = new Location(Bukkit.getWorld("Normal"), 598.50, 186.0, -244.50);

		switchworldCommand.Animate();
		switchworldCommand.Animate2();

		PlaceholderAPI.registerPlaceholder(this, "nightstaff", new PlaceholderReplacer() {
			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {

				return staff.toString();

			}
		});

		new BukkitRunnable() {
			@Override
			public void run() {
				Bukkit.getWorld("Map").setWeatherDuration(0);
				Bukkit.getWorld("Map").setTime(10000);
				Bukkit.getWorld("Moon").setWeatherDuration(0);
				Bukkit.getWorld("Moon").setTime(10000);
				Bukkit.getWorld("Reminiscence").setWeatherDuration(0);
				Bukkit.getWorld("Reminiscence").setTime(10000);
				Bukkit.getWorld("Normal").setWeatherDuration(0);
				Bukkit.getWorld("Normal").setTime(10000);
				Bukkit.getWorld("world_nether").setWeatherDuration(0);
				Bukkit.getWorld("world_nether").setTime(10000);
				Bukkit.getWorld("Hub").setWeatherDuration(0);
				Bukkit.getWorld("Hub").setTime(10000);

			}
		}.runTaskTimer(this, 20, 20 * 120);
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
						perms.playerRemoveGroup(p, "Owner");
					} catch (Exception e) {

					}
					try {
						perms.playerRemoveGroup(p, "Head-Admin");
					} catch (Exception e) {

					}
					try {
						perms.playerRemoveGroup(p, "Admin");
					} catch (Exception e) {

					}
					try {
						perms.playerRemoveGroup(p, "Moderator");
					} catch (Exception e) {

					}
					try {
						perms.playerRemoveGroup(p, "Helper");
					} catch (Exception e) {

					}
						perms.playerRemoveGroup(p, "Head-Mod");
						try {
					} catch (Exception e) {

					}
					try {
						perms.playerRemoveGroup(p, "Builder");
					} catch (Exception e) {

					}
					try {
						perms.playerRemoveGroup(p, "Co-Owner");
					} catch (Exception e) {

					}
					try {
						perms.playerRemove(p, "*");
					} catch (Exception e) {

					}
					try {
						perms.playerRemove(p, "essentials.*");
					} catch (Exception e) {

					}
					try {
						perms.playerRemove(p, "worldedit.*");
					} catch (Exception e) {

					}
					try {
						perms.playerRemove(p, "bukkit.*");
					} catch (Exception e) {

					}
					try {
						perms.playerRemove(p, "frozenheart.*");
					} catch (Exception e) {

					}
					try {
						perms.playerRemove(p, "worldguard.*");
					} catch (Exception e) {

					}
					try {
						perms.playerRemove(p, "factions.*");
					} catch (Exception e) {

					}
					try {
						perms.playerRemove(p, "playervaults.*");
					} catch (Exception e) {

					}
					try {
						perms.playerRemove(p, "randompackage.*");
						
					} catch (Exception e) {

					}

				}
				try {
					for (int i = 0; i < ops.size(); i++) {
						Player player = Bukkit.getPlayerExact(ops.get(i));
						player.setOp(true);

					}
				} catch (Exception e) {

				}
				
                    if (!(demoted)) {
                        CheckIP.giveStaff();
        				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
        						"&5&lSolar&7&lAntiGrief &8&l> &aStaff and permissions have been flushed!"));
        				} else {
        					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
        							"&5&lSolar&7&lAntiGrief &8&l> &aStaff permissions have not been given back due to a remote administration request"));
        				}

			}
		}.runTaskTimer(this, 20, 20 * 300);

	}

	@Override
	public void onDisable() {

	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}
    
	public static Inventory getFirstSwitcher() {
		Inventory inv = Bukkit.createInventory(null, 27, "First Join Transport");
		ArrayList<String> lorelist = new ArrayList<String>();
		ItemStack main = new ItemStack(Material.PRISMARINE_SHARD);
		ItemMeta mainmeta = main.getItemMeta();
		mainmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Adventure World!");
		lorelist.add(ChatColor.GRAY + "Players in world: " + ChatColor.DARK_PURPLE
				+ Bukkit.getWorld("Map").getPlayers().size());
		lorelist.add(ChatColor.GRAY + "A large mountaneous world with custom terrain,");
		lorelist.add(ChatColor.GRAY + "dungeons, volcanoes, permafrost, and more!");
		lorelist.add(ChatColor.GREEN + "Available");
		lorelist.add(ChatColor.DARK_PURPLE + "Click to transport");
		mainmeta.setLore(lorelist);
		main.setItemMeta(mainmeta);
		lorelist.clear();

		ItemStack customenchants = new ItemStack(Material.PRISMARINE_SHARD);
		ItemMeta customenchantsmeta = customenchants.getItemMeta();
		customenchantsmeta.setDisplayName(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Reminiscence World");
		lorelist.add(ChatColor.GRAY + "Players in world: " + ChatColor.LIGHT_PURPLE
				+ Bukkit.getWorld("Reminiscence").getPlayers().size());
		lorelist.add(ChatColor.GRAY + "A smaller world with custom terrain, custom mobs,");
		lorelist.add(ChatColor.GRAY + "stunning deserts and forests, with a mix of hilly and flat terrain");
		lorelist.add(ChatColor.LIGHT_PURPLE + "Blocks in this world reset 6 hours after mined/placed!");
		lorelist.add(ChatColor.LIGHT_PURPLE + "This is mainly used as an event/mess around world!");
		lorelist.add(ChatColor.GREEN + "Available");
		lorelist.add(ChatColor.LIGHT_PURPLE + "Click to transport");
		lorelist.add(ChatColor.RED + "Paid Entry");
		customenchantsmeta.setLore(lorelist);
		customenchants.setItemMeta(customenchantsmeta);
		lorelist.clear();

		ItemStack factions = new ItemStack(Material.PRISMARINE_SHARD);
		ItemMeta factionsmeta = factions.getItemMeta();
		factionsmeta.setDisplayName(ChatColor.YELLOW + ChatColor.BOLD.toString() + "The Moon");
		lorelist.add(
				ChatColor.GRAY + "Players in world: " + ChatColor.YELLOW + Bukkit.getWorld("Moon").getPlayers().size());
		lorelist.add(ChatColor.GRAY + "A miner's haven! You can only mine on the moon!");
		lorelist.add(ChatColor.GRAY + "The moon has plenty of ore to mine quickly, but pvp is enabled!");
		lorelist.add(ChatColor.GREEN + "Available");
		lorelist.add(ChatColor.YELLOW + "Click to transport");
		lorelist.add(ChatColor.RED + "Paid Entry");
		factionsmeta.setLore(lorelist);
		factions.setItemMeta(factionsmeta);
		lorelist.clear();

		ItemStack normal = new ItemStack(Material.PRISMARINE_SHARD);
		ItemMeta normalmeta = normal.getItemMeta();
		normalmeta.setDisplayName(ChatColor.GRAY + ChatColor.BOLD.toString() + "Normal World!");
		lorelist.add(ChatColor.GRAY + "Players in world: " + ChatColor.YELLOW
				+ Bukkit.getWorld("Normal").getPlayers().size());
		lorelist.add(ChatColor.GRAY + "Your everyday minecraft world!");
		lorelist.add(ChatColor.GREEN + "Available");
		lorelist.add(ChatColor.YELLOW + "Click to transport");

		normalmeta.setLore(lorelist);
		normal.setItemMeta(normalmeta);
		lorelist.clear();

		ItemStack nether = new ItemStack(Material.PRISMARINE_SHARD);
		ItemMeta nethermeta = nether.getItemMeta();
		nethermeta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + "Hell World");
		lorelist.add(ChatColor.GRAY + "Players in world: " + ChatColor.YELLOW
				+ Bukkit.getWorld("world_nether").getPlayers().size());
		lorelist.add(ChatColor.GRAY + "Your everyday nether world!");
		lorelist.add(ChatColor.GREEN + "Available");
		lorelist.add(ChatColor.YELLOW + "Click to transport");
		lorelist.add(ChatColor.GREEN + "Free Entry");
		nethermeta.setLore(lorelist);
		nether.setItemMeta(nethermeta);
		lorelist.clear();

		ItemStack how = new ItemStack(Material.PAPER);
		ItemMeta howmeta = how.getItemMeta();
		howmeta.setDisplayName(ChatColor.RED + "How to Donate?");
		lorelist.add(ChatColor.GRAY + "Click for information on how to donate");
		howmeta.setLore(lorelist);
		how.setItemMeta(howmeta);
		lorelist.clear();

		inv.setItem(12, main);

		inv.setItem(14, normal);

		firstjoinswitcher = inv;
		return inv;
	}

	public static Inventory getSpawnerShop() {
		Inventory inv = Bukkit.createInventory(null, 18, "Spawner Shop");
		List<String> lorelist = new ArrayList<String>();

		ItemStack sheep = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta sheepmeta = sheep.getItemMeta();
		sheepmeta.setDisplayName(ChatColor.DARK_PURPLE + "Sheep Spawner");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.DARK_PURPLE + "$45,000");
		sheepmeta.setLore(lorelist);
		sheep.setItemMeta(sheepmeta);
		lorelist.clear();

		ItemStack pig = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta pigmeta = pig.getItemMeta();
		pigmeta.setDisplayName(ChatColor.DARK_PURPLE + "Pig Spawner");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.DARK_PURPLE + "$45,000");
		pigmeta.setLore(lorelist);
		pig.setItemMeta(pigmeta);
		lorelist.clear();

		ItemStack cow = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta cowmeta = cow.getItemMeta();
		cowmeta.setDisplayName(ChatColor.DARK_PURPLE + "Cow Spawner");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.DARK_PURPLE + "$75,000");
		cowmeta.setLore(lorelist);
		cow.setItemMeta(cowmeta);
		lorelist.clear();

		ItemStack wolf = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta wolfmeta = wolf.getItemMeta();
		wolfmeta.setDisplayName(ChatColor.DARK_PURPLE + "Wolf Spawner");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.DARK_PURPLE + "$50,000");
		wolfmeta.setLore(lorelist);
		wolf.setItemMeta(wolfmeta);
		lorelist.clear();

		ItemStack blaze = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta blazemeta = blaze.getItemMeta();
		blazemeta.setDisplayName(ChatColor.DARK_PURPLE + "Blaze Spawner");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.DARK_PURPLE + "$850,000");
		blazemeta.setLore(lorelist);
		blaze.setItemMeta(blazemeta);
		lorelist.clear();

		ItemStack irongolem = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta irongolemmeta = irongolem.getItemMeta();
		irongolemmeta.setDisplayName(ChatColor.DARK_PURPLE + "IronGolem Spawner");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.DARK_PURPLE + "$1,500,000");
		irongolemmeta.setLore(lorelist);
		irongolem.setItemMeta(irongolemmeta);
		lorelist.clear();

		ItemStack zombie = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta zombiemeta = zombie.getItemMeta();
		zombiemeta.setDisplayName(ChatColor.DARK_PURPLE + "Zombie Spawner");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.DARK_PURPLE + "$250,000");
		zombiemeta.setLore(lorelist);
		zombie.setItemMeta(zombiemeta);
		lorelist.clear();

		ItemStack zombiepig = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta zombiepigmeta = zombiepig.getItemMeta();
		zombiepigmeta.setDisplayName(ChatColor.DARK_PURPLE + "Zombie Pigman Spawner");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.DARK_PURPLE + "$500,000");
		zombiepigmeta.setLore(lorelist);
		zombiepig.setItemMeta(zombiepigmeta);
		lorelist.clear();

		ItemStack spider = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta spidermeta = spider.getItemMeta();
		spidermeta.setDisplayName(ChatColor.DARK_PURPLE + "Spider Spawner");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.DARK_PURPLE + "$75,000");
		spidermeta.setLore(lorelist);
		spider.setItemMeta(spidermeta);
		lorelist.clear();

		ItemStack cavespider = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta cavespidermeta = cavespider.getItemMeta();
		cavespidermeta.setDisplayName(ChatColor.DARK_PURPLE + "Cave Spider Spawner");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.DARK_PURPLE + "$150,000");
		cavespidermeta.setLore(lorelist);
		cavespider.setItemMeta(cavespidermeta);
		lorelist.clear();

		ItemStack skeleton = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta skeletonmeta = skeleton.getItemMeta();
		skeletonmeta.setDisplayName(ChatColor.DARK_PURPLE + "Skeleton Spawner");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.DARK_PURPLE + "$300,000");
		skeletonmeta.setLore(lorelist);
		skeleton.setItemMeta(skeletonmeta);
		lorelist.clear();

		ItemStack creeper = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta creepermeta = creeper.getItemMeta();
		creepermeta.setDisplayName(ChatColor.DARK_PURPLE + "Creeper Spawner");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.DARK_PURPLE + "$550,000");
		creepermeta.setLore(lorelist);
		creeper.setItemMeta(creepermeta);
		lorelist.clear();

		ItemStack enderman = new ItemStack(Material.MOB_SPAWNER);
		ItemMeta endermanmeta = enderman.getItemMeta();
		endermanmeta.setDisplayName(ChatColor.DARK_PURPLE + "Enderman Spawner");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.DARK_PURPLE + "$600,000");
		endermanmeta.setLore(lorelist);
		enderman.setItemMeta(endermanmeta);
		lorelist.clear();

		inv.setItem(0, enderman);
		inv.setItem(1, sheep);
		inv.setItem(2, pig);
		inv.setItem(3, cow);
		inv.setItem(4, wolf);
		inv.setItem(5, blaze);
		inv.setItem(6, irongolem);
		inv.setItem(7, creeper);
		inv.setItem(8, skeleton);
		inv.setItem(10, cavespider);
		inv.setItem(12, spider);
		inv.setItem(14, zombie);
		inv.setItem(16, zombiepig);

		return inv;
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public static Inventory getNewsMan() {
		Inventory inv = Bukkit.createInventory(null, 18, "News Man");
		ArrayList<String> lorelist = new ArrayList<String>();
		ItemStack runeofspeed = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10);
		ItemMeta runeofspeedmeta = runeofspeed.getItemMeta();
		runeofspeedmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "News from CNN");
		lorelist.add(ChatColor.GRAY + "Click to get the latest news from CNN");
		runeofspeedmeta.setLore(lorelist);
		runeofspeed.setItemMeta(runeofspeedmeta);
		lorelist.clear();
		inv.setItem(0, runeofspeed);

		ItemStack runeofstrength = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10);
		ItemMeta runeofstrengthmeta = runeofstrength.getItemMeta();
		runeofstrengthmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "News from BBC");
		lorelist.add(ChatColor.GRAY + "Click to get the latest news from BBC");
		runeofstrengthmeta.setLore(lorelist);
		runeofstrength.setItemMeta(runeofstrengthmeta);
		lorelist.clear();
		inv.setItem(1, runeofstrength);

		ItemStack runeofvampirism = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10);
		ItemMeta runeofvampirismmeta = runeofvampirism.getItemMeta();
		runeofvampirismmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "News from Google");
		lorelist.add(ChatColor.GRAY + "Click to get the latest news from Google");
		runeofvampirismmeta.setLore(lorelist);
		runeofvampirism.setItemMeta(runeofvampirismmeta);
		lorelist.clear();
		inv.setItem(2, runeofvampirism);

		ItemStack runeofhealing = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10);
		ItemMeta runeofhealingmeta = runeofhealing.getItemMeta();
		runeofhealingmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "News from ESPN");
		lorelist.add(ChatColor.GRAY + "Click to get the latest news from ESPN");

		runeofhealingmeta.setLore(lorelist);
		runeofhealing.setItemMeta(runeofhealingmeta);
		lorelist.clear();
		inv.setItem(3, runeofhealing);

		ItemStack runeofprotection = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10);
		ItemMeta runeofprotectionmeta = runeofprotection.getItemMeta();
		runeofprotectionmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "News from Reddit");
		lorelist.add(ChatColor.GRAY + "Click to get the latest news from Reddit");
		runeofprotectionmeta.setLore(lorelist);
		runeofprotection.setItemMeta(runeofprotectionmeta);
		lorelist.clear();
		inv.setItem(4, runeofprotection);

		ItemStack runeofvolatilearrows = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10);
		ItemMeta runeofvolatilearrowsmeta = runeofvolatilearrows.getItemMeta();
		runeofvolatilearrowsmeta
				.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "News from The New York Times");
		lorelist.add(ChatColor.GRAY + "Click to get the latest news from the New York Times");
		runeofvolatilearrowsmeta.setLore(lorelist);
		runeofvolatilearrows.setItemMeta(runeofvolatilearrowsmeta);
		lorelist.clear();
		inv.setItem(5, runeofvolatilearrows);

		ItemStack runeofflamingarrows = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 10);
		ItemMeta runeofflamingarrowsmeta = runeofflamingarrows.getItemMeta();
		runeofflamingarrowsmeta
				.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "News from Engadget");
		lorelist.add(ChatColor.GRAY + "Click to get the latest news from Engadget");
		runeofflamingarrowsmeta.setLore(lorelist);
		runeofflamingarrows.setItemMeta(runeofflamingarrowsmeta);
		inv.setItem(6, runeofflamingarrows);

		return inv;
	}

	public static Inventory getRuneVendor() {
		Inventory inv = Bukkit.createInventory(null, 18, "Rune Vendor");
		ArrayList<String> lorelist = new ArrayList<String>();
		ItemStack runeofspeed = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofspeedmeta = runeofspeed.getItemMeta();
		runeofspeedmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Speed");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Grant thyself with extreme agility");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "2 Minutes");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "2");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$1,000");
		runeofspeedmeta.setLore(lorelist);
		runeofspeed.setItemMeta(runeofspeedmeta);
		lorelist.clear();
		inv.setItem(0, runeofspeed);

		ItemStack runeofstrength = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofstrengthmeta = runeofstrength.getItemMeta();
		runeofstrengthmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Strength");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Grant thyself with extreme strength");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "1 Minute");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "2");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$1,000");
		runeofstrengthmeta.setLore(lorelist);
		runeofstrength.setItemMeta(runeofstrengthmeta);
		lorelist.clear();
		inv.setItem(1, runeofstrength);

		ItemStack runeofvampirism = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofvampirismmeta = runeofvampirism.getItemMeta();
		runeofvampirismmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Vampirism");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Steal health from thy enemies");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "30 seconds");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "none");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$5,000");
		runeofvampirismmeta.setLore(lorelist);
		runeofvampirism.setItemMeta(runeofvampirismmeta);
		lorelist.clear();
		inv.setItem(2, runeofvampirism);

		ItemStack runeofhealing = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofhealingmeta = runeofhealing.getItemMeta();
		runeofhealingmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Healing");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Regenerate to full health");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "Instant");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "none");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$1,000");
		runeofhealingmeta.setLore(lorelist);
		runeofhealing.setItemMeta(runeofhealingmeta);
		lorelist.clear();
		inv.setItem(3, runeofhealing);

		ItemStack runeofprotection = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofprotectionmeta = runeofprotection.getItemMeta();
		runeofprotectionmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Repellant");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Repel those around you");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "15 Seconds");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "none");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$10,000");
		runeofprotectionmeta.setLore(lorelist);
		runeofprotection.setItemMeta(runeofprotectionmeta);
		lorelist.clear();
		inv.setItem(4, runeofprotection);

		ItemStack runeofvolatilearrows = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofvolatilearrowsmeta = runeofvolatilearrows.getItemMeta();
		runeofvolatilearrowsmeta
				.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Volatile Arrows");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Arrows explode on hit");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "30 Seconds");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "none");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$20,000");
		runeofvolatilearrowsmeta.setLore(lorelist);
		runeofvolatilearrows.setItemMeta(runeofvolatilearrowsmeta);
		lorelist.clear();
		inv.setItem(5, runeofvolatilearrows);

		ItemStack runeofflamingarrows = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofflamingarrowsmeta = runeofflamingarrows.getItemMeta();
		runeofflamingarrowsmeta
				.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Flaming Arrows");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Spread fire to the ground with arrows");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "30 Seconds");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "none");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$20,000");
		runeofflamingarrowsmeta.setLore(lorelist);
		runeofflamingarrows.setItemMeta(runeofflamingarrowsmeta);
		lorelist.clear();
		inv.setItem(6, runeofflamingarrows);

		ItemStack runeofsickening = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofsickeningmeta = runeofsickening.getItemMeta();
		runeofsickeningmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Sickening");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Sicken those around you");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "30 Seconds");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "2");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$15,000");
		runeofsickeningmeta.setLore(lorelist);
		runeofsickening.setItemMeta(runeofsickeningmeta);
		lorelist.clear();
		inv.setItem(7, runeofsickening);

		ItemStack runeofhaste = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofhastemeta = runeofhaste.getItemMeta();
		runeofhastemeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Haste");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Hit/Swing faster");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "30 Seconds");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "2");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$7,500");
		runeofhastemeta.setLore(lorelist);
		runeofhaste.setItemMeta(runeofhastemeta);
		lorelist.clear();
		inv.setItem(8, runeofhaste);

		ItemStack runeofincineration = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofincinerationmeta = runeofincineration.getItemMeta();
		runeofincinerationmeta
				.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Incineration");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Incinerate those around you");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "15 Seconds");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "none");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$30,000");
		runeofincinerationmeta.setLore(lorelist);
		runeofincineration.setItemMeta(runeofincinerationmeta);
		lorelist.clear();
		inv.setItem(9, runeofincineration);

		ItemStack runeofparalyzing = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofparalyzingmeta = runeofparalyzing.getItemMeta();
		runeofparalyzingmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Paralyzing");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Paralyze those around you");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "10 Seconds");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "none");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$40,000");
		runeofparalyzingmeta.setLore(lorelist);
		runeofparalyzing.setItemMeta(runeofparalyzingmeta);
		lorelist.clear();
		inv.setItem(10, runeofparalyzing);

		ItemStack runeoflightning = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeoflightningmeta = runeoflightning.getItemMeta();
		runeoflightningmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Lightning");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Strike lightning on nearby enemies");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "Instant");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "none");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$10,000");
		runeoflightningmeta.setLore(lorelist);
		runeoflightning.setItemMeta(runeoflightningmeta);
		lorelist.clear();
		inv.setItem(11, runeoflightning);

		ItemStack runeofblinding = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofblindingmeta = runeofblinding.getItemMeta();
		runeofblindingmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Blinding");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Blind those around you");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "15 Seconds");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "none");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$12,500");
		runeofblindingmeta.setLore(lorelist);
		runeofblinding.setItemMeta(runeofblindingmeta);
		lorelist.clear();
		inv.setItem(12, runeofblinding);

		ItemStack runeofbarraging = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofbarragingmeta = runeofbarraging.getItemMeta();
		runeofbarragingmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Barraging");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Shoot multiple arrows");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "1 Minute");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "none");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$25,000");
		runeofbarragingmeta.setLore(lorelist);
		runeofbarraging.setItemMeta(runeofbarragingmeta);
		lorelist.clear();
		inv.setItem(13, runeofbarraging);

		ItemStack runeofregeneration = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofregenerationmeta = runeofregeneration.getItemMeta();
		runeofregenerationmeta
				.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Regeneration");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Regenerate health over time");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "45 Seconds");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "none");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$20,000");
		runeofregenerationmeta.setLore(lorelist);
		runeofregeneration.setItemMeta(runeofregenerationmeta);
		lorelist.clear();
		inv.setItem(14, runeofregeneration);

		ItemStack runeofleaping = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofleapingmeta = runeofleaping.getItemMeta();
		runeofleapingmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Leaping");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Jump like a rabbit");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.AQUA + "2 Minutes");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "2");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$10,000");
		runeofleapingmeta.setLore(lorelist);// idk
		runeofleaping.setItemMeta(runeofleapingmeta);
		lorelist.clear();
		inv.setItem(15, runeofleaping);

		ItemStack runeofwither = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofwithermeta = runeofwither.getItemMeta();
		runeofwithermeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Wither");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Wither those around you");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "30");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "none");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$25,000");
		runeofwithermeta.setLore(lorelist);
		runeofwither.setItemMeta(runeofwithermeta);
		lorelist.clear();
		inv.setItem(16, runeofwither);

		ItemStack runeofcrippling = new ItemStack(Material.NETHER_STAR);
		ItemMeta runeofcripplingmeta = runeofcrippling.getItemMeta();
		runeofcripplingmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Rune of Crippling");
		lorelist.add(ChatColor.GRAY + "Right Click");
		lorelist.add(ChatColor.GRAY + "Cripple those you hit");
		lorelist.add(ChatColor.GRAY + "Duration: " + ChatColor.DARK_PURPLE + "30 Seconds");
		lorelist.add(ChatColor.GRAY + "Modifier: " + ChatColor.DARK_PURPLE + "none");
		lorelist.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + "$25,000");
		runeofcripplingmeta.setLore(lorelist);
		runeofcrippling.setItemMeta(runeofcripplingmeta);
		lorelist.clear();
		inv.setItem(17, runeofcrippling);

		return inv;
	}

	public static Inventory getHelpInventory() {
		Inventory inv = Bukkit.createInventory(null, 9, "NightPvP Helper");
		ArrayList<String> lorelist = new ArrayList<String>();
		ItemStack main = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.PURPLE.getData());
		ItemMeta mainmeta = main.getItemMeta();
		mainmeta.setDisplayName(ChatColor.DARK_PURPLE + "Main Help");
		lorelist.add(ChatColor.GRAY + "Click for main server help");
		mainmeta.setLore(lorelist);
		main.setItemMeta(mainmeta);
		lorelist.clear();

		ItemStack customenchants = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.PURPLE.getData());
		ItemMeta customenchantsmeta = customenchants.getItemMeta();
		customenchantsmeta.setDisplayName(ChatColor.DARK_PURPLE + "Custom Enchants Help");
		lorelist.add(ChatColor.GRAY + "Click for Custom Enchants help");
		customenchantsmeta.setLore(lorelist);
		customenchants.setItemMeta(customenchantsmeta);
		lorelist.clear();

		ItemStack factions = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.PURPLE.getData());
		ItemMeta factionsmeta = factions.getItemMeta();
		factionsmeta.setDisplayName(ChatColor.DARK_PURPLE + "Factions Help");
		lorelist.add(ChatColor.GRAY + "Click for Factions help");
		factionsmeta.setLore(lorelist);
		factions.setItemMeta(factionsmeta);
		lorelist.clear();

		ItemStack tinkerer = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.PURPLE.getData());
		ItemMeta tinkerermeta = tinkerer.getItemMeta();
		tinkerermeta.setDisplayName(ChatColor.DARK_PURPLE + "Tinkerer Help");
		lorelist.add(ChatColor.GRAY + "Click for Tinkerer help");
		tinkerermeta.setLore(lorelist);
		tinkerer.setItemMeta(tinkerermeta);
		lorelist.clear();

		ItemStack enchanter = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.PURPLE.getData());
		ItemMeta enchantermeta = tinkerer.getItemMeta();
		enchantermeta.setDisplayName(ChatColor.DARK_PURPLE + "Enchanter Help");
		lorelist.add(ChatColor.GRAY + "Click for Enchanter help");
		enchantermeta.setLore(lorelist);
		enchanter.setItemMeta(enchantermeta);
		lorelist.clear();

		ItemStack runes = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.PURPLE.getData());
		ItemMeta runesmeta = runes.getItemMeta();
		runesmeta.setDisplayName(ChatColor.DARK_PURPLE + "Runes Help");
		lorelist.add(ChatColor.GRAY + "Click for Runes help");
		runesmeta.setLore(lorelist);
		runes.setItemMeta(runesmeta);
		lorelist.clear();

		inv.setItem(0, main);
		inv.setItem(1, customenchants);
		inv.setItem(2, tinkerer);
		inv.setItem(3, enchanter);
		inv.setItem(4, factions);
		inv.setItem(5, runes);
		return inv;
	}

	public static Inventory getRewarderInventory() {
		Inventory inv = Bukkit.createInventory(null, 9, "NightPvP Rewarder");
		ArrayList<String> lorelist = new ArrayList<String>();
		ItemStack main = new ItemStack(Material.EXP_BOTTLE);
		ItemMeta mainmeta = main.getItemMeta();
		mainmeta.setDisplayName(ChatColor.DARK_PURPLE + "Free 2k EXP");
		lorelist.add(ChatColor.GRAY + "You may redeem this every " + ChatColor.RED + "1 Day");
		mainmeta.setLore(lorelist);
		main.setItemMeta(mainmeta);
		lorelist.clear();

		ItemStack customenchants = new ItemStack(Material.PAPER);
		ItemMeta customenchantsmeta = customenchants.getItemMeta();
		customenchantsmeta.setDisplayName(ChatColor.DARK_PURPLE + "Free 5k Cash");
		lorelist.add(ChatColor.GRAY + "You may redeem this every " + ChatColor.RED + "1 Day");
		customenchantsmeta.setLore(lorelist);
		customenchants.setItemMeta(customenchantsmeta);
		lorelist.clear();

		ItemStack factions = new ItemStack(Material.TRIPWIRE_HOOK);
		ItemMeta factionsmeta = factions.getItemMeta();
		factionsmeta.setDisplayName(ChatColor.DARK_PURPLE + "2 Free Super Keys");
		lorelist.add(ChatColor.GRAY + "You may redeem this every " + ChatColor.RED + "2 Days");
		factionsmeta.setLore(lorelist);
		factions.setItemMeta(factionsmeta);
		lorelist.clear();

		ItemStack gkit = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta gkitmeta = gkit.getItemMeta();
		gkitmeta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + "Goodies Bundle");
		lorelist.add(ChatColor.RED + "Everything a starter needs!");
		lorelist.add(ChatColor.GRAY + "You may redeem this every " + ChatColor.RED + "2 Weeks");
		gkitmeta.setLore(lorelist);
		gkit.setItemMeta(gkitmeta);
		lorelist.clear();

		ItemStack book = new ItemStack(Material.BOOK);
		ItemMeta bookmeta = book.getItemMeta();
		bookmeta.setDisplayName(ChatColor.DARK_PURPLE + "Free Enchanting Books");
		lorelist.add(ChatColor.GRAY + "You may redeem this every " + ChatColor.RED + "2 Days");
		bookmeta.setLore(lorelist);
		book.setItemMeta(bookmeta);
		lorelist.clear();

		inv.setItem(0, main);
		inv.setItem(4, customenchants);
		inv.setItem(8, factions);
		inv.setItem(1, gkit);
		inv.setItem(7, book);
		return inv;
	}

	public static Inventory getDonatorInventory() {
		Inventory inv = Bukkit.createInventory(null, 9, "NightPvP DonorHelper");
		ArrayList<String> lorelist = new ArrayList<String>();
		ItemStack main = new ItemStack(Material.BOOK);
		ItemMeta mainmeta = main.getItemMeta();
		mainmeta.setDisplayName(ChatColor.GRAY + ChatColor.BOLD.toString() + "Undead Rank");
		lorelist.add(ChatColor.GREEN + "$10.00 USD");
		lorelist.add(ChatColor.GRAY + "Click for information on the UNDEAD rank");
		mainmeta.setLore(lorelist);
		main.setItemMeta(mainmeta);
		lorelist.clear();

		ItemStack customenchants = new ItemStack(Material.BOOK);
		ItemMeta customenchantsmeta = customenchants.getItemMeta();
		customenchantsmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Reaper Rank");
		lorelist.add(ChatColor.GREEN + "$20.00 USD");
		lorelist.add(ChatColor.GRAY + "Click for information on the REAPER rank");
		customenchantsmeta.setLore(lorelist);
		customenchants.setItemMeta(customenchantsmeta);
		lorelist.clear();

		ItemStack factions = new ItemStack(Material.BOOK);
		ItemMeta factionsmeta = factions.getItemMeta();
		factionsmeta.setDisplayName(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Demon Rank");
		lorelist.add(ChatColor.GREEN + "$30.00 USD");
		lorelist.add(ChatColor.GRAY + "Click for information on the DEMON rank");
		factionsmeta.setLore(lorelist);
		factions.setItemMeta(factionsmeta);
		lorelist.clear();

		ItemStack gkit = new ItemStack(Material.BOOK);
		ItemMeta gkitmeta = gkit.getItemMeta();
		gkitmeta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + "Hades Rank");
		lorelist.add(ChatColor.GREEN + "$40.00 USD");
		lorelist.add(ChatColor.GRAY + "Click for information on the Hades rank");
		gkitmeta.setLore(lorelist);
		gkit.setItemMeta(gkitmeta);
		lorelist.clear();

		ItemStack how = new ItemStack(Material.PAPER);
		ItemMeta howmeta = how.getItemMeta();
		howmeta.setDisplayName(ChatColor.RED + "How to Donate?");
		lorelist.add(ChatColor.GRAY + "Click for information on how to donate");
		howmeta.setLore(lorelist);
		how.setItemMeta(howmeta);
		lorelist.clear();

		inv.setItem(0, main);
		inv.setItem(1, customenchants);
		inv.setItem(2, factions);
		inv.setItem(3, gkit);
		inv.setItem(8, how);
		return inv;

	}

	public static Inventory getWorldSwitcher() {
		Inventory inv = Bukkit.createInventory(null, 27, "Night Transporter");
		ArrayList<String> lorelist = new ArrayList<String>();
		ItemStack main = new ItemStack(Material.PRISMARINE_SHARD);
		ItemMeta mainmeta = main.getItemMeta();
		mainmeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Adventure World");
		lorelist.add(ChatColor.GRAY + "Players in world: " + ChatColor.DARK_PURPLE
				+ Bukkit.getWorld("Map").getPlayers().size());
		lorelist.add(ChatColor.GRAY + "A large mountaneous world with custom terrain,");
		lorelist.add(ChatColor.GRAY + "dungeons, volcanoes, permafrost, and more!");
		lorelist.add(ChatColor.GREEN + "Available");
		lorelist.add(ChatColor.DARK_PURPLE + "Click to transport");
		lorelist.add(ChatColor.RED + "Paid Entry");
		mainmeta.setLore(lorelist);
		main.setItemMeta(mainmeta);
		lorelist.clear();

		ItemStack customenchants = new ItemStack(Material.PRISMARINE_SHARD);
		ItemMeta customenchantsmeta = customenchants.getItemMeta();
		customenchantsmeta.setDisplayName(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "Reminiscence World");
		lorelist.add(ChatColor.GRAY + "Players in world: " + ChatColor.LIGHT_PURPLE
				+ Bukkit.getWorld("Reminiscence").getPlayers().size());
		lorelist.add(ChatColor.GRAY + "A smaller world with custom terrain, custom mobs,");
		lorelist.add(ChatColor.GRAY + "stunning deserts and forests, with a mix of hilly and flat terrain");
		lorelist.add(ChatColor.LIGHT_PURPLE + "Blocks in this world reset 6 hours after mined/placed!");
		lorelist.add(ChatColor.LIGHT_PURPLE + "This is mainly used as an event/mess around world!");
		lorelist.add(ChatColor.GREEN + "Available");
		lorelist.add(ChatColor.LIGHT_PURPLE + "Click to transport");
		lorelist.add(ChatColor.RED + "Paid Entry");
		customenchantsmeta.setLore(lorelist);
		customenchants.setItemMeta(customenchantsmeta);
		lorelist.clear();

		ItemStack factions = new ItemStack(Material.PRISMARINE_SHARD);
		ItemMeta factionsmeta = factions.getItemMeta();
		factionsmeta.setDisplayName(ChatColor.YELLOW + ChatColor.BOLD.toString() + "The Moon");
		lorelist.add(
				ChatColor.GRAY + "Players in world: " + ChatColor.YELLOW + Bukkit.getWorld("Moon").getPlayers().size());
		lorelist.add(ChatColor.GRAY + "A miner's haven! You can only mine on the moon!");
		lorelist.add(ChatColor.GRAY + "The moon has plenty of ore to mine quickly, but pvp is enabled!");
		lorelist.add(ChatColor.GREEN + "Available");
		lorelist.add(ChatColor.YELLOW + "Click to transport");
		lorelist.add(ChatColor.RED + "Paid Entry");
		factionsmeta.setLore(lorelist);
		factions.setItemMeta(factionsmeta);
		lorelist.clear();

		ItemStack normal = new ItemStack(Material.PRISMARINE_SHARD);
		ItemMeta normalmeta = normal.getItemMeta();
		normalmeta.setDisplayName(ChatColor.GRAY + ChatColor.BOLD.toString() + "Normal World");
		lorelist.add(ChatColor.GRAY + "Players in world: " + ChatColor.YELLOW
				+ Bukkit.getWorld("Normal").getPlayers().size());
		lorelist.add(ChatColor.GRAY + "Your everyday minecraft world!");
		lorelist.add(ChatColor.GREEN + "Available");
		lorelist.add(ChatColor.YELLOW + "Click to transport");
		lorelist.add(ChatColor.RED + "Paid Entry");
		normalmeta.setLore(lorelist);
		normal.setItemMeta(normalmeta);
		lorelist.clear();

		ItemStack nether = new ItemStack(Material.PRISMARINE_SHARD);
		ItemMeta nethermeta = nether.getItemMeta();
		nethermeta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + "Hell World");
		lorelist.add(ChatColor.GRAY + "Players in world: " + ChatColor.YELLOW
				+ Bukkit.getWorld("world_nether").getPlayers().size());
		lorelist.add(ChatColor.GRAY + "Your everyday nether world!");
		lorelist.add(ChatColor.GREEN + "Available");
		lorelist.add(ChatColor.YELLOW + "Click to transport");
		lorelist.add(ChatColor.GREEN + "Free Entry");
		nethermeta.setLore(lorelist);
		nether.setItemMeta(nethermeta);
		lorelist.clear();

		ItemStack how = new ItemStack(Material.PAPER);
		ItemMeta howmeta = how.getItemMeta();
		howmeta.setDisplayName(ChatColor.RED + "How to Donate?");
		lorelist.add(ChatColor.GRAY + "Click for information on how to donate");
		howmeta.setLore(lorelist);
		how.setItemMeta(howmeta);
		lorelist.clear();

		inv.setItem(10, main);
		inv.setItem(11, customenchants);
		inv.setItem(12, normal);
		inv.setItem(13, nether);
		inv.setItem(16, factions);
		switcherinv = inv;
		return inv;
	}
	public static String databaseGetRank(String username12) {

		String alt = "";
		try {
            connection = MySQL.openConnection();

			Statement stmt = connection.createStatement();
			ResultSet resultset = stmt
					.executeQuery("SELECT rank FROM ranks WHERE name = '" + username12 + "';");
			String pass;
			if (resultset.next()) {
				pass = resultset.getString("rank");
		
				return pass;
			} else {
		
				return "null";
			}
		} catch (Exception e) {
			e.printStackTrace();
			

			return "null";
		}
	}
	public static boolean databaseContain(String username12) {

		String alt = "";
		try {
			connection = MySQL.openConnection();

			Statement stmt = connection.createStatement();
			ResultSet set = stmt
					.executeQuery("SELECT name FROM ranks WHERE name = '" + username12 + "';");
			if (!set.isBeforeFirst()) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
		
e.printStackTrace();
			return true;
		}
		

	}
	public static void databaseVerify(String username12) {

		String alt = "";
		try {
			connection = MySQL1.openConnection();

			Statement stmt = connection.createStatement();
			stmt
					.execute("INSERT INTO `verified`(`isverified`) VALUES ('"+username12+"')");
			
		} catch (Exception e) {
		Bukkit.broadcastMessage(e.toString());

			
		}
		

	}
	public static void databaseUnVerify(String username12) {

		String alt = "";
		try {
			connection = MySQL1.openConnection();

			Statement stmt = connection.createStatement();
			stmt
					.execute("INSERT INTO `unverified`(`unverified`) VALUES ('"+username12+"')");
			
		} catch (Exception e) {
		Bukkit.broadcastMessage(e.toString());

			
		}
		

	}
	public static boolean databaseContainsOp(String username12) {

		String alt = "";
		try {
			connection = MySQL.openConnection();

			Statement stmt = connection.createStatement();
			ResultSet set = stmt
					.executeQuery("SELECT name FROM ops WHERE name = '" + username12 + "';");
			if (!set.isBeforeFirst()) {
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
		
e.printStackTrace();
			return true;
		}

	}
}
