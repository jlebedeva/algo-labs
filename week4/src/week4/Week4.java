package week4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Programming Assignment Week 4
 * Your task is to code up the algorithm from the video lectures for computing
 * strongly connected components (SCCs), and to run this algorithm on the given graph. 
 * http://spark-public.s3.amazonaws.com/algo1/programming_prob/SCC.txt
 * or
 * http://spark-public.s3.amazonaws.com/algo1/programming_prob/SCC.zip
 * 
 * @author jane
 */
public class Week4 {
    private static final boolean debug = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
//        HashMap<String, Vertex> vertices = load("test.txt");
//        HashMap<String, Vertex> vertices = load("test2.txt");
        HashMap<String, Vertex> vertices = load("SCC.txt");

        System.out.println("Vertices number = " + vertices.size());
        int edgesNum = 0;
        for (Vertex v : vertices.values()) {
            edgesNum = edgesNum + v.out.size();
        }
        System.out.println("Edges number = " + edgesNum);
        if (debug) {
            System.out.println("Graph: " + vertices);
        }
        List<SCC> result = strictlyConnectedComponents(vertices.values());
        Collections.sort(result, new SCC.SortBySize());
        
        report(result);
    }
    
    private static HashMap<String, Vertex> load(String filename) throws FileNotFoundException, IOException {
        HashMap<String, Vertex> vertices = new HashMap<>();
        
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        long ts = System.currentTimeMillis();
        long linesCount = 0;
        while ((line = br.readLine()) != null) {
            linesCount++;
            if (linesCount % 10000 == 0) {
                long now = System.currentTimeMillis();
                double lps = (10000.0 / (double)(now - ts)) * 1000;
                ts = now;
                System.out.println("Read line " + linesCount + " @ " + lps + " l/s"); 
            }
            String[] split = line.split("\\s");
            Vertex from = null;
            Vertex to = null;
            for (int i = 0; i < split.length; i++) {
                String s = split[i].trim();
                if (s != null && s.length() > 0) {
                    Vertex vertex = vertices.get(s);
                    
                    if (vertex == null) {
                        vertex = new Vertex(Integer.parseInt(s));
                        vertices.put(s, vertex);
                    }
                    
                    if (from == null) {
                        from = vertex;
                    } else {
                        to = vertex;
                        to.in.add(from);
                        from.out.add(to);
                    }
                }
            }
        }
        return vertices;
    }
    
    private static void report(List<SCC> result) {
        StringBuilder sb = new StringBuilder();
        sb.append("Result size: ");
        String delim = "";
        for (int i = 0; i < 5; i++) {
            if (i < result.size()) {
                sb.append(delim).append(result.get(i).getList().size());
            } else {
                sb.append(delim).append(0);
            }
            delim = ", ";
        }
        System.out.println(sb.toString());
        if (debug) {
            System.out.println("Result: " + result);
        }
    }
    
    private static List<SCC> strictlyConnectedComponents(Collection<Vertex> vertices) {
        return depthFirstSearchDirectLoop(depthFirstSearchReverseLoop(vertices));
    }
    
    private static TreeSet<Vertex> depthFirstSearchReverseLoop(Collection<Vertex> vertices) {        
        System.out.println("Reverse loop started: vertices size is " + vertices.size());
        TreeSet<Vertex> result = new TreeSet<>();
        Iterator<Vertex> it = vertices.iterator();
        int currentTime = 0;
        
        while (it.hasNext()) {
            Vertex v = it.next();
            if (debug) {
                System.out.println("Outer reverse call on " + v);
            }
            currentTime = depthFirstSearchReverse(v, currentTime);
            result.add(v);
        }
        if (debug) {
            for (Vertex v : result) {
                System.out.println("Vertex " + v + " assigned finishing time " + v.finishingTime);
            }
        }
        return result;
    }
    
    private static List<SCC> depthFirstSearchDirectLoop(Set<Vertex> vertices) {
        System.out.println("Direct loop started: sorted by finishing time vertices size is " + vertices.size());
        List<SCC> result = new LinkedList<>();
        Iterator<Vertex> it = vertices.iterator();

        while (it.hasNext()) {
            Vertex v = it.next();
            List<Vertex> list = new LinkedList<>();
            depthFirstSearchDirect(list, v);
            if (!list.isEmpty()) {
                if (debug) {
                    System.out.println("Strictly connected component: " + list);
                }
                result.add(new SCC(list));
            }
        }
        if (debug) {
            System.out.println("Direct loop finished: " + result);
        }
        return result;
    }

    private static int depthFirstSearchReverse(Vertex vertex, int finishingTime) {
        if (debug) {
            System.out.println("Reverse search on " + vertex + " explored=" + vertex.explored);
        }
        if (vertex.explored) {
            return finishingTime;
        } else {
            vertex.explored = true;
            for (Vertex child : vertex.in) {
                finishingTime = depthFirstSearchReverse(child, finishingTime);
            }
            finishingTime++;
            vertex.finishingTime = finishingTime;
            return finishingTime;
        }
    }

    private static List<Vertex> depthFirstSearchDirect(List<Vertex> list, Vertex vertex) {
        if (debug) {
            System.out.println("Direct search on " + vertex + " explored=" + vertex.explored);
        }
        if (!vertex.explored) { //flip back
            return null;
        } else {
            vertex.explored = false;
            for (Vertex child : vertex.out) {
                depthFirstSearchDirect(list, child);
            }
            list.add(vertex);
            return list;
        }
    }
}
