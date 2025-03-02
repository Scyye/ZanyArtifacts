//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.enchant;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

public interface ZanyBowEnchant {
	void onShootBow(ItemStack var1, EntityShootBowEvent var2, Player var3);

	void onArrowLandEntity(Entity var1, ItemStack var2, ProjectileHitEvent var3, Player var4);

	void onArrowLandBlock(Block var1, ItemStack var2, ProjectileHitEvent var3, Player var4);
}
