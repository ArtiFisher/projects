package chain;

import java.util.HashSet;
import java.util.Set;

public class UsualWord extends WordHandler {
        static int count=0;
        WordHandler next;
        public UsualWord(){

        }
        public UsualWord(WordHandler next){
            this.next=next;
        }

    public void handleRequest(String value){
            if(value.matches("[A-ZА-Я|a-zа-я][a-zа-я]+"))
                count++;
        }

    public static int getCount() {
        return count;
    }
}
