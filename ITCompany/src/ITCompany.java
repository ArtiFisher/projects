import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ITCompany {

    static Set<Node> set=new HashSet<Node>();
    static ArrayList<Node>way=new ArrayList<Node>();
    static boolean found=false;

    public class Node{
        String value;
        boolean vacancy;
        boolean M=false;
        ArrayList<Node>list;


        public Node(String value, boolean vacancy) {
            this.value = value;
            this.vacancy = vacancy;
            M = false;
            this.list=new ArrayList<Node>();
        }


        public void edge(Node n){
            list.add(n);
            n.list.add(this);
        }


        @Override
        public boolean equals(Object o){
            if(((Node)o).value.equalsIgnoreCase(this.value))
                return true;
            else
                return false;
        }

        @Override
        public int hashCode(){
            int res=0;
            for(int i=0;i<value.length();i++)
                res+=value.charAt(i);
            return res;
        }

    }

    public void searching(Node current, Node finish){
        boolean continuous=false;
        way.add(current);
        for(Node node:current.list){
           if(node.equals(finish)){
               way.add(node);
               found=true;
               return;
           }
        }
        for(Node node:current.list)
            if((!way.contains(node))&&(!node.M)){
                searching(node,finish);
                continuous=true;
                break;
            }

        if((!continuous)&&(!found))
            way.remove(current);

    }

    public void algo(){
        for(Node vacancy:set)
            if((!vacancy.M)&&(vacancy.vacancy)){
               for(Node worker:set){
                  if((!worker.vacancy)&&(!worker.M)&&(!vacancy.list.contains(worker))) {
                      searching(vacancy,worker);
                      for(Node n:way){
                          n.M=true;
                      }
                      //way.clear();
                      break;
                  }
               }
            }
    }

//
//    public void start(){
//        this.map.put("Progger",new Node("Progger",true));
//        this.map.put("Tester",new Node("Tester",true));
//        this.map.put("Secret",new Node("Secret",true));
//        this.map.put("Press",new Node("Press",true));
//        this.map.put("Science",new Node("Science",true));
//
//        this.map.put("Vace",new Node("Vace",false));
//        this.map.put("Tim",new Node("Tim",false));
//        this.map.put("Nick",new Node("Nick",false));
//        this.map.put("Zina",new Node("Zina",false));
//        this.map.put("Zina",new Node("Zina",false));
//        this.map.put("Semen",new Node("Semen",false));
//        this.map.put("Sava",new Node("Sava",false));
//        this.map.put("Const",new Node("Const",false));
//        this.map.put("Lara",new Node("Lara",false));
//        this.map.put("Ivan",new Node("Ivan",false));
//        this.map.put("Valen",new Node("Valen",false));
//
//
//        this.edge("Vace","Progger");
//        this.edge("Tim","Progger");
//        this.edge("Nick","Progger");
//        this.edge("Zina","Progger");
//        this.edge("Semen","Progger");
//        this.edge("Sava","Tester");
//        this.edge("Const","Tester");
//        this.edge("Lara","Tester");
//        this.edge("Zina","Tester");
//        this.edge("Zina","Secret");
//        this.edge("Lara","Secret");
//        this.edge("Ivan","Secret");
//        this.edge("Ivan","Press");
//        this.edge("Zina","Press");
//        this.edge("Tim","Press");
//        this.edge("Vace","Science");
//        this.edge("Tim","Science");
//        this.edge("Zina","Science");
//        this.edge("Valen","Science");
//    }


    public void readFile(){
        try{

            Scanner scannerFile = new Scanner(new File("E:\\!!Course4\\GraphAlg\\alg_lab5\\input.txt"));
            while(scannerFile.hasNextLine()){
                Scanner scanner = new Scanner(scannerFile.nextLine()).useDelimiter("[, ]+");

                Node vacancy=new Node(scanner.next(), true);
                set.add(vacancy);
                while(scanner.hasNext()){
                    Node worker= new Node(scanner.next("[А-Яа-я]+"),false);
                    //worker.edge(vacancy);

                    if(set.contains(worker)){
                    for(Node w:set)
                       if(w.equals(worker))
                       {
                           w.list.add(vacancy);
                           vacancy.list.add(w);
                           break;
                       }
                    }
                    else{

                        worker.list.add(vacancy);
                        vacancy.list.add(worker);
                        set.add(worker);
                    }

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ITCompany it=new ITCompany();
        it.readFile();
        it.algo();

//        for(Node n:way)
//        System.out.println(n.value);
//        System.out.println();
//        System.out.println();

        int count=0;
        for(Node node:ITCompany.set)
            if(node.M)  {
                System.out.println(node.value);
                count++;
            }


        System.out.println(count/2);

    }

}
