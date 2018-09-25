package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Voleur (Thief)
 * 
 * Tous vos adversaires dévoilent les 2 premières cartes de leur deck. S'ils dévoilent des cartes Trésor, ils en écartent 1 de votre choix. Parmi ces cartes Trésor écartées, recevez celles de votre choix. Les autres cartes dévoilées sont défaussées.
 */
public class Thief extends AttackCard {

	public Thief() {
		super("Thief", 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		CardList carteRemove =new CardList();
		for (Player otherPlayer : p.otherPlayers()) {
			String joueMoat="n";
			Boolean moatEnMain=false;
			for (Card carte : otherPlayer.cardsInHand()) {
				if (carte.getName().equals("Moat")){
					moatEnMain=true;
				}
			}
			if (moatEnMain) {
				List<String> choices = Arrays.asList("y", "n");
				joueMoat=otherPlayer.choose("voulez vous jouer votre carte Moat?", choices, false);
			}
			if(joueMoat.equals("n")) {
				CardList pioche =new CardList();
				Card cartePiochee=otherPlayer.drawCard();
				if(cartePiochee.isATypeOf(CardType.Treasure)) {
					pioche.add(cartePiochee);
				}
				else {
					otherPlayer.addToDiscard(cartePiochee);	
					}
				cartePiochee=otherPlayer.drawCard();
				if(cartePiochee.isATypeOf(CardType.Treasure)) {
					pioche.add(cartePiochee);
				}
				else {
					otherPlayer.addToDiscard(cartePiochee);	
					}
				String choix=p.chooseCard("choisissez la carte a écarter de "+ otherPlayer.getName(), pioche, false);
				carteRemove.add(pioche.remove(choix));
				if(!pioche.isEmpty()) {
					otherPlayer.addToDiscard(pioche.get(0));
				}
			}
		
		}
		String choix="null";
		while (!choix.equals("") && !carteRemove.isEmpty()) {	
			choix=p.chooseCard("choississez la carte que vous voulez récupérer ou appuyez sur entrée ",carteRemove, true);
			if(!choix.equals("")) {
				p.addToDiscard(carteRemove.remove(choix));
			}
		}

	}
}