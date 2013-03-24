package week6.pkg1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author jane
 */
public class Week61 {

    private static final HashMap<Integer, Integer> map = new HashMap<>();

    public static void main(String args[]) throws FileNotFoundException, IOException {
        String filename = "HashInt.txt";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            count++;
            map.put(Integer.parseInt(line.trim()), count);
        }
        int result = 0;
        for (int i = 2500; i <= 4000; i++) {
            if (has2Sum(i)) {
                result++;
            }
        }
        System.out.println("Result: " + result);
    }
    
    public static boolean has2Sum(Integer sum) {
        for (Integer i : map.keySet()) {
            if (i > sum) {
                continue;
            }
            int toLookFor = sum - i;
            Integer index = map.get(toLookFor);
            if (index != null && index != map.get(i)) {
                return true;
            }
        }
        return false;
    }
}
