package action;

import game.Game;
import player.Player;
import card.Card;

public class Action {

	protected Game game;

	protected Card card;

	public Action(Game game) {
		super();
		this.game = game;
	}

	public Card getCard() {
		return card;
	}

	public Game getGame() {
		return game;
	}

	public Player getPlayer() {
		return game.getActivePlayer();
	}

	public void setCard(Card card) {
		this.card = card;
	}

}
