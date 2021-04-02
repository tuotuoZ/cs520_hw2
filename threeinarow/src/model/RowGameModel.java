package model;


import view.RowGameGUI;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RowGameModel
{
    public static final String GAME_END_NOWINNER = "Game ends in a draw";

    public RowBlockModel[][] blocksData;
    private RowGameGUI view;

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    /**
     * The current player taking their turn
     */

    // Type safety: replaced the magic string to constant.
    private static final String PLAYER1 = "1";
    private static final String PLAYER2 = "2";

    // Information hiding: changed player to private
    private String player = PLAYER1;
    private int movesLeft = 9;

    private String finalResult = null;

    // New fields for the nXm feature
    private final int dimRow, dimCol;

    public RowGameModel(int inputRow, int inputCol) {
        super();
        dimRow = inputRow;
        dimCol = inputCol;
        // New update for the nXm grid.
        blocksData =  new RowBlockModel[dimRow][dimCol];
        movesLeft = dimRow * dimCol;
        // Initialize a JButton for each cell of the 3x3 game board.
        for(int row = 0; row<dimRow; row++) {
            for(int column = 0; column<dimCol ;column++) {
                blocksData[row][column] = new RowBlockModel(this);
                changes.firePropertyChange();
            }
        }

    }



    // public method to access private fields
    public String getPlayer(){
        return player;
    }

    public void changePlayer(){
        if (player == PLAYER1){
            player = PLAYER2;
        }else{
            player = PLAYER1;
        }
        changes.firePropertyChange("player", "old", player);
    }

    public void decreaseMove(){
        movesLeft--;
    }
    public int getMovesLeft(){
        return movesLeft;
    }
    public void resetGame(){
        player = PLAYER1;
        movesLeft = dimRow * dimCol;
        setFinalResult(null);
    }

    public boolean isPlayerOneTurn(){
        return (((dimRow*dimCol) - movesLeft) % 2 == 0);
    }

    public String getFinalResult() {
	return this.finalResult;
    }

    public void setFinalResult(String finalResult) {
	    this.finalResult = finalResult;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        changes.addPropertyChangeListener(l);
    }
    public void removePropertyChangeListener(PropertyChangeListener l) {
        changes.removePropertyChangeListener(l);
    }



}
