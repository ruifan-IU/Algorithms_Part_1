/* *****************************************************************************
 *  Name:              Rui Fan
 *  Coursera User ID:  123456
 *  Last modified:     08/12/2024
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // instance variables:
    private int size;
    private WeightedQuickUnionUF uf;
    private boolean[] open;
    private boolean[] connectedToTop;
    private int numOfOpen = 0;

    // constructor:
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Invalid grid size.");
        size = n;
        int arrayLength = n * n + 2;
        uf = new WeightedQuickUnionUF(arrayLength);
        open = new boolean[arrayLength];
        open[0] = true;
        open[arrayLength - 1] = true;
        connectedToTop = new boolean[arrayLength];
        connectedToTop[0] = true;
    }

    // instance methods:
    private int convert(int row, int col) {
        return size * (row - 1) + col;
    }

    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException("Invalid indices.");
        }

        int index = convert(row, col);

        if (!open[index]) {
            open[index] = true;
            numOfOpen++;
            if (row == 1) {
                uf.union(index, 0);
                connectedToTop[index] = true;
            }
            if (row == size) {
                uf.union(index, size * size + 1);
            }
            if (row - 1 > 0 && open[index - size]) {
                int rootOne = uf.find(index);
                int rootTwo = uf.find(index - size);

                if (connectedToTop[rootOne] || connectedToTop[rootTwo]) {
                    connectedToTop[rootOne] = true;
                    connectedToTop[rootTwo] = true;
                }

                uf.union(index, index - size);
            }
            if (row + 1 <= size && open[index + size]) {
                int rootOne = uf.find(index);
                int rootTwo = uf.find(index + size);

                if (connectedToTop[rootOne] || connectedToTop[rootTwo]) {
                    connectedToTop[rootOne] = true;
                    connectedToTop[rootTwo] = true;
                }

                uf.union(index, index + size);
            }
            if (col + 1 <= size && open[index + 1]) {
                int rootOne = uf.find(index);
                int rootTwo = uf.find(index + 1);

                if (connectedToTop[rootOne] || connectedToTop[rootTwo]) {
                    connectedToTop[rootOne] = true;
                    connectedToTop[rootTwo] = true;
                }

                uf.union(index, index + 1);
            }
            if (col - 1 > 0 && open[index - 1]) {
                int rootOne = uf.find(index);
                int rootTwo = uf.find(index - 1);

                if (connectedToTop[rootOne] || connectedToTop[rootTwo]) {
                    connectedToTop[rootOne] = true;
                    connectedToTop[rootTwo] = true;
                }

                uf.union(index, index - 1);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException("Invalid indices.");
        }

        int index = convert(row, col);

        return open[index];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException("Invalid indices.");
        }

        int index = convert(row, col);
        if (!open[index]) {
            return false;
        }
        else {
            int root = uf.find(index);
            return connectedToTop[root];
        }
    }

    public int numberOfOpenSites() {
        return numOfOpen;
    }

    public boolean percolates() {
        int index = size * size + 1;
        int root = uf.find(index);

        return connectedToTop[root];
    }

    public static void main(String[] args) {


    }
}
