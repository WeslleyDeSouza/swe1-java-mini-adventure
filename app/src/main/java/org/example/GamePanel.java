package org.example;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {

    // Tile-Einstellungen
    public static final int TILE_SIZE = 48;
    public static final int SCREEN_COLS = 16;
    public static final int SCREEN_ROWS = 12;
    public static final int SCREEN_WIDTH = TILE_SIZE * SCREEN_COLS;   // 768
    public static final int SCREEN_HEIGHT = TILE_SIZE * SCREEN_ROWS;  // 576

    // Game Loop
    private static final int TARGET_FPS = 60;
    private Thread gameThread;
    private boolean running;

    // Input, Map & Player
    private final KeyHandler keyHandler = new KeyHandler();
    private final TileManager tileManager = new TileManager();
    private final CollisionChecker collisionChecker = new CollisionChecker(tileManager);
    private final Player player = new Player(keyHandler);

    public GamePanel() {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(keyHandler);
        player.setCollisionChecker(collisionChecker);
    }

    public void startGameLoop() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / TARGET_FPS;
        double delta = 0;
        long lastTime = System.nanoTime();

        // Delta-Time Loop: sammelt vergangene Zeit und triggert genau 60 Updates/Sekunde
        while (running) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            // Wenn genug Zeit fuer den naechsten Frame vergangen ist
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

            // CPU schonen
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void update() {
        player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
}
