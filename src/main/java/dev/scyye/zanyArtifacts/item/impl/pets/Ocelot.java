package dev.scyye.zanyArtifacts.item.impl.pets;

import dev.scyye.zanyArtifacts.item.ZanyPet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.UUID;

public class Ocelot extends ZanyPet {
	UUID ocelotId;
	public Ocelot(String id, String mcHead, boolean glow, AbilityMeta[] abilities, String... lore) {
		super(id, mcHead, glow, abilities, lore);
	}

	@Override
	public void effect(Player player, ZanyPet pet) {
		for (Entity entity : player.getNearbyEntities(6, 6, 6)) {
			if (entity instanceof Creeper creeper) {
				Location playerLoc = player.getLocation();
				Location creeperLoc = creeper.getLocation();

				Vector away = creeperLoc.toVector()
						.subtract(playerLoc.toVector())
						.normalize()
						.multiply(15); // move 6 blocks away

				Location fleeTo = creeperLoc.clone().add(away);

				creeper.setTarget(null); // stop targeting the player
				creeper.getPathfinder().moveTo(fleeTo);
			}
		}
	}

	@Override
	public void equip(Player player, ZanyPet pet) {
		player.addPotionEffect(
				new PotionEffect(
						PotionEffectType.NIGHT_VISION,
						-1,
						255,
						false,
						false,
						false

				)
		);
	}

	@Override
	public void unequip(Player player, ZanyPet pet) {
		player.removePotionEffect(PotionEffectType.NIGHT_VISION);
	}
}
