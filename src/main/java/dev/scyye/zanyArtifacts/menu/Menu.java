package dev.scyye.zanyArtifacts.menu;

import dev.scyye.zanyArtifacts.Main;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public abstract class Menu {
	public static HashMap<String, Menu> allMenus = new HashMap<>();
	@Getter
	private final InventoryType type;
	@Getter
	private final String name;
	private final ItemStack placeholderItem;
	private final boolean fillEmptySlots;

	@Getter
	Inventory inventory;

	public String getId() {
		return this.name.toLowerCase().replaceAll(" ", "_");
	}

	public Menu(InventoryType type, String name, boolean fillEmptySlots, @Nullable ItemStack placeholderItem) {
		this.type = type;
		this.name = name;
		this.fillEmptySlots = fillEmptySlots;

		this.inventory = Bukkit.createInventory(null, type, Component.text(name));

		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.plugin, this::callUpdate, 1L, 1L);

		if (placeholderItem != null) {
			placeholderItem.getItemMeta().getPersistentDataContainer().set(new NamespacedKey(Main.plugin, "menu"), PersistentDataType.STRING, "placeholder");
			this.placeholderItem = placeholderItem;
		} else {
			ItemStack item = new ItemStack(Material.BARRIER);
			ItemMeta placeholderMeta = item.getItemMeta();
			assert placeholderMeta != null;
			placeholderMeta.displayName(Component.text("[]").color(TextColor.color(255,0,0)));
			placeholderMeta.getPersistentDataContainer().set(new NamespacedKey(Main.plugin, "menu"), PersistentDataType.STRING, "placeholder");
			item.setItemMeta(placeholderMeta);
			this.placeholderItem = item;
		}
	}

	public void buildInventory(Player player) {
		this.inventory.clear();
		this.init(player);
		for (int i = 0; i < this.inventory.getSize(); i++) {
			if (this.inventory.getItem(i) == null && this.fillEmptySlots) {
				this.inventory.setItem(i, this.placeholderItem);
			}
		}
	}

	private void callUpdate() {
		if (!this.inventory.getViewers().isEmpty()) {
			this.update(this.inventory.getViewers());
		}
	}

	public abstract void init(Player player);

	public void update(List<HumanEntity> viewers) {

	}

	public abstract void interact(InventoryClickEvent event);
}
