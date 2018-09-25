package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Espion (Spy)
 * 
 * +1 Carte.
 * +1 Action.
 * Tous les joueurs (vous aussi) dévoilent la première carte de leur deck. Vous décidez ensuite si chaque carte dévoilée est défaussée ou replacée sur son deck.
 */
public class Spy extends AttackCard {

	public Spy() {
		super("Spy", 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		
		p.addToHand(p.drawCard());
		p.incrementActions(1);
		Card cartePiochee=p.drawCard();
		List<String> choices = Arrays.asList("y", "n");
		String choix=p.choose(p.getName()+" pioche la carte "+cartePiochee.getName()+" voulez vous \ny: la d�fausser\nn: La remettre sur le deck", choices, false);
		if (choix.equals("y")) {
			p.addToDiscard(cartePiochee);
		}
		else {
			p.addAtTheTopOfTheDeck(cartePiochee);
		}
		for (Player otherPlayer : p.otherPlayers()) {
			String joueMoat="n";
			Boolean moatEnMain=false;
			for (Card carte : otherPlayer.cardsInHand()) {
				if (carte.getName().equals("Moat")){
					moatEnMain=true;
				}
			}
			if (moatEnMain) {
				joueMoat=otherPlayer.choose("voulez vous jouer votre carte Moat?", choices, false);
			}
			if(joueMoat.equals("n")) {
				cartePiochee=otherPlayer.drawCard();
				choix=otherPlayer.choose(otherPlayer.getName()+" pioche la carte "+cartePiochee.getName()+" voulez vous \ny: la d�fausser\nn: La remettre sur le deck", choices, false);
				if (choix.equals("y")) {
					otherPlayer.addToDiscard(cartePiochee);
				
				}
				else {
					otherPlayer.addAtTheTopOfTheDeck(cartePiochee);
	
				}
			}
		}
		
	}
}