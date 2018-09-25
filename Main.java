
import java.util.*;
import dominion.*;
import dominion.card.*;
import dominion.card.base.*;

/**
 * Classe pour l'exécution d'une partie de Dominion
 */
class Main {
	public static void main(String[] args) {
		// Noms des joueurs de la partie
		// (le nombre total de joueurs correspond au nombre de noms dans le 
		// tableau)
		String[] playerNames = new String[]{"Marco", "Polo"};
		// Prépare les piles "royaume" de la réserve (hors cartes communes)
		List<CardList> kingdomStacks = new ArrayList<CardList>();
		CardList stack=new CardList();
		// Ajouter un bloc pour chaque carte royaume à utiliser
		//1
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Village());
		}
		kingdomStacks.add(stack);
		//2
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Cellar());
		}
		kingdomStacks.add(stack);
		//3
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Market());
		}
		kingdomStacks.add(stack);
		//4
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Militia());
		}
		kingdomStacks.add(stack);
		//5
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Mine());
		}
		kingdomStacks.add(stack);
		//6
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Moat());
		}
		kingdomStacks.add(stack);
		//7
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Remodel());
		}
		kingdomStacks.add(stack);
		//8
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Smithy());
		}
		kingdomStacks.add(stack);
		//9
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Woodcutter());
		}
		kingdomStacks.add(stack);
		//10
		stack = new CardList();
		for (int i = 0; i < 10; i++) {
			stack.add(new Workshop());
		}
		kingdomStacks.add(stack);
		
		// Instancie et exécute une partie
		Game g = new Game(playerNames, kingdomStacks);
		g.run();
	}
}