/**
 * Given an array of stock prices, return the maximum profit, for the case
 * when you buy and sell once.
 */
public class StockPrices {

    public static int getProfit(int[] prices) {
        if (prices == null || prices.length <= 1) {
            throw new IllegalArgumentException("Array of prices must have at least two elements");
        }
        
        int min = prices[0];
        int profit = Integer.MIN_VALUE;
        for (int i = 1; i < prices.length; i++) {
            int p = prices[i];
            profit = Math.max(profit, p - min);
            min = Math.min(min, p);
        }
        
        return profit;
    }
    
    public static void main(String[] args) {
        System.out.println(getProfit(new int[] {5,4,3,2,1})); // -1.  The prices decrease through the entire sequence
        System.out.println(getProfit(new int[] {1,2,3,4,5})); // 4
        System.out.println(getProfit(new int[] {3,2,11,6,5})); // 9
        System.out.println(getProfit(new int[] {3,2})); // -1
        System.out.println(getProfit(new int[] {3,4})); // 1
        System.out.println(getProfit(new int[] {3})); // Exception
    }
}
