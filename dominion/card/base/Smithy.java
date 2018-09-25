package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Forgeron (Smithy)
 * 
 * +3 Cartes.
 */
public class Smithy extends ActionCard {

	public Smithy() {
		super("Smithy", 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		for (int i=0;i<3;i++) {
			p.addToHand(p.drawCard());
		}
		
	}
}