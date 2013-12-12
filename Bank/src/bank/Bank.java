package bank;

import builders.DOMBuilder;
import builders.SAXBuilder;
import builders.StAXBuilder;
import builders.TXTBuilder;

import java.util.ArrayList;

public class Bank {

    public static final String INPUT = "input.xml";

    public static void main(String[] args) {
        if (INPUT.substring(INPUT.lastIndexOf('.') + 1, INPUT.length()).equalsIgnoreCase("txt"))
            Client.build(new TXTBuilder());
        else if (INPUT.substring(INPUT.lastIndexOf('.') + 1, INPUT.length()).equalsIgnoreCase("xml"))  {
            Client.build(new StAXBuilder());
        }
        else
            System.out.println("unknown extension");

        System.out.println(Client.getInstance());
        System.out.println();
        System.out.println("positive sum = " + Client.getInstance().positiveSum() + "\n");
        System.out.println("negative sum = " + Client.getInstance().negativeSum() + "\n");
        System.out.println("absolute sum = " + Client.getInstance().absoluteSum() + "\n");
        ArrayList<Account> search = Client.getInstance().search(-701000, 700287.9);
        for (Account account : search) {
            System.out.println(account);
        }

        System.out.println();
        Client.getInstance().sort(false);
        System.out.println(Client.getInstance());
    }
}
