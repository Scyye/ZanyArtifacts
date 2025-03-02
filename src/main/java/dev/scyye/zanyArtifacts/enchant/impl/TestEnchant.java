//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.enchant.impl;


import dev.scyye.zanyArtifacts.enchant.ZanyEnchant;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.key.Key;
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
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class TestEnchant extends ZanyEnchant {

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
	public TestEnchant(TextComponent display, int anvilCost, int maxLevel, int weight, int minCost, int maxCost, int increasePerLevel, EquipmentSlotGroup[] activeSlots, TagKey<ItemType>[] supportedItemTags, TagKey<Enchantment>[] enchantmentTags) {
		super(display, anvilCost, maxLevel, weight, minCost, maxCost, increasePerLevel, activeSlots, supportedItemTags, enchantmentTags);
	}

	@Override
	protected @NotNull Key getKey() {
		return Key.key("zany:test_enchant");
	}

	@Override
	public void onBreakBlock(Block block, ItemStack itemStack, BlockBreakEvent event) {
		event.getPlayer().sendMessage("Block broke poggers");
	}

	@Override
	public void onMove(PlayerMoveEvent event, Location var2) {
		event.getPlayer().sendMessage("Test enchant active");
	}

	@Override
	public void onHitEntity(Entity entity, ItemStack item, EntityDamageEvent.DamageCause damageCause, EntityDamageByEntityEvent event) {
		event.getDamager().sendMessage("hit entity");
	}

	@Override
	public void onKillEntity(Entity var1, ItemStack var2, EntityDamageEvent.DamageCause var3, EntityDeathEvent event) {
		if (event.getDamageSource().getCausingEntity() != null)
			event.getDamageSource().getCausingEntity().sendMessage("kill entity");
	}

	@Override
	public void onGetHit(LivingEntity var1, EntityDamageEvent.DamageCause var2, EntityDamageByEntityEvent event) {
		event.getEntity().sendMessage("got hit");
	}

	@Override
	public void constantEffect(Player entity, ItemStack item) {
		entity.sendMessage("constant effect");
	}
}

