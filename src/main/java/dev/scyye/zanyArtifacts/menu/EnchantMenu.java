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
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import dev.scyye.zanyArtifacts.Main;
import dev.scyye.zanyArtifacts.SuperList;
import dev.scyye.zanyArtifacts.enchant.ZanyEnchant;
@Deprecated(forRemoval = true)
public class EnchantMenu {
	/*
	String invName;
	Inventory inv;
	ItemStack placeholderItem;

	public EnchantMenu() {
		this.invName = ChatColor.BOLD + "EnchantGUI";
		this.inv = Bukkit.createInventory((InventoryHolder)null, 54, this.invName);
	}

	public InventoryView openInv(Player player) {
		this.init();
		return player.openInventory(this.inv);
	}

	public void init() {
		this.placeholderItem = new ItemStack(Material.BARRIER);
		ItemMeta placeholderMeta = this.placeholderItem.getItemMeta();

		assert placeholderMeta != null;

		placeholderMeta.setDisplayName(ChatColor.DARK_RED + "This enchant doesn't exist yet;");
		placeholderMeta.setLore(List.of(ChatColor.DARK_RED + "Come back later!"));
		placeholderMeta.getPersistentDataContainer().set(new NamespacedKey(Main.plugin, "enchant"), PersistentDataType.STRING, "placeholder");
		this.placeholderItem.setItemMeta(placeholderMeta);

		int j;
		for(j = 0; j < 54; ++j) {
			this.inv.setItem(j, this.placeholderItem);
		}

		j = 0;

		for(Iterator<ZanyEnchant> var3 = Main.enchantIds.values().iterator(); var3.hasNext(); ++j) {
			ZanyEnchant enchant = (ZanyEnchant)var3.next();
			ItemStack displayItem = new ItemStack(Material.ENCHANTED_BOOK);
			ItemMeta display = displayItem.getItemMeta();
			boolean hasDifferentLevels = enchant.getStartLevel() != enchant.getMaxLevel();
			display.setDisplayName(enchant.getName());
			SuperList<String> lore = new SuperList();
			if (enchant.getMaxLevel() == enchant.getStartLevel()) {
				lore.add("Click for enchant!");
			} else {
				lore.add("&6Left click for lvl " + enchant.getMaxLevel());
				lore.add("&6Right click for lvl " + enchant.getStartLevel());
			}

			lore.add("");
			lore.add("Info:");
			lore.addIf("isTreasure", enchant.isTreasure());
			lore.addIf("isCursed", enchant.isCursed());
			int var10001 = enchant.getMaxLevel();
			lore.addIf("Max level: " + var10001, hasDifferentLevels);
			var10001 = enchant.getStartLevel();
			lore.addIf("Start level: " + var10001, hasDifferentLevels);
			var10001 = enchant.getMaxLevel();
			lore.addIf("Level: " + var10001, !hasDifferentLevels);
			lore.add("");

			for (Material mat : Material.values()) {
				if (!mat.isBlock() && !mat.isAir() && !mat.isRecord()) {
					if (enchant.isZanyOnly()) {
						lore.add("&4Can Only Be Applied To ZanyItems!");
						break;
					}

					lore.add("&7- " + enchant.getTargetItem().registryKey().key().asString());

				}
			}

			display.setLore(lore);
			//display.getPersistentDataContainer().set(new NamespacedKey(Main.plugin, "enchant"), PersistentDataType.STRING, enchant.getKey().getKey());
			displayItem.setItemMeta(display);
			this.inv.setItem(j, displayItem);
		}

	}*/
}
