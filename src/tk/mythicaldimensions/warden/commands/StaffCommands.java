package tk.mythicaldimensions.warden.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tk.mythicaldimensions.warden.WardenPlugin;
import tk.mythicaldimensions.warden.utility.WardenUtil;

public class StaffCommands implements CommandExecutor {
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
		
		// Check for perms and if player
		if (sender.hasPermission(WardenUtil.WARDEN_STAFF_PERM)) {
			Player player = Bukkit.getPlayer(sender.getName());
				
			try {
				if (!WardenPlugin.staffState.get(player.getDisplayName())) {
					int i = 0;
					
					// Save inv
					WardenPlugin.staffInv.put(player.getDisplayName(), player.getInventory());
					
					// Set staff inv
					player.getInventory().clear();
					
					ItemStack freeze = new ItemStack(Material.BLAZE_ROD); ItemMeta freezeMeta = freeze.getItemMeta();
					freezeMeta.setDisplayName("Freeze"); freeze.setItemMeta(freezeMeta);
					ItemStack staffTicket = new ItemStack(Material.NAME_TAG); ItemMeta ticketMeta = staffTicket.getItemMeta();
					ticketMeta.setDisplayName("Ticket"); staffTicket.setItemMeta(ticketMeta);
					ItemStack teleport = new ItemStack(Material.ENDER_PEARL); ItemMeta teleportMeta = teleport.getItemMeta();
					teleportMeta.setDisplayName("Teleport"); teleport.setItemMeta(teleportMeta);
					ItemStack peak = new ItemStack(Material.CHEST); ItemMeta peakMeta = peak.getItemMeta();
					peakMeta.setDisplayName("Peak"); peak.setItemMeta(peakMeta);
					ItemStack findPlayer = new ItemStack(Material.COMPASS); ItemMeta findPlayerMeta = findPlayer.getItemMeta();
					findPlayerMeta.setDisplayName("Find Player"); findPlayer.setItemMeta(findPlayerMeta);
					ItemStack activeStaff = new ItemStack(Material.CLOCK); ItemMeta staffMeta = activeStaff.getItemMeta();
					staffMeta.setDisplayName("Vanish"); activeStaff.setItemMeta(staffMeta);
					ItemStack drag = new ItemStack(Material.FISHING_ROD); ItemMeta dragMeta = drag.getItemMeta();
					dragMeta.setDisplayName("Drag"); drag.setItemMeta(dragMeta);
					ItemStack ban = new ItemStack(Material.IRON_AXE); ItemMeta banMeta = ban.getItemMeta();
					banMeta.setDisplayName("Ban"); ban.setItemMeta(freezeMeta);
					ItemStack mute = new ItemStack(Material.IRON_SWORD); ItemMeta muteMeta = mute.getItemMeta();
					muteMeta.setDisplayName("Mute"); mute.setItemMeta(muteMeta);
					
					ItemStack[] inv = new ItemStack[] {
						freeze, staffTicket, teleport, peak,
						findPlayer, activeStaff, drag, ban, mute
					};
					for (ItemStack it : inv) {
						player.getInventory().setItem(i, it); 
						i++;
					}
					
					// Change staff state
					WardenPlugin.staffState.put(player.getDisplayName(), true);
					
					// Change player gamemode
					player.setGameMode(GameMode.CREATIVE);
				} else {
					int i = 0;
					
					// Set player inv
					player.getInventory().clear();
					
					for (ItemStack it : WardenPlugin.staffInv.get(player.getDisplayName())) {
						player.getInventory().setItem(i, it);
						i++;
					}
					
					// Change staff state
					WardenPlugin.staffState.put(player.getDisplayName(), false);
					
					// Change player gamemode
					player.setGameMode(GameMode.SURVIVAL);
				}
			} catch (NullPointerException e) {
				WardenPlugin.staffState.put(player.getDisplayName(), false);
			}
		}
		
		return commandReturn;
	}
}
