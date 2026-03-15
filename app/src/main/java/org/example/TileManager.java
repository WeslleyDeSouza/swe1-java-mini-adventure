package org.example;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    private final Tile[] tileTypes;
    private int[][] mapData;
    private int mapCols;
    private int mapRows;

    public TileManager() {
        tileTypes = new Tile[10];
        // 0 = Boden (begehbar)
        tileTypes[0] = new Tile(new Color(60, 60, 60), false);
        // 1 = Wand (solid)
        tileTypes[1] = new Tile(new Color(120, 80, 50), true);
        // 2 = Tuer (begehbar)
        tileTypes[2] = new Tile(new Color(180, 140, 60), false);
        // 3 = Schreibtisch (solid)
        tileTypes[3] = new Tile(new Color(100, 60, 30), true);
        // 4 = Teppich (begehbar)
        tileTypes[4] = new Tile(new Color(80, 40, 40), false);

        loadMap("/maps/office.txt");
    }

    private void loadMap(String path) {
        try (InputStream is = getClass().getResourceAsStream(path);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            // Erste Zeile: Spalten und Zeilen
            String[] size = br.readLine().trim().split("\\s+");
            mapCols = Integer.parseInt(size[0]);
            mapRows = Integer.parseInt(size[1]);
            mapData = new int[mapRows][mapCols];

            for (int row = 0; row < mapRows; row++) {
                String[] tokens = br.readLine().trim().split("\\s+");
                for (int col = 0; col < mapCols; col++) {
                    mapData[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Map konnte nicht geladen werden: " + path, e);
        }
    }

    public void draw(Graphics2D g2) {
        for (int row = 0; row < mapRows; row++) {
            for (int col = 0; col < mapCols; col++) {
                int tileIndex = mapData[row][col];
                Tile tile = tileTypes[tileIndex];

                int x = col * GamePanel.TILE_SIZE;
                int y = row * GamePanel.TILE_SIZE;

                g2.setColor(tile.getColor());
                g2.fillRect(x, y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
            }
        }
    }

    // Prueft ob das Tile an der gegebenen Pixel-Position solid ist
    public boolean isSolid(int pixelX, int pixelY) {
        int col = pixelX / GamePanel.TILE_SIZE;
        int row = pixelY / GamePanel.TILE_SIZE;

        if (col < 0 || col >= mapCols || row < 0 || row >= mapRows) {
            return true; // Ausserhalb der Map = solid
        }

        return tileTypes[mapData[row][col]].isSolid();
    }

    public int getMapCols() { return mapCols; }
    public int getMapRows() { return mapRows; }
}
