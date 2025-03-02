//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.item.impl;

import java.util.Objects;
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
	}


	public void onShoot(ItemStack bow, EntityShootBowEvent event, Player player) {
		this.setAmmo(this.getAmmo() - 1);
		player.sendMessage("Current Ammo: " + this.getAmmo());
		if (this.getAmmo() < 0) {
			player.sendMessage("Reload by left clicking!");
			player.getInventory().addItem(new ItemStack(Material.ARROW, 1));
			event.setCancelled(true);
		}

	}

	public void onLand(boolean block, ItemStack bow, ProjectileHitEvent event, Player player) {
	}

	public void onItemCreate(ItemStack itemStack) {
		this.key = new NamespacedKey(Main.plugin, "ammo");
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

	public void leftClickAirAction(Player player, ItemStack itemStack) {
		this.setAmmo(6);
	}

	public void shiftLeftClickAirAction(Player player, ItemStack itemStack) {
	}

	public void leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
		this.leftClickAirAction(player, itemStack);
	}

	public void shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
	}

	public void rightClickAirAction(Player player, ItemStack itemStack) {
	}

	public void shiftRightClickAirAction(Player player, ItemStack itemStack) {
	}

	public void rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
	}

	public void shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
	}
}
