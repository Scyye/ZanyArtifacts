package dev.scyye.zanyArtifacts.item.impl.pets;

import dev.scyye.zanyArtifacts.item.ZanyPet;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnderChest extends ZanyPet {
	public EnderChest(String id, String mcHead, boolean glow, AbilityMeta[] abilities, String... lore) {
		super(id, mcHead, glow, abilities, lore);
	}

	@Override
	public void effect(Player player, ZanyPet pet) {

	}

	@Override
	public void equip(Player player, ZanyPet pet) {

	}

	@Override
	public void unequip(Player player, ZanyPet pet) {

	}

	@Override
	public void leftClickAirAction(Player player, ItemStack itemStack) {
		player.openInventory(player.getEnderChest());
	}
}
