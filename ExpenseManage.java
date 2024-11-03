import java.util.List;

public interface ExpenseManage {

    void addExpense(Expense expense);

    void deleteExpense(Expense expense);

    void updateExpense(Expense expense);

    List<Expense> listExpense();

    String getExpenseSummary();

    String getMonthlyExpenseSummary(int month);
}