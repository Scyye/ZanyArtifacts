package dev.scyye.zanyArtifacts.menu.standalone;

import dev.scyye.zanyArtifacts.Utils;
import dev.scyye.zanyArtifacts.item.RecipeManager;
import dev.scyye.zanyArtifacts.item.ZanyItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

public class CraftCreator implements Listener {

	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		if (!event.getView().title().equals(Component.text("Crafting Menu"))) return;
		if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.GRAY_STAINED_GLASS_PANE)
			event.setCancelled(true);
		if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.GREEN_CONCRETE) {
			event.setCancelled(true);
			String[] recipe = new String[9];
			// 0 1 2
 			// 9 10 11
 			// 18 19 20
			for (int i = 0; i < 9; i++) {
				int index = i < 3 ? i : (i < 6 ? i + 6 : i + 12);
				ItemStack item = event.getInventory().getItem(index);
				if (item == null || item.getType()==Material.AIR) {
					recipe[i] = "AIR";
				} else if (Utils.isZany(item)) {
					ZanyItem zanyItem = Utils.getZany(item);
					recipe[i] = zanyItem.getId();
				} else if (item.getType() != Material.AIR) {
					recipe[i] = item.getType().name();
				}
			}

			ItemStack result = event.getInventory().getItem(13);

			if (result == null || result.getType() == Material.AIR) {
				event.getWhoClicked().sendMessage(Component.text("You must set a result item!").color(TextColor.color(255,0,0)));
				return;
			}

			String resultId = Utils.isZany(result) ? Utils.getZany(result).getId() : result.getType().name();


			RecipeManager.addRecipe(new RecipeManager.Recipe(
					UUID.randomUUID().toString(),
					recipe,
					resultId,
					true, // assuming all recipes are shaped for simplicity
					result.getAmount()
			));
		} else if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.RED_CONCRETE) {
			event.setCancelled(true);
			String[] recipe = new String[9];
			// 0 1 2
			// 9 10 11
			// 18 19 20
			for (int i = 0; i < 9; i++) {
				int index = i < 3 ? i : (i < 6 ? i + 6 : i + 12);
				ItemStack item = event.getInventory().getItem(index);
				if (item == null || item.getType()==Material.AIR) {
					recipe[i] = "AIR";
					continue;
				} else if (Utils.isZany(item)) {
					ZanyItem zanyItem = Utils.getZany(item);
					recipe[i] = zanyItem.getId();
				} else if (item.getType() != Material.AIR) {
					recipe[i] = item.getType().name();
				}
			}

			ItemStack result = event.getInventory().getItem(13);

			if (result == null || result.getType() == Material.AIR) {
				event.getWhoClicked().sendMessage(Component.text("You must set a result item!").color(TextColor.color(255,0,0)));
				return;
			}

			String resultId = Utils.isZany(result) ? Utils.getZany(result).getId() : result.getType().name();

			String recipeId = RecipeManager.getRecipes().stream().filter(
					recipe1 -> recipe1.result().equals(resultId) && Arrays.equals(recipe1.ingredients(), recipe)
			).limit(1).findFirst().orElse(null).id();
			RecipeManager.removeRecipe(recipeId);

			event.getWhoClicked().sendMessage(Component.text("Recipe deleted!").color(TextColor.color(255,0,0)));
		}
	}

	private static ItemStack deleteButton() {
		ItemStack item = new ItemStack(Material.RED_CONCRETE);
		item.editMeta(meta -> {
			meta.displayName(Component.text("Delete Recipe").color(TextColor.color(255,0,0)));
		});
		return item;
	}

	private static ItemStack addButton() {
		ItemStack item = new ItemStack(Material.GREEN_CONCRETE);
		item.editMeta(meta -> {
			meta.displayName(Component.text("Add Recipe").color(TextColor.color(0,255,0)));
		});
		return item;
	}
	private static ItemStack placeholderItem() {
		ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		item.editMeta(meta -> {
			meta.displayName(Component.text(" ").color(TextColor.color(128,128,128)));
		});
		return item;
	}

	public static void open(Player player) {
		Inventory inv = Bukkit.createInventory(player, 27, Component.text("Crafting Menu"));
		inv.setItem(26, addButton());
		inv.setItem(24, deleteButton());
		// set the first 3x3 grid to, and fill the rest with placeholders except for 13
		for (int i = 0; i < 27; i++) {
			Set<Integer> skipIndices = Set.of(0, 1, 2, 9, 10, 11, 13, 18, 19, 20, 24, 26);
			if (skipIndices.contains(i)) continue;
			inv.setItem(i, placeholderItem());
		}
		player.openInventory(inv);
	}

}
