package backend;

import java.util.ArrayList;

public class HouseholdMember {
    
    // Attributes
    protected String name;
    protected int age;
    protected ArrayList<Expense> expenses;

    // Constructor
    public HouseholdMember(String name, int age) {
        this.name = name;
        this.age = age;
        this.expenses = new ArrayList<>();
    }
    
    public HouseholdMember(String name, int age, ArrayList<Expense> expenses) {
        this.name = name;
        this.age = age;
        this.expenses = new ArrayList<>();
        for (Expense expense : expenses) {
            this.expenses.add(expense);
        }
    }

    // Getters and Setters
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<Expense> getExpenses() {
        return this.expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    // Other Methods
    public void printMember() {
        System.out.println("Member: " + this.name + "\n" +
                           "Age: " + this.age);
        System.out.println("\nExpenses:");
        for (Expense expense : expenses) {
            expense.printExpense();
            System.out.println();
        }
    }

    public double calculateYearlyExpense() {
        double totalYearlyExpense = 0.0;
        for (Expense expense : expenses) {
            totalYearlyExpense += expense.calculateYearlyAmount();
        }
        return totalYearlyExpense;
    }
    
    public void addExpense(Expense expense) {
        this.expenses.add(expense);
    }
    
    public void removeExpense(Expense expense) {
        this.expenses.remove(expense);
    }
    
    public void removeExpense(String name) {
        for (int i = 0; i < this.expenses.size(); i++) {
            if (this.expenses.get(i).getName().equalsIgnoreCase(name)) {
                this.expenses.remove(i);
            }
        }
    }
    
    public Expense getExpense(String name) {
        for (Expense expense : this.expenses) {
            if (expense.getName().equalsIgnoreCase(name)) {
                return expense;
            }
        }
        return null;
    }
}
