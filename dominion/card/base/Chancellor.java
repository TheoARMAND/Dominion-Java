package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Chancellier (Chancellor)
 * 
 * +2 Pièces.
 * Vous pouvez immédiatement défausser votre deck.
 */
public class Chancellor extends ActionCard {

	public Chancellor() {
		super("Chancellor", 3);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		p.incrementMoney(2);
		List<String> choices = Arrays.asList("y", "n");
		String choix=p.choose("voulez vous d�fausser votre deck? (y/n)", choices, false);
		if (choix.equals("y")) {	
			while (p.cardsInDraw().size()>0) {
				p.addToDiscard(p.drawCard());
			}
		}
		
	}
}