package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import player.Player;
import player.bot.StupidBotPlayer;
import ui.ConsoleOutput;
import action.dump.ActionDump;
import action.dump.ActionDumpType;
import action.pick.ActionPick;
import action.pick.ActionPickType;
import card.AvailableCardStack;
import card.Card;
import card.Color;
import card.CardType;

public class Game {

	private final static List<Card> availableCards;
	// private final static int ROUND_COUNT = 3;
	private final static int HAND_COUNT = 8;

	private final AvailableCardStack cardStack;
	private final List<Player> players;
	private Player activePlayer;
	private Player opponentPlayer;

	private final Board board;

	private List<Round> rounds;

	static {

		availableCards = new ArrayList<Card>();
		for (Color color : Color.values()) {
			for (int i = 0; i < 3; i++) {
				Card card = new Card();
				card.setColor(color);
				card.setType(CardType.BET);
				availableCards.add(card);
			}
			for (int i = 1; i < 11; i++) {
				Card card = new Card();
				card.setColor(color);
				card.setType(CardType.POINT);
				card.setValue(i);
				availableCards.add(card);
			}
		}
	}

	public Game() {
		players = new ArrayList<Player>();
		board = new Board();
		cardStack = new AvailableCardStack();
	}

	private void changePlayer() {
		if (activePlayer == null) {
			activePlayer = players.get(0);
			opponentPlayer = players.get(1);
		} else if (activePlayer == players.get(0)) {
			activePlayer = players.get(1);
			opponentPlayer = players.get(0);
		} else if (activePlayer == players.get(1)) {
			activePlayer = players.get(0);
			opponentPlayer = players.get(1);
		}
	}

	private void commitActionDump(ActionDump actionDump) {
		activePlayer.removeCardFromHand(actionDump.getCard());
		if (actionDump.getAction() == ActionDumpType.DUMP_CARD_TO_EXPEDITION) {
			activePlayer.addCardToExpedition(actionDump.getCard());
		} else if (actionDump.getAction() == ActionDumpType.DUMP_CARD_TO_BOARD) {
			board.addCard(actionDump.getCard());
		}

	}

	private Card commitActionPick(ActionPick actionPick) {
		Card pickedCard = null;
		if (actionPick.getActionType() == ActionPickType.PICK_FROM_BOARD) {
			pickedCard = board.pickCard(actionPick.getCard().getColor());
			activePlayer.addCardToHand(pickedCard);
		} else if (actionPick.getActionType() == ActionPickType.PICK_FROM_CARD_STACK) {
			pickedCard = cardStack.pop();
			activePlayer.addCardToHand(pickedCard);
		}
		return pickedCard;
	}

	private void dealCards() {
		Collections.shuffle(cardStack);
		for (Player player : players) {
			for (int i = 0; i < HAND_COUNT; i++) {
				player.addCardToHand(cardStack.pop());
			}
		}
	}

	public void displayGameDebug() {
		StringBuilder str = new StringBuilder();

		str.append(board.toString());
		str.append("\r\n");
		str.append("\r\n");

		str.append(cardStack.toString());
		str.append("\r\n");
		str.append("\r\n");

		str.append(activePlayer.toString());
		str.append("\r\n");
		str.append("\r\n");

		System.out.println(str.toString());
	}

	public void doPlay() {
		Player player1 = new StupidBotPlayer();
		player1.setName("Antoine");
		players.add(player1);
		Player player2 = new StupidBotPlayer();
		player2.setName("Laurence");
		players.add(player2);

		// for (int i = 0; i < ROUND_COUNT; i++) {
		doRound();
		// }
	}

	private void doRound() {
		resetGame();
		dealCards();
		changePlayer();

		while (!cardStack.isEmpty()) {
			if (activePlayer != null) {
				System.out.println();
				System.out.println(activePlayer.getName() + "'s turn...");
			}
			ConsoleOutput.displayGame(this, activePlayer);
			ActionDump actionDump = new ActionDump(this);
			activePlayer.doActionDump(actionDump);
			commitActionDump(actionDump);
			ConsoleOutput.displayGame(this, activePlayer);
			ActionPick actionPick = new ActionPick(this);
			activePlayer.doActionPick(actionPick);
			Card pickedCard = commitActionPick(actionPick);
			ConsoleOutput.displayPickedCard(pickedCard, activePlayer);
			changePlayer();
			// System.out.println();
			// ConsoleHelper.prompt("Press Enter to end turn...");
			// System.out.println();
			ConsoleOutput.displayTurnEnd();
		}

		System.out.println();
		ConsoleOutput.displayGame(this, null);
		System.out.println();
		ConsoleOutput.displayScores(this);
	}

	public Player getActivePlayer() {
		return activePlayer;
	}

	public List<Card> getAvailablecards() {
		return availableCards;
	}

	public Board getBoard() {
		return board;
	}

	public AvailableCardStack getCardStack() {
		return cardStack;
	}

	public Player getOpponentPlayer() {
		return opponentPlayer;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<Round> getRounds() {
		return rounds;
	}

	private void resetGame() {
		cardStack.clear();
		board.reset();
		for (Player player : players) {
			player.reset();
		}
		cardStack.addAll(availableCards);
	}

	public void setActivePlayer(Player activePlayer) {
		this.activePlayer = activePlayer;
	}

	public void setOpponentPlayer(Player opponentPlayer) {
		this.opponentPlayer = opponentPlayer;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}

}
