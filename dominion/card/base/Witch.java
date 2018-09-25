package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Sorcière (Witch)
 * 
 * +2 Cartes.
 * Tous vos adversaires recoivent une carte Curse.
 */
public class Witch extends AttackCard {

	public Witch() {
		super("Witch", 5);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		p.addToHand(p.drawCard());
		p.addToHand(p.drawCard());
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
				Card curse=otherPlayer.removeFromSupply("Curse");
				otherPlayer.addToDiscard(curse);
			}
		}
		
	}
}