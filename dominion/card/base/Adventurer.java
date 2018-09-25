package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Aventurier (Adventurer)
 * 
 * Dévoilez des cartes de votre deck jusqu'à ce que 2 cartes Trésor soient dévoilées. Ajoutez ces cartes Trésor à votre main et défaussez les autres cartes dévoilées.
 */
public class Adventurer extends ActionCard {

	public Adventurer() {
		super("Adventurer", 6);
	}

	@Override
	public void play(Player p) {
		int nbrTreasureDrew=0;
		while (nbrTreasureDrew<2) {
			Card cardDrew=p.drawCard();
			if(cardDrew.isATypeOf(CardType.Treasure)) {
				p.addToHand(cardDrew);
				nbrTreasureDrew++;
			}
			else {
				p.addToDiscard(cardDrew);
			}
		}
		
	}
}