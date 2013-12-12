package bank;

/**
 * This is class containing quantity of money on this account and its
 * condition(active|locked). Field ascending is used for sorting in necessary
 * order, it is set from method sort of class Client.
 *
 * @author Artem Rybakov
 */
public class Account implements Comparable<Account> {

    public static boolean ascending = true;
    private boolean active;
    private double money;

    public Account() {
        this.active = true;
        this.money = 0;
    }

    public Account(boolean active) {
        this.active = active;
        this.money = 0;
    }

    public Account(double money) {
        this.money = money;
        this.active = true;
    }

    public Account(boolean active, double money) {
        this.active = active;
        this.money = money;
    }

    /**
     * @return condition of this account
     */
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return quantity of money on this account
     */
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public int compareTo(Account account) {
        if (Account.ascending) {
            return (int) ((this.getMoney() - account.getMoney()));
        } else {
            return (int) ((account.getMoney() - this.getMoney()));
        }
    }

    @Override
    public String toString() {
        String res = new String("" + this.getMoney() + " - ");
        if (this.isActive()) {
            res += "active";
        } else {
            res += "locked";
        }
        return res;
    }
}
