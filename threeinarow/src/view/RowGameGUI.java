package view;

import controller.RowGameController;
import model.RowGameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class RowGameGUI implements RowGameView, PropertyChangeListener
{
    public JFrame gui = new JFrame("Three in a Row");
    public RowGameBoardView gameBoardView;
    public JButton reset = new JButton("Reset");
    public RowGameStatusView gameStatusView;
    
    private RowGameController gameController;


    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameGUI(RowGameController gameController, int inputRow, int inputCol) {
        int dimRow, dimCol;
        dimRow = inputRow;
        dimCol = inputCol;
        this.gameController = gameController;

            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setSize(new Dimension(dimCol*150, dimRow*115));
            gui.setResizable(true);

        gameBoardView = new RowGameBoardView(this.gameController, inputRow, inputCol);
            JPanel gamePanel = gameBoardView.gamePanel;

            JPanel options = new JPanel(new FlowLayout());
            options.add(reset);

        gameStatusView = new RowGameStatusView(this.gameController);
            JPanel messages = gameStatusView.messages;

            gui.add(gamePanel, BorderLayout.NORTH);
            gui.add(options, BorderLayout.CENTER);
            gui.add(messages, BorderLayout.SOUTH);

            reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    gameController.resetGame();
                }
            });
    }

    /**
     * Updates the game view after the game model
     * changes state.
     *
     * @param gameModel The current game model
     */
    public void update(RowGameModel gameModel) {
	gameBoardView.update(gameModel);

	gameStatusView.update(gameModel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        update((RowGameModel) evt.getSource());
    }
}
