package dev.scyye.zanyArtifacts.item;

import com.google.common.annotations.Beta;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.List;

//TODO:
// ADD FEEDING
@Beta
public abstract class ZanyPet extends ZanyItem{
	public static HashMap<Player, List<ZanyPet>> currentPets = new HashMap<>();

	public ZanyPet(String id, String mcHead, boolean glow, AbilityMeta[] abilities, String... lore) {
		ItemStack head = new ItemStack(org.bukkit.Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(mcHead));
		meta.setUnbreakable(true);
		head.setItemMeta(meta);
		super("pet_" + id, head, 1, "&6"+prettify(id)+" Pet", true, glow ? new EnchantmentData[]{new EnchantmentData(Enchantment.UNBREAKING, 1)} : new EnchantmentData[0], new AttributeData[0], new ItemFlag[]{ItemFlag.HIDE_ENCHANTS}, lore, abilities);
	}

	private ZanyPet(String id, ItemStack itemStack, int amount, String displayName, boolean unbreakable, EnchantmentData[] enchantments, AttributeData[] attributes, ItemFlag[] itemFlags, String[] lore, AbilityMeta[] abilities) {
		super(id, itemStack, amount, displayName, unbreakable, enchantments, attributes, itemFlags, lore, abilities);
	}

	@Override
	public void onItemCreate(ItemStack var1) {

	}

	@Override
	public void leftClickAirAction(Player var1, ItemStack var2) {

	}

	public abstract void effect(Player player, ZanyPet pet);
	public abstract void equip(Player player, ZanyPet pet);
	public abstract void unequip(Player player, ZanyPet pet);

	private static String prettify(String str) {
		StringBuilder sb = new StringBuilder();
		for (char c : str.toCharArray()) {
			if (sb.isEmpty()) {
				sb.append(Character.toUpperCase(c));
			} else if (Character.isUpperCase(c)) {
				sb.append(" ").append(c);
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
