package org._7hills.liueri19;

import java.awt.*;

/**
 * Each cell hold a certain amount of sand grains.
 */
public class Cell {
	private final int x, y;
	private final Ground ground;
	private int sandGrains = 1;
	
	public Cell(int x, int y, Ground ground) {
		this.x = x;
		this.y = y;
		this.ground = ground;
	}

	/**
	 * Add one grain of sand to this cell.
	 */
	public void addSand() {
		sandGrains++;
	}

	/**
	 * Spill if this cell exceeds the spill threshold defined by the ground it is on.
	 * @Return	true if this cell did spill, false otherwise
	 */
	public boolean checkSpill() {
		boolean doSpill = sandGrains > ground.SPILL_THRESHOLD;
		if (doSpill)
			spill();
		return doSpill;
	}

	/**
	 * Reduce the sand grains on this cell by 4, spilling them to 4 adjacent cells.
	 */
	public void spill() {
		ground.addSand(x - 1, y - 1);
		ground.addSand(x - 1, y + 1);
		ground.addSand(x + 1, y - 1);
		ground.addSand(x + 1, y + 1);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Color toColor() {
		//can do this
//		int rgb = red;
//		rgb = (rgb << 8) + green;
//		rgb = (rgb << 8) + blue;
		//
		int grayValue = (int) (255 - 255f / sandGrains);
		return new Color(grayValue, grayValue, grayValue);
	}

//	//this specific way of comparing is purposed to make output easier to implement.
//	@Override
//	public int compareTo(Cell c) {
//		if (this.y > c.y)
//			return -1;
//		if (this.y < c.y)
//			return 1;
//		else {
//			if (this.x < c.x)
//				return -1;
//			if (this.x > c.x)
//				return 1;
//		}
//		return 0;
//	}
	
//	public boolean equals(Cell c) {
//		return this.x == c.x && this.y == c.y;
//	}
}
