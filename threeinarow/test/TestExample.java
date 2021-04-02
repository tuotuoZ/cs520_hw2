import model.RowBlockModel;
import model.RowGameModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestExample {
    private RowGameModel gameModel;
    private int row, col;
    @Before
    public void setUp() {
        row = 3;
        col = 3;
	    gameModel = new RowGameModel(row,col);
    }

    @After
    public void tearDown() {
	gameModel = null;
    }

    @Test
    public void testNewGame() {
        assertEquals ("1", gameModel.getPlayer());
        assertEquals (row * col , gameModel.getMovesLeft());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBlockViolatesPrecondition() {
	RowBlockModel block = new RowBlockModel(null);
    }
}
