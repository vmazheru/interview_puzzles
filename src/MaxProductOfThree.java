
/**
 * Given an array of integers, find a max product of three of them in linear time
 */
public class MaxProductOfThree {

    public static void main(String[] args) {
        assert 495 == findMaxProductOfThree(new int[] {-11,2,5,3,0,1,-9,-1});    // mixed numbers
        assert 0 == findMaxProductOfThree(new int[] {-11,-2,-5,-3,0,-1,-9,-1});  // negatives with one zero
        assert -2 == findMaxProductOfThree(new int[] {-11,-2,-5,-3,-1,-9,-1});   // negatives only
        assert 495 == findMaxProductOfThree(new int[] {11,2,5,3,1,9,1});         // positives only
        assert 495 == findMaxProductOfThree(new int[] {11,2,5,0,1,9,1});         // positives with one zero
        assert 297 == findMaxProductOfThree(new int[] {11,2,-5,3,1,9,1});        // only one negative number                                                                

        // test with arrays of 3 elements
        assert 110 == findMaxProductOfThree(new int[] {11,2,5});
        assert -110 == findMaxProductOfThree(new int[] {11,2,-5});
        assert 0 == findMaxProductOfThree(new int[] {0,2,-5});
    }
 
    // keeps track of 
    //  1) three largest positive numbers
    //  2) two smallest positive numbers (in case when there is only one negative number in the array)
    //  3) two smallest negative numbers (whose absolute value is greatest) (in case if array only consists of negatives)
    //  4) three largest negative numbers (whose absolute value is smallest)
   private static int findMaxProductOfThree(int[] arr) {
       if (arr.length < 3) throw new IllegalArgumentException("Array must have more than 3 values");
       
       int neg1 = Integer.MAX_VALUE;    // neg1 < neg2
       int neg2 = Integer.MAX_VALUE;
       
       int maxNeg1 = Integer.MIN_VALUE; // maxNeg1 < maxNeg2 < maxNeg3
       int maxNeg2 = Integer.MIN_VALUE;
       int maxNeg3 = Integer.MIN_VALUE;
       
       int minPos1 = Integer.MAX_VALUE; // minPos1 < minPos2
       int minPos2 = Integer.MAX_VALUE;
       
       int pos1 = Integer.MIN_VALUE; // pos1  > pos2 > pos3
       int pos2 = Integer.MIN_VALUE;
       int pos3 = Integer.MIN_VALUE;
       
       for (int i = 0; i < arr.length; i++) {
          int v = arr[i];
          
          if (v >= 0) {
              if (v < minPos1) {
                  minPos2 = minPos1;
                  minPos1 = v;
              } else if (v < minPos2) {
                  minPos2 = v;
              }
              
              if (v > pos1) {
                  pos3 = pos2;
                  pos2 = pos1;
                  pos1 = v;
              } else if (v > pos2) {
                  pos3 = pos2;
                  pos2 = v;
              } else if (v > pos3) {
                  pos3 = v;
              }
          } else {
              if (v > maxNeg3) {
                  maxNeg1 = maxNeg2;
                  maxNeg2 = maxNeg3;
                  maxNeg3 = v;
              } else if (v > maxNeg2) {
                  maxNeg1 = maxNeg2;
                  maxNeg2 = v;
              } else if (v > maxNeg1) {
                  maxNeg1 = v;
              }
              
              if (v < neg1) {
                  neg2 = neg1;
                  neg1 = v;
              } else if (v < neg2) {
                  neg2 = v;
              }
          }
       }
       
       // if array only has non-negative values
       if (neg1 == Integer.MAX_VALUE) {
           return pos1 * pos2 * pos3;
       }
       // if array only has negative numbers
       if (pos1 == Integer.MIN_VALUE) {
           return maxNeg1 * maxNeg2 * maxNeg3;
       }
       
       // if array has mixed numbers
       if (neg1 != Integer.MAX_VALUE && neg2 != Integer.MAX_VALUE) { // there are two large negative numbers
           return neg1 * neg2 * pos1;
       }
       
       if (neg1 != Integer.MAX_VALUE && neg2 == Integer.MAX_VALUE) { // one negative number only
           if (pos1 != Integer.MIN_VALUE && pos2 != Integer.MIN_VALUE && pos3 != Integer.MIN_VALUE) {
               return pos1 * pos2 * pos3;
           }
       }
       
       return neg1 * minPos1 * minPos2;
   }
  
}

