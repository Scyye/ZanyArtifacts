package dev.scyye.zanyArtifacts.menu;

import dev.scyye.zanyArtifacts.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class MenuListener implements Listener {
	@EventHandler
	public void onInventoryInteract(InventoryClickEvent event) {
		for (var menu : Menu.allMenus.values()) {
			if (event.getCurrentItem() == null)
				return;
			if (event.getCurrentItem().getItemMeta() == null)
				return;
			if (Objects.equals(event.getCurrentItem().getItemMeta().getPersistentDataContainer()
					.get(Main.getKey("menu"), PersistentDataType.STRING), "placeholder"))
				event.setCancelled(true);

			if (event.getInventory().equals(menu.getInventory()))
				menu.interact(event);
		}
	}
}
