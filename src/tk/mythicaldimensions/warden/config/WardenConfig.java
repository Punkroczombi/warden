package tk.mythicaldimensions.warden.config;

import org.bukkit.configuration.file.FileConfiguration;

import tk.mythicaldimensions.warden.WardenPlugin;

/**
 * An internal config class used to store all variables from the plugin's
 * FileConfiguration Storing the variables in an object like this is a much more
 * efficient method than constantly accessing the config.yml
 * 
 * @author [Empty]
 */
public final class WardenConfig {

	// Keys for accessing information in this plugin's FileConfiguration
	// We are saving these to avoid "Magic Values"
	// What that means is that instead of directly inputing the keys every time we
	// want to access the FileConfiguration,
	// we use these constant values to ensure no typos as well as clarity. If a key
	// needs to be changed, it only needs to be updated in one place
	// In the FileConfiguration, a period '.' represents a break,
	// therefore the string after the period is a ConfigurationSection "underneath"
	// ConfigurationSection represented by the string before the period
	public static final String NO_PERM_KEY = "locale.noperm";
	public static final String INVALID_PLAYER_KEY = "locale.invalidplayer";
	public static final String ONLY_PLAYERS_KEY = "locale.onlyplayers";
	public static final String CONFIG_RELOAD_KEY = "locale.reload";
	public static final String INVALID_COMMAND_KEY = "locale.invalid";
	public static final String RCON_ALLOW_KEY = "locale.rcon";

	// Message strings
	// We are going to use these to store the messages from the FileConfiguration
	private String noPermMessage, invalidPlayerMessage, onlyPlayersMessage, configReloadedMessage,
			invalidCommandMessage;
	// Test
	// Booleans
	private boolean allowRcon;
	
	/**
	 * Default config constructor Once we create the Config object we want to ensure
	 * that the defaults are set and that we load whatever data is in the file
	 */
	public WardenConfig() {
		setDefaults();
		loadConfig();
	}

	/**
	 * Load data from plugin FileConfiguration Here we load all information from the
	 * FileConfiguration to our internal variables
	 */
	public void loadConfig() {
		// Get this plugin's FileConfiguration object
		FileConfiguration config = WardenPlugin.getInstance().getConfig();

		// Load all the values from the FileConfiguration into our internal config
		// The string key is required to get the value, however the second provides a
		// default in case it was unable to get a value
		noPermMessage = config.getString(NO_PERM_KEY, "&4You do not have permission for that!");
		invalidPlayerMessage = config.getString(INVALID_PLAYER_KEY, "&4That is not a valid player!");
		onlyPlayersMessage = config.getString(ONLY_PLAYERS_KEY, "&4YOnly players can enter that command!");
		configReloadedMessage = config.getString(CONFIG_RELOAD_KEY, "&F[&4Warden Config Reloaded&F]");
		invalidCommandMessage = config.getString(INVALID_COMMAND_KEY, "&4Invalid Command");
		allowRcon = config.getBoolean(RCON_ALLOW_KEY, false);
	}

	/**
	 * Set default FileConfiguration This will create the file if it doesn't exist
	 * This will also setup any default values and write them to the config.yml file
	 * if they are missing You may have seen people using saveDefaultConfig() to
	 * copy a config.yml file from the plugin jar, however I personally don't like
	 * this method because if we add values to the config then they won't be copied
	 * over if the config.yml file already exists
	 */
	public void setDefaults() {
		// Get this plugin's FileConfiguration object
		FileConfiguration config = WardenPlugin.getInstance().getConfig();

		// Set the default values using a string key plus the value we want to set
		config.addDefault(NO_PERM_KEY, "&4You do not have permission for that!");
		config.addDefault(INVALID_PLAYER_KEY, "&4That is not a valid player!");
		config.addDefault(ONLY_PLAYERS_KEY, "&4YOnly players can enter that command!");
		config.addDefault(CONFIG_RELOAD_KEY, "&F[&4Warden Config Reloaded&F]");
		config.addDefault(INVALID_COMMAND_KEY, "&4Invalid Command");;
		config.addDefault(RCON_ALLOW_KEY, false);

		// Copy the defaults that we set back to the FileConfiguration object
		config.options().copyDefaults(true);

		// Last step is to actually write the defaults to the config.yml file
		WardenPlugin.getInstance().saveConfig();
	}

	/**
	 * Save the FileConfiguration Currently unused in our plugin but can be useful
	 * if values can be updated from in game or if another plugin updates the values
	 * It's often easier to create these methods in the beginning so they already
	 * exist if we need them in the future For example if we add a way to change the
	 * config in game we already have an easy way to save their changes
	 */
	public void saveConfig() {
		// Get this plugin's FileConfiguration object
		FileConfiguration config = WardenPlugin.getInstance().getConfig();

		// Save any updated values from our internal config to the FileConfiguration
		// object
		config.set(NO_PERM_KEY, noPermMessage);
		config.set(INVALID_PLAYER_KEY, invalidPlayerMessage);
		config.set(ONLY_PLAYERS_KEY, onlyPlayersMessage);
		config.set(CONFIG_RELOAD_KEY, configReloadedMessage);
		config.set(INVALID_COMMAND_KEY, invalidCommandMessage);
		config.set(RCON_ALLOW_KEY, allowRcon);

		// We always need to remember to save the plugin's updated FileConfiguration
		// object to write to file
		WardenPlugin.getInstance().saveConfig();
	}

	/**
	 * Reloads the config Currently does nothing more than loadConfig() Can be used
	 * in some situations if any specific actions are required to unload config
	 * before loading values again
	 */
	public void reloadConfig() {
		loadConfig();
	}

	/**
	 * Gets the No Permission message
	 * 
	 * @return The No Permission message
	 */
	public String getNoPermMessage() {
		return noPermMessage;
	}

	/**
	 * Sets the No Permission message
	 * 
	 * @param The No Permission message
	 */
	public void setNoPermMessage(String message) {
		this.noPermMessage = message;
	}

	/**
	 * Gets the Invalid Player message
	 * 
	 * @return The Invalid Player message
	 */
	public String getInvalidPlayerMessage() {
		return invalidPlayerMessage;
	}

	/**
	 * Sets the Invalid Player message
	 * 
	 * @param The Invalid Player message
	 */
	public void setInvalidPlayerMessage(String message) {
		this.invalidPlayerMessage = message;
	}

	/**
	 * Gets the Only Players message
	 * 
	 * @return The Only Playersn message
	 */
	public String getOnlyPlayersMessage() {
		return onlyPlayersMessage;
	}

	/**
	 * Sets the Only Players message
	 * 
	 * @param The Only Players message
	 */
	public void setOnlyPlayersMessage(String message) {
		this.onlyPlayersMessage = message;
	}

	/**
	 * Gets the Config Reloaded message
	 * 
	 * @return The Config Reloaded message
	 */
	public String getConfigReloadedMessage() {
		return configReloadedMessage;
	}

	/**
	 * Sets the Config Reloaded message
	 * 
	 * @param The Config Reloaded message
	 */
	public void setConfigReloadedMessage(String message) {
		this.configReloadedMessage = message;
	}

	/**
	 * Gets the Invalid message
	 * 
	 * @return The Invalid message
	 */
	public String getCommandInvalidMessage() {
		return invalidCommandMessage;
	}

	/**
	 * Sets the Invalid Command message
	 * 
	 * @param The Invalid Command message
	 */
	public void setInvalidCommandMessage(String message) {
		this.invalidCommandMessage = message;
	}
	
	/**
	 * Gets if Rcon is allowed
	 */
	public boolean getAllowRcon() {
		return allowRcon;
	}
	
	/**
	 * Sets if Rcon is allowed
	 */
	public void setAllowRcon(boolean state) {
		this.allowRcon = state;
	}
}
