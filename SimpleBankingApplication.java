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
            scanner.nextLine();  // Consume newline
            
            switch (choice) {
                case 1: createAccount(); break;
                case 2: deposit(); break;
                case 3: withdraw(); break;
                case 4: displayAccount(); break;
                case 5: 
                    System.out.println("\nExiting application...");
                    System.exit(0);
                default: 
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}