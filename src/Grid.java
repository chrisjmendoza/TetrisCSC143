import java.awt.*;

/**
 * This is the Tetris board represented by a (HEIGHT - by - WIDTH) matrix of
 * Squares.
 * <p>
 * The upper left Square is at (0,0). The lower right Square is at (HEIGHT -1,
 * WIDTH -1).
 * <p>
 * Given a Square at (x,y) the square to the left is at (x-1, y) the square
 * below is at (x, y+1)
 * <p>
 * Each Square has a color. A white Square is EMPTY; any other color means that
 * spot is occupied (i.e. a piece cannot move over/to an occupied square). A
 * grid will also remove completely full rows.
 *
 * @author CSC 143
 */
public class Grid {
    // Width and Height of Grid in number of squares
    public static final int HEIGHT = 20;
    public static final int WIDTH = 10;
    public static final int LEFT = 100; // pixel position of left of grid
    public static final int TOP = 50; // pixel position of top of grid
    public static final Color EMPTY = Color.WHITE;
    private static final int BORDER = 5;
    private Square[][] board;

    /**
     * Creates the grid
     */
    public Grid() {
        board = new Square[HEIGHT][WIDTH];

        // put squares in the board
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                board[row][col] = new Square(this, row, col, EMPTY, false);

            }
        }
    }

    /**
     * Returns true if the location (row, col) on the grid is occupied
     *
     * @param row the row in the grid
     * @param col the column in the grid
     */
    public boolean isSet(int row, int col) {
        if(col >= WIDTH) return true;
        return !board[row][col].getColor().equals(EMPTY);
    }

    /**
     * Changes the color of the Square at the given location
     *
     * @param row the row of the Square in the Grid
     * @param col the column of the Square in the Grid
     * @param c   the color to set the Square
     * @throws IndexOutOfBoundsException if row < 0 || row>= HEIGHT || col < 0 || col >= WIDTH
     */
    public void set(int row, int col, Color c) {
        try {
            if (row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH) {
                throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("This is your problem: " + ex.getMessage()
                    + "\nHere is where it happened:\n");
            ex.printStackTrace();
        }
        board[row][col].setColor(c);
    }

    /**
     * Checks for and remove all solid rows of squares.
     * <p>
     * If a solid row is found and removed, all rows above it are moved down and
     * the top row set to empty
     */
    public void checkRows() {
        for (int i = HEIGHT - 1; i >= 0; i--) {
            for (int j = 0; j <= WIDTH - 1; j++) {
                // Create a boolean flag that turns false if the color is not set
                boolean check = true;
                // Check the board color for a white space.
                // If found, change boolean flag and end this row check
                if (board[i][j].getColor().equals(EMPTY)) {
                    check = false;
                    // Move the check to the end of the row
                    j = WIDTH;
                }
                // If it reaches the end of the columns and check is still true,
                // start copying the row above
                if (j == WIDTH - 1 && check) {
                    for (int k = i; k >= 0; k--) {
                        for (int l = 0; l < WIDTH; l++) {
                            // Once the top of the board is reached, make the last row blank
                            if (k != 0) {
                                board[k][l].setColor(board[k - 1][l].getColor());
                            } else {
                                board[k][l].setColor(EMPTY);
                            }
                        }
                    }
                    i++;
                }
            }
        }
    }

    /**
     * Draws the grid on the given Graphics context
     */
    public void draw(Graphics g) {

        // draw the edges as rectangles: left, right in blue then bottom in red
        g.setColor(Color.BLUE);
        g.fillRect(LEFT - BORDER, TOP, BORDER, HEIGHT * Square.HEIGHT);
        g.fillRect(LEFT + WIDTH * Square.WIDTH, TOP, BORDER, HEIGHT
                * Square.HEIGHT);
        g.setColor(Color.RED);
        g.fillRect(LEFT - BORDER, TOP + HEIGHT * Square.HEIGHT, WIDTH
                * Square.WIDTH + 2 * BORDER, BORDER);

        // draw all the squares in the grid
        // empty ones first (to avoid masking the black lines of the pieces that
        // have already fallen)
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                if (board[r][c].getColor().equals(EMPTY)) {
                    board[r][c].draw(g);
                }
            }
        }
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                if (!board[r][c].getColor().equals(EMPTY)) {
                    board[r][c].draw(g);
                }
            }
        }
    }
}