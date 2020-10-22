package tk.mythicaldimensions.warden;

import java.util.HashMap;
import java.util.UUID;
/**
 * The main class that extends JavaPlugin This is where we register any event
 * listeners or take any actions that are required on startup or shutdown
 * 
 * @author [Empty]
 */
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import tk.mythicaldimensions.warden.commands.StaffCommands;
import tk.mythicaldimensions.warden.commands.WardenCommands;
import tk.mythicaldimensions.warden.config.WardenConfig;
import tk.mythicaldimensions.warden.events.WardenEvents;
import tk.mythicaldimensions.warden.utility.Permissions;
import tk.mythicaldimensions.warden.utility.WardenUtil;

public class WardenPlugin extends JavaPlugin {
	
	// Logger instance for logging debug messages and information about what the plugin is doing
	private final Logger logger = Logger.getLogger("Warden");
	
	// Our internal config object for storing the configuration options that server
	private static WardenConfig config;
	
	// Our internal permissions object
	private static Permissions perms;

	// An instance of this plugin for easy access
	private static WardenPlugin plugin;
	
	// Player Attachment
	public static HashMap<UUID, PermissionAttachment> playerAttachment = new HashMap<UUID, PermissionAttachment>();
	// Player staff inventory
	public static HashMap<String, Inventory> staffInv = new HashMap<String, Inventory>();
	// Player staff state
	public static HashMap<String, Boolean> staffState = new HashMap<String, Boolean>();
	
	// Heart Beat
	public static int beatTime = 0;
	// Start time
	public static long startTime = System.currentTimeMillis();
	
	// Debug
	public static boolean debug = false;
	
	/**
	 * Ran when plugin is enabled Set static instance of this class Register event
	 * listeners Create configuration object Set command executor Register
	 * permissions
	 */
	@Override
	public void onEnable() {
		plugin = this;
		// Register Events
		getLogger().info("[Warden] Registering Events");
		Bukkit.getPluginManager().registerEvents(new WardenEvents(), this);
		// Getting config
		getLogger().info("[Warden] Getting Config");
		config = new WardenConfig();
		// Getting permissions
		getLogger().info("[Warden] Loading Permissions");
		perms = new Permissions();
		// Register Commands
		getLogger().info("[Warden] Registering Commands");
		this.getCommand("warden").setExecutor(new WardenCommands());
		this.getCommand("staff").setExecutor(new StaffCommands());
		// Register Perms
		getLogger().info("[Warden] Registering Permissions");
		WardenUtil.registerPermissions();
		getLogger().info("[Warden] Plugin Enabled");
		
		/**
		 * Ran constantly aslong the plugin is registered Controls all plugin logic
		 */
		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				/** 
				 * Timing
				 */
				long elapsedTime = System.currentTimeMillis() - startTime;
				long elapsedSeconds = elapsedTime / 1000;
				beatTime = (int) elapsedSeconds;
				
				/**
				 *  Debug scripts
				 */
				if (elapsedSeconds % 60 == 0 && debug == true) {
					// Heart Beat
					getLogger().info("[SCPSL] Beep Boop Uptime: [" + elapsedSeconds + "] s");
				}
				
			}
		}, 0L, 20L);
	}
	
	/**
	 * Ran when plugin is disabled Remove permissions to clean
	 * up in case plugin is added again before server restart
	 */
	@Override
	public void onDisable() {
		getLogger().info("[Warden] Removing Attachments");
		WardenUtil.unregisterPlayerPermissions();
		getLogger().info("[Warden] Saving Perms");
		getInternalPermissions().savePermissions();
		getLogger().info("[Warden] Plugin Disabled");
	}
	/**
	 * Gets the logger for this plugin
	 */
	@Override
	public Logger getLogger() {
		return logger;
	}

	/**
	 * Gets an instance of this plugin
	 * 
	 * @return The static instance of this plugin
	 */
	public static WardenPlugin getInstance() {
		return plugin;
	}

	/**
	 * Gets the internal config
	 * 
	 * @return The internal config
	 */
	public static WardenConfig getInternalConfig() {
		return config;
	}
	
	/**
	 * Gets the internal perms
	 * 
	 * @return The internal perms
	 */
	public static Permissions getInternalPermissions() {
		return perms;
	}
}
