package gameTestBUTO;

import java.awt.Color;
import java.awt.Graphics;

public class Platform {
    private int x, y, width, height;

    public Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(Graphics g) {
        // Platform as a gray rectangle
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
