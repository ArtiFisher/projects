import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

public class Tunnel {
    private Semaphore tunnelControl;
    private String name;
    private Train current;

    public Tunnel(String name) {
        tunnelControl = new Semaphore(1);
        this.name = name;
    }

    public void enterProtocol(Train train) {
        try {
            tunnelControl.acquire();
            current = train;
            System.out.println(current.getNumber() + " entered " + name + " tunnel.");
        } catch (InterruptedException ie) {
        }
    }

    public void exitProtocol() {
        tunnelControl.release();
        System.out.println(current.getNumber() + " left " + name + " tunnel.");
    }
}
