package card;

public class ColorCardStack extends AvailableCardStack {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2783009638952042717L;

	protected Color color;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(String.format("%6s", color.name()));
		str.append(" ");
		if (!isEmpty()) {
			for (Card card : this) {
				str.append(card.toShortString());
				str.append(", ");
			}
			str.delete(str.length() - 2, str.length());
		} else {
			str.append("<empty>");
		}
		return str.toString();
	}

}
