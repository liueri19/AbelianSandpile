package org._7hills.liueri19;

/**
 * This class represents a spot holding sand on the ground.
 */
public class Coordinate implements Comparable<Coordinate> {
	private final int x, y;
	private final Ground ground;
	private int numSand = 0;	//number of sand grains at this coordinate
	private Coordinate[] surroundings = new Coordinate[4];
	
	public Coordinate(int x, int y, Ground ground) {
		this.x = x;
		this.y = y;
		this.ground = ground;
	}
	
	public void addSand() {
		numSand++;
		this.checkSpill();
	}
	
	public void checkSpill() {
		if (this.numSand == ground.SPILL_THRESHOLD+1)
			this.spill();
	}
	
	public void spill() {
		numSand = 0;
		if (surroundings[0] == null)
			initSurroundings();
		surroundings[0].addSand();
		surroundings[1].addSand();
		surroundings[2].addSand();
		surroundings[3].addSand();
	}
	
	private void initSurroundings() {
		surroundings[0] = ground.getCoordinate(x -1, y);
		surroundings[1] = ground.getCoordinate(x +1, y);
		surroundings[2] = ground.getCoordinate(x, y -1);
		surroundings[3] = ground.getCoordinate(x, y +1);
	}

	//this specific way of comparing is purposed to make output easier to implement.
	@Override
	public int compareTo(Coordinate c) {
		if (this.y > c.y)
			return -1;
		if (this.y < c.y)
			return 1;
		else {
			if (this.x < c.x)
				return -1;
			if (this.x > c.x)
				return 1;
		}
		return 0;
	}
	
	public boolean equals(Coordinate c) {
		return this.x == c.x && this.y == c.y;
	}
}
