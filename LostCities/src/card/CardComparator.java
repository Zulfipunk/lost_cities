package card;

import java.util.Comparator;

public class CardComparator implements Comparator<Card> {

	@Override
	public int compare(Card o1, Card o2) {
		if (o1.getColor().ordinal() > o2.getColor().ordinal()) {
			return 1;
		} else if (o1.getColor().ordinal() < o2.getColor().ordinal()) {
			return -1;
		} else {
			if (o1.getType().ordinal() > o2.getType().ordinal()) {
				return 1;
			} else if (o1.getType().ordinal() < o2.getType().ordinal()) {
				return -1;
			} else {
				if (o1.getType() == Type.BET) {
					return 0;
				} else {
					if (o1.getValue().intValue() > o2.getValue().intValue()) {
						return 1;
					} else if (o1.getValue().intValue() < o2.getValue().intValue()) {
						return -1;
					} else {
						return 0;
					}
				}
			}
		}
	}
}
