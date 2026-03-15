package org.example;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player {

    private int x, y;
    private final int speed = 4;
    private final KeyHandler keyHandler;

    // Blickrichtung fuer spaetere Sprite-Animationen
    private String direction = "down";

    public Player(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        // Startposition: Mitte des Screens
        this.x = GamePanel.SCREEN_WIDTH / 2 - GamePanel.TILE_SIZE / 2;
        this.y = GamePanel.SCREEN_HEIGHT / 2 - GamePanel.TILE_SIZE / 2;
    }

    public void update() {
        // Nur bewegen wenn eine Taste gedrueckt ist
        if (keyHandler.upPressed || keyHandler.downPressed ||
            keyHandler.leftPressed || keyHandler.rightPressed) {

            if (keyHandler.upPressed) {
                direction = "up";
                y -= speed;
            }
            if (keyHandler.downPressed) {
                direction = "down";
                y += speed;
            }
            if (keyHandler.leftPressed) {
                direction = "left";
                x -= speed;
            }
            if (keyHandler.rightPressed) {
                direction = "right";
                x += speed;
            }
        }

        // Bildschirmgrenzen einhalten
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > GamePanel.SCREEN_WIDTH - GamePanel.TILE_SIZE) {
            x = GamePanel.SCREEN_WIDTH - GamePanel.TILE_SIZE;
        }
        if (y > GamePanel.SCREEN_HEIGHT - GamePanel.TILE_SIZE) {
            y = GamePanel.SCREEN_HEIGHT - GamePanel.TILE_SIZE;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.CYAN);
        g2.fillRect(x, y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public String getDirection() { return direction; }
}
