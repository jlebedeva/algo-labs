/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package week3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 *
 * @author jane
 */
public class MinCut implements Callable<MinCutResult> {

        private final List<Edge> inputGraph;
        private final Map<Short, List<Short>> inputVertices;
        private Random random;

        public MinCut(List<Edge> graph, Map<Short, List<Short>> vertices) {
            this.inputGraph = graph;
            this.inputVertices = vertices;
        }
        
        @Override
        public MinCutResult call() throws Exception {
            List<Edge> graph = new ArrayList<>();
            for (Edge e : inputGraph) {
                graph.add(e.clone());
            }
            Map<Short, List<Short>> vertices = new HashMap<>();
            for (Short s : inputVertices.keySet()) {
                vertices.put(s, new ArrayList<>(inputVertices.get(s)));
            }
            random = new Random();
            short maxEdgeNum = (short) vertices.size();
            int iterations = vertices.size() - 2;
            for (int i = 0; i < iterations; i++) {
//                System.out.println("Iteration " + i);
//                System.out.println("Vertices size: " + vertices.size());
//                System.out.println("Graph size: " + graph.size());
                int edgeNum = random.nextInt(graph.size());
                Edge edge = graph.get(edgeNum);
//                System.out.println("Chosen edge " + edge);
                List<Short> l = vertices.remove(edge.begin);
                List<Short> l2 = vertices.remove(edge.end);
                if (l == null) {
                    System.out.println("Could not find edge point " + edge.begin);
                }
                if (l2 == null) {
                    System.out.println("Could not find edge point " + edge.end);
                }
                l.addAll(l2);
                Short newVertex = ++maxEdgeNum;
                vertices.put(newVertex, l);
//                System.out.println("New vertex " + newVertex + ": " + vertices.get(newVertex));
                Iterator<Edge> it = graph.iterator();
                while (it.hasNext()) {
                    Edge e = it.next();
                    boolean begin = isPartOf(e.begin, edge.begin, edge.end, l);
                    boolean end = isPartOf(e.end, edge.begin, edge.end, l);
                    if (begin && end) {
                        it.remove();
                    } else if (begin) {
                        e.begin = newVertex;
                    } else if (end) {
                        e.end = newVertex;
                    }
                }
            }
            List<Short> left = null;
            List<Short> right = null;
            for (List l : vertices.values()) {
                if (left == null) {
                    left = l;
                } else {
                    right = l;
                }
            }
            return new MinCutResult(left, right, (short) graph.size());
        }

        private boolean isPartOf(Short vertex, Short oldBegin, Short oldEnd, List<Short> contracted) {
            if (vertex.equals(oldBegin)
                    || vertex.equals(oldEnd)
                    || contracted.contains(vertex)) {
                return true;
            }
            return false;
        }
    }
