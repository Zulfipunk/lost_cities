package player.bot;

import player.Player;
import action.Action;
import action.dump.ActionDump;
import action.dump.ActionDumpType;
import action.pick.ActionPick;
import action.pick.ActionPickType;

public class StupidBotPlayer extends Player {

	@Override
	public Action doActionDump(ActionDump actionDump) {
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

}
