package player.action;

import player.Player;
import card.Card;

public class ActionPick {

	private Player player;
	private Action action;
	private Card card;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public enum Action {
		PICK_FROM_CARD_STACK, PICK_FROM_BOARD
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Action: ");
		str.append(player.getName());
		str.append(" ");
		str.append(action.name());
		if (card != null) {
			str.append(" ");
			str.append(card.toString());
		}
		return str.toString();
	}
}
