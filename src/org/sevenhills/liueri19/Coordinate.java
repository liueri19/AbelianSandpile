package org.sevenhills.liueri19;

public class Coordinate implements Comparable<Coordinate> {
	//A specific coordinate in the plane.
	final int X;
	final int Y;
	final Plane PLANE;
	int numSand = 0; //number of sand grains at this coordinate
	Coordinate[] surroundings = new Coordinate[4];
	
	public Coordinate(int x, int y, Plane plane) {
		this.X = x;
		this.Y = y;
		this.PLANE = plane;
	}
	
	public int getNumSand() {
		return numSand;
	}
	
	public void addSand() {
		numSand++;
		this.checkSpill();
	}
	
	public void checkSpill() {
		if (this.numSand == PLANE.SPILL_THRESHOLD+1)
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
		surroundings[0] = PLANE.getCoordinate(X-1, Y);
		surroundings[1] = PLANE.getCoordinate(X+1, Y);
		surroundings[2] = PLANE.getCoordinate(X, Y-1);
		surroundings[3] = PLANE.getCoordinate(X, Y+1);
	}

	//this specific way of comparing is purposed to make output easier to implement.
	@Override
	public int compareTo(Coordinate c) {
		if (this.Y > c.Y)
			return -1;
		if (this.Y < c.Y)
			return 1;
		else {
			if (this.X < c.X)
				return -1;
			if (this.X > c.X)
				return 1;
		}
		return 0;
	}
	
	public boolean equals(Coordinate c) {
		return this.X == c.X && this.Y == c.Y;
	}
}
