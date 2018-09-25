package test;

import dominion.card.base.*;
import dominion.card.common.*;

public class TestCards3 extends Test {

	private static void testMoatReaction(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		PlayerProxy p2 = new PlayerProxy(g.getPlayer(2));
		p0.addToHand(Witch.class, 1);
		p1.addToHand(Copper.class, 3);
		p1.addToHand(Moat.class, 1);
		p2.addToHand(Estate.class, 3);
		p2.addToHand(Moat.class, 1);
		g.setInput("y", "n");
		p0.playCard("Witch");
		t.check(p1.discard.size() == 0);
		t.check(hasCards(p2.discard, "Curse"));
	}

	private static void testSpy(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		PlayerProxy p2 = new PlayerProxy(g.getPlayer(2));
		p0.clear();
		p0.addToHand(Spy.class, 1);
		p0.addToDraw(Copper.class, 2);
		p1.clear();
		p1.addToDraw(Silver.class, 1);
		p2.clear();
		p2.addToDiscard(Estate.class, 1);
		g.setInput("n", "y", "n"); // p0 ne défausse pas, p1 défausse, p2 ne défausse pas
		p0.playCard("Spy");
		t.check(hasCards(p0.hand, "Copper"));
		t.check(hasCards(p0.draw, "Copper"));
		t.check(p0.getActions() == 1);
		t.check(p1.draw.size() == 0);
		t.check(hasCards(p1.discard, "Silver"));
		t.check(hasCards(p2.draw, "Estate"));
	}

	private static void testThief(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		PlayerProxy p1 = new PlayerProxy(g.getPlayer(1));
		PlayerProxy p2 = new PlayerProxy(g.getPlayer(2));
		p0.clear();
		p0.addToHand(Thief.class, 1);
		p1.clear();
		p1.addToDraw(Copper.class, 1);
		p1.addToDraw(Estate.class, 1);
		p2.clear();
		p2.addToDraw(Silver.class, 1);
		p2.addToDraw(Gold.class, 1);
		g.setInput("Gold", "Gold", "");
		p0.playCard("Thief");
		t.check(hasCards(p0.discard, "Gold"));
		t.check(hasCards(p1.discard, "Estate"));
		t.check(p1.draw.size() == 0);
		t.check(hasCards(p2.discard, "Silver"));
		t.check(p2.draw.size() == 0);
	}

	private static void testThroneRoom(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		p0.clear();
		p0.addToHand(ThroneRoom.class, 1);
		p0.addToHand(Village.class, 1);
		p0.addToHand(Woodcutter.class, 1);
		p0.addToDraw(Copper.class, 4);
		g.setInput("Village");
		p0.playCard("Throne Room");
		t.check(hasCards(p0.hand, "Woodcutter", "Copper", "Copper"));
		t.check(hasCards(p0.inPlay, "Throne Room", "Village"));
		t.check(p0.getActions() == 4);
	}

	private static void testLibrary(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		p0.clear();
		p0.addToHand(Library.class, 3);
		p0.addToHand(Copper.class, 2);
		p0.addToDraw(Gold.class, 1);
		p0.addToDraw(Village.class, 2);
		p0.addToDraw(Silver.class, 1);
		g.setInput("y", "n");
		p0.playCard("Library");
		t.check(hasCards(p0.hand, "Copper", "Copper", "Library", "Library", "Silver", "Village", "Gold"));
		t.check(hasCards(p0.discard, "Village"));
	}

	private static void testAdventurer(Test t) {
		GameProxy g = new GameProxy(IOGame.minimal());
		PlayerProxy p0 = new PlayerProxy(g.getPlayer(0));
		p0.clear();
		p0.addToHand(Adventurer.class, 1);
		p0.addToDraw(Estate.class, 2);
		p0.addToDraw(Silver.class, 2);
		p0.addToDraw(Estate.class, 2);
		p0.playCard("Adventurer");
		t.check(hasCards(p0.hand, "Silver", "Silver"));
		t.check(hasCards(p0.discard, "Estate", "Estate"));
		t.check(hasCards(p0.draw, "Estate", "Estate"));
	}

	public void run() {
		this.runTest("Moat (reaction)", TestCards3::testMoatReaction);
		this.runTest("Spy", TestCards3::testSpy);
		this.runTest("Thief", TestCards3::testThief);
		this.runTest("Throne Room", TestCards3::testThroneRoom);
		this.runTest("Library", TestCards3::testLibrary);
		this.runTest("Adventurer", TestCards3::testAdventurer);
	}

	public static void main(String[] args) {
		TestCards3 t = new TestCards3();
		t.run();
		t.showResults();
	}
}