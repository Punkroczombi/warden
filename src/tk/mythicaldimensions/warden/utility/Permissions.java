package tk.mythicaldimensions.warden.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import tk.mythicaldimensions.warden.WardenPlugin;

/**
 * A utility class with various static methods to provide a clean easy API We
 * are declaring it as final because there are only utility methods and we don't
 * want anyone accidentally extending this class
 * 
 * @author [Empty]
 */
public class Permissions {
	
	/*
	 * Permissions map
	 */
	private HashMap<String, Object[]> permissionsMap = new HashMap<String, Object[]>();
	
	public Permissions() {
		setDefaults();
		loadPermissions();
	}
	
	/*
	 * Get Permissions
	 */
	public void loadPermissions() {
		try {
			File file = new File(WardenPlugin.getInstance().getDataFolder()+File.separator+"permissions.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			Object[] groupKeys = config.getConfigurationSection("Groups").getKeys(false).toArray();
			String[] groups = Arrays.asList(groupKeys).toArray(new String[groupKeys.length]);
			
			for (String it : groups) {
				Object[] obj = new Object[] {
					config.getConfigurationSection("Groups." + it + ".Options").getValues(false), //Options 0
					config.getString("Groups." + it + ".Prefix"), //Prefix 1
					config.getString("Groups." + it + ".Suffix"), //Suffix 2
					config.getStringList("Groups." + it + ".Perms"), //Perms 3
					config.getStringList("Groups." + it + ".Users"), //Users 4
					config.getStringList("Groups." + it + ".Parents") //Parents 5
				}; 
				
				permissionsMap.put(it, obj);
			}
			
			
		} catch (IOException e) {
			return;
		}
	}
	
	/*
	 * Save Permissions
	 */
	@SuppressWarnings("unchecked")
	public void savePermissions() {
		try {
			File file = new File(WardenPlugin.getInstance().getDataFolder()+File.separator+"permissions.yml");
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			Object[] groupKeys = config.getConfigurationSection("Groups").getKeys(false).toArray();
			String[] groups = Arrays.asList(groupKeys).toArray(new String[groupKeys.length]);
			
			for (String it : groups) {
				config.set("Groups." + it + ".Prefix", (String) getPermissionsMap().get(it)[1]);
				config.set("Groups." + it + ".Suffix", (String) getPermissionsMap().get(it)[2]);
				config.set("Groups." + it + ".Perms", (List<String>) getPermissionsMap().get(it)[3]);
				config.set("Groups." + it + ".Users", (List<String>) getPermissionsMap().get(it)[4]);
			}
			config.save(file);
			
		} catch (IOException e) {
			return;
		}
	}
	
	/*
	 * Set Default
	 */
	public void setDefaults() {
		try {
			File file = new File(WardenPlugin.getInstance().getDataFolder()+File.separator+"permissions.yml");
			List<String> empty = Arrays.asList();
			if (!file.exists()) {
			    file.createNewFile();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			config.addDefault("Groups.Default.Options.Default", true);
			config.addDefault("Groups.Default.Prefix", "");
			config.addDefault("Groups.Default.Suffix", "");
			config.addDefault("Groups.Default.Perms", empty);
			config.addDefault("Groups.Default.Users", empty);
			
			config.options().copyDefaults(true);
			config.save(file);
		} catch (IOException e) {
			return;
		}
	}
	
	/*
	 * New group /w parent, prefix, and suffix
	 */
	public void makeNewGroup(String groupName, String[] args) {
		try {
			File file = new File(WardenPlugin.getInstance().getDataFolder()+File.separator+"permissions.yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			List<String> empty = Arrays.asList();
			
			switch (args.length) {	
			case 0:
				config.set("Groups." + groupName + ".Options", empty);
				config.set("Groups." + groupName + ".Prefix", "");
				config.set("Groups." + groupName + ".Suffix", "");
				config.set("Groups." + groupName + ".Parent", empty);
				config.set("Groups." + groupName + ".Perms", empty);
				config.set("Groups." + groupName + ".Users", empty);
				break;
				
			case 1:
				config.set("Groups." + groupName + ".Options", "");
				config.set("Groups." + groupName + ".Prefix", args[1]);
				config.set("Groups." + groupName + ".Suffix", "");
				config.set("Groups." + groupName + ".Parent", empty);
				config.set("Groups." + groupName + ".Perms", empty);
				config.set("Groups." + groupName + ".Users", empty);
				break;
				
			case 2:
				config.set("Groups." + groupName + ".Options", "");
				config.set("Groups." + groupName + ".Prefix", args[1]);
				config.set("Groups." + groupName + ".Suffix", args[2]);
				config.set("Groups." + groupName + ".Parent", empty);
				config.set("Groups." + groupName + ".Perms", empty);
				config.set("Groups." + groupName + ".Users", empty);
				break;
				
			case 3:
				List<String> parents = new ArrayList<String>();
				parents.add(args[3]);
				
				config.set("Groups." + groupName + ".Options", "");
				config.set("Groups." + groupName + ".Prefix", args[1]);
				config.set("Groups." + groupName + ".Suffix", args[2]);
				config.set("Groups." + groupName + ".Parent", parents);
				config.set("Groups." + groupName + ".Perms", empty);
				config.set("Groups." + groupName + ".Users", empty);
				break;
			}
			
			config.save(file);
		} catch (IOException e) {
			return;
		}
	}
	
	/*
	 * Edit existing group
	 */
	@SuppressWarnings("unchecked")
	public void editGroup(String groupName, String[] args) {
		try {
			File file = new File(WardenPlugin.getInstance().getDataFolder()+File.separator+"permissions.yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			switch (args[0].toLowerCase()) {
			case "add":
				if (args[1].equalsIgnoreCase("world")) {
					config.set("Groups." + groupName + "." + args[2], args[3]);
				} else if (args[1].equalsIgnoreCase("perm")) {
					config.set("Groups." + groupName + ".Perms", ((List<String>) getPermissionsMap().get(groupName)[3]).add(args[2]));
				}
				break;
				
			case "remove":
				if (args[1].equalsIgnoreCase("world")) {
					int i = 0;
					List<String> perms = config.getStringList("Group." + groupName + "." + args[2]);
					for (String it : config.getStringList("Groups." + groupName + "." + args[2])) {
						i++;
						if (args[3] == it) {
							perms.remove(i);
							config.set("Groups." + groupName + "." + args[2], perms);
						}
					}
				} else if (args[1].equalsIgnoreCase("perm")) {
					int i = 0;
					for (String it : ((List<String>) getPermissionsMap().get(groupName)[3])) {
						i++;
						if (args[2] == it) {
							((List<String>) getPermissionsMap().get(groupName)[3]).remove(i);
						}
					}
				}
				break;
				
			case "parent":
				if (!args[1].isEmpty()) {
					List<String> parents = (List<String>) getPermissionsMap().get(groupName)[5];
					parents.add(args[2]);
				}
				break;
				
			case "prefix":
				if (!args[1].isEmpty()) {
					config.set("Groups." + groupName + ".Prefix", args[2]);
				}
				break;
				
			case "suffix":
				if (!args[1].isEmpty()) {
					config.set("Groups." + groupName + ".Suffix", args[2]);
				}
				break;
				
			case "delete":
				config.set("Groups." + groupName, null);
				break;
				
			default:
				break;
			}
			
			config.save(file);
		} catch (IOException e) {
			return;
		}
	}
	
	/*
	 * Reload Permissions
	 */
	public void reloadPermissions() {
		loadPermissions();
	}
	
	/*
	 * Get permissionsMap
	 */
	public HashMap<String, Object[]> getPermissionsMap() {
		return permissionsMap;
	}
	
	/*
	 * Get permissions
	 */
	@SuppressWarnings("unchecked")
	public String[] getPermissions(String groupName) {
		File file = new File(WardenPlugin.getInstance().getDataFolder()+File.separator+"permissions.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		List<String> parents = (List<String>) getPermissionsMap().get(groupName)[5];
		List<String> perms = (List<String>) getPermissionsMap().get(groupName)[3];
		
		for (String it : parents) {
			for (String perm : config.getStringList("Groups." + it + ".Perms")) {
				perms.add(perm);
			}
		}
		
		return perms.toArray(new String[perms.size()]);
	}
	
	/*
	 * Get Prefix
	 */
	public String getPrefix(String groupName) {
		return (String) getPermissionsMap().get(groupName)[1];
	}
	
	/*
	 * Get Suffix
	 */
	public String getSuffix(String groupName) {
		return (String) getPermissionsMap().get(groupName)[2];
	}
	
	/*
	 * Check if in group
	 */
	@SuppressWarnings("unchecked")
	public boolean checkIfInGroup(String playerName, String groupName) {
		boolean check = false;
		
		for (String it : (List<String>) getPermissionsMap().get(groupName)[4]) {
			if (it == playerName) {
				check = true;
				break;
			}
		}
		return check;
	}
	
	/*
	 * Check if default
	 */
	@SuppressWarnings("rawtypes")
	public boolean checkIfDefault(String groupName) {
		boolean check = (boolean) ((LinkedHashMap) getPermissionsMap().get(groupName)[0]).get("Default");
		return check;
	}
}
