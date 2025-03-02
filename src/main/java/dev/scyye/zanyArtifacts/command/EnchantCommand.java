package dev.scyye.zanyArtifacts.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.keys.ItemTypeKeys;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import net.kyori.adventure.key.Key;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import dev.scyye.zanyArtifacts.Main;
import dev.scyye.zanyArtifacts.Utils;
import dev.scyye.zanyArtifacts.enchant.ZanyEnchant;

@Deprecated(forRemoval = true)
public class EnchantCommand implements TabExecutor {
	private static final TreeMap<Integer, String> map = new TreeMap();

	public EnchantCommand() {
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return true;/*
		if (!(sender instanceof Player player)) {
			return false;
		}

		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Must specify a ZanyEnchant!");
			return true;
		}

		if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
			player.sendMessage(ChatColor.RED + "You aren't holding an item!");
			return true;
		}

		ItemStack itemStack = player.getInventory().getItemInMainHand();
		ZanyEnchant enchant = Main.plugin.enchantIds.get(args[0]);

		if (!enchant.getTargetItem().registryKey().equals(ItemTypeTagKeys.create(Key.key(enchant.getTargetItem().key().namespace())))) {
			player.sendMessage(ChatColor.RED + "You can't enchant this item with this enchant!");
			return true;
		}

		for (Enchantment enchantment : itemStack.getEnchantments().keySet()) {
			if (enchant.conflictsWith(enchantment)) {
				player.sendMessage(ChatColor.RED + "This enchantment conflicts with: " + enchantment.getKey().getKey());
				return false;
			}
		}

		int level = args.length >= 2 ? Integer.parseInt(args[1]) : 1;
		ItemMeta meta = itemStack.getItemMeta();
		assert meta != null;

		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + enchant.getName() + " " + toRoman(level));
		meta.setLore(lore);
		itemStack.setItemMeta(meta);
		itemStack.addUnsafeEnchantment(enchant, level);

		return false;*/
	}

	public static String toRoman(int number) {
		int l = (Integer)map.floorKey(number);
		if (number == l) {
			return (String)map.get(number);
		} else {
			String var10000 = (String)map.get(l);
			return var10000 + toRoman(number - l);
		}
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return args.length > 1 ? Utils.getNumberArgs(args[1]) : ZanyEnchant.allEnchants.keySet().stream().toList();
	}

	static {
		map.put(1000, "M");
		map.put(900, "CM");
		map.put(500, "D");
		map.put(400, "CD");
		map.put(100, "C");
		map.put(90, "XC");
		map.put(50, "L");
		map.put(40, "XL");
		map.put(10, "X");
		map.put(9, "IX");
		map.put(5, "V");
		map.put(4, "IV");
		map.put(1, "I");
	}
}
