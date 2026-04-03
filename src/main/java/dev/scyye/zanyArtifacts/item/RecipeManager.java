package dev.scyye.zanyArtifacts.item;

import dev.scyye.zanyArtifacts.Main;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.*;

public class RecipeManager implements Listener {
	@Getter
	private static List<Recipe> recipes = new ArrayList<>();
	public record Recipe(String id, String[] ingredients, String result, boolean isShaped, int resultAmount) {
	}

	public static void addRecipe(Recipe recipe) {
		recipes.add(recipe);

		registerRecipe(recipe);
		Map<String, Object> recipeMap = new HashMap<>();
		recipeMap.put("id", recipe.id);
		recipeMap.put("ingredients", Arrays.asList(recipe.ingredients));
		recipeMap.put("result", recipe.result);
		recipeMap.put("isShaped", recipe.isShaped);
		recipeMap.put("resultAmount", recipe.resultAmount);
		Main.plugin.getConfig().set("recipes." + recipe.id, recipeMap);
		Main.plugin.saveConfig();
	}

	public static void removeRecipe(String id) {
		recipes.removeIf(recipe -> recipe.id.equals(id));
		Bukkit.removeRecipe(new NamespacedKey(Main.plugin, id));
		Main.plugin.getConfig().set("recipes." + id, null);
		Main.plugin.saveConfig();
	}

	static ItemStack createItem(String id, int amount) {
		System.out.println("Creating item with ID: " + id + " and amount: " + amount);
		ItemStack item = ZanyItem.allItems.get(id);
		System.out.println(Arrays.toString(ZanyItem.allItems.keySet().toArray()));
		if (item==null) {
			System.out.println("Item not found in ZanyItem.allItems, creating new ItemStack.");
			item = new ItemStack(Material.valueOf(id.toUpperCase()), amount);
		}
		item.setAmount(amount);
		return item;
	}

	private static void loadRecipes() {
		var config = Main.plugin.getConfig();
		var recipesSection = config.getList("recipes");
		if (recipesSection == null || recipesSection.isEmpty()) {
			Main.plugin.getLogger().warning("No recipes found in config!");
			recipes = new ArrayList<>();
			return;
		}
		List<Recipe> recipeList = new ArrayList<>();

		for (Object recipe : recipesSection) {
			Map<String, Object> recipeMap = (Map<String, Object>) recipe;
			for (var entry : recipeMap.keySet()) {
				System.out.println("Processing recipe entry: " + entry);
				System.out.println(config.get("recipes."+entry));
				System.out.println(config.getString("recipes." + entry + ".id"));
				String id = config.getString("recipes."+entry+".id");
				List<String> ingredients = config.getStringList("recipes."+entry+".ingredients");
				String result = config.getString("recipes."+entry+".result");
				boolean isShaped = config.getBoolean("recipes."+entry+".shaped", false);
				int resultAmount = config.getInt("recipes."+entry+".resultAmount", 1);

				recipeList.add(
						new Recipe(id, ingredients.toArray(new String[0]), result, isShaped, resultAmount)
				);
			}
		}

		recipes = recipeList;
	}


	public static void init() {
		loadRecipes();

		for (var recipe : recipes) {
			registerRecipe(recipe);
		}
	}

	private static void registerRecipe(Recipe recipe) {
		System.out.println(Arrays.toString(recipe.ingredients));
		List<ItemStack> recipeItems = new ArrayList<>();
		List<ItemStack> finalRecipe = new ArrayList<>();

		for(int i = 0; i < 9 && i < recipe.ingredients().length; ++i) {
			finalRecipe.add(createItem(recipe.ingredients[i], 1));
		}

		for (ItemStack itemStack : finalRecipe) {
			recipeItems.add(Objects.requireNonNullElseGet(itemStack, () -> new ItemStack(Material.AIR)));
		}
		if (recipe.isShaped()) {
			ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(Main.plugin, recipe.id), createItem(recipe.result, recipe.resultAmount));
			shapedRecipe.shape("123", "456", "789");
			System.out.println(Arrays.toString(recipeItems.toArray()));
			for(var i = 0; i < recipeItems.size(); ++i) {
				if (recipeItems.get(i).getType() == Material.AIR)
					continue;
				System.out.println("Setting ingredient " + i + " to " + recipeItems.get(i).getType());
				shapedRecipe.setIngredient(Integer.toString(i+1).toCharArray()[0], new RecipeChoice.ExactChoice(recipeItems.get(i)));
			}
			System.out.println(shapedRecipe.getIngredientMap());
			Bukkit.addRecipe(shapedRecipe);
		} else {
			var shapelessRecipe = new ShapelessRecipe(new NamespacedKey(Main.plugin, recipe.id), createItem(recipe.result(), recipe.resultAmount()));
			for (String ingredient : recipe.ingredients()) {
				if (!ingredient.equals("AIR")) {
					shapelessRecipe.addIngredient(createItem(ingredient, 1));
				}
			}
			Bukkit.addRecipe(shapelessRecipe);
		}
	}


}
