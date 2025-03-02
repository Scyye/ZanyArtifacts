package dev.scyye.zanyArtifacts.menu.impl;

import dev.scyye.zanyArtifacts.Main;
import dev.scyye.zanyArtifacts.Utils;
import dev.scyye.zanyArtifacts.menu.Menu;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GiveMenu extends Menu {
	public GiveMenu(InventoryType type, String name, boolean fillEmptySlots, @Nullable ItemStack placeholderItem) {
		super(type, name, fillEmptySlots, placeholderItem);
	}

	@Override
	public void init(Player player) {
		for (var entry : Main.itemIds.entrySet()) {
			var item = entry.getValue();
			ItemStack menuItem = item.createItem();
			menuItem.getItemMeta().getPersistentDataContainer().set(new NamespacedKey(Main.plugin, "menu"),
					org.bukkit.persistence.PersistentDataType.STRING, "give-" + entry.getKey());
			this.getInventory().addItem(item.createItem());
		}
	}

	@Override
	public void update(List<HumanEntity> viewers) {

	}

	@Override
	public void interact(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clickedItem = event.getCurrentItem();
		if (clickedItem == null) return;
		if (clickedItem.getItemMeta() == null) return;
		if (clickedItem.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.plugin, "menu"),
				org.bukkit.persistence.PersistentDataType.STRING)) {
			String[] data = clickedItem.getItemMeta().getPersistentDataContainer()
					.get(new NamespacedKey(Main.plugin, "menu"), org.bukkit.persistence.PersistentDataType.STRING).split("-");
			if (data[0].equals("give")) {
				Utils.givePlayerItemSafely(player, Main.plugin.itemIds.get(data[1]).createItem());
				event.setCancelled(true);
			}
		}
	}
}
