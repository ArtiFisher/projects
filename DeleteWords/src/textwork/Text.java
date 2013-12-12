package textwork;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Text {
    private ArrayList<Sentence>list;
    private PrintWriter out;


    public Text(String input, String output) {
        list=new ArrayList<Sentence>();
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(new File(output))));
            Scanner scannerFile = new Scanner(new File(input)).useDelimiter("\\.[ \n\t\r]+");
            while (scannerFile.hasNext())
                list.add(new Sentence(scannerFile.next()));
            Collections.sort(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(){
        for(Sentence sent:list)
            sent.writeToFile(out);
    }


}
