package ui;

import game.Board;
import game.Game;

import java.util.ArrayList;
import java.util.List;

import player.Player;
import card.BoardCardStack;
import card.Card;
import card.ExpeditionCardStack;
import card.Type;

public class ConsoleOutput {
    public static final String CARD_TOP = ".------.";

    public static final String CARD_BOTTOM = "'------'";

    public static final String CARD_BODY = "|      |";

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
        EMPTY_CARD.add(CARD_BODY);
        EMPTY_CARD.add(CARD_BODY);
        EMPTY_CARD.add(CARD_BODY);
        EMPTY_CARD.add(CARD_BODY);
        EMPTY_CARD.add(CARD_BOTTOM);
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
            str.append(" |");
            lines[i] = str.toString();
        }

        System.out.println("BOARD");
        System.out.println(BOARD_EDGE);
        for (String line : lines) {
            System.out.println(line);
        }
        System.out.println(BOARD_EDGE);
    }

    public static void displayGame(Game game) {
        System.out.println();
        System.out.println(SEPARATOR);

        System.out.println();
        displayBoard(game.getBoard());
        System.out.println();
        displayPlayerExpeditions(game.getActivePlayer());
        System.out.println();
        displayHand(game.getActivePlayer());
        System.out.println();
        System.out.println(game.getActivePlayer()
            .getName());
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

    public static void displayOpponentExpeditions() {
        // TODO
    }

    public static void displayPlayerExpeditions(Player player) {
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

        System.out.println("YOUR EXPEDITIONS");
        for (String line : lines) {
            System.out.println(line);
        }
    }

    private static List<String> buildBottomCard(Card card) {
        List<String> cardStrings = new ArrayList<String>();
        cardStrings.add(CARD_TOP);
        cardStrings.add(CARD_BODY.replace("      ", String.format("%6s", card.isBet() ? Type.BET.name() : card.getValue())));
        return cardStrings;
    }

    private static List<String> buildCard(Card card) {
        List<String> cardStrings = new ArrayList<String>();
        cardStrings.add(CARD_TOP);
        cardStrings.add(CARD_BODY.replace("      ", String.format("%6s", card.isBet() ? Type.BET.name() : card.getValue())));
        cardStrings.add(CARD_BODY);
        cardStrings.add(CARD_BODY);
        cardStrings.add(CARD_BODY.replace("      ", String.format("%-6s", card.getColor())));
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
}
