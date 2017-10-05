package org._7hills.liueri19;

/**
 * The ground to place the sand pile on.
 */
public class Ground {
	/**
	 * Sand grains would spill once exceeding this threshold, default to 4.
	 */
	public final int SPILL_THRESHOLD;
	/**
	 * Total number grains of sand to drop.
	 */
	private final int TOTAL_GRAINS = 1000;
	private Cell[][] cells;
	private int offset, sideLength;	//offset is half of sideLength
	/*
	The indexes in the array cells will not correspond to the coordinates of the cell.
	Array indexes start at 0, where as coordinates depends on the size of the grid.
	Coordinates have origin (0, 0) at the center of the grid. The cell at the
	center of the grid is at indexes [offset][offset] in cells.
	For a cell with coordinate (x, y), the corresponding indexes are
	[x + offset][y + offset]
	 */
	
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
		Ground ground = new Ground();

		//add TOTAL_GRAINS grains of sand to the ground

		/*
		for the number of grains in TOTAL_GRAINS:
			add one grain of sand to center;
			update the entire grid;
		 */
	}

	/**
	 * Add one grain of sand to the specified cell.
	 * @param x	the x location of the cell
	 * @param y	the y location of the cell
	 */
	public void addSand(int x, int y) {
		/*
		Remember about offset. If you don't know what I'm talking about, refer
		to the block of comment near the beginning.
		 */
//		Cell cell = cells[x + offset][y + offset];
//		if (cell == null)
//			cells[x + offset][y + offset] = new Cell(x, y, this);
//		else
//			cell.addSand();
		/*
		get cell at the specified indexes;
		add one grain of sand to that cell;
		 */
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

	/**
	 * Update this sand pile until no more cell should spill.
	 */
	public void updateSandPile() {
//		boolean completed = false;
//		while (!completed) {
//			completed = true;
//			for (Cell[] row : cells) {
//				for (Cell cell : row) {
//					if (cell == null)
//						continue;
//					if (cell.checkSpill())
//						completed = false;
//				}
//			}
//		}
		/*
		while there still are cells with too much sand:
			for every row on the grid:
				for every cell in that row:
					if the cell have too much sand:
						spill;
					otherwise do nothing;
		 */
	}
}
