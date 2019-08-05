import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Find maximum count of connected TRUE squares. Squares count as connected if they are on
 * the same line or row, but not diagonal.
 * 
 * For example, for matrix
 * 				new boolean[][] {
 *					{_, T, _, T, _, T, },
 *					{T, T, T, T, _, T, },
 *					{_, T, T, _, T, T, },
 *					{_, _, T, _, T, T, },
 *				} )); 
 *	it returns 9
 */
public class MatrixMaxConnectedSquares {

	public static int matrixMaxConnectedSquares(boolean[][] m) {
		Set<Pair> globalSet = new HashSet<>();
		int maxSum = 0;

		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				Set<Pair> localSet = new HashSet<>();
			    matrixMaxConnectedSquares(m, localSet, globalSet, new Pair(i, j));
			    maxSum = Math.max(maxSum, localSet.size());
			}
		}
		
		return maxSum;
	}
	
	private static void matrixMaxConnectedSquares(boolean[][]m, Set<Pair> localSet, Set<Pair> globalSet, Pair p) {
		if (!m[p.i][p.j] || globalSet.contains(p) || localSet.contains(p)) return;
		
		localSet.add(p);
		globalSet.add(p);
		List<Pair> adjusted = new ArrayList<>();
		
		final int rows = m.length;
		final int cols = m[0].length;
		
		if (p.j > 0 && m[p.i][p.j-1]) checkAdjusted(p.i, p.j-1, adjusted, localSet);       // check left
		if (p.i > 0 && m[p.i-1][p.j]) checkAdjusted(p.i-1, p.j, adjusted, localSet);       // check up
		if (p.j < cols-1 && m[p.i][p.j+1]) checkAdjusted(p.i, p.j+1, adjusted, localSet);  // check right
		if (p.i < rows-1 && m[p.i+1][p.j]) checkAdjusted(p.i+1, p.j, adjusted, localSet);  // check bottom
		
		for (Pair adj : adjusted) {
			matrixMaxConnectedSquares(m, localSet, globalSet, adj);
		}
	}
	
	private static void checkAdjusted(int i, int j, List<Pair> adjusted, Set<Pair> localSet) {
		Pair p = new Pair(i, j);
		if (!localSet.contains(p)) adjusted.add(p);
	}
	
	private static class Pair {
		private final int i;
		private final int j;
		
		Pair(int i, int j) {
			this.i = i;
			this.j = j;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			if (obj == this) return true;
			if (obj.getClass() != Pair.class) return false;
			Pair other = (Pair)obj;
			return i == other.i && j == other.j;
		}
		
		@Override
		public int hashCode() {
			return i + j;
		}
		
	}
	
	public static void main(String[] args) {
		boolean T = true;
		boolean _ = false;
		
		System.out.println(matrixMaxConnectedSquares(
				new boolean[][] {
					{_, T, _, T, _, T, },
					{T, T, T, T, _, T, },
					{_, T, T, _, T, T, },
					{_, _, T, _, T, T, },
				} )); // prints 9
		
		
		System.out.println(matrixMaxConnectedSquares(
				new boolean[][] {
					{_, T, _, T, _, T, },
					{T, T, _, T, _, T, },
					{_, T, T, _, T, T, },
					{_, _, T, _, T, T, },
				} )); // prints 6
		
		System.out.println(matrixMaxConnectedSquares(
				new boolean[][] {
					{_, T, _, T, _, T, },
					{T, T, _, T, _, T, },
					{_, T, T, _, T, T, },
					{_, _, T, _, T, T, },
				} )); // prints 6
		
		System.out.println(matrixMaxConnectedSquares(
				new boolean[0][0])); // prints 0

		System.out.println(matrixMaxConnectedSquares(
				new boolean[][] {
					{_, _, _, _, _, _, },
					{_, _, _, _, _, _, },
					{_, _, _, _, _, _, },
					{_, _, _, _, _, _, },
				} )); // prints 0
		
		System.out.println(matrixMaxConnectedSquares(
				new boolean[][] {
					{T, T, T, _, T, T, },
					{T, T, T, _, T, T, },
					{T, T, T, _, T, T, },
					{T, T, T, _, T, T, },
				} )); // prints 12
		
		System.out.println(matrixMaxConnectedSquares(
				new boolean[][] {
					{T, T, T, T, T, T, },
					{T, T, T, T, T, T, },
					{T, T, T, T, T, T, },
					{T, T, T, T, T, T, },
				} )); // prints 24
		
		System.out.println(matrixMaxConnectedSquares(
				new boolean[][] {
					{T, _, _, T, _, _, },
					{_, T, _, _, T, _, },
					{_, _, T, _, _, T, },
					{_, _, _, T, _, _, },
				} )); // prints 1
		
		System.out.println(matrixMaxConnectedSquares(
				new boolean[][] {
					{T },
					{_ },
					{T },
					{T },
				} )); // prints 2
		
		System.out.println(matrixMaxConnectedSquares(
				new boolean[][] {
					{T, T, _, T, T, T, },
				} )); // prints 3
		
	}

}
