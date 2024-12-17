package gameTestBUTO;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Game {
    private Player player1, player2;
    private Platform platform;
    private GamePanel gamePanel;
    private int round;
    private boolean isRoundActive;

    public Game() {
        platform = new Platform(0, 500, 800, 20); // A platform at the bottom of the screen
        player1 = new Player(100, 400, platform, true); // Player 1
        player2 = new Player(600, 400, platform, false); // Player 2
        round = 1;
        isRoundActive = true;

        // Create the GamePanel and pass this Game instance
        gamePanel = new GamePanel(this);

        // Set up key inputs
        gamePanel.addKeyListener(new KeyInputs(player1, player2));
        gamePanel.setFocusable(true);
    }

    public void startGameLoop() {
        new Thread(() -> {
            double timePerFrame = 1000000000.0 / 60.0;
            double timePerUpdate = 1000000000.0 / 60.0;

            long previousTime = System.nanoTime();
            double deltaU = 0, deltaF = 0;

            while (true) {
                long currentTime = System.nanoTime();
                deltaU += (currentTime - previousTime) / timePerUpdate;
                deltaF += (currentTime - previousTime) / timePerFrame;
                previousTime = currentTime;

                // Update game logic
                while (deltaU >= 1) {
                    update();
                    deltaU--;
                }

                // Render game graphics
                if (deltaF >= 1) {
                    gamePanel.repaint();
                    deltaF--;
                }
            }
        }).start();
    }

    public void update() {
        if (player1.isDead() || player2.isDead()) {
            if (isRoundActive) {
                endRound();
            }
        } else {
            player1.update();
            player2.update();
        }
    }

    private void endRound() {
        isRoundActive = false;
        if (player1.isDead()) {
            // Player 2 wins the round
            System.out.println("Player 2 wins Round " + round);
        } else if (player2.isDead()) {
            // Player 1 wins the round
            System.out.println("Player 1 wins Round " + round);
        }

        // Wait for a moment before starting the next round
        try {
            Thread.sleep(2000);  // 2-second pause before respawn
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Reset for the next round
        round++;
        player1.respawn();
        player2.respawn();
        isRoundActive = true;
    }

    public void render(Graphics g) {
        platform.render(g);
        player1.render(g);
        player2.render(g);

        // Display current round number
        g.setColor(Color.BLACK);
        g.drawString("Round: " + round, 20, 20);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
