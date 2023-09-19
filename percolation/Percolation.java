import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // define instance variables
    private boolean[][] grid;
    private int gridSize; // n-value or the size of the actual grid
    private int openSites; // total number of open sites in the grid
    private int virtualBottom; // the virtual bottom to connect to
    private int virtualTop = 0; // the virtual top (always equal to 0)
    private WeightedQuickUnionUF wQF; // weighted quick union data structure

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        gridSize = n;
        virtualBottom = n * n + 1;
        openSites = 0;
        // +2 comes from virtual top/bottom
        wQF = new WeightedQuickUnionUF(n * n + 2);

        // establish grid to be a new 2D n*n array
        grid = new boolean[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkValidity(row, col);

        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSites++;
        }

        // for cases where it's right below virtual top
        if (row == 0) {
            wQF.union(flatten(row, col), virtualTop);
        }
        // for cases where it's right above virtual bottom
        if (row == gridSize - 1) {
            wQF.union(flatten(row, col), virtualBottom);
        }
        // right side
        if (col + 1 <= gridSize - 1 && isOpen(row, col + 1)) {
            wQF.union(flatten(row, col), flatten(row, col + 1));
        }
        // left side
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            wQF.union(flatten(row, col), flatten(row, col - 1));
        }
        // top side
        if (row + 1 <= gridSize - 1 && isOpen(row + 1, col)) {
            wQF.union(flatten(row, col), flatten(row + 1, col));
        }
        // bottom side
        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            wQF.union(flatten(row, col), flatten(row - 1, col));
        }

    }

    // helper method for checking the validity of the inputs
    private void checkValidity(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize)
            throw new IllegalArgumentException();
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkValidity(row, col);
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkValidity(row, col);
        return (wQF.find(virtualTop) == wQF.find(flatten(row, col)));
    }

    // convert 2D -> 1D for union-find data structures
    private int flatten(int row, int col) {
        return gridSize * row + col + 1;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // if the top is connected to the bottom, then the system percolates
        return wQF.find(virtualTop) == wQF.find(virtualBottom);
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        // create new percolation
        Percolation p = new Percolation(n);
        int argCount = args.length;
        for (int i = 1; argCount >= 2; i += 2) {
            int row = Integer.parseInt(args[i]);
            int col = Integer.parseInt(args[i + 1]);
            if (!p.isOpen(row, col)) {
                p.open(row, col);
                if (p.isFull(row, col)) {
                    StdOut.println("It's full.");
                }
            }
            if (p.percolates()) {
                StdOut.println("Percolates.");
                StdOut.println("" + p.numberOfOpenSites());
            }
            argCount -= 2;
        }
        if (!p.percolates()) {
            StdOut.println("Doesn't percolate.");
        }

    }

}
