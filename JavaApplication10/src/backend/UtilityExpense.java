package backend;

public class UtilityExpense extends Expense {
    
    // Attributes
    private String provider;

    // Constructor
    public UtilityExpense(String name, double amount, String dateDue, boolean recurring, String frequency, String provider) {
        super(name, amount, dateDue, recurring, frequency);
        
        this.provider = provider;
    }

    // Getters and Setters
    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    // Other Methods
    public void printExpense() {
        super.printExpense();
        System.out.println("Utility Provider: " + this.provider);
    }
}
