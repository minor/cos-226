import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {

    // create a new pic variable
    private Picture pic;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("Null picture not permitted.");
        }
        pic = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(pic);
    }

    // width of current picture
    public int width() {
        return pic.width();
    }

    // height of current picture
    public int height() {
        return pic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        checkValidity(x, y);
        Color colorL = getColorAt(x - 1, y);
        Color colorR = getColorAt(x + 1, y);
        Color colorT = getColorAt(x, y - 1);
        Color colorB = getColorAt(x, y + 1);
        return Math.sqrt(diff(colorL, colorR) + diff(colorT, colorB));
    }

    // helper method to check validity
    private void checkValidity(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new IllegalArgumentException("Inputs are invalid.");
        }
    }

    // helper method to get the color
    private Color getColorAt(int x, int y) {
        return new Color(pic.getRGB(Math.floorMod(x, width()),
                                    Math.floorMod(y, height())));
    }

    // helper method to get the diff/delta between two colors
    private double diff(Color color1, Color color2) {
        return Math.pow(color1.getRed() - color2.getRed(), 2) +
                Math.pow(color1.getGreen() - color2.getGreen(), 2) +
                Math.pow(color1.getBlue() - color2.getBlue(), 2);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int w = width();
        int h = height();
        double[][] energyGrid = new double[w][h];
        double[][] dist = new double[w][h];
        int[][] pathTo = new int[w][h];
        IndexMinPQ<Double> pq = new IndexMinPQ<>(w * h);

        int[] n = new int[h];
        Arrays.fill(n, -1);

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                energyGrid[x][y] = energy(x, y);

                if (x == 0) {
                    dist[x][y] = energyGrid[x][y];
                    pq.insert(y, dist[x][y]);
                }
                else {
                    dist[x][y] = Double.POSITIVE_INFINITY;
                }
            }
        }

        while (!pq.isEmpty()) {
            int idx = pq.delMin();
            int col = idx / h, row = idx % h;
            if (col == w - 1) return buildHorizontalSeam(pathTo, row, w);
            relaxHorizontalEdges(col, row, energyGrid, dist, pathTo, pq, h);
        }

        return n;
    }

    // [add comment here]
    private void relaxHorizontalEdges(int col, int row,
                                      double[][] energyGrid, double[][] distTo,
                                      int[][] pathTo,
                                      IndexMinPQ<Double> pq, int height) {
        for (int adj = -1; adj <= 1; adj++) {
            int nextRow = row + adj;
            if (nextRow >= 0 && nextRow < height) {
                double newDist = distTo[col][row] + energyGrid[col + 1][nextRow];
                if (newDist < distTo[col + 1][nextRow]) {
                    distTo[col + 1][nextRow] = newDist;
                    pathTo[col + 1][nextRow] = row;
                    pq.insert((col + 1) * height + nextRow, newDist);
                }
            }
        }
    }

    // create the horizontal seam path
    private int[] buildHorizontalSeam(int[][] path, int endRow, int width) {
        int[] seam = new int[width];
        for (int x = width - 1, y = endRow; x >= 0; x--) {
            seam[x] = y;
            y = path[x][y];
        }
        return seam;
    }

    // flips the image to allow other methods to be reused
    private void flipImage() {
        Picture flipped = new Picture(height(), width());
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                flipped.setRGB(y, x, pic.getRGB(x, y));
            }
        }
        pic = flipped;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int w = width();
        int h = height();
        double[][] energyGrid = new double[h][w];
        double[][] dist = new double[h][w];
        int[][] pathTo = new int[h][w];
        int[] m = new int[h];
        Arrays.fill(m, -1);
        IndexMinPQ<Double> pq = new IndexMinPQ<>(w * h);

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                energyGrid[y][x] = energy(x, y);
                if (y == 0) {
                    dist[y][x] = energyGrid[y][x];
                    pq.insert(x, dist[y][x]);
                }
                else {
                    dist[y][x] = Double.POSITIVE_INFINITY;
                }
            }
        }

        while (!pq.isEmpty()) {
            int idx = pq.delMin();
            int row = idx / w, col = idx % w;
            if (row == h - 1) return buildVerticalSeam(pathTo, col, h);
            relaxEdges(row, col, energyGrid, dist, pathTo, pq, w);
        }

        return m;
    }

    // relaxes the edges to find the shortest [directional] seam path
    private void relaxEdges(int row, int col, double[][] energyGrid,
                            double[][] distTo, int[][] pathTo,
                            IndexMinPQ<Double> pq, int width) {
        for (int adj = -1; adj <= 1; adj++) {
            int nextCol = col + adj;
            if (nextCol >= 0 && nextCol < width) {
                double newDist = distTo[row][col] + energyGrid[row + 1][nextCol];
                if (newDist < distTo[row + 1][nextCol]) {
                    distTo[row + 1][nextCol] = newDist;
                    pathTo[row + 1][nextCol] = col;
                    pq.insert((row + 1) * width + nextCol, newDist);
                }
            }
        }
    }

    // helper method to construct the vertical seam path
    private int[] buildVerticalSeam(int[][] path, int endColumn, int height) {
        int[] seam = new int[height];
        for (int y = height - 1, x = endColumn; y >= 0; y--) {
            seam[y] = x;
            x = path[y][x];
        }
        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        flipImage();
        removeVerticalSeam(seam);
        flipImage();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        valSeam(seam);
        Picture newPic = new Picture(width() - 1, height());
        for (int y = 0; y < height(); y++) {
            for (int x = 0, newX = 0; x < width(); x++) {
                if (x != seam[y]) {
                    newPic.setRGB(newX++, y, pic.getRGB(x, y));
                }
            }
        }
        pic = newPic;
    }

    // helper method to validate seam for removal
    private void valSeam(int[] seam) {
        if (seam == null || seam.length != height()) {
            throw new IllegalArgumentException("Seam isn't valid.");
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= width() || (i > 0
                    && Math.abs(seam[i] - seam[i - 1]) > 1)) {
                throw new IllegalArgumentException("Seam element(s) are"
                                                           + "invalid.");
            }
        }
    }


    //  unit testing (required)
    public static void main(String[] args) {
        Picture pic = new Picture("stadium2000-by-8000.png");
        SeamCarver sc = new SeamCarver(pic);
        sc.picture().show();
        StdOut.println("Width: " + sc.width());
        StdOut.println("Height: " + sc.height());
        StdOut.println("Energy at (0,0): " + sc.energy(0, 0));

        Stopwatch sw = new Stopwatch();
        sc.removeVerticalSeam(sc.findVerticalSeam());
        sc.removeHorizontalSeam(sc.findHorizontalSeam());
        StdOut.println("Elapsed Time: " + sw.elapsedTime());
    }

}
