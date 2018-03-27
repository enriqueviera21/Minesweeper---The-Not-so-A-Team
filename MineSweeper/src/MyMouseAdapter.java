import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	public void mousePressed(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame) c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		myPanel.mouseDownGridX = myPanel.getGridX(x, y);
		myPanel.mouseDownGridY = myPanel.getGridY(x, y);
		myPanel.repaint();
		
		switch (e.getButton()) {
			case 1:		//Left mouse button
				
				break;
			case 3:		//Right mouse button
				Component d = e.getComponent();
				while (!(d instanceof JFrame)) {
					d = d.getParent();
					if (d == null) {
						return;
					}
				}
				JFrame myFrame1 = (JFrame)d;
				MyPanel myPanel1 = (MyPanel) myFrame1.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
				Insets myInsets1 = myFrame1.getInsets();
				int x2 = myInsets1.left;
				int y2 = myInsets1.top;
				e.translatePoint(-x2, -y2);
				int x0 = e.getX();
				int y0 = e.getY();
				myPanel1.x = x0;
				myPanel1.y = y0;
				myPanel1.mouseDownGridX = myPanel1.getGridX(x0, y0);
				myPanel1.mouseDownGridY = myPanel1.getGridY(x0, y0);
				myPanel1.repaint();
				break;
			default:    //Some other button (2 = Middle mouse button, etc.)
				//Do nothing
				break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame)c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y);
		switch (e.getButton()) {
			case 1:		//Left mouse button
				
				if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
					//Had pressed outside
					//Do nothing
				} else {
					if ((gridX == -1) || (gridY == -1)) {
						//Is releasing outside
						//Do nothing
					} else {
						if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
							//Released the mouse button on a different cell where it was pressed
							//Do nothing
						} else {
							//Released the mouse button on the same cell where it was pressed
							/*if ((gridX == 0) || (gridY == 0)) {
								//On the left column and on the top row... do nothing
							} else {*/
								//On the grid other than on the left column and on the top row:
								Color newColor = null;
								if(myPanel.getGridPoint(myPanel.mouseDownGridX, myPanel.mouseDownGridY) == -1) {
									for (int i = 0; i < 15; i++) {
										myPanel.colorArray[myPanel.bombGrid[i][0]][myPanel.bombGrid[i][1]] = Color.BLACK;
										return;
									}
									// lost game
								} else if(myPanel.getGridPoint(myPanel.mouseDownGridX, myPanel.mouseDownGridY) == 0) {
									newColor = Color.GRAY;
								} else {
									newColor = Color.GRAY;
									myPanel.repaint();
								}
								
									myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
									myPanel.revealAdjacent(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
//									myPanel.repaint();
							//}
						}
					}
				}
				myPanel.repaint();
				
				break;
			case 3:		//Right mouse button
				
				if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
					//Had pressed outside
					//Do nothing
				} else {
					if ((gridX == -1) || (gridY == -1)) {
						//Is releasing outside
						//Do nothing
					} else {
						if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
							//Released the mouse button on a different cell where it was pressed
							//Do nothing
						} else {
							//Released the mouse button on the same cell where it was pressed
							if ((gridX == 0) || (gridY == 0)) {
								//On the left column and on the top row... do nothing
							} else {
								//if(myPanel.getGridPoint(myPanel.mouseDownGridX, myPanel.mouseDownGridY)==0) {
								if(myPanel.grid[myPanel.mouseDownGridX][myPanel.mouseDownGridY] > 0) {
								}
								myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.RED;
								myPanel.repaint();
								//}
							}
						}
					}
				}
				myPanel.repaint();
				break;
			default:    //Some other button (2 = Middle mouse button, etc.)
				//Do nothing
				
				break;
		}
	}
}
		