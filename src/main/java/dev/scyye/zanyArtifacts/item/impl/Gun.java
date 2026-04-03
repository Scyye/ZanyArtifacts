//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.item.impl;

import java.util.List;
import java.util.Objects;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import dev.scyye.zanyArtifacts.Main;
import dev.scyye.zanyArtifacts.item.AttributeData;
import dev.scyye.zanyArtifacts.item.EnchantmentData;
import dev.scyye.zanyArtifacts.item.ZanyBow;
import dev.scyye.zanyArtifacts.item.ZanyItem;

public class Gun extends ZanyItem implements ZanyBow {
	NamespacedKey key;

	public Gun(String id, ItemStack itemStack, int amount, String displayName, boolean unbreakable, EnchantmentData[] enchantments, AttributeData[] attributes, ItemFlag[] itemFlags, String[] lore, AbilityMeta[] abilities) {
		super(id, itemStack, amount, displayName, unbreakable, enchantments, attributes, itemFlags, lore, abilities);
		this.key = Main.getKey("ammo");
	}


	public void onShoot(ItemStack bow, EntityShootBowEvent event, Player player) {
		if (this.getAmmo() <= 0) {
			player.sendMessage("Reload by left clicking!");
			assert event.getConsumable() != null;
			player.getInventory().addItem(event.getConsumable());
			event.setCancelled(true);
			return;
		}
		this.setAmmo(this.getAmmo() - 1);
		if (this.getAmmo() < 0) {
			this.setAmmo(0);
		}
		player.sendMessage("Current Ammo: " + this.getAmmo());
	}

	public void onLand(boolean block, ItemStack bow, ProjectileHitEvent event, Player player) {
	}

	public void onItemCreate(ItemStack itemStack) {
		itemStack.getItemMeta().getPersistentDataContainer().set(this.key, PersistentDataType.INTEGER, 6);
		this.getRawItem().setItemMeta(itemStack.getItemMeta());
	}

	public void setAmmo(int amt) {
		ItemMeta meta = this.getRawItem().getItemMeta();
		meta.getPersistentDataContainer().set(this.key, PersistentDataType.INTEGER, amt);
		this.getRawItem().setItemMeta(meta);
	}

	public int getAmmo() {
		PersistentDataContainer data = this.getRawItem().getItemMeta().getPersistentDataContainer();
		Integer ammo = data.get(this.key, PersistentDataType.INTEGER);
		return Objects.requireNonNullElse(ammo, 0);
	}

	@Override
	public void leftClickAirAction(Player player, ItemStack itemStack) {
		this.setAmmo(6);
	}

	@Override
	public void updateItem(Player player, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		List<Component> lore = meta.lore();
		assert lore != null;
		lore.set(0, Component.text("Ammo: " + this.getAmmo()));
		meta.lore(lore);
		item.setItemMeta(meta);
	}
}
