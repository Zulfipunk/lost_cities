package player;

import player.action.ActionDump;
import player.action.ActionPick;

public class BotPlayer extends Player {

	@Override
	public ActionDump doActionDump(ActionDump actionDump) {
		actionDump.setAction(ActionDump.ActionDumpType.DUMP_CARD_TO_EXPEDITION);
		try {
			actionDump.setCard(actionDump.getAvailableCards().get(0));
		} catch (IndexOutOfBoundsException e) {
			actionDump.setAction(ActionDump.ActionDumpType.DUMP_CARD_TO_BOARD);
			actionDump.setCard(actionDump.getPlayer().getHand().get(0));
		}
		return actionDump;
	}

	@Override
	public ActionPick doActionPick(ActionPick actionPick) {
		actionPick.setAction(ActionPick.ActionPickType.PICK_FROM_CARD_STACK);
		return actionPick;
	}

}
