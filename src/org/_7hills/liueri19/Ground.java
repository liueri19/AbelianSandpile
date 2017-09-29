package org._7hills.liueri19;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The plane that will hold the sand.
 */
public class Ground {
	/**
	 * Sand grains would spill once exceeding this threshold, default to 4.
	 */
	public final int SPILL_THRESHOLD;
	private int totalGrains = 0;
	private List<Coordinate> coordinates = new ArrayList<>();
	private Coordinate origin = new Coordinate(0, 0, this);
	
	public Ground(int spillThreshold) {
		this.SPILL_THRESHOLD = spillThreshold;
		coordinates.add(origin);
	}
	
	public Ground() {
		this(3);
	}
	
	public static void main(String[] args) {
		Ground ground = new Ground();
	}
	
	//helper method for drawing image
	static Color getMatchingColor(int grains) {
		if (grains == 0)
			return Color.BLACK;
		if (grains == 1)
			return new Color(85, 85, 85);
		if (grains == 2)
			return new Color(170, 170, 170);
		return Color.WHITE;
	}
}
