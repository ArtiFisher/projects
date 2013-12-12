package builders;

import bank.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TXTBuilder extends Builder {

    public void buildClient(String input) {
        try {
            client = new Client();
            double money;
            boolean activity;
            Scanner scannerFile = new Scanner(new File(input));
            while (scannerFile.hasNext()) {
                money = scannerFile.nextDouble();
                if (scannerFile.next().equalsIgnoreCase("active"))
                    activity = true;
                else
                    activity = false;

                client.addAccount(new Account(activity, money));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
