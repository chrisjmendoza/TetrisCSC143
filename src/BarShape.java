import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * An L-Shape piece in the Tetris Game.
 *
 * This piece is made up of 4 squares in the following configuration:
 *
 * Sq <br>
 * Sq <br>
 * Sq Sq <br>
 *
 * The game piece "floats above" the Grid. The (row, col) coordinates are the
 * location of the middle Square on the side within the Grid
 *
 * @author CSC 143
 */
public class BarShape extends AbstractPiece {

    /**
     * Creates an L-Shape piece. See class description for actual location of r
     * and c
     *
     * @param r
     *            row location for this piece
     * @param c
     *            column location for this piece
     * @param g
     *            the grid for this game piece
     *
     */
    public BarShape(int r, int c, Grid g) {
        super();
        grid = g;
        square = new Square[PIECE_COUNT];
        ableToMove = true;

        // Create the squares
        square[0] = new Square(g, r, c - 1, Color.CYAN, true);
        square[1] = new Square(g, r, c, Color.CYAN, true);
        square[2] = new Square(g, r, c + 1, Color.CYAN, true);
        square[3] = new Square(g, r, c + 2, Color.CYAN, true);
    }
}
