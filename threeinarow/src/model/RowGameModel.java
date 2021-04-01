package model;


public class RowGameModel 
{
    public static final String GAME_END_NOWINNER = "Game ends in a draw";

    public RowBlockModel[][] blocksData = new RowBlockModel[3][3];

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


    public RowGameModel() {
        super();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                blocksData[row][col] = new RowBlockModel(this);
            } // end for col
        } // end for row
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
    }

    public void decreaseMove(){
        movesLeft--;
    }
    public int getMovesLeft(){
        return movesLeft;
    }
    public void resetGame(){
        player = PLAYER1;
        movesLeft = 9;
        setFinalResult(null);
    }


    public String getFinalResult() {
	return this.finalResult;
    }

    public void setFinalResult(String finalResult) {
	this.finalResult = finalResult;
    }
}
