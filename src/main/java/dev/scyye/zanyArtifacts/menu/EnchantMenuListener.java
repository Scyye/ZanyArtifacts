//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.menu;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import dev.scyye.zanyArtifacts.Main;
import dev.scyye.zanyArtifacts.Utils;
import dev.scyye.zanyArtifacts.enchant.ZanyEnchant;

@Deprecated(forRemoval = true)
public class EnchantMenuListener implements Listener {
	public EnchantMenuListener() {
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		/*
		if (event.getClickedInventory() != null && event.getCurrentItem() != null) {
			if (event.getView().getTitle().equalsIgnoreCase((new EnchantMenu()).invName)) {
				String p = (String)event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.plugin, "enchant"), PersistentDataType.STRING);
				if (p.equalsIgnoreCase("placeholder")) {
					event.getWhoClicked().sendMessage(ChatColor.DARK_RED + "This item is coming soon!");
					return;
				}

				ZanyEnchant enchant = (ZanyEnchant)Main.plugin.enchantIds.get(p);
				ItemStack currItem = event.getWhoClicked().getItemInUse();
				int lvl = event.getClick() == ClickType.LEFT ? enchant.getMaxLevel() : enchant.getStartLevel();
				if (enchant.canEnchantItem(currItem)) {
					currItem.addUnsafeEnchantment(enchant, lvl);
				} else {
					Utils.givePlayerItemSafely((Player)event.getWhoClicked(), enchant.toItem(lvl));
				}

				event.setCancelled(true);
			}

		}*/
	}
}
