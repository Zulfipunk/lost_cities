package game;

import java.util.ArrayList;
import java.util.List;

import card.BoardCardStack;
import card.Card;
import card.Color;
import card.ColorCardStack;

public class Board {
	private final List<BoardCardStack> stacks;

	public Board() {
		stacks = new ArrayList<BoardCardStack>();
		for (Color color : Color.values()) {
			BoardCardStack stack = new BoardCardStack();
			stack.setColor(color);
			stacks.add(stack);
		}
	}

	public void reset() {
		for (ColorCardStack stack : stacks) {
			stack.clear();
		}
	}
	
	public BoardCardStack getBoardCardStack(Color color) {
		for (BoardCardStack stack : stacks) {
			if (stack.getColor() == color) {
				return stack;
			}
		}
		return null;
	}
	
	public List<BoardCardStack> getStacks() {
		return stacks;
	}
	
	public void addCard(Card card) {
		getBoardCardStack(card.getColor()).push(card);
	}
	
	public Card pickCard(Color color) {
		return getBoardCardStack(color).pop();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Board");
		str.append("\r\n");
		str.append("Stacks: ");
		str.append("\r\n");
		for (ColorCardStack expedition : stacks) {
			str.append(expedition).toString();
			str.append("\r\n");
		}
		return str.toString();
	}
}
