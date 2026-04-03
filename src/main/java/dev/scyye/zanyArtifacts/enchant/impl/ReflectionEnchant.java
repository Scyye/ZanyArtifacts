package dev.scyye.zanyArtifacts.enchant.impl;

import dev.scyye.zanyArtifacts.enchant.ZanyEnchant;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;

import java.util.List;
import java.util.UUID;

@Deprecated(forRemoval = true)
@SuppressWarnings("UnstableApiUsage")
public class ReflectionEnchant extends ZanyEnchant {
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
	public ReflectionEnchant(TextComponent display, int anvilCost, int maxLevel, int weight, int minCost, int maxCost, int increasePerLevel, EquipmentSlotGroup[] activeSlots, TagKey<ItemType>[] supportedItemTags, TagKey<Enchantment>[] enchantmentTags) {
		super(display, anvilCost, maxLevel, weight, minCost, maxCost, increasePerLevel, activeSlots, supportedItemTags, enchantmentTags);
	}

	@Override
	public void onBreakBlock(Block block, ItemStack item, BlockBreakEvent event) {

	}

	@Override
	public void onMove(PlayerMoveEvent event, Location location) {

	}

	@Override
	public void onHitEntity(Entity entity, ItemStack item, EntityDamageEvent.DamageCause cause, EntityDamageByEntityEvent event) {

	}

	@Override
	public void onKillEntity(Entity entity, ItemStack item, EntityDamageEvent.DamageCause cause, EntityDeathEvent event) {

	}


	@Override
	public void onGetHit(LivingEntity entity, EntityDamageEvent.DamageCause cause, EntityDamageByEntityEvent event) {
		if (cause == EntityDamageEvent.DamageCause.PROJECTILE) {
			// get the direction the arrow is coming from
			Arrow arrow = (Arrow) event.getDamager();
			Location arrowLocation = arrow.getLocation();
			Location playerLocation = entity.getLocation();
			double angle = Math.atan2(arrowLocation.getZ() - playerLocation.getZ(), arrowLocation.getX() - playerLocation.getX());
			double angleDegrees = Math.toDegrees(angle);

			// create an arrow that reflects the original arrow based on the angle
			entity.getWorld().spawnArrow(playerLocation, arrow.getVelocity().multiply(-1), 2, 5);
		}
	}

	static List<UUID> ignoredArrows = List.of();

	@Override
	public void constantEffect(Player entity, ItemStack item) {
		/*
		Collection<Entity> entities = entity.getWorld().getNearbyEntities(entity.getEyeLocation(), 0.5, 0.5, 0.5);
		entities.stream().filter(entity1 -> entity1 instanceof Arrow && !ignoredArrows.contains(entity1.getUniqueId()))
				.collect(Collectors.toSet())
				.forEach(arrow -> {
					arrow.setVelocity(arrow.getVelocity().multiply(-1));
					ignoredArrows.add(arrow.getUniqueId());
				});*/
	}
}
