package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Festin (Feast)
 * 
 * Écartez cette carte.
 * Recevez une carte coûtant jusqu'à 5 Pièces.
 */
public class Feast extends ActionCard {

	public Feast() {
		super("Feast", 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		p.removeFromInPlay("Feast");
		String choix;
		CardList choices=new CardList();
		for (Card c: p.getGame().availableSupplyCards()) {
			if (c.getCost()<=5) {
				choices.add(c);
			}
		}
		choix = p.chooseCard("quelle carte voulez vous prendre", choices, false);
		p.gain(choix);
	}
}