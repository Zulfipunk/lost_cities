package player.bot;

import game.Game;

import java.util.HashMap;
import java.util.Map;

import player.Player;
import action.Action;
import action.dump.ActionDump;
import action.dump.ActionDumpType;
import action.pick.ActionPick;
import action.pick.ActionPickType;
import card.Card;
import card.CardType;

public class AverageBotPlayer extends Player {

	@Override
	public Action doActionDump(ActionDump actionDump) {
		Map<Card, Integer> cardActionRates = new HashMap<Card, Integer>();
		for (Card card : actionDump.getPossibleExpeditionCards()) {
			cardActionRates.put(card, getCardRate(card, actionDump.getGame()));
		}

		actionDump.setAction(ActionDumpType.DUMP_CARD_TO_EXPEDITION);
		try {
			actionDump.setCard(actionDump.getPossibleExpeditionCards().get(0));
		} catch (IndexOutOfBoundsException e) {
			actionDump.setAction(ActionDumpType.DUMP_CARD_TO_BOARD);
			actionDump.setCard(actionDump.getPlayer().getHand().get(0));
		}
		return actionDump;
	}

	@Override
	public ActionPick doActionPick(ActionPick actionPick) {
		actionPick.setActionType(ActionPickType.PICK_FROM_CARD_STACK);
		return actionPick;
	}

	private int getCardRate(Card card, Game game) {
		int rate = 0;

		if (hasExpedion(card.getColor())) {
			Card topExpeditionCard = getExpeditionStack(card.getColor()).peek();

			// TODO Ferme une expédition? (si dernière carte excellent sinon...)

			if (topExpeditionCard.getType() == CardType.BET) {
				if (card.getType() == CardType.BET) {
					rate += Integer.MAX_VALUE;
				} else if (card.getValue().equals(2)) {
					rate += Integer.MAX_VALUE;
				} else {
					rate += ((4 - card.getValue().intValue()) * 5);
				}
			} else {
				if (card.getType() != CardType.BET) {
					rate += ((3 - (card.getValue().intValue() - topExpeditionCard.getValue())) * 5);
				}
			}

		} else {
			rate += (-10);
		}

		// Série en main

		// Points potentiels? (Points manqués, Expédition adverse)

		// Other player already has expedition

		// Beaucoup de cartes (expedition + main)

		return rate;
	}
}
