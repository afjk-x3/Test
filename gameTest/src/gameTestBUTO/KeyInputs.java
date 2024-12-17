package gameTestBUTO;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInputs extends KeyAdapter {
    private Player player1;
    private Player player2;

    public KeyInputs(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Player 1 controls
        if (key == KeyEvent.VK_A) {  // Player 1 moves left
            player1.moveLeft();
        }
        if (key == KeyEvent.VK_D) {  // Player 1 moves right
            player1.moveRight();
        }
        if (key == KeyEvent.VK_W) {  // Player 1 jumps
            player1.jump();
        }
        if (key == KeyEvent.VK_F) {  // Player 1 attacks
            player1.attack(player2);
        }
        if (key == KeyEvent.VK_S) {  // Player 1 blocks
            player1.block();
        }

        // Player 2 controls
        if (key == KeyEvent.VK_LEFT) {  // Player 2 moves left
            player2.moveLeft();
        }
        if (key == KeyEvent.VK_RIGHT) {  // Player 2 moves right
            player2.moveRight();
        }
        if (key == KeyEvent.VK_UP) {  // Player 2 jumps
            player2.jump();
        }
        if (key == KeyEvent.VK_K) {  // Player 2 attacks
            player2.attack(player1);
        }
        if (key == KeyEvent.VK_DOWN) {  // Player 2 blocks
            player2.block();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        // Stop movement when key is released
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
            player1.stop();
        }

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            player2.stop();
        }
    }
}
