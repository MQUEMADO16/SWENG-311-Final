package budgetmanager.backend;

import java.util.ArrayList;

public class Budget {
    
    // Attributes
    private Household household;
    private String savingsRule;

    // Constructor
    public Budget(Household household, String savingsRule) {
        this.household = household;
        this.savingsRule = savingsRule;
    }

    // Getters and Setters
    public Household getHousehold() {
        return this.household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public String getSavingsRule() {
        return this.savingsRule;
    }

    public void setSavingsRule(String savingsRule) {
        this.savingsRule = savingsRule;
    }

    // Method Headers

    /**
     * Method to allocate funds based on income and expenses as well as savings rule
     */
    public void allocateFunds() {
        double totalIncome = this.household.calculateMonthlyIncome();  // Get total household income
        double totalExpenses = this.household.calculateMonthlyExpense();  // Get total monthly expenses

        // Determine the amount for savings based on the rule
        double savingsAmount = 0;
        double needsAmount = 0;
        double wantsAmount = 0;

        if (this.savingsRule.equals("50-30-20")) {
            // For the 50-30-20 rule
            needsAmount = totalIncome * 0.50;
            wantsAmount = totalIncome * 0.30;
            savingsAmount = totalIncome * 0.20;
        } else if (this.savingsRule.equals("80-20")) {
            // For the 80-20 rule
            needsAmount = totalIncome * 0.80;
            savingsAmount = totalIncome * 0.20;
            wantsAmount = 0; // No discretionary spend in the 80-20 rule
        }

        // First, allocate the amount to savings
        this.household.getSavings().addSavings(savingsAmount);

        // Need to distribute the needs and discretionary spend.
        double remainingNeeds = needsAmount;
        double remainingWants = wantsAmount;

        for (HouseholdMember member : this.household.getMembers()) {
            // If the member is an Independent, adjust their discretionary spend.
            if (member instanceof Independent) {
                Independent independent = (Independent) member;

                // Handle Needs
                double memberNeeds = independent.calculateYearlyExpense() / 12; // Monthly needs for the Independent
                if (remainingNeeds >= memberNeeds) {
                    remainingNeeds -= memberNeeds;
                    independent.setWeeklyDiscretionSpend(remainingWants);  // Give all remaining discretionary funds
                } else {
                    independent.setWeeklyDiscretionSpend(remainingWants);  // Adjust if there are insufficient funds
                    remainingNeeds = 0;
                }
            }

            // If the member is a Dependent, adjust their allowance.
            if (member instanceof Dependent) {
                Dependent dependent = (Dependent) member;

                // Handle Needs (the dependent is assumed to have the same needs as their expenses)
                double memberNeeds = dependent.calculateYearlyExpense() / 12; // Monthly needs for the Dependent
                if (remainingNeeds >= memberNeeds) {
                    remainingNeeds -= memberNeeds;
                    dependent.setWeeklyAllowance(remainingWants);  // Give all remaining allowance as discretionary funds
                } else {
                    dependent.setWeeklyAllowance(remainingWants);  // Adjust if there are insufficient funds
                    remainingNeeds = 0;
                }
            }
        }

        // Print out the allocations for debugging
        System.out.println("Allocated Funds:");
        System.out.println("Needs Allocation: " + needsAmount);
        System.out.println("Wants (Discretionary) Allocation: " + wantsAmount);
        System.out.println("Savings Allocation: " + savingsAmount);
    }

    /**
     * Method to adjust the budget based on new incomes or expenses as well as savings rule
     */
    public void adjustBudget() {
        // Recalculate total income and expenses based on the current members' data
        double totalIncome = this.household.calculateMonthlyIncome();  
        double totalExpenses = this.household.calculateMonthlyExpense();  

        // Recalculate savings, needs, and wants based on the rule
        double savingsAmount = 0;
        double needsAmount = 0;
        double wantsAmount = 0;

        if (this.savingsRule.equals("50-30-20")) {
            needsAmount = totalIncome * 0.50;
            wantsAmount = totalIncome * 0.30;
            savingsAmount = totalIncome * 0.20;
        } else if (this.savingsRule.equals("80-20")) {
            needsAmount = totalIncome * 0.80;
            savingsAmount = totalIncome * 0.20;
            wantsAmount = 0; 
        }

        // Update savings based on recalculated savings amount
        this.household.getSavings().addSavings(savingsAmount);

        // Reset discretionary and allowances for members
        double remainingNeeds = needsAmount;
        double remainingWants = wantsAmount;

        for (HouseholdMember member : this.household.getMembers()) {
            // Adjust discretionary spending for Independents
            if (member instanceof Independent) {
                Independent independent = (Independent) member;
                double memberNeeds = independent.calculateYearlyExpense() / 12; 
                if (remainingNeeds >= memberNeeds) {
                    remainingNeeds -= memberNeeds;
                    independent.setWeeklyDiscretionSpend(remainingWants);
                } else {
                    independent.setWeeklyDiscretionSpend(remainingWants); 
                    remainingNeeds = 0;
                }
            }

            // Adjust allowance for Dependents
            if (member instanceof Dependent) {
                Dependent dependent = (Dependent) member;
                double memberNeeds = dependent.calculateYearlyExpense() / 12; 
                if (remainingNeeds >= memberNeeds) {
                    remainingNeeds -= memberNeeds;
                    dependent.setWeeklyAllowance(remainingWants); 
                } else {
                    dependent.setWeeklyAllowance(remainingWants); 
                    remainingNeeds = 0;
                }
            }
        }
        
        // Optionally, print a summary for debugging
        System.out.println("Budget adjusted with new members. Total Income: " + totalIncome + " Total Expenses: " + totalExpenses);
    }

    /**
     * Method to check if the savings goal has been met.
     * @return true if goal is met
     */
    public boolean savingsGoalIsMet() {
        return this.household.getSavings().getGoal() <= this.household.getSavings().getAmountSaved();
    }

    /**
     * Method to print the monthly budget summary.
     */
    public void printMonthlySummary() {
        double totalIncome = this.household.calculateMonthlyIncome();
        double totalExpenses = this.household.calculateMonthlyExpense();
        double savingsAmount = this.household.getSavings().getAmountSaved();
        double needsAmount = totalIncome * 0.50;  // Assuming 50% for needs in 50-30-20 rule
        double discretionaryAmount = totalIncome * 0.30;  // Assuming 30% for wants in 50-30-20 rule

        // Print monthly summary
        System.out.println("MONTHLY BUDGET SUMMARY");
        System.out.println("Total Monthly Income: " + totalIncome);
        System.out.println("Total Monthly Expenses: " + totalExpenses);
        System.out.println("Total Savings: " + savingsAmount);
        System.out.println("Needs Allocation (50%): " + needsAmount);
        System.out.println("Discretionary Spending (30%): " + discretionaryAmount);
    }

    /**
     * Method to print the yearly budget summary.
     */
    public void printYearlySummary() {
        double totalIncome = this.household.calculateYearlyIncome();
        double totalExpenses = this.household.calculateYearlyExpense();
        double savingsAmount = this.household.getSavings().getAmountSaved() * 12; // Multiply by 12 for yearly savings
        double needsAmount = totalIncome * 0.50 * 12;  // Assuming 50% for needs in 50-30-20 rule for the whole year
        double discretionaryAmount = totalIncome * 0.30 * 12;  // Assuming 30% for wants in 50-30-20 rule for the whole year

        // Print yearly summary
        System.out.println("YEARLY BUDGET SUMMARY");
        System.out.println("Total Yearly Income: " + totalIncome);
        System.out.println("Total Yearly Expenses: " + totalExpenses);
        System.out.println("Total Savings: " + savingsAmount);
        System.out.println("Needs Allocation (50%): " + needsAmount);
        System.out.println("Discretionary Spending (30%): " + discretionaryAmount);
    }
}
