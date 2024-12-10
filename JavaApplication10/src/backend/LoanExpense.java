package backend;

public class LoanExpense extends Expense {
    
    // Attributes
    private double loanAmount;
    private String lender;
    private String loanType;
    private double interestRate;
    private double leftToPay;

    // Constructor
    public LoanExpense(String name, double amount, String dateDue, boolean recurring, String frequency, double loanAmount, String lender, String loanType, double interestRate, double leftToPay) {
        super(name, amount, dateDue, recurring, frequency);
        
        this.loanAmount = loanAmount;
        this.lender = lender;
        this.loanType = loanType;
        this.interestRate = interestRate;
        this.leftToPay = leftToPay;
    }

    // Getters and Setters
    public double getLoanAmount() {
        return this.loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLender() {
        return this.lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public String getLoanType() {
        return this.loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public double getInterestRate() {
        return this.interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getLeftToPay() {
        return this.leftToPay;
    }

    public void setLeftToPay(double leftToPay) {
        this.leftToPay = leftToPay;
    }

    // Other Methods
    public double calculateMonthlyAmount() {
        // Monthly interest rate
        double monthlyInterestRate = this.interestRate / 100 / 12;
        
        // If the loan has an interest rate, calculate monthly payment
        if (monthlyInterestRate > 0) {
            return (this.loanAmount * monthlyInterestRate) / (1 - Math.pow(1 + monthlyInterestRate, -12)); // assuming a 12 month loan term
        } else {
            // If interest rate is 0%, the payment is just the loan amount divided by 12 (one year)
            return this.loanAmount / 12;  // Assuming a 12 month term
        }
    }
    
    @Override
    public void printExpense() {
        super.printExpense();
        System.out.println("Loan Lender: " + this.lender + "\n" +
                           "Loan Amount: " + this.loanAmount + "\n" +
                           "Loan Type: " + this.loanType + "\n" +
                           "Interest Rate: " + this.interestRate + "%\n" +
                           "Left to Pay: " + this.leftToPay);
    }
}
