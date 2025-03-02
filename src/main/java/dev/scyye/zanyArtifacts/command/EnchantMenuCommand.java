package dev.scyye.zanyArtifacts.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.scyye.zanyArtifacts.menu.EnchantMenu;

public class EnchantMenuCommand implements CommandExecutor {
	public EnchantMenuCommand() {
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		/*
		if (sender instanceof Player player) {
			(new EnchantMenu()).openInv(player);
			return true;
		} else {
			return false;
		}*/
		return false;
	}
}
