package gameTestBUTO;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Create the game instance
        Game game = new Game();

        // Create a window for the game
        JFrame window = new JFrame("Fighting Game");

        // Set up the window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);  // Center the window on the screen
        window.add(game.getGamePanel());  // Add the game panel to the window
        window.setVisible(true);

        // Start the game loop
        game.startGameLoop();
    }
}
