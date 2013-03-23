/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package week5;

import java.util.Comparator;
import week5.Heap.Node;

/**
 *
 * @author jane
 */
public class Main {

    public static void main(String[] args) {

        Comparator<SomeShit> comparator = new Comparator<SomeShit>() {
            @Override
            public int compare(SomeShit o1, SomeShit o2) {
                return o1.i - o2.i;
            }
        };
        Heap<SomeShit> heap = new Heap(comparator);
        int[] array = new int[]{1, 7, 8, 123, 1342, 2, 4, 9, 3, 5, 5, 9, 6, 6, 0};
        SomeShit[] someArray = new SomeShit[array.length];
        for (int i = 0; i < someArray.length; i++) {
            someArray[i] = new SomeShit(array[i]);
        }
        heap.build(someArray);
        System.out.println("Heap: " + heap);
        System.out.println("Min: " + heap.extractMin() + ", heap: " + heap);
        SomeShit ins = new SomeShit(100);
        System.out.println("BEFORE Insert: " + ins + ", heap: " + heap);
        heap.insert(ins);
        System.out.println("AFTER Insert: " + ins + ", heap: " + heap);
        System.out.println("BEFORE Insert: " + ins + ", heap: " + heap);
        heap.insert(ins);
        System.out.println("AFTER Insert: " + ins + ", heap: " + heap);        
        ins = new SomeShit(1);
        System.out.println("BEFORE Insert: " + ins + ", heap: " + heap);
        heap.insert(ins);
        System.out.println("AFTER Insert: " + ins + ", heap: " + heap);
        heap.delete(ins);
        System.out.println("AFTER Delete node " + ins + ", heap: " + heap);
    }
    
    static class SomeShit {
        int i;

        public SomeShit(int i) {
            this.i = i;
        }
        
        @Override
        public String toString() {
            return String.valueOf(i);
        }
    }
}
