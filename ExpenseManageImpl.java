import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseManageImpl implements ExpenseManage {

    private static final String FILE_NAME = "expense.json";
    private List<Expense> expenses = new ArrayList<>();

    public ExpenseManageImpl() {
        loadExpenses();
    }

    @Override
    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveExpenses();
    }

    @Override
    public void deleteExpense(Expense expense) {
        expenses = expenses.stream()
                .filter(e -> e.getId() != expense.getId())
                .collect(Collectors.toList());
        saveExpenses();
    }

    @Override
    public void updateExpense(Expense expense) {
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getId() == expense.getId()) {
                expenses.set(i, expense);
                break;
            }
        }
        saveExpenses();
    }

    @Override
    public List<Expense> listExpense() {
        return expenses;
    }

    @Override
    public String getExpenseSummary() {
        double totalAmount = expenses.stream().mapToDouble(Expense::getAmount).sum();
        return "Total Expenses: " + totalAmount;
    }

    @Override
    public String getMonthlyExpenseSummary(int month) {
        double totalAmount = expenses.stream()
                .filter(e -> Integer.parseInt(e.getDate().split("-")[1]) == month)
                .mapToDouble(Expense::getAmount)
                .sum();
        return "Total Expenses for month " + month + ": " + totalAmount;
    }

    private void loadExpenses() {
        try {
            if (Files.exists(Paths.get(FILE_NAME))) {
                String content = new String(Files.readAllBytes(Paths.get(FILE_NAME)));
                expenses = parseExpenses(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveExpenses() {
        try {
            String json = toJson(expenses);
            Files.write(Paths.get(FILE_NAME), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Expense> parseExpenses(String json) {
        List<Expense> expenses = new ArrayList<>();
        json = json.trim();
        if (json.startsWith("[") && json.endsWith("]")) {
            json = json.substring(1, json.length() - 1);
            String[] items = json.split("},\\{");
            for (String item : items) {
                item = item.replace("{", "").replace("}", "");
                String[] fields = item.split(",");
                int id = Integer.parseInt(fields[0].split(":")[1].trim());
                String category = fields[1].split(":")[1].trim().replace("\"", "");
                double amount = Double.parseDouble(fields[2].split(":")[1].trim());
                String description = fields[3].split(":")[1].trim().replace("\"", "");
                String date = fields[4].split(":")[1].trim().replace("\"", "");
                expenses.add(new Expense(id, category, amount, description, date));
            }
        }
        return expenses;
    }

    private String toJson(List<Expense> expenses) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            json.append("{")
                    .append("\"id\":").append(expense.getId()).append(",")
                    .append("\"category\":\"").append(expense.getCategory()).append("\",")
                    .append("\"amount\":").append(expense.getAmount()).append(",")
                    .append("\"description\":\"").append(expense.getDescription()).append("\",")
                    .append("\"date\":\"").append(expense.getDate()).append("\"")
                    .append("}");
            if (i < expenses.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }
}