/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package week3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author jane
 */
public class Week3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        List<Edge> graph = new ArrayList<>();
        Map<Short, List<Short>> vertices = new HashMap<>();
        
//        String filename = "test1.txt";
//        String filename = "test20.txt";
//        String filename = "test.txt";
        String filename = "kargerMinCut.txt";
        
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] split = line.split("\\s");
            Short vertex = null;
            for (int i = 0; i < split.length; i++) {
                String s = split[i].trim();
                if (s != null && s.length() > 0) {
                    short value = Short.parseShort(s);
                    if (vertex == null) {
                        vertex = value;
                        List<Short> l = new ArrayList<>();
                        l.add(vertex);
                        vertices.put(vertex, l);
                    } else {
                        if (!graph.contains(new Edge(value, vertex))) {
                            graph.add(new Edge(vertex, value));
                        }
                    }
                }
            }
        }
        int size = graph.size();
        System.out.println("Vertices number = " + vertices.size());
        System.out.println("Edges number = " + size);
        System.out.println("Edges: " + graph);
        int runs = 4000;
//        int runs = 500;
//        int runs = size * size * ((int)Math.round(Math.log(size)));
        System.out.println("Result: " + minCut(graph, vertices, runs));
        
    }

    private static int minCut(List<Edge> graph, Map<Short, List<Short>> vertices, int times) throws InterruptedException, ExecutionException {
        int threadNum = 4;
        int batchSize = 20;
        int roundNum = times / (batchSize * threadNum);
        System.out.println("There will be " + times + " runs total with " + threadNum + " threads: "
                + roundNum + " round(s) of " + batchSize + " each");
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        Short min = null;
        for (int i = 1; i <= roundNum; i++) {
            List<MinCut> tasks = new ArrayList<>();
            for (int j = 0; j < batchSize; j++) {
                tasks.add(new MinCut(graph, vertices));
            }
            List<Future<MinCutResult>> results = executor.invokeAll(tasks);
            StringBuilder sb = new StringBuilder();
            sb.append("Round ");
            sb.append(i);
            sb.append(" (");
            String delim = "";
            for (Future<MinCutResult> result : results) {
                short current = result.get().cuts;
                sb.append(delim);
                delim = ", ";
                sb.append(current);
                if (min == null || min > current) {
                    min = current;
                }
            }
            sb.append(") minimum is ");
            sb.append(min);
            System.out.println(sb.toString());
        }
        executor.shutdown();
        return min;
    }
}
