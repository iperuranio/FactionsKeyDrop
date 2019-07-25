package main;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ConvertKey implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			Inventory inv = p.getInventory();
			
			int key = 0;
			int koth = 0;
			
			for(ItemStack is : inv.getContents()) {
				if(is != null) {
					if(is.isSimilar(Main.frammento)) {
						key += is.getAmount();
					} else if(is.isSimilar(Main.frammento_koth)) {
						koth += is.getAmount();
					}
				}
			}
			
			if(key == 0 && koth == 0) {
				p.sendMessage("§2§lFazioni §8§l» §7Non hai nessun frammento da convertire.");
				return true;
			}
			
			if(key < 9 && koth < 2) {
				p.sendMessage("§2§lFazioni §8§l» §7Hai troppi pochi frammenti per convertirli in Key.");
				return true;
			}
			
			if(key != 0 && koth != 0 && key % 9 == 0 && koth % 2 == 0) {
				remove(Main.frammento, inv, key);
				remove(Main.frammento_koth, inv, koth);
				
				for(int i = 0; i < key/9; i++) {
					addItem(inv, Main.key, p.getLocation());
				}
				
				for(int i = 0; i < koth/2; i++) {
					addItem(inv, Main.key_koth, p.getLocation());
				}
				p.sendMessage("§2§lFazioni §8§l» §7Sono stati convertiti §a"+key+" §7frammenti Medium in §a"+key/9+" §7key!");
				p.sendMessage("§2§lFazioni §8§l» §7Sono stati convertiti §a"+koth+" §7frammenti Koth in §a"+koth/2+" §7key!");
				p.updateInventory();
				return true;
			}
			
			if(key != 0 && key % 9 == 0) {
				
				remove(Main.frammento, inv, key);
				
				for(int i = 0; i < key/9; i++) {
					addItem(inv, Main.key, p.getLocation());
				}
				p.sendMessage("§2§lFazioni §8§l» §7Sono stati convertiti §a"+key+" §7frammenti Medium in §a"+key/9+" §7key!");
				p.updateInventory();
				return true;
			} else if(koth != 0 && koth % 2 == 0) {
				remove(Main.frammento_koth, inv, koth);
				for(int i = 0; i < koth/2; i++) {
					addItem(inv, Main.key_koth, p.getLocation());
				}
				p.sendMessage("§2§lFazioni §8§l» §7Sono stati convertiti §a"+koth+" §7frammenti Koth in §a"+koth/2+" §7key!");
				p.updateInventory();
				return true;
			}
			
			key = (int) (key / 9);
			koth = (int) (koth / 2);
			
			int removingKey = 9*key;
			int removingKoth = 2*koth;
			
			remove(Main.frammento, inv, removingKey);
			remove(Main.frammento_koth, inv, removingKoth);
			
			for(int i = 0; i < key; i++) {
				addItem(inv, Main.key, p.getLocation());
			}
			
			for(int i = 0; i < koth; i++) {
				addItem(inv, Main.key_koth, p.getLocation());
			}
			
			if(key > 1) {
				p.sendMessage("§2§lFazioni §8§l» §7Sono stati convertiti §a"+key*9+" §7frammenti Medium in §a"+key+" §7key!");
			}
			
			if(koth > 1) {
				p.sendMessage("§2§lFazioni §8§l» §7Sono stati convertiti §a"+koth*2+" §7frammenti Koth in §a"+koth+" §7key!");
			}
			
			p.updateInventory();
		}
		return true;
	}
	
	public void remove(ItemStack toCheck, Inventory inv, int removingKey) {
		int i = 0;
		
		for(ItemStack is : inv.getContents()) {
			if(is != null) {
				if(is.isSimilar(toCheck)) {
					if(removingKey == 0) continue;
					
					if(is.getAmount() == 1) {
						inv.setItem(i, new ItemStack(Material.AIR));
						removingKey--;
					} else {
						if(is.getAmount() > removingKey) {
							int dummy = is.getAmount();
							is.setAmount(dummy - removingKey);
							removingKey -= dummy - is.getAmount();
						} else if(is.getAmount() == removingKey) {
							inv.setItem(i, new ItemStack(Material.AIR));
							removingKey = 0;
						} else if(is.getAmount() < removingKey) {
							inv.setItem(i, new ItemStack(Material.AIR));
							removingKey -= is.getAmount();
						}
					}
				}
			}
			
			i++;
		}
	}
	
	public void addItem(Inventory inv, ItemStack is, Location l) {
		HashMap<Integer, ItemStack> items = inv.addItem(is);
		
		if(items.isEmpty())
			return;
		
		final World w = l.getWorld();
		
		for(Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
			w.dropItemNaturally(l, entry.getValue());
		}
	}
}
