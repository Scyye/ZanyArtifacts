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

	@Override
	public void leftClickAirAction(Player var1, ItemStack var2) {

	}

	public void leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
		player.getWorld().createExplosion(block.getLocation(), 6.0F, false, true);
	}

	public void rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
		this.leftClickBlockAction(player, event, block, itemStack);
		ItemStack newItem = itemStack.clone();
		newItem.setAmount(itemStack.getAmount() - 1);
		player.getInventory().setItemInMainHand(newItem);
	}

	public void shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
	}
}
