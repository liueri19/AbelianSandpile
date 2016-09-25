package org.sevenhills.liueri19;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
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
		//maybe not print after every single drop
		//this.output();
	}
	
	public void output() {
		logger.log("Drop %d:", totalGrains);
		Coordinate prevC = null;
		int yMax = coordinates.get(0).Y;
		for (Coordinate c : coordinates) {
			if (prevC == null || prevC.Y != c.Y)
				logger.log("\n%s%d ", new String(new char[2 * (yMax + c.X)]).replace("\0", " "), c.numSand); //yMax + c.X because yMax will be non-negative and c.X will be non-positive
			else
				logger.log("%d ", c.numSand);
			prevC = c;
		}
		logger.log("\n");
	}
	
	public static void main(String[] args) {
		Plane plane = new Plane();
		//output as file
		for (int i = 0; i < 333333; i++) {
			plane.refresh();
			System.out.printf("Drop %d;\n", plane.totalGrains);
		}
		plane.output();
		plane.logger.closeFile();
		
		//output as image
		File file = new File("result_Drop" + plane.totalGrains + ".gif");
		int yMax = plane.coordinates.get(0).Y;
		int sideLength = 2 * yMax + 1;
		BufferedImage image = new BufferedImage(sideLength, sideLength, BufferedImage.TYPE_INT_ARGB);
		//draw image
		for (Coordinate c : plane.coordinates) {
			image.setRGB(c.X + yMax, c.Y + yMax, getMatchingColor(c.numSand).getRGB());
		}
		//write image
		try {
			ImageIO.write(image, "gif", file);
		} catch (IOException e) {
			System.out.println("Failed to write new image file, here's the stack trace:");
			e.printStackTrace();
		}
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
