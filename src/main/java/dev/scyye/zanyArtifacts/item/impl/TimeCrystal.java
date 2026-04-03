package dev.scyye.zanyArtifacts.item.impl;

import dev.scyye.zanyArtifacts.Main;
import dev.scyye.zanyArtifacts.Utils;
import dev.scyye.zanyArtifacts.item.AttributeData;
import dev.scyye.zanyArtifacts.item.EnchantmentData;
import dev.scyye.zanyArtifacts.item.ZanyItem;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class TimeCrystal extends ZanyItem {
	public static boolean started = false;
	public TimeCrystal(String id, ItemStack itemStack, int amount, String displayName, boolean unbreakable, EnchantmentData[] enchantments, AttributeData[] attributes, ItemFlag[] itemFlags, String[] lore, AbilityMeta[] abilities) {
		super(id, itemStack, amount, displayName, unbreakable, enchantments, attributes, itemFlags, lore, abilities);
	}

	public record LocationState(Location location, Player player) {}
	public record InventoryState(PlayerInventory inventory, Player player) {}
	public record ChangedBlock(Block previous, Block current) {}

	static HashMap<Location, Material> changedBlocks = new HashMap<>();
	List<LocationState> playerLocations = new ArrayList<>();
	List<World> worldStates = new ArrayList<>();
	List<HashMap<Location, Material>> historyBlocks = new ArrayList<>();
	List<InventoryState> playerInventory = new ArrayList<>();

	int entryCount = 0;

	@Override
	public void onItemCreate(ItemStack var1) {
		System.out.println("Starting State Saver Task...");
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					playerLocations.add(new LocationState(player.getLocation(), player));
					playerInventory.add(new InventoryState(player.getInventory(), player));
				}
				if (Bukkit.getOnlinePlayers().isEmpty()) {
					playerLocations.add(new LocationState(new Location(Bukkit.getServer().getWorld("world"), 0, 0, 0), null));
					playerInventory.add(new InventoryState(null, null));
				}
				worldStates.add(Bukkit.getServer().getWorld("world"));

				historyBlocks.add(new HashMap<>(changedBlocks));
				changedBlocks.clear();
				entryCount++;

				System.out.println("Saved state #" + entryCount);
			}
		};
		Timer timer = new Timer();
		if (!started)
			timer.schedule(task, 1000, 1000);
		started = true;
	}

	@Override
	public void updateItem(Player player, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<Component> lore = meta.lore();
		assert lore != null;
		lore.set(0, Component.text("Saved States: " + entryCount));
		meta.lore(lore);
		item.setItemMeta(meta);
	}

	@Override
	public void leftClickAirAction(Player var1, ItemStack var2) {
		// Code here
		Location location;
		World world;
		PlayerInventory inventory;
		System.out.println("Attempting to revert to state from .5 minute ago...");
		System.out.println("Current entry count: " + entryCount);
		System.out.println(playerLocations.size());
		System.out.println(worldStates.size());
		System.out.println(playerInventory.size());

		HashMap<Location, Material> previousBlocks = historyBlocks.get(entryCount-30);
		for (Map.Entry<Location, Material> entry : previousBlocks.entrySet()) {
			System.out.println("Reverting block at " + entry.getKey() + " to " + entry.getValue());
			Location loc = entry.getKey();
			Material previousBlock = entry.getValue();
			Block currentBlock = loc.getBlock();
			if (!currentBlock.getType().equals(previousBlock)) {
				System.out.println("Block type differs. Reverting...");
				currentBlock.setType(previousBlock);
			}
		}

		try {
			world = worldStates.get(entryCount-30);
			inventory = playerInventory.get(entryCount-30).inventory;
			location = playerLocations.get(entryCount-30).location;
		} catch (IndexOutOfBoundsException e) {
			var1.sendMessage("Missing state data.");
			return;
		}

		List<HashMap<Location, Material>> blocksToChange = historyBlocks.subList(entryCount-30, entryCount);
		for (HashMap<Location, Material> blockMap : blocksToChange) {
			for (Map.Entry<Location, Material> entry : blockMap.entrySet()) {
				Location loc = entry.getKey();
				Material previousBlock = entry.getValue();
				Block currentBlock = loc.getBlock();
				if (!currentBlock.getType().equals(previousBlock)) {
					currentBlock.setType(previousBlock);
				}
			}
		}
		if (location != null && world != null && inventory != null) {
			var1.teleport(location);
			var1.getInventory().setContents(inventory.getContents());
			var1.sendMessage("Reverted to state from .5 minute ago!");
			for (int i = 0; i < 30; i++) {
				playerLocations.removeLast();
				worldStates.removeLast();
				playerInventory.removeLast();
				historyBlocks.removeLast();
				entryCount--;
			}
		} else {
			var1.sendMessage("No saved state from .5 minute ago.");
		}
	}

	@Override
	public void shiftLeftClickAirAction(Player var1, ItemStack var2) {
		leftClickAirAction(var1, var2);
	}

	@Override
	public void leftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
		leftClickAirAction(var1, var4);
	}

	@Override
	public void shiftLeftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
		leftClickAirAction(var1, var4);
	}

	@Override
	public void rightClickAirAction(Player var1, ItemStack var2) {
		leftClickAirAction(var1, var2);
	}

	@Override
	public void shiftRightClickAirAction(Player var1, ItemStack var2) {
		leftClickAirAction(var1, var2);
	}

	@Override
	public void rightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
		leftClickAirAction(var1, var4);
	}

	@Override
	public void shiftRightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
		leftClickAirAction(var1, var4);
	}

	public static class BlockChangeListener implements Listener {
		@EventHandler
		public void onBlockBreak(BlockBreakEvent event) {
			changedBlocks.put(event.getBlock().getLocation(), event.getBlock().getType());
		}
	}


}
