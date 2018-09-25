package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Cave (Cellar)
 * 
 * +1 Action.
 * DÃ©faussez autant de cartes que vous voulez.
 * +1 Carte par carte dÃ©faussÃ©e.
 */
public class Cellar extends ActionCard {

	public Cellar() {
		super("Cellar", 2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		int compteur=0;
		p.incrementActions(1);
		String carteADefausser="rien";
		while (!carteADefausser.equals("") && !p.cardsInHand().isEmpty()){
			carteADefausser=p.chooseCard("Entrez la carte que vous voulez défausser et si vous avez fini appuyez sur Entrée", p.cardsInHand(), true);
			if (!carteADefausser.equals("")) {
				p.addToDiscard(p.removeFromHand(carteADefausser));
				compteur++;
			}
		}
		for (int i=0;i<compteur;i++) {
			p.addToHand(p.drawCard());
		}
			
		
	}
}