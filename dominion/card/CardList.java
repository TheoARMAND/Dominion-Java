package dominion.card;
import java.util.*;

/**
 * Liste de cartes
 */
public class CardList extends ArrayList<Card> {
	
	/**
	 * Constructeur vide
	 */
	public CardList() {
		super();
	}
	
	/**
	 * Constructeur à partir d'une liste de cartes
	 */
	public CardList(List<Card> l) {
		super(l);
	}
	
	/**
	 * Mélange la liste
	 */
	public void shuffle() {
		Collections.shuffle(this);
	}
	
	/**
	 * Retire une carte de la liste dont le nom est égal à l'argument passé
	 * 
	 * @param cardName le nom de la carte à retirer
	 * @return la carte retirée si elle a été trouvée, {@code null} sinon
	 */
	public Card remove(String cardName) {
		for (Card c: this) {
			if (c.getName().equals(cardName)) {
				this.remove(c);
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Renvoie une carte de la liste dont le nom est égal à l'argument
	 * 
	 * @param cardName le nom de la carte à chercher
	 * @return une carte de la liste ayant le nom cherché si elle existe, 
	 * {@code null} sinon
	 */
	public Card getCard(String cardName) {
		for (Card c: this) {
			if (c.getName().equals(cardName)) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Représentation de la liste sous forme d'une chaîne de caractères
	 * 
	 * Cette fonction renvoie une chaîne de caractères constituée des noms des
	 * cartes de la liste séparées par ", ".
	 * Par exemple, si la liste contient une carte Village et une carte Copper,
	 * la fonction renvoie la chaîne "Village, Copper"
	 */
	public String toString() {
		if (this.size() == 0) {
			return "";
		}
		
		String r = "";
		for (Card c: this) {
			r += c.toString() + ", ";
		}
		return r.substring(0, r.length() - 2);
	}
}
