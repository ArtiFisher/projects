public class Train extends Thread {

    private Tunnel tunnel;
    private String number;

    public Train(Tunnel tunnel, String name) {
        super();
        this.tunnel = tunnel;
        this.number = name;
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

    public String getNumber() {
        return number;
    }

    public void run() {
        tunnel.enterProtocol(this);
        try {
            sleep(1000);        //in tunnel
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tunnel.exitProtocol();
    }
}
