package dev.scyye.zanyArtifacts.enchant.impl;

import dev.scyye.zanyArtifacts.enchant.ZanyEnchant;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
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

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Deprecated(forRemoval = true)
@SuppressWarnings("UnstableApiUsage")
public class GroundSlamEnchant extends ZanyEnchant {

	// Tracks the starting Y-coordinates of players who are falling
	private static final HashMap<UUID, Double> fallingPlayers = new HashMap<>();

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
	public GroundSlamEnchant(TextComponent display, int anvilCost, int maxLevel, int weight, int minCost, int maxCost, int increasePerLevel, EquipmentSlotGroup[] activeSlots, TagKey<ItemType>[] supportedItemTags, TagKey<Enchantment>[] enchantmentTags) {
		super(display, anvilCost, maxLevel, weight, minCost, maxCost, increasePerLevel, activeSlots, supportedItemTags, enchantmentTags);
	}

	@Override
	public void onBreakBlock(Block block, ItemStack item, BlockBreakEvent event) {

	}

	@Override
	public void onMove(PlayerMoveEvent event, Location location) {
		Player player = event.getPlayer();
		Location from = event.getFrom();
		Location to = event.getTo();

		// Check if the player is falling
		if (to.getY() < from.getY()) {
			// If not already tracked, add the player to the falling list
			fallingPlayers.putIfAbsent(player.getUniqueId(), from.getY());
		} else if (to.getY() >= from.getY()) {
			// If the player is moving up or on flat ground, stop tracking
			if (fallingPlayers.containsKey(player.getUniqueId())) {
				// Calculate the total fall distance
				double startY = fallingPlayers.remove(player.getUniqueId());
				double fallDistance = startY - to.getY();

				// Trigger an action if the fall distance exceeds the threshold
				// Minimum fall distance to trigger an action
				double FALL_THRESHOLD = 5.0;
				if (fallDistance >= FALL_THRESHOLD) {
					player.sendMessage("You just fell " + fallDistance + " blocks!");
					// Additional logic here, e.g., apply damage or effects
				}
			}
		}

		// If the player is on the ground (not falling), remove them from tracking
		if (player.isOnGround()) {
			fallingPlayers.remove(player.getUniqueId());
		}

		if (player.getInventory().getBoots() == null)
			return;

		if (player.getLastDamageCause() == null) {
			System.out.println("you took no damage");
			return;
		}

		EntityDamageEvent.DamageCause cause = player.getLastDamageCause().getCause();
		if (!cause.equals(EntityDamageEvent.DamageCause.FALL))
			return;
		double lastDamage = player.getLastDamage();
		int level = getLevel(player.getInventory().getBoots());
		List<Entity> entities = player.getNearbyEntities(3+(2*level), 3+(2*level), 3+(2*level));
		for (Entity entity : entities) {
			if (!(entity instanceof LivingEntity))
				continue;
			entity.setVelocity(entity.getVelocity().add(player.getLocation().getDirection().multiply(0.5).setY(0.2)));
			((LivingEntity) entity).damage(lastDamage*1.4, player);
		}
	}

	@Override
	public void onHitEntity(Entity entity, ItemStack item, EntityDamageEvent.DamageCause cause, EntityDamageByEntityEvent event) {

	}

	@Override
	public void onKillEntity(Entity entity, ItemStack item, EntityDamageEvent.DamageCause cause, EntityDeathEvent event) {

	}

	@Override
	public void onGetHit(LivingEntity entity, EntityDamageEvent.DamageCause cause, EntityDamageByEntityEvent event) {

	}

	@Override
	public void constantEffect(Player entity, ItemStack item) {

	}
}
