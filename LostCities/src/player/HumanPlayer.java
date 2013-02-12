package player;


import game.Board;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import player.action.ActionDump;
import player.action.ActionPick;
import card.BoardCardStack;
import card.Card;

public class HumanPlayer extends Player {

	private final static String PROMPT_DUMP;
	private final static String PROMPT_PICK;

	static {
		StringBuilder str = new StringBuilder();
		str.append("Select action:");
		str.append("\r\n");
		str.append("1. Build expedition");
		str.append("\r\n");
		str.append("2. Dump card to board");
		str.append("\r\n");
		PROMPT_DUMP = str.toString();
		
		str = new StringBuilder();
		str.append("Select action:");
		str.append("\r\n");
		str.append("1. Pick from main card stack");
		str.append("\r\n");
		str.append("2. Pick from board stacks");
		str.append("\r\n");
		PROMPT_PICK = str.toString();
	}

	@Override
	public ActionDump doActionDump(ActionDump actionDump) {
		while (true) {
			try {
				actionDump.setAction(selectActionDump());
				actionDump.setCard(selectCardDump());
				return actionDump;
			} catch (Exception e) {
				// Cancel
			}
			
		}
	}

	private player.action.ActionDump.Action selectActionDump() {
		Console console = System.console();
		while (true) {
			String strAnswer = console.readLine(PROMPT_DUMP);
			try {
				return player.action.ActionDump.Action.values()[Integer.valueOf(strAnswer) - 1];
			} catch (NumberFormatException e) {
				System.out.println("Wrong choice. Please try again!");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Wrong choice. Please try again!");
			}

		}
	}

	private Card selectCardDump() throws Exception {
		Console console = System.console();
		while (true) {
			StringBuilder str = new StringBuilder();
			str.append("Select card:");
			str.append("\r\n");
			int i = 1;
			for (Card card : hand) {
				str.append(i++);
				str.append(". ");
				str.append(card.toString());
				str.append("\r\n");
			}
			str.append("0. Cancel");
			str.append("\r\n");
			String strAnswer = console.readLine(str.toString());
			try {
				Integer intAnswer = Integer.valueOf(strAnswer);
				if (intAnswer == 0) {
					throw new Exception("cancel");
				} else {
					return hand.get(intAnswer - 1);
				}
			} catch (NumberFormatException e) {
				System.out.println("Wrong choice. Please try again!");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Wrong choice. Please try again!");
			}

		}
	}
	
	@Override
	public ActionPick doActionPick(ActionPick actionPick, Board board) {
		while (true) {
			try {
				actionPick.setAction(selectActionPick());
				if (actionPick.getAction() == ActionPick.Action.PICK_FROM_BOARD) {
					actionPick.setCard(selectCardPick(board));
				}
				return actionPick;
			} catch (Exception e) {
				// Cancel
			}
		}
	}

	private player.action.ActionPick.Action selectActionPick() {
		Console console = System.console();
		while (true) {
			String strAnswer = console.readLine(PROMPT_PICK);
			try {
				return player.action.ActionPick.Action.values()[Integer.valueOf(strAnswer) - 1];
			} catch (NumberFormatException e) {
				System.out.println("Wrong choice. Please try again!");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Wrong choice. Please try again!");
			}

		}
	}

	private Card selectCardPick(Board board) throws Exception {
		Console console = System.console();
		while (true) {
			StringBuilder str = new StringBuilder();
			str.append("Select card:");
			str.append("\r\n");
			int i = 1;
			List<Card> possibleCards = new ArrayList<Card>();
			for (BoardCardStack stack : board.getStacks()) {
				if (!stack.isEmpty()) {
					str.append(i++);
					str.append(". ");
					str.append(stack.peek().toString());
					possibleCards.add(stack.peek());
					str.append("\r\n");
				}
				
			}
			str.append("0. Cancel");
			str.append("\r\n");
			String strAnswer = console.readLine(str.toString());
			try {
				Integer intAnswer = Integer.valueOf(strAnswer);
				if (intAnswer == 0) {
					throw new Exception("cancel");
				} else {
					return possibleCards.get(intAnswer - 1);
				}
			} catch (NumberFormatException e) {
				System.out.println("Wrong choice. Please try again!");
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Wrong choice. Please try again!");
			}

		}
	}
}
