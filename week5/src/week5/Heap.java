package week5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author jane
 */
public class Heap<T> {

    /*
     * Let's pretend it's an array to skip copying it on growth
     */
    private ArrayList<Node<T>> heap = new ArrayList<>();
    private HashMap<T, Integer> map = new HashMap<>();
    private Comparator comparator;

    public Heap(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    private void add(Node<T> node) {
        heap.add(node);
        map.put(node.getPayload(), node.getIndex());
    }
    
    private Node<T> remove(int index) {
        Node<T> node = heap.remove(index);
        map.remove(node.getPayload());
        return node;
    }

    public void build(T[] array) {
        Arrays.sort(array, comparator);
        for (int i = 0; i < array.length; i++) {
            add(new Node(array[i], i));
        }
    }

    public void build(List<T> list) {
        Collections.sort(list, comparator);
        for (int i = 0; i < list.size(); i++) {
            add(new Node(list.get(i), i));
        }
    }
    
    public Node<T> insert(T newObject) {
        int index = heap.size();
        Node newNode = new Node(newObject, index);
        add(newNode);
        bubbleUp(newNode, parent(newNode));
        return newNode;
    }

    public T delete(T object) {
        Integer index = map.get(object);
        if (index == null) {
            return null;
        } else {
            return delete(heap.get(index));
        }
//        System.out.println("Index to delete = " + index);
    }
    
    private T delete(Node<T> node) {
        T result = null;
        int lastIndex = heap.size() - 1;
        if (node.getIndex() != lastIndex) {
            Node<T> lastNode = heap.get(lastIndex);
            swap(node, lastNode);
            result = remove(lastIndex).getPayload();
            bubbleDown(lastNode);
        } else {
            result = remove(lastIndex).getPayload();
        }
        return result;
    }

    public T extractMin() {
        if (heap.isEmpty()) {
            return null;
        }
        return delete(heap.get(0));
    }

    private void bubbleUp(Node<T> child, Node<T> parent) {
        if (!assertHeapProperty(parent, child)) {
            swap(child, parent);
//            System.out.println("BubbleUp: " + heap);
            bubbleUp(child, parent(child));
        }
    }

    private void bubbleDown(Node<T> parent) {
        Node leftChild = leftChild(parent);
        Node rightChild = rightChild(parent);
        
        Node smallest = parent; //supposed new parent is the smallest
        if (!assertHeapProperty(smallest, leftChild)) {
            smallest = leftChild;
        }
        if (!assertHeapProperty(smallest, rightChild)) {
            smallest = rightChild;
        }
//        System.out.println("Smallest: " + smallest);
        if (parent != smallest) {
            swap(parent, smallest);
//            System.out.println("BubbleDown: " + heap);
            bubbleDown(parent);
        }
    }

    private void swap(Node<T> firstNode, Node<T> secondNode) {
        int firstIndex = firstNode.getIndex();
        int secondIndex = secondNode.getIndex();
        
        heap.set(firstIndex, secondNode);
        map.put(secondNode.getPayload(), firstIndex);
        secondNode.setIndex(firstIndex);
        
        heap.set(secondIndex, firstNode);
        map.put(firstNode.getPayload(), secondIndex);
        firstNode.setIndex(secondIndex);
    }

    private Node extractChildByIndex(int index) {
        if (heap.size() > index) {
            return heap.get(index);
        } else {
            return null;
        }
    }
    
    private boolean assertHeapProperty(Node<T> parent, Node<T> child) {
//        System.out.println("Parent: " + parent);
//        System.out.println("Child: " + child);
        if (parent == null || child == null) {
            return true;
        } else {
            return parent.compare(child) <= 0;
        }
    }

    private Node parent(Node node) {
        if (node.getIndex() == 0) {
            return null;
        } else {
            return heap.get(indexFromNumber(numberOfNode(node.getIndex()) / 2));
        }
    }

    private int numberOfNode(int index) {
        return index + 1;
    }
    
    private int indexFromNumber(int number) {
        return number - 1;
    }
    
    private Node leftChild(Node node) {
        Node child = extractChildByIndex(indexFromNumber(numberOfNode(node.getIndex()) * 2));
//        System.out.println("Parent: " + node + ", leftChild: " + child);
        return child;
    }

    private Node rightChild(Node node) {
        return extractChildByIndex(indexFromNumber((numberOfNode(node.getIndex()) * 2) + 1));
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int levels = Double.valueOf(Math.floor(Math.log(heap.size()) / Math.log(2))).intValue();
        for (int i = 0; i <= levels; i++) {
            sb.append("\n").append("level ").append(i).append(": ");
            String delim = "";
            int firstElement = 1<<i;
            int lastElement = (firstElement * 2) - 1;
            for (int j = indexFromNumber(firstElement); j < heap.size() && j <= indexFromNumber(lastElement); j++) {
                sb.append(delim).append(heap.get(j));
                delim = ", ";
            }
            sb.append("; ");
        }
        return sb.toString();
    }

    public class Node<T> {

        private T payload;
        private int index;

        public Node(T payload, int index) {
            this.payload = payload;
            this.index = index;
        }

        public T getPayload() {
            return payload;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int compare(Node other) {
            return comparator.compare(this.getPayload(), other.getPayload());
        }
        
        @Override
        public String toString() {
            return payload.toString() + "(" + numberOfNode(index) + ")";
        }
    }
}