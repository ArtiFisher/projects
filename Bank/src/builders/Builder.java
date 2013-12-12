package builders;

import bank.Client;

/**
 * @author Artem Rybakov
 */
public abstract class Builder {
    protected static Client client;

    public abstract void buildClient(String input);

    public static Client getClient() {
        return client;
    }
}
