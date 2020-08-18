import java.util.SortedMap;
import java.util.TreeMap;
import java.io.PrintStream;

public class Bank {
    /* These variables maintain mappings of String -> Account.  They keep
     * the set of keys (Strings) in "compareTo" order, and the set of
     * values (Accounts) is ordered according to the corresponding keys. */
    private SortedMap<String,Account> accounts = new TreeMap<String,Account>();
    private SortedMap<String,Account> names = new TreeMap<String,Account>();

    public void openAccount(String name, int initBalance) {
        Account acc =
            new Account(name, chooseNumber(), initBalance);
        accounts.put(acc.number, acc);
        names.put(name, acc);
    }

    public void deposit(String number, int amount) {
        if (amount < 0) {
            ERROR("Negative deposit");
        }
        Account acc = accounts.get(number);
        if (acc == null) {
            ERROR("No such account");
        }
        acc.balance += amount;
    }

    public void withdraw(String number, int amount) {
        if (amount < 0) {
            ERROR("Negative withdrawal");
        }
        Account acc = accounts.get(number);
        if (acc == null) {
            ERROR("No such account");
        }
        if (acc.balance < amount) {
            ERROR("Insufficient funds");
        }
        acc.balance -= amount;
    }
        
    public void printByAccount(PrintStream str) {
        // accounts.values() is the set of mapped-to values.  Its
        // iterator produces elements in order of the corresponding keys.
        for (Account account : accounts.values())
            account.print(str);
    }

    /** Print out all bank accounts sorted by name on STR. */
    public void printByName(PrintStream str) {
        for (Account account : names.values())
            account.print(str);
    }

    private void ERROR(String message) {
        throw new IllegalArgumentException(message);
    }

    /** Choose a new, unused account number string. */
    private String chooseNumber() {
        String acc;
        acc = "1000000000";
        for (Account account: accounts.values()) {
            if (account.number.compareTo(acc) > 0) {
                acc = account.number;
            }
        }
        return "" + (Long.parseLong(acc) + 1);
    }

    private static class Account {
        Account(String name, String number, int init) {
            this.name = name; this.number = number;
            this.balance = init;
        }
        /** Account-holder's name */
        final String name;
        /** Account number */
        final String number;
        /** Current balance */
        int balance;

        /** Print THIS on STR in some useful format. */
        void print(PrintStream str) {
            str.printf("Account %s: Account Name: %s; Balance: %d%n",
                       number, name, balance);
        }
    }                   
    
}

class BankClient {
    public static void main(String... args) {
        Bank b = new Bank();
        b.openAccount("Doe, John", 1000);
        b.openAccount("Roe, Jane", 500);
        b.openAccount("Jones, Chuck", 2000);
        b.openAccount("Gates, Bill", 50000000);

        System.out.println("By account:");
        b.printByAccount(System.out);
        System.out.println();
        System.out.println("By name:");
        b.printByName(System.out);
    }
}

