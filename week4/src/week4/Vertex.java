package week4;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jane
 */
public class Vertex implements Comparable<Vertex> {
    
    List<Vertex> in = new ArrayList<>();
    List<Vertex> out = new ArrayList<>();
    int label;
    int finishingTime = 0;
    boolean explored = false;

    public Vertex(int label) {
        this.label = label;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.label;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex other = (Vertex) obj;
        if (this.label != other.label) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(label).append("(");
        String delim = "";
        for (Vertex v : out) {
            sb.append(delim);
            delim = ", ";
            sb.append(v.label);
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public int compareTo(Vertex o) {
        return o.finishingTime - this.finishingTime;
    }
}
