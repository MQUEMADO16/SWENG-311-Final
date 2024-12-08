package budgetmanager.backend;

public class InsuranceExpense extends Expense {
    
    // Attributes
    private String provider;
    private String insuranceType;
    private double coverageAmount;
    private double premiumRate;

    // Constructor
    public InsuranceExpense(String name, double amount, String dateDue, boolean recurring, String frequency, String provider, String insuranceType, double coverageAmount, double premiumRate) {
        super(name, amount, dateDue, recurring, frequency);
        
        this.provider = provider;
        this.insuranceType = insuranceType;
        this.coverageAmount = coverageAmount;
        this.premiumRate = premiumRate;
    }

    // Getters and Setters
    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getInsuranceType() {
        return this.insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public double getCoverageAmount() {
        return this.coverageAmount;
    }

    public void setCoverageAmount(double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }
    
    public double getPremiumRate() {
        return this.premiumRate;
    }
    
    public void setPremiumRate(double premiumRate) {
        this.premiumRate = premiumRate;
    }

    // Other Methods
    public void printExpense() {
        super.printExpense();
        System.out.println("Insurance Provider: " + this.provider + "\n" +
                           "Insurance Type: " + this.insuranceType + "\n" +
                           "Coverage Amount: " + this.coverageAmount);
    }

    public double calculatePremium() {
        return this.coverageAmount * premiumRate;
    }
}
