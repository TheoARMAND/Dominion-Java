package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Chambre du conseil (Council Room)
 * 
 * +4 Cartes.
 * +1 Achat.
 * Tous vos adversaires piochent 1 carte.
 */
public class CouncilRoom extends ActionCard {

	public CouncilRoom() {
		super("Council Room", 5);
		
	}

	@Override
	public void play(Player p) {
		for (int i=0;i<4;i++) {
			p.addToHand(p.drawCard());
		}
		p.incrementBuys(1);
			for (Player otherPlayer : p.otherPlayers()) {
				otherPlayer.addToHand(otherPlayer.drawCard());		
		}		
	}
}