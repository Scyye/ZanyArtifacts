//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.item.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import dev.scyye.zanyArtifacts.item.AttributeData;
import dev.scyye.zanyArtifacts.item.EnchantmentData;
import dev.scyye.zanyArtifacts.item.ZanyItem;

public class TechnobladeSword extends ZanyItem {
	List<UUID> piglins = new ArrayList<>();

	public TechnobladeSword(String id, ItemStack itemStack, int amount, String displayName, boolean unbreakable, EnchantmentData[] enchantments, AttributeData[] attributes, ItemFlag[] itemFlags, String[] lore, AbilityMeta[] abilities) {
		super(id, itemStack, amount, displayName, unbreakable, enchantments, attributes, itemFlags, lore, abilities);
	}

	public void onItemCreate(ItemStack itemStack) {
	}

	public boolean leftClickAirAction(Player player, ItemStack itemStack) {
		player.getWorld().setTime(13000L);
		Entity piglin = player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIFIED_PIGLIN);
		piglin.setInvulnerable(true);
		piglin.customName(Component.text("Technoblade Piglin", TextColor.color(0xFFC109)));
		piglins.add(piglin.getUniqueId());
		piglin.setCustomNameVisible(true);
		return true;
	}

	public boolean shiftLeftClickAirAction(Player player, ItemStack itemStack) {
		return false;
	}

	public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
		this.leftClickAirAction(player, itemStack);
		return false;
	}

	public boolean shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
		return false;
	}

	public boolean rightClickAirAction(Player player, ItemStack itemStack) {
		player.getWorld().setTime(0L);
		for (Entity entity : player.getWorld().getEntities()) {
			if (piglins.contains(entity.getUniqueId())) {
				piglins.remove(entity.getUniqueId());
				entity.remove();
			}
		}

		return true;
	}

	public boolean shiftRightClickAirAction(Player player, ItemStack itemStack) {
		return false;
	}

	public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
		this.rightClickAirAction(player, itemStack);
		return true;
	}

	public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack itemStack) {
		return false;
	}
}
