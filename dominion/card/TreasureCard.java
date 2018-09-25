package dominion.card;
import java.util.*;
import dominion.*;

/**
 * Les cartes Trésor
 */
public abstract class TreasureCard extends Card {

	public TreasureCard(String name, int cost) {
		super(name,cost);
		this.addType(CardType.Treasure);
		// TODO Auto-generated constructor stub
	}
	public abstract int treasureValue();
}