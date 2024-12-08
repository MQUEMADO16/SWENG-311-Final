package budgetmanager.backend;

import java.util.ArrayList;

public class Independent extends HouseholdMember {
    
    // Attributes
    private ArrayList<Income> income;
    private double weeklyDiscretionSpend;

    // Constructor
    public Independent(String name, int age, double weeklyDiscretionSpend) {
        super(name, age);
        this.income = new ArrayList<>();
        this.weeklyDiscretionSpend = weeklyDiscretionSpend;
    }
    
    public Independent(String name, int age, double weeklyDiscretionSpend, ArrayList<Income> income, ArrayList<Expense> expenses) {
        super(name, age, expenses);
        this.income = new ArrayList<>();
        this.weeklyDiscretionSpend = weeklyDiscretionSpend;
        
        for (Income inc : income) {
            this.income.add(inc);
        }
    }

    // Getters and Setters
    public ArrayList<Income> getIncome() {
        return this.income;
    }

    public void setIncome(ArrayList<Income> income) {
        this.income = income;
    }

    public double getWeeklyDiscretionSpend() {
        return this.weeklyDiscretionSpend;
    }

    public void setWeeklyDiscretionSpend(double weeklyDiscretionSpend) {
        this.weeklyDiscretionSpend = weeklyDiscretionSpend;
    }

    // Other Methods
    public void printIndependent() {
        printMember();
        System.out.println("Weekly Discretionary Spend: " + this.weeklyDiscretionSpend);
        System.out.println("Income Details:");
        for (Income inc : income) {
            inc.printIncome(); // Assuming Income has a printIncome method
        }
    }

    public double calculateMonthlyIncome() {
        double totalMonthlyIncome = 0.0;
        for (Income inc : income) {
            totalMonthlyIncome += inc.calculateMonthlyAmount();  // Assuming Income has a method to calculate monthly amount
        }
        return totalMonthlyIncome;
    }

    public double calculateYearlyIncome() {
        double totalYearlyIncome = 0.0;
        for (Income inc : income) {
            totalYearlyIncome += inc.calculateYearlyAmount();  // Assuming Income has a method to calculate yearly amount
        }
        return totalYearlyIncome;
    }
    
    public void addIncome(Income income) {
        this.income.add(income);
    }
    
    public void removeIncome(Income income) {
        this.income.remove(income);
    }
    
    public void removeIncome(String source) {
        for (int i = 0; i < this.income.size(); i++) {
            if (this.income.get(i).getSource().equalsIgnoreCase(source)) {
                this.income.remove(i);
            }
        }
    }
    
    public Income getIncome(String source) {
        for (Income income : this.income) {
            if (income.getSource().equalsIgnoreCase(source)) {
                return income;
            }
        }
        return null;
    }
}
