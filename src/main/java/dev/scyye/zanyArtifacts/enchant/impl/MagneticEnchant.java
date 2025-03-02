package dev.scyye.zanyArtifacts.enchant.impl;

import dev.scyye.zanyArtifacts.enchant.ZanyEnchant;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;

public class MagneticEnchant extends ZanyEnchant {

	/**
	 * Constructor for ZanyEnchant, do not use this constructor directly, use a subclass of ZanyEnchant
	 * <p>
	 * To create a new enchantment, create a new class that extends ZanyEnchant and implement the abstract methods
	 * Then, in the main class, create a new instance of the enchantment and add it to the enchantIds map
	 * <p>
	 * The {@code ZanyEnchant#primaryItemTags} field defaults to the supportedItemTags field, but can be overridden with the
	 * {@code ZanyEnchant#setPrimaryItemTags} method
	 *
	 * @param display           The way the enchantment appears in item lore
	 * @param anvilCost         (0-inf) The cost to apply the enchantment in an anvil
	 * @param maxLevel          (1-255) The maximum level of the enchantment
	 * @param weight            (1-1024) The likelihood of the enchantment appearing in the enchantment table
	 * @param minCost           The minimum cost to apply the enchantment in an anvil
	 * @param maxCost           The maximum cost to apply the enchantment in an anvil
	 * @param increasePerLevel  The cost increase per level of the enchantment
	 * @param activeSlots       The slots the enchantment will be active in
	 * @param supportedItemTags The tags of items that can be enchanted with this enchantment
	 * @param enchantmentTags   The metadata of the enchantment
	 */
	public MagneticEnchant(TextComponent display, int anvilCost, int maxLevel, int weight, int minCost, int maxCost, int increasePerLevel, EquipmentSlotGroup[] activeSlots, TagKey<ItemType>[] supportedItemTags, TagKey<Enchantment>[] enchantmentTags) {
		super(display, anvilCost, maxLevel, weight, minCost, maxCost, increasePerLevel, activeSlots, supportedItemTags, enchantmentTags);
	}

	@Override
	public void onBreakBlock(Block var1, ItemStack var2, BlockBreakEvent var3) {

	}

	@Override
	public void onMove(PlayerMoveEvent event, Location location) {

	}

	@Override
	public void onHitEntity(Entity var1, ItemStack var2, EntityDamageEvent.DamageCause var3, EntityDamageByEntityEvent var4) {

	}

	@Override
	public void onKillEntity(Entity var1, ItemStack var2, EntityDamageEvent.DamageCause var3, EntityDeathEvent var4) {

	}

	@Override
	public void onGetHit(LivingEntity var1, EntityDamageEvent.DamageCause var2, EntityDamageByEntityEvent var3) {

	}

	@Override
	public void constantEffect(Player player, ItemStack itemStack) {
		if (player.getInventory().getBoots() == null)
			return;
		player.getWorld().getNearbyEntitiesByType(Item.class, player.getLocation(), getLevel(player.getInventory().getBoots())*5).forEach(
				item -> {
					item.teleport(player.getLocation());
				}
		);
	}
}
