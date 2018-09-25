package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Chapelle (Chapel)
 * 
 * Écartez jusqu'à 4 cartes de votre main.
 */
public class Chapel extends ActionCard {

	public Chapel() {
		super("Chapel", 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		String choix="rien";
		int i=0;
		while(i<4 && !choix.equals("")) {
			choix=p.chooseCard("choisissez la carte que vous voulez defausser ou appuyez sur entr�e quand vous avez termin�",p.cardsInHand(),true);
			if (!choix.equals("")) {
				p.removeFromHand(choix);
			}
			i++;
		}
		
	}
}