/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michal
 */



public class Percolation {
    private static final boolean OPENED = true;
    private static final boolean CLOSED = false;
    private boolean[][] grid;
    private final int gridSize;
    //0 - top, N*(N+1)+1 - bottom
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    
    private final int top;
    private final int bottom;
    
    public Percolation(final int N) {
        // create N-by-N grid, with all sites blocked
        grid = new boolean[N+1][N+1];
        gridSize = N;
        top = 0;
        bottom = gridSize*(gridSize +1) +1;
        
        uf = new WeightedQuickUnionUF(N*(N+1) + 2);
        uf2 = new WeightedQuickUnionUF(N*(N+1) +2);
        for (int j = 1; j <= gridSize; j++) {
            uf.union(top, xyTo1D(1, j));
            uf2.union(top, xyTo1D(1, j));
            uf.union(xyTo1D(gridSize, j), bottom);
        }    
    }
    
    public void open(final int i, final  int j) {
        // open site (row i, column j) if it is not already
        if (!validateIndices(i, j)) {
            throw new IndexOutOfBoundsException("index ("+i+","+j+") out of bounds");
        }
        
        if (grid[i][j] == OPENED)
            return;
        grid[i][j] = OPENED;
        int[][] neig = neighbours(i, j);
        for (int nInd = 0; nInd < neig.length; nInd++) {
            final int ni = neig[nInd][0];
            final int nj = neig[nInd][1];
            if (isOpen(ni, nj)) {
                uf.union(xyTo1D(i, j), xyTo1D(ni, nj));
                uf2.union(xyTo1D(i, j), xyTo1D(ni, nj));
            }
        }    
    }
    
   public boolean isOpen(final int i, final int j) {
       // is site (row i, column j) open?
       if (!validateIndices(i, j)) {
            throw new IndexOutOfBoundsException("index ("+i+","+j+") out of bounds");
       }
       return grid[i][j] == OPENED;
   }
   
   public boolean isFull(final int i, final int j) {
       // is site (row i, column j) full?
       if (!validateIndices(i, j)) {
            throw new IndexOutOfBoundsException("index ("+i+","+j+") out of bounds");
       }
       if (!isOpen(i, j)) {
           return false;
       }
       return uf2.connected(top, xyTo1D(i, j));
   }
   
   public boolean percolates() {
       // does the system percolate?
       if (gridSize == 1)
           return isOpen(1, 1);
       return uf.connected(top, bottom);
   }
   
   private int xyTo1D(final int x, final int y) {
       return x*gridSize + y;
   }
   
   private int[][] neighbours(int i, int j) {
       if (!validateIndices(i, j)) {
            throw new IndexOutOfBoundsException("index ("+i+","+j+") out of bounds");
       }
       if (gridSize == 1)
           return new int[][]{};
       int[][] ret;
       int iToAdd;
       int jToAdd;
       if (i == 1 || i == gridSize) {
           if (i == 1)
                   iToAdd = 1;
               else
                   iToAdd = -1;
           if (j == 1 || j == gridSize) {
               if (j == 1)
                   jToAdd = 1;
               else
                   jToAdd = -1;  
               ret = new int[][] {{i, j + jToAdd}, {i+ iToAdd, j}};   
           }
           else {
               ret = new int[][] {{i, j + 1}, {i, j - 1}, {i+ iToAdd, j}};
           }
       }
       else if (j == 1 || j == gridSize) {
           if (j == 1)
                   jToAdd = 1;
               else
                   jToAdd = -1;
           ret = new int[][] {{i, j + jToAdd}, {i+ 1, j} , {i-1, j }};
       }
       else {
           ret = new int[][] {{i, j + 1}, {i, j-1} , {i-1, j }, {i+1, j}};
       }
       
       return ret;
       
   }
   
   private boolean validateIndices(int i, int j) {
       return (i > 0 && i <= gridSize && j > 0 && j <= gridSize);
   }
   
   public static void main(String[] args) {
       Percolation per = new Percolation(1);
       //per.open(1, 1);
       StdOut.println(per.percolates());
    }
   
}
