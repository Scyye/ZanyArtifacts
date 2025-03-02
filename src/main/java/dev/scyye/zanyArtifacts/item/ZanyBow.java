//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.item;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

public interface ZanyBow {
	void onShoot(ItemStack var1, EntityShootBowEvent var2, Player var3);

	void onLand(boolean var1, ItemStack var2, ProjectileHitEvent var3, Player var4);
}
