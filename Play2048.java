/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  GameState
 *  SimpleCanvas
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Play2048
implements KeyListener {
    private static final int SQUARE_SIZE = 100;
    private Color GRID_COLOR = new Color(26, 188, 156);
    private Color[] TEXT_COLORS = new Color[]{new Color(59, 59, 59), new Color(52, 152, 219), Color.BLUE, Color.YELLOW};
    private Color BACK_COLOR = new Color(236, 239, 240);
    private GameState gameState;
    private SimpleCanvas sc;

    public Play2048(String file) {
        try {
            this.gameState = new GameState(file);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.setUp();
    }

    private void setUp() {
        int width = this.gameState.getBoard()[0].length;
        int height = this.gameState.getBoard().length;
        this.sc = new SimpleCanvas("2048", width * 100 + 10, height * 100 + 100, this.BACK_COLOR);
        this.sc.getGraphics().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.sc.addKeyListener((KeyListener)this);
        this.drawGrid();
        this.drawNums();
        Font font = new Font("Arial", 1, 50);
        this.sc.setFont(font);
        FontMetrics metrics = this.sc.getGraphics().getFontMetrics(font);
        this.sc.drawString("2048", 280, 475, this.TEXT_COLORS[1]);
    }

    private void drawNums() {
        int width = this.gameState.getBoard().length;
        int height = this.gameState.getBoard()[0].length;
        int y = 0;
        String number = " ";
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Font font = new Font("Arial", 1, 35);
                this.sc.setFont(font);
                FontMetrics metrics = this.sc.getGraphics().getFontMetrics(font);
                number = Integer.toString(this.gameState.getBoard()[i][j].getSquare());
                if (number.length() > 4) {
                    font = new Font("Arial", 1, 25);
                    this.sc.setFont(font);
                    metrics = this.sc.getGraphics().getFontMetrics(font);
                } else if (Integer.valueOf(number) == 0) {
                    number = "";
                }
                int x = 5 + 100 * j + 50 - metrics.stringWidth(number) / 2;
                y = 68 + 100 * i;
                this.sc.drawString(number, x, y, this.TEXT_COLORS[0]);
            }
        }
        this.sc.drawRectangle(0, 420, 250, 500, this.BACK_COLOR);
        this.sc.drawString("Score : " + String.valueOf(this.gameState.getScore()), 20, 470, this.TEXT_COLORS[1]);
    }

    private void drawGrid() {
        int width = this.gameState.getBoard().length;
        int height = this.gameState.getBoard()[0].length;
        Color ZEROCOLOR = new Color(22, 126, 156);
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                String number = Integer.toString(this.gameState.getBoard()[j][i].getSquare());
                Color RECTCOLOR = this.chooseColor(Integer.parseInt(number));
                if (Integer.parseInt(number) == 0) {
                    this.sc.drawRectangle(6 + 100 * i, 7 + 100 * j, 100 * i + 100, 100 * j + 100 + 2, ZEROCOLOR);
                    continue;
                }
                if (Integer.parseInt(number) <= 0) continue;
                this.sc.drawRectangle(6 + 100 * i, 7 + 100 * j, 100 * i + 100, 100 * j + 100 + 2, RECTCOLOR);
            }
        }
    }

    private Color chooseColor(int i) {
        Color Emerald = new Color(231, 223, 134);
        Color BelizeHole = new Color(151, 206, 104);
        Color Carrot = new Color(240, 79, 3);
        Color MidnightBlue = new Color(116, 116, 204);
        Color SunFlower = new Color(89, 188, 251);
        Color Alizarin = new Color(245, 213, 69);
        Color Wisteria = new Color(46, 204, 113);
        Color Silver = new Color(254, 198, 6);
        Color Concrete = new Color(151, 206, 104);
        Color Orange = new Color(136, 112, 255);
        Color Amethyst = new Color(255, 215, 0);
        Color[] RECT_COLORS = new Color[]{Emerald, BelizeHole, Carrot, Amethyst, SunFlower, Alizarin, Wisteria, Silver, Concrete, Orange, Amethyst};
        int[] Num = new int[]{2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};
        for (int j = 0; j < Num.length; ++j) {
            if (i == Num[j]) {
                return RECT_COLORS[j];
            }
            if (j != Num.length - 1) continue;
            return RECT_COLORS[Num.length - 1];
        }
        return RECT_COLORS[Num.length - 1];
    }

    private void rungame() {
        this.drawGrid();
        this.drawNums();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 37 && !this.gameState.gameOver()) {
            this.gameState.left();
            this.rungame();
        }
        if (key == 39 && !this.gameState.gameOver()) {
            this.gameState.right();
            this.rungame();
        }
        if (key == 38 && !this.gameState.gameOver()) {
            this.gameState.up();
            this.rungame();
        }
        if (key == 40 && !this.gameState.gameOver()) {
            this.gameState.down();
            this.rungame();
        }
        Font font = new Font("Arial", 1, 35);
        this.sc.setFont(font);
        FontMetrics metrics = this.sc.getGraphics().getFontMetrics(font);
        if (this.gameState.gameOver()) {
            this.sc.drawString("Game Over", 200 - metrics.stringWidth("Game Over") / 2, 200, this.TEXT_COLORS[0]);
            this.sc.drawRectangle(200 - metrics.stringWidth("Game Over") / 2, 210, 200 - metrics.stringWidth("Game Over") / 2 + metrics.stringWidth("Game Over"), 250, this.chooseColor(2048));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    public Play2048(double p) {
        try {
            this.gameState = new GameState(p);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.setUp();
    }

    public Play2048() {
        try {
            this.gameState = new GameState(0.3);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.setUp();
    }

    public void play2048() {
        while (!this.gameState.gameOver()) {
            boolean score = false;
            if (this.aiplay() == 1 && !this.gameState.gameOver()) {
                this.gameState.left();
                this.rungame();
            }
            if (this.aiplay() == 2 && !this.gameState.gameOver()) {
                this.gameState.right();
                this.rungame();
            }
            if (this.aiplay() == 3 && !this.gameState.gameOver()) {
                this.gameState.up();
                this.rungame();
            }
            if (this.aiplay() != 4 || this.gameState.gameOver()) continue;
            this.gameState.down();
            this.rungame();
        }
    }

    private int findzeros(Square[][] board) {
        int amtzeros = 0;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                if (board[i][j].getSquare() != 0) continue;
                ++amtzeros;
            }
        }
        return amtzeros;
    }

    public int aiplay() {
        int i;
        GameState brain = this.gameState.clonegame(this.gameState);
        Random rand = new Random();
        ArrayList<Integer> FirstMove = new ArrayList<Integer>();
        ArrayList<Integer> Max = new ArrayList<Integer>();
        ArrayList<Integer> Score = new ArrayList<Integer>();
        for (int i2 = 0; i2 < 1000; ++i2) {
            while (!brain.gameOver()) {
                int randomNum = rand.nextInt(4) + 1;
                FirstMove.add(randomNum);
                if (randomNum == 1 && !brain.gameOver()) {
                    brain.left();
                }
                if (randomNum == 2 && !brain.gameOver()) {
                    brain.right();
                }
                if (randomNum == 3 && !brain.gameOver()) {
                    brain.up();
                }
                if (randomNum != 4 || brain.gameOver()) continue;
                brain.down();
            }
            Max.add(this.findmax(brain.getBoard()));
            Score.add(brain.getScore());
        }
        int maxvalue = (Integer)Collections.max(Max);
        for (i = 0; i < FirstMove.size(); ++i) {
            if (Max.get(i) == Collections.max(Max)) continue;
            FirstMove.remove(i);
            Max.remove(i);
            Score.remove(i);
        }
        for (i = 0; i < FirstMove.size(); ++i) {
            System.out.println("" + i + " " + FirstMove.get(i) + " " + Max.get(i) + " " + Score.get(i));
        }
        return (Integer)Collections.max(FirstMove);
    }

    public Play2048(int i) {
        try {
            this.gameState = new GameState(0.1);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private int findmax(Square[][] board) {
        int maxValue = 0;
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                if (board[i][j].getSquare() <= maxValue) continue;
                maxValue = board[i][j].getSquare();
            }
        }
        return maxValue;
    }
}