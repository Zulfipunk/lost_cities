package game;

import java.util.HashMap;

import player.Player;

public class Round {
	private HashMap<Player, Integer> points;

	public HashMap<Player, Integer> getPoints() {
		return points;
	}

	public void setPoints(HashMap<Player, Integer> points) {
		this.points = points;
	}
}
