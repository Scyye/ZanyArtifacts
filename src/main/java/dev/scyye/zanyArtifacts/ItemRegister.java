package dev.scyye.zanyArtifacts;

import dev.scyye.zanyArtifacts.item.ZanyItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRegister {
	private static List<ZanyAddon> addons = new ArrayList<>();
	private static Map<String, ZanyItem> toRegister = new HashMap<>();
	private static Map<String, ZanyItem> registered = new HashMap<>();

	public static Map<String, ZanyItem> getItems() {
		return registered;
	}
	public static void register(ZanyItem item) {
		toRegister.put(item.getId(), item);
	}

	public static void startRegistration() {
		for (ZanyItem item : toRegister.values()) {
			registered.put(item.getId(), item);
			Main.plugin.getLogger().info("Registered item: " + item.getId());
		}
		toRegister.clear();
	}

	protected static void registerAddon(ZanyAddon addon) {
		addons.add(addon);
	}
}
