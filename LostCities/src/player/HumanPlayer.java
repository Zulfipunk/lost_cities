package player;

import player.action.ActionDump;
import player.action.ActionPick;
import ui.ConsoleHelper;
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
                actionDump.setCard(selectCardDump(actionDump));
                return actionDump;
            } catch (Exception e) {
                // Cancel
            }

        }
    }

    @Override
    public ActionPick doActionPick(ActionPick actionPick) {
        while (true) {
            try {
                actionPick.setAction(selectActionPick());
                if (actionPick.getAction() == ActionPick.Action.PICK_FROM_BOARD) {
                    actionPick.setCard(selectCardPick(actionPick));
                }
                return actionPick;
            } catch (Exception e) {
                // Cancel
            }
        }
    }

    private player.action.ActionDump.Action selectActionDump() {
        while (true) {
            String strAnswer = ConsoleHelper.prompt(PROMPT_DUMP);
            try {
                return player.action.ActionDump.Action.values()[Integer.valueOf(strAnswer) - 1];
            } catch (NumberFormatException e) {
                System.out.println("Wrong choice. Please try again!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Wrong choice. Please try again!");
            }

        }
    }

    private player.action.ActionPick.Action selectActionPick() {
        while (true) {
            String strAnswer = ConsoleHelper.prompt(PROMPT_PICK);
            try {
                return player.action.ActionPick.Action.values()[Integer.valueOf(strAnswer) - 1];
            } catch (NumberFormatException e) {
                System.out.println("Wrong choice. Please try again!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Wrong choice. Please try again!");
            }

        }
    }

    private Card selectCardDump(ActionDump actionDump) throws Exception {
        while (true) {
            StringBuilder str = new StringBuilder();
            str.append("Select card:");
            str.append("\r\n");
            int i = 1;
            for (Card card : actionDump.getAvailableCards()) {
                str.append(i++);
                str.append(". ");
                str.append(card.toString());
                str.append("\r\n");
            }
            str.append("0. Cancel");
            str.append("\r\n");
            String strAnswer = ConsoleHelper.prompt(str.toString());
            try {
                Integer intAnswer = Integer.valueOf(strAnswer);
                if (intAnswer == 0) {
                    throw new Exception("cancel");
                } else {
                    return actionDump.getAvailableCards()
                        .get(intAnswer - 1);
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong choice. Please try again!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Wrong choice. Please try again!");
            }

        }
    }

    private Card selectCardPick(ActionPick actionPick) throws Exception {
        while (true) {
            StringBuilder str = new StringBuilder();
            str.append("Select card:");
            str.append("\r\n");
            int i = 1;
            for (Card card : actionPick.getAvailableCards()) {
                str.append(i++);
                str.append(". ");
                str.append(card.toString());
                str.append("\r\n");
            }
            str.append("0. Cancel");
            str.append("\r\n");
            String strAnswer = ConsoleHelper.prompt(str.toString());
            try {
                Integer intAnswer = Integer.valueOf(strAnswer);
                if (intAnswer == 0) {
                    throw new Exception("cancel");
                } else {
                    return actionPick.getAvailableCards()
                        .get(intAnswer - 1);
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong choice. Please try again!");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Wrong choice. Please try again!");
            }

        }
    }
}
