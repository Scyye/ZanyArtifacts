//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.enchant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.keys.tags.EnchantmentTagKeys;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import io.papermc.paper.registry.tag.TagKey;
import io.papermc.paper.tag.TagEntry;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
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
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public abstract class ZanyEnchant {
	public static HashMap<String, ZanyEnchant> allEnchants = new HashMap<>();

	@Getter TextComponent display;

	@Getter int maxLevel = 1;
	@Getter int weight = 1;

	@Getter int anvilCost = 0;
	int minCost = 0;
	int maxCost = 0;
	@Getter int increasePerLevel = 0;

	EquipmentSlotGroup[] activeSlots = new EquipmentSlotGroup[] {EquipmentSlotGroup.HAND};

	TagKey<ItemType>[] supportedItemTags = new TagKey[] {ItemTypeTagKeys.SWORDS};
	TagKey<ItemType>[] primaryItemTags = null;

	TagKey<Enchantment>[] enchantmentTags = new TagKey[] {EnchantmentTagKeys.create(Key.key("zany:zany_only"))};

	/**
	 * Constructor for ZanyEnchant, do not use this constructor directly, use a subclass of ZanyEnchant
	 * <p>
	 * To create a new enchantment, create a new class that extends ZanyEnchant and implement the abstract methods
	 * Then, in the main class, create a new instance of the enchantment and add it to the enchantIds map
	 * <p>
	 * The {@code ZanyEnchant#primaryItemTags} field defaults to the supportedItemTags field, but can be overridden with the
	 * {@code ZanyEnchant#setPrimaryItemTags} method
	 * @param display The way the enchantment appears in item lore
	 * @param anvilCost (0-inf) The cost to apply the enchantment in an anvil
	 * @param maxLevel (1-255) The maximum level of the enchantment
	 * @param weight (1-1024) The likelihood of the enchantment appearing in the enchantment table
	 * @param minCost The minimum cost to apply the enchantment in an anvil
	 * @param maxCost The maximum cost to apply the enchantment in an anvil
	 * @param increasePerLevel The cost increase per level of the enchantment
	 * @param activeSlots The slots the enchantment will be active in
	 * @param supportedItemTags The tags of items that can be enchanted with this enchantment
	 * @param enchantmentTags The metadata of the enchantment
	 */
	public ZanyEnchant(TextComponent display, int anvilCost, int maxLevel, int weight, int minCost,
					   int maxCost, int increasePerLevel, EquipmentSlotGroup[] activeSlots,
					   TagKey<ItemType>[] supportedItemTags, TagKey<Enchantment>[] enchantmentTags) {
		this.display = display;
		this.anvilCost = orElse(anvilCost, 0);
		this.maxLevel = orElse(maxLevel, 1);
		this.weight = orElse(weight, 1);
		this.minCost = orElse(minCost, 0);
		this.maxCost = orElse(maxCost, 0);
		this.increasePerLevel = increasePerLevel;
		this.activeSlots = orElse(activeSlots, new EquipmentSlotGroup[] {EquipmentSlotGroup.HAND});
		this.supportedItemTags = orElse(supportedItemTags, new TagKey[] {ItemTypeTagKeys.SWORDS});
		this.enchantmentTags = orElse(enchantmentTags, new TagKey[] {EnchantmentTagKeys.create(Key.key("zany:zany_only"))});

		allEnchants.put(getId(), this);
	}

	public static <T extends ZanyEnchant> T createBasicEnchant(Class<T> type, TextComponent display, int maxLevel, @Nullable EquipmentSlotGroup[] activeSlots,
																	@Nullable TagKey<ItemType>[] supportedItemTags, @Nullable TagKey<Enchantment>[] enchantmentTags) {
		try {
			return type.getConstructor(TextComponent.class, int.class, int.class, int.class, int.class, int.class, int.class,
					EquipmentSlotGroup[].class, TagKey[].class, TagKey[].class)
					.newInstance(display, -1, maxLevel, -1, -1, -1, -1, activeSlots, supportedItemTags, enchantmentTags);
		} catch (Exception e) {
			e.printStackTrace();
			return (T) emptyEnchant();
		}
	}

	private static ZanyEnchant emptyEnchant() {
		return new ZanyEnchant(Component.text("Empty"), 0, 1, 1, 0, 0, 0, new EquipmentSlotGroup[] {EquipmentSlotGroup.HAND}, new TagKey[] {ItemTypeTagKeys.SWORDS}, new TagKey[] {EnchantmentTagKeys.create(Key.key("zany:zany_only"))}) {
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
			}

			@Override
			public void constantEffect(Player entity, ItemStack item) {
			}
		};
	}

	private <T> T[] orElse(@Nullable T[] value, @Nonnull T[] defaultValue) {
		if (value == null || value.length == 0) {
			return defaultValue;
		}
		return value;
	}

	private int orElse(int value, int defaultValue) {
		if (value == -1) {
			return defaultValue;
		}
		return value;
	}

	@Subst("enchant_id")
	public String getId() {
		return this.display.content().toLowerCase().replaceAll(" ", "_");
	}

	@Subst("zany:id")
	@NotNull
	protected Key getKey() {
		return Key.key("zany:" + getId());
	}

	protected int getLevel(ItemStack item) {
		if (!item.hasItemMeta()) return 0;
		if (!item.getItemMeta().hasEnchants()) return 0;
		for (Enchantment enchant : item.getItemMeta().getEnchants().keySet()) {
			if (enchant.getKey().key().equals(getKey())) {
				return item.getEnchantmentLevel(enchant);
			}
		}
		return 1;
	}

	@NotNull
	protected EnchantmentRegistryEntry.EnchantmentCost getMinimumCost() {
		return EnchantmentRegistryEntry.EnchantmentCost.of(this.minCost, this.increasePerLevel);
	}

	@NotNull
	protected EnchantmentRegistryEntry.EnchantmentCost getMaximumCost() {
		return EnchantmentRegistryEntry.EnchantmentCost.of(this.maxCost, this.increasePerLevel);
	}

	@NotNull
	protected Iterable<EquipmentSlotGroup> getActiveSlots() {
		return List.of(this.activeSlots);
	}

	@NotNull
	protected Set<TagEntry<ItemType>> getSupportedItems() {
		return Arrays.stream(supportedItemTags).map(TagEntry::tagEntry).collect(Collectors.toSet());
	}

	@NotNull
	protected Set<TagEntry<ItemType>> getPrimaryItems() {
		if (primaryItemTags == null) {
			return getSupportedItems();
		}
		return Arrays.stream(primaryItemTags).map(TagEntry::tagEntry).collect(Collectors.toSet());
	}

	@NotNull
	protected Set<TagKey<Enchantment>> getEnchantTagKeys() {
		return Set.of(enchantmentTags);
	}

	@NotNull
	TagKey<ItemType> getTagForSupportedItems() {
		return TagKey.create(RegistryKey.ITEM, Key.key(getKey().asString() + "_enchantable"));
	}

	@NotNull
	TagKey<ItemType> getTagForPrimaryItems() {
		return TagKey.create(RegistryKey.ITEM, Key.key(getKey().asString() + "_primary"));
	}

	@NotNull
	TagEntry<Enchantment> getTagEntry() {
		return TagEntry.valueEntry(TypedKey.create(RegistryKey.ENCHANTMENT, getKey()));
	}

	ZanyEnchant setPrimaryItemTags(TagKey<ItemType>[] primaryItemTags) {
		this.primaryItemTags = primaryItemTags;
		return this;
	}



	public abstract void onBreakBlock(Block block, ItemStack item, BlockBreakEvent event);

	public abstract void onMove(PlayerMoveEvent event, Location location);

	public abstract void onHitEntity(Entity entity, ItemStack item, EntityDamageEvent.DamageCause cause, EntityDamageByEntityEvent event);

	public abstract void onKillEntity(Entity entity, ItemStack item, EntityDamageEvent.DamageCause cause, EntityDeathEvent event);

	public abstract void onGetHit(LivingEntity entity, EntityDamageEvent.DamageCause cause, EntityDamageByEntityEvent event);

	public abstract void constantEffect(Player entity, ItemStack item);
}
