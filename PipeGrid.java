package Grid;

public class PipeGrid {
	private int[][] grid = new int[6][6];

	public PipeGrid() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				this.grid[i][j] = 0;
			}
		}
	}

	public PipeGrid(int[][] input) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				grid[i][j] = input[i][j];
			}
		}
	}

	private boolean isConsecutive(int input, int gridVal) {
		if (input == gridVal + 1 || input == gridVal - 1) {
			return true;
		}

		return false;
	}

	private boolean isValid(int i, int j, int input) {
		if (i > 5 || j > 5 || i < 0 || j < 0) {
			return false;
		} else if (this.grid[i][j] != 0) {
			return false;
		} else {
			if (input == 1) {
				return true;
			} else {
				if (i == 0) {
					if (j == 0) {
						return (this.isConsecutive(input, grid[0][1]) || this
								.isConsecutive(input, grid[1][0]));
					} else if (j == 5) {
						return (this.isConsecutive(input, grid[0][4]) || this
								.isConsecutive(input, grid[1][5]));
					} else {
						return (this.isConsecutive(input, grid[0][j - 1])
								|| this.isConsecutive(input, grid[0][j + 1]) || this
									.isConsecutive(input, grid[1][j]));
					}
				} else if (i == 5) {
					if (j == 0) {
						return (this.isConsecutive(input, grid[4][0]) || this
								.isConsecutive(input, grid[5][1]));
					} else if (j == 5) {
						return (this.isConsecutive(input, grid[5][4]) || this
								.isConsecutive(input, grid[4][5]));
					} else {
						return (this.isConsecutive(input, grid[5][j - 1])
								|| this.isConsecutive(input, grid[5][j + 1]) || this
									.isConsecutive(input, grid[4][j]));
					}
				} else if (j == 0) {
					return (this.isConsecutive(input, grid[i - 1][0])
							|| this.isConsecutive(input, grid[i + 1][0]) || this
								.isConsecutive(input, grid[i][1]));
				} else if (j == 5) {
					return (this.isConsecutive(input, grid[i - 1][5])
							|| this.isConsecutive(input, grid[i + 1][5]) || this
								.isConsecutive(input, grid[i][4]));
				} else {
					return (this.isConsecutive(input, grid[i - 1][j])
							|| this.isConsecutive(input, grid[i + 1][j])
							|| this.isConsecutive(input, grid[i][j + 1]) || this
								.isConsecutive(input, grid[i][j - 1]));
				}
			}
		}
	}
	
	private void reset() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				this.grid[i][j] = 0;
			}
		}
	}

	/*
	 * Generates a new direction 0: Right 1: Down 2: Left 3: up
	 */
	private int[] genDir(int i, int j) {
		int dir = (int) (Math.random() * 5);
		int coord[] = new int[2];

		switch (dir) {
		case 0:
			coord[0] = i;
			coord[1] = j + 1;
			break;
		case 1:
			coord[0] = i + 1;
			coord[1] = j;
			break;
		case 2:
			coord[0] = i;
			coord[1] = j - 1;
			break;
		case 3:
			coord[0] = i - 1;
			coord[1] = j;
			break;
		}

		return coord;
	}

	public void randGen() {
		boolean isFinished = false;

		while (!isFinished) {
			int currI = (int) (5 * Math.random());
			int currJ = (int) (5 * Math.random());
			int input = 1;
			grid[currI][currJ] = input++;

			while (isValid(currI + 1, currJ, input)
					|| isValid(currI, currJ + 1, input)
					|| isValid(currI - 1, currJ, input)
					|| isValid(currI, currJ - 1, input)) {

				int coord[] = genDir(currI, currJ);
				int tempI = coord[0];
				int tempJ = coord[1];

				while (!isValid(tempI, tempJ, input)) {
					int newCoord[] = genDir(currI, currJ);
					tempI = newCoord[0];
					tempJ = newCoord[1];
				}

				currI = tempI;
				currJ = tempJ;
				this.setPoint(currI, currJ, input);
				input++;
			}
			
			if (input != 37) {
				reset();
			} else {
				isFinished = true;
			}
		}
	}

	public int[][] getGrid() {
		return this.grid;
	}

	public void setGrid(int[][] input) {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				this.grid[i][j] = input[i][j];
			}
		}
	}

	public int getPoint(int i, int j) {
		return this.grid[i][j];
	}

	public void setPoint(int i, int j, int input) {
		this.grid[i][j] = input;
	}
	
	/*
	 * Generates a new direction 
	 * 0: Right 
	 * 1: Down 
	 * 2: Left 
	 * 3: up
	 */
	public int[] getPath() {
		int[] currCoord = new int[2];
		int[] path = new int[35];
		int currInt = 1;
		int currPath = 0;
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (getPoint(i, j) == currInt) {
					currCoord[0] = i;
					currCoord[1] = j;
				}
			}
		}
		currInt++;
		while (currInt != 37) {
			if (getPoint(currCoord[0] + 1, currCoord[1]) == currInt) {
				path[currPath] = 1;
			} else if (getPoint(currCoord[0], currCoord[1] + 1) == currInt) {
				path[currPath] = 0;
			} else if (getPoint(currCoord[0] - 1, currCoord[1]) == currInt) {
				path[currPath] = 3;
			} else {
				path[currPath] = 2;
			}
			System.out.println(currInt);
			currInt++;
			currPath++;
		}
		
		return path;
	}
	
	public void setPath(int i, int j, int path[]) {
		int initI = i;
		int initJ = j;
		setPoint(i, j, 1);
		for (int p = 0; p < 35; p++) {
			if (path[p] == 0) {
				setPoint(initI, initJ + 1, p + 2);
			} else if (path[p] == 1) {
				setPoint(initI - 1, initJ, p + 2);
			} else if (path[p] == 2) {
				setPoint(initI, initJ - 1, p + 2);
			} else {
				setPoint(initI - 1, initJ, p + 2);
			}
		}
	}

	public static int[] getSums(PipeGrid input) {
		int sums[] = new int[4];
		int grid[][] = input.getGrid();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				sums[0] += grid[i][j];
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 3; j < 6; j++) {
				sums[1] += grid[i][j];
			}
		}

		for (int i = 3; i < 6; i++) {
			for (int j = 3; j < 6; j++) {
				sums[2] += grid[i][j];
			}
		}

		for (int i = 3; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				sums[3] += grid[i][j];
			}
		}

		return sums;
	}

	public static double getStdDev(PipeGrid input) {
		int sum = 0;
		double mean = 0;
		double var = 0;
		double stdDev = 0;

		int quads[] = PipeGrid.getSums(input);

		for (int i = 0; i < quads.length; i++) {
			sum += quads[i];
		}

		mean = sum / 4;

		for (int i = 0; i < quads.length; i++) {
			var += Math.pow((quads[i] - mean), 2);
		}
		
		var /= 4;
		stdDev = Math.sqrt(var);

		return stdDev;

	}

	public static void printState(PipeGrid input) {
		int quads[] = PipeGrid.getSums(input);
		int grid[][] = input.getGrid();
		double stdDev = PipeGrid.getStdDev(input);

		System.out.println("------------------------");
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (grid[i][j] < 10) {
					System.out.print("|" + grid[i][j] + " |");
				} else {
					System.out.print("|" + grid[i][j] + "|");
				}
			}
			System.out.println();
		}
		
		System.out.println("------------------------");
		System.out.println("1st Quadrant: " + quads[0]);
		System.out.println("2nd Quadrant: " + quads[1]);
		System.out.println("3rd Quadrant: " + quads[2]);
		System.out.println("4th Quadrant: " + quads[3]);
		System.out.println("Standard Dev: " + stdDev);
	}
}
