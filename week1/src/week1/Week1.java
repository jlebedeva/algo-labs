/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package week1;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Number of inversions recursive algorithm implementation
 * @author jane
 */
public class Week1 {
    
    private static boolean debug = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        String filename = "test_0";
//        String filename = "test_3";
//        String filename = "test_15";
        String filename = "IntegerArray.txt";
        List<String> lines = Files.readAllLines(Paths.get(filename), Charset.forName("UTF-8"));
        int[] input = new int[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            input[i] = Integer.parseInt(line.trim());
        }
        if (input.length > 100) {
            debug = false;
        }
        System.out.println("Input size = " + input.length);
        if (debug) {
            System.out.println("Input: " + Arrays.toString(input));
        }
        MergeResult result = sortAndCount(input, 0, input.length);
        System.out.println("Result counter: " + result.counter);
        
    }
    
    private static MergeResult sortAndCount(int[] input, int index, int length) {
        if (debug) {
            System.out.println("Sort: index = " + index + ", length = " + length);
        }
        MergeResult result = new MergeResult(length); 
        if (length == 1) {
            result.sorted[0] = input[index];
        } else {
            int leftLength = length / 2;
            int rightLength = length - leftLength;
            int rightIndex = index + leftLength;
            result = merge(
                    sortAndCount(input, index, leftLength), 
                    sortAndCount(input, rightIndex, rightLength));
        }
        if (debug) {
            System.out.println("Sort finished: index = " + index + ", length = " + length + " -> " + result);
        }
        return result;
    }
    
    private static MergeResult merge(MergeResult leftResult, MergeResult rightResult) {
        if (debug) {
            System.out.println("Merge: left = " + leftResult + " and right = " + rightResult);
        }
        int[] left = leftResult.sorted;
        int[] right = rightResult.sorted;
        MergeResult result = new MergeResult(left.length + right.length);
        result.counter = leftResult.counter + rightResult.counter;
        int i = 0, j = 0;
        while (true) {
            if (left[i] <= right[j]) {
                result.sorted[i+j] = left[i];
                i++;
                if (i >= left.length) {
                    System.arraycopy(right, j, result.sorted, i + j, right.length - j);
                    break;
                }
            } else if (left[i] > right[j]) {
                result.sorted[i+j] = right[j];
                result.counter = result.counter + left.length - i;
                j++;
                if (j >= right.length) {
                    System.arraycopy(left, i, result.sorted, i + j, left.length - i);
                    break;
                }
            }
        }
        return result;
    }
    
    private static class MergeResult {

        int[] sorted;
        long counter = 0;
        
        MergeResult(int size) {
            this.sorted = new int[size];
        }
        
        @Override
        public String toString() {
            return "result(" + counter + " inversions in " + Arrays.toString(sorted) + ")";
        }
    }
}
