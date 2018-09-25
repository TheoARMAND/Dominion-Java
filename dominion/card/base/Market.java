package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Marché (Market)
 * 
 * +1 Carte.
 * +1 Action.
 * +1 Achat.
 * +1 Pièce.
 */
public class Market extends ActionCard {

	public Market() {
		super("Market", 5);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		p.addToHand(p.drawCard());
		p.incrementActions(1);
		p.incrementBuys(1);
		p.incrementMoney(1);
		
	}
}