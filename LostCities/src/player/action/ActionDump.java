package player.action;

import java.util.List;

import player.Player;
import card.Card;

public class ActionDump {

    private Player player;
    private Action action;
    private Card card;

    private List<Card> availableCards;

    public Action getAction() {
        return action;
    }

    public List<Card> getAvailableCards() {
        return availableCards;
    }

    public Card getCard() {
        return card;
    }

    public Player getPlayer() {
        return player;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setAvailableCards(List<Card> availableCards) {
        this.availableCards = availableCards;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Action: ");
        str.append(player.getName());
        str.append(" ");
        str.append(action.name());
        str.append(" ");
        str.append(card.toString());
        return str.toString();
    }

    public enum Action {
        CARD_TO_EXPEDITION,
        DUMP_CARD_TO_BOARD
    }
}
