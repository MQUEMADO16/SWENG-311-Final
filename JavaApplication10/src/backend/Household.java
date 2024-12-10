package backend;

import java.util.ArrayList;

public class Household {

    // Attributes
    private String householdName;
    private ArrayList<HouseholdMember> members;
    private double monthlyIncome;
    private double monthlyExpense;
    private ArrayList<Income> incomes;
    private ArrayList<Expense> expenses;
    private double spendBalance;
    private Savings savings;
    
    // Default constructor
    public Household() {
        this.householdName = "";
        this.members = new ArrayList<>();
        this.incomes = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.savings = new Savings();
        this.monthlyIncome = 0.0;
        this.monthlyExpense = 0.0;
        this.spendBalance = 0.0;
    }

    // Constructor
    public Household(String householdName, Savings savings) {
        this.householdName = householdName;
        this.members = new ArrayList<>();
        this.incomes = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.savings = savings;
        this.monthlyIncome = 0.0;
        this.monthlyExpense = 0.0;
        this.spendBalance = 0.0;
    }
    
    public Household(Household house) {
        this.householdName = house.householdName;
        this.members = house.members;
        this.incomes = house.incomes;
        this.expenses = house.expenses;
        this.savings = house.savings;
        this.monthlyIncome = house.monthlyIncome;
        this.monthlyExpense = house.monthlyExpense;
        this.spendBalance = house.spendBalance;
    }

    // Getters and Setters
    public String getHouseholdName() {
        return this.householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }

    public ArrayList<HouseholdMember> getMembers() {
        return this.members;
    }

    public void setMembers(ArrayList<HouseholdMember> members) {
        this.members = members;
    }

    public double getMonthlyIncome() {
        return this.monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public double getMonthlyExpense() {
        return this.monthlyExpense;
    }

    public void setMonthlyExpense(double monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }

    public ArrayList<Income> getIncomes() {
        return this.incomes;
    }

    public void setIncomes(ArrayList<Income> incomes) {
        this.incomes = incomes;
    }

    public ArrayList<Expense> getExpenses() {
        return this.expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    public double getSpendBalance() {
        return this.spendBalance;
    }

    public void setSpendBalance(double spendBalance) {
        this.spendBalance = spendBalance;
    }

    public Savings getSavings() {
        return this.savings;
    }

    public void setSavings(Savings savings) {
        this.savings = savings;
    }

    // Other Methods
    public boolean isMember(HouseholdMember member) {
        for (HouseholdMember householdMember : this.members) {
            if (householdMember.getName() == member.getName()) {
                return true;
            }
        }
        return false;
    }
    
    public HouseholdMember getMember(String name) {
        for(HouseholdMember member : this.members) {
            if (name == member.getName()) {
                return member;
            }
        }
        return null;
    }
    
    public void addMember(HouseholdMember member) {
        if (!isMember(member)) {
            this.members.add(member);
            for(Expense expense : member.getExpenses()) {
                this.expenses.add(expense);
            }
            if (member instanceof Independent) {
                for(Income income : ((Independent) member).getIncome()) {
                    this.incomes.add(income);
                }
            }
        }
    }

    public void removeMember(HouseholdMember member) {
        this.members.remove(member);
        for(Expense expense : member.getExpenses()) {
            this.expenses.remove(expense);
        }
        if (member instanceof Independent) {
            for(Income income : ((Independent) member).getIncome()) {
                this.incomes.remove(income);
            }
        }
    }

    public double calculateMonthlyIncome() {
        double totalIncome = 0.0;
        for (Income income : this.incomes) {
            totalIncome += income.calculateMonthlyAmount();
        }
        return totalIncome;
    }

    public double calculateMonthlyExpense() {
        double totalExpense = 0.0;
        for (Expense expense : this.expenses) {
            totalExpense += expense.calculateMonthlyAmount();
        }
        return totalExpense;
    }

    public double calculateYearlyIncome() {
        double totalIncome = 0.0;
        for (Income income : this.incomes) {
            totalIncome += income.calculateYearlyAmount();
        }
        return totalIncome;
    }

    public double calculateYearlyExpense() {
        double totalExpense = 0.0;
        for (Expense expense : this.expenses) {
            totalExpense += expense.calculateYearlyAmount();
        }
        return totalExpense;
    }
    
    public void fetchExpenses() {
        expenses.clear();
        for (HouseholdMember member : members) {
            for (Expense expense : member.getExpenses()) {
                expenses.add(expense);
            }
        }
    }
    
    public void fetchIncomes() {
        incomes.clear();
        for (HouseholdMember member : members) {
            if (member instanceof Independent) {
                for (Income income : ((Independent) member).getIncome()) {
                    incomes.add(income);
                }
            }
        }
    }
}
