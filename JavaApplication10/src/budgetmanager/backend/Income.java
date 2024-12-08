package budgetmanager.backend;

public class Income {
    
    // Attributes
    private String source;
    private double amount;
    private String frequency;

    // Constructor
    public Income(String source, double amount, String frequency) {
        this.source = source;
        this.amount = amount;
        this.frequency = frequency;
    }

    // Getters and Setters
    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    // Other Methods
    public void printIncome() {
        System.out.println("Income Source: " + this.source + "\n" +
                           "Amount: " + this.amount + "\n" +
                           "Frequency: " + this.frequency);
    }

    public double calculateMonthlyAmount() {
        switch (this.frequency.toLowerCase()) {
            case "weekly":
                return this.amount * 4; // Weekly income multiplied by 4 weeks
            case "biweekly":
                return this.amount * 2; // Biweekly income multiplied by 2
            case "monthly":
                return this.amount; // Monthly income stays the same
            case "annually":
                return this.amount / 12; // Annual income divided by 12 for monthly
            default:
                return 0.0;
        }
    }

    public double calculateYearlyAmount() {
        switch (this.frequency.toLowerCase()) {
            case "weekly":
                return this.amount * 52; // Weekly income multiplied by 52 weeks
            case "biweekly":
                return this.amount * 26; // Biweekly income multiplied by 26
            case "monthly":
                return this.amount * 12; // Monthly income multiplied by 12
            case "annually":
                return this.amount; // Annual income stays the same
            default:
                return 0.0;
        }
    }
}
