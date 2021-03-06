import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 70;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9;   //Last row has only one cell
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	public int[][] bombGrid = new int[15][2];
	public Random generator = new Random();
	public int[][] grid = new int[9][9];
	public int[][] adjacentCell = new int[46][2]; // Used to verify if the cell which is going through the revealAdjacent method already went about it
												  // 46 is the minimum of white spaces in the 9x9 grid with 15 bombs
	public int adjacentCounter = 0; // Used to add cell to adjacentCell array.
	
	public MyPanel() {   //This is the constructor... this code runs first to initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 2!");
		}
		
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}
		
		// Bomb Grid: 
		int[] bombPoint = new int[2];
		for(int i = 0; i < 15; i++) {
			switch (generator.nextInt(9)) {
			case 0:
				bombPoint[0] = 0;
				break;
			case 1:
				bombPoint[0] = 1;
				break;
			case 2:
				bombPoint[0] = 2;
				break;
			case 3:
				bombPoint[0] = 3;
				break;
			case 4:
				bombPoint[0] = 4;
				break;
			case 5:
				bombPoint[0] = 5;
				break;
			case 6:
				bombPoint[0] = 6;
				break;
			case 7:
				bombPoint[0] = 7;
				break;
			case 8:
				bombPoint[0] = 8;
				break;
			}
			switch (generator.nextInt(9)) {
			case 0:
				bombPoint[1] = 0;
				break;
			case 1:
				bombPoint[1] = 1;
				break;
			case 2:
				bombPoint[1] = 2;
				break;
			case 3:
				bombPoint[1] = 3;
				break;
			case 4:
				bombPoint[1] = 4;
				break;
			case 5:
				bombPoint[1] = 5;
				break;
			case 6:
				bombPoint[1] = 6;
				break;
			case 7:
				bombPoint[1] = 7;
				break;
			case 8:
				bombPoint[1] = 8;
				break;
			}
			
			bombGrid[i][0] = bombPoint[0];
			bombGrid[i][1] = bombPoint[1];
			
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);

		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), 2*y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));
		}

		//Draw an additional cell at the bottom left
		
		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				if ((x == 0) || (y != TOTAL_ROWS)) {
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), INNER_CELL_SIZE, INNER_CELL_SIZE);
					if (grid[x][y] > 0) {						
						Font font = new Font("Helvetica", Font.PLAIN, 50);
						g.setFont(font);
						g.setColor(Color.WHITE);
						g.drawString(Integer.toString(grid[x][y]),x1 + GRID_X + (x * (INNER_CELL_SIZE)) + 25, y1 + GRID_Y + ((y+1) * (INNER_CELL_SIZE)) - 18);
						g.setColor(c);
				
					}
				}
				
			}
		}	
	}


	// This method helps to find the adjacent boxes that don't have a mine.
	// It is partially implemented since the verify hasn't been discussed in class
	// Verify that the coordinates in the parameters are valid.
	// Also verifies if there are any mines around the x,y coordinate
	public void revealAdjacent(int x, int y){
		for (int i = 0; i < 46; i++) {
			if(adjacentCell[i][0] == x && adjacentCell[i][1] == y) {return;}}
		if(grid[x][y] == 0) {
			try {
				if((x-1 >= 0) && grid[x-1][y] == 0){
					colorArray[x-1][y] = Color.GRAY;
				}
			}catch(ArrayIndexOutOfBoundsException e) {}
			try {
				if((x-1 >= 0) && (y-1 >= 0) && grid[x-1][y-1] == 0){
					colorArray[x-1][y-1] = Color.GRAY;
				}
			}catch(ArrayIndexOutOfBoundsException e) {}
			try {
				if((x-1 >= 0) && (y+1 <= 9) && grid[x-1][y+1] == 0){
					colorArray[x-1][y+1] = Color.GRAY;
				}
			}catch(ArrayIndexOutOfBoundsException e) {}
			try {
				if((x+1 <= 9) && grid[x+1][y] == 0){
					colorArray[x+1][y] = Color.GRAY;
				}
			}catch(ArrayIndexOutOfBoundsException e) {}
			try {
				if((x+1 <= 9) && (y-1 >= 0) && grid[x+1][y-1] == 0){
					colorArray[x+1][y-1] = Color.GRAY;
				}
			}catch(ArrayIndexOutOfBoundsException e) {}
			try {
				if((x+1 <= 9) && (y+1 <= 9) && grid[x+1][y+1] == 0){
					colorArray[x+1][y+1] = Color.GRAY;
				}
			}catch(ArrayIndexOutOfBoundsException e) {}
			try {
				if((y+1 <= 9) && grid[x][y+1] == 0){
					colorArray[x][y+1] = Color.GRAY;
				}
			}catch(ArrayIndexOutOfBoundsException e) {}
			try {
				if((y-1 >= 0) && grid[x][y-1] == 0){
					colorArray[x][y-1] = Color.GRAY;
				}
			}catch(ArrayIndexOutOfBoundsException e) {}
			
			adjacentCell[adjacentCounter][0] = x;
			adjacentCell[adjacentCounter][1] = y;
			adjacentCounter += 1;
			
			try{revealAdjacent(x-1,y);}catch(ArrayIndexOutOfBoundsException e) {}
			try{revealAdjacent(x-1,y-1);}catch(ArrayIndexOutOfBoundsException e) {}
			try{revealAdjacent(x-1,y+1);}catch(ArrayIndexOutOfBoundsException e) {}
			try{revealAdjacent(x+1,y);}catch(ArrayIndexOutOfBoundsException e) {}
			try{revealAdjacent(x+1,y-1);}catch(ArrayIndexOutOfBoundsException e) {}
			try{revealAdjacent(x+1,y);}catch(ArrayIndexOutOfBoundsException e) {}
			try{revealAdjacent(x,y-1);}catch(ArrayIndexOutOfBoundsException e) {}
			try{revealAdjacent(x,y+1);}catch(ArrayIndexOutOfBoundsException e) {}

			try {
				if(grid[x-1][y] > 0) { colorArray[x-1][y] = Color.GRAY;}
			}catch(ArrayIndexOutOfBoundsException e) {}
			try {
				if(grid[x+1][y] > 0) { colorArray[x+1][y] = Color.GRAY;}
			}catch(ArrayIndexOutOfBoundsException e) {}
			try {
				if(grid[x][y-1] > 0) { colorArray[x][y-1] = Color.GRAY;}
			}catch(ArrayIndexOutOfBoundsException e) {}
			try {
				if(grid[x][y+1] > 0) { colorArray[x][y+1] = Color.GRAY;}
			}catch(ArrayIndexOutOfBoundsException e) {}
			
		}
	}
	
		
	// Sets a two dimensional array to represent the MineSweeper 9x9 Grid.
	// Bombs represent -1 and empty cells represent 0.
	// The correct numbers are taken from the setNumber method.
	public void setGrid() {
		for (int i = 0; i<15; i++) {
			grid[bombGrid[i][0]][bombGrid[i][1]] = -1;
		}
		for (int i = 0; i<9; i++) {
			for (int j = 0; j<9;j++) {
				if(grid[i][j] != -1) {
					grid[i][j] = setNumber(i, j);
				}
			}
		}
	}
	// Returns the status of a point in the grid (bomb = -1, nothing = 0, or number of bombs around)
	public int getGridPoint(int x, int y){
		setGrid();
		return this.grid[x][y];
	}
	// Verifies all the cells around a certain cell to see if it has any bombs.
	// Returns the number of bombs around this cell.
	public int setNumber(int x, int y) {
		int counter = 0;
		if(IsBomb(x-1, y)){counter+=1;}
		if(IsBomb(x-1, y-1)){counter+=1;}
		if(IsBomb(x-1, y+1)){counter+=1;}
		if(IsBomb(x+1, y)){counter+=1;}
		if(IsBomb(x+1, y-1)){counter+=1;}
		if(IsBomb(x+1, y+1)){counter+=1;}
		if(IsBomb(x, y-1)) {counter+=1;}
		if(IsBomb(x, y+1)) {counter+=1;}
		return counter;		
	}
	
	// Returns a boolean value verifying if the given point is a bomb from the bombGrid.
		public boolean IsBomb(int xPoint, int yPoint) { 
			for (int i = 0; i < 15; i++) {
				try {
					if (bombGrid[i][0] == xPoint && bombGrid[i][1] == yPoint) {
						//System.out.println(bombGrid[i][0]+"\t"+bombGrid[i][1]);
						return true;}
				}catch(ArrayIndexOutOfBoundsException e) {}
			}
			return false;
		}

	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return x;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS -1) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
			return y;
		}
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}
	
}