package test;

import dominion.Game;
import dominion.Player;
import dominion.card.CardList;

import java.lang.reflect.Field;
import java.util.List;

public class GameProxy {
	/**
	 * Instance de Game associée
	 */
	public Game game;
	/**
	 * Accès direct aux piles de réserve du jeu
	 */
	public List<CardList> supplyStacks;
	/**
	 * Accès direct aux joueurs du jeu
	 */
	public Player[] players;
	private Field currentPlayerIndex_f;

	public GameProxy(Game g) {
		this.game = g;
		try {
			Field supplyStacks_f = Game.class.getDeclaredField("supplyStacks");
			supplyStacks_f.setAccessible(true);
			this.supplyStacks = (List<CardList>) supplyStacks_f.get(this.game);
			Field players_f = Game.class.getDeclaredField("players");
			players_f.setAccessible(true);
			this.players = (Player[]) players_f.get(this.game);
			this.currentPlayerIndex_f = Game.class.getDeclaredField("currentPlayerIndex");
			currentPlayerIndex_f.setAccessible(true);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public Player getPlayer(int index) {
		return this.game.getPlayer(index);
	}

	public int getCurrentPlayerIndex() {
		try {
			return this.currentPlayerIndex_f.getInt(this.game);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Renvoie la pile correspondant au nom de carte passé en argument.
	 * @param cardName: nom de la carte recherchée
	 * @return: la pile de réserve correspondant au nom, ou null si aucune.
	 */
	public CardList getSupplyStack(String cardName) {
		for (CardList stack: this.supplyStacks) {
			if (!stack.isEmpty() && stack.get(0).getName().equals(cardName)) {
				return stack;
			}
		}
		return null;
	}

	public void run() {
		this.game.run();
	}

	/**
	 * Fixe la liste d'instructions du jeu.
	 * N'a d'effet que si le jeu est une instance de IOGame
	 * @param args liste de chaînes de caractères. Chaque élément est une instruction (sans '\n' à la fin)
	 */
	public void setInput(String... args) {
		if (this.game instanceof IOGame) {
			IOGame g = (IOGame) this.game;
			g.clearInstructions();
			for(String s: args) {
				g.addInstruction(s);
			}
		}
	}
}