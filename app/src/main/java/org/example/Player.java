package org.example;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player {

    private int x, y;
    private final int speed = 4;
    private final KeyHandler keyHandler;
    private CollisionChecker collisionChecker;

    // Blickrichtung fuer spaetere Sprite-Animationen
    private String direction = "down";

    public Player(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        // Startposition: Mitte des Screens
        this.x = GamePanel.SCREEN_WIDTH / 2 - GamePanel.TILE_SIZE / 2;
        this.y = GamePanel.SCREEN_HEIGHT / 2 - GamePanel.TILE_SIZE / 2;
    }

    public void setCollisionChecker(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }

    public void update() {
        if (keyHandler.upPressed) {
            direction = "up";
            if (collisionChecker == null || collisionChecker.canMove(x, y, GamePanel.TILE_SIZE, "up", speed)) {
                y -= speed;
            }
        }
        if (keyHandler.downPressed) {
            direction = "down";
            if (collisionChecker == null || collisionChecker.canMove(x, y, GamePanel.TILE_SIZE, "down", speed)) {
                y += speed;
            }
        }
        if (keyHandler.leftPressed) {
            direction = "left";
            if (collisionChecker == null || collisionChecker.canMove(x, y, GamePanel.TILE_SIZE, "left", speed)) {
                x -= speed;
            }
        }
        if (keyHandler.rightPressed) {
            direction = "right";
            if (collisionChecker == null || collisionChecker.canMove(x, y, GamePanel.TILE_SIZE, "right", speed)) {
                x += speed;
            }
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
