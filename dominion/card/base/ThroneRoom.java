package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Salle du trône (Throne Room)
 * 
 * Choisissez 1 carte Action de votre main.
 * Jouez-la deux fois.
 */
public class ThroneRoom extends ActionCard {

	public ThroneRoom() {
		super("Throne Room", 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		//if(!p.getActionCards().isEmpty()) {
		String carteAJouer=p.chooseCard("choisissez la carte � jouer ou appuyez sur entr�e", p.getActionCards(), true);
		Card cardPlayed=p.removeFromHand(carteAJouer);
		cardPlayed.play(p);
		cardPlayed.play(p);
		p.addToInPlay(cardPlayed);
		//}
		
	}
}