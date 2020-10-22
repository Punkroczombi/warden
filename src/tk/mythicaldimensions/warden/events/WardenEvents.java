package tk.mythicaldimensions.warden.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;

import tk.mythicaldimensions.warden.WardenPlugin;
import tk.mythicaldimensions.warden.utility.WardenUtil;

/**
 * A listener class containing all events for our plugin Currently there is only
 * one event, however we could easily add more
 * 
 * @author [Empty]
 */
public class WardenEvents implements Listener {
	
	// Player Join
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		// Add to Attachment List
		WardenUtil.addToAttachment(player.getDisplayName());
		
		// Apply Perms
		WardenUtil.applyPerms(player.getDisplayName());
		
		if (WardenPlugin.debug) {
			WardenPlugin.getInstance().getLogger().info("[Warden] Player " + player.getDisplayName() + " was added to Player List");
		}
	}
	
	// Player leave
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		// Remove from Attachment List
		WardenUtil.removeFromAttachment(player.getDisplayName());
		
		if (WardenPlugin.debug) {
			WardenPlugin.getInstance().getLogger().info("[Warden] Player " + player.getDisplayName() + " was removed from Player List");
		}
	}
	
	// Rcon server command
	@EventHandler
	public void onRconCommand(RemoteServerCommandEvent event) {
		if (!WardenPlugin.getInternalConfig().getAllowRcon()) {
			event.setCancelled(true);
		}
	}
	
	// Player Chat
	@EventHandler
	public void onAsyncChatEvent(AsyncPlayerChatEvent event) {
		//Player player = event.getPlayer();
		//String message = event.getMessage();
			
		//event.setMessage(WardenUtil.getChatEffect(player.getDisplayName(), message));
	}
	
	// Block break event
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		try {
			if (WardenPlugin.staffState.get(player.getDisplayName())) {
				event.setCancelled(true);
			}
		} catch (NullPointerException e) {
			return;
		}
	}
	
	// Block place event
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		
		try {
			if (WardenPlugin.staffState.get(player.getDisplayName())) {
				event.setCancelled(true);
			}
		} catch (NullPointerException e) {
			return;
		}
	}
	
	// Item pickup event
	@EventHandler 
	public void onEnitityPickupItemEvent(EntityPickupItemEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			try {
				if (WardenPlugin.staffState.get(player.getDisplayName())) {
					event.setCancelled(true);
				}
			} catch (NullPointerException e) {
				return;
			}
		}
	}
	
	// Item drop event
	@EventHandler 
	public void onEnitityDropItemEvent(EntityDropItemEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			try {
				if (WardenPlugin.staffState.get(player.getDisplayName())) {
					event.setCancelled(true);
				}
			} catch (NullPointerException e) {
				return;
			}
		}
	}
	
	// Player interact event
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		try {
			if (WardenPlugin.staffState.get(player.getDisplayName())) {
				switch (event.getItem().getItemMeta().getDisplayName()) {
				case "Freeze":
					
					break;
					
				case "Ticket":
					
					break;
					
				case "Teleport":
					
					break;
					
				case "Peak":
					
					break;
					
				case "Find Player":
					
					break;
					
				case "Vanish":
					for (Player it : Bukkit.getServer().getOnlinePlayers()) {
						it.hidePlayer(WardenPlugin.getInstance(), player);
					}
					break;
					
				case "Drag":
					
					break;
					
				case "Ban":
					
					break;
					
				case "Mute":
					
					break;
				}
			}
		} catch (NullPointerException e) {
			return;
		}
	}
	
	// Inventory click event
	@EventHandler
	public void onPlayerInventoryClickEvent(InventoryClickEvent event) {
		Player player = (Player) event.getView().getPlayer();
		
		try {
			if (WardenPlugin.staffState.get(player.getDisplayName())) {
				event.setCancelled(true);
			}
		} catch (NullPointerException e) {
			return;
		}
	}
}
