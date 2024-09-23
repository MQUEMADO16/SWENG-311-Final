public class Insurance extends Expense {

    private String insuranceType;
    private String provider;
    private int coverageAmount; // very simplified, generalizes all details to single coverage amount (ie. deductible, out of pocket etc.)

    public Insurance() {
        
        super.Expense();
        this.insuranceType = "";
        this.provider = "";
        this.coverageAmount = 0;
    }

    public Insurance(double amount, String name, boolean recurring, String insuranceType, String provider int coverageAmount) {
        
        super.Expense(amount, name, recurring);
        this.provider = provider;
        this.insuranceType = insuranceType;
        this.coverageAmount = coverageAmount;
    }

    public String getInsuranceType() {

        return this.insuranceType;
    }

    public void setInsuranceType(String insuranceType) {

        this.insuranceType = insuranceType;
    }

    public int getCoverageAmount() {
        
        return this.coverageAmount;
    }

    public void setCoverageAmount(int coverageAmount) {

        this.coverageAmount = coverageAmount;
    }

    public void printInsurance() {

        super.printExpense();
        System.out.println("Insurance Type: " + this.insuranceType);
        System.out.println("Insurance Provider: " + this.provider);
        System.out.println("Coverage: $" + this.coverage);
    }
}