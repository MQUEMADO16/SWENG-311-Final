package budgetmanager.backend;

public class GroceryExpense extends Expense {
    
    // Attributes
    private String storeName;
    private int itemsCount;

    // Constructor
    public GroceryExpense(String name, float amount, String dateDue, boolean recurring, String frequency, String storeName, int itemsCount) {
        super(name, amount, dateDue, recurring, frequency);
        
        this.storeName = storeName;
        this.itemsCount = itemsCount;
    }

    // Getters and Setters
    public String getStoreName() {
        return this.storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getItemsCount() {
        return this.itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    // Other Methods
    public void printExpense() {
        super.printExpense();
        System.out.println("Store Name: " + this.storeName + "\n" +
                           "Items Count: " + this.itemsCount);
    }
}
