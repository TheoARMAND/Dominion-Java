package test;

import dominion.Game;
import dominion.card.CardList;

import java.util.List;

class BotGame extends Game {
	private GameProxy proxy;

	/**
	 * Sous-class de Game permettant d'exécuter automatiquement une stratégie
	 * @param playerNames noms des joueurs
	 * @param kingdomStacks piles de réserve
	 */
	public BotGame(String[] playerNames, List<CardList> kingdomStacks) {
		super(playerNames, kingdomStacks);
	}

	public void setProxy(GameProxy proxy) {
		this.proxy = proxy;
	}

	/**
	 * Stratégie des bots (ici Big Money)
	 *
	 * À chaque tour, le bot joue tous ses trésors et achète la carte la plus chère qu'il peut acheter parmi
	 *   - Province
	 *   - Gold
	 *   - Silver
	 * S'il a moins de 3 pièces (ne peut pas acheter de Silver) il passe son tour.
	 *
	 * @return l'instruction à envoyer pour appliquer la stratégie
	 */
	private String strategy() {
		GameProxy g = this.proxy;
		PlayerProxy p = new PlayerProxy(g.players[g.getCurrentPlayerIndex()]);
		if (p.getMoney() >= 8) {
			return "Province";
		} else if (p.getMoney() >= 6) {
			return "Gold";
		} else if (p.getMoney() >= 3) {
			return "Silver";
		} else {
			return "";
		}
	}

	/**
	 * Méthode permettant de lire les instructions des joueurs. Redéfinie pour lire automatiquement le résultat de la
	 * stratégie à appliquer
	 */
	public String readLine() {
		return this.strategy();
	}
}
