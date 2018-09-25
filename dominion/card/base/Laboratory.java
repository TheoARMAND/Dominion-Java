package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Laboratoire (Laboratory)
 * 
 * +2 Cartes.
 * +1 Action.
 */
public class Laboratory extends ActionCard {

	public Laboratory() {
		super("Laboratory",5);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		p.addToHand(p.drawCard());
		p.addToHand(p.drawCard());
		p.incrementActions(1);
		
	}
}