import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		JFrame myFrame = new JFrame("Sweeper of Mines");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(900, 900);

		MyPanel myPanel = new MyPanel();
		myFrame.add(myPanel);

		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);
		myFrame.setVisible(true);
		
		if (myPanel.IsBomb(myPanel.mouseDownGridX, myPanel.mouseDownGridY) && myPanel.mouseDownGridX == 0 && myPanel.mouseDownGridY == 0) {
			JOptionPane.showMessageDialog(null, "You exploded a mine.");	
			} 
		int counterWhite = 0;
		int counterBlackOrRed = 0;
		for (int i = 0; i < 9; i++) {
			for( int j = 0; j < 9; j++) {
				if (myPanel.colorArray[i][j] == Color.WHITE) {counterWhite++;}
				if (myPanel.colorArray[i][j] == Color.BLACK || myPanel.colorArray[i][j] == Color.RED ) {counterWhite++;}
				if (counterBlackOrRed == 15 && counterWhite == 66) {
					JOptionPane.showMessageDialog(null, "Congratulations! You have won.");
				}
					
			}
		}
		}
	}