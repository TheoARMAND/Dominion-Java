package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Bureaucrate (Bureaucrat)
 * 
 * Recevez une carte Argent; placez-la sur votre deck.
 * Tous vos adversaires dévoilent une carte Victoire et la placent sur leur deck (sinon ils dévoilent leur main afin que vous puissiez voir qu'ils n'ont pas de cartes Victoire).
 */
public class Bureaucrat extends AttackCard {

	public Bureaucrat() {
		super("Bureaucrat", 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		p.addAtTheTopOfTheDeck(p.removeFromSupply("Silver"));
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
				if(!otherPlayer.getVictoryCards().isEmpty()) {			
					String cardShown=otherPlayer.chooseCard("S�lectionnez la carte que vous souhaitez d�voiler",otherPlayer.getVictoryCards(),false);
					System.out.println(otherPlayer.getName()+" d�voile "+cardShown+" \n cette carte est remise sur son deck");
					otherPlayer.addAtTheTopOfTheDeck(otherPlayer.removeFromHand(cardShown));
				}
				else {
					System.out.println(otherPlayer.getName()+" ne poss�de pas de cartes victoire dans sa main");
					System.out.println(otherPlayer.cardsInHand().toString());
				}	
			}
		}
	}
	
	
	
	
	
	
	
	
}