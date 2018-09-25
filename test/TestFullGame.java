package test;

import dominion.card.CardList;

import java.util.ArrayList;
import java.util.List;

public class TestFullGame extends Test {

	private static void testBigMoney(Test t) {
		String[] player_names = new String[]{"Toto", "Titi", "Tutu"};
		List<CardList> kingdomStacks = new ArrayList<>();
		BotGame g = new BotGame(player_names, kingdomStacks);
		GameProxy g_p = new GameProxy(g);
		g.setProxy(g_p);
		t.check(g_p.getSupplyStack("Province").size() == 12);

		g_p.run();
		int score0 = g_p.players[0].victoryPoints();
		int score1 = g_p.players[1].victoryPoints();
		int score2 = g_p.players[2].victoryPoints();
		t.check(score0 % 6 == 3);
		t.check(score1 % 6 == 3);
		t.check(score2 % 6 == 3);
		t.check(score0 + score1 + score2 == 9 + 6 * 12);
	}


	public void run() {
		this.runTest("Partie complète (Big Money, 3 joueurs)", TestFullGame::testBigMoney);
	}

	public static void main(String[] args) {
		TestFullGame t = new TestFullGame();
		t.run();
		t.showResults();
	}
}