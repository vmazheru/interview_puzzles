public class SpiralPrint {
	public static void main(String[] args)  { 
        int a[][] = { 
        		{  1,  2,  3,  4,  5,  6 }, 
            {  7,  8,  9, 10, 11, 12 }, 
            { 13, 14, 15, 16, 17, 18 } }; 
        spiralPrint(a.length, a[0].length, a); 
    } 
	
	// Function print matrix in spiral form 
    static void spiralPrint(int rows, int cols, int a[][]) { 
        int rowStart = 0, colStart = 0; 
        int rowEnd = rows, colEnd = cols;
        while (rowStart < rowEnd && colStart < colEnd) { 
            // Print the first row from the remaining rows 
            for (int i = colStart; i < colEnd; ++i) { 
                System.out.print(a[rowStart][i] + " "); 
            } 
            rowStart++; 
  
            // Print the last column from the remaining columns 
            for (int i = rowStart; i < rowEnd; ++i) { 
                System.out.print(a[i][colEnd - 1] + " "); 
            } 
            colEnd--; 
  
            // Print the last row from the remaining rows 
            if (rowStart < rowEnd) { 
                for (int i = colEnd - 1; i >= colStart; --i) { 
                    System.out.print(a[rowEnd - 1][i] + " "); 
                } 
                rowEnd--;
            } 
  
            // Print the first column from the remaining columns */ 
            if (colStart < colEnd) { 
                for (int i = rowEnd - 1; i >= rowStart; --i) { 
                    System.out.print(a[i][colStart] + " "); 
                } 
                colStart++; 
            } 
        } 
    } 
  
    
	
}
