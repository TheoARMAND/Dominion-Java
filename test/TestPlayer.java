package test;

import dominion.Player;
import dominion.card.Card;
import dominion.card.CardList;
import dominion.card.common.*;

public class TestPlayer extends Test {

	private static void testPlayerConstructor(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		Player p = g.getPlayer(0);
		PlayerProxy p_p = new PlayerProxy(p);
		t.check(p_p.hand.size() == 5);
		t.check(p_p.draw.size() == 5);
	}

	private static void testIncrements(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		Player p = g.getPlayer(0);
		PlayerProxy p_p = new PlayerProxy(p);
		p_p.clear();
		// Actions
		p.incrementActions(2);
		t.check(p_p.getActions() == 2);
		p.incrementActions(-1);
		t.check(p_p.getActions() == 1);
		// Money
		p.incrementMoney(2);
		t.check(p_p.getMoney() == 2);
		p.incrementMoney(-1);
		t.check(p_p.getMoney() == 1);
		// Buys
		p.incrementBuys(2);
		t.check(p_p.getBuys() == 2);
		p.incrementBuys(-1);
		t.check(p_p.getBuys() == 1);
	}

	private static void testDrawCard(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		Player p = g.getPlayer(0);
		PlayerProxy p_p = new PlayerProxy(p);

		//test que le drawCard enlève bien une carte de draw
		p_p.clear();
		p_p.addToDraw(Copper.class, 2);
		t.check(p.drawCard().getName().equals("Copper"));
		t.check(p_p.draw.size() == 1);

		//test que le drawCard, lorsque la pioche est vide, transfère le discard vers le draw, puis pioche
		p_p.clear();
		p_p.addToDiscard(Copper.class, 3);
		t.check(p.drawCard().getName().equals("Copper"));
		t.check(p_p.discard.size() == 0);
		t.check(p_p.draw.size() == 2);

		//test quand aucune carte, ni dans draw ni dans discard
		p_p.clear();
		t.check(p.drawCard() == null);
	}

	private static void testCardsInHand(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		Player p = g.getPlayer(0);
		PlayerProxy p_p = new PlayerProxy(p);
		//test que le cardsInHand renvoie bien une liste indépendante
		p_p.clear();
		p_p.addToHand(Copper.class, 2);
		CardList l = p.cardsInHand();
		t.check(p_p.hand.equals(l));  // les listes sont logiquement égales
		t.check(p_p.hand != l);  // les listes sont physiquement différentes
	}

	private static void testTotalCards(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		Player p = g.getPlayer(0);
		PlayerProxy p_p = new PlayerProxy(p);

		p_p.clear();
		p_p.addToHand(Copper.class, 1);
		p_p.addToDraw(Copper.class, 2);
		p_p.addToInPlay(Copper.class, 4);
		p_p.addToDiscard(Copper.class, 8);
		t.check(p.totalCards().size() == 15);
	}

	private static void testVictoryPoints(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		Player p = g.getPlayer(0);
		PlayerProxy p_p = new PlayerProxy(p);

		p_p.clear();
		t.check(p.victoryPoints() == 0);

		p_p.addToHand(Copper.class, 1);
		p_p.addToDraw(Copper.class, 2);
		p_p.addToInPlay(Copper.class, 4);
		p_p.addToDiscard(Copper.class, 8);
		t.check(p.victoryPoints() == 0);

		p_p.addToHand(Estate.class, 1);
		t.check(p.victoryPoints() == 1);

		p_p.addToDraw(Duchy.class, 1);
		t.check(p.victoryPoints() == 4);

		p_p.addToDiscard(Province.class, 1);
		t.check(p.victoryPoints() == 10);

		p_p.addToInPlay(Estate.class, 1);
		t.check(p.victoryPoints() == 11);
	}

	private static void testGetTreasureCards(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		Player p = g.getPlayer(0);
		PlayerProxy p_p = new PlayerProxy(p);

		p_p.clear();
		p_p.addToHand(Copper.class, 1);
		p_p.addToHand(Silver.class, 2);
		p_p.addToHand(Gold.class, 4);
		p_p.addToHand(Estate.class, 1);
		t.check(p.getTreasureCards().size() == 7);

		p_p.addToDraw(Copper.class, 1);
		t.check(p.getTreasureCards().size() == 7);

		p_p.addToInPlay(Copper.class, 1);
		t.check(p.getTreasureCards().size() == 7);

		p_p.addToDiscard(Copper.class, 1);
		t.check(p.getTreasureCards().size() == 7);

	}

	private static void testPlayCard(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		Player p = g.getPlayer(0);
		PlayerProxy p_p = new PlayerProxy(p);

		p_p.clear();
		p_p.addToHand(Copper.class, 1);
		p_p.playCard("Copper");
		t.check(p_p.hand.size() == 0);
		t.check(p_p.inPlay.size() == 1);

		p_p.clear();
		p_p.addToHand(Copper.class, 1);
		p_p.playCard("Blob");
		t.check(p_p.hand.size() == 1);
		t.check(p_p.inPlay.size() == 0);
	}

	private static void testGain(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		Player p = g.getPlayer(0);
		PlayerProxy p_p = new PlayerProxy(p);
		p_p.clear();
		p.gain("Silver");
		t.check(p_p.discard.size() == 1);
		t.check(g.getSupplyStack("Silver").size()==39);
	}

	private static void testBuyCard(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		Player p = g.getPlayer(0);
		PlayerProxy p_p = new PlayerProxy(p);

		p_p.clear();
		p_p.setMoney(8);
		p_p.setBuys(1);
		Card c = p.buyCard("Province");
		t.check(hasThisCard(p_p.discard, "Province"));
		t.check(p_p.getBuys() == 0);
		t.check(p_p.getMoney() == 0);
		t.check(c.getName().equals("Province"));
	}

	private static void testBuyCardTooExpensive(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		Player p = g.getPlayer(0);
		PlayerProxy p_p = new PlayerProxy(p);

		p_p.clear();
		p_p.setMoney(7);
		p_p.setBuys(1);
		Card c = p.buyCard("Province");
		t.check(p_p.getBuys() == 1);
		t.check(p_p.getMoney() == 7);
		t.check(c == null);
	}

	private static void testBuyCardNoBuys(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		Player p = g.getPlayer(0);
		PlayerProxy p_p = new PlayerProxy(p);

		p_p.clear();
		p_p.setMoney(8);
		p_p.setBuys(0);
		Card c = p.buyCard("Province");
		t.check(p_p.getBuys()==0);
		t.check(p_p.getMoney()==8);
		t.check(c==null);
	}

	public void run() {
	    this.runTest("Constructeur de Player", TestPlayer::testPlayerConstructor);
	    this.runTest("Incrémentation des compteurs", TestPlayer::testIncrements);
	    this.runTest("Piocher une carte", TestPlayer::testDrawCard);
	    this.runTest("Liste des cartes en main", TestPlayer::testCardsInHand);
	    this.runTest("Liste de toutes les cartes", TestPlayer::testTotalCards);
	    this.runTest("Points de victoire", TestPlayer::testVictoryPoints);
	    this.runTest("Cartes Trésor en main", TestPlayer::testGetTreasureCards);
	    this.runTest("Jouer une carte", TestPlayer::testPlayCard);
	    this.runTest("Gagner une carte", TestPlayer::testGain);
		this.runTest("Acheter une carte", TestPlayer::testBuyCard);
		this.runTest("Acheter une carte trop chère", TestPlayer::testBuyCardTooExpensive);
		this.runTest("Acheter une carte sans buy", TestPlayer::testBuyCardNoBuys);
	}

	public static void main(String[] args) {
		TestPlayer t = new TestPlayer();
		t.run();
		t.showResults();
	}
}
