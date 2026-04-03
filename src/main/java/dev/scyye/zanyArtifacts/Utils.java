//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import dev.scyye.zanyArtifacts.item.ZanyItem;

@SuppressWarnings("unused")
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
		return itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(Main.getKey("id"), PersistentDataType.STRING);
	}

	public static ZanyItem getZany(ItemStack itemStack) {
		return ZanyItem.allItems.get(itemStack.getItemMeta().getPersistentDataContainer().get(Main.getKey("id"), PersistentDataType.STRING));
	}

	public static float round(double value, int places) {
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (float) tmp / factor;
	}

	public static ItemStack[] getZanyItemsFromInventory(PlayerInventory inventory, String id) {
		List<ItemStack> items = new ArrayList<>();
		for (ItemStack itemStack : inventory.getContents()) {
			if (itemStack == null) continue;
			if (Utils.isZany(itemStack) && Utils.getZany(itemStack).getId().equals(id)) {
				items.add(itemStack);
			}
		}
		return items.toArray(new ItemStack[0]);
	}
}
