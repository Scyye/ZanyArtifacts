//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.item;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import io.papermc.paper.datacomponent.DataComponentBuilder;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.inventory.tooltip.TooltipContext;
import io.papermc.paper.persistence.PersistentDataContainerView;
import io.papermc.paper.registry.set.RegistryKeySet;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import dev.scyye.zanyArtifacts.Main;
import dev.scyye.zanyArtifacts.Utils;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

public abstract class ZanyItem extends ItemStack {
	public static HashMap<String, ZanyItem> allItems = new HashMap<>();

	ItemStack itemStack;
	int amount;
	String displayName;
	boolean unbreakable;
	EnchantmentData[] enchantments;
	AttributeData[] attributes;
	ItemFlag[] itemFlags;
	String[] lore;
	@Getter
	String id;
	AbilityMeta[] abilities;


	public ZanyItem(String id, ItemStack itemStack, int amount, String displayName, boolean unbreakable,
					EnchantmentData[] enchantments, AttributeData[] attributes, ItemFlag[] itemFlags,
					String[] lore, AbilityMeta[] abilities) {
		this.id = id;
		this.itemStack = itemStack;
		this.amount = amount;
		this.displayName = displayName;
		this.unbreakable = unbreakable;
		this.enchantments = enchantments;
		this.attributes = attributes;
		this.itemFlags = itemFlags;
		this.lore = lore;
		this.abilities = abilities;
		this.createItem(true);
		allItems.put(this.id, this);
	}

	public ItemStack createItem(boolean init) {
		ItemMeta meta = this.itemStack.getItemMeta();
		assert meta != null;

		for(EnchantmentData enchantment : this.enchantments) {
			meta.addEnchant(enchantment.enchantment, enchantment.level, enchantment.ignoreMaxLevel);
		}

		for(AttributeData data : this.attributes) {
			meta.addAttributeModifier(data.attribute, data.modifier);
		}

		meta.addItemFlags(this.itemFlags);
		meta.displayName(colorizeString(this.displayName));
		meta.setUnbreakable(this.unbreakable);
		List<TextComponent> lore = new ArrayList<>();


		for (AbilityMeta ability : this.abilities) {
			lore.addAll(ability.buildAbilityLoreString());
		}
		for(String line : this.lore) {
			lore.add(colorizeString(line));
		}

		meta.lore(lore);
		meta.getPersistentDataContainer().set(Main.getKey("id"), PersistentDataType.STRING, this.id);
		this.itemStack.setItemMeta(meta);
		if (!init) {
			this.onItemCreate(this.itemStack);
			BukkitScheduler scheduler = Bukkit.getScheduler();
			scheduler.runTaskTimerAsynchronously(Main.plugin, () -> {
				for (Player player : Bukkit.getOnlinePlayers()) {
					for (ItemStack item : Utils.getZanyItemsFromInventory(player.getInventory(), this.id)) {
						this.updateItem(player, item);
					}
				}
			}, 1L, 1L);
		}

		return this.itemStack;
	}

	public static class AbilityMeta {
		public enum AbilityType {
			LEFT_CLICK_AIR,
			LEFT_CLICK_BLOCK,
			RIGHT_CLICK_AIR,
			RIGHT_CLICK_BLOCK,
			CLICK_AIR,
			CLICK_BLOCK,
			LEFT_CLICK_ENTITY,
			RIGHT_CLICK_ENTITY,
			CLICK_ENTITY,
			LEFT_CLICK,
			RIGHT_CLICK,
			CLICK,
			CONSTANT,
			PET
		}

		String name;
		String description;
		AbilityType type;
		boolean shift;
		boolean consume;
		float cooldownMs;

		public AbilityMeta(String name, String description, AbilityType type, boolean shift, boolean consume, float cooldownMs) {
			this.name = name;
			this.description = description;
			this.type = type;
			this.shift = shift;
			this.consume = consume;
			this.cooldownMs = cooldownMs;
		}

		List<TextComponent> buildAbilityLoreString() {
			List<TextComponent> abilityLore = new ArrayList<>();

			if (this.type == AbilityType.PET) {
				abilityLore.add(Component.text("Pet Ability: " + name.toUpperCase().replaceAll("_", " ")+" ", TextColor.color(0x0ee3c7), TextDecoration.BOLD));
				abilityLore.add(Component.text(description,
						TextColor.color(0x7f7f7f)));
				abilityLore.add(Component.text("Pet abilities are active when in your inventory or your pet bag"));
			}
			else {
				abilityLore.add(Component.text("Ability: " + name.toUpperCase().replaceAll("_", " ")+" ", TextColor.color(0xe3c70e), TextDecoration.BOLD)
						.append(Component.text((shift?"SHIFT ":"") + type.name().replaceAll("_", " "),
								TextColor.color(0xc7e30e)))
						.append(Component.text(consume?" [CONSUMES ITEM]":"",
								TextColor.color(0xE30008))));
				abilityLore.add(Component.text(description,
						TextColor.color(0x7f7f7f)));
			}


			if (cooldownMs > 0) {

				// pretify to (MM)m:(SS)s
				int totalSeconds = (int) (cooldownMs / 1000);
				int minutes = totalSeconds / 60;
				int seconds = totalSeconds % 60;
				String cooldownString = (minutes > 0 ? minutes + "m:" : "") +
						(seconds > 0 ? seconds + "s" : "");
				abilityLore.add(Component.text("Cooldown: " + cooldownString, TextColor.color(0x7f7f7f)));
			}
			abilityLore.add(Component.text(" ", TextColor.color(0x000000)));
			return abilityLore;
		}
	}

	public abstract void onItemCreate(ItemStack var1);

	public abstract void leftClickAirAction(Player var1, ItemStack var2);

	public void shiftLeftClickAirAction(Player var1, ItemStack var2) {
		leftClickAirAction(var1, var2);
	}

	public void leftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
		leftClickAirAction(var1, var4);
	}

	public void shiftLeftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
		leftClickBlockAction(var1, var2, var3, var4);
	}

	public void rightClickAirAction(Player var1, ItemStack var2) {

	}

	public void shiftRightClickAirAction(Player var1, ItemStack var2) {
		rightClickAirAction(var1, var2);
	}

	public void rightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
		rightClickAirAction(var1, var4);
	}

	public void shiftRightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {
		rightClickBlockAction(var1, var2, var3, var4);
	}

	public void updateItem(Player player, ItemStack item) {
		// Override in subclasses for periodic updates
	}

	public ItemStack getRawItem() {
		return this.itemStack;
	}

	public static @NotNull TextComponent colorizeString(@NotNull String textToTranslate) {
		char[] b = textToTranslate.toCharArray();

		for(int i = 0; i < b.length - 1; ++i) {
			if (b[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
				b[i] = 167;
				b[i + 1] = Character.toLowerCase(b[i + 1]);
			}
		}

		return Component.text(new String(b));
	}

	@Override
	public boolean matchesWithoutData(@NotNull ItemStack item, @NotNull Set<@NotNull DataComponentType> excludeTypes, boolean ignoreCount) {
		return itemStack.matchesWithoutData(item, excludeTypes, ignoreCount);
	}

	@Override
	public boolean matchesWithoutData(@NotNull ItemStack item, @NotNull Set<@NotNull DataComponentType> excludeTypes) {
		return itemStack.matchesWithoutData(item, excludeTypes);
	}

	@Override
	public boolean isDataOverridden(@NotNull DataComponentType type) {
		return itemStack.isDataOverridden(type);
	}

	@Override
	public void copyDataFrom(@NotNull ItemStack source, @NotNull Predicate<@NotNull DataComponentType> filter) {
		itemStack.copyDataFrom(source, filter);
	}

	@Override
	public void resetData(@NotNull DataComponentType type) {
		itemStack.resetData(type);
	}

	@Override
	public void unsetData(@NotNull DataComponentType type) {
		itemStack.unsetData(type);
	}

	@Override
	public void setData(DataComponentType.@NotNull NonValued type) {
		itemStack.setData(type);
	}

	@Override
	public <T> void setData(DataComponentType.@NotNull Valued<T> type, @NotNull T value) {
		itemStack.setData(type, value);
	}

	@Override
	public <T> void setData(DataComponentType.@NotNull Valued<T> type, @NotNull DataComponentBuilder<T> valueBuilder) {
		itemStack.setData(type, valueBuilder);
	}

	@Override
	public @Unmodifiable Set<@NotNull DataComponentType> getDataTypes() {
		return itemStack.getDataTypes();
	}

	@Override
	public boolean hasData(@NotNull DataComponentType type) {
		return itemStack.hasData(type);
	}

	@Override
	public <T> @Nullable T getDataOrDefault(DataComponentType.@NotNull Valued<? extends T> type, @Nullable T fallback) {
		return itemStack.getDataOrDefault(type, fallback);
	}

	@Override
	public <T> @Nullable T getData(DataComponentType.@NotNull Valued<T> type) {
		return itemStack.getData(type);
	}

	@Override
	public @NotNull @Unmodifiable List<Component> computeTooltipLines(@NotNull TooltipContext tooltipContext, @Nullable Player player) {
		return itemStack.computeTooltipLines(tooltipContext, player);
	}

	@Override
	public boolean isEmpty() {
		return itemStack.isEmpty();
	}

	@Override
	public @NotNull ItemStack damage(int amount, @NotNull LivingEntity livingEntity) {
		return itemStack.damage(amount, livingEntity);
	}

	@Override
	public boolean canRepair(@NotNull ItemStack toBeRepaired) {
		return itemStack.canRepair(toBeRepaired);
	}

	@Override
	public boolean isRepairableBy(@NotNull ItemStack repairMaterial) {
		return itemStack.isRepairableBy(repairMaterial);
	}

	@Override
	public @NotNull String translationKey() {
		return itemStack.translationKey();
	}

	@Override
	public boolean hasItemFlag(@NotNull ItemFlag flag) {
		return itemStack.hasItemFlag(flag);
	}

	@Override
	public @NotNull Set<ItemFlag> getItemFlags() {
		return itemStack.getItemFlags();
	}

	@Override
	public void removeItemFlags(@NotNull ItemFlag... itemFlags) {
		itemStack.removeItemFlags(itemFlags);
	}

	@Override
	public void addItemFlags(@NotNull ItemFlag... itemFlags) {
		itemStack.addItemFlags(itemFlags);
	}

	@Override
	public void lore(@Nullable List<? extends Component> lore) {
		itemStack.lore(lore);
	}

	@Override
	public @Nullable List<Component> lore() {
		return itemStack.lore();
	}

	@Override
	public @NotNull ItemStack subtract(int qty) {
		return itemStack.subtract(qty);
	}

	@Override
	public @NotNull ItemStack subtract() {
		return itemStack.subtract();
	}

	@Override
	public @NotNull ItemStack add(int qty) {
		return itemStack.add(qty);
	}

	@Override
	public @NotNull ItemStack add() {
		return itemStack.add();
	}

	@Override
	public @NotNull ItemStack asQuantity(int qty) {
		return itemStack.asQuantity(qty);
	}

	@Override
	public @NotNull ItemStack asOne() {
		return itemStack.asOne();
	}

	@Override
	public int getMaxItemUseDuration(@NotNull LivingEntity entity) {
		return itemStack.getMaxItemUseDuration(entity);
	}

	@Override
	public byte @NotNull [] serializeAsBytes() {
		return itemStack.serializeAsBytes();
	}

	@Override
	public @NotNull ItemStack ensureServerConversions() {
		return itemStack.ensureServerConversions();
	}

	@Override
	public @NotNull Component effectiveName() {
		return itemStack.effectiveName();
	}

	@Override
	public @NotNull Component displayName() {
		return itemStack.displayName();
	}

	@Override
	public @NotNull HoverEvent<HoverEvent.ShowItem> asHoverEvent(@NotNull UnaryOperator<HoverEvent.ShowItem> op) {
		return itemStack.asHoverEvent(op);
	}

	@Override
	public @NotNull ItemStack enchantWithLevels(int levels, @NotNull RegistryKeySet<@NotNull Enchantment> keySet, @NotNull Random random) {
		return itemStack.enchantWithLevels(levels, keySet, random);
	}

	@Override
	public @NotNull ItemStack enchantWithLevels(int levels, boolean allowTreasure, @NotNull Random random) {
		return itemStack.enchantWithLevels(levels, allowTreasure, random);
	}

	@Override
	public boolean setItemMeta(@Nullable ItemMeta itemMeta) {
		return itemStack.setItemMeta(itemMeta);
	}

	@Override
	public boolean hasItemMeta() {
		return itemStack.hasItemMeta();
	}

	@Override
	public ItemMeta getItemMeta() {
		return itemStack.getItemMeta();
	}

	@Override
	public <M extends ItemMeta> boolean editMeta(@NotNull Class<M> metaClass, @NotNull Consumer<? super M> consumer) {
		return itemStack.editMeta(metaClass, consumer);
	}

	@Override
	public boolean editMeta(@NotNull Consumer<? super ItemMeta> consumer) {
		return itemStack.editMeta(consumer);
	}

	@Override
	public @NotNull Map<String, Object> serialize() {
		return itemStack.serialize();
	}

	@Override
	public void removeEnchantments() {
		itemStack.removeEnchantments();
	}

	@Override
	public int removeEnchantment(@NotNull Enchantment ench) {
		return itemStack.removeEnchantment(ench);
	}

	@Override
	public void addUnsafeEnchantment(@NotNull Enchantment ench, int level) {
		itemStack.addUnsafeEnchantment(ench, level);
	}

	@Override
	public void addUnsafeEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
		itemStack.addUnsafeEnchantments(enchantments);
	}

	@Override
	public void addEnchantment(@NotNull Enchantment ench, int level) {
		itemStack.addEnchantment(ench, level);
	}

	@Override
	public void addEnchantments(@NotNull Map<Enchantment, Integer> enchantments) {
		itemStack.addEnchantments(enchantments);
	}

	@Override
	public @NotNull Map<Enchantment, Integer> getEnchantments() {
		return itemStack.getEnchantments();
	}

	@Override
	public int getEnchantmentLevel(@NotNull Enchantment ench) {
		return itemStack.getEnchantmentLevel(ench);
	}

	@Override
	public boolean containsEnchantment(@NotNull Enchantment ench) {
		return itemStack.containsEnchantment(ench);
	}

	@Override
	public int hashCode() {
		return itemStack.hashCode();
	}

	@Override
	public @NotNull ZanyItem clone() {
		return Utils.getZany(itemStack.clone());
	}

	@Override
	public boolean isSimilar(@Nullable ItemStack stack) {
		return itemStack.isSimilar(stack);
	}

	@Override
	public boolean equals(Object obj) {
		return itemStack.equals(obj);
	}

	@Override
	public String toString() {
		return itemStack.toString();
	}

	@Override
	public int getMaxStackSize() {
		return itemStack.getMaxStackSize();
	}

	@Override
	public void setAmount(int amount) {
		itemStack.setAmount(amount);
	}

	@Override
	public int getAmount() {
		return itemStack.getAmount();
	}

	@Override
	public @NotNull ItemStack withType(@NotNull Material type) {
		return itemStack.withType(type);
	}

	@Override
	public @NotNull Material getType() {
		return itemStack.getType();
	}

	@Override
	public boolean editPersistentDataContainer(@NotNull Consumer<PersistentDataContainer> consumer) {
		return itemStack.editPersistentDataContainer(consumer);
	}

	@Override
	public @NotNull PersistentDataContainerView getPersistentDataContainer() {
		return itemStack.getPersistentDataContainer();
	}
}
