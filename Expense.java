public class Expense {
    
    protected double amount;
    protected String name;
    protected boolean recurring;

    public Expense () {

        this.amount = 0.0;
        this.name = "";
        this.recurring = false;
    }

    public Expense(double amount, String name, boolean recurring) {

        this.amount = amount;
        this.name = name;
        this.recurring = recurring;
    }

    public double getAmount() {

        return amount;
    }

    public void setAmount(double amount) {

        this.amount = amount;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public boolean getRecurring() {

        return recurring;
    }
    public void setRecurring(boolean recurring) {

        this.recurring = recurring;
    }

    public void calculateAnnualTotalExpense(int currentMonth) {

        if(recurring) {
            double wholeYear = amount * 12;
            double restOfYear = amount * (12 - currentMonth);
            System.out.print("For the year this expense will cost: $" + wholeYear + "/nFor the rest of the year it will cost: $" + restOfYear);
        }
        else {
            System.out.println("This expense is not recurring. Annual cost is: $" + amount);
        }
    }

    public void printExpense() {

        System.out.print("Expense name: " + name + "/nAmount: $" + amount);
        if(recurring) System.out.println("Expense is recurring");
        else System.out.println("Expense is not recurring");

    }
}
