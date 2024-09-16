import java.util.ArrayList;
import java.util.Scanner;

public class PersonalFinanceTracker {
    private ArrayList<String> expenses;
    private Scanner scanner;

    public PersonalFinanceTracker() {
        this.expenses = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("Personal Finance Tracker");
            System.out.println("1. Add Expense");
            System.out.println("2. Remove Expense");
            System.out.println("3. View Expenses");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    removeExpense();
                    break;
                case 3:
                    viewExpenses();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addExpense() {
        System.out.print("Enter expense description: ");
        String description = scanner.next();
        System.out.print("Enter expense amount: ");
        double amount = scanner.nextDouble();
        expenses.add(description + ": $" + amount);
        System.out.println("Expense added successfully!");
    }

    private void removeExpense() {
        System.out.print("Enter expense number to remove: ");
        int index = scanner.nextInt() - 1;
        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
            System.out.println("Expense removed successfully!");
        } else {
            System.out.println("Invalid expense number. Please try again.");
        }
    }

    private void viewExpenses() {
        System.out.println("Expenses:");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println((i + 1) + ". " + expenses.get(i));
        }
    }

    public static void main(String[] args) {
        PersonalFinanceTracker tracker = new PersonalFinanceTracker();
        tracker.run();
    }
}