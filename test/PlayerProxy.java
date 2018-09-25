package test;

import java.lang.reflect.Field;
import dominion.*;
import dominion.card.*;

public class PlayerProxy {
	public Player player;
	public CardList hand, discard, draw, inPlay;
	private Field actions_f, buys_f, money_f;

	public PlayerProxy(Player p) {
		this.player = p;
		try {
			// Card lists
			Field hand_f = Player.class.getDeclaredField("hand");
			hand_f.setAccessible(true);
			this.hand = (CardList) hand_f.get(p);
			Field discard_f = Player.class.getDeclaredField("discard");
			discard_f.setAccessible(true);
			this.discard = (CardList) discard_f.get(p);
			Field draw_f = Player.class.getDeclaredField("draw");
			draw_f.setAccessible(true);
			this.draw = (CardList) draw_f.get(p);
			Field inPlay_f = Player.class.getDeclaredField("inPlay");
			inPlay_f.setAccessible(true);
			this.inPlay = (CardList) inPlay_f.get(p);
			// Counters
			this.actions_f = Player.class.getDeclaredField("actions");
			this.actions_f.setAccessible(true);
			this.buys_f = Player.class.getDeclaredField("buys");
			this.buys_f.setAccessible(true);
			this.money_f = Player.class.getDeclaredField("money");
			this.money_f.setAccessible(true);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setActions(int value) {
		try {
			this.actions_f.setInt(this.player, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setBuys(int value) {
		try {
			this.buys_f.setInt(this.player, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setMoney(int value) {
		try {
			this.money_f.setInt(this.player, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public int getActions() {
		return this.player.getActions();
	}

	public int getBuys() {
		return this.player.getBuys();
	}

	public int getMoney() {
		return this.player.getMoney();
	}
	
	public void addTo(CardList list, Class<? extends Card> cardClass, int nbCopies) {
		try {
			for (int i = 0; i < nbCopies; i++) {
				list.add((Card) cardClass.getConstructor().newInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addToHand(Class<? extends Card> cardClass, int nbCopies) {
		this.addTo(this.hand, cardClass, nbCopies);
	}
	
	public void addToDiscard(Class<? extends Card> cardClass, int nbCopies) {
		this.addTo(this.discard, cardClass, nbCopies);
	}

	public void addToDraw(Class<? extends Card> cardClass, int nbCopies) {
		this.addTo(this.draw, cardClass, nbCopies);
	}

	public void addToInPlay(Class<? extends Card> cardClass, int nbCopies) {
		this.addTo(this.inPlay, cardClass, nbCopies);
	}
	
	public void playCard(String cardName) {
		this.player.playCard(cardName);
	}

	public int victoryPoints() {
		return this.player.victoryPoints();
	}

	public void clear() {
		this.setActions(0);
		this.setBuys(0);
		this.setMoney(0);
		this.hand.clear();
		this.discard.clear();
		this.draw.clear();
		this.inPlay.clear();
	}
}