package dev.scyye.zanyArtifacts.command;

import dev.scyye.zanyArtifacts.menu.standalone.CraftCreator;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class CreateRecipe implements BasicCommand {
	@Override
	public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public boolean canUse(CommandSender sender) {
		return sender.isOp();
	}

	@Override
	public @Nullable String permission() {
		return "zany.create.recipe.command";
	}

	@Override
	public void execute(CommandSourceStack source, String[] args) {
		if (!(source.getExecutor() instanceof Player player)) return;
		CraftCreator.open(player);
	}
}
