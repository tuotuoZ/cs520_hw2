package view;

import controller.RowGameController;
import model.CoordinateButtonModel;
import model.RowGameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RowGameBoardView implements RowGameView
{
    public CoordinateButtonModel[][] blocks;

    public JPanel gamePanel = new JPanel(new FlowLayout());
    private final int dimRow, dimCol;
    
    public RowGameBoardView(RowGameController gameController, int inputRow, int inputCol) {
    	super();
        dimRow = inputRow;
        dimCol = inputCol;
        JPanel game = new JPanel(new GridLayout(dimRow, dimCol));
        gamePanel.add(game, BorderLayout.CENTER);
	    blocks = new CoordinateButtonModel[dimRow][dimCol];
       // Initialize a JButton for each cell of the 3x3 game board.
        for(int row = 0; row<dimRow; row++) {
            for(int column = 0; column<dimCol ;column++) {
                blocks[row][column] = new CoordinateButtonModel(row, column);
                blocks[row][column].setPreferredSize(new Dimension(75,75));
                game.add(blocks[row][column]);
                blocks[row][column].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
			            gameController.move((CoordinateButtonModel) e.getSource());
                    }
                });
            }
        }	
    }

    /**
     * Updates the game view after the game model
     * changes state.
     *
     * @param gameModel The current game model
     */
    public void update(RowGameModel gameModel) {
	for (int row = 0; row < dimRow; row++) {
	    for (int column = 0; column < dimCol; column++) {
		this.updateBlock(gameModel, row, column);
	    } // end for col
	} // end for row	
    }

    /**
     * Updates the block view at the given row and column 
     * after the game model changes state.
     *
     * @param gameModel The game model
     * @param row The row that contains the block
     * @param col The column that contains the block
     */
    protected void updateBlock(RowGameModel gameModel, int row, int col) {
	blocks[row][col].setText(gameModel.blocksData[row][col].getContents());
	blocks[row][col].setEnabled(gameModel.blocksData[row][col].getIsLegalMove());	
    }
}
