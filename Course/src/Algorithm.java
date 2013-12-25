import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Algorithm {
    static Set<Vertex> A, N, R, V;
    static Set<Edge> E;
    static int size;

    public static void main(String[] args) throws Exception {
        A = new HashSet<Vertex>();
        N = new HashSet<Vertex>();
        R = new HashSet<Vertex>();
        V = new HashSet<Vertex>();
        E = new HashSet<Edge>();
        int row = 0, col = 0;
        Scanner input = new Scanner(new File("input.txt"));
        size = input.nextInt();
        input.nextLine();
        input.nextLine();
        input.nextLine();
        for (int i = 0; i < size; i++)
            V.add(new Vertex(i + 1));

        while (input.hasNextLine()) {
            row++;
            col = 1;
            Scanner line = new Scanner(input.nextLine());
            line.nextInt();
            while (line.hasNextInt()) {
                if (line.nextInt() == 1)
                    newEdge(row, col);
                col++;
            }

        }
        for (Vertex v : V) {
            if (v.neighbors.size() < size - 1) {
                A.add(v);
                N.addAll(v.neighbors);
                break;
            }
        }
        for(Vertex v:V){
            if(!(A.contains(v)||N.contains(v)))
                R.add(v);
        }

        boolean undone=true;

        Vertex n=new Vertex(0);
        while(undone){
            undone=false;
            label:for (Vertex i : N)
                for (Vertex k : R)
                    if(!E.contains(new Edge(i,k))) {
                        undone=true;
                        n=i;
                        break label;
                    }
            A.add(n);
            N.remove(n);
            NcrossR(n);

        }

        System.out.println("A");

        for(Vertex v:A){
            System.out.print(v.number);
            System.out.print("  ");
        }
        System.out.println();
        System.out.println("N");

        for(Vertex v:N){
            System.out.print(v.number);
            System.out.print("  ");

        }
        System.out.println();
        System.out.println("R");

        for(Vertex v:R){
            System.out.print(v.number);
            System.out.print("  ");

        }

    }

    public static void newEdge(int first, int second) {
        Vertex f, s;
        f = new Vertex(first);
        s = new Vertex(second);
        for (Vertex v : V) {
            if (v.number == first)
                f = v;
            if (v.number == second)
                s = v;
        }
        f.neighbors.add(s);
        s.neighbors.add(f);
        E.add(new Edge(f, s));
    }

    public static void NcrossR(Vertex n){
        for(Vertex r:n.neighbors)
            if(R.contains(r)){
                N.add(r);
                R.remove(r);
        }
    }

}
