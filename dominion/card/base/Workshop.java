package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Atelier (Workshop)
 * 
 * Recevez une carte coûtant jusqu'à 4 Pièces.
 */
public class Workshop extends ActionCard {

	public Workshop() {
		super("Workshop", 3);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		String choix;
		CardList choices=new CardList();
		for (Card c: p.getGame().availableSupplyCards()) {
			if (c.getCost()<=4) {
				choices.add(c);
			}
		}
		choix = p.chooseCard("quelle carte voulez vous prendre", choices, false);
		p.gain(choix);
		
		
	}
}