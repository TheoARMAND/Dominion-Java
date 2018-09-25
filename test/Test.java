package test;

import dominion.card.Card;
import dominion.card.CardList;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class Test {
	/**
	 * Nombre total de tests réussis
 	 */
	private int nb_pass = 0;

	/**
	 * Nombre total de tests ratés
	 */
	private int nb_fail = 0;

	/**
	 * Nombre total d'exceptions levées
	 */
	private int nb_error = 0;

	/**
	 * Indique si le test courant est correct
	 */
	private boolean testOk;

	/**
	 * Affichage de résultat de tous les tests passés
	 */
	private StringBuilder results = new StringBuilder();

	/**
	 * Vérifie une condition, et modifie éventuellement l'état de succès du test
	 * @param test: condition à tester
	 */
	public void check(boolean test) {
		this.testOk &= test;
	}

	/**
	 * Nombre total de tests effectués (somme des réussite, succès et erreurs)
	 */
	public int nb_test() {
		return this.nb_pass + this.nb_fail + this.nb_error;
	}

	/**
	 * Exécute un test
	 * @param description: description du test à effectuer, qui sera affichée à l'écran
	 * @param test_function: instructions du test (fonction statique qui prend en argument un objet de type Test dont
	 *                     les attributs sont modifiés en fonction du succès des vérifications)
	 */
	public void runTest(String description, Consumer<Test> test_function) {
		this.results.append(description + " : ");
		this.testOk = true;
		try {
			test_function.accept(this);
			if (this.testOk) {
				// succès
				this.results.append("[OK]\n");
				this.nb_pass += 1;
			}
			else {
				// échec
				this.results.append("[ÉCHEC]\n");
				this.nb_fail += 1;
			}
		} catch (Exception e) {
			// exception levée
			this.results.append("[ERREUR]\n");
			this.nb_error += 1;
		}
	}

	/**
	 * Méthode exécutant tous les tests du jeu de tests
	 */
	public abstract void run();

	/**
	 * Représentation globale des résultats du jeu de tests
	 */
	public void showResults() {
		System.out.println();
		System.out.println("----");
		System.out.println(this.results);
		System.out.println("----");
		System.out.println("Tests effectués : " + this.nb_test());
		System.out.println("Succès : " + this.nb_pass);
		System.out.println("Échecs : " + nb_fail);
		System.out.println("Erreurs : " + nb_error);
	}

	/* Méthodes statiques */
	
	/**
	 * Convertit une CardList en liste de chaînes de caractères (les noms des
	 * cartes)
	 */
	public static String[] cardsToString (CardList l) {
		String[] result = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			result[i] = l.get(i).getName();
		}
		return result;
	}
	
	/**
	 * Teste si une CardList contient exactement les cartes indiquées dans la
	 * chaîne de caractères `namesString` (noms de cartes séparées par des
	 * virgules)
	 */
	public static boolean isList(CardList cards, String namesString) {
		String[] names = namesString.split(",\\s*");
		String[] cardNames = cardsToString(cards);
		for (int i = 0; i < names.length; i++) {
			if (!names[i].equals(cardNames[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Teste si une CardList contient exactement les cartes indiquées dans la
	 * chaîne `namesString` (noms de cartes séparées par des virgules). Les deux
	 * listes doivent avoir les mêmes éléments, avec les mêmes multiplicités
	 * mais l'ordre n'a pas d'importance.
	 */
	public static boolean hasCards(CardList cards, String... names) {
		if (cards.size() != names.length) {
			return false;
		}
		Arrays.sort(names);
		String[] cardNames = cardsToString(cards);
		Arrays.sort(cardNames);
		for (int i = 0; i < names.length; i++) {
			if (!names[i].equals(cardNames[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Teste si une CardList contient au moins le nom indiqué dans chaîne `namesString` (un nom de carte). 
	 */

	public static boolean hasThisCard(CardList cards, String name) {
		List<String> l = Arrays.asList(cardsToString(cards));
		return l.contains(name);
	}

	/**
	 * Renvoie une CardList contenant `nb_copies` exemplaires de la carte 
	 * passée en argument
	 * 
	 * @param c: classe de carte à instancier
	 * @param nb_copies: nombre d'exemplaires à mettre dans la pile
	 * @return une liste de cartes
	 */
	public static CardList makeStack(Class<?> c, int nb_copies) {
		CardList stack = new CardList();
		for (int i = 0; i < nb_copies; i++) {
			try {
				stack.add((Card) c.getConstructor().newInstance());
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return stack;
	}

	/**
	 * Flux de sortie vide, permettant d'ignorer la sortie standard
	 */
	public final static PrintStream nullOut = new PrintStream(new OutputStream() {
		public void close() {}
		public void flush() {}
		public void write(byte[] b) {}
		public void write(byte[] b, int off, int len) {}
		public void write(int b) {}
	});

}
