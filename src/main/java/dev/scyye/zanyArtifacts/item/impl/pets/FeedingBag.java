package dev.scyye.zanyArtifacts.item.impl.pets;

import dev.scyye.zanyArtifacts.item.AttributeData;
import dev.scyye.zanyArtifacts.item.EnchantmentData;
import dev.scyye.zanyArtifacts.item.ZanyItem;
import dev.scyye.zanyArtifacts.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static dev.scyye.zanyArtifacts.Main.plugin;

public class FeedingBag extends ZanyItem {
	private static final NamespacedKey BAG_ID = new NamespacedKey(plugin, "feeding_bag_id");
	private static final Map<String, Inventory> feedingBags = new HashMap<>();

	public FeedingBag(String id, ItemStack itemStack, int amount, String displayName, boolean unbreakable, EnchantmentData[] enchantments, AttributeData[] attributes, ItemFlag[] itemFlags, String[] lore, AbilityMeta[] abilities) {
		super(id, itemStack, amount, displayName, unbreakable, enchantments, attributes, itemFlags, lore, abilities);
	}

	@Override
	public void onItemCreate(ItemStack itemStack) {
	}

	@Override
	public void leftClickAirAction(Player player, ItemStack item) {
		// Open a chest inventory with 3 rows, called "Feeding Bag", specific to the player that is persistent and doesn't reset every time they open it
		ItemMeta meta = item.getItemMeta();
		// If the bag doesn't have an ID yet, create one and persist it to the ItemStack's ItemMeta
		if (meta == null || !meta.getPersistentDataContainer().has(BAG_ID, PersistentDataType.STRING)) {
			if (meta == null) meta = Bukkit.getItemFactory().getItemMeta(item.getType());
			UUID bagId = UUID.randomUUID();
			meta.getPersistentDataContainer().set(BAG_ID, PersistentDataType.STRING, bagId.toString());
			item.setItemMeta(meta); // <-- Persist the change back to the ItemStack
		}

		player.openInventory(getInventory(item));
	}

	public static Inventory getInventory(ItemStack bag) {
		// Make sure the bag has an ID (defensive) and persist one if it doesn't so we don't end up using a null key
		String id = null;
		if (bag.getItemMeta() != null) {
			id = bag.getItemMeta().getPersistentDataContainer().get(BAG_ID, PersistentDataType.STRING);
		}

		if (id == null) {
			ItemMeta meta = bag.getItemMeta();
			if (meta == null) meta = Bukkit.getItemFactory().getItemMeta(bag.getType());
			id = UUID.randomUUID().toString();
			meta.getPersistentDataContainer().set(BAG_ID, PersistentDataType.STRING, id);
			bag.setItemMeta(meta);
		}

		final String key = id;
		return feedingBags.computeIfAbsent(key, str -> {
			Inventory inv = Bukkit.createInventory(null, 27, "Feeding Bag");
			loadInventory(str, inv);
			return inv;
		});
	}

	public static void saveInventory(String uuid, Inventory inv) {
		File file = new File(plugin.getDataFolder() + "/feedingbags", uuid + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		config.set("items", inv.getContents());

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadInventory(String uuid, Inventory inv) {
		File file = new File(plugin.getDataFolder() + "/feedingbags", uuid + ".yml");
		if (!file.exists()) return;

		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		List<ItemStack> items = (List<ItemStack>) config.get("items");

		if (items != null) {
			inv.setContents(items.toArray(new ItemStack[0]));
		}
	}

	public static class FeedingBagSaveEvent implements Listener {
		@EventHandler
		public void onInventoryClose(InventoryCloseEvent event) {
			for (Map.Entry<String, Inventory> entry : feedingBags.entrySet()) {
				if (entry.getValue().equals(event.getInventory())) {
					saveInventory(entry.getKey(), entry.getValue());
					break;
				}
			}
		}
	}
}
