package dev.scyye.zanyArtifacts.item.impl.pets;

import dev.scyye.zanyArtifacts.Utils;
import dev.scyye.zanyArtifacts.item.ZanyPet;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Notch extends ZanyPet {
	double damageNegated = 0;
	double initialHealth = 0;
	public Notch(String id, String mcHead, boolean glow, AbilityMeta[] abilities, String... lore) {
		super(id, mcHead, glow, abilities, lore);
	}

	@Override
	public void effect(Player player, ZanyPet pet) {
		if (player.getHealth() < initialHealth) {
			damageNegated += initialHealth - player.getHealth();
			player.setHealth(initialHealth);
			ZanyPet updatedPet = (ZanyPet) pet.clone();
			ItemMeta petMeta = updatedPet.getItemMeta();
			List<Component> lore = petMeta.lore();
			assert lore != null;
			lore.remove(lore.getLast());
			lore.add(Component.text("Negated " + damageNegated + " damage"));
			petMeta.lore(lore);
			updatedPet.setItemMeta(petMeta);
			// Update the pet in the player's inventory
			player.getInventory().forEach(i -> {
				if (Utils.isZany(i) && Utils.getZany(i).getId().equals(pet.getId())) {
					int slot = player.getInventory().first(i);
					player.getInventory().setItem(slot, updatedPet);
				}
			});
		}
		if (player.getHealth() > initialHealth) {
			initialHealth = player.getHealth();
		}
	}

	@Override
	public void equip(Player player, ZanyPet pet) {
		initialHealth = player.getHealth();
	}

	@Override
	public void unequip(Player player, ZanyPet pet) {

	}
}
