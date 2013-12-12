package chain;

import java.util.HashSet;
import java.util.Set;

public class Abbreviation extends WordHandler{
    static Set<String> set=new HashSet<String>();
    WordHandler next;
    public Abbreviation(WordHandler next){
        this.next=next;
    }

    public void handleRequest(String value){
        if(value.matches("[A-ZА-Я][A-ZА-Я]+")){
            set.add(value);
        }
        else
            next.handleRequest(value);
    }

    public static Set<String> getSet() {
        return set;
    }
}
