import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    // create a new pic variable
    private Picture pic;
    private int width;
    private int height;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException("Null picture not permitted.");
        }
        pic = new Picture(picture);
        width = picture.width();
        height = picture.height();
    }

    // current picture
    public Picture picture() {
        return new Picture(pic);
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // helper method to get different colors
    public int getColor(String color, int x) {
        if (color.equals("red")) {
            // red
            return ((x >> 16) & 0xFF);
        }
        else if (color.equals("green")) {
            // green
            return ((x >> 8) & 0xFF);
        }
        else {
            // blue
            return ((x) & 0xFF);
        }
    }


    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        // handle the exceptions
        if (x < 0 || x > width() - 1) {
            throw new IllegalArgumentException("Column is invalid.");
        }
        if (y < 0 || y > height() - 1) {
            throw new IllegalArgumentException("Row is invalid.");
        }


    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        // Initialize a 2D array to store the energies of pixels
        double[][] energyMatrix = new double[height()][width()];

        // Populate the energy matrix by computing the energy of each pixel
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                energyMatrix[y][x] = energy(x, y);
            }
        }

        // Initialize a 2D array to store the cumulative energies
        double[][] cumulativeEnergy = new double[height()][width()];

        // Copy the first column of the energy matrix to the cumulative energy matrix
        for (int y = 0; y < height(); y++) {
            cumulativeEnergy[y][0] = energyMatrix[y][0];
        }

        // Calculate the cumulative minimum energy for each pixel
        for (int x = 1; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                double minEnergy = cumulativeEnergy[y][x - 1];

                // Check the three neighboring pixels on the left to find the minimum energy
                if (y > 0) {
                    minEnergy = Math.min(minEnergy, cumulativeEnergy[y - 1][x - 1]);
                }
                if (y < height() - 1) {
                    minEnergy = Math.min(minEnergy, cumulativeEnergy[y + 1][x - 1]);
                }

                cumulativeEnergy[y][x] = energyMatrix[y][x] + minEnergy;
            }
        }

        // Find the minimum energy seam by tracing back the path from the rightmost column
        int[] seam = new int[width()];
        double minEnergy = Double.POSITIVE_INFINITY;
        int minEnergyIndex = -1;

        for (int y = 0; y < height(); y++) {
            if (cumulativeEnergy[y][width() - 1] < minEnergy) {
                minEnergy = cumulativeEnergy[y][width() - 1];
                minEnergyIndex = y;
            }
        }

        seam[width() - 1] = minEnergyIndex;

        for (int x = width() - 2; x >= 0; x--) {
            minEnergyIndex = backtrackSeamHorizontal(cumulativeEnergy, energyMatrix, minEnergyIndex,
                                                     x + 1);
            seam[x] = minEnergyIndex;
        }

        return seam;
    }

    // Helper method to backtrack and find the minimum energy index for horizontal seam
    private int backtrackSeamHorizontal(double[][] cumulativeEnergy, double[][] energyMatrix, int y,
                                        int x) {
        double minEnergy = cumulativeEnergy[y][x];
        int minEnergyIndex = y;

        if (y > 0 && cumulativeEnergy[y - 1][x] < minEnergy) {
            minEnergy = cumulativeEnergy[y - 1][x];
            minEnergyIndex = y - 1;
        }
        if (y < height() - 1 && cumulativeEnergy[y + 1][x] < minEnergy) {
            minEnergy = cumulativeEnergy[y + 1][x];
            minEnergyIndex = y + 1;
        }

        return minEnergyIndex;
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        // Initialize a 2D array to store the energies of pixels
        double[][] energyMatrix = new double[height()][width()];

        // Populate the energy matrix by computing the energy of each pixel
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                energyMatrix[y][x] = energy(x, y);
            }
        }

        // Initialize a 2D array to store the cumulative energies
        double[][] cumulativeEnergy = new double[height()][width()];

        // Copy the first row of the energy matrix to the cumulative energy matrix
        for (int x = 0; x < width(); x++) {
            cumulativeEnergy[0][x] = energyMatrix[0][x];
        }

        // Calculate the cumulative minimum energy for each pixel
        for (int y = 1; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                double minEnergy = cumulativeEnergy[y - 1][x];

                // Check the three neighboring pixels above to find the minimum energy
                if (x > 0) {
                    minEnergy = Math.min(minEnergy, cumulativeEnergy[y - 1][x - 1]);
                }
                if (x < width() - 1) {
                    minEnergy = Math.min(minEnergy, cumulativeEnergy[y - 1][x + 1]);
                }

                cumulativeEnergy[y][x] = energyMatrix[y][x] + minEnergy;
            }
        }

        // Find the minimum energy seam by tracing back the path from the bottom row
        int[] seam = new int[height()];
        double minEnergy = Double.POSITIVE_INFINITY;
        int minEnergyIndex = -1;

        for (int x = 0; x < width(); x++) {
            if (cumulativeEnergy[height() - 1][x] < minEnergy) {
                minEnergy = cumulativeEnergy[height() - 1][x];
                minEnergyIndex = x;
            }
        }

        seam[height() - 1] = minEnergyIndex;

        for (int y = height() - 2; y >= 0; y--) {
            minEnergyIndex = backtrackSeam(cumulativeEnergy, energyMatrix, y + 1, minEnergyIndex);
            seam[y] = minEnergyIndex;
        }

        return seam;
    }

    // Helper method to backtrack and find the minimum energy index
    private int backtrackSeam(double[][] cumulativeEnergy, double[][] energyMatrix, int y, int x) {
        double minEnergy = cumulativeEnergy[y][x];
        int minEnergyIndex = x;

        if (x > 0 && cumulativeEnergy[y][x - 1] < minEnergy) {
            minEnergy = cumulativeEnergy[y][x - 1];
            minEnergyIndex = x - 1;
        }
        if (x < width() - 1 && cumulativeEnergy[y][x + 1] < minEnergy) {
            minEnergy = cumulativeEnergy[y][x + 1];
            minEnergyIndex = x + 1;
        }

        return minEnergyIndex;
    }


    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        // handle the exceptions
        if (seam == null) {
            throw new IllegalArgumentException("Input is null.");
        }
        if (seam.length != width()) {
            throw new IllegalArgumentException("Input length is invalid.");
        }

        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i + 1] - seam[i]) > 1) {
                throw new IllegalArgumentException("Input is not a seam.");
            }
        }

        // set the color
        int[][] colors = new int[height()][width()];
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                colors[row][col] = pic.getRGB(col, row);
            }
        }

        // create a new pic
        pic = new Picture(width(), height() - 1);
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                if (row == seam[col]) {
                    continue;
                }
                pic.setRGB(col, row, colors[col][row]);
            }
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        // handle the exceptions
        if (seam == null) {
            throw new IllegalArgumentException("Input is null.");
        }
        if (seam.length != height()) {
            throw new IllegalArgumentException("Input length is invalid.");
        }

        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i + 1] - seam[i]) > 1) {
                throw new IllegalArgumentException("Input is not a seam.");
            }
        }

        // set the color
        int[][] colors = new int[height()][width()];
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                colors[row][col] = pic.getRGB(col, row);
            }
        }

        // create a new pic
        pic = new Picture(width(), height() - 1);
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                if (row == seam[col]) {
                    continue;
                }
                pic.setRGB(col, row, colors[col][row]);
            }
        }
    }

    //  unit testing (required)
    public static void main(String[] args) {

    }

}
