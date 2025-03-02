//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.item.impl;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import dev.scyye.zanyArtifacts.item.AttributeData;
import dev.scyye.zanyArtifacts.item.EnchantmentData;
import dev.scyye.zanyArtifacts.item.ZanyItem;

public class ExplosiveStick extends ZanyItem {

	public ExplosiveStick(String id, ItemStack itemStack, int amount, String displayName, boolean unbreakable, EnchantmentData[] enchantments, AttributeData[] attributes, ItemFlag[] itemFlags, String[] lore, AbilityMeta[] abilities) {
		super(id, itemStack, amount, displayName, unbreakable, enchantments, attributes, itemFlags, lore, abilities);
	}

	public void onItemCreate(ItemStack itemStack) {
	}

	public boolean leftClickAirAction(Player player, ItemStack itemStack) {
		return false;
	}

	public boolean shiftLeftClickAirAction(Player player, ItemStack itemStack) {
		return false;
	}

	public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
		player.getWorld().createExplosion(block.getLocation(), 6.0F, false, true);
		return true;
	}

	public boolean shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
		return false;
	}

	public boolean rightClickAirAction(Player player, ItemStack itemStack) {
		return false;
	}

	public boolean shiftRightClickAirAction(Player player, ItemStack itemStack) {
		return false;
	}

	public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
		this.leftClickBlockAction(player, event, block, itemStack);
		ItemStack newItem = itemStack.clone();
		newItem.setAmount(itemStack.getAmount() - 1);
		player.getInventory().setItemInMainHand(newItem);
		return true;
	}

	public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
		return false;
	}
}
