package tk.mythicaldimensions.warden.utility;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionDefault;

import tk.mythicaldimensions.warden.WardenPlugin;
/**
 * A utility class with various static methods to provide a clean easy API We
 * are declaring it as final because there are only utility methods and we don't
 * want anyone accidentally extending this class
 * 
 * @author [Empty]
 */
public class WardenUtil {
	// Permission string required to use the reload command
	public static final String WARDEN_RELOAD_PERM = "warden.reload";
	public static final String WARDEN_STAFF_PERM = "warden.staff";

	// List of all registered permissions
	public static final ArrayList<Permission> perms = new ArrayList<>();

	/**
	 * Prevent anyone from initializing this class as it is solely to be used for
	 * static utility
	 */
	private WardenUtil() {
	}
	
	/*
	 * Add to attachment list
	 */
	public static void addToAttachment(String playerName) {
		Player player = Bukkit.getPlayer(playerName);
		
		PermissionAttachment attachment = player.addAttachment(WardenPlugin.getInstance());
		WardenPlugin.playerAttachment.put(player.getUniqueId(), attachment);
	}
	
	/*
	 * remove from attachment list
	 */
	public static void removeFromAttachment(String playerName) {
		Player player = Bukkit.getPlayer(playerName);
		
		player.removeAttachment(WardenPlugin.playerAttachment.get(player.getUniqueId()));
		WardenPlugin.playerAttachment.remove(player.getUniqueId());
	}
	
	/*
	 * Check if in attachment list
	 */
	public static boolean checkIfInAttachment(String playerName) {
		boolean check  = false;
		Player player = Bukkit.getPlayer(playerName);
		
		for (UUID it : WardenPlugin.playerAttachment.keySet()) {
			if (it == player.getUniqueId()) {
				check = true;
				break;
			}
		}
		return check;
	}
	
	/*
	 * Remove all player attachments
	 */
	public static void unregisterPlayerPermissions() {
		for (UUID it : WardenPlugin.playerAttachment.keySet()) {
			Player player = Bukkit.getPlayer(it);
			
			removeFromAttachment(player.getDisplayName());
		}
	}
	
	/*
	 * Apply all perms
	 */
	public static void applyPerms(String playerName) {
		Player player = Bukkit.getPlayer(playerName);
		PermissionAttachment attachment = WardenPlugin.playerAttachment.get(player.getUniqueId());
		
		for (String it : WardenPlugin.getInternalPermissions().getPermissionsMap().keySet()) {
			if (WardenPlugin.getInternalPermissions().checkIfInGroup(player.getDisplayName(), it) 
					|| WardenPlugin.getInternalPermissions().checkIfDefault(it)) {
				
				if (WardenPlugin.getInternalPermissions().getPermissions(it) != null) {
					for (String perm : WardenPlugin.getInternalPermissions().getPermissions(it)) {
						if (perm.charAt(0) == '+') {
							attachment.setPermission(perm, true);
						} else if (perm.charAt(0) == '-') {
							attachment.setPermission(perm, false);
						}
					}
				}
			}
		}
	}
	
	/*
	 * Add chat stuff
	 */
	public static String getChatEffect(String playerName, String message) {
		Player player = Bukkit.getPlayer(playerName);
		String fullMessage = "";
		
		for (String it : WardenPlugin.getInternalPermissions().getPermissionsMap().keySet()) {
			if (WardenPlugin.getInternalPermissions().checkIfInGroup(player.getDisplayName(), it)) {
				
				String prefix = WardenPlugin.getInternalPermissions().getPrefix(it);
				String suffix = WardenPlugin.getInternalPermissions().getSuffix(it);
				
				if (prefix != null && suffix != null) {
					fullMessage = prefix + message + suffix;
				} else if (prefix != null) {
					fullMessage = prefix + message;
				} else if (suffix != null) {
					fullMessage = message + suffix;
				} else {
					fullMessage = message;
				}
			}
		}
		
		return fullMessage;
	}
		
	/**
	 * Register all Scpsl permissions
	 */
	public static void registerPermissions() {
		// Create the permissions and store them in a list
		// The list is mainly used internally but a getter could be used to grant other
		// developers access to the list
		// For a permission we only need to have a string representing the key, however
		// it's best to include the description and who has the permission by default
		perms.add(new Permission(WARDEN_RELOAD_PERM, "Allows players to reload the config", PermissionDefault.OP));
		perms.add(new Permission(WARDEN_STAFF_PERM, "Allows players to use staff", PermissionDefault.OP));

		// Loop through the list and add all the permissions we created
		for (Permission perm : perms) {
			Bukkit.getPluginManager().addPermission(perm);

			// Log a message that we added the permission
			WardenPlugin.getInstance().getLogger().fine("Registered Permission: " + perm.getName());
		}
	}

	/**
	 * Unregister all Scpsl permissions
	 */
	public static void unregisterPermissions() {
		// Remove all permissions that we created
		// Mainly used when disabling the plugin to prevent issues if the permissions
		// are changed and the plugin is enabled again (possibly an update?)
		// While using the /reload command is bad practice, many server owners will do
		// so anyway and that can cause issues if we don't clean up properly
		for (Permission perm : perms) {
			Bukkit.getPluginManager().removePermission(perm);

			// Log a message that we removed the permission
			WardenPlugin.getInstance().getLogger().fine("Unregistered Permission: " + perm.getName());
		}

		// Clear the list of permissions incase this method was called but the plugin
		// wasn't disabled
		// If we don't do this then calling registerPermissions() would result in trying
		// to register each permission twice
		perms.clear();
	}
}
