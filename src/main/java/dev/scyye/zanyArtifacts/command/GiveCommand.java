//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.command;

import java.util.Collection;
import java.util.stream.Collectors;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import dev.scyye.zanyArtifacts.Utils;
import dev.scyye.zanyArtifacts.item.ZanyItem;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class GiveCommand implements BasicCommand {
	@Override
	public void execute(CommandSourceStack source, String @NotNull [] args) {
		if (!(source.getSender() instanceof Player player)) {
			return;
		}
		if (!ZanyItem.allItems.containsKey(args[0])) {
			player.sendMessage("Item '" + args[0] + "' doesn't exist, are you sure you spelt it correctly?");
		}

		Utils.givePlayerItemSafely(player, ZanyItem.allItems.get(args[0]).createItem(false));
	}

	@Override
	@NotNull
	public Collection<String> suggest(@NotNull CommandSourceStack source, String[] args) {
		if (args.length==0) {
			return ZanyItem.allItems.keySet();
		}
		return ZanyItem.allItems.keySet().stream()
				.filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toSet());
	}

	@Override
	public @Nullable String permission() {
		return "zany.give.command";
	}
}
