package org.example;

public class CollisionChecker {

    private final TileManager tileManager;

    public CollisionChecker(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    // Prueft ob der Spieler sich in die gewuenschte Richtung bewegen kann
    public boolean canMove(int x, int y, int size, String direction, int speed) {
        // Kleiner Inset damit der Spieler nicht pixelgenau an Waenden haengt
        int inset = 4;

        int left = x + inset;
        int right = x + size - inset - 1;
        int top = y + inset;
        int bottom = y + size - inset - 1;

        switch (direction) {
            case "up":
                top -= speed;
                return !tileManager.isSolid(left, top) && !tileManager.isSolid(right, top);
            case "down":
                bottom += speed;
                return !tileManager.isSolid(left, bottom) && !tileManager.isSolid(right, bottom);
            case "left":
                left -= speed;
                return !tileManager.isSolid(left, top) && !tileManager.isSolid(left, bottom);
            case "right":
                right += speed;
                return !tileManager.isSolid(right, top) && !tileManager.isSolid(right, bottom);
            default:
                return true;
        }
    }
}
