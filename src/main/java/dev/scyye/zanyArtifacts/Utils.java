//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import dev.scyye.zanyArtifacts.item.ZanyItem;

public class Utils {
	public Utils() {
	}

	public static void givePlayerItemSafely(Player player, ItemStack itemStack) {

		for (ItemStack item : player.getInventory().addItem(new ItemStack[]{itemStack}).values()) {
			if (item != null && item.getType() != Material.AIR) {
				Item itemEntity = player.getWorld().dropItemNaturally(player.getLocation(), item);
				itemEntity.setVelocity(player.getLocation().getDirection().multiply(0.1F));
			}
		}

	}

	public static List<String> getNumberArgs(String in) {
		List<String> poss = new ArrayList<>(Arrays.asList(in + "1", in + "2", in + "3", in + "4", in + "5", in + "6", in + "7", in + "8", in + "9", in + "0"));
		List<String> list = new ArrayList<>();

		for (String s : poss) {
			if (s.startsWith(in)) {
				list.add(s);
			}
		}

		Objects.requireNonNull(in);
		list.removeIf(in::equals);
		return list;
	}

	public static boolean isZany(ItemStack itemStack) {
		return itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.plugin, "id"), PersistentDataType.STRING);
	}

	public static ZanyItem getZany(ItemStack itemStack) {
		return Main.plugin.itemIds.get(itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.plugin, "id"), PersistentDataType.STRING));
	}

	public static void loreItem(ItemStack itemStack, List<String> lore) {
		ItemMeta itemMeta = itemStack.getItemMeta();

		assert itemMeta != null;

		itemMeta.setLore(lore);
		itemStack.setItemMeta(itemMeta);
	}
}
