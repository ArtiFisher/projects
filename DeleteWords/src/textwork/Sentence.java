package textwork;

import chain.Abbreviation;
import chain.PLName;
import chain.UsualWord;
import chain.WordHandler;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Sentence implements Comparable<Sentence> {
    private String value;
    private ArrayList<Word> list;
    private int words;

    public Sentence(String value) {
        list=new ArrayList<Word>();
        words=0;
        this.value = value;
        Scanner sent=new Scanner(value).useDelimiter("[ \n\t,;:\\()\'\"’/-]+");
        chain.WordHandler handler=new WordHandler(new Abbreviation(new PLName(new UsualWord())));
        while(sent.hasNext("[a-zA-Zа-яА-Я]+")){
            String word=sent.next("[a-zA-Zа-яА-Я]+");
            handler.handleRequest(word);
            list.add(new Word(word));
            words++;
        }
    }

    public String getValue() {
        return value;
    }

    public int wordsQuantity(){
        return words;
    }

    @Override
    public int compareTo(Sentence sentence) {
        if(this.wordsQuantity() - sentence.wordsQuantity()!=0)
            return this.wordsQuantity() - sentence.wordsQuantity();
        else
            return this.getValue().length() - sentence.getValue().length();
    }

    public void writeToFile(PrintWriter out){
        out.print(value);
        out.print(".\n");
        out.flush();
    }
}
