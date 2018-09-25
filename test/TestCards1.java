package test;

import dominion.card.base.*;
import dominion.card.common.*;

public class TestCards1 extends Test {

	private static void testMoatAction(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Moat.class, 1);
		p1.addToDraw(Copper.class, 3);
		p1.playCard("Moat");
		t.check(hasCards(p1.hand, "Copper", "Copper"));
		t.check(hasCards(p1.draw, "Copper"));
	}

	private static void testVillage(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Village.class, 1);
		p1.addToHand(Copper.class, 1);
		p1.addToDraw(Estate.class, 2);
		p1.playCard("Village");
		t.check(p1.getActions() == 2);
		t.check(hasCards(p1.hand, "Copper", "Estate"));
	}

	private static void testWoodcutter(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Woodcutter.class, 1);
		p1.playCard("Woodcutter");
		t.check(p1.getBuys() == 1);
		t.check(p1.getMoney() == 2);
	}

	private static void testGardens(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Gardens.class, 1);
		p1.addToDiscard(Silver.class, 8);
		t.check(p1.victoryPoints() == 0); // 9 cartes
		p1.addToDraw(Copper.class, 1);
		t.check(p1.victoryPoints() == 1); // 10 cartes
		p1.addToDraw(Gardens.class, 4);
		p1.addToDraw(Copper.class, 26);
		t.check(p1.victoryPoints() == 20); // 5 Gardens, 40 cartes
	}

	private static void testSmithy(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Smithy.class, 1);
		p1.addToHand(Copper.class, 1);
		p1.addToDraw(Estate.class, 4);
		p1.playCard("Smithy");
		t.check(hasCards(p1.hand, "Copper", "Estate", "Estate", "Estate"));
		t.check(p1.discard.size() == 0);
		t.check(p1.draw.size() == 1);
	}

	private static void testFestival(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		p0.clear();
		p0.addToHand(Festival.class, 1);
		p0.playCard("Festival");
		t.check(p0.getBuys() == 1);
		t.check(p0.getMoney() == 2);
		t.check(p0.getActions() == 2);
		t.check(p0.hand.size() == 0);
	}

	private static void testLaboratory(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		p0.clear();
		p0.addToHand(Laboratory.class, 1);
		p0.addToDiscard(Copper.class, 2);
		p0.playCard("Laboratory");
		t.check(hasCards(p0.hand, "Copper", "Copper"));
		t.check(p0.getActions() == 1);
	}

	private static void testMarket(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		p0.clear();
		p0.addToHand(Market.class, 1);
		p0.addToDiscard(Duchy.class, 1);
		p0.playCard("Market");
		t.check(hasCards(p0.hand, "Duchy"));
		t.check(p0.getActions() == 1);
		t.check(p0.getBuys() == 1);
		t.check(p0.getMoney() == 1);
	}

	public void run() {
		this.runTest("Moat (action)", TestCards1::testMoatAction);
		this.runTest("Village", TestCards1::testVillage);
		this.runTest("Woodcutter", TestCards1::testWoodcutter);
		this.runTest("Gardens", TestCards1::testGardens);
		this.runTest("Smithy", TestCards1::testSmithy);
		this.runTest("Festival", TestCards1::testFestival);
		this.runTest("Laboratory", TestCards1::testLaboratory);
		this.runTest("Market", TestCards1::testMarket);
	}

	public static void main(String[] args) {
		TestCards1 t = new TestCards1();
		t.run();
		t.showResults();
	}
}