//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import dev.scyye.zanyArtifacts.Main;

public class ZanyRecipe {
	boolean shaped;
	ZanyItem item;
	List<ItemStack> recipe = new ArrayList<>(9);

	public ZanyRecipe(boolean shaped, ZanyItem item) {
		this.shaped = shaped;
		this.item = item;
	}

	public ZanyRecipe setIndex(int index, ItemStack item) {
		this.recipe.set(index, item);
		return this;
	}

	public ZanyRecipe setRecipe(ItemStack... itemStacks) {
		List<ItemStack> finalRecipe = new ArrayList<>();

		for(int i = 0; i < 9 && i < itemStacks.length; ++i) {
			finalRecipe.add(itemStacks[i]);

			for (ItemStack itemStack : finalRecipe) {
				if (finalRecipe.size() < 9 && itemStack == null) {
					this.setIndex(i, new ItemStack(Material.AIR));
				}
			}
		}

		int i;
		if (this.shaped) {
			ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, "recipe_" + this.item.getId()), this.item.createItem());
			shapedRecipe.shape("123", "456", "789");

			for(i = 1; i < finalRecipe.size() + 1; ++i) {
				if (itemStacks[i - 1].getType() == Material.AIR)
					continue;
				shapedRecipe.setIngredient((char)(i + 48), new RecipeChoice.ExactChoice(itemStacks[i - 1]));
			}

			Bukkit.addRecipe(shapedRecipe);
		} else {
			ShapelessRecipe shapelessRecipe = new ShapelessRecipe(new NamespacedKey(Main.plugin, "recipe_" + this.item.getId()), this.item.createItem());

			for(i = 0; i < finalRecipe.size(); ++i) {
				if (itemStacks[i].getType() == Material.AIR)
					break;
				shapelessRecipe.addIngredient(new RecipeChoice.ExactChoice(itemStacks[i]));
			}

			Bukkit.addRecipe(shapelessRecipe);
		}

		return this;
	}
}
