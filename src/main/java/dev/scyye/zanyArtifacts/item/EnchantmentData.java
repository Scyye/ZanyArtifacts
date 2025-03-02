//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.item;

import org.bukkit.enchantments.Enchantment;

public class EnchantmentData {
	Enchantment enchantment;
	int level;
	boolean ignoreMaxLevel;

	public EnchantmentData(Enchantment enchantment) {
		this.enchantment = enchantment;
		this.level = 1;
		this.ignoreMaxLevel = true;
	}

	public EnchantmentData(Enchantment enchantment, int level) {
		this.enchantment = enchantment;
		this.level = level;
		this.ignoreMaxLevel = true;
	}

	public EnchantmentData(Enchantment enchantment, int level, boolean ignoreMaxLevel) {
		this.enchantment = enchantment;
		this.level = level;
		this.ignoreMaxLevel = ignoreMaxLevel;
	}
}
