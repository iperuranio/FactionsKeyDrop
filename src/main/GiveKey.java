package main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveKey implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender.isOp())
		{
			Player p = (Player) sender;
			
			p.getInventory().addItem(Main.frammento_koth);
		}
		else
		{
			sender.sendMessage("Comando sconosciuto.");
		}
		return true;
	}

}
