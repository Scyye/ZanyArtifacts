package dev.scyye.zanyArtifacts;

import dev.scyye.zanyArtifacts.command.GiveCommand;
import dev.scyye.zanyArtifacts.command.MenuCommand;
import dev.scyye.zanyArtifacts.enchant.impl.*;
import dev.scyye.zanyArtifacts.item.*;
import dev.scyye.zanyArtifacts.item.impl.*;
import dev.scyye.zanyArtifacts.menu.Menu;
import dev.scyye.zanyArtifacts.menu.MenuListener;
import dev.scyye.zanyArtifacts.menu.impl.GiveMenu;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.keys.tags.EnchantmentTagKeys;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import io.papermc.paper.registry.tag.TagKey;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import dev.scyye.zanyArtifacts.enchant.EnchantmentListener;
import dev.scyye.zanyArtifacts.enchant.ZanyEnchant;

import static dev.scyye.zanyArtifacts.enchant.EnchantmentListener.getZanyEnchants;

@SuppressWarnings("UnstableApiUsage")
public class Main extends JavaPlugin {
	public static Main plugin;

	public void onEnable() {
		plugin = this;


		// Register Listeners
		Bukkit.getPluginManager().registerEvents(new ItemListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new EnchantmentListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new MenuListener(), plugin);

		// Register commands
		this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
				event -> {
					event.registrar().register("zanygive", new GiveCommand());
					event.registrar().register("menu", new MenuCommand());
				}
		);

		this.registerItems();

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.setInvulnerable(false);
		}

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			for (Player p : Bukkit.getOnlinePlayers()) {
				for (ItemStack item : p.getInventory()) {
					ZanyEnchant[] enchants = getZanyEnchants(item);
					for (ZanyEnchant enchant : enchants) {
						if (enchant != null) {
							enchant.constantEffect(p, item);
						}
					}
				}
			}
		}, 1L, 1L);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, CooldownHandler.Cooldown::handleCooldowns, 1L, 1L);
	}

	public void onDisable() {
	}

	public void registerItems() {
		ZanyItem testItem = new TestItem("test_item", new ItemStack(Material.IRON_AXE), 1, "Super Cool Test Item", true, new EnchantmentData[]{new EnchantmentData(Enchantment.SHARPNESS, 2)}, new AttributeData[0], new ItemFlag[0], new String[]{"&6This is super cool lore", "&5And here's another line!"}, new ZanyItem.AbilityMeta[0]);
		(new ZanyRecipe(true, testItem)).setRecipe(new ItemStack(Material.STRING), new ItemStack(Material.STRING), new ItemStack(Material.STRING), new ItemStack(Material.AIR), new ItemStack(Material.STICK), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.STICK), new ItemStack(Material.AIR));
		new ExplosiveStick("explosive_stick", new ItemStack(Material.TORCH), 1, "&cExplosive Stick", true, new EnchantmentData[0], new AttributeData[0], new ItemFlag[0], new String[]{"&4Hitting a Block Will", "&4Cause a Mighty Explosion"}, new ZanyItem.AbilityMeta[0]);
		new TechnobladeSword("technoblade_sword", new ItemStack(Material.GOLDEN_SWORD), 1, "&bTechnoblade &6Sword", true, new EnchantmentData[0], new AttributeData[0], new ItemFlag[0], new String[]{"&7Uses the power of &6THE BLADE", "&7to summon pigmen when you", "&7left click!", "&7Right click to remove them", "", "&6R.I.P Your Majesty."}, new ZanyItem.AbilityMeta[]{
				new ZanyItem.AbilityMeta(
						"summon_pigmen",
						"Summon Pigmen",
						ZanyItem.AbilityMeta.AbilityType.LEFT_CLICK,
						false,
						false,
						0
				),
				new ZanyItem.AbilityMeta(
						"remove_pigmen",
						"Remove Pigmen",
						ZanyItem.AbilityMeta.AbilityType.RIGHT_CLICK,
						false,
						false,
						0
				)
		});
		new Gun("gun", new ItemStack(Material.BOW), 1, "&7&lTHE RETURN OF THE GUN", true, new EnchantmentData[0], new AttributeData[0], new ItemFlag[0], new String[]{"&7Left click to reload", "&2Max ammo: 6"}, new ZanyItem.AbilityMeta[0]);
		new TeleportBow("teleport_bow", new ItemStack(Material.BOW), 1, "&dTeleport Bow", true, new EnchantmentData[0], new AttributeData[0], new ItemFlag[0], new String[]{"&7Teleport to where the arrow lands!"}, new ZanyItem.AbilityMeta[0]);
		new KamikazeStick("kamikaze_stick", new ItemStack(Material.STICK), 1, "&4Kamikaze Stick", true, new EnchantmentData[0], new AttributeData[0], new ItemFlag[0], new String[]{"&4click a block to kamikaze yo ass"}, new ZanyItem.AbilityMeta[0]);
		new FlashBang("flash_bang", new ItemStack(Material.SOUL_LANTERN), 6, "&fFlash Bang", true, new EnchantmentData[0], new AttributeData[0], new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES}, new String[]{"&2Click anywhere to flashbang people in a 5 block radius"}, new ZanyItem.AbilityMeta[0]);

		for (ZanyItem item : ZanyItem.allItems.values()) {
			item.updateLore();
		}
	}

	public static void registerEnchants() {
		ZanyEnchant.createBasicEnchant(
				TestEnchant.class,
				Component.text("Test Enchant"),
				3,
				new EquipmentSlotGroup[]{EquipmentSlotGroup.ANY},
				new TagKey[]{ItemTypeTagKeys.SWORDS},
				null
		);
		ZanyEnchant.createBasicEnchant(
				ExplosiveTouchEnchant.class,
				Component.text("Explosive Touch"),
				7,
				new EquipmentSlotGroup[]{EquipmentSlotGroup.ANY},
				new TagKey[]{ItemTypeTagKeys.SWORDS, ItemTypeTagKeys.AXES, ItemTypeTagKeys.PICKAXES, ItemTypeTagKeys.HOES, ItemTypeTagKeys.SHOVELS, ItemTypeTagKeys.ENCHANTABLE_SWORD, ItemTypeTagKeys.ENCHANTABLE_WEAPON, ItemTypeTagKeys.ENCHANTABLE_MINING},
				new TagKey[]{EnchantmentTagKeys.ON_MOB_SPAWN_EQUIPMENT}
		);

		ZanyEnchant.createBasicEnchant(
				MagneticEnchant.class,
				Component.text("Magnetic"),
				5,
				new EquipmentSlotGroup[]{EquipmentSlotGroup.FEET},
				new TagKey[]{ItemTypeTagKeys.FOOT_ARMOR, ItemTypeTagKeys.ENCHANTABLE_FOOT_ARMOR},
				new TagKey[]{EnchantmentTagKeys.TREASURE}
		);

		ZanyEnchant.createBasicEnchant(
				FasterFallingEnchant.class,
				Component.text("Faster Falling"),
				5,
				new EquipmentSlotGroup[]{EquipmentSlotGroup.FEET},
				new TagKey[]{ItemTypeTagKeys.FOOT_ARMOR, ItemTypeTagKeys.ENCHANTABLE_FOOT_ARMOR},
				null
		);

		ZanyEnchant.createBasicEnchant(
				GroundSlamEnchant.class,
				Component.text("Ground Slam"),
				5,
				new EquipmentSlotGroup[]{EquipmentSlotGroup.FEET},
				new TagKey[]{ItemTypeTagKeys.FOOT_ARMOR, ItemTypeTagKeys.ENCHANTABLE_FOOT_ARMOR},
				new TagKey[]{EnchantmentTagKeys.TRADES_PLAINS_COMMON}
		);

		ZanyEnchant.createBasicEnchant(
				ReflectionEnchant.class,
				Component.text("Reflection"),
				3,
				new EquipmentSlotGroup[]{EquipmentSlotGroup.ARMOR},
				new TagKey[]{ItemTypeTagKeys.ENCHANTABLE_ARMOR},
				null
		);

		ZanyEnchant.createBasicEnchant(
				MurderEnchant.class,
				Component.text("Murder"),
				1,
				new EquipmentSlotGroup[]{ EquipmentSlotGroup.ARMOR },
				new TagKey[]{ ItemTypeTagKeys.ENCHANTABLE_ARMOR},
				new TagKey[]{ EnchantmentTagKeys.TREASURE}
		);
	}

	public static void registerMenus() {
		Menu giveMenu = new GiveMenu(
				InventoryType.CHEST,
				"Item GUI",
				true,
				null
		);

		Menu.allMenus.put(giveMenu.getId(), giveMenu);
	}

	public static NamespacedKey getKey(String key) {
		return new NamespacedKey(plugin, key);
	}
}
