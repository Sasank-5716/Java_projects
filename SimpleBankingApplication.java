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

     private static void displayMenu() {
        System.out.println("\n╔══════════════════════════╗");
        System.out.println("║   BANKING APPLICATION    ║");
        System.out.println("╠══════════════════════════╣");
        System.out.println("║ 1. Create Account        ║");
        System.out.println("║ 2. Deposit Funds         ║");
        System.out.println("║ 3. Withdraw Funds        ║");
        System.out.println("║ 4. View Account          ║");
        System.out.println("║ 5. Exit                  ║");
        System.out.println("╚══════════════════════════╝");
        System.out.print("Enter your choice: ");
    }
}