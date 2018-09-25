package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Milice (Militia)
 * 
 * 2 Pièces.
 * Tous vos adversaires défaussent leurs cartes de façon à n'avoir que 3 cartes en main.
 */
public class Militia extends AttackCard {

	public Militia() {
		super("Militia", 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		p.incrementMoney(2);
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
				while(otherPlayer.cardsInHand().size()>3) {
					String choix=otherPlayer.chooseCard("choisssez la carte � defausser",otherPlayer.cardsInHand(), false);
					otherPlayer.addToDiscard(otherPlayer.removeFromHand(choix));
				}
			}
		}
		
	}
}