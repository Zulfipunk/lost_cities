package card;

import java.util.ArrayList;
import java.util.Collections;

public class Hand extends ArrayList<Card> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean add(Card card) {
		boolean b = super.add(card);
		Collections.sort(this, new CardComparator());
		return b;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(" ");
		if (!isEmpty()) {
			for (Card card : this) {
				str.append(card.toString());
				str.append(", ");
			}
			str.delete(str.length() - 2, str.length());
		} else {
			str.append("<empty>");
		}
		return str.toString();
	}
}
