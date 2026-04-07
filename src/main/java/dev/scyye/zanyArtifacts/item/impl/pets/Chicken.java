package dev.scyye.zanyArtifacts.item.impl.pets;

import dev.scyye.zanyArtifacts.Utils;
import dev.scyye.zanyArtifacts.item.ZanyPet;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;

public class Chicken extends ZanyPet {
	long lastEggTime = 0L;
	AttributeModifier speedModifier = new AttributeModifier(new NamespacedKey("zany", "chicken_speed"), 0.2, AttributeModifier.Operation.MULTIPLY_SCALAR_1);

	public Chicken(String id, String mcHead, boolean glow, AbilityMeta[] abilities, String... lore) {
		super(id, mcHead, glow, abilities, lore);
	}

	@Override
	public void effect(Player player, ZanyPet pet) {
		// Gives you an egg every 2 minutes
		if (System.currentTimeMillis() - lastEggTime >= 1000 * 60 * 2) {
			Utils.givePlayerItemSafely(player, new ItemStack(org.bukkit.Material.EGG, 1));
			lastEggTime = System.currentTimeMillis();
		}
	}

	@Override
	public void equip(Player player, ZanyPet pet) {
		player.getAttribute(Attribute.MOVEMENT_SPEED).addModifier(speedModifier);
	}

	@Override
	public void unequip(Player player, ZanyPet pet) {
		player.getAttribute(Attribute.MOVEMENT_SPEED).removeModifier(speedModifier);
	}
}
