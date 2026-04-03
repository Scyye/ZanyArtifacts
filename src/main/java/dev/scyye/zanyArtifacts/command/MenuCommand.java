package dev.scyye.zanyArtifacts.command;

import dev.scyye.zanyArtifacts.menu.Menu;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@SuppressWarnings("UnstableApiUsage")
public class MenuCommand implements BasicCommand {
	@Override
	public void execute(@NotNull CommandSourceStack source, String @NotNull [] args) {
		String menuId = args[0];
		assert menuId != null;

		Menu menu = Menu.allMenus.get(menuId);
		assert menu != null;

		Player player = (Player) source.getExecutor();
		assert player != null;

		menu.buildInventory(player);
		player.openInventory(menu.getInventory());
	}

	@Override
	@NotNull
	public Collection<String> suggest(@NotNull CommandSourceStack commandSourceStack, String @NotNull [] args) {
		if (args.length == 0) {
			return Menu.allMenus.keySet();
		}
		return Menu.allMenus.keySet().stream().filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase())).toList();
	}

	@Override
	public boolean canUse(@NotNull CommandSender sender) {
		return sender instanceof Player && sender.hasPermission("zany.menu.command");
	}
}
