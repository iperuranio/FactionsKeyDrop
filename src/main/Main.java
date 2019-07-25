package main;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public World world = null;
	public static ItemStack frammento = createItem(Material.STRING, "§5Frammento Key Medium", Arrays.asList("§r", "§7Utilizza 9 frammenti per", "§7craftare una Key Medium!"), (short) 0);
	public static ItemStack frammento_koth = createItem(Material.BLAZE_POWDER, "§4Frammento Key Koth", Arrays.asList("§r", "§7Utilizza 2 frammenti per", "§7craftare una Key Koth!"), (short) 0);
	public static ItemStack key = createItem(Material.FIREWORK_CHARGE, "§5§oChiave Medium", Arrays.asList("§7Apre una §5Cassa Medium§7!"), (short) 0);
	public static ItemStack key_koth = createItem(Material.BLAZE_ROD, "§4§oChiave Koth", Arrays.asList("§7Apre una §4Cassa Koth§7!"), (short) 0);
	
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("givekey").setExecutor(new GiveKey());
		getCommand("convertkey").setExecutor(new ConvertKey());
	}
//	
//	@EventHandler
//	public void pre(PrepareItemCraftEvent e) {
//		if(e.getInventory() != null) {
//			if(e.getInventory().contains(frammento_koth, 2)) {
//				e.getInventory().remove(frammento_koth);
//				final Player owner = (Player) e.getView().getPlayer();
//				owner.closeInventory();
//				e.getViewers().forEach(p -> p.closeInventory());
//				
//				owner.getInventory().addItem(key_koth);
//				owner.playSound(owner.getLocation(), Sound.NOTE_PLING, 5, 3);
//			} else if(e.getInventory().contains(frammento, 9)) {
//				e.getInventory().remove(frammento);
//				final Player owner = (Player) e.getView().getPlayer();
//				owner.closeInventory();
//				e.getViewers().forEach(p -> p.closeInventory());
//				
//				owner.getInventory().addItem(key);
//			}
//		}
//	}
//	
//	@EventHandler
//	public void craft(CraftItemEvent e) {
//		
//	}
	
	@EventHandler
	public void onSpawn(EntityDeathEvent e)
	{
		LivingEntity en = e.getEntity();
		
		if (en != null && en.getKiller() != null && !(en instanceof Player) && en.getKiller() instanceof Player&& en.getLocation().getWorld().getName().equals("Spawn"))
		{
			Random random = new Random();
			int num = random.nextInt(100);
			
			if(num < 1)
			{
				e.getDrops().add(frammento);
			}
		}
	}
	
	public static ItemStack createItem(Material m, String nome, List<String> lore, short metadata)
	{
		ItemStack is = new ItemStack(m, 1, metadata);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(nome);
		meta.setLore(lore);
		
		is.setItemMeta(meta);
		return is;
	}
}
