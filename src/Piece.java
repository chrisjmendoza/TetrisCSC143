import java.awt.*;

/**
 * Created by chris on 10/26/16.
 */
public interface Piece {


    /**
     * Draws the piece on the given Graphics context
     */
    public void draw(Graphics g);

    /**
     * Moves the piece if possible Freeze the piece if it cannot move down
     * anymore
     *
     * @param direction
     *            the direction to move
     */
    public void move(Direction direction);

    /**
     * Returns the (row,col) grid coordinates occupied by this Piece
     *
     * @return an Array of (row,col) Points
     */
    public Point[] getLocations();

    /**
     * Return the color of this piece
     */
    public Color getColor();

    /**
     * Returns if this piece can move in the given direction
     */
    public boolean canMove(Direction direction);

    /**
     * The rotation of the piece
     */
    public void rotate();
}
