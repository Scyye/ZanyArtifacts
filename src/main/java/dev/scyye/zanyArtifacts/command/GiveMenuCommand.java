//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.scyye.zanyArtifacts.menu.GiveMenu;

@Deprecated(forRemoval = true)
public class GiveMenuCommand implements CommandExecutor {
	public GiveMenuCommand() {
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player player) {
			(new GiveMenu()).openInv(player);
			return true;
		} else {
			return false;
		}
	}
}
