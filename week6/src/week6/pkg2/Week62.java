package week6.pkg2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

/**
 *
 * @author jane
 */
public class Week62 {
    
    private static final Heap<Integer> rightHeap = new Heap<Integer>(new Comparator<Integer>() {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1 - o2;
        }
    });
    private static final Heap<Integer> leftHeap = new Heap<Integer>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    });

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        int mod = 10000;
        int result = 0;
        String filename = "Median.txt";
//        String filename = "testMedian.txt";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
//        int count = 0;
        while ((line = br.readLine()) != null) {
//            count++;
            int newInt = Integer.parseInt(line.trim());
//            System.out.println();
//            System.out.println("Iteration " + count);
//            System.out.println("New Integer: " + newInt);
            addNewNumber(newInt);
            int median = getMedian();
//            System.out.println("LeftHeap has size " + leftHeap.size() + leftHeap);
//            System.out.println("RightHeap has size " + rightHeap.size() + rightHeap);
//            System.out.println("Median is " + median);
            result = (result + median);
        }
        System.out.println("Result is " + (result % mod));
    }

    public static void addNewNumber(int number) {
        Integer median = getMedian();
        if (median == null) {
            leftHeap.insert(number);
        } else {
            boolean mayGoToLeftHeap = leftHeap.size() <= rightHeap.size();
            boolean mayGoToRightHeap = leftHeap.size() >= rightHeap.size();
            if (median > number) {
                if (!mayGoToLeftHeap) {
                    rightHeap.insert(leftHeap.extractMin());
                }
                leftHeap.insert(number);
            } else if (median < number) {
                if (!mayGoToRightHeap) {
                    leftHeap.insert(rightHeap.extractMin());
                }
                rightHeap.insert(number);
            } else {
                System.out.println("There's a duplicate " + number);
            }
        }
    }

    public static Integer getMedian() {
        if (rightHeap.size() > leftHeap.size()) {
            return rightHeap.getMin();
        } else {
            return leftHeap.getMin();
        }
    }
}

