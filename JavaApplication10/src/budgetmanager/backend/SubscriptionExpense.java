package budgetmanager.backend;

public class SubscriptionExpense extends Expense {
    
    // Attributes
    private String subscriptionType;
    private String paymentMethod;
    private boolean autoRenewal;

    // Constructor
    public SubscriptionExpense(String name, float amount, String dateDue, boolean recurring, String frequency, String subscriptionType, String paymentMethod, boolean autoRenewal) {
        super(name, amount, dateDue, recurring, frequency);
        
        this.subscriptionType = subscriptionType;
        this.paymentMethod = paymentMethod;
        this.autoRenewal = autoRenewal;
    }

    // Getters and Setters
    public String getSubscriptionType() {
        return this.subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isAutoRenewal() {
        return this.autoRenewal;
    }

    public void setAutoRenewal(boolean autoRenewal) {
        this.autoRenewal = autoRenewal;
    }

    // Other Methods
    public void printExpense() {
        super.printExpense();
        System.out.println("Subscription Type: " + this.subscriptionType + "\n" +
                           "Payment Method: " + this.paymentMethod + "\n" +
                           "Auto Renewal: " + (this.autoRenewal ? "Yes" : "No"));
    }
}
