package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import player.BotPlayer;
import player.Player;
import player.action.ActionDump;
import player.action.ActionDump.ActionDumpType;
import player.action.ActionPick;
import ui.ConsoleOutput;
import card.AvailableCardStack;
import card.BoardCardStack;
import card.Card;
import card.Color;
import card.ExpeditionCardStack;
import card.Type;

public class Game {

	private final static List<Card> availableCards;
	private final static int ROUND_COUNT = 3;
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
				card.setType(Type.BET);
				availableCards.add(card);
			}
			for (int i = 1; i < 11; i++) {
				Card card = new Card();
				card.setColor(color);
				card.setType(Type.POINT);
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
		if (actionPick.getAction() == ActionPick.ActionPickType.PICK_FROM_BOARD) {
			pickedCard = board.pickCard(actionPick.getCard().getColor());
			activePlayer.addCardToHand(pickedCard);
		} else if (actionPick.getAction() == ActionPick.ActionPickType.PICK_FROM_CARD_STACK) {
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
		Player player1 = new BotPlayer();
		player1.setName("Antoine");
		players.add(player1);
		Player player2 = new BotPlayer();
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
			ActionDump actionDump = new ActionDump();
			actionDump.setPlayer(activePlayer);
			actionDump.setAvailableCards(getPossibleExpeditionCards());
			activePlayer.doActionDump(actionDump);
			commitActionDump(actionDump);
			ConsoleOutput.displayGame(this, activePlayer);
			ActionPick actionPick = new ActionPick();
			actionPick.setPlayer(activePlayer);
			actionPick.setAvailableCards(getAvailableBoardCards());
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

	private List<Card> getAvailableBoardCards() {
		List<Card> availableBoardCards = new ArrayList<Card>();
		for (BoardCardStack boardCardStack : board.getStacks()) {
			if (!boardCardStack.isEmpty()) {
				availableBoardCards.add(boardCardStack.peek());
			}
		}
		return availableBoardCards;
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

	private List<Card> getPossibleExpeditionCards() {
		List<Card> possibleExpeditionCards = new ArrayList<Card>();
		for (Card card : activePlayer.getHand()) {
			ExpeditionCardStack expeditionCardStack = activePlayer.getExpeditionStack(card.getColor());
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
