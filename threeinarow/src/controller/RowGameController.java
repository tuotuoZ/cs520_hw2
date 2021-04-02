package controller;

import model.CoordinateButtonModel;
import model.RowGameModel;
import view.RowGameGUI;

/**
 * Java implementation of the 3 in a row game, using the Swing framework.
 *
 * This quick-and-dirty implementation violates a number of software engineering
 * principles and needs a thorough overhaul to improve readability,
 * extensibility, and testability.
 */
public class RowGameController {
    public static final String GAME_END_NOWINNER = "Game ends in a draw";

    public RowGameModel gameModel;
    public RowGameGUI gameView;
	private final int dimRow, dimCol;
	private final int winNum = 3;

    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameController() {
    	dimRow = 6;
    	dimCol = 6;
		gameModel = new RowGameModel(dimRow,dimCol);
		gameView = new RowGameGUI(this, dimRow, dimCol);
		gameModel.addPropertyChangeListener(gameView);
		resetGame();

    }

    public RowGameModel getModel() {
	return this.gameModel;
    }

    public RowGameGUI getView() {
	return this.gameView;
    }

    public void startUp() {
	gameView.gui.setVisible(true);
    }

	/**
	 * Check if any player wins the game
	 */

	protected boolean checkWinnter(int row, int col) {
		if (checkVerticalWin(row,col)){
			return true;
		}
		else if(checkHorizontalWin(row,col)){
			return true;
		}
		else if (checkLeftDownToRightUp(row,col)){
			return true;
		}
		else if (checkLeftUpToRighDown(row, col)){
			return true;
		}
		return false;
	}

	/**
	 * Check if there is a vertical line for a player to win.
	 *
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean checkVerticalWin(int row, int col){
		String[] blocks = new String[winNum];

		// Start checking from the up two blocks to the newly placed block.
		// Loop to start checking from the newly placed block and down two blocks.
		for (int s = -winNum +1;s < 1; s++){
			try{
				for (int i = 0; i < winNum; i++){
					blocks[i] = gameModel.blocksData[row + s + i][col].getContents();
				}
				if(allEqual(blocks)){
					return true;
				}
			}
			catch (Exception e){
				// Do nothing, continue the loop;
				continue;
			}
		}
		return false;
	}

	/**
	 * Vertical check is same as the horizontal check
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean checkHorizontalWin(int row, int col){
		String[] blocks = new String[winNum];

		// Start checking from the left two blocks to the newly placed block.
		// Loop to from newly placed to right two blocks
		for (int s = -winNum +1;s < 1; s++){
			try{
				for (int i = 0; i < winNum; i++){
					blocks[i] = gameModel.blocksData[row][col + s + i].getContents();
				}
				if(allEqual(blocks)){
					return true;
				}
			}
			catch (Exception e){
				// Do nothing, continue the loop;
				continue;
			}
		}
		return false;
	}

	/**
	 * Check from down left to up right line.
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean checkLeftDownToRightUp(int row, int col){
		String[] blocks = new String[winNum];

		// Start checking from the left up two blocks to the newly placed block.
		// Loop to from newly placed to right two blocks
		for (int s = -winNum +1;s < 1; s++){
			try{
				for (int i = 0; i < winNum; i++){
					blocks[i] = gameModel.blocksData[row + s + i][col - s - i].getContents();
				}
				if(allEqual(blocks)){
					return true;
				}
			}
			catch (Exception e){
				// Do nothing, continue the loop;
				continue;
			}
		}
		return false;
	}



	/**
	 * Check from Left up to right down line
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean checkLeftUpToRighDown(int row, int col){
		String[] blocks = new String[winNum];

		// Start checking from the left up two blocks to the newly placed block.
		// Loop to from newly placed to right two blocks
		for (int s = -winNum +1;s < 1; s++){
			try{
				for (int i = 0; i < winNum; i++){
					blocks[i] = gameModel.blocksData[row + s + i][col + s + i].getContents();
				}
				if(allEqual(blocks)){
					return true;
				}
			}
			catch (Exception e){
				// Do nothing, continue the loop;
				continue;
			}
		}
		return false;
	}

	/**
	 * Check if the collection are all the same
	 *
	 * If only one or zero block passed in, return false
	 *
	 * @param blocks string list for block contents
	 */
	private boolean allEqual(String[] blocks){
		if (blocks.length < 2){
			return true;
		}
		for (int i = 1; i < blocks.length;i++){
			if (blocks[i] != blocks[0]){
				return false;
			}
		}
		return true;
	}

	/**
	 * Draw and update the content for the clicked button in the blcoksData.
	 *
	 * @param row2 The x
	 * @param col2 The y
	 *
	 */
	protected void drawMove(int row2, int col2){
		if(gameModel.isPlayerOneTurn()){
			gameModel.blocksData[row2][col2].setContents("X");
			gameModel.blocksData[row2][col2].setIsLegalMove(false);

		}
		else{
			gameModel.blocksData[row2][col2].setContents("O");
			gameModel.blocksData[row2][col2].setIsLegalMove(false);

		}
		if(row2 > 0){
			gameModel.blocksData[row2-1][col2].setIsLegalMove(true);

		}
	}

	/**
	 * Moves the current player into the given block.
	 *
	 * @param block The block to be moved to by the current player
	 */
	public void move(CoordinateButtonModel block) {
		gameModel.decreaseMove();

		// Update the block
		int col = block.getCol();
		int row = block.getRow();
		drawMove(row, col);
		// Check winner
		if(gameModel.getMovesLeft() < dimRow*dimCol-4){
			if(checkWinnter(row, col)){
				gameModel.setFinalResult(String.format("Player %s wins!", gameModel.getPlayer()));
				endGame();
			}else if(gameModel.getMovesLeft() ==0){
				gameModel.setFinalResult(GAME_END_NOWINNER);
				endGame();
			}
		}
		// Update the player string
		gameModel.changePlayer();

		//gameView.update(gameModel);
	}



	/**
     * Ends the game disallowing further player turns.
     */
    public void endGame() {
		for(int row = 0;row<dimRow;row++) {
			for(int column = 0;column<dimCol;column++) {
			this.gameModel.blocksData[row][column].setIsLegalMove(false);
			}
		}

		//gameView.update(gameModel);
    }

    /**
     * Resets the game to be able to start playing again.
     */
    public void resetGame() {
        for(int row = 0;row<dimRow;row++) {
            for(int column = 0;column<dimCol;column++) {
                gameModel.blocksData[row][column].reset();
		// Enable the bottom row
	        gameModel.blocksData[row][column].setIsLegalMove(row == dimRow-1);
            }
        }
        gameModel.resetGame();

		//gameView.update(gameModel);
    }
}


