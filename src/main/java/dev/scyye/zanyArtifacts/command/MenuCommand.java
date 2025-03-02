package dev.scyye.zanyArtifacts.command;

import dev.scyye.zanyArtifacts.menu.Menu;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

@SuppressWarnings("UnstableApiUsage")
public class MenuCommand implements BasicCommand {
	@Override
	public void execute(@NotNull CommandSourceStack source, String @NotNull [] args) {
		String menuId = args[0];
		if (menuId == null)
			return;

		Menu menu = Menu.allMenus.get(menuId);
		if (menu == null)
			return;

		Player player = (Player) source.getExecutor();

		menu.buildInventory(player);

		player.openInventory(menu.getInventory());
	}

	@Override
	@NotNull
	public Collection<String> suggest(@NotNull CommandSourceStack commandSourceStack, String @NotNull [] args) {
		return BasicCommand.super.suggest(commandSourceStack, args);
	}

	@Override
	public boolean canUse(@NotNull CommandSender sender) {
		return sender instanceof Player && sender.hasPermission("zany.menu.command");
	}
}
