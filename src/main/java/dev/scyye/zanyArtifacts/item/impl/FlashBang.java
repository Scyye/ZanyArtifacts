package dev.scyye.zanyArtifacts.item.impl;

import dev.scyye.zanyArtifacts.item.AttributeData;
import dev.scyye.zanyArtifacts.item.EnchantmentData;
import dev.scyye.zanyArtifacts.item.ZanyItem;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FlashBang extends ZanyItem {

	public FlashBang(String id, ItemStack itemStack, int amount, String displayName, boolean unbreakable, EnchantmentData[] enchantments, AttributeData[] attributes, ItemFlag[] itemFlags, String[] lore, AbilityMeta[] abilities) {
		super(id, itemStack, amount, displayName, unbreakable, enchantments, attributes, itemFlags, lore, abilities);
	}

	@Override
	public void onItemCreate(ItemStack var1) {

	}

	@Override
	public boolean leftClickAirAction(Player player, ItemStack var2) {
		player.getLocation().getNearbyPlayers(5).forEach(p -> p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 5, true, false, false)));
		return false;
	}

	@Override
	public boolean shiftLeftClickAirAction(Player var1, ItemStack var2) {
		leftClickAirAction(var1, var2);
		return false;
	}

	@Override
	public boolean leftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
		leftClickAirAction(var1, var4);
		return false;
	}

	@Override
	public boolean shiftLeftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
		leftClickAirAction(var1, var4);
		return false;
	}

	@Override
	public boolean rightClickAirAction(Player var1, ItemStack var2) {
		leftClickAirAction(var1, var2);
		return false;
	}

	@Override
	public boolean shiftRightClickAirAction(Player var1, ItemStack var2) {
		leftClickAirAction(var1, var2);
		return false;
	}

	@Override
	public boolean rightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
		leftClickAirAction(var1, var4);
		return false;
	}

	@Override
	public boolean shiftRightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
		leftClickAirAction(var1, var4);
		return false;
	}
}
