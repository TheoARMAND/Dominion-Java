package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Bibliothèque (Library)
 * 
 * Piochez jusqu'à ce que vous ayez 7 cartes en main. Chaque carte Action piochée peut être mise de côté. Défaussez les cartes mises de côté lorsque vous avez terminé de piocher.
 */
public class Library extends ActionCard {

	public Library() {
		super("Library", 5);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		CardList carteADefausser= new CardList();
		while(p.cardsInHand().size()<=7) {
			Card cartePiochee=p.drawCard();
			p.addToHand(cartePiochee);
			if (cartePiochee.isATypeOf(CardType.Action)) {
				List<String> choices = Arrays.asList("y", "n");
				String choix=p.choose("voulez vous defausser cette carte? (y/n)", choices, false);
				if (choix.equals("y")) {
					carteADefausser.add(cartePiochee);
				}
			}
		}
		for(int i=0;i<carteADefausser.size();i++) {
			p.addToDiscard(p.removeFromHand(carteADefausser.get(i).getName()));
		}
		
	}
}