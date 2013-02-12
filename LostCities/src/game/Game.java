package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import player.HumanPlayer;
import player.Player;
import player.action.ActionDump;
import player.action.ActionDump.Action;
import player.action.ActionPick;
import ui.ConsoleOutput;
import card.AvailableCardStack;
import card.BoardCardStack;
import card.Card;
import card.CardComparator;
import card.Color;
import card.Type;

public class Game {

    private final static List<Card> availableCards;
    private final static int ROUND_COUNT = 3;
    private final static int HAND_COUNT = 8;

    private final AvailableCardStack cardStack;
    private final List<Player> players;
    private Player activePlayer;

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
        Player player1 = new HumanPlayer();
        player1.setName("Antoine");
        players.add(player1);
        Player player2 = new HumanPlayer();
        player2.setName("Laurence");
        players.add(player2);

        for (int i = 0; i < ROUND_COUNT; i++) {
            doRound();
        }
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

    public List<Player> getPlayers() {
        return players;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    private void changePlayer() {
        if (activePlayer == null) {
            activePlayer = players.get(0);
        } else if (activePlayer == players.get(0)) {
            activePlayer = players.get(1);
        } else if (activePlayer == players.get(1)) {
            activePlayer = players.get(0);
        }
    }

    private void commitActionDump(ActionDump actionDump) {
        activePlayer.removeCardFromHand(actionDump.getCard());
        if (actionDump.getAction() == Action.CARD_TO_EXPEDITION) {
            activePlayer.addCardToExpedition(actionDump.getCard());
        } else if (actionDump.getAction() == Action.DUMP_CARD_TO_BOARD) {
            board.addCard(actionDump.getCard());
        }

    }

    private void commitActionPick(ActionPick actionPick) {
        if (actionPick.getAction() == ActionPick.Action.PICK_FROM_BOARD) {
            activePlayer.addCardToHand(board.pickCard(actionPick.getCard()
                .getColor()));
        } else if (actionPick.getAction() == ActionPick.Action.PICK_FROM_CARD_STACK) {
            Card pickedCard = cardStack.pop();
            System.out.println();
            System.out.println("*** " + pickedCard.toString() + " picked ***");

            activePlayer.addCardToHand(pickedCard);
        }

        System.out.println();
        System.console()
            .readLine("Press Enter to en turn...");
        System.out.println();
    }

    private void dealCards() {
        Collections.shuffle(cardStack);
        for (Player player : players) {
            for (int i = 0; i < HAND_COUNT; i++) {
                player.addCardToHand(cardStack.pop());
            }
        }
    }

    private void doRound() {
        resetGame();
        dealCards();
        changePlayer();

        while (!cardStack.isEmpty()) {
            ConsoleOutput.displayGame(this);
            ActionDump actionDump = new ActionDump();
            actionDump.setPlayer(activePlayer);
            boolean validDump = false;
            while (!validDump) {
                activePlayer.doActionDump(actionDump);
                validDump = isActionDumpValid(actionDump);
                if (!validDump) {
                    System.out.println("Illegal action! Do something else!");
                } else {
                    commitActionDump(actionDump);
                }
            }

            ConsoleOutput.displayGame(this);

            ActionPick actionPick = new ActionPick();
            actionPick.setPlayer(activePlayer);
            activePlayer.doActionPick(actionPick, board);
            commitActionPick(actionPick);

            changePlayer();
        }

        System.out.println("!!! Round Over !!!");

        ConsoleOutput.displayGame(this);
    }

    private boolean isActionDumpValid(ActionDump actionDump) {
        if (actionDump.getAction() == Action.CARD_TO_EXPEDITION) {

            BoardCardStack boardCardStack = board.getBoardCardStack(actionDump.getCard()
                .getColor());
            if (!boardCardStack.isEmpty()) {
                Card topBoardStackCard = boardCardStack.peek();
                return new CardComparator().compare(actionDump.getCard(), topBoardStackCard) >= 0;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private void resetGame() {
        cardStack.clear();
        board.reset();
        for (Player player : players) {
            player.reset();
        }
        cardStack.addAll(availableCards);
    }

}
