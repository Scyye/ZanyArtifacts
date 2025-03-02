//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.item;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.LivingEntity;
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

public class ItemListener implements Listener {
	public static List<ZanyItem> allItems = new ArrayList();

	public ItemListener() {
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
					if (item.leftClickAirAction(player, itemStack)) {
					}
				} else if (item.shiftLeftClickAirAction(player, itemStack)) {
				}
			} else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (!player.isSneaking()) {
					if (item.leftClickBlockAction(player, event, event.getClickedBlock(), itemStack)) {
					}
				} else if (item.shiftLeftClickBlockAction(player, event, event.getClickedBlock(), itemStack)) {
				}
			} else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
				if (!player.isSneaking()) {
					if (item.rightClickAirAction(player, itemStack)) {
					}
				} else if (item.shiftRightClickAirAction(player, itemStack)) {
				}
			} else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (!player.isSneaking()) {
					if (item.rightClickBlockAction(player, event, event.getClickedBlock(), itemStack)) {
					}
				} else if (item.shiftRightClickBlockAction(player, event, event.getClickedBlock(), itemStack)) {
				}
			}
		}

	}

	@EventHandler
	private void playerShootEvent(EntityShootBowEvent event) {
		LivingEntity var3 = event.getEntity();
		if (!Utils.isZany(event.getBow()))
			return;
		if (var3 instanceof Player player) {
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
