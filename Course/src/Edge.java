public class Edge {
    public Vertex from;
    public Vertex to;

    public Edge(Vertex to, Vertex from) {
        this.to = to;
        this.from = from;
    }


    @Override
    public boolean equals(Object o) {
        if ((this.to == ((Edge) o).to)&&(this.from == ((Edge) o).from))
            return true;
        if ((this.to == ((Edge) o).from)&&(this.from == ((Edge) o).to))
            return true;
        return false;
    }

    public boolean same(Vertex n, Vertex r){
        if ((this.to.equals(r))&&(this.from.equals(n)))
            return true;
        if ((this.to.equals(n))&&(this.from.equals(r)))
            return true;
        return false;
    }

    @Override
    public int hashCode(){
        return to.number*from.number;
    }

}
