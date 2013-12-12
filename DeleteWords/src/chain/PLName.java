package chain;

import java.util.HashSet;
import java.util.Set;

public class PLName extends WordHandler{

    static Set<String> set=new HashSet<String>();
        WordHandler next;
        public PLName(WordHandler next){
            this.next=next;
        }

    public void handleRequest(String value){
            if((value.matches("Java|C|Python|Delphi|Ruby")))
                set.add(value);
            else
                next.handleRequest(value);
        }

    public static Set<String> getSet() {
        return set;
    }
}

