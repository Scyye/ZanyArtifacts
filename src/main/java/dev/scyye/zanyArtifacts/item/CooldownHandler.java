package dev.scyye.zanyArtifacts.item;

import dev.scyye.zanyArtifacts.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings({"unused", "deprecation"})
public class CooldownHandler {
	public static double trim(double untrimmeded, int decimal) {
		DecimalFormat twoDec = new DecimalFormat("#.#" + "#".repeat(Math.max(0, decimal - 1)));
		return Double.parseDouble(twoDec.format(untrimmeded));
	}

	public enum TimeUnit {
		BEST,
		DAYS,
		HOURS,
		MINUTES,
		SECONDS,
	}

	public static double convert(long time, TimeUnit unit, int decPoint) {
		if(unit == TimeUnit.BEST) {
			if(time < 60000L) unit = TimeUnit.SECONDS;
			else if(time < 3600000L) unit = TimeUnit.MINUTES;
			else if(time < 86400000L) unit = TimeUnit.HOURS;
			else unit = TimeUnit.DAYS;
		}
		if(unit == TimeUnit.SECONDS) return trim(time / 1000.0D, decPoint);
		if(unit == TimeUnit.MINUTES) return trim(time / 60000.0D, decPoint);
		if(unit == TimeUnit.HOURS) return trim(time / 3600000.0D, decPoint);
		if(unit == TimeUnit.DAYS) return trim(time / 86400000.0D, decPoint);
		return trim(time, decPoint);
	}

	public static class AbilityCooldown {
		public HashMap<String, AbilityCooldown> cooldownMap = new HashMap<>();

		public String ability = "";
		public UUID player;
		public long millis;
		public long systime;

		public AbilityCooldown(String ability, UUID player, long millis, long systime) {
			this.ability = ability;
			this.player = player;
			this.millis = millis;
			this.systime = systime;
		}

		public AbilityCooldown(UUID player) {
			this.player = player;
		}
	}

	public static class Cooldown {
		public static HashMap<UUID, AbilityCooldown> cooldownPlayers = new HashMap<>();

		public static void add(UUID player, String ability, long millis) {
			if(!cooldownPlayers.containsKey(player)) cooldownPlayers.put(player, new AbilityCooldown(player));
			if(isCooling(player, ability)) return;
			cooldownPlayers.get(player).cooldownMap.put(ability, new AbilityCooldown(ability, player, millis, System.currentTimeMillis()));
		}

		public static boolean isCooling(UUID player, String ability) {
			if(!cooldownPlayers.containsKey(player)) return false;
			return cooldownPlayers.get(player).cooldownMap.containsKey(ability);
		}

		public static double getRemaining(UUID player, String ability) {
			if(!cooldownPlayers.containsKey(player)) return 0.0;
			if(!cooldownPlayers.get(player).cooldownMap.containsKey(ability)) return 0.0;
			return ((cooldownPlayers.get(player).cooldownMap.get(ability).millis) + cooldownPlayers.get(player).cooldownMap.get(ability).systime) - System.currentTimeMillis();
		}

		public static void coolDurMessage(Player player, String ability) {
			if(player == null) {
				return;
			}
			if(!isCooling(player.getUniqueId(), ability)) {
				return;
			}
			player.sendMessage(ChatColor.GRAY + ability + " Cooldown " + ChatColor.AQUA + getRemaining(player.getUniqueId(), ability) + " Seconds");
		}

		public static void removeCooldown(UUID player, String ability) {
			if(!cooldownPlayers.containsKey(player)) {
				return;
			}
			if(!cooldownPlayers.get(player).cooldownMap.containsKey(ability)) {
				return;
			}
			cooldownPlayers.get(player).cooldownMap.remove(ability);
			Player cPlayer = Bukkit.getPlayer(player);
			assert cPlayer != null;
			cPlayer.sendMessage(ChatColor.GRAY + "You can now use " + ChatColor.AQUA + ability);
		}

		public static void handleCooldowns() {
			if(cooldownPlayers.isEmpty()) {
				return;
			}
			for (UUID key : cooldownPlayers.keySet()) {
				for (String name : cooldownPlayers.get(key).cooldownMap.keySet()) {
					Player player = Bukkit.getPlayer(key);
					if (player == null)
						continue;
					double remainingSeconds = getRemaining(key, name)/1000;
					player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent
							(ChatColor.GRAY + name + " Cooldown " + (remainingSeconds*1000) +
									ChatColor.RED + Utils.round(remainingSeconds, 2) + "ms"));
					if (getRemaining(key, name) <= 0.0) {
						removeCooldown(key, name);
						player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent());
					}
				}
			}
		}
	}
}
