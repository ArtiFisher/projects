package textwork;


import java.io.PrintWriter;

public class Word {
    private String value;

    public Word(String value) {
        this.value = value;
    }

    public void writeToFile(PrintWriter out){
            out.print(value);
            out.flush();
    }


}

