package week4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author jane
 */
public class SCC {
    
    private List<Vertex> list = new ArrayList<>();

    public SCC(List<Vertex> list) {
        this.list = list;
    }
    
    List<Vertex> getList() {
        return list;
    }
    
    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.list);
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
        final SCC other = (SCC) obj;
        if (!Objects.equals(this.list, other.list)) {
            return false;
        }
        return true;
    }

    public static class SortBySize implements Comparator<SCC> {

        @Override
        public int compare(SCC o1, SCC o2) {
            return o2.list.size() - o1.list.size();
        }
    }
}
