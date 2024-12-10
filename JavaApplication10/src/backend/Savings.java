package backend;

public class Savings {

    // Attributes
    private double amountSaved;
    private String towards;
    private double goal;
    
    // Default constructor
    public Savings() {
        this.amountSaved = 0.0;
        this.towards = "";
        this.goal = 0.0;
    }

    // Constructor
    public Savings(double amountSaved, String towards, double goal) {
        this.amountSaved = amountSaved;
        this.towards = towards;
        this.goal = goal;
    }

    // Getters and Setters
    public double getAmountSaved() {
        return this.amountSaved;
    }

    public void setAmountSaved(double amountSaved) {
        this.amountSaved = amountSaved;
    }

    public String getTowards() {
        return this.towards;
    }

    public void setTowards(String towards) {
        this.towards = towards;
    }

    public double getGoal() {
        return this.goal;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    // Other Methods
    public void printSavings() {
        System.out.println("Savings towards: " + this.towards + "\n" +
                           "Amount Saved: " + this.amountSaved + "\n" +
                           "Goal: " + this.goal);
    }

    public void addSavings(double amount) {
        this.amountSaved += amount;
    }

    public void subtractSavings(double amount) {
        if (this.amountSaved >= amount) {
            this.amountSaved -= amount;
        } else {
            System.out.println("Not enough savings to subtract.");
        }
    }
}
