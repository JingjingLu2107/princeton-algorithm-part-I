/******************************************************************************
 *  Compilation:  javac-algs4 CollidingDisks.java
 *  Execution:    java-algs4 CollidingDisks n
 *
 *  Simulates the motion of n hard disks, subject to the laws of elastic
 *  collisions. This program is intended to test that algs4.jar is properly
 *  installed.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] openGrid;
    final private WeightedQuickUnionUF uf;
    final private int bottom;
    final private int top;
    final private int length;
    private int numOfOpenSite;


    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException(n + " is invalid size for percolation");
        }

        //including 2 virtaul root which are top and bottom
        uf = new WeightedQuickUnionUF(n * n + 2);

        //the actual site is from 0 to (n * n -1)
        bottom = n * n;   //bottom virtual root
        top = n * n + 1;              //top virtual root
        openGrid = new boolean[n][n];
        numOfOpenSite = 0;
        length = n;
    }

    private boolean isValidIndex(int i) {
        return i <= length && i > 0;
    }

    private boolean isValidRowAndColum(int i, int j) {
        return isValidIndex(i) && isValidIndex(j);
    }

    private void validate(int i, int j) {
        if (!isValidRowAndColum(i, j)) {
            throw new IllegalArgumentException("Invalid row and colum " + i + ", " + j);
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            openGrid[row - 1][col - 1] = true;
            numOfOpenSite++;

            int site = siteIndex(row, col);

            //let first row connect with virtual top
            if (row == 1) {
                uf.union(top, site);
            }

            //let last row connect with virtual bottom
            if (row == length) {
                uf.union(bottom, site);
            }

            //check neighbours
            //top
            unionIfPossible(row - 1, col, site);
            //left
            unionIfPossible(row, col - 1, site);
            //right
            unionIfPossible(row, col + 1, site);
            //bottom
            unionIfPossible(row + 1, col, site);
        }
    }


    private void unionIfPossible(int row, int colum, int site) {
        if (isValidRowAndColum(row, colum) && isOpen(row, colum)) {
            int siteNeighbour = siteIndex(row, colum);
            uf.union(siteNeighbour, site);
        }
    }


    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openGrid[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int site = siteIndex(row, col);
        return uf.connected(site, top);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpenSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(bottom, top);
    }

    // the input's top left is (1,1), instead of (0,0), we using 1 dimesion array to represent n * n grid
    private int siteIndex(int row, int colum) {
        return (row - 1) * length + (colum - 1);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            percolation.open(row, col);
        }

        System.out.printf("the opened site   = %d\r\n", percolation.numOfOpenSite);
        System.out.printf("the percent of opened site   = %f",
                          percolation.numOfOpenSite / (double) (percolation.length
                                  * percolation.length));

    }


}
