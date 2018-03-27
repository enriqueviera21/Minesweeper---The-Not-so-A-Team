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
	public static int[][] bombGrid = new int[15][2];
	public Random generator = new Random();
	public int[][] grid = new int[9][9];
	public static boolean searchMines = false;
	public static int mineCenter;
	public int n;
	
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
		/*for (int x = 0; x < TOTAL_COLUMNS; x++) {   //Top row
			colorArray[x][0] = Color.LIGHT_GRAY;
		}
		for (int y = 0; y < TOTAL_ROWS; y++) {   //Left column
			colorArray[0][y] = Color.LIGHT_GRAY;
		}*/
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
			//System.out.print(bombGrid[i][0]+"\t");
			//System.out.println(bombGrid[i][1]);
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
		//g.drawRect(x1 + GRID_X, y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS - 1)), INNER_CELL_SIZE + 1, INNER_CELL_SIZE + 1);
		
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
		
		//for (int i = 0; i < TOTAL_COLUMNS; i++) {
			//for (int j = 0; j < TOTAL_ROWS; j++) {
				while(grid[x][y] == 0) {
					if((x-1 > 0) && grid[x-1][y] == 0){
						colorArray[x-1][y] = Color.GRAY;
						revealAdjacent(x,y);
					}
					if((x-1 > 0) && (y-1 > 0) && grid[x-1][y-1] == 0){
						colorArray[x-1][y-1] = Color.GRAY;
						revealAdjacent(x,y);
					}
					if((x-1 > 0) && (y+1 < 9) && grid[x-1][y+1] == 0){
						colorArray[x-1][y+1] = Color.GRAY;
						revealAdjacent(x,y);
					}
					if((x+1 < 9) && grid[x+1][y] == 0){
						colorArray[x+1][y] = Color.GRAY;
						revealAdjacent(x,y);
					}
					if((x+1 < 9) && (y-1 > 0) && grid[x+1][y-1] == 0){
						colorArray[x+1][y-1] = Color.GRAY;
						revealAdjacent(x,y);
					}
					if((x+1 < 9) && (y+1 < 9) && grid[x+1][y+1] == 0){
						colorArray[x+1][y+1] = Color.GRAY;
						revealAdjacent(x,y);
					}
					if((y+1 < 9) && grid[x][y+1] == 0){
						colorArray[x][y+1] = Color.GRAY;
						revealAdjacent(x,y);
					}
					if((y-1 > 0) && grid[x][y-1] == 0){
						colorArray[x][y-1] = Color.GRAY;
						revealAdjacent(x,y);
					}
					//
			}
		}
		
//		if(grid[x][y] == 0) {
//			if((x-1 > 0) && grid[x-1][y] == 0){
//				colorArray[x-1][y] = Color.YELLOW;
//			}else if((x-1 > 0) && (y-1 > 0) && grid[x-1][y-1] == 0){
//				colorArray[x-1][y-1] = Color.CYAN;
//			}else if((x-1 > 0) && (y+1 < 9) && grid[x-1][y+1] == 0){
//				colorArray[x-1][y+1] = Color.MAGENTA;
//			}else if((x+1 < 9) && grid[x+1][y] == 0){
//				colorArray[x+1][y] = Color.PINK;
//			}else if((x+1 < 9) && (y-1 > 0) && grid[x+1][y-1] == 0){
//				colorArray[x+1][y-1] = Color.BLUE;
//			}else if((x+1 < 9) && (y+1 < 9) && grid[x+1][y+1] == 0){
//				colorArray[x+1][y+1] = Color.GREEN;
//			}else if((y+1 < 9) && grid[x][y+1] == 0){
//				colorArray[x][y+1] = Color.ORANGE;
//			}else if((y-1 > 0) && grid[x][y-1] == 0){
//				colorArray[x][y-1] = Color.GRAY;
//			}
//			revealAdjacent(x,y);
//			//try {revealAdjacent(x-1,y);}catch(IndexOutOfBoundsException e) {}
//			//try {revealAdjacent(x-1,y-1);}catch(IndexOutOfBoundsException e) {}
//			//try {revealAdjacent(x-1,y+1);}catch(IndexOutOfBoundsException e) {}
//			//try {revealAdjacent(x+1,y);}catch(IndexOutOfBoundsException e) {}
//			//try {revealAdjacent(x+1,y-1);}catch(IndexOutOfBoundsException e) {}
//			//try {revealAdjacent(x+1,y);}catch(IndexOutOfBoundsException e) {}
//			//try {revealAdjacent(x,y-1);}catch(IndexOutOfBoundsException e) {}
//			//try {revealAdjacent(x,y+1);}catch(IndexOutOfBoundsException e) {}
			
			
			
		//}
	//}
	
		
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
		if((x-1 > 0) && IsBomb(x-1, y)){counter+=1;}
		if((x-1 > 0) && (y-1 > 0) && IsBomb(x-1, y-1)){counter+=1;}
		if((x-1 > 0) && (y+1 < 9) && IsBomb(x-1, y+1)){counter+=1;}
		if((x+1 < 9) && IsBomb(x+1, y)){counter+=1;}
		if((x+1 < 9) && (y-1 > 0) && IsBomb(x+1, y-1)){counter+=1;}
		if((x+1 < 9) && (y+1 < 9) && IsBomb(x+1, y+1)){counter+=1;}
		if((y-1 > 0) && IsBomb(x, y-1)) {counter+=1;}
		if((y+1 < 9) && IsBomb(x, y+1)) {counter+=1;}
			System.out.println(counter + "\t"+ x +"\t" + y);
		return counter;		
	}
	
	// Returns a boolean value verifying if the given point is a bomb from the bombGrid.
		public boolean IsBomb(int xPoint, int yPoint) { 
			for (int i = 0; i < 15; i++) {
				if (bombGrid[i][0] == xPoint && bombGrid[i][1] == yPoint) {
					//System.out.println(bombGrid[i][0]+"\t"+bombGrid[i][1]);
					return true;}
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
////	public searchMine() {
////		for (int i = 0; i < TOTAL_COLUMNS; i++) {
////			for (int j = 0; j < TOTAL_ROWS; j++) {
////				if ( bombGrid[i][j] !=0 && colorArray[i][j] == Color.BLACK ) {
////					searchMines = true;	
////				}
////			}
////		}
//	}
//	public static boolean countUncovered() {
//		boolean uncovered = true;
//		for (int i = 0; i < TOTAL_COLUMNS; i++) {
//			for (int j = 0; j < TOTAL_ROWS; j++) {
//				if (bombGrid[i][j] == 0 && colorArray[i][j] == (Color.WHITE)) {
//					uncovered = false;
//				}
//			}
//		}
//		return uncovered;
//	}
	public void adjacent() {
		for (int i = 0; i < TOTAL_COLUMNS; i++) {
			for (int j = 0; j < TOTAL_ROWS; j++) {
				grid[i][j] = this.grid[x][y];
				
			}
		}
	}
//	public static void MineCounter(int MouseDownX, int MouseDownY) {
//		mineCenter = 0;
//		
//		for (int i = MouseDownX - 1 ; i <= MouseDownX + 1; i++) {
//			for (int j = MouseDownY -1; j <= MouseDownY + 1; j++) {
//				if (i < 0 || i > TOTAL_COLUMNS - 1 || j < 0 || j > TOTAL_ROWS - 1) {
//					
//				} else if (bombGrid[i][j] == -1) {
//					mineCenter++;
//				}
//			}
//		}
//		if (mineCenter == 0) {
//			for (int i = MouseDownX - 1; i <= MouseDownX + 1; i++) {
//				for (int j = MouseDownY - 1 ; j <= MouseDownY + 1; j++) {
//					if (i < 0 || i > TOTAL_COLUMNS - 1 || j < 0 || j > TOTAL_ROWS - 1) {
//						
//					} else if (bombGrid[i][j] == 0 && colorArray[i][j] != Color.GRAY) {
//						colorArray[i][j] = Color.GRAY;
//						MineCounter(i,j);
//					}
//						
//					}
//				}
//		}
//	}
		
}