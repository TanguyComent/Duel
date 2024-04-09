package fr.kitsoko.duelplug;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class main extends JavaPlugin {
	
	private Map<Player, Player> players = new HashMap<>();
	
	@Override
	public void onEnable() {
		System.out.println("Mirylis Duel !");
		getCommand("duel").setExecutor(this);
	}
	
	@Override
	public void onDisable() {
			System.out.println("le serv s'�teind");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(label.equalsIgnoreCase("duel") && sender instanceof Player) {
			Player player = (Player)sender;
			// le joueur fait /duel
			if(args.length == 0) {
				player.sendMessage("/duel <joueur> pour lancer un duel");
				player.sendMessage("/duel accept pour accepter un duel contre un autre joueur");
				player.sendMessage("/duel deny pour refuser une demande de duel");
				return true;
			}
			
			//command duel principale (permet d'envoyer une requ�te de duel a un autre joueur)
			if(args.length == 1) {
				String targetName = args[0];
				
				
				// command /duel accept, commande qui lance le duel
				if(args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("a")) {
					// /duel accept
					if(players.containsKey(player)) {
						
						Player envoyer = players.get(player);
						Random random = new Random();
						
						
						
						int mapsalea = random.nextInt(2);
						
						switch(mapsalea) {
						case 0:
							player.teleport(new Location(Bukkit.getWorld("world"), -(21594040), 54, 4211085));
							envoyer.teleport(new Location(Bukkit.getWorld("world"), -(21593994), 54, 4211049));
							break;
						case 1:
							player.teleport(new Location(Bukkit.getWorld("world"), -(21596615), 75, 4210207));
							envoyer.teleport(new Location(Bukkit.getWorld("world"), -(21596581), 75, 4210241));
							break;
						default:
							break;
								
						}
						
						player.sendMessage("fait /spawn si tu gagne le duel !");
						envoyer.sendMessage("fait /spawn si tu gagne ce duel !");
						players.remove(player);
						return true;
						
					}
					
					
				}else if(args[0].equalsIgnoreCase("deny") || args[0].equalsIgnoreCase("d")) {
					// /duel deny
					
					if(players.containsKey(player)) {
						player.sendMessage("Vous avez refuser la demande de duel");
						players.remove(player);
						
					}
					
					
				
				}else if(Bukkit.getPlayer(targetName) != null) {
					
					Player target = Bukkit.getPlayer(targetName);
					
					if(players.containsKey(target)) {
						
						player.sendMessage("ce joueur est d�ja en duel");
						return true;
					}
					
					players.put(target, player);
					player.sendMessage("Une requ�te de duel a bien �t� envoyer a : " + targetName);
					target.sendMessage(player.getName() + " vous a envoy� une demande de duel");
					target.sendMessage("Faites /duel accept pour lancer le duel");
					target.sendMessage("Faites /duel deny afin de refuser le duel");
					
				}else {
					player.sendMessage("Joueur d�connecter ou introuvable");
					return true;
				}
					
				}
			return true;
			}
		
		
		return false;
	}

}
