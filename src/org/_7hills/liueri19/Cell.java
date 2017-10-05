package org._7hills.liueri19;

/**
 * Each cell hold a certain amount of sand grains.
 */
public class Cell {
	private final int x, y;	//the x and y coordinates of this cell
	private final Ground ground;
	private int sandGrains = 1;	//the number of sand grains on this cell

	/**
	 * Construct a new Cell at coordinates x and y on the specified ground.
	 * @param x	the desired x coordinate of this cell
	 * @param y	the desired y coordinate of this cell
	 * @param ground	the ground this cell is on
	 */
	public Cell(int x, int y, Ground ground) {
		this.x = x;
		this.y = y;
		this.ground = ground;
	}

	/**
	 * Add one grain of sand to this cell.
	 */
	public void addSand() {
//		sandGrains++;
		//increase the amount of sand grains on this cell by one
	}

	/**
	 * Remove the specified number of sand grains from this cell.
	 * @param grains	number of grains to remove
	 */
	public void removeSand(int grains) {
//		if (grains > sandGrains)
//			throw new IllegalArgumentException("Can not remove more sand than the cell has");
//		sandGrains -= grains;
		//decrease the amount of sand grains on this cell by the specified amount
	}

	/**
	 * Spill if this cell exceeds the spill threshold defined by the ground it is on.
	 * @return	true if this cell did spill, false otherwise
	 */
	public boolean checkSpill() {
//		boolean doSpill = sandGrains > ground.SPILL_THRESHOLD;
//		if (doSpill)
//			spill();
//		return doSpill;
		/*
		if the amount of sand grains on this cell > the spill threshold:
			spill;
		return true if this cell spilled, false otherwise
		 */
		return true;
	}

	/**
	 * Reduce the sand grains on this cell by 4, spilling them to 4 adjacent cells.
	 */
	public void spill() {
		/*
		add one grain of sand to the cell above, below, to the left, and to the right;
		remove 4 grains from this cell
		 */
	}

	@Override
	public String toString() {
		return Integer.toString(sandGrains);
	}
}
