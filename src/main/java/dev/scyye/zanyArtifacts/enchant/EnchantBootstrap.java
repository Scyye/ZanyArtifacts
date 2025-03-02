package dev.scyye.zanyArtifacts.enchant;

import dev.scyye.zanyArtifacts.Main;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public class EnchantBootstrap implements PluginBootstrap {
	@Override
	public void bootstrap(@NotNull BootstrapContext context) {
		Main.registerEnchants();
		Collection<ZanyEnchant> zanyEnchants = Main.enchantIds.values();

		context.getLifecycleManager().registerEventHandler(LifecycleEvents.TAGS.preFlatten(RegistryKey.ITEM).newHandler((event) -> {
			for (ZanyEnchant enchant : zanyEnchants) {
				event.registrar().addToTag(
						ItemTypeTagKeys.create(enchant.getTagForSupportedItems().key()),
						enchant.getSupportedItems()
				);
				event.registrar().addToTag(
						ItemTypeTagKeys.create(enchant.getTagForPrimaryItems().key()),
						enchant.getPrimaryItems()
				);
			}
		}));

		context.getLifecycleManager().registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler(event -> {
			for (ZanyEnchant enchant : zanyEnchants) {
				event.registry().register(TypedKey.create(RegistryKey.ENCHANTMENT, enchant.getKey()), enchantment -> {
					enchantment.description(enchant.getDisplay());
					enchantment.anvilCost(enchant.getAnvilCost());
					enchantment.maxLevel(enchant.getMaxLevel());
					enchantment.weight(enchant.getWeight());
					enchantment.minimumCost(enchant.getMinimumCost());
					enchantment.maximumCost(enchant.getMaximumCost());
					enchantment.activeSlots(enchant.getActiveSlots());
					enchantment.supportedItems(event.getOrCreateTag(enchant.getTagForSupportedItems()));
					enchantment.primaryItems(event.getOrCreateTag(enchant.getTagForPrimaryItems()));
				});
				System.out.println("Registered enchantment: " + enchant.getKey());
			}
		}));

		context.getLifecycleManager().registerEventHandler(LifecycleEvents.TAGS.preFlatten(RegistryKey.ENCHANTMENT).newHandler((event) -> {
			for (ZanyEnchant enchant : zanyEnchants) {
				enchant.getEnchantTagKeys().forEach(enchantmentTagKey -> {
					event.registrar().addToTag(enchantmentTagKey, Set.of(enchant.getTagEntry()));
				});
			}
		}));
	}
}
