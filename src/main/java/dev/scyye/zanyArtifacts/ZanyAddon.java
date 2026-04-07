package dev.scyye.zanyArtifacts;

import dev.scyye.zanyArtifacts.item.ZanyItem;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ZanyAddon extends JavaPlugin {
	@Override
	public void onLoad() {
		ItemRegister.registerAddon(this);
		this.registerItems();
	}

	@Override
	public final void onEnable() {
		Main.plugin.getLogger().info("Enabling addon: " + this.getName());
		this.onAddonEnable();
	}

	@Override
	public final void onDisable() {
		Main.plugin.getLogger().info("Disabling addon: " + this.getName());
		this.onAddonDisable();
	}

	public abstract void registerItems();

	public abstract void onAddonEnable();

	public abstract void onAddonDisable();
}
