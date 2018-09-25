package dominion.card.base;
import java.util.*;
import dominion.*;
import dominion.card.*;

/**
 * Carte Mine
 * 
 * Écartez une carte Trésor de votre main. Recevez une carte Trésor coûtant jusqu'à 3 Pièces de plus ; ajoutez cette carte à votre main.
 */
public class Mine extends ActionCard {

	public Mine() {
		super("Mine", 5);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play(Player p) {
		String choix=p.chooseCard("Choisissez la carte � �carter ou appuyez sur entr�e", p.getTreasureCards(), true);
		if (!choix.equals("")) {
			Card cardToRemove=p.removeFromHand(choix);
			int cost=cardToRemove.getCost();
			CardList cardToGain=p.getGame().availableSupplyCards();
			for(int i=0;i<cardToGain.size();i++) {
				if(cardToGain.get(i).getCost()>cost+3 || !cardToGain.get(i).isATypeOf(CardType.Treasure)) {
					cardToGain.remove(cardToGain.get(i).getName());
				}
			}
			choix=p.chooseCard("choisissez la carte Tr�sor que vous voulez gagner ou appuyez sur entr�e",cardToGain,true);
			if (!choix.equals("")) {
				p.addToHand(p.removeFromSupply(choix));
			}
		}
	}
}