package test;

import dominion.Game;
import dominion.Player;
import dominion.card.CardList;

import java.util.List;

public class TestGame extends Test {

	private static void testNbPlayers(Test t) {
		Game g = IOGame.minimal();
		t.check(g.numberOfPlayers() == 3);
	}

	private static void testGetPlayer(Test t) {
		Game g = IOGame.minimal();
		Player p = g.getPlayer(1);
		t.check(p.getName().equals("Titi"));
	}

	private static void testOtherPlayersSize(Test t) {
		Game g = IOGame.minimal();
		Player p = g.getPlayer(1);
		t.check(g.otherPlayers(p).size() == 2);
	}

	private static void testOtherPlayersNames(Test t) {
		Game g = IOGame.minimal();
		Player p = g.getPlayer(1);
		t.check(g.otherPlayers(p).get(0).getName().equals("Tutu"));
		t.check(g.otherPlayers(p).get(1).getName().equals("Toto"));
	}

	private static void testNbCardsInSupplies(Test t) {
		Game g = IOGame.minimal();
		GameProxy g_p = new GameProxy(g);
		List<CardList> supplies = g_p.supplyStacks;
		t.check(g_p.getSupplyStack("Estate").size() == 12);
		t.check(g_p.getSupplyStack("Duchy").size() == 12);
		t.check(g_p.getSupplyStack("Province").size() == 12);
		t.check(g_p.getSupplyStack("Curse").size() == 20);
		t.check(g_p.getSupplyStack("Silver").size() == 40);
		t.check(g_p.getSupplyStack("Gold").size() == 30);
	}

	private static void testGetFromSupply(Test t) {
		Game g = IOGame.minimal();
		Player p = g.getPlayer(0);
		GameProxy g_p = new GameProxy(g);
		PlayerProxy p_p = new PlayerProxy(p);
		t.check(g.getFromSupply("Gold").getName().equals("Gold"));
	}

	private static void testGetNotInSupply(Test t) {
		Game g = IOGame.minimal();
		Player p = g.getPlayer(0);
		GameProxy g_p = new GameProxy(g);
		PlayerProxy p_p = new PlayerProxy(p);
		t.check(g.getFromSupply("Blop") == null);
	}

	private static void testRemoveFromSupply(Test t) {
		Game g = IOGame.minimal();
		GameProxy g_p = new GameProxy(g);
		t.check(g.removeFromSupply("Duchy").getName().equals("Duchy"));
		CardList supp = g_p.getSupplyStack("Duchy");
		t.check(supp != null && supp.size() == 11);
	}

	private static void testRemoveNotInSupply(Test t) {
		Game g = IOGame.minimal();
		GameProxy g_p = new GameProxy(g);
		t.check(g.removeFromSupply("Blip") == null);
	}

	private static void testNbAvailableSupplies(Test t) {
		Game g = IOGame.minimal();
		CardList availableSupplies = g.availableSupplyCards();
		t.check(availableSupplies.size() == 7);
		for (int i=0; i<12; i++) {
			g.removeFromSupply("Estate");
		}
		t.check(g.availableSupplyCards().size() == 6);
	}

	private static void testEndGame3Stack(Test t) {
		Game g = IOGame.minimal();
		t.check(!g.isFinished());
		for (int i=0; i<12; i++) {
			g.removeFromSupply("Estate");
		}
		t.check(!g.isFinished());
		for (int i=0; i<20; i++) {
			g.removeFromSupply("Curse");
		}
		t.check(!g.isFinished());
		for (int i=0; i<30; i++) {
			g.removeFromSupply("Gold");
		}
		t.check(g.isFinished());
	}

	private static void testEndGameProvince(Test t) {
		Game g = IOGame.minimal();
		for (int i=0; i<12; i++) {
			g.removeFromSupply("Province");
		}
		t.check(g.isFinished());
	}

	public void run() {
		this.runTest("Nombre de joueurs", TestGame::testNbPlayers);
		this.runTest("Accès aux joueurs", TestGame::testGetPlayer);
		this.runTest("Autres joueurs (nombre)", TestGame::testOtherPlayersSize);
		this.runTest("Autres joueurs (noms)", TestGame::testOtherPlayersNames);
		this.runTest("Nombre de cartes dans les piles de réserve", TestGame::testNbCardsInSupplies);
		this.runTest("Nombre de piles de réserve disponibles", TestGame::testNbAvailableSupplies);
		this.runTest("Trouver une carte de la réserve", TestGame::testGetFromSupply);
		this.runTest("Trouver une carte absente de la réserve", TestGame::testGetNotInSupply);
		this.runTest("Retirer une carte de la réserve", TestGame::testRemoveFromSupply);
		this.runTest("Retirer une carte absente de la réserve", TestGame::testRemoveNotInSupply);
		this.runTest("Lister les piles de réserve non vides", TestGame::testNbAvailableSupplies);
		this.runTest("Fin de partie (3 piles vides)", TestGame::testEndGame3Stack);
		this.runTest("Fin de partie (Provinces)", TestGame::testEndGameProvince);
	}

	public static void main(String[] args) {
		TestGame t = new TestGame();
		t.run();
		t.showResults();
	}
}