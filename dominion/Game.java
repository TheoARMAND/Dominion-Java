package dominion;
import java.util.*;
import dominion.card.*;
import dominion.card.common.*;

/**
 * Class représentant une partie de Dominion
 */
public class Game {
	/**
	 * Tableau contenant les joueurs de la partie
	 */
	private Player[] players;
	
	/**
	 * Index du joueur dont c'est actuellement le tour
	 */
	private int currentPlayerIndex;
	
	/**
	 * Liste des piles dans la réserve du jeu.
	 * 
	 * On suppose ici que toutes les listes contiennent des copies de la même
	 * carte.
	 * Ces piles peuvent être vides en cours de partie si toutes les cartes de 
	 * la pile ont été achetées ou gagnées par les joueurs.
	 */
	private List<CardList> supplyStacks;
	
	/**
	 * Liste des cartes qui ont été écartées (trash)
	 */
	private CardList trashedCards;
	
	/**
	 * Scanner permettant de lire les entrÃ©es au clavier
	 */
	private Scanner scanner;
	
	/**
	 * Constructeur
	 * 
	 * @param playerNames liste des noms des joueurs qui participent à la 
	 * partie. Le constructeur doit créer les objets correspondant aux joueurs
	 * @param kingdomStacks liste de piles de réserve à utiliser correspondant 
	 * aux cartes "royaume" à utiliser dans la partie, auxquelles le 
	 * constructeur doit ajouter les piles "communes":
	 * - 60 Copper
	 * - 40 Silver
	 * - 30 Gold
	 * - 8 (si 2 joueurs) ou 12 (si 3 ou 4 joueurs) Estate, Duchy et Province 	 * - 10 * (n-1) Curse où n est le nombre de joueurs dans la partie
	 */
	public Game(String[] playerNames, List<CardList> kingdomStacks) {
		this.players=new Player[playerNames.length];
		for (int i=0; i<playerNames.length;i++) {
			this.players[i]=new Player(playerNames[i],this);		
		}
		this.supplyStacks=new ArrayList<CardList>();
		CardList copperList= new CardList();
		CardList silverList= new CardList();
		CardList goldList= new CardList();
		
		CardList estateList= new CardList();
		CardList duchyList= new CardList();
		CardList provinceList= new CardList();
		CardList curseList= new CardList();
		
		
		for(int i=0;i<60;i++) {
			copperList.add(new Copper());
		}
		for(int i=0;i<40;i++) {
			silverList.add(new Silver());
		}
		for(int i=0;i<30;i++) {
			goldList.add(new Gold());
		}
		if (numberOfPlayers()<=2) {
			for(int i=0;i<8;i++) {
				estateList.add(new Estate());
			}
			for(int i=0;i<8;i++) {
				duchyList.add(new Duchy());
			}
			for(int i=0;i<8;i++) {
				provinceList.add(new Province());
			}			
		}
		else  {
			for(int i=0;i<12;i++) {
				estateList.add(new Estate());
			}
			for(int i=0;i<12;i++) {
				duchyList.add(new Duchy());
			}
			for(int i=0;i<12;i++) {
				provinceList.add(new Province());
			}
		}
		for (int i=0; i<(10*(numberOfPlayers()-1));i++) {
			curseList.add(new Curse());
		}
		
		this.supplyStacks.add(estateList);
		this.supplyStacks.add(duchyList);
		this.supplyStacks.add(provinceList);
		this.supplyStacks.add(curseList);
		this.supplyStacks.add(copperList);
		this.supplyStacks.add(silverList);
		this.supplyStacks.add(goldList);
		this.supplyStacks.addAll(kingdomStacks);
		
		
	}
	
	/**
	 * Renvoie le joueur correspondant à l'indice passé en argument
	 * On suppose {@code index} est un indice valide du tableau 
	 * {@code this.players}
	 * 
	 * @param index indice dans le tableau des joueurs du joueur à renvoyer
	 */
	public Player getPlayer(int index) {
		return players[index];
	}
	
	/**
	 * Renvoie le nombre de joueurs participant à la partie
	 */
	public int numberOfPlayers() {
		return players.length;
	}
	
	/**
	 * Renvoie l'indice du joueur passé en argument dans le tableau des 
	 * joueurs, ou -1 si le joueur n'est pas dans le tableau.
	 */
	private int indexOfPlayer(Player p) {
		int index=0;
		int i=0;
		while(i<players.length) {
			if (players[i].equals(p)) {
				index=i;
			}		
			i++;
		}
		return index;
	}
	
	/**
	 * Renvoie la liste des adversaires du joueur passé en argument, dans 
	 * l'ordre dans lequel ils apparaissent à partir du joueur {@code p}.
	 * 
	 * @param p joueur dont on veut renvoyer la liste des adversaires. On 
	 * suppose que {@code p} est bien dans le tableau des joueurs.
	 * @return un {@code ArrayList} contenant les autres joueurs de la partie 
	 * en commençant par celui qui se trouve juste après {@code p} et en 
	 * terminant par celui qui se trouve juste avant (le tableau est considéré 
	 * comme cyclique c'est-à-dire qu'après le dernier élément on revient au 
	 * premier).
	 */
	public List<Player> otherPlayers(Player p) {
		List<Player> autreJoueurs=new ArrayList<Player>();
		int index=indexOfPlayer(p);
		for (int i=0;i<numberOfPlayers()-1;i++) {
			if (index==numberOfPlayers()-1) {
				autreJoueurs.add(players[0]);
				index=0;
			}
			else {
				autreJoueurs.add(players[index+1]);
				index++;
			}
		}
		return autreJoueurs;
	}
	
	/**
	 * Renvoie la liste des cartes qui sont disponibles à l'achat dans la 
	 * réserve.
	 * 
	 * @return une liste de cartes contenant la première carte de chaque pile 
	 * non-vide de la réserve (cartes royaume et cartes communes)
	 */
	public CardList availableSupplyCards() {
		CardList available= new CardList();
		
		for(CardList cardList : this.supplyStacks) {
			
			if(!cardList.isEmpty()) {
				available.add(cardList.get(0));
				
			}
		}
		return available;
		
	}
	
	/**
	 * Renvoie une représentation de l'état de la partie sous forme d'une chaîne
	 * de caractères.
	 * 
	 * Cette représentation comporte
	 * - le nom du joueur dont c'est le tour
	 * - la liste des piles de la réserve en indiquant pour chacune :
	 *   - le nom de la carte
	 *   - le nombre de copies disponibles
	 *   - le prix de la carte
	 *   si la pile n'est pas vide, ou "Empty stack" si la pile est vide
	 */
	public String toString() {
		Player currentPlayer = this.players[this.currentPlayerIndex];
		String r = String.format("     -- %s's Turn --\n", currentPlayer.getName());
		for (List<Card> stack: this.supplyStacks) {
			if (stack.isEmpty()) {
				r += "[Empty stack]   ";
			} else {
				Card c = stack.get(0);
				r += String.format("%s x%d(%d)   ", c.getName(), stack.size(), c.getCost());
			}
		}
		r += "\n";
		return r;
	}
	
	/**
	 * Renvoie une carte de la réserve dont le nom est passé en argument.
	 * 
	 * @param cardName nom de la carte à trouver dans la réserve
	 * @return la carte trouvée dans la réserve ou {@code null} si aucune carte 
	 * ne correspond
	 */
	public Card getFromSupply(String cardName) {
		int i=0;
		boolean cardFound=false;
		Card card = null;
		
		while(i<availableSupplyCards().size() && !cardFound) {
			if(this.availableSupplyCards().get(i).getName().equals(cardName)) {
				card=this.availableSupplyCards().get(i);
				cardFound=true;
			}
			i++;
		}
		return card;
	}
	
	/**
	 * Retire et renvoie une carte de la réserve
	 * 
	 * @param cardName nom de la carte à retirer de la réserve
	 * @return la carte retirée de la réserve ou {@code null} si aucune carte
	 * ne correspond au nom passé en argument
	 */
	public Card removeFromSupply(String cardName) {
		int i=0;
		boolean cardFound=false;
		Card card = null;
		
		while(i<supplyStacks.size() && !cardFound) {
			if(!this.supplyStacks.get(i).isEmpty()) {
				if( this.supplyStacks.get(i).get(0).getName().equals(cardName) ) {
					card=this.supplyStacks.get(i).remove(0);
					cardFound=true;
				}
			}
			i++;
		}
		return card;
	}
	
	/**
	 * Teste si la partie est terminée
	 * 
	 * @return un booléen indiquant si la partie est terminée, c'est-à-dire si
	 * au moins l'unedes deux conditions de fin suivantes est vraie
	 *  - 3 piles ou plus de la réserve sont vides
	 *  - la pile de Provinces de la réserve est vide
	 * (on suppose que toute partie contient une pile de Provinces, et donc si 
	 * aucune des piles non-vides de la réserve n'est une pile de Provinces, 
	 * c'est que la partie est terminée)
	 */
	/*public boolean isFinished() {
		boolean provinceFind=false;
		int vide = 0;
		int i=0;
		while(i<this.supplyStacks.size() && vide<3) {
			if(this.supplyStacks.get(i).isEmpty()) {
				vide++;	
			}
			i++;
		}
		
		for (int s=0;s<availableSupplyCards().size();s++) {
			
			if (availableSupplyCards().get(s).getName()=="Province") {
				provinceFind=true;
			}
		}
		
		
		return (!provinceFind || vide>=3);
	}*/
	public boolean isFinished() {
		int compt=0;
		boolean res=true;
		for(CardList stack : supplyStacks) {
			if (!stack.isEmpty() && stack.get(0).getName().equals("Province")) {
				res = false;			
			}
			if (stack.isEmpty())  {
				compt++ ;
			}
		}
		return res || compt >=3 ;
	}
	/**
	 * Boucle d'exécution d'une partie.
	 * 
	 * Cette méthode exécute les tours des joueurs jusqu'à ce que la partie soit
	 * terminée. Lorsque la partie se termine, la méthode affiche le score 
	 * final et les cartes possédées par chacun des joueurs.
	 */
	public void run() {
		
		while (! this.isFinished()) {
			// joue le tour du joueur courant
			this.players[this.currentPlayerIndex].playTurn();
			// passe au joueur suivant
			this.currentPlayerIndex += 1;
				if (this.currentPlayerIndex >= this.players.length) {
					this.currentPlayerIndex = 0;
				}
		}
			System.out.println("Game over.");
			// Affiche le score et les cartes de chaque joueur
			for (int i = 0; i < this.players.length; i++) {
				Player p = this.players[i];
				System.out.println(String.format("%s: %d Points.\n%s\n", p.getName(), p.victoryPoints(), p.totalCards().toString()));
			}
	}

/**
 * Lit une ligne de l'entrÃ©e standard
 * 
 * C'est cette mÃ©thode qui doit Ãªtre appelÃ©e Ã  chaque fois qu'on veut lire
 * l'entrÃ©e clavier de l'utilisateur (par exemple dans Player.choose), ce
 * qui permet de n'avoir qu'un seul Scanner pour tout le programme
 * 
 * @return une chaÃ®ne de caractÃ¨res correspondant Ã  la ligne suivante de
 * l'entrÃ©e standard (sans le retour Ã  la ligne final)
 */
	public String readLine() {
		scanner = new Scanner(System.in);
		return this.scanner.nextLine();
	}
	
	public void addToTrash(Card c) {
		this.trashedCards.add(c);
	}


}