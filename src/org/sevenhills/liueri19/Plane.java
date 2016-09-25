package org.sevenhills.liueri19;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plane {
	//The plane the sandpile will be built on
	final int SPILL_THRESHOLD; //sand grains should stack until exceeds this threshold, default to 3
	int totalGrains = 0;
	List<Coordinate> coordinates = new ArrayList<Coordinate>();
	Coordinate origin = new Coordinate(0, 0, this);
	Logger logger = new Logger();
	
	public Plane(int spillThreshold) {
		this.SPILL_THRESHOLD = spillThreshold;
		coordinates.add(origin);
	}
	
	public Plane() {
		this(3);
	}
	
	public Coordinate getCoordinate(int x, int y) {
		int index = Collections.binarySearch(coordinates, new Coordinate(x, y, this));
		if (index < 0) {
			Coordinate c = new Coordinate(x, y, this);
			coordinates.add(-index - 1, c);
			return c;
		}
		return coordinates.get(index);
	}
	
	public void refresh() {
		origin.addSand();
		totalGrains++;
		Collections.sort(coordinates);
		this.output();
	}
	
	public void output() {
		logger.log("Drop %d:", totalGrains);
		Coordinate prevC = null;
		int yMax = coordinates.get(0).Y;
		for (Coordinate c : coordinates) {
			if (prevC == null || prevC.Y != c.Y)
				logger.log("\n%s%d ", new String(new char[2 * (yMax + c.X)]).replace("\0", " "), c.numSand);
			else
				logger.log("%d ", c.numSand);
			prevC = c;
		}
		logger.log("\n");
	}
	
	public static void main(String[] args) {
		Plane plane = new Plane();
		for (int i = 0; i < 700; i++)
			plane.refresh();
		plane.logger.closeFile();
	}
}
