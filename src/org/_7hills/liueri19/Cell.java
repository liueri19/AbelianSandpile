package org._7hills.liueri19;

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
	 * Remove the specified number of sand grains from this cell.
	 * @param grains	number of grains to remove
	 */
	public void removeSand(int grains) {
		if (grains > sandGrains)
			throw new IllegalArgumentException("Can not remove more sand than the cell has");
		sandGrains -= grains;
	}

	/**
	 * Spill if this cell exceeds the spill threshold defined by the ground it is on.
	 * @return	true if this cell did spill, false otherwise
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
		ground.addSand(x - 1, y);
		ground.addSand(x + 1, y);
		ground.addSand(x, y - 1);
		ground.addSand(x, y + 1);
		removeSand(4);
	}

	//turns out to be unused
//	public int getX() {
//		return x;
//	}
//
//	public int getY() {
//		return y;
//	}

	/**
	 * Return a certain shade of gray depending on the amount of sand in this cell.
	 * @return	an int value representing the shade of gray
	 */
	public int getColor() {
		int grayValue = (int) (255 - 255f * sandGrains / ground.SPILL_THRESHOLD);
		int color = grayValue;
		color = (color << 8) + grayValue;
		color = (color << 8) + grayValue;
		return color;
	}

	@Override
	public String toString() {
		return Integer.toString(sandGrains);
	}
}
