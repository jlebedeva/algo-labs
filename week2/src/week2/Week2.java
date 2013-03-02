/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package week2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jane
 */
public abstract class Week2 {
    private static boolean debug = true;
    protected final int[] input;
    private int hold;
    private long counter;
    
    private Week2(int[] input) {
        this.input = input;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {        
        String filename = "QuickSort.txt";
        List<String> lines = Files.readAllLines(Paths.get(filename), Charset.forName("UTF-8"));
        int[] array = new int[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            array[i] = Integer.parseInt(line.trim());
        }
        if (array.length > 100) {
            debug = false;
        }
        
        System.out.println("Input size = " + array.length);
        if (debug) {
            System.out.println("Input: " + Arrays.toString(array));
        }
        
        List<Week2> list = new ArrayList<>();
        
        list.add(new Week2(Arrays.copyOf(array, array.length)) {
            @Override
            protected int getPivot(int start, int length) {
                if (debug) {
                    System.out.println("getPivot: start = " + start + ", length = " + length);
                }
                return start;
            }
        });
        
        list.add(new Week2(Arrays.copyOf(array, array.length)) {
            @Override
            protected int getPivot(int start, int length) {
                if (debug) {
                    System.out.println("getPivot: start = " + start + ", length = " + length);
                }
                return start + length - 1;
            }
        });
        
        list.add(new Week2(array) {
            @Override
            protected int getPivot(int start, int length) {
                if (debug) {
                    System.out.println("getPivot: start = " + start + ", length = " + length);
                }
                int last = start + length - 1;
                return getMedian(start, last, last - length / 2);
            }

            private int getMedian(int... elements) {
                List<Pair> list = new ArrayList<>();
                for (int index : elements) {
                    list.add(new Pair(index, this.input[index]));
                }
                Collections.sort(list);
                return list.get(1).index;
            }
        });
        
        for (Week2 w : list) {
            w.partition(0, array.length);
            System.out.println("Number of comparisons: " + w.counter);
        }
        if (debug) {
            System.out.println("Sorted: " + Arrays.toString(array));
        }
    }

    private void partition(int start, int length) {
        if (debug) {
            System.out.println("partition: start = " + start + ", length = " + length);
        }
        int pivot = getPivot(start, length);
        pivot = sort(pivot, start, length);
        counter = counter + (length - 1);
        int leftLength = pivot - start;
        int rightLength = length - leftLength - 1;
        if (leftLength > 0) {
            partition(start, leftLength);
        }
        if (rightLength > 0) {
            partition(pivot + 1, rightLength);
        }
    }

    private int sort(int pivot, int start, int length) {
        if (debug) {
            System.out.println("sort: pivot = " + pivot + ", start = " + start + ", length = " + length);
        }
        if (length == 1) {
            return pivot;
        }
        swap(start, pivot);
        int j = 1;
        for (int i = 1; i < length; i++) {
            if (input[start + i] < input[start]) {
                swap(start + i, start + j);
                j++;
            }
        }
        int newPivot = start + j - 1;
        swap(start, newPivot);
        return newPivot;
    }
    
    protected abstract int getPivot(int start, int length);

    private void swap(int a, int b) {
        if (debug) {
            System.out.println("swap: a = " + a + ", b = " + b);
        }
        hold = input[a];
        input[a] = input[b];
        input[b] = hold;
    }
    
    class Pair implements Comparable {
        int index;
        int value;

        public Pair(int index, int value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(Object o) {
            if (this.value == ((Pair)o).value) {
                return 0;
            } else if (this.value > ((Pair)o).value) {
                return 1;
            } else {
                return -1;
            }
        }  
    }
}
