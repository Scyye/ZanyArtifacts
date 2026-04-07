package dev.scyye.zanyArtifacts.enchant;

import dev.scyye.zanyArtifacts.Main;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.event.RegistryEvents;
import io.papermc.paper.registry.keys.EnchantmentKeys;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;

@SuppressWarnings({"UnstableApiUsage", "unused"})
public class EnchantBootstrap implements PluginBootstrap {
	@Override
	public void bootstrap(@NotNull BootstrapContext context) {
		Main.registerEnchants();

		context.getLifecycleManager().registerEventHandler(LifecycleEvents.TAGS.preFlatten(RegistryKey.ITEM).newHandler((event) -> {
			for (ZanyEnchant enchant : ZanyEnchant.allEnchants.values()) {
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

		context.getLifecycleManager().registerEventHandler(
				RegistryEvents.ENCHANTMENT.compose().newHandler(event -> {

					for (ZanyEnchant enchant : ZanyEnchant.allEnchants.values()) {

						event.registry().register(
								EnchantmentKeys.create(enchant.getKey()),
								enchantment -> {
									enchantment.description(enchant.getDisplay());
									enchantment.anvilCost(enchant.getAnvilCost());
									enchantment.maxLevel(enchant.getMaxLevel());
									enchantment.weight(enchant.getWeight());
									enchantment.minimumCost(enchant.getMinimumCost());
									enchantment.maximumCost(enchant.getMaximumCost());
									enchantment.activeSlots(enchant.getActiveSlots());
									enchantment.supportedItems(event.getOrCreateTag(enchant.getTagForSupportedItems()));
									enchantment.primaryItems(event.getOrCreateTag(enchant.getTagForPrimaryItems()));
								}
						);

						System.out.println("Registered enchantment: " + enchant.getKey());
					}
				})
		);

		context.getLifecycleManager().registerEventHandler(LifecycleEvents.TAGS.preFlatten(RegistryKey.ENCHANTMENT).newHandler((event) -> {
			for (ZanyEnchant enchant : ZanyEnchant.allEnchants.values()) {
				enchant.getEnchantTagKeys().forEach(enchantmentTagKey ->
						event.registrar().addToTag(enchantmentTagKey, Set.of(enchant.getTagEntry())));
			}
		}));
	}
}
