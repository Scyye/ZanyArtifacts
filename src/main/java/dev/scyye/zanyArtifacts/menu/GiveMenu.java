//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.menu;

import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import dev.scyye.zanyArtifacts.Main;
import dev.scyye.zanyArtifacts.item.ZanyItem;
@Deprecated(forRemoval = true)
public class GiveMenu {
	String invName;
	Inventory inv;
	ItemStack placeholderItem;

	public GiveMenu() {
		this.invName = ChatColor.BOLD + "GiveGUI";
		this.inv = Bukkit.createInventory((InventoryHolder)null, 54, this.invName);
	}

	public void openInv(Player player) {
		this.init();
		player.openInventory(this.inv);
	}

	public void init() {
		this.placeholderItem = new ItemStack(Material.BARRIER);
		ItemMeta placeholderMeta = this.placeholderItem.getItemMeta();

		assert placeholderMeta != null;

		placeholderMeta.setDisplayName(ChatColor.DARK_RED + "This item doesn't exist yet;");
		placeholderMeta.setLore(List.of(ChatColor.DARK_RED + "Come back later!"));
		this.placeholderItem.setItemMeta(placeholderMeta);

		int j;
		for(j = 0; j < 54; ++j) {
			this.inv.setItem(j, this.placeholderItem);
		}

		j = 0;

		for(Iterator var3 = ZanyItem.allItems.values().iterator(); var3.hasNext(); ++j) {
			ZanyItem zanyItem = (ZanyItem)var3.next();
			this.inv.setItem(j, zanyItem.createItem());
		}

	}
}
