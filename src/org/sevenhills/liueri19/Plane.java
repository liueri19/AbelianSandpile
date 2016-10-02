package org.sevenhills.liueri19;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Plane extends JPanel {
	//in development, known bugs not fixed
	
	private static final long serialVersionUID = 1L;
	//The plane the sandpile will be built on
	final int SPILL_THRESHOLD; //sand grains should stack until exceeds this threshold, default to 3
	int totalGrains = 0;
	int numSurroundings;
	Coordinate origin;
	List<Coordinate> coordinates = Collections.synchronizedList(new ArrayList<Coordinate>()); //unsynchronized list will rise CME 
/*	Logger logger = new Logger();*/ //logging feature not needed during development
	
	public Plane(int spillThreshold, int numSurroundings) {
		this.SPILL_THRESHOLD = spillThreshold;
		this.numSurroundings = numSurroundings;
		origin = new Coordinate(0, 0, this);
		coordinates.add(origin);
	}
	
	Coordinate getCoordinate(int x, int y) {
		int index = Collections.binarySearch(coordinates, new Coordinate(x, y, this));
		if (index < 0) {
			Coordinate c = new Coordinate(x, y, this);
			coordinates.add(-index - 1, c);
			return c;
		}
		return coordinates.get(index);
	}
	
	void refresh() {
		origin.addSand();
		totalGrains++;
		Collections.sort(coordinates);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Coordinate c : coordinates) {
			g.drawImage(getMatchingPixel(SPILL_THRESHOLD, c.numSand), c.X+149, c.Y+149, null);
		}
	}
	
/*	void outputLog() {
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
	}*/
	
	public static void main(String[] args) {
		//define constants for the plane
		//int spillThreshold = 7;
		int spillThreshold = 3;
		int dorpsOfSand = 9999;
		//to which surroundings coordinates should one coordinate spill to:
		//int numSurroundings = 8;
		int numSurroundings = 4;
		
		//run simulation, real time graphical display
		Plane plane = new Plane(spillThreshold, numSurroundings);
		//setup JFrame
		JFrame frame = new JFrame();
		Dimension d = new Dimension(300, 300);
		frame.setMinimumSize(d);
		frame.setSize(d);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("AbelianSandpile_Extended");
		frame.add(plane);
		frame.pack();
		frame.setVisible(true);
		for (int i = 0; i < dorpsOfSand; i++) {
			plane.refresh();
			plane.repaint();
			System.out.printf("Drop %d;\n", plane.totalGrains);
		}
		
		//output as file
/*		plane.outputLog();
		plane.logger.closeFile();*/
		//output as image
		int yMax = plane.coordinates.get(0).Y;
		int sideLength = 2 * yMax + 1;
		ImgPrinter img = new ImgPrinter(sideLength, sideLength, BufferedImage.TYPE_INT_ARGB);
		//draw image
		for (Coordinate c : plane.coordinates)
			img.setRGB(c.X + yMax, c.Y + yMax, getMatchingColor(plane.SPILL_THRESHOLD, c.numSand));
		//write image
		try {
			img.print("gif", "result_Drop" + plane.totalGrains + "_ST" + plane.SPILL_THRESHOLD + ".gif");
		} catch (IOException e) {
			System.out.println("Failed to write new image file, here's the stack trace:");
			e.printStackTrace();
		}
		
	}
	
	//helper method for drawing image
	static Color getMatchingColor(int spillThreshold, int grains) {
		int rgbValue = 255 / spillThreshold * grains;
		return new Color(rgbValue, rgbValue, rgbValue);
	}
	
	static BufferedImage getMatchingPixel(int spillThreshold, int grains) {
		Color color = getMatchingColor(spillThreshold, grains);
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		img.setRGB(0, 0, color.getRGB());
		return img;
	}
}


class Coordinate implements Comparable<Coordinate> {
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
