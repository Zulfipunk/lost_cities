package player;

import game.Board;

import java.util.ArrayList;
import java.util.List;

import player.action.ActionDump;
import player.action.ActionPick;
import card.Card;
import card.Color;
import card.ColorCardStack;
import card.ExpeditionCardStack;
import card.Hand;

public abstract class Player {
    private String name;

    protected final Hand hand;
    private final List<ExpeditionCardStack> expeditions;

    public Player() {
        hand = new Hand();
        expeditions = new ArrayList<ExpeditionCardStack>();
        for (Color color : Color.values()) {
            ExpeditionCardStack expedition = new ExpeditionCardStack();
            expedition.setColor(color);
            expeditions.add(expedition);
        }
    }

    public void addCardToExpedition(Card card) {
        getExpeditionStack(card.getColor()).push(card);
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public abstract ActionDump doActionDump(ActionDump actionDump);

    public abstract ActionPick doActionPick(ActionPick actionPick, Board board);

    public List<ExpeditionCardStack> getExpeditions() {
        return expeditions;
    }

    public ExpeditionCardStack getExpeditionStack(Color color) {
        for (ExpeditionCardStack stack : expeditions) {
            if (stack.getColor() == color) {
                return stack;
            }
        }
        return null;
    }

    public Hand getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public Card pickCardFromExpedition(Color color) {
        return getExpeditionStack(color).pop();
    }

    public void removeCardFromHand(Card card) {
        hand.remove(card);
    }

    public void reset() {
        hand.clear();
        for (ColorCardStack expedition : expeditions) {
            expedition.clear();
        }

    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(name);
        str.append("\r\n");
        str.append("Hand: ");
        str.append(hand.toString());
        str.append("\r\n");
        str.append("Expeditions: ");
        str.append("\r\n");
        for (ColorCardStack expedition : expeditions) {
            str.append(expedition)
                .toString();
            str.append("\r\n");
        }
        return str.toString();
    }
}
