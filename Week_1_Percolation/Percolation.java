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
     private boolean[] connectedToBottom;
     private int numOfOpen = 0;
     private boolean percolate;
 
     // constructor:
     public Percolation(int n) {
         if (n <= 0) throw new IllegalArgumentException("Invalid grid size.");
         size = n;
         int arrayLength = n * n + 1;
         uf = new WeightedQuickUnionUF(arrayLength);
         open = new boolean[arrayLength];
         connectedToTop = new boolean[arrayLength];
         connectedToBottom = new boolean[arrayLength];
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
                 connectedToTop[index] = true;
             }
 
             if (row == size) {
                 connectedToBottom[index] = true;
             }
 
             boolean connectedTop;
             boolean connectedBottom;
 
             connectedTop = connectedToTop[uf.find(index)];
             connectedBottom = connectedToBottom[uf.find(index)];
 
             if (row - 1 > 0 && open[index - size]) {
                 connectedTop = connectedTop || connectedToTop[uf.find(index - size)];
                 connectedBottom = connectedBottom || connectedToBottom[uf.find(index - size)];
                 uf.union(index, index - size);
             }
             if (row + 1 <= size && open[index + size]) {
                 connectedTop = connectedTop || connectedToTop[uf.find(index + size)];
                 connectedBottom = connectedBottom || connectedToBottom[uf.find(index + size)];
                 uf.union(index, index + size);
             }
             if (col + 1 <= size && open[index + 1]) {
                 connectedTop = connectedTop || connectedToTop[uf.find(index + 1)];
                 connectedBottom = connectedBottom || connectedToBottom[uf.find(index + 1)];
                 uf.union(index, index + 1);
             }
             if (col - 1 > 0 && open[index - 1]) {
                 connectedTop = connectedTop || connectedToTop[uf.find(index - 1)];
                 connectedBottom = connectedBottom || connectedToBottom[uf.find(index - 1)];
                 uf.union(index, index - 1);
             }
 
             int root = uf.find(index);
             connectedToTop[root] = connectedTop;
             connectedToBottom[root] = connectedBottom;
 
             if (connectedToTop[root] && connectedToBottom[root]) {
                 percolate = true;
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
         return percolate;
     }
 
     public static void main(String[] args) {
 
 
     }
 }
 