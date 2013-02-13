package action.pick;

import game.Game;

import java.util.ArrayList;
import java.util.List;

import action.Action;
import card.BoardCardStack;
import card.Card;

public class ActionPick extends Action {

	private ActionPickType actionType;

	public ActionPick(Game game) {
		super(game);
	}

	public ActionPickType getActionType() {
		return actionType;
	}

	public List<Card> getAvailableBoardCards() {
		List<Card> availableBoardCards = new ArrayList<Card>();
		for (BoardCardStack boardCardStack : game.getBoard().getStacks()) {
			if (!boardCardStack.isEmpty()) {
				availableBoardCards.add(boardCardStack.peek());
			}
		}
		return availableBoardCards;
	}

	public void setActionType(ActionPickType actionType) {
		this.actionType = actionType;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Action: ");
		str.append(getPlayer().getName());
		str.append(" ");
		str.append(actionType.name());
		if (card != null) {
			str.append(" ");
			str.append(card.toString());
		}
		return str.toString();
	}
}
