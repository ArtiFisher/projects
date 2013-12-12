package chain;

public class WordHandler {
    WordHandler next;
    WordHandler(){}
    public WordHandler(WordHandler next){
        this.next=next;
    }

    public void handleRequest(String value){
        next.handleRequest(value);
    }

}
