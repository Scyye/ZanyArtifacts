package dev.scyye.zanyArtifacts.item.impl;

import dev.scyye.zanyArtifacts.item.AttributeData;
import dev.scyye.zanyArtifacts.item.EnchantmentData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class TestBullet extends Bullet {

	public TestBullet(String id, AbilityMeta[] abilities) {
		super(id, abilities);
	}

	@Override
	public void onShoot(EntityShootBowEvent event, Player player, boolean failed) {
		if (failed)
			return;
		player.sendMessage(Component.text("Shooting TestBullet!"));
	}

	@Override
	public void leftClickAirAction(Player var1, ItemStack var2) {

	}

	@Override
	public void shiftLeftClickAirAction(Player var1, ItemStack var2) {

	}

	@Override
	public void leftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {

	}

	@Override
	public void shiftLeftClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {

	}

	@Override
	public void rightClickAirAction(Player var1, ItemStack var2) {

	}

	@Override
	public void shiftRightClickAirAction(Player var1, ItemStack var2) {

	}

	@Override
	public void rightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {

	}

	@Override
	public void shiftRightClickBlockAction(Player var1, PlayerInteractEvent var2, Block var3, ItemStack var4) {

	}
}
