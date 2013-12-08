import java.util.HashSet;
import java.util.Set;

public class Vertex {
    public int number;
    public Set<Vertex> neighbors;

    public Vertex(int number) {
        this.number = number;
        this.neighbors = new HashSet<Vertex>();

    }

    @Override
    public boolean equals(Object o) {
        if (this.number == ((Vertex) o).number)
            return true;
        return false;
    }

    @Override
    public int hashCode(){
        return number;
    }


}
