import java.awt.*;
import java.util.Random;

/**
 * Manages the game Tetris. Keeps track of the current piece and the grid.
 * Updates the display whenever the state of the game has changed.
 *
 * @author CSC 143
 */
public class Game {

    private Grid grid; // the grid that makes up the Tetris board

    private Tetris display; // the visual for the Tetris game

    private Piece piece; // the current piece that is dropping

    private boolean isOver; // has the game finished?

    /**
     * Creates a Tetris game
     *
     * @param display
     *            the display
     */
    public Game(Tetris display) {
        grid = new Grid();
        this.display = display;
        randPiece();
        isOver = false;
    }

    /**
     * Draws the current state of the game
     *
     * @param g
     *            the Graphics context on which to draw
     */
    public void draw(Graphics g) {
        grid.draw(g);
        if (piece != null) {
            piece.draw(g);
        }
    }

    /**
     * Moves the piece in the given direction
     *
     * @param direction
     *            the direction to move
     */
    public void movePiece(Direction direction) {
        if (piece != null) {
            if (direction == Direction.UP) {
                piece.rotate();
            } else {
                piece.move(direction);
            }
        }
        updatePiece();
        display.update();
        grid.checkRows();
    }

    /**
     * Returns true if the game is over
     */
    public boolean isGameOver() {
        // game is over if the piece occupies the same space as some non-empty
        // part of the grid. Usually happens when a new piece is made
        if (piece == null) {
            return false;
        }

        // check if game is already over
        if (isOver) {
            return true;
        }

        // check every part of the piece
        Point[] p = piece.getLocations();
        for (int i = 0; i < p.length; i++) {
            if (grid.isSet((int) p[i].getX(), (int) p[i].getY())) {
                isOver = true;
                return true;
            }
        }
        return false;
    }

    private Piece randPiece() {
        Random rand = new Random();
        switch (rand.nextInt(7)) {
            case 0:
                piece = new LShape(1, Grid.WIDTH / 2 - 1, grid);
                break;
            case 1:
                piece = new ZShape(1, Grid.WIDTH / 2 - 1, grid);
                break;
            case 2:
                piece = new JShape(1, Grid.WIDTH / 2 - 1, grid);
                break;
            case 3:
                piece = new SquareShape(1, Grid.WIDTH / 2 - 1, grid);
                break;
            case 4:
                piece = new TShape(1, Grid.WIDTH / 2 - 1, grid);
                break;
            case 5:
                piece = new SShape(1, Grid.WIDTH / 2 - 1, grid);
                break;
            case 6:
                piece = new BarShape(1, Grid.WIDTH / 2 - 1, grid);
                break;
        }
        return piece;
    }

    /** Updates the piece */
    private void updatePiece() {
        if (piece == null) {
            randPiece();
        }

        // set Grid positions corresponding to frozen piece
        // and then release the piece
        else if (!piece.canMove(Direction.DOWN)) {
            Point[] p = piece.getLocations();
            Color c = piece.getColor();
            for (int i = 0; i < p.length; i++) {
                grid.set((int) p[i].getX(), (int) p[i].getY(), c);
            }
            piece = null;
        }
    }
}