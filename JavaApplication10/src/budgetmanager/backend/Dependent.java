package budgetmanager.backend;

import java.util.ArrayList;

public class Dependent extends HouseholdMember {

    // Attributes
    private double weeklyAllowance;

    // Constructor
    public Dependent(String name, int age, double weeklyAllowance) {
        super(name, age);
        this.weeklyAllowance = weeklyAllowance;
    }

    // Getters and Setters
    public double getWeeklyAllowance() {
        return this.weeklyAllowance;
    }

    public void setWeeklyAllowance(double weeklyAllowance) {
        this.weeklyAllowance = weeklyAllowance;
    }

    // Other Methods
    public void printDependent() {
        printMember();
        System.out.println("Weekly Allowance: " + this.weeklyAllowance);
    }
}
