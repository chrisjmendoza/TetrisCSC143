import java.awt.*;

/**
 * Created by Chris Mendoza
 * on 10/26/16 for practicing the Java homework of Tetris.
 */
public abstract class AbstractPiece implements Piece {

    // number of squares in one Tetris game piece
    protected static final int PIECE_COUNT = 4;
    protected boolean ableToMove; // can this piece move
    protected boolean ableToRotate; // can this piece rotate
    protected Square[] square; // the squares that make up this piece
    // Made up of PIECE_COUNT squares
    protected Grid grid; // the board this piece is on

    public void draw(Graphics g) {
        for (int i = 0; i < PIECE_COUNT; i++) {
            square[i].draw(g);
        }
    }

    /**
     * Moves the piece if possible Freeze the piece if it cannot move down
     * anymore
     *
     * @param direction
     *            the direction to move
     */
    public void move(Direction direction) {
        if (canMove(direction)) {
            for (int i = 0; i < PIECE_COUNT; i++)
                square[i].move(direction);
        }
        // if we couldn't move, see if because we're at the bottom
        else if (direction == Direction.DOWN) {
            ableToMove = false;
        }
    }

    /**
     * Returns the (row,col) grid coordinates occupied by this Piece
     *
     * @return an Array of (row,col) Points
     */
    public Point[] getLocations() {
        Point[] points = new Point[PIECE_COUNT];
        for (int i = 0; i < PIECE_COUNT; i++) {
            points[i] = new Point(square[i].getRow(), square[i].getCol());
        }
        return points;
    }

    /**
     * Return the color of this piece
     */
    public Color getColor() {
        // all squares of this piece have the same color
        return square[0].getColor();
    }

    /**
     * Returns if this piece can move in the given direction
     *
     */
    public boolean canMove(Direction direction) {
        if (!ableToMove) return false;

        // Each square must be able to move in that direction
        boolean answer = true;
        for (int i = 0; i < PIECE_COUNT; i++) {
            answer = answer && square[i].canMove(direction);
            if(!answer) return false;
        }
        return answer;
    }

    public boolean canRotate() {
        if (!ableToRotate) return false;

        // Each square must be able to rotate in that direction
        boolean answer = true;

        // store current piece coordinates
        int[] xOrigin = new int[PIECE_COUNT];
        int[] yOrigin = new int[PIECE_COUNT];

        int[] rotatedX = new int[PIECE_COUNT];
        int[] rotatedY = new int[PIECE_COUNT];

        for (int i = 0; i < PIECE_COUNT; i++) {
            xOrigin[i] = square[i].getCol();
            yOrigin[i] = square[i].getRow();
        }

        for (int i = 0; i < PIECE_COUNT; i++) {

            int tempX = xOrigin[i] - xOrigin[1];
            int tempY = yOrigin[i] - yOrigin[1];

            rotatedX[i] = (int) Math.round(tempX * Math.cos(Math.PI / 2) - tempY * Math.sin(Math.PI / 2));
            rotatedY[i] = (int) Math.round(tempX * Math.sin(Math.PI / 2) + tempY * Math.cos(Math.PI / 2));

            rotatedX[i] += xOrigin[i];
            rotatedY[i] += yOrigin[i];
        }

        for (int i = 0; i < PIECE_COUNT; i++) {
            answer = answer && square[i].canRotate(rotatedX[i], rotatedY[i]);
            if (!answer) break;
        }

        return answer;
    }

    public void rotate() {
        if (canRotate()) {
            // store current piece coordinates
            int[] xOrigin = new int[PIECE_COUNT];
            int[] yOrigin = new int[PIECE_COUNT];

            int[] rotatedX = new int[PIECE_COUNT];
            int[] rotatedY = new int[PIECE_COUNT];

            for (int i = 0; i < PIECE_COUNT; i++) {
                xOrigin[i] = square[i].getCol();
                yOrigin[i] = square[i].getRow();
            }

            for (int i = 0; i < PIECE_COUNT; i++) {

                int tempX = xOrigin[i] - xOrigin[1];
                int tempY = yOrigin[i] - yOrigin[1];

                rotatedX[i] = (int) Math.round(tempX * Math.cos(Math.PI / 2) - tempY * Math.sin(Math.PI / 2));
                rotatedY[i] = (int) Math.round(tempX * Math.sin(Math.PI / 2) + tempY * Math.cos(Math.PI / 2));

                rotatedX[i] += xOrigin[1];
                rotatedY[i] += yOrigin[1];

                square[i].setCol(rotatedX[i]);
                square[i].setRow(rotatedY[i]);

            }

        } else {
            ableToRotate = false;
        }

    }

}
