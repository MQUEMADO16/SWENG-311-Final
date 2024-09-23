import java.util.Calendar;

public class Loan extends Expense {
    /*
     *Some Loans have Pay on Princicpal options (not pay on the interest accrued and go straight to the source of the interest)
     *Do we want to have that option here at some point?
     */
    private String loanType;
    private String interestType;
    private String billCycle; //Monthly, weekly, yearly

    private double interestRate;
    private double totalPaid;
    private double loanAmount;
    private double leftToPay;
    private double amountOwed;
    private double yearlyAmount;

    private int paymentsPerYear;
    private int yearsPassed;
    private int yearsToGo;


    //Constructors
    public Loan() {
        super.Expense();
        loanType = "";
        interestType = "";
        billCycle = "";
        interestRate = 0;
        totalPaid = 0;
        leftToPay = 0;
        yearsPassed = 0;
        yearsToGo = 0;
    }
    public Loan(double amount, String name, boolean recurring, double loanSize, double paid, double interest, double owed, String lType, String iType, String billPer, int yrsPassed) {

        super.Expense(amount, name, recurring);
        loanType = lType; //Will be incorperated at later date
        totalPaid = paid;
        interestRate = interest;
        interestType = iType; //Will be incorperated at later date
        billCycle = billPer;
        yearsPassed = yrsPassed;
        loanAmount = loanSize;
        amountOwed = owed;
    }

//Get Functions
    public double getPaymentsPerYear() {

        return calcPaymentsPerYear(get);
    }

    public String getLoanType() {

        return loanType;
    }

    public String getInterestType() {

        return interestType;
    }

    public String getBillCycle() {

        return billCycle;
    }

    public double getInterestRate() {

        return interestRate;
    }
    
    public double getTotalPaid() {
        
        return totalPaid;
    }

    public double getLoanAmount() {


        return loanAmount;
    }

    public double getLeftToPay() {

        return leftToPay;
    }

    public double getAmountOwed() {

        return amountOwed;
    }

    public double getYearlyAmount() {

        return yearlyAmount;
    }

    public int getYearsPassed() {

        return yearsPassed;
    } 

    public int getYearsToGo() {

        return yearsToGo;
    } 

//Calculations
    public void calcYearsToGo() {

        yearsToGo = (int)((-1 * Math.log(((1 - interestRate * leftToPay)/ amountOwed))/Math.log(1 + interestRate)/paymentsPerYear));
    }

    public void calcPaymentsPerYear() {

        switch (billCycle){

            case "Monthly":
                this.paymentsPerYear = 12;
            break;

            case "Weekly":
                this.paymentsPerYear = 52;
            break;
            
            default:
                this.paymentsPerYear = 0;
            break;
        }
    }

    public void calculateEstimatedYearlyAmount() { //Does not consider payments done

        getPaymentsPerYear();
        this.yearlyAmount = amountOwed * ((Math.pow((1 + interestRate), paymentsPerYear) - 1)/interestRate);
    }

    public void setTotalPaid(int payment) {

        this.totalPaid += payment;
    }

}
