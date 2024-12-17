package gameTestBUTO;

import javax.swing.*;

public class GameWindow {
    public GameWindow(GamePanel gamePanel) {
        JFrame frame = new JFrame("Fighting Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(gamePanel);
        frame.setVisible(true);
    }
}
