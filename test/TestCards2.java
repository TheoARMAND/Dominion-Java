package test;

import dominion.card.base.*;
import dominion.card.common.*;

public class TestCards2 extends Test {

	private static void testCellar(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Cellar.class, 1);
		p1.addToHand(Estate.class, 3);
		p1.addToDraw(Copper.class, 2);
		g.setInput("Estate", "Duchy", "Estate", "");
		p1.playCard("Cellar");
		t.check(p1.getActions() == 1);
		t.check(hasCards(p1.hand, "Copper", "Copper", "Estate"));
		t.check(hasCards(p1.discard, "Estate", "Estate"));
	}

	private static void testChapel(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Chapel.class, 1);
		p1.addToHand(Estate.class, 1);
		p1.addToHand(Copper.class, 2);
		g.setInput("Estate", "Estate", "Copper", "");
		p1.playCard("Chapel");
		t.check(hasCards(p1.hand, "Copper"));
		t.check(p1.discard.isEmpty());
	}

	private static void testChancellorNo(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Chancellor.class, 1);
		p1.addToDraw(Estate.class, 2);
		g.setInput("n");
		p1.playCard("Chancellor");
		t.check(p1.getMoney() == 2);
		t.check(hasCards(p1.draw, "Estate", "Estate"));
		t.check(p1.discard.size() == 0);
	}

	private static void testChancellorYes(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Chancellor.class, 1);
		p1.addToDraw(Estate.class, 2);
		g.setInput("y");
		p1.playCard("Chancellor");
		t.check(p1.getMoney() == 2);
		t.check(hasCards(p1.discard, "Estate", "Estate"));
		t.check(p1.draw.size() == 0);
	}

	private static void testWorkshop(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Workshop.class, 1);
		g.setInput("Gold", "Silver");
		p1.playCard("Workshop");
		t.check(hasCards(p1.discard, "Silver"));
	}

	private static void testBureaucrat(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		PlayerProxy p2 = new PlayerProxy(g.getPlayer(2));
		p0.clear();
		p0.addToHand(Estate.class, 1);
		p0.addToHand(Duchy.class, 1);
		p1.clear();
		p1.addToHand(Bureaucrat.class, 1);
		p2.clear();
		p2.addToHand(Gold.class, 2);
		p2.addToHand(Copper.class, 2);
		g.setInput("Province", "Duchy", "");
		p1.playCard("Bureaucrat");
		t.check(hasCards(p0.hand, "Estate"));
		t.check(hasCards(p0.draw, "Duchy"));
		t.check(p1.discard.isEmpty());
		t.check(hasCards(p1.draw, "Silver"));
		t.check(p2.draw.isEmpty());
	}

	private static void testFeast(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Feast.class, 1);
		g.setInput("Gold", "Duchy");
		p1.playCard("Feast");
		t.check(p1.draw.isEmpty());
		t.check(p1.hand.isEmpty());
		t.check(hasCards(p1.discard, "Duchy"));
	}

	private static void testMilitia(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		PlayerProxy p2 = new PlayerProxy(g.getPlayer(2));
		p1.clear();
		p1.addToHand(Militia.class, 1);
		p2.clear();
		p2.addToHand(Silver.class, 2); // 2 cartes en main, non affecté par Militia
		p0.clear();
		p0.addToHand(Estate.class, 2);
		p0.addToHand(Copper.class, 4); // 6 cartes en main, doit en défausser 3
		g.setInput("Estate", "Estate", "Estate", "Copper");
		// le 3e Estate n'est pas valide, le Copper devrait être choisi automatiquement
		p1.playCard("Militia");
		t.check(p1.getMoney() == 2);
		t.check(hasCards(p2.hand, "Silver", "Silver"));
		t.check(p2.discard.size() == 0);
		t.check(hasCards(p0.hand, "Copper", "Copper", "Copper"));
		t.check(hasCards(p0.discard, "Estate", "Estate", "Copper"));
	}

	private static void testMoneylenderWithCopper(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Moneylender.class, 1);
		p1.addToHand(Silver.class, 2);
		p1.addToHand(Copper.class, 1);
		p1.playCard("Moneylender");
		t.check(p1.getMoney() == 3);
		t.check(hasCards(p1.hand, "Silver", "Silver"));
	}

	private static void testMoneylenderNoCopper(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Moneylender.class, 1);
		p1.addToHand(Silver.class, 2);
		p1.playCard("Moneylender");
		t.check(p1.getMoney() == 0);
		t.check(hasCards(p1.hand, "Silver", "Silver"));
	}

	private static void testRemodel(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		p1.clear();
		p1.addToHand(Remodel.class, 1);
		p1.addToHand(Silver.class, 2);
		p1.addToHand(Estate.class, 2);
		g.setInput("Silver", "Province", "Duchy");
		p1.playCard("Remodel");
		t.check(hasCards(p1.hand, "Silver", "Estate", "Estate"));
		t.check(hasCards(p1.discard, "Duchy"));
		t.check(g.getSupplyStack("Duchy").size() == 11);
	}

	private static void testCouncilRoom(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		PlayerProxy p2 = new PlayerProxy(g.getPlayer(2));
		p0.clear();
		p0.addToDraw(Copper.class, 3);
		p1.clear();
		p1.addToHand(CouncilRoom.class, 1);
		p1.addToDraw(Copper.class, 2);
		p1.addToDiscard(Estate.class, 4);
		p2.clear();
		p2.addToDiscard(Estate.class, 1);
		p1.playCard("Council Room");
		t.check(hasCards(p1.hand, "Copper", "Copper", "Estate", "Estate"));
		t.check(hasCards(p2.hand, "Estate"));
		t.check(hasCards(p0.hand, "Copper"));
	}

	private static void testMine(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		p0.clear();
		p0.addToHand(Mine.class, 1);
		p0.addToHand(Silver.class, 1);
		p0.addToHand(Copper.class, 1);
		g.setInput("Copper", "Gold", "Estate", "Silver");
		p0.playCard("Mine");
		t.check(hasCards(p0.hand, "Silver", "Silver"));
		t.check(p0.discard.size() == 0);
	}


	private static void testWitch(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		PlayerProxy p2 = new PlayerProxy(g.getPlayer(2));
		p0.clear();
		p1.clear();
		p1.addToHand(Witch.class, 1);
		p1.addToDiscard(Copper.class, 3);
		p2.clear();
		p1.playCard("Witch");
		t.check(hasCards(p1.hand, "Copper", "Copper"));
		t.check(hasCards(p1.draw, "Copper"));
		t.check(hasCards(p0.discard, "Curse"));
		t.check(hasCards(p2.discard, "Curse"));
		t.check(g.getSupplyStack("Curse").size() == 18);
	}

	public void run() {
		this.runTest("Cellar", TestCards2::testCellar);
		this.runTest("Chapel", TestCards2::testChapel);
		this.runTest("Chancellor (non)", TestCards2::testChancellorNo);
		this.runTest("Chancellor (oui)", TestCards2::testChancellorYes);
		this.runTest("Workshop", TestCards2::testWorkshop);
		this.runTest("Bureaucrat", TestCards2::testBureaucrat);
		this.runTest("Feast", TestCards2::testFeast);
		this.runTest("Militia", TestCards2::testMilitia);
		this.runTest("Moneylender (avec Copper)", TestCards2::testMoneylenderWithCopper);
		this.runTest("Moneylender (sans Copper)", TestCards2::testMoneylenderNoCopper);
		this.runTest("Remodel", TestCards2::testRemodel);
		this.runTest("Council Room", TestCards2::testCouncilRoom);
		this.runTest("Mine", TestCards2::testMine);
		this.runTest("Witch", TestCards2::testWitch);
	}

	public static void main(String[] args) {
		TestCards2 t = new TestCards2();
		t.run();
		t.showResults();
	}
}