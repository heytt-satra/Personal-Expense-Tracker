import java.util.*;

// Investment Scheme Class
class InvestmentScheme {
    private String name;
    private String riskLevel; // Low, Medium, High
    private double expectedReturn;
    private double minInvestment;

    public InvestmentScheme(String name, String riskLevel, double expectedReturn, double minInvestment) {
        this.name = name;
        this.riskLevel = riskLevel;
        this.expectedReturn = expectedReturn;
        this.minInvestment = minInvestment;
    }

    public String getName() {
        return name;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public double getExpectedReturn() {
        return expectedReturn;
    }

    public double getMinInvestment() {
        return minInvestment;
    }

    @Override
    public String toString() {
        return "Scheme: " + name + " | Risk: " + riskLevel + " | Return: " + expectedReturn + "% | Min Investment: $" + minInvestment;
    }
}

// 1. User Class
class User {
    private String name;
    private double totalIncome;
    private double totalExpenses;
    private Budget budget;
    private List<Transaction> transactions;

    public User(String name, double totalIncome) {
        this.name = name;
        this.totalIncome = totalIncome;
        this.totalExpenses = 0;
        this.budget = new Budget();
        this.transactions = new ArrayList<>();
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Budget getBudget() {
        return budget;
    }

    // Calculate the remaining savings after expenses
    public double getSavings() {
        return totalIncome - totalExpenses;
    }
}

// 2. Transaction Class
class Transaction {
    private String category;
    private double amount;
    private Date date;

    public Transaction(String category, double amount, Date date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}

// 3. Budget Class
class Budget {
    private Map<String, Double> budgetLimits; // Category -> Budget Limit
    private Map<String, Double> expenses; // Category -> Total Expenses

    public Budget() {
        this.budgetLimits = new HashMap<>();
        this.expenses = new HashMap<>();
    }

    public void setBudgetLimit(String category, double limit) {
        budgetLimits.put(category, limit);
    }

    public void addExpense(String category, double amount) {
        expenses.put(category, expenses.getOrDefault(category, 0.0) + amount);
    }

    public boolean isOverBudget(String category) {
        return expenses.getOrDefault(category, 0.0) > budgetLimits.getOrDefault(category, Double.MAX_VALUE);
    }

    public Map<String, Double> getExpenses() {
        return expenses;
    }

    public Map<String, Double> getBudgetLimits() {
        return budgetLimits;
    }
}

// 4. ExpenseTracker Class
class ExpenseTracker {
    private User user;

    public ExpenseTracker(User user) {
        this.user = user;
    }

    public void addTransaction(Transaction transaction) {
        user.getTransactions().add(transaction);
        user.getBudget().addExpense(transaction.getCategory(), transaction.getAmount());
        user.setTotalExpenses(user.getTotalExpenses() + transaction.getAmount());
    }

    public void displayExpensesByCategory() {
        System.out.println("Expenses by Category:");
        for (String category : user.getBudget().getExpenses().keySet()) {
            System.out.println("Category: " + category + ", Total: " + user.getBudget().getExpenses().get(category));
        }
    }
}

// 5. BudgetManager Class
class BudgetManager {
    private Budget budget;

    public BudgetManager(Budget budget) {
        this.budget = budget;
    }

    public boolean checkBudget(String category) {
        if (budget.isOverBudget(category)) {
            System.out.println("Warning: You are over budget for " + category);
            return true; // Return true if over budget
        } else {
            System.out.println("You are within the budget for " + category);
            return false; // Return false if within budget
        }
    }
}

// 6. InvestmentAdvice Class (Updated)
class InvestmentAdvice {
    private List<InvestmentScheme> schemes;

    // Initialize investment schemes (in place of a real database)
    public InvestmentAdvice() {
        schemes = new ArrayList<>();
        schemes.add(new InvestmentScheme("High-Risk Stock Fund", "High", 12.5, 500));
        schemes.add(new InvestmentScheme("Moderate Growth Fund", "Medium", 8.5, 300));
        schemes.add(new InvestmentScheme("Conservative Bond Fund", "Low", 4.0, 200));
        schemes.add(new InvestmentScheme("Real Estate Investment", "Medium", 7.0, 1000));
        schemes.add(new InvestmentScheme("Crypto Fund", "High", 20.0, 100));
        schemes.add(new InvestmentScheme("Index Fund", "Medium", 10.0, 100));
        // Add more schemes as needed (at least 20 options)
    }

    // Suggest investments based on risk tolerance and amount
    public void suggestInvestments(double riskTolerance, double savings) {
        String riskCategory;
        if (riskTolerance > 0.7) {
            riskCategory = "High";
        } else if (riskTolerance > 0.4) {
            riskCategory = "Medium";
        } else {
            riskCategory = "Low";
        }

        boolean foundScheme = false;
        System.out.println("Suggested investment options for your risk tolerance (" + riskCategory + "):");
        for (InvestmentScheme scheme : schemes) {
            if (scheme.getRiskLevel().equalsIgnoreCase(riskCategory) && savings >= scheme.getMinInvestment()) {
                System.out.println(scheme);
                foundScheme = true;
            }
        }

        if (!foundScheme) {
            System.out.println("You don't have enough savings to invest in the suggested schemes.");
        }
    }
}

// 7. NotificationService Class
class NotificationService {
    public void sendNotification(String message) {
        System.out.println("Notification: " + message);
    }

    // Updated: check notifications after each transaction and notify immediately
    public void checkForNotifications(Budget budget) {
        for (String category : budget.getBudgetLimits().keySet()) {
            if (budget.isOverBudget(category)) {
                sendNotification("You have exceeded your budget for " + category);
            }
        }
    }
}

// 8. Main Application (FinancialApp)
public class FinancialApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User Input for Name and Income
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your total monthly income: ");
        double income = scanner.nextDouble();

        User user = new User(name, income);
        ExpenseTracker expenseTracker = new ExpenseTracker(user);
        BudgetManager budgetManager = new BudgetManager(user.getBudget());
        NotificationService notificationService = new NotificationService();
        InvestmentAdvice investmentAdvice = new InvestmentAdvice();

        // Set Budgets for Categories
        System.out.print("Enter number of budget categories: ");
        int numCategories = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < numCategories; i++) {
            System.out.print("Enter budget category name: ");
            String category = scanner.nextLine();
            System.out.print("Enter budget limit for " + category + ": ");
            double limit = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            user.getBudget().setBudgetLimit(category, limit);
        }

        // Add Transactions and Check Notifications Immediately
        System.out.print("Enter number of transactions: ");
        int numTransactions = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < numTransactions; i++) {
            System.out.print("Enter transaction category: ");
            String category = scanner.nextLine();
            System.out.print("Enter transaction amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            Transaction transaction = new Transaction(category, amount, new Date());
            expenseTracker.addTransaction(transaction);

            // After each transaction, check if any category is over budget and notify
            if (budgetManager.checkBudget(category)) {
                notificationService.sendNotification("You have exceeded your budget for " + category);
            }
        }

        // Display Expenses by Category
        expenseTracker.displayExpensesByCategory();

        // Calculate savings and suggest investments based on risk tolerance
        double savings = user.getSavings();
        System.out.println("Your savings after expenses: $" + savings);

        if (savings > 0) {
            System.out.print("Enter your risk tolerance (0.0 to 1.0): ");
            double riskTolerance = scanner.nextDouble();
            investmentAdvice.suggestInvestments(riskTolerance, savings);
        } else {
            System.out.println("You don't have any savings to invest.");
        }

        scanner.close();
    }
}
