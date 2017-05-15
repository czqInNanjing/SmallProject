package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author Qiang
 * @since 11/04/2017
 */
public class Percolation {

    private boolean[][] grid;
    private WeightedQuickUnionUF unionUF;
    private WeightedQuickUnionUF unionUFWithNoDestination;
    private int size;
    private int source;
    private int endpoint;
    private int numOfOpenSites;


    /**
     * create n-by-n grid, with all sites blocked
     *
     * @param n grid row num
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive integer!");
        }

        grid = new boolean[n][n];
        this.size = n;
        source = n * n;
        endpoint = n * n + 1;
        unionUF = new WeightedQuickUnionUF(n * n + 2);
        unionUFWithNoDestination = new WeightedQuickUnionUF(n * n + 1);
        for (int i = 0; i < n; i++) {
            unionUF.union(i, source);
            unionUF.union((size - 1) * size + i, endpoint);
            unionUFWithNoDestination.union(i, source);
        }


    }


    /**
     * open site (row, col) if it is not open already
     *
     * @param row row num
     * @param col col num
     */
    public void open(int row, int col) {

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            numOfOpenSites++;
            connectToAdjacent(row, col);
        }
    }

    private void connectToAdjacent(int row, int col) {

        if (row != 1) {
            connectIfOpen(row, col, row - 1, col);
        }
        if (row != size) {
            connectIfOpen(row, col, row + 1, col);
        }
        if (col != 1) {
            connectIfOpen(row, col, row, col - 1);
        }
        if (col != size) {
            connectIfOpen(row, col, row, col + 1);
        }

    }

    private void connectIfOpen(int row1, int col1, int row2, int col2) {
        if (isOpen(row2, col2)) {
            unionUF.union(getPos(row1, col1), getPos(row2, col2));
            unionUFWithNoDestination.union(getPos(row1, col1), getPos(row2, col2));
        }
    }

    private int getPos(int row, int col) {
        return (row - 1) * size + col - 1;
    }


    /**
     * is site (row, col) open?
     *
     * @param row row num
     * @param col col num
     * @return whether is open
     */
    public boolean isOpen(int row, int col) {
        checkRange(row, col);
        return grid[row - 1][col - 1];
    }

    /**
     * is site (row, col) full?
     *
     * @param row row num
     * @param col col num
     * @return whether is full
     */
    public boolean isFull(int row, int col) {
        checkRange(row, col);
        return isOpen(row, col) && unionUFWithNoDestination.connected(getPos(row, col), source);
    }


    /**
     * number of open sites
     *
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    /**
     * does the system percolate?
     *
     * @return does the system percolate?
     */
    public boolean percolates() {
        if (size == 1) {
            for (int i = 0; i < size; i++) {
                if (isOpen(1, i + 1)) {
                    return true;
                }
            }
            return false;
        }
        return unionUF.connected(source, endpoint);
    }


    private void checkRange(int row, int col) {

        if (row > size || row <= 0 || col > size || col <= 0) {
            throw new IndexOutOfBoundsException();
        }

    }


}
