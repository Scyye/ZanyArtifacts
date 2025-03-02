//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import dev.scyye.zanyArtifacts.Utils;
@Deprecated(forRemoval = true)
public class GiveMenuListener implements Listener {
	public GiveMenuListener() {
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		assert event.getClickedInventory() != null && event.getCurrentItem() != null;

		if (event.getView().getTitle().equalsIgnoreCase((new GiveMenu()).invName)) {
			Utils.givePlayerItemSafely((Player)event.getWhoClicked(), Utils.getZany(event.getCurrentItem()).createItem());
			event.setCancelled(true);
		}

	}
}
