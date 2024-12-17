package gameTestBUTO;

import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        this.setFocusable(true);  // Allow the panel to listen to key events
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  // Call the superclass to clear the panel

        // Render the game components (platform, players, etc.)
        game.render(g);
    }
}
