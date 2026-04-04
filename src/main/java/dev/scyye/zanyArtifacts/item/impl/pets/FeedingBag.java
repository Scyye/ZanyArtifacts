package dev.scyye.zanyArtifacts.item.impl.pets;

import dev.scyye.zanyArtifacts.item.AttributeData;
import dev.scyye.zanyArtifacts.item.EnchantmentData;
import dev.scyye.zanyArtifacts.item.ZanyItem;
import dev.scyye.zanyArtifacts.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FeedingBag extends ZanyItem {
	public FeedingBag(String id, ItemStack itemStack, int amount, String displayName, boolean unbreakable, EnchantmentData[] enchantments, AttributeData[] attributes, ItemFlag[] itemFlags, String[] lore, AbilityMeta[] abilities) {
		super(id, itemStack, amount, displayName, unbreakable, enchantments, attributes, itemFlags, lore, abilities);
	}

	@Override
	public void onItemCreate(ItemStack var1) {

	}

	@Override
	public void leftClickAirAction(Player player, ItemStack item) {
		// Open a chest inventory with 3 rows, called "Feeding Bag", specific to the player that is persistent and doesn't reset every time they open it
		Inventory inv = Bukkit.createInventory(player, InventoryType.CHEST, "Feeding Bag");
		player.openInventory(inv);
	}
}
