import java.util.ArrayList;
import java.util.Scanner;

public class PersonalFinanceTracker {
    private ArrayList<Transaction> transactions;
    private ArrayList<Category> categories;
    private Scanner scanner;
    private Goal goal;

    public PersonalFinanceTracker() {
        this.transactions = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.goal = null;
    }

    public void run() {
        while (true) {
            System.out.println("Personal Finance Tracker");
            System.out.println("1. Add Transaction");
            System.out.println("2. Remove Transaction");
            System.out.println("3. View Transactions");
            System.out.println("4. Create Category");
            System.out.println("5. Set Budget");
            System.out.println("6. View Budget Progress");
            System.out.println("7. Set Goal");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    removeTransaction();
                    break;
                case 3:
                    viewTransactions();
                    break;
                case 4:
                    createCategory();
                    break;
                case 5:
                    setBudget();
                    break;
                case 6:
                    viewBudgetProgress();
                    break;
                case 7:
                    setGoal();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addTransaction() {
        System.out.print("Enter transaction description: ");
        String description = scanner.next();
        System.out.print("Enter transaction amount: ");
        double amount = scanner.nextDouble();
        System.out.print("Enter category (or 0 for none): ");
        int categoryIndex = scanner.nextInt() - 1;
        Category category = categoryIndex >= 0 && categoryIndex < categories.size() ? categories.get(categoryIndex) : null;
        Transaction transaction = new Transaction(description, amount, category);
        transactions.add(transaction);
        System.out.println("Transaction added successfully!");

        // Check if the transaction exceeds the goal
        if (goal != null) {
            double totalAmount = 0;
            for (Transaction t : transactions) {
                totalAmount += t.getAmount();
            }
            if (totalAmount > goal.getAmount()) {
                System.out.println("Warning: You are spending too much and may not reach your goal!");
            }
        }
    }

    private void removeTransaction() {
        System.out.print("Enter transaction number to remove: ");
        int index = scanner.nextInt() - 1;
        if (index >= 0 && index < transactions.size()) {
            transactions.remove(index);
            System.out.println("Transaction removed successfully!");
        } else {
            System.out.println("Invalid transaction number. Please try again.");
        }
    }

    private void viewTransactions() {
        System.out.println("Transactions:");
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            System.out.println((i + 1) + ". " + transaction.getDescription() + " - $" + transaction.getAmount() + " (" + (transaction.getCategory() != null ? transaction.getCategory().getName() : "None") + ")");
        }
    }

    private void createCategory() {
        System.out.print("Enter category name: ");
        String name = scanner.next();
        Category category = new Category(name);
        categories.add(category);
        System.out.println("Category created successfully!");
    }

    private void setBudget() {
        System.out.print("Enter category number to set budget for: ");
        int categoryIndex = scanner.nextInt() - 1;
        Category category = categoryIndex >= 0 && categoryIndex < categories.size() ? categories.get(categoryIndex) : null;
        if (category != null) {
            System.out.print("Enter budget amount: ");
            double budget = scanner.nextDouble();
            category.setBudget(budget);
            System.out.println("Budget set successfully!");
        } else {
            System.out.println("Invalid category number. Please try again.");
        }
    }

    private void viewBudgetProgress() {
        System.out.println("Budget Progress:");
        for (Category category : categories) {
            double totalAmount = 0;
            for (Transaction transaction : transactions) {
                if (transaction.getCategory() != null && transaction.getCategory().equals(category)) {
                    totalAmount += transaction.getAmount();
                }
            }
            System.out.println(category.getName() + ": $" + totalAmount + " / $" + category.getBudget());
        }
    }

    private void setGoal() {
        System.out.print("Enter goal amount: ");
        double amount = scanner.nextDouble();
        System.out.print("Enter goal description: ");
        String description = scanner.next();
        goal = new Goal(amount, description);
        System.out.println("Goal set successfully!");
    }

    private class Transaction {
        private String description;
        private double amount;
        private Category category;

        public Transaction(String description, double amount, Category category) {
            this.description = description;
            this.amount = amount;
            this.category = category;
        }

        public String getDescription() {
            return description;
        }

        public double getAmount() {
            return amount;
        }

        public Category getCategory() {
            return category;
        }
    }

    private class Category {
        private String name;
        private double budget;

        public Category(String name) {
            this.name = name;
            this.budget = 0;
        }

        public String getName() {
            return name;
        }

        public double getBudget() {
            return budget;
        }

        public void setBudget(double budget) {
            this.budget = budget;
        }
    }

    private class Goal {
        private double amount;
        private String description;

        public Goal(double amount, String description) {
            this.amount = amount;
            this.description = description;
        }

        public double getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }
    }

    public static void main(String[] args) {
        PersonalFinanceTracker tracker = new PersonalFinanceTracker();
        tracker.run();
    }
}
