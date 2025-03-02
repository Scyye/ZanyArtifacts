//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.item;

import org.bukkit.enchantments.Enchantment;

public class EnchantmentData {
	Enchantment enchantment;
	int level;
	boolean ignoreMaxLevel = true;

	public EnchantmentData(Enchantment enchantment, int level) {
		this.enchantment = enchantment;
		this.level = level;
	}
}
