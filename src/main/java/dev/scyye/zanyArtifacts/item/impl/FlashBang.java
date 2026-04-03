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
	public void leftClickAirAction(Player player, ItemStack var2) {
		player.getLocation().getNearbyPlayers(5).forEach(p -> p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 5, true, false, false)));
	}
}
