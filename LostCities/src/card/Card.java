package card;

public class Card {
	private CardType type;
	private Color color;
	private Integer value;

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}

		Card card = (Card) obj;

		if (isBet() || card.isBet()) {
			return card.getColor() == this.getColor() && card.getType() == this.getType();
		} else {
			return card.getColor() == this.getColor() && card.getType() == this.getType() && card.getValue().equals(this.getValue());
		}
	}

	public Color getColor() {
		return color;
	}

	public CardType getType() {
		return type;
	}

	public Integer getValue() {
		return value;
	}

	public boolean isBet() {
		return getType() == CardType.BET;
	}

	public boolean isHigherEquals(Card card) {
		return new CardComparator().compare(this, card) >= 0;
	}

	public boolean isLowerEquals(Card card) {
		return new CardComparator().compare(this, card) <= 0;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String toShortString() {
		if (type == CardType.BET) {
			return type.name();
		} else if (type == CardType.POINT) {
			return String.valueOf(value);
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(color.name());
		str.append(" ");
		str.append(toShortString());
		return str.toString();
	}
}
