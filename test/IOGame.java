package test;

import dominion.Game;
import dominion.card.CardList;

import java.util.ArrayList;
import java.util.List;

/**
 * Une extension de la class Game dans laquelle la méthode readLine est redéfinie pour lire les instructions depuis
 * une liste de chaînes de caractères
 */
public class IOGame extends Game {
	/**
	 * Liste contenant les instructions à lire (qui remplacent les entrées au clavier)
	 */
	private List<String> instructions;

	/**
	 * Constructeur, qui reprend exactement le constructeur de Game
	 */
	public IOGame(String[] playerNames, List<CardList> kingdomStacks) {
		super(playerNames, kingdomStacks);
		this.instructions = new ArrayList<>();
	}

	/**
	 * Création d'un jeu minimal pour les tests (3 joueurs mais aucune carte Kingdom)
	 */
	public static IOGame minimal() {
		String []playerNames = new String[]{"Toto", "Titi", "Tutu"};
		List<CardList> kingdomStacks = new ArrayList<>();
		return new IOGame(playerNames, kingdomStacks);
	}

	/**
	 * Efface la liste des instructions
	 */
	public void clearInstructions() {
		this.instructions.clear();
	}

	/**
	 * Ajoute une instruction
	 */
	public void addInstruction(String s) {
		this.instructions.add(s);
	}

	/**
	 * Lit et renvoie une instruction dans la liste
	 */
	public String readLine() {
		return this.instructions.remove(0);
	}
}