package dev.scyye.zanyArtifacts.item;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ActionableAbility {
	void onClick(PlayerInteractEvent event);
	void onLeftClick(PlayerInteractEvent event);
	void onRightClick(PlayerInteractEvent event);
	void onShiftLeftClick(PlayerInteractEvent event);
	void onShiftRightClick(PlayerInteractEvent event);
	void onLeftClickEntity(PlayerInteractEvent event);
	void onRightClickEntity(PlayerInteractEvent event);
	void onShiftLeftClickEntity(PlayerInteractEvent event);
	void onShiftRightClickEntity(PlayerInteractEvent event);
	void onLeftClickBlock(PlayerInteractEvent event);
	void onRightClickBlock(PlayerInteractEvent event);
	void onShiftLeftClickBlock(PlayerInteractEvent event);
	void onShiftRightClickBlock(PlayerInteractEvent event);
	void constantEffect(Player player);
}
