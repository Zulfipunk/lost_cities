package card;

public class BoardCardStack extends ColorCardStack {

	/**
	 * 
	 */
	private static final long serialVersionUID = -229361470518253610L;

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(String.format("%6s", color.name()));
		str.append(" ");
		if (!empty()) {
			for (int i = 0; i < size() - 1; i++) {
				str.append("|");
			}
			str.append(" ");
			str.append(peek().toShortString());
		} else {
			str.append("<empty>");
		}
		return str.toString();
	}

}
