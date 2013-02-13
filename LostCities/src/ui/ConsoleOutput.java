package ui;

import game.Board;
import game.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import player.HumanPlayer;
import player.Player;
import card.BoardCardStack;
import card.Card;
import card.Color;
import card.ExpeditionCardStack;
import card.CardType;

public class ConsoleOutput {
	public static final String CARD_TOP = ".------.";

	public static final String CARD_BOTTOM = "'------'";

	public static final String CARD_EMPTY_BODY = "|      |";

	public static final Map<Color, String> CARD_BODY;

	static {
		CARD_BODY = new HashMap<Color, String>();
		CARD_BODY.put(Color.BLUE, "|++++++|");
		CARD_BODY.put(Color.GREEN, "|======|");
		CARD_BODY.put(Color.RED, "|######|");
		CARD_BODY.put(Color.WHITE, "|      |");
		CARD_BODY.put(Color.YELLOW, "|^^^^^^|");
	}

	public static final String BOARD_EDGE;

	public static final String SEPARATOR = "********************************************************************************";

	static {
		StringBuffer str = new StringBuffer();
		str.append("+");
		for (int i = 0; i < 46; i++) {
			str.append("-");
		}
		str.append("+");
		BOARD_EDGE = str.toString();
	}

	private static List<String> EMPTY_CARD;

	static {
		EMPTY_CARD = new ArrayList<String>();
		EMPTY_CARD.add(CARD_TOP);
		EMPTY_CARD.add(CARD_EMPTY_BODY);
		EMPTY_CARD.add(CARD_EMPTY_BODY);
		EMPTY_CARD.add(CARD_EMPTY_BODY);
		EMPTY_CARD.add(CARD_EMPTY_BODY);
		EMPTY_CARD.add(CARD_BOTTOM);
	}

	private static List<String> buildBottomCard(Card card) {
		List<String> cardStrings = new ArrayList<String>();
		cardStrings.add(CARD_TOP);
		cardStrings.add(CARD_EMPTY_BODY.replace("      ", String.format("%6s", card.isBet() ? CardType.BET.name() : card.getValue())));
		return cardStrings;
	}

	private static List<String> buildCard(Card card) {
		List<String> cardStrings = new ArrayList<String>();
		cardStrings.add(CARD_TOP);
		cardStrings.add(CARD_EMPTY_BODY.replace("      ", String.format("%6s", card.isBet() ? CardType.BET.name() : card.getValue())));
		cardStrings.add(CARD_BODY.get(card.getColor()));
		cardStrings.add(CARD_BODY.get(card.getColor()));
		cardStrings.add(CARD_EMPTY_BODY.replace("      ", String.format("%-6s", card.getColor())));
		cardStrings.add(CARD_BOTTOM);
		return cardStrings;
	}

	private static List<String> buildExpedition(ExpeditionCardStack expeditionCardStack) {
		List<String> expeditionStrings = new ArrayList<String>();
		for (Card card : expeditionCardStack) {
			if (card == expeditionCardStack.peek()) {
				expeditionStrings.addAll(buildCard(card));
			} else {
				expeditionStrings.addAll(buildBottomCard(card));
			}
		}
		return expeditionStrings;
	}

	private static List<String> buildReverseBottomCard(Card card) {
		List<String> cardStrings = new ArrayList<String>();

		cardStrings.add(CARD_EMPTY_BODY.replace("      ", String.format("%-6s", card.isBet() ? CardType.BET.name() : card.getValue())));
		cardStrings.add(CARD_BOTTOM);
		return cardStrings;
	}

	private static List<String> buildReverseCard(Card card) {
		List<String> cardStrings = new ArrayList<String>();
		cardStrings.add(CARD_TOP);
		cardStrings.add(CARD_EMPTY_BODY.replace("      ", String.format("%6s", card.getColor())));
		cardStrings.add(CARD_BODY.get(card.getColor()));
		cardStrings.add(CARD_BODY.get(card.getColor()));
		cardStrings.add(CARD_EMPTY_BODY.replace("      ", String.format("%-6s", card.isBet() ? CardType.BET.name() : card.getValue())));
		cardStrings.add(CARD_BOTTOM);
		return cardStrings;
	}

	private static List<String> buildReverseExpedition(ExpeditionCardStack expeditionCardStack) {

		List<Card> cardsReversed = new ArrayList<Card>();
		for (int i = expeditionCardStack.size() - 1; i >= 0; i--) {
			cardsReversed.add(expeditionCardStack.get(i));
		}

		List<String> expeditionStrings = new ArrayList<String>();
		for (Card card : cardsReversed) {
			if (card == expeditionCardStack.peek()) {
				expeditionStrings.addAll(buildReverseCard(card));
			} else {
				expeditionStrings.addAll(buildReverseBottomCard(card));
			}
		}
		return expeditionStrings;
	}

	public static void displayActivePlayerExpeditions(Player player) {
		List<String> lines = new ArrayList<String>();

		int rowCount = 0;
		List<List<String>> cardsStrings = new ArrayList<List<String>>();
		for (ExpeditionCardStack expeditionCardStack : player.getExpeditions()) {
			List<String> expeditionStrings = buildExpedition(expeditionCardStack);
			cardsStrings.add(expeditionStrings);
			if (expeditionStrings.size() > rowCount) {
				rowCount = expeditionStrings.size();
			}
		}

		for (int i = 0; i < rowCount; i++) {
			StringBuilder str = new StringBuilder();
			str.append("  ");
			for (List<String> expeditionStrings : cardsStrings) {
				if (i < expeditionStrings.size()) {
					str.append(expeditionStrings.get(i));
				} else {
					str.append("        ");
				}
				str.append(" ");
			}
			lines.add(str.toString());
		}

		for (String line : lines) {
			System.out.println(line);
		}
	}

	public static void displayBoard(Board board) {
		String[] lines = new String[6];

		List<List<String>> cardsStrings = new ArrayList<List<String>>();
		for (BoardCardStack stack : board.getStacks()) {
			if (stack.isEmpty()) {
				cardsStrings.add(EMPTY_CARD);
			} else {
				cardsStrings.add(buildCard(stack.peek()));
			}
		}

		for (int i = 0; i < 6; i++) {
			StringBuilder str = new StringBuilder();
			str.append("| ");
			for (List<String> cardStrings : cardsStrings) {
				str.append(cardStrings.get(i));
				str.append(" ");
			}
			str.append("|");
			lines[i] = str.toString();
		}

		System.out.println(BOARD_EDGE);
		for (String line : lines) {
			System.out.println(line);
		}
		System.out.println(BOARD_EDGE);
	}

	public static void displayGame(Game game, Player player) {
		// if (player == null || player instanceof HumanPlayer) {
		System.out.println();
		displayOpponentPlayerExpeditions(game.getOpponentPlayer());
		displayBoard(game.getBoard());
		displayActivePlayerExpeditions(game.getActivePlayer());
		if (player != null) {
			System.out.println();
			displayHand(player);
		}
		// }
	}

	public static void displayHand(Player player) {
		String[] lines = new String[6];
		List<List<String>> cardsStrings = new ArrayList<List<String>>();
		for (Card card : player.getHand()) {
			cardsStrings.add(buildCard(card));
		}

		for (int i = 0; i < 6; i++) {
			StringBuilder str = new StringBuilder();
			for (List<String> cardStrings : cardsStrings) {
				str.append(cardStrings.get(i));
				str.append(" ");
			}
			lines[i] = str.toString();
		}

		System.out.println("YOUR HAND");
		for (String line : lines) {
			System.out.println(line);
		}
	}

	public static void displayOpponentPlayerExpeditions(Player player) {
		List<String> lines = new ArrayList<String>();

		int rowCount = 0;
		List<List<String>> cardsStrings = new ArrayList<List<String>>();
		for (ExpeditionCardStack expeditionCardStack : player.getExpeditions()) {
			List<String> expeditionStrings = buildReverseExpedition(expeditionCardStack);
			cardsStrings.add(expeditionStrings);
			if (expeditionStrings.size() > rowCount) {
				rowCount = expeditionStrings.size();
			}
		}

		for (int i = 0; i < rowCount; i++) {
			StringBuilder str = new StringBuilder();
			str.append("  ");
			for (List<String> expeditionStrings : cardsStrings) {
				int offset = rowCount - expeditionStrings.size();
				if (offset == 0 && i < expeditionStrings.size()) {
					str.append(expeditionStrings.get(i));
				} else if (i < offset) {
					str.append("        ");
				} else {
					str.append(expeditionStrings.get(i - offset));
				}
				str.append(" ");
			}
			lines.add(str.toString());
		}

		for (String line : lines) {
			System.out.println(line);
		}
	}

	public static void displayPickedCard(Card pickedCard, Player player) {
		if (player instanceof HumanPlayer) {
			System.out.println("*** " + pickedCard.toString() + " picked ***");
		}
	}

	public static void displayScores(Game game) {
		System.out.println();
		System.out.println("Score:");
		for (Player player : game.getPlayers()) {
			System.out.println();
			System.out.println(player.getName());
			int roundScore = 0;
			for (ExpeditionCardStack expeditionCardStack : player.getExpeditions()) {
				int expeditionScore = expeditionCardStack.getScore();
				roundScore += expeditionScore;
				System.out.println(expeditionCardStack.getColor().name() + " " + expeditionScore + " points");
			}
			System.out.println("------------------");
			System.out.println("TOTAL " + roundScore + " points");
		}
	}

	public static void displayTurnEnd() {
		System.out.println();
		System.out.println("TURN END -------------------------------------------------------------");
	}
}
