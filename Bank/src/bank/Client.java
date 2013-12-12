package bank;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import builders.Builder;
import builders.TXTBuilder;

/**
 * This class is the only client of a Bank.
 * It is realised with Singleton.
 * This class consists mostly of methods for working with client's accounts.
 *
 * @author Artem Rybakov
 * @see bank.Bank
 * @see bank.Account
 */

public class Client {

    private ArrayList<Account> accounts;
    private static Client instance;

    public Client() {
        accounts = new ArrayList<Account>();
    }

    /**
     * This method is used for Singleton realization.
     *
     * @return instance of created or existing class
     */

    public static Client build(Builder builder) {
        if (instance == null) {
            builder.buildClient(Bank.INPUT);
            instance = Builder.getClient();
        }
        return instance;
    }

    public static Client getInstance() {
        if (instance == null) {
            System.out.println("Unknown builder!");
        }
        return instance;
    }

    /**
     * @return list of accounts
     */
    public ArrayList getAccounts() {
        return accounts;
    }

    /**
     * Adds new account to list of accounts.
     *
     * @param account new account, that is going to be added
     */

    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * @return sum of available money, excluding blocked accounts
     */

    public double absoluteSum() {
        double sum = 0;
        for (Account account : accounts) {
            if (account.isActive()) {
                sum += account.getMoney();
            }
        }
        return sum;
    }

    /**
     * @return sum of money, from active accounts with negative balance
     */

    public double negativeSum() {
        double sum = 0;
        for (Account account : accounts) {
            if ((account.isActive()) && (account.getMoney() < 0)) {
                sum += account.getMoney();
            }
        }
        return sum;
    }

    /**
     * @return sum of money, from active accounts with positive balance
     */

    public double positiveSum() {
        double sum = 0;
        for (Account account : accounts) {
            if ((account.isActive()) && (account.getMoney() > 0)) {
                sum += account.getMoney();
            }
        }
        return sum;
    }

    /**
     * prints quantity of money from active accounts in given limits.
     *
     * @param low  lower limit
     * @param high upper limit
     */

    public ArrayList<Account> search(double low, double high) {
        ArrayList<Account> res = new ArrayList<Account>();
        for (Account account : accounts) {
            if ((account.isActive()) && (account.getMoney() >= low) && (account.getMoney() <= high))
                res.add(account);
        }
        return res;
    }

    /**
     * sorts accounts, according to given parameter, sets this parameter in class Account.
     *
     * @param ascending true - ascending order, false - descending order
     */

    public void sort(boolean ascending) {
        Account.ascending = ascending;
        Collections.sort(accounts);
    }

    /**
     * prints all accounts to console.
     */

    @Override
    public String toString() {
        String res;
        if (!accounts.isEmpty()) {
            res = new String("Client's accounts:\n");
            for (Account account : accounts) {
                res += account.getMoney() + " -";
                if (account.isActive()) {
                    res += " active\n";
                } else {
                    res += " locked\n";
                }
            }
        } else {
            res = new String("No accounts found!");
        }
        return res;
    }
}
