package week5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jane
 */
public class Week5 {
    private static final long infinity = 1000000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Map<Short, Map<Short, Edge>> vertices = new HashMap<>();
        
        String filename = "dijkstraData.txt";
//        String filename = "test.txt";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {            
            String[] split = line.split("\\s");
            Short from = null;
            Map<Short, Edge> edges = new HashMap<>();
            for (int i = 0; i < split.length; i++) {
                String s = split[i].trim();
                if (s != null && s.length() > 0) {
                    if (from == null) {
                        from = Short.parseShort(s);
                        vertices.put(from, edges);
                        /*
                         * There's two Edge objects for each edge - one for
                         * each node
                         */
                    } else {
                        String[] splitEdge = s.split(",");
                        Short to = null;
                        for (int j = 0; j < splitEdge.length; j++) {
                            String piece = splitEdge[j].trim();
                            if (to == null) {
                                to = Short.parseShort(piece);
                            } else {
                                edges.put(to, new Edge(from, to, Long.parseLong(piece)));
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Vertices number = " + vertices.size());
//        System.out.println("Vertices: " + vertices);
        Map<Short, Long> distances = dijkstra((short)1, vertices);
        short[] indeces = {7,37,59,82,99,115,133,165,188,197};
        String delim = "";
        StringBuilder sb = new StringBuilder();
        for (short vertex : indeces) {
            sb.append(delim).append(distances.get(vertex));
            delim = ",";
        }
        System.out.println("Result: " + sb.toString());
//        System.out.println("Distances: " + distances);
    }
    
    public static Map<Short, Long> dijkstra(short source, Map<Short, Map<Short, Edge>> vertices) {
        Map<Short, Long> distances = new HashMap<>();
        distances.put(source, 0L);
        Heap<Edge> heap = new Heap<>(new Comparator<Edge>() {

            @Override
            public int compare(Edge o1, Edge o2) {
                long result = o1.weigth - o2.weigth;
                if (result == 0) {
                    return 0;
                } else if (result > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        heap.build(new ArrayList<>(vertices.get(source).values()));
//        System.out.println("Heap after setup: " + heap);
        while (true) {
            Edge minEdge = heap.extractMin();
//            System.out.println("minEdge " + minEdge);
            if (minEdge == null) {
                break;
            }
            long weigth = minEdge.weigth;
            for (Edge e : vertices.get(minEdge.end).values()) {
                if (distances.containsKey(e.end)) {
                    heap.delete(new Edge(e.end, e.begin));
                } else {
                    e.weigth = e.weigth + weigth;
                    heap.insert(e);
                }
//                System.out.println("Heap after edge " + e + " " + heap);
            }
            distances.put(minEdge.end, weigth);
        }

        for (Short vertex : vertices.keySet()) {
            if (!distances.containsKey(vertex)) {
                distances.put(vertex, infinity);
            }
        }
        return distances;
    }
}
