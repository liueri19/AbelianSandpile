package org._7hills.liueri19;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The ground to place the sand pile on.
 */
public class Ground extends JPanel {
	/**
	 * Sand grains would spill once exceeding this threshold, default to 4.
	 */
	public final int SPILL_THRESHOLD;
	private int totalGrains = 1000;
	private Cell[][] cells;
	private int offset, sideLength;	//offset is half of sideLength
	private Cell origin = new Cell(0, 0, this);
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	public Ground(int spillThreshold) {
		SPILL_THRESHOLD = spillThreshold;
		sideLength = (int) Math.sqrt(totalGrains / Math.PI) * 2;
		cells = new Cell[sideLength][sideLength];	//init a 2d array guaranteed to hold all sand
		offset = sideLength / 2;
		addSand(0, 0);
	}
	
	public Ground() {
		this(4);
	}
	
	public static void main(String[] args) throws InterruptedException {
		//setup the window
		Ground ground = new Ground();
		JFrame frame = new JFrame("Sand Pile");
		ground.setPreferredSize(new Dimension(ground.sideLength, ground.sideLength));
		ground.setBackground(Color.WHITE);
		frame.add(ground);
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//thread for repaint
		ground.executor.submit((Runnable) ground::repaint);	//the same thing as new Runnable calling repaint

		for (int grain = 0; grain < ground.totalGrains; grain++) {
			//add a sand, update, wait
			ground.origin.addSand();
			ground.updateSandPile();
			Thread.sleep(250);
		}
	}

	/**
	 * Add one grain of sand to the specified cell.
	 * @param x	the x location of the cell
	 * @param y	the y location of the cell
	 */
	public void addSand(int x, int y) {
		Cell cell = cells[x + offset][y + offset];
		if (cell == null)
			cells[x + offset][y + offset] = new Cell(x, y, this);
		else
			cell.addSand();
	}

	public void updateSandPile() {
		boolean completed = false;
		while (!completed) {
			completed = true;
			for (Cell[] row : cells) {
				for (Cell cell : row) {
					if (cell == null)
						continue;
					if (cell.checkSpill())
						completed = false;
				}
			}
		}
	}

	@Override
	public void paintComponent(Graphics graphics) {
		//construct an image
		BufferedImage image = new BufferedImage(sideLength, sideLength, BufferedImage.TYPE_INT_ARGB);

	}
}
