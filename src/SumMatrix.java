
/**
 * Design a matrix of integers with two operations
 * 
 * int sum(int row1, int col1, int row2, int col2);
 * void update(int row, int col, int value) ;
 * 
 * The sum() operation must take O(1) time;
 * The update operation may be O(mXn) time (where m and n are number of rows and columns in the matrix)
 * 
 */
public class SumMatrix {
    
    public static void main(String[] args) {
        SumMatrix m = new SumMatrix(new int[][] {
            {1,1,1,1,1},
            {1,1,1,1,1},
            {1,1,1,1,1},
            {1,1,1,1,1},
            {1,1,1,1,1},
        });
        
        m.showSums();
        
        assert 1 == m.sum(0, 0, 0, 0);
        assert 1 == m.sum(4, 4, 4, 4);
        assert 4 == m.sum(0, 0, 1, 1);
        assert 4 == m.sum(2, 2, 3, 3);
        assert 25 == m.sum(0, 0, 4, 4);
        assert 5 == m.sum(0, 0, 0, 4);
        assert 5 == m.sum(0, 0, 4, 0);
        assert 8 == m.sum(1, 1, 2, 4);
        
        System.out.println();
        
        m.update(2, 2, 2);
        
        m.showSums();
    }

    // actual values
    private int[][] m;
    
    // pre-calculated sums.  Here every cell will contain a sum of numbers from cell (0,0) to that cell inclusive
    private int[][] sums;
    
    public SumMatrix(int[][] m) {
        this.m = m;
        sums = new int[m.length][m[0].length];
        
        // pre-calculate sums
        for (int i = 0; i < m.length; i++) {
            int rowSum = 0;
            for (int j = 0; j < m[0].length; j++) {
                int n = m[i][j];
                rowSum += n;
                if (i > 0) { //not a first row
                    sums[i][j] = sums[i-1][j] + rowSum;
                } else {     // first row
                    sums[i][j] = rowSum;
                }
            }
        }
    }
    
    // when given a upper-left and lower-right corners of the sub-matrix, break the larger sum-matrix
    // (0,0) to (row2, col2) into 4 regions: upper-left, upper, left, and the sum-matrix itself.
    // the sum of the matrix-itself will be
    // result = large sub-matrix - left - upper + upper-left.  
    // We add upper-left, because it was subtracted twice when subtracting left and upper.
    public int sum(int row1, int col1, int row2, int col2) {
        int whole = sum(row2, col2);
        int upperLeft = row1 == 0 || col1 == 0 ? 0 : sum(row1 - 1, col1 - 1);
        int left = col1 == 0 ? 0 : sum(row2, col1 - 1);
        int top = row1 == 0 ? 0 : sum(row1 - 1, col2);
        return whole - left - top + upperLeft; 
    }

    // when updating a cell, adjust all cells to the right and below (including the cell itself) by
    // the difference between old and new value
    public void update(int row, int col, int value) {
        int diff = value - m[row][col];
        
        for (int i = row; i < sums.length; i++) {
            for (int j = col; j < sums[0].length; j++) {
                sums[i][j] += diff;
            }
        }
        
        m[row][col] = value;
    }
    
    private int sum(int row, int col) {
        return sums[row][col];
    }

    private void showSums () {
        int m[][] = sums;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                int n = m[i][j];
                int numSpaces = Math.max(0, 3 - Integer.toString(n).length());
                for (int k = 0; k < numSpaces; k++) {
                    System.out.print(" ");
                }
                System.out.print(n);
                if (j < m[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}