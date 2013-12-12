import chain.*;
import textwork.Text;

import java.io.File;
import java.util.Scanner;

public class SentencesSorting {

    public static final String INPUT = "input.txt";
    public static final String OUTPUT = "output.txt";

    public static void main(String[] args){
        (new Text(INPUT, OUTPUT)).writeToFile();
        chain();
    }

    public static void chain(){
        System.out.println("Abbreviations:");
        for(String abbr:Abbreviation.getSet())
            System.out.println(abbr+" ");
        System.out.println("Languages:");
        for(String pl:PLName.getSet())
            System.out.println(pl+" ");
        System.out.println("Words:");
        System.out.println(UsualWord.getCount());
    }

}
