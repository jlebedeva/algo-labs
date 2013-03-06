/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package week3;

import java.util.Objects;

/**
 *
 * @author jane
 */
public class Edge {

    Short begin;
    Short end;

    public Edge(Short begin, Short end) {
        this.begin = begin;
        this.end = end;
    }
    
    @Override
    public String toString() {
        return begin + "-" + end;
    }
    
    @Override
    public Edge clone() {
        return new Edge(begin, end);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.begin);
        hash = 53 * hash + Objects.hashCode(this.end);
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
        final Edge other = (Edge) obj;
        if (!Objects.equals(this.begin, other.begin)) {
            return false;
        }
        if (!Objects.equals(this.end, other.end)) {
            return false;
        }
        return true;
    }
}
