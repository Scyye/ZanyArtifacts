package dev.scyye.zanyArtifacts.item.impl;

import dev.scyye.zanyArtifacts.Main;
import dev.scyye.zanyArtifacts.Utils;
import dev.scyye.zanyArtifacts.item.AttributeData;
import dev.scyye.zanyArtifacts.item.EnchantmentData;
import dev.scyye.zanyArtifacts.item.ZanyItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;

public abstract class Bullet extends ZanyItem {
	public static final NamespacedKey bulletKey = Main.getKey("bullet_type");
	public static HashMap<String, Bullet> allBullets = new HashMap<>();
	String bulletType;

	public Bullet(String id, ItemStack itemStack, int amount, String displayName, boolean unbreakable, EnchantmentData[] enchantments, AttributeData[] attributes, ItemFlag[] itemFlags, String[] lore, AbilityMeta[] abilities) {
		super(id, itemStack, amount, displayName, unbreakable, enchantments, attributes, itemFlags, lore, abilities);
		System.out.println("Creating bullet: " + id);
		this.bulletType = id;
		ItemMeta meta = itemStack.getItemMeta();
		meta.getPersistentDataContainer().set(bulletKey, PersistentDataType.STRING, id);
		itemStack.setItemMeta(meta);
		allBullets.put(id, this);
		System.out.println("Registered bullet: " + id);
	}

	public Bullet(String id, AbilityMeta[] abilities) {
		this(id, new ItemStack(Material.ARROW, 1), 1, beautify(id), false,
				new EnchantmentData[]{}, new AttributeData[]{}, new ItemFlag[]{}, new String[]{"A bullet."}, abilities);
	}

	private static String beautify(String id) {
		String[] parts = id.split("_");
		StringBuilder displayName = new StringBuilder();
		for (String part : parts) {
			displayName.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase()).append(" ");
		}
		return displayName.toString().trim();
	}

	@Override
	public void onItemCreate(ItemStack var1) {
		if (var1.getType() != Material.ARROW && var1.getType() != Material.SPECTRAL_ARROW && var1.getType() != Material.TIPPED_ARROW) {
			throw new IllegalArgumentException("Bullet must be an arrow type item!");
		}
	}

	public void onShoot(EntityShootBowEvent event, Player player, boolean failed) {
		// Default implementation does nothing
	}

	public static class BulletListener implements Listener {
		@EventHandler
		public void onShoot(EntityShootBowEvent event) {
			Player player = (Player) event.getEntity();
			if (event.getBow() == null ||
					!Utils.isZany(event.getBow()) ||
					event.getConsumable() == null ||
					!(event.getConsumable().getPersistentDataContainer().has(bulletKey))) {
				player.sendMessage("This is not a bullet!");
				return; // Not a bullet
			}
			ItemStack bulletItem = event.getConsumable();
			String type = bulletItem.getPersistentDataContainer().get(bulletKey, PersistentDataType.STRING);
			Bullet bullet = allBullets.get(type);
			if (bullet != null) {
				bullet.onShoot(event, player, event.isCancelled());
			} else {
				player.sendMessage("Unknown bullet type: " + type);
			}
		}
	}
}
