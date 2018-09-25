package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Rénovation (Remodel)
 * 
 * Écartez une carte de votre main.
 * Recevez une carte coûtant jusqu'à 2 Pièces de plus que la carte écartée.
 */
public class Remodel extends ActionCard {

	public Remodel() {
		super("Remodel", 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		String choix=p.chooseCard("choisissez la carte à écarter", p.cardsInHand(),true);
		Card carteAEcarter=p.removeFromHand(choix);
		CardList carteAChoisir=new CardList();
		for (int i=0;i<p.getGame().availableSupplyCards().size();i++) {
			if(p.getGame().availableSupplyCards().get(i).getCost()<=carteAEcarter.getCost()+2) {
				carteAChoisir.add(p.getGame().availableSupplyCards().get(i));
			}
		}
		choix=p.chooseCard("choisissez la carte à gagner ou appuyez sur entrée", carteAChoisir, true);
		p.gain(choix);
		
	}
}