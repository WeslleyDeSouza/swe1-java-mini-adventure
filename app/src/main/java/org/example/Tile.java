package org.example;

import java.awt.Color;

public class Tile {

    private final Color color;
    private final boolean solid;

    public Tile(Color color, boolean solid) {
        this.color = color;
        this.solid = solid;
    }

    public Color getColor() { return color; }
    public boolean isSolid() { return solid; }
}
