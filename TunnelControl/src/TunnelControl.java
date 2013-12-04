import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TunnelControl {

    public static final String INPUT = "input.txt";

    public static void main(String[] args) throws IOException {

        Scanner scannerFile = new Scanner(new File(INPUT));

        ArrayList<Tunnel> tunnels = new ArrayList<Tunnel>();
        ArrayList<Train> trains = new ArrayList<Train>();

        while (scannerFile.hasNextLine()) {
            Scanner scanner = new Scanner(scannerFile.nextLine()).useDelimiter("[, ]+");
            tunnels.add(new Tunnel(scanner.next()));
            while (scanner.hasNext())
                trains.add(new Train(tunnels.get(tunnels.size() - 1), scanner.next()));
        }

        for (Train train : trains)
            train.start();


    }

}

