//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import dev.scyye.zanyArtifacts.Main;
import dev.scyye.zanyArtifacts.Utils;
import org.jetbrains.annotations.NotNull;

public abstract class ZanyItem {
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
		this.createItem();
		allItems.put(this.id, this);
	}

	public ItemStack createItem() {
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

		for(String line : this.lore) {
			lore.add(colorizeString(line));
		}
		for (AbilityMeta ability : this.abilities) {
			lore.addAll(ability.buildAbilityLoreString());
		}

		meta.lore(lore);
		meta.getPersistentDataContainer().set(Main.getKey("id"), PersistentDataType.STRING, this.id);
		this.itemStack.setItemMeta(meta);
		this.onItemCreate(this.itemStack);
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
			CONSTANT
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

			abilityLore.add(Component.text("Ability: " + name, TextColor.color(0xe3c70e))
					.append(Component.text((shift?"SHIFT ":"") + type.name().replaceAll("_", " "),
							TextColor.color(0xc7e30e)))
					.append(Component.text(consume?" [CONSUMES ITEM]":"",
							TextColor.color(0xE30008))));
			abilityLore.add(Component.text(description,
					TextColor.color(0x7f7f7f)));
			abilityLore.add(Component.text("Cooldown: " + Utils.round(cooldownMs/1000, 2) + "s",
					TextColor.color(0xe32110)));
			return abilityLore;
		}
	}

	public void updateLore() {
		Utils.loreItem(this.itemStack, Arrays.asList(this.lore));
	}

	public abstract void onItemCreate(ItemStack var1);

	public abstract void leftClickAirAction(Player var1, ItemStack var2);

	public abstract void shiftLeftClickAirAction(Player var1, ItemStack var2);

	public abstract void leftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4);

	public abstract void shiftLeftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4);

	public abstract void rightClickAirAction(Player var1, ItemStack var2);

	public abstract void shiftRightClickAirAction(Player var1, ItemStack var2);

	public abstract void rightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4);

	public abstract void shiftRightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4);

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
}
