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
	/**
	 * Size of the window in pixels.
	 */
	public static final int FRAME_DIMENSION = 1000;
	/**
	 * The delay between each grain is draped. Increase this value will slow down the process.
	 */
	public static final int DELAY = 10;
	/**
	 * Total number grains of sand to drop.
	 */
	private final int TOTAL_GRAINS = 1000;
	private Cell[][] cells;
	private int offset, sideLength;	//offset is half of sideLength
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private volatile boolean completed = false;
	
	public Ground(int spillThreshold) {
		SPILL_THRESHOLD = spillThreshold;
		sideLength = (int) (Math.sqrt(TOTAL_GRAINS / Math.PI)) * 2;	//guaranteed even
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
		ground.setBackground(Color.WHITE);
		frame.setPreferredSize(new Dimension(FRAME_DIMENSION, FRAME_DIMENSION));
		frame.add(ground);
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//thread for repaint
		//I found this more intuitive than the lambda version
		ground.executor.submit(new Runnable() {
			@Override
			public void run() {
				while (!ground.completed)
					ground.repaint();
			}
		});

		for (int grain = 0; grain < ground.TOTAL_GRAINS; grain++) {
			//add a sand, update, wait
			ground.addSand(0, 0);
			ground.updateSandPile();
			Thread.sleep(DELAY);
		}
		ground.completed = true;
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

	/**
	 * Return the cell at the specified location.
	 * @param x	the row index of the desired cell
	 * @param y	the column index of the desired cell
	 * @return	the cell at the specified location, may be null
	 */
	private Cell getCellAt(int x, int y) {
		return cells[x][y];	//no offset is intended
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
		BufferedImage image = new BufferedImage(sideLength, sideLength, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < sideLength; x++) {
			for (int y = 0; y < sideLength; y++) {
				Cell cell = getCellAt(x, y);
				if (cell == null)
					image.setRGB(x, y, Color.WHITE.getRGB());
				else {
					image.setRGB(x, y, cell.getColor());
				}
			}
		}
		graphics.drawImage(image, 0, 0, FRAME_DIMENSION, FRAME_DIMENSION, null);
	}
}
