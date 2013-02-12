package card;

import java.util.Stack;


public class AvailableCardStack extends Stack<Card> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3071939464550497388L;

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Card Stack: ");
		str.append(size());
		str.append(" left.");
		return str.toString();
	}
}
