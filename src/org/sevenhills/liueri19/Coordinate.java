package org.sevenhills.liueri19;

public class Coordinate implements Comparable<Coordinate> {
	//A specific coordinate in the plane.
	final int X;
	final int Y;
	final Plane PLANE;
	int numSand = 0; //number of sand grains at this coordinate
	Coordinate[] surroundings;
	
	public Coordinate(int x, int y, Plane plane) {
		this.X = x;
		this.Y = y;
		this.PLANE = plane;
		this.surroundings = new Coordinate[plane.numSurroundings];
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
		numSand = numSand - PLANE.numSurroundings;
		if (surroundings[0] == null)
			initSurroundings();
		for (Coordinate c : surroundings)
			c.addSand();
	}
	
	private void initSurroundings() {
		//the following code is bugged:
		/*int index = 0;
		for (int deltaX = -1; deltaX < 2; deltaX++) {
			for (int deltaY = -1; deltaY < 2; deltaY++) {
				if (deltaX == 0 && deltaY == 0)
					break;
				surroundings[index] = PLANE.getCoordinate(X + deltaX, Y + deltaY);
				index++;
			}
		}*/
		//equivalent code:
		surroundings[0] = PLANE.getCoordinate(X-1, Y);
		surroundings[1] = PLANE.getCoordinate(X+1, Y);
		surroundings[2] = PLANE.getCoordinate(X, Y-1);
		surroundings[3] = PLANE.getCoordinate(X, Y+1);
		//uncomment following for numSurroundings == 8:
/*		surroundings[4] = PLANE.getCoordinate(X-1, Y-1);
		surroundings[5] = PLANE.getCoordinate(X+1, Y+1);
		surroundings[6] = PLANE.getCoordinate(X+1, Y-1);
		surroundings[7] = PLANE.getCoordinate(X-1, Y+1);*/
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
