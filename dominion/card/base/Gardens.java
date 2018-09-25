package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Jardins (Gardens)
 * 
 * Vaut 1VP pour chaque 10 cartes dans votre deck (arrondi à l'unité inférieure).
 */
public class Gardens extends VictoryCard {

	public Gardens() {
		super("Gardens", 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		// TODO Auto-generated method stub
		
	}
	
	public int victoryValue(Player p) {
		return p.totalCards().size()/10;
	}
	
}