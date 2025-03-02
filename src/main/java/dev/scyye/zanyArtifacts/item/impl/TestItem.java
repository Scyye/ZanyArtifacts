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

public class TestItem extends ZanyItem {

	public TestItem(String id, ItemStack itemStack, int amount, String displayName, boolean unbreakable, EnchantmentData[] enchantments, AttributeData[] attributes, ItemFlag[] itemFlags, String[] lore, AbilityMeta[] abilities) {
		super(id, itemStack, amount, displayName, unbreakable, enchantments, attributes, itemFlags, lore, abilities);
	}

	public void onItemCreate(ItemStack itemStack) {
	}

	public boolean leftClickAirAction(Player player, ItemStack itemStack) {
		player.sendMessage("heyyyy");
		return false;
	}

	public boolean shiftLeftClickAirAction(Player player, ItemStack itemStack) {
		return false;
	}

	public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
		return false;
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
		return false;
	}

	public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
		return false;
	}
}
