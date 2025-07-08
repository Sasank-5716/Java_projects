import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleBankingApplication {
    private static final List<BankAccount> accounts = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    displayAccount();
                    break;
                case 5:
                    System.out.println("\nExiting application...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("+-------------------------+");
        System.out.println("|   BANKING APPLICATION   |");
        System.out.println("+-------------------------+");
        System.out.println("| 1. Create Account       |");
        System.out.println("| 2. Deposit Funds        |");
        System.out.println("| 3. Withdraw Funds       |");
        System.out.println("| 4. View Account         |");
        System.out.println("| 5. Exit                 |");
        System.out.println("+-------------------------+");

        System.out.print("Enter your choice: ");
    }

    private static void createAccount() {
        System.out.print("\nEnter account holder name: ");
        String name = scanner.nextLine();

        System.out.print("Enter initial deposit: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        if (balance < 100) {
            System.out.println("Minimum initial deposit is $100");
            return;
        }

        String accountNumber = "ACCT" + (1000 + accounts.size());
        accounts.add(new BankAccount(accountNumber, name, balance));
        System.out.println("\nAccount created successfully!");
        System.out.println("Account Number: " + accountNumber);
    }

    private static void deposit() {
        BankAccount account = getAccount();
        if (account == null)
            return;

        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount <= 0) {
            System.out.println("Invalid amount");
            return;
        }

        account.deposit(amount);
        System.out.printf("Deposited $%.2f. New balance: $%.2f%n", amount, account.getBalance());
    }

    private static void withdraw() {
        BankAccount account = getAccount();
        if (account == null)
            return;

        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (amount <= 0) {
            System.out.println("Invalid amount");
            return;
        }

        if (account.withdraw(amount)) {
            System.out.printf("Withdrew $%.2f. New balance: $%.2f%n", amount, account.getBalance());
        } else {
            System.out.println("Insufficient funds");
        }
    }

    private static void displayAccount() {
        BankAccount account = getAccount();
        if (account == null)
            return;

        System.out.println("+-------------------------+");
        System.out.println("|      ACCOUNT DETAILS    |");
        System.out.println("+-------------------------+");
        System.out.printf("| Holder: %-16s |\n", account.getAccountHolderName());
        System.out.printf("| Number: %-16s |\n", account.getAccountNumber());
        System.out.printf("| Balance: $%-14.2f |\n", account.getBalance());
        System.out.println("+-------------------------+");

    }

    private static BankAccount getAccount() {
        System.out.print("Enter account number: ");
        String acctNumber = scanner.nextLine();

        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber().equals(acctNumber)) {
                return acc;
            }
        }
        System.out.println("Account not found");
        return null;
    }
}

class BankAccount {
    private final String accountNumber;
    private final String accountHolderName;
    private double balance;

    public BankAccount(String accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }
}