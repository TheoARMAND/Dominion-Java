package dominion.card;
import java.util.*;
import dominion.*;

/**
 * Les cartes Réaction
 * Rmq: les cartes Réaction sont toutes des cartes Action
 */
public abstract class ReactionCard extends ActionCard {

	public ReactionCard(String name, int cost) {
		super(name, cost);
		this.addType(CardType.Reaction);
		// TODO Auto-generated constructor stub
	}
}