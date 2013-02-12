package card;

public class ExpeditionCardStack extends ColorCardStack {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2164142776213486231L;

	private int getMultiplier() {
		int betCount = 0;
		for (Card card : this) {
			if (card.isBet()) {
				betCount++;
			}
		}
		return betCount + 1;
	}

	public int getScore() {
		if (isEmpty()) {
			return 0;
		} else {
			int score = -20;
			for (Card card : this) {
				if (!card.isBet()) {
					score += card.getValue();
				}
			}
			score *= getMultiplier();

			if (size() >= 8) {
				score += 20;
			}
			return score;
		}
	}
}
