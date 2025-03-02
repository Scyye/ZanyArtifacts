package dev.scyye.zanyArtifacts.enchant;

import java.util.*;
import javax.annotation.Nullable;

import dev.scyye.zanyArtifacts.SuperList;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import dev.scyye.zanyArtifacts.Main;

public class EnchantmentListener implements Listener {
	public EnchantmentListener() {
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null)
			return;
		if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchants())
			return;

		ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
		ZanyEnchant[] itemEnchants = this.getZanyEnchants(heldItem);

		for (ZanyEnchant enchant : itemEnchants)
			if (enchant != null)
				enchant.onBreakBlock(event.getBlock(), heldItem, event);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		List<ZanyEnchant> inventoryEnchants = new ArrayList<>();

		for (ItemStack item : event.getPlayer().getInventory()) {
			ZanyEnchant[] itemEnchants = this.getZanyEnchants(item);

			for (ZanyEnchant enchant : itemEnchants)
				if (enchant != null)
					inventoryEnchants.add(enchant);
		}

		for (ZanyEnchant enchantment : inventoryEnchants)
			enchantment.onMove(event, event.getTo());
	}

	@EventHandler
	public void onHitEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player player) {
			ZanyEnchant[] itemEnchants = getZanyEnchants(player.getInventory().getItemInMainHand());

			for(var enchant : itemEnchants)
				if (enchant != null)
					enchant.onHitEntity(event.getEntity(), player.getInventory().getItemInMainHand(), event.getCause(), event);
		}
		if (!(event.getEntity() instanceof LivingEntity livingEntity) || !(event.getDamager() instanceof LivingEntity) || livingEntity.getEquipment() == null)
			return;
		ItemStack[] armor = livingEntity.getEquipment().getArmorContents();

		for(var item : armor)
			for (ZanyEnchant enchant : getZanyEnchants(item))
				if (enchant != null)
					enchant.onGetHit((LivingEntity) event.getDamager(), event.getCause(), event);
	}

	@EventHandler
	public void onKill(EntityDeathEvent event) {
		if (event.getEntity().getKiller() == null)
			return;

		ItemStack itemStack = event.getEntity().getKiller().getInventory().getItemInMainHand();
		if (itemStack.getItemMeta() == null)
			return;
		if (!itemStack.getItemMeta().hasEnchants())
			return;

		for (ZanyEnchant enchant : getZanyEnchants(itemStack))
			if (enchant != null) {
				EntityDamageEvent.DamageCause r = null;
				if (event.getEntity().getLastDamageCause() != null)
					r = event.getEntity().getLastDamageCause().getCause();
				enchant.onKillEntity(event.getEntity(), itemStack, r, event);
			}

	}

	@EventHandler
	public void onShoot(EntityShootBowEvent event) {
		if (!(event.getEntity() instanceof Player player))
			return;
		if (!player.getInventory().getItemInMainHand().getItemMeta().hasEnchants())
			return;


		for (ZanyEnchant enchant : this.getZanyEnchants(event.getBow()))
			if (enchant instanceof ZanyBowEnchant bowEnchant)
				bowEnchant.onShootBow(event.getBow(), event, (Player) event.getEntity());
	}

	@EventHandler
	public void onLand(ProjectileHitEvent event) {
		if (!(event.getEntity().getShooter() instanceof Player player))
			return;
		if (!player.getInventory().getItemInMainHand().getItemMeta().hasEnchants())
			return;


		for (ZanyEnchant enchant : getZanyEnchants(player.getInventory().getItemInMainHand()))
			if (enchant instanceof ZanyBowEnchant bowEnchant)
				if (event.getHitBlock() != null)
					bowEnchant.onArrowLandBlock(event.getHitBlock(), player.getInventory().getItemInMainHand(), event, player);
				else
					bowEnchant.onArrowLandEntity(event.getHitEntity(), player.getInventory().getItemInMainHand(), event, player);
	}

	public static ZanyEnchant[] getZanyEnchants(@Nullable ItemStack itemStack) {
		SuperList<ZanyEnchant> itemEnchants = new SuperList<>();
		if (itemStack == null)
			return new ZanyEnchant[0];
		if (itemStack.getType().isAir())
			return new ZanyEnchant[0];
		if (itemStack.getItemMeta() == null)
			return new ZanyEnchant[0];
		if (!itemStack.getItemMeta().hasEnchants())
			return new ZanyEnchant[0];

		for (ZanyEnchant zanyEnchant : Main.enchantIds.values())
			itemEnchants.addIf(zanyEnchant,
					itemStack.getEnchantments().keySet().stream().anyMatch(enchantment ->
							enchantment.getKey().equals(zanyEnchant.getKey())));

		return itemEnchants.toArray(new ZanyEnchant[0]);
	}
}
