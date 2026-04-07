//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.item;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import dev.scyye.zanyArtifacts.Utils;

import java.util.ArrayList;
import java.util.List;

public class ItemListener implements Listener {
	public ItemListener() {
	}

	@EventHandler(
			priority = EventPriority.HIGH
	)
	private void onInventoryUpdate(PlayerInventorySlotChangeEvent event) {
		Player player = event.getPlayer();
		ItemStack oldItem = event.getOldItemStack();
		ItemStack newItem = event.getNewItemStack();

		// if neither old nor new item is zany, ignore
		if (!Utils.isZany(oldItem) && !Utils.isZany(newItem))
			return;

		// Handle unequip: if an old item was a pet and player no longer has that pet anywhere -> unequip
		if (oldItem.hasItemMeta() && Utils.isZany(oldItem) && Utils.getZany(oldItem) instanceof ZanyPet pet) {
			boolean stillHas = false;
			for (ItemStack is : player.getInventory()) {
				if (is == null) continue;
				ZanyItem zi = Utils.getZany(is);
				if (zi != null && zi.getId().equals(pet.getId())) {
					stillHas = true;
					break;
				}
			}
			if (!stillHas) {
				try {
					pet.unequip(player, pet);
				} catch (Exception ignored) {}
				List<ZanyPet> list = ZanyPet.currentPets.get(player);
				if (list != null) {
					list.removeIf(p -> p.getId().equals(pet.getId()));
					if (list.isEmpty()) ZanyPet.currentPets.remove(player);
				}
			}
		}



		// Handle equip: if new item is a pet and player didn't already have it equipped -> equip
		if (newItem.hasItemMeta() && Utils.isZany(newItem) && Utils.getZany(newItem) instanceof ZanyPet pet) {
			List<ZanyPet> list = ZanyPet.currentPets.getOrDefault(player, new ArrayList<>());
			boolean already = list.stream().anyMatch(p -> p.getId().equals(pet.getId()));
			if (!already) {
				try {
					pet.equip(player, pet);
				} catch (Exception ignored) {}
				list.add(pet);
				ZanyPet.currentPets.put(player, list);
			}
		}
	}

	@EventHandler(
			priority = EventPriority.HIGH
	)
	private void onPlayerUse(PlayerInteractEvent event) {
		if (event.getHand() == EquipmentSlot.HAND && Utils.isZany(event.getPlayer().getInventory().getItemInMainHand())) {
			this.useZanyItem(event, event.getPlayer().getInventory().getItemInMainHand());
		}

		if (event.getHand() == EquipmentSlot.OFF_HAND && Utils.isZany(event.getPlayer().getInventory().getItemInOffHand())) {
			this.useZanyItem(event, event.getPlayer().getInventory().getItemInOffHand());
		}

	}

	private void useZanyItem(PlayerInteractEvent event, ItemStack itemStack) {
		Player player = event.getPlayer();
		ZanyItem item = Utils.getZany(itemStack);
		if (item != null) {
			if (event.getAction() == Action.LEFT_CLICK_AIR) {
				if (!player.isSneaking()) {
					item.leftClickAirAction(player, itemStack);
				} else {
					item.shiftLeftClickAirAction(player, itemStack);
				}
			} else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (!player.isSneaking()) {
					item.leftClickBlockAction(player, event, event.getClickedBlock(), itemStack);
				} else {
					item.shiftLeftClickBlockAction(player, event, event.getClickedBlock(), itemStack);
				}
			} else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
				if (!player.isSneaking()) {
					item.rightClickAirAction(player, itemStack);
				} else {
					item.shiftRightClickAirAction(player, itemStack);
				}
			} else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (!player.isSneaking()) {
					item.rightClickBlockAction(player, event, event.getClickedBlock(), itemStack);
				} else {
					item.shiftRightClickBlockAction(player, event, event.getClickedBlock(), itemStack);
				}
			}
		}

	}

	@EventHandler
	private void playerShootEvent(EntityShootBowEvent event) {
		if (event.getBow() == null || !Utils.isZany(event.getBow()))
			return;
		if (event.getEntity() instanceof Player player) {
			ZanyBow zanyBow = (ZanyBow)Utils.getZany(event.getBow());
			if (zanyBow != null) {
				zanyBow.onShoot(event.getBow(), event, player);
			}
		}

	}

	@EventHandler
	public void onLand(ProjectileHitEvent event) {
		ProjectileSource var3 = event.getEntity().getShooter();
		if (var3 instanceof Player player) {
			if (!Utils.isZany(player.getInventory().getItemInMainHand()))
				return;
			ZanyBow zanyBow = (ZanyBow)Utils.getZany(player.getInventory().getItemInMainHand());
			zanyBow.onLand(event.getHitBlock() != null, player.getInventory().getItemInMainHand(), event, player);
		}

	}
}
