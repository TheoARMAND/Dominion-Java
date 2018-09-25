package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Prêteur sur gages (Moneylender)
 * 
 * Écartez une carte Cuivre de votre main.
 * Dans ce cas, +3 Pièces.
 */
public class Moneylender extends ActionCard {

	public Moneylender() {
		super("Moneylender", 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		Boolean test=false;
		for (int i=0;i<p.cardsInHand().size();i++) {
			if (p.cardsInHand().get(i).getName().equals("Copper")) {
				test=true;
			}
		}
		if (test==true) {
			p.removeFromHand("Copper");
			p.incrementMoney(3);
		}
	}
}