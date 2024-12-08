package budgetmanager.backend;

/*
 *  This is a class that models a single expense of a household member.
 */
public class Expense {
    
    // Attributes
    protected String name;
    protected double amount;
    protected String dateDue;
    protected boolean recurring;
    protected String frequency;

    // Constructor
    public Expense(String name, double amount, String dateDue, boolean recurring, String frequency) {
        this.name = name;
        this.amount = amount;
        this.dateDue = dateDue;
        this.recurring = recurring;
        this.frequency = frequency;
    }

    // Getters and Setters
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDateDue() {
        return this.dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public boolean isRecurring() {
        return this.recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    // Other methods
    public double calculateMonthlyAmount() {
        if (isRecurring()) {
            switch (this.frequency.toLowerCase()) {
                case "daily":
                    return this.amount * 30;
                case "weekly":
                    return this.amount * 4;
                case "biweekly":
                    return this.amount * 2;
                case "monthly":
                    return this.amount;
                case "quarterly":
                    return this.amount / 3;
                case "yearly":
                    return this.amount / 12;
                default:
                    return 0.0; // In case of an invalid frequency
            }
        } else {
            return this.amount; // Non-recurring expenses are just the amount
        }
    }
    
    public double calculateYearlyAmount() {
        if (isRecurring()) {
            switch (this.frequency.toLowerCase()) {
                case "daily":
                    return this.amount * 365;
                case "weekly":
                    return this.amount * 52;
                case "biweekly":
                    return this.amount * 26;
                case "monthly":
                    return this.amount * 12;
                case "quarterly":
                    return this.amount * 4;
                case "yearly":
                    return this.amount;
                default:
                    return 0.0; // In case of an invalid frequency
            }
        } else {
            return this.amount; // Non-recurring expenses are just the amount
        }
    }

    public void printExpense() {
        System.out.println("Expense: " + this.name + "\n" +
                           "Amount: " + this.amount + "\n" +
                           "Date Due: " + this.dateDue + "\n" +
                           "Is Recurring: " + (this.recurring ? "Yes" : "No"));
        if(isRecurring())
            System.out.println("Frequency: " + this.frequency);
    }
}
