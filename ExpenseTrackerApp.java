import java.util.Scanner;

public class ExpenseTrackerApp {
    public static void main(String[] args) {

        System.out.println("Welcome to Expense Tracker App");
        Scanner scanner = new Scanner(System.in);
        ExpenseManageImpl expenseManage = new ExpenseManageImpl();

        while (true) {
            System.out.println("1. Add Expense");
            System.out.println("2. Delete Expense");
            System.out.println("3. Update Expense");
            System.out.println("4. List Expense");
            System.out.println("5. View Expense Summary");
            System.out.println("6. View Monthly Expense Summary");
            System.out.println("7. Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.println("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.println("Enter date (yyyy-mm-dd): ");
                    String date = scanner.nextLine();
                    Expense expense = new Expense(id, category, amount, description, date);
                    expenseManage.addExpense(expense);
                    break;
                case 2:
                    System.out.println("Enter id: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    expense = new Expense(id, "", 0, "", "");
                    expenseManage.deleteExpense(expense);
                    break;
                case 3:
                    System.out.println("Enter id: ");
                    id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter category: ");
                    category = scanner.nextLine();
                    System.out.println("Enter amount: ");
                    amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Enter description: ");
                    description = scanner.nextLine();
                    System.out.println("Enter date (yyyy-mm-dd): ");
                    date = scanner.nextLine();
                    expense = new Expense(id, category, amount, description, date);
                    expenseManage.updateExpense(expense);
                    break;
                case 4:
                    for (Expense exp : expenseManage.listExpense()) {
                        System.out.println(exp);
                    }
                    break;
                case 5:
                    System.out.println(expenseManage.getExpenseSummary());
                    break;
                case 6:
                    System.out.println("Enter month (1-12): ");
                    int month = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println(expenseManage.getMonthlyExpenseSummary(month));
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}