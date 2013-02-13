package action.dump;

import game.Game;

import java.util.ArrayList;
import java.util.List;

import action.Action;
import card.Card;
import card.ExpeditionCardStack;

public class ActionDump extends Action {

	private ActionDumpType action;

	public ActionDump(Game game) {
		super(game);
	}

	public ActionDumpType getAction() {
		return action;
	}

	public List<Card> getPossibleExpeditionCards() {
		List<Card> possibleExpeditionCards = new ArrayList<Card>();
		for (Card card : getPlayer().getHand()) {
			ExpeditionCardStack expeditionCardStack = getPlayer().getExpeditionStack(card.getColor());
			if (expeditionCardStack.isEmpty()) {
				possibleExpeditionCards.add(card);
			} else {
				if (card.isHigherEquals(expeditionCardStack.peek())) {
					possibleExpeditionCards.add(card);
				}
			}
		}
		return possibleExpeditionCards;
	}

	public void setAction(ActionDumpType action) {
		this.action = action;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Action: ");
		str.append(getPlayer().getName());
		str.append(" ");
		str.append(action.name());
		str.append(" ");
		str.append(card.toString());
		return str.toString();
	}
}
