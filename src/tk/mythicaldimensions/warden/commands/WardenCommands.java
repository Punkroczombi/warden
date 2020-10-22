package tk.mythicaldimensions.warden.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import tk.mythicaldimensions.warden.WardenPlugin;
import tk.mythicaldimensions.warden.utility.WardenUtil;

/**
 * A command executor class that is used whenever the Essentials command is run
 * It's best practice to have each command in its own class implementing
 * CommandExecutor It makes our code much more clean and ensures that our
 * onCommand() will only be executed for the command this executor is registered
 * to
 * 
 * @author [Empty]
 */
public class WardenCommands implements CommandExecutor {

	/**
	 * Executed when Essentials command is run CommandSender will generally be a Player,
	 * Command Block, or Console but we should always check before doing an action
	 * that not all of them support Command will always be the command that this
	 * executor is registered to: In this case essentials label is in the case that an
	 * alias is used instead of Essentials We don't really need to worry about this but
	 * be aware that it might not be the same as the name of the command args is an
	 * array of all other arguments entered, we always want to check the length of
	 * args in case there aren't as many as you would expect!
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean commandReturn = false;
		
		// Check for 7 args
		if (args.length == 7) {
			// Check for groups
			if (args[0].equalsIgnoreCase("group")) {
				// Check for perms
				if (sender.hasPermission(WardenUtil.WARDEN_RELOAD_PERM)) {
					// Check if edit
					if (args[1].equalsIgnoreCase("edit")) {
						String[] arg = new String[]{
							args[3],
							args[4],
							args[5],
							args[6]
						};
						WardenPlugin.getInternalPermissions().makeNewGroup(args[2], arg);

						commandReturn = true;
					}
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					WardenPlugin.getInternalConfig().getNoPermMessage()));
			
					// Return false
					commandReturn = false;
				}
			}
		// Check for 6 args
		} else if (args.length == 6) {
			// Check for groups
			if (args[0].equalsIgnoreCase("group")) {
				// Check for perms
				if (sender.hasPermission(WardenUtil.WARDEN_RELOAD_PERM)) {
					// Check if create
					if (args[1].equalsIgnoreCase("create")) {
						String[] arg = new String[]{
							args[3],
							args[4],
							args[5]
						};
						WardenPlugin.getInternalPermissions().makeNewGroup(args[2], arg);
						
						commandReturn = true;
						
					// Check if edit
					} else if (args[1].equalsIgnoreCase("edit")) {
						String[] arg = new String[]{
							args[3],
							args[4],
							args[5]
						};
						WardenPlugin.getInternalPermissions().makeNewGroup(args[2], arg);

						commandReturn = true;
					}
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						WardenPlugin.getInternalConfig().getNoPermMessage()));
	
					// Return false
					commandReturn = false;
				}
			}
		// Check for 5 args
		} else if (args.length == 5) {
			// Check for groups
			if (args[0].equalsIgnoreCase("group")) {
				// Check for perms
				if (sender.hasPermission(WardenUtil.WARDEN_RELOAD_PERM)) {
					// Check if create
					if (args[1].equalsIgnoreCase("create")) {
						String[] arg = new String[]{
							args[3],
							args[4]
						};
						WardenPlugin.getInternalPermissions().makeNewGroup(args[2], arg);

						commandReturn = true;
					
					// Check if edit
					} else if (args[1].equalsIgnoreCase("edit")) {
						String[] arg = new String[]{
							args[3],
							args[4]
						};
						WardenPlugin.getInternalPermissions().makeNewGroup(args[2], arg);

						commandReturn = true;
					}
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						WardenPlugin.getInternalConfig().getNoPermMessage()));
				
					// Return false
					commandReturn = false;
				}
			}
		// Check for 4 args
		} else if (args.length == 4) {
			// Check for groups
			if (args[0].equalsIgnoreCase("group")) {
				// Check for perms
				if (sender.hasPermission(WardenUtil.WARDEN_RELOAD_PERM)) {
					// Check if create
					if (args[1].equalsIgnoreCase("create")) {
						String[] arg = new String[]{
							args[3],
						};
						WardenPlugin.getInternalPermissions().makeNewGroup(args[2], arg);

						commandReturn = true;
								
					// Check if edit
					} else if (args[1].equalsIgnoreCase("edit")) {
						String[] arg = new String[]{
							args[3],
						};
						WardenPlugin.getInternalPermissions().makeNewGroup(args[2], arg);
						
						commandReturn = true;
					}
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							WardenPlugin.getInternalConfig().getNoPermMessage()));
							
					// Return false
					commandReturn = false;
				}
			}
		// Check for 3 args
		} else if (args.length == 3) {
			// Check for groups
			if (args[0].equalsIgnoreCase("group")) {
				// Check for perms
				if (sender.hasPermission(WardenUtil.WARDEN_RELOAD_PERM)) {
					// Check if create
					if (args[1].equalsIgnoreCase("create")) {
						String[] arg = new String[0];
						WardenPlugin.getInternalPermissions().makeNewGroup(args[2], arg);

						commandReturn = true;
					}
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							WardenPlugin.getInternalConfig().getNoPermMessage()));
							
					// Return false
					commandReturn = false;
				}
			}
		// Check for 1 args
		} else if (args.length == 1) {
			// Check for reload
			if (args[0].equalsIgnoreCase("reload")) {
				// Check for perms
				if (sender.hasPermission(WardenUtil.WARDEN_RELOAD_PERM)) {
					// Reload internal config
					WardenPlugin.getInternalConfig().reloadConfig();
					WardenPlugin.getInternalPermissions().reloadPermissions();
							
					// Send reload message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							WardenPlugin.getInternalConfig().getConfigReloadedMessage()));

					// Return true
					commandReturn = true;

					// Sender doesn't have perm
				} else {
					// Send a no Perm message
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							WardenPlugin.getInternalConfig().getNoPermMessage()));

					// Return false
					commandReturn = false;
				}
						
			// Check for help
			} else if (args[0].equalsIgnoreCase("help")) {
				// Send help message
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&F/warden &6group &Fcreate &6<name, prefix, suffix, parent>" 
						+ "\n&F/warden &6group &Fedit &6<add-parent-delete> &F<perm-world-parent>"
						+ "\n&F/warden &6reload" + "\n&F/warden &6debug" + "\n&F/warden &6help"));
				
				// Return true
				commandReturn = true;
					
			// Check for debug
			} else if (args[0].equalsIgnoreCase("debug")) {
				// Check for perms
				if (sender.hasPermission(WardenUtil.WARDEN_RELOAD_PERM)) {
					// check the state of debug
					if (WardenPlugin.debug == true) {
						// Send message to sender and set false
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"Debug is &6Disabled"));
						WardenPlugin.debug = false;
					} else {
						// Send message to sender and set true
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"Debug is &6Enabled"));
						WardenPlugin.debug = true;
					}
							
				// Return true
				commandReturn = true;
				}
		
				return commandReturn;
			} else {
				// Send invalid
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						WardenPlugin.getInternalConfig().getCommandInvalidMessage()));

				// Return false
				commandReturn = false;
			}
		
		} else {
			// Return invalid message
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					WardenPlugin.getInternalConfig().getCommandInvalidMessage()));

			// Return false
			commandReturn = false;
		}
		return commandReturn;
	}
}