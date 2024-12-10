package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import backend.*;

public class GUI extends JFrame implements LoginListener {

    // Class Attributes
    private Household household;
    private Budget budget;
    private String username;

    static JFrame mainFrame;

    static JPanel currentPanel;

    public GUI() {
        new Login(this);
        this.household = new Household();
    }

    @Override
    public void onLoginSuccess(String username) {
        this.username = username;
        createMainFrame();
    }

    public void createMainFrame() {
        // Initialize the main frame
        mainFrame = new JFrame("Budget Management System");
        mainFrame.setLayout(new BorderLayout()); // Use a BorderLayout for flexibility

        // Create the menu panel and set it as the initial panel
        switchPanel(createMenuPanel(username));

        // Create and set up the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create Menus
        JMenu menu = new JMenu("Menu");
        JMenu householdMenu = new JMenu("Household");
        JMenu budgetMenu = new JMenu("Budget");

        // Create Menu Items
        JMenuItem menuItemExit = new JMenuItem("Exit");
        JMenuItem menuItemSave = new JMenuItem("Save");
        JMenuItem menuItemEditHousehold = new JMenuItem("Edit Household");
        JMenuItem menuItemLoadHousehold = new JMenuItem("Load Household");
        JMenuItem menuItemViewBudget = new JMenuItem("View Budget");
        JMenuItem menuItemSummary = new JMenuItem("View Summary"); // New Summary menu item

        // Add Menu Items to Menus
        menu.add(menuItemSave);
        menu.add(menuItemExit);
        householdMenu.add(menuItemEditHousehold);
        householdMenu.add(menuItemLoadHousehold);
        budgetMenu.add(menuItemViewBudget);
        budgetMenu.add(menuItemSummary); // Add Summary menu item to Budget menu

        // Add Menus to the Menu Bar
        menuBar.add(menu);
        menuBar.add(householdMenu);
        menuBar.add(budgetMenu);

        // User
        JMenu userMenu = new JMenu("User: " + username);

        // Add filler menu item to create space between other menus and the user menu
        menuBar.add(Box.createHorizontalGlue()); // This will push the user menu to the right
        menuBar.add(userMenu);

        // Set the menu bar for the frame
        mainFrame.setJMenuBar(menuBar);

        // Configure the frame
        mainFrame.setSize(700, 500);
        mainFrame.setLocationRelativeTo(null); // Center the frame on the screen
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        // Action Listeners for the Menu Items
        menuItemExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to quit?", "Quit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0); // Exit the program if the user confirms
            }
        });

        menuItemSave.addActionListener(e -> {
            // DATABASE STUFF HERE
        });

        menuItemEditHousehold.addActionListener(e -> {
            switchPanel(createHouseholdEditPanel());
        });

        menuItemLoadHousehold.addActionListener(e -> {
            // DATABASE STUFF HERE
        });

        menuItemViewBudget.addActionListener(e -> {
            switchPanel(createBudgetPanel());
        });

        menuItemSummary.addActionListener(e -> {
            switchPanel(createSummaryPanel());
        });
    }

    // Method to switch panels
    public void switchPanel(JPanel newPanel) {
        // Remove the current panel
        if (currentPanel != null) {
            mainFrame.remove(currentPanel);
        }
        currentPanel = newPanel; // Set the new panel as the current panel
        mainFrame.add(currentPanel, BorderLayout.CENTER); // Add the new panel to the frame
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public JPanel createMenuPanel(String username) {
        household.fetchExpenses();
        household.fetchIncomes();
        
        JPanel menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components

        // Create a label for household details section
        JLabel householdDetailsLabel = new JLabel("Household Details");
        householdDetailsLabel.setFont(new Font ("Arial", Font.BOLD, 18));
        householdDetailsLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the label

        // Add householdDetailsLabel to the top of the menu panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span across both columns
        menuPanel.add(householdDetailsLabel, gbc);

        // Household Name
        JLabel householdNameLabel = new JLabel("Household Name: ");
        JLabel householdNameValue = new JLabel(household.getHouseholdName());

        // Monthly Income
        JLabel monthlyIncomeLabel = new JLabel("Monthly Income: ");
        JLabel monthlyIncomeValue = new JLabel("$" + String.format("%.2f", household.calculateMonthlyIncome()));

        // Monthly Expense
        JLabel monthlyExpenseLabel = new JLabel("Monthly Expense: ");
        JLabel monthlyExpenseValue = new JLabel("$" + String.format("%.2f", household.calculateMonthlyExpense()));

        // Spend Balance
        JLabel spendBalanceLabel = new JLabel("Spend Balance: ");
        JLabel spendBalanceValue = new JLabel("$" + String.format("%.2f", household.getSpendBalance()));

        // Savings
        JLabel savingsLabel = new JLabel("Savings: ");
        JLabel savingsValue = new JLabel("$" + String.format("%.2f", household.getSavings().getAmountSaved()));

        // Household Members, display using JList
        JLabel householdMembersLabel = new JLabel("Household Members:");
        DefaultListModel<String> memberListModel = new DefaultListModel<>();
        for (HouseholdMember member : household.getMembers()) {
            memberListModel.addElement(member.getName());
        }
        JList<String> memberList = new JList<>(memberListModel);
        memberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        memberList.setLayoutOrientation(JList.VERTICAL);
        memberList.setVisibleRowCount(5);
        JScrollPane memberScrollPane = new JScrollPane(memberList); // Adding scrolling capability for the list
        
        Font largerFont = new Font("Arial", Font.BOLD, 14); // Adjust the size as needed

        // Set fonts
        householdNameLabel.setFont(largerFont);
        monthlyIncomeLabel.setFont(largerFont);
        monthlyExpenseLabel.setFont(largerFont);
        spendBalanceLabel.setFont(largerFont);
        savingsLabel.setFont(largerFont);
        householdMembersLabel.setFont(largerFont);
        householdNameValue.setFont(largerFont);
        monthlyIncomeValue.setFont(largerFont);
        monthlyExpenseValue.setFont(largerFont);
        spendBalanceValue.setFont(largerFont);
        savingsValue.setFont(largerFont);

        // Create a JPanel to contain the household details
        JPanel householdDetailsPanel = new JPanel(new GridBagLayout());
        householdDetailsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Border around the details
        GridBagConstraints detailsGbc = new GridBagConstraints();
        detailsGbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components inside the panel

        // Household Name
        detailsGbc.gridy = 0;
        detailsGbc.gridx = 0;
        householdDetailsPanel.add(householdNameLabel, detailsGbc);
        detailsGbc.gridx = 1;
        householdDetailsPanel.add(householdNameValue, detailsGbc);

        // Monthly Income
        detailsGbc.gridy = 1;
        detailsGbc.gridx = 0;
        householdDetailsPanel.add(monthlyIncomeLabel, detailsGbc);
        detailsGbc.gridx = 1;
        householdDetailsPanel.add(monthlyIncomeValue, detailsGbc);

        // Monthly Expense
        detailsGbc.gridy = 2;
        detailsGbc.gridx = 0;
        householdDetailsPanel.add(monthlyExpenseLabel, detailsGbc);
        detailsGbc.gridx = 1;
        householdDetailsPanel.add(monthlyExpenseValue, detailsGbc);

        // Spend Balance
        detailsGbc.gridy = 3;
        detailsGbc.gridx = 0;
        householdDetailsPanel.add(spendBalanceLabel, detailsGbc);
        detailsGbc.gridx = 1;
        householdDetailsPanel.add(spendBalanceValue, detailsGbc);

        // Savings
        detailsGbc.gridy = 4;
        detailsGbc.gridx = 0;
        householdDetailsPanel.add(savingsLabel, detailsGbc);
        detailsGbc.gridx = 1;
        householdDetailsPanel.add(savingsValue, detailsGbc);

        // Household Members
        detailsGbc.gridy = 5;
        detailsGbc.gridx = 0;
        householdDetailsPanel.add(householdMembersLabel, detailsGbc);
        detailsGbc.gridx = 1;
        householdDetailsPanel.add(memberScrollPane, detailsGbc);

        // Add the householdDetailsPanel to the main panel
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        menuPanel.add(householdDetailsPanel, gbc);

        return menuPanel; // Return the menu panel
    }

    public JPanel createHouseholdEditPanel() {
        
        // Create the panel for editing the household details
        JPanel householdPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components

        // Labels and text fields for the household details
        JLabel householdNameLabel = new JLabel("Edit Household Name:");
        JTextField householdNameField = new JTextField(20);
        householdNameField.setText(household.getHouseholdName());

        JLabel spendBalanceLabel = new JLabel("Edit Spend Balance:");
        JTextField spendBalanceField = new JTextField(20);
        spendBalanceField.setText(String.valueOf(household.getSpendBalance()));

        JLabel savingsBalanceLabel = new JLabel("Edit Savings Balance:");
        JTextField savingsBalanceField = new JTextField(20);
        savingsBalanceField.setText(String.valueOf(household.getSavings().getAmountSaved()));

        // Add components for household name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        householdPanel.add(householdNameLabel, gbc);

        gbc.gridx = 1;
        householdPanel.add(householdNameField, gbc);

        // Add components for spend balance
        gbc.gridx = 0;
        gbc.gridy = 1;
        householdPanel.add(spendBalanceLabel, gbc);

        gbc.gridx = 1;
        householdPanel.add(spendBalanceField, gbc);

        // Add components for savings balance
        gbc.gridx = 0;
        gbc.gridy = 2;
        householdPanel.add(savingsBalanceLabel, gbc);

        gbc.gridx = 1;
        householdPanel.add(savingsBalanceField, gbc);

        // Create dropdown for selecting a household member
        JLabel memberLabel = new JLabel("Select Household Member:");
        DefaultComboBoxModel<String> memberComboBoxModel = new DefaultComboBoxModel<>();
        for (HouseholdMember member : household.getMembers()) {
            memberComboBoxModel.addElement(member.getName());
        }
        JComboBox<String> memberComboBox = new JComboBox<>(memberComboBoxModel);

        // Add the combo box for selecting a member
        gbc.gridx = 0;
        gbc.gridy = 3;
        householdPanel.add(memberLabel, gbc);

        gbc.gridx = 1;
        householdPanel.add(memberComboBox, gbc);

        // Buttons for removing, editing, and adding members
        JButton removeMemberButton = new JButton("Remove Selected Member");
        JButton editMemberButton = new JButton("Edit Selected Member");
        JButton addMemberButton = new JButton("Add New Member");

        // Panel for member management buttons
        JPanel memberButtonsPanel = new JPanel(new FlowLayout());
        memberButtonsPanel.add(removeMemberButton);
        memberButtonsPanel.add(editMemberButton);
        memberButtonsPanel.add(addMemberButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Make buttons span across two columns
        householdPanel.add(memberButtonsPanel, gbc);

        // Buttons for submit and cancel
        JButton submitButton = new JButton("Submit Changes");
        JButton cancelButton = new JButton("Cancel");

        // Add the submit and cancel buttons
        JPanel submitCancelPanel = new JPanel(new FlowLayout());
        submitCancelPanel.add(submitButton);
        submitCancelPanel.add(cancelButton);

        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        householdPanel.add(submitCancelPanel, gbc);

        // Action listener for the submit button (saving the household details)
        submitButton.addActionListener(e -> {
            String householdName = householdNameField.getText().trim();
            String spendBalance = spendBalanceField.getText().trim();
            String savingsBalance = savingsBalanceField.getText().trim();

            // Validate input
            if (!householdName.isEmpty() && !spendBalance.isEmpty() && !savingsBalance.isEmpty()) {
                try {
                    double spendBalanceValue = Double.parseDouble(spendBalance);
                    double savingsBalanceValue = Double.parseDouble(savingsBalance);

                    household.setHouseholdName(householdName);  // Apply name to household object
                    household.setSpendBalance(spendBalanceValue);
                    household.getSavings().setAmountSaved(savingsBalanceValue);  // Apply the savings balance

                    JOptionPane.showMessageDialog(householdPanel, "Household details updated successfully!");
                    switchPanel(createMenuPanel(username));  // Return to the main panel
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(householdPanel, "Please enter valid numeric values for Spend Balance and Savings Balance.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(householdPanel, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action listener for the cancel button
        cancelButton.addActionListener(e -> {
            switchPanel(createMenuPanel(username)); // Cancel and return to the main menu without making changes
        });

        // Action listener for removing a member
        removeMemberButton.addActionListener(e -> {
            String selectedMemberName = (String) memberComboBox.getSelectedItem();
            if (selectedMemberName != null) {
                HouseholdMember memberToRemove = null;
                for (HouseholdMember member : household.getMembers()) {
                    if (member.getName().equals(selectedMemberName)) {
                        memberToRemove = member;
                        break;
                    }
                }
                if (memberToRemove != null) {
                    household.removeMember(memberToRemove); // Remove member from the household
                    memberComboBoxModel.removeElement(selectedMemberName);  // Update the combo box
                    JOptionPane.showMessageDialog(householdPanel, selectedMemberName + " has been removed.");
                }
            }
        });

        // Action listener for editing a member
        editMemberButton.addActionListener(e -> {
            String selectedMemberName = (String) memberComboBox.getSelectedItem();
            if (selectedMemberName != null) {
                switchPanel(createEditMemberPanel(selectedMemberName));
            }
        });

        // Action listener for adding a new member
        addMemberButton.addActionListener(e -> {
            switchPanel(createAddMemberPanel());
        });

        return householdPanel;
    }
    
    public JPanel createAddMemberPanel() {
        mainFrame.setSize(800, 700);

        JPanel addMemberPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components

        // Labels and text fields for member details
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(20);

        JLabel memberTypeLabel = new JLabel("Member Type:");
        JComboBox<String> memberTypeCombo = new JComboBox<>(new String[]{"Independent", "Dependent"});

        // Add and Cancel buttons
        JButton addButton = new JButton("Add Member");
        JButton cancelButton = new JButton("Cancel");

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        addMemberPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        addMemberPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addMemberPanel.add(ageLabel, gbc);

        gbc.gridx = 1;
        addMemberPanel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        addMemberPanel.add(memberTypeLabel, gbc);

        gbc.gridx = 1;
        addMemberPanel.add(memberTypeCombo, gbc);

        // Panel for main action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 1;
        gbc.gridy = 3;
        addMemberPanel.add(buttonPanel, gbc);

        // Action listener for the add member button
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String memberType = (String) memberTypeCombo.getSelectedItem();

            if (!name.isEmpty() && !ageText.isEmpty()) {
                try {
                    int age = Integer.parseInt(ageText);
                    HouseholdMember newMember = null;

                    if (memberType.equals("Independent")) {
                        newMember = new Independent(name, age, 0.0);
                    } else if (memberType.equals("Dependent")) {
                        newMember = new Dependent(name, age, 0.0);
                    }

                    if (newMember != null) {
                        household.addMember(newMember);
                        JOptionPane.showMessageDialog(addMemberPanel, "Member added successfully!");
                        switchPanel(createHouseholdEditPanel()); // Switch back to the household edit panel
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(addMemberPanel, "Please enter a valid age.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(addMemberPanel, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action listener for the cancel button
        cancelButton.addActionListener(e -> {
            switchPanel(createHouseholdEditPanel()); // Switch back to the household edit panel without adding
        });

        return addMemberPanel;
    }

    public JPanel createEditMemberPanel(String memberName) {
        HouseholdMember member = household.getMember(memberName);

        mainFrame.setSize(800, 700);

        JPanel editMemberPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components

        // Labels and text fields for member details
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(member.getName(), 20);

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(String.valueOf(member.getAge()), 20);

        // Expense list (always visible)
        JComboBox<String> expenseBox = new JComboBox<>();
        for (Expense expense : member.getExpenses()) {
            expenseBox.addItem(expense.getName());
        }

        // Income list (only visible for Independent members)
        JComboBox<String> incomeBox = new JComboBox<>();
        if (member instanceof Independent) {
            Independent independentMember = (Independent) member;
            for (Income income : independentMember.getIncome()) {
                incomeBox.addItem(income.getSource());
            }
            incomeBox.setVisible(true); // Make incomeBox visible if Independent
        } else {
            incomeBox.setVisible(false); // Hide incomeBox if not Independent
        }

        // Add and Cancel buttons
        JButton saveButton = new JButton("Save Member");
        JButton cancelButton = new JButton("Cancel");

        // Add and Remove buttons for Expenses
        JButton addExpenseButton = new JButton("Add Expense");
        JButton removeExpenseButton = new JButton("Remove Expense");

        // Add and Remove buttons for Incomes (only visible for Independent members)
        JButton addIncomeButton = new JButton("Add Income");
        JButton removeIncomeButton = new JButton("Remove Income");

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        editMemberPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        editMemberPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        editMemberPanel.add(ageLabel, gbc);

        gbc.gridx = 1;
        editMemberPanel.add(ageField, gbc);

        // Always visible expense box
        gbc.gridx = 0;
        gbc.gridy = 2;
        editMemberPanel.add(new JLabel("Expense:"), gbc);

        gbc.gridx = 1;
        editMemberPanel.add(expenseBox, gbc);

        // Income box (conditionally visible)
        if (member instanceof Independent) {
            gbc.gridx = 0;
            gbc.gridy = 3;
            editMemberPanel.add(new JLabel("Income:"), gbc);

            gbc.gridx = 1;
            editMemberPanel.add(incomeBox, gbc);
        }

        // Panel for buttons at the bottom
        JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 10, 10));  // GridLayout with two columns
        buttonPanel.add(addExpenseButton);
        buttonPanel.add(removeExpenseButton);

        if (member instanceof Independent) {
            buttonPanel.add(addIncomeButton);
            buttonPanel.add(removeIncomeButton);
        }

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 1;
        gbc.gridy = 4;
        editMemberPanel.add(buttonPanel, gbc);

        // Action listener for the save button
        saveButton.addActionListener(e -> {
            switchPanel(createHouseholdEditPanel());
        });

        // Action listener for the cancel button
        cancelButton.addActionListener(e -> {
            switchPanel(createHouseholdEditPanel()); // Switch back to the household edit panel without saving
        });

        // Action listener for the add expense button
        addExpenseButton.addActionListener(e -> {
            switchPanel(createAddExpensePanel(member));
        });

        // Action listener for the remove expense button
        removeExpenseButton.addActionListener(e -> {
            // Remove the selected expense if an item is selected
            if (expenseBox.getSelectedItem() != null) {
                String selectedExpense = (String) expenseBox.getSelectedItem();
                member.removeExpense(selectedExpense); // Remove expense from member
                // Update the expense box to reflect the changes
                expenseBox.removeItem(selectedExpense);
            }
        });

        // Action listener for the add income button (only for Independent members)
        addIncomeButton.addActionListener(e -> {
            switchPanel(createAddIncomePanel(member));
        });

        // Action listener for the remove income button (only for Independent members)
        removeIncomeButton.addActionListener(e -> {
            if (incomeBox.getSelectedItem() != null) {
                String selectedIncome = (String) incomeBox.getSelectedItem();
                if (member instanceof Independent) {
                    Independent independentMember = (Independent) member;
                    independentMember.removeIncome(selectedIncome); // Remove income from Independent member
                    // Update the income box to reflect the changes
                    incomeBox.removeItem(selectedIncome);
                }
            }
        });

        return editMemberPanel;
    }
    
    public JPanel createAddExpensePanel(HouseholdMember member) {
        JPanel expensePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Expense type
        JLabel typeLabel = new JLabel("Expense Type:");
        JComboBox<String> expenseTypeComboBox = new JComboBox<>(new String[]{"n/a", "Loan", "Subscription", "Insurance", "Grocery", "Utility"});

        // Shared attributes
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(20);

        JLabel dateDueLabel = new JLabel("Date Due:");
        JTextField dateDueField = new JTextField(20);

        JLabel frequencyLabel = new JLabel("Frequency:");
        JTextField frequencyField = new JTextField(20);

        JLabel recurringLabel = new JLabel("Recurring:");
        JCheckBox recurringCheckBox = new JCheckBox();

        // Initially disable the frequency field
        frequencyField.setEnabled(false);

        // Add shared fields to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        expensePanel.add(typeLabel, gbc);
        gbc.gridx = 1;
        expensePanel.add(expenseTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        expensePanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        expensePanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        expensePanel.add(amountLabel, gbc);
        gbc.gridx = 1;
        expensePanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        expensePanel.add(dateDueLabel, gbc);
        gbc.gridx = 1;
        expensePanel.add(dateDueField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        expensePanel.add(frequencyLabel, gbc);
        gbc.gridx = 1;
        expensePanel.add(frequencyField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        expensePanel.add(recurringLabel, gbc);
        gbc.gridx = 1;
        expensePanel.add(recurringCheckBox, gbc);

        // Add an item listener to the recurring checkbox
        recurringCheckBox.addItemListener(e -> {
            if (recurringCheckBox.isSelected()) {
                frequencyField.setEnabled(true); // Enable frequency field when recurring
            } else {
                frequencyField.setEnabled(false); // Disable frequency field when not recurring
            }
        });

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Continue");
        JButton cancelButton = new JButton("Cancel");

        // Add action listeners for the buttons
        submitButton.addActionListener(e -> {
            String selectedType = (String) expenseTypeComboBox.getSelectedItem();
            String name = nameField.getText().trim();
            double amount = Double.parseDouble(amountField.getText().trim());
            String dateDue = dateDueField.getText().trim();
            String frequency = recurringCheckBox.isSelected() ? frequencyField.getText().trim() : "";

            try {
                Expense expense = new Expense(name, amount, dateDue, recurringCheckBox.isSelected(), frequency);

                switch (selectedType) {
                    case "Loan":
                        switchPanel(createAddLoanExpensePanel(member, expense));
                        break;
                    case "Subscription":
                        switchPanel(createAddSubscriptionExpensePanel(member, expense));
                        break;
                    case "Insurance":
                        switchPanel(createAddInsuranceExpensePanel(member, expense));
                        break;
                    case "Grocery":
                        switchPanel(createAddGroceryExpensePanel(member, expense));
                        break;
                    case "Utility":
                        switchPanel(createAddUtilityExpensePanel(member, expense));
                        break;
                    case "n/a":
                        member.addExpense(expense);
                        switchPanel(createEditMemberPanel(member.getName()));
                        break;
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(expensePanel, "Amount must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            switchPanel(createEditMemberPanel(member.getName()));
        });

        buttonsPanel.add(submitButton);
        buttonsPanel.add(cancelButton);

        // Add buttons panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        expensePanel.add(buttonsPanel, gbc);

        return expensePanel;
    }
    
    public JPanel createAddLoanExpensePanel(HouseholdMember member, Expense expense) {
        JPanel loanPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Loan fields
        JLabel loanAmountLabel = new JLabel("Loan Amount:");
        JTextField loanAmountField = new JTextField(20);

        JLabel lenderLabel = new JLabel("Lender:");
        JTextField lenderField = new JTextField(20);

        JLabel loanTypeLabel = new JLabel("Loan Type:");
        JTextField loanTypeField = new JTextField(20);

        JLabel interestRateLabel = new JLabel("Interest Rate (%):");
        JTextField interestRateField = new JTextField(20);

        JLabel leftToPayLabel = new JLabel("Left to Pay:");
        JTextField leftToPayField = new JTextField(20);

        // Add fields to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        loanPanel.add(loanAmountLabel, gbc);
        gbc.gridx = 1;
        loanPanel.add(loanAmountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loanPanel.add(lenderLabel, gbc);
        gbc.gridx = 1;
        loanPanel.add(lenderField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        loanPanel.add(loanTypeLabel, gbc);
        gbc.gridx = 1;
        loanPanel.add(loanTypeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        loanPanel.add(interestRateLabel, gbc);
        gbc.gridx = 1;
        loanPanel.add(interestRateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        loanPanel.add(leftToPayLabel, gbc);
        gbc.gridx = 1;
        loanPanel.add(leftToPayField, gbc);

        // Submit button
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            // Implement the event handler logic here
            try {
                double loanAmount = Double.parseDouble(loanAmountField.getText().trim());
                String lender = lenderField.getText().trim();
                String loanType = loanTypeField.getText().trim();
                double interestRate = Double.parseDouble(interestRateField.getText().trim());
                double leftToPay = Double.parseDouble(leftToPayField.getText().trim());

                // Create a new LoanExpense object or add it to the member's expenses
                LoanExpense loanExpense = new LoanExpense(expense.getName(), expense.getAmount(), expense.getDateDue(), expense.isRecurring(), expense.getFrequency(), loanAmount, lender, loanType, interestRate, leftToPay);
                member.addExpense(loanExpense);

                // Switch back to the member edit panel or other logic
                switchPanel(createEditMemberPanel(member.getName()));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(loanPanel, "Please enter valid numbers for the loan details.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonsPanel.add(submitButton);

        // Add the buttons panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        loanPanel.add(buttonsPanel, gbc);

        return loanPanel;
    }
    
    public JPanel createAddSubscriptionExpensePanel(HouseholdMember member, Expense expense) {
        JPanel subscriptionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Subscription fields
        JLabel subscriptionTypeLabel = new JLabel("Subscription Type:");
        JTextField subscriptionTypeField = new JTextField(20);

        JLabel paymentMethodLabel = new JLabel("Payment Method:");
        JTextField paymentMethodField = new JTextField(20);

        JLabel autoRenewalLabel = new JLabel("Auto Renewal:");
        JCheckBox autoRenewalCheckBox = new JCheckBox();

        // Add fields to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        subscriptionPanel.add(subscriptionTypeLabel, gbc);
        gbc.gridx = 1;
        subscriptionPanel.add(subscriptionTypeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        subscriptionPanel.add(paymentMethodLabel, gbc);
        gbc.gridx = 1;
        subscriptionPanel.add(paymentMethodField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        subscriptionPanel.add(autoRenewalLabel, gbc);
        gbc.gridx = 1;
        subscriptionPanel.add(autoRenewalCheckBox, gbc);

        // Submit button
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            // Implement the event handler logic here
            try {
                String subscriptionType = subscriptionTypeField.getText().trim();
                String paymentMethod = paymentMethodField.getText().trim();
                boolean autoRenewal = autoRenewalCheckBox.isSelected();

                SubscriptionExpense subscriptionExpense = new SubscriptionExpense(expense.getName(), expense.getAmount(), expense.getDateDue(), expense.isRecurring(), expense.getFrequency(), subscriptionType, paymentMethod, autoRenewal);

                // Add the subscription expense to the member
                member.addExpense(subscriptionExpense);

                // Switch back to the member edit panel or other logic
                switchPanel(createEditMemberPanel(member.getName()));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(subscriptionPanel, "Error while submitting subscription expense.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonsPanel.add(submitButton);

        // Add the buttons panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        subscriptionPanel.add(buttonsPanel, gbc);

        return subscriptionPanel;
    }
    
    public JPanel createAddInsuranceExpensePanel(HouseholdMember member, Expense expense) {
        JPanel insurancePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Insurance fields
        JLabel providerLabel = new JLabel("Provider:");
        JTextField providerField = new JTextField(20);

        JLabel insuranceTypeLabel = new JLabel("Insurance Type:");
        JTextField insuranceTypeField = new JTextField(20);

        JLabel coverageAmountLabel = new JLabel("Coverage Amount:");
        JTextField coverageAmountField = new JTextField(20);

        JLabel premiumRateLabel = new JLabel("Premium Rate:");
        JTextField premiumRateField = new JTextField(20);

        // Add fields to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        insurancePanel.add(providerLabel, gbc);
        gbc.gridx = 1;
        insurancePanel.add(providerField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        insurancePanel.add(insuranceTypeLabel, gbc);
        gbc.gridx = 1;
        insurancePanel.add(insuranceTypeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        insurancePanel.add(coverageAmountLabel, gbc);
        gbc.gridx = 1;
        insurancePanel.add(coverageAmountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        insurancePanel.add(premiumRateLabel, gbc);
        gbc.gridx = 1;
        insurancePanel.add(premiumRateField, gbc);

        // Submit button
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            // Implement the event handler logic here
            try {
                String provider = providerField.getText().trim();
                String insuranceType = insuranceTypeField.getText().trim();
                double coverageAmount = Double.parseDouble(coverageAmountField.getText().trim());
                double premiumRate = Double.parseDouble(premiumRateField.getText().trim());

                InsuranceExpense insuranceExpense = new InsuranceExpense( expense.getName(), expense.getAmount(), expense.getDateDue(), expense.isRecurring(), expense.getFrequency(), provider, insuranceType, coverageAmount, premiumRate);

                // Add the insurance expense to the member
                member.addExpense(insuranceExpense);

                // Switch back to the member edit panel or other logic
                switchPanel(createEditMemberPanel(member.getName()));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(insurancePanel, "Coverage amount and premium rate must be valid numbers.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonsPanel.add(submitButton);

        // Add the buttons panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        insurancePanel.add(buttonsPanel, gbc);

        return insurancePanel;
    }
    
    public JPanel createAddGroceryExpensePanel(HouseholdMember member, Expense expense) {
        JPanel groceryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Grocery fields
        JLabel storeNameLabel = new JLabel("Store Name:");
        JTextField storeNameField = new JTextField(20);

        JLabel itemsCountLabel = new JLabel("Items Count:");
        JTextField itemsCountField = new JTextField(20);

        // Add fields to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        groceryPanel.add(storeNameLabel, gbc);
        gbc.gridx = 1;
        groceryPanel.add(storeNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        groceryPanel.add(itemsCountLabel, gbc);
        gbc.gridx = 1;
        groceryPanel.add(itemsCountField, gbc);

        // Submit button
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            // Implement the event handler logic here
            try {
                String storeName = storeNameField.getText().trim();
                int itemsCount = Integer.parseInt(itemsCountField.getText().trim());

                GroceryExpense groceryExpense = new GroceryExpense(expense.getName(), expense.getAmount(), expense.getDateDue(), expense.isRecurring(),expense.getFrequency(), storeName, itemsCount);

                // Add the grocery expense to the member
                member.addExpense(groceryExpense);

                // Switch back to the member edit panel or other logic
                switchPanel(createEditMemberPanel(member.getName()));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(groceryPanel, "Items count must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonsPanel.add(submitButton);

        // Add the buttons panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        groceryPanel.add(buttonsPanel, gbc);

        return groceryPanel;
    }
    
    public JPanel createAddUtilityExpensePanel(HouseholdMember member, Expense expense) {
        JPanel utilityPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Utility fields
        JLabel providerLabel = new JLabel("Utility Provider:");
        JTextField providerField = new JTextField(20);

        // Add fields to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        utilityPanel.add(providerLabel, gbc);
        gbc.gridx = 1;
        utilityPanel.add(providerField, gbc);

        // Submit button
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            // Implement the event handler logic here
            String provider = providerField.getText().trim();

            if (provider.isEmpty()) {
                JOptionPane.showMessageDialog(utilityPanel, "Provider name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UtilityExpense utilityExpense = new UtilityExpense(expense.getName(), expense.getAmount(), expense.getDateDue(), expense.isRecurring(), expense.getFrequency(), provider);

            // Add the utility expense to the member
            member.addExpense(utilityExpense);

            // Switch back to the member edit panel or other logic
            switchPanel(createEditMemberPanel(member.getName()));
        });

        buttonsPanel.add(submitButton);

        // Add the buttons panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        utilityPanel.add(buttonsPanel, gbc);

        return utilityPanel;
    }
    
    public JPanel createAddIncomePanel(HouseholdMember member) {
        // Create the main panel
        JPanel addIncomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components

        // Source field
        JLabel sourceLabel = new JLabel("Source:");
        JTextField sourceField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        addIncomePanel.add(sourceLabel, gbc);

        gbc.gridx = 1;
        addIncomePanel.add(sourceField, gbc);

        // Amount field
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addIncomePanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        addIncomePanel.add(amountField, gbc);

        // Frequency field
        JLabel frequencyLabel = new JLabel("Frequency (e.g., Monthly, Weekly):");
        JTextField frequencyField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 2;
        addIncomePanel.add(frequencyLabel, gbc);

        gbc.gridx = 1;
        addIncomePanel.add(frequencyField, gbc);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // Action for Save Button
        saveButton.addActionListener(e -> {
            String source = sourceField.getText();
            double amount;
            String frequency = frequencyField.getText();

            // Handle invalid amount input
            try {
                amount = Double.parseDouble(amountField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addIncomePanel, "Please enter a valid amount.");
                return;
            }

            // Create the Income object
            Income newIncome = new Income(source, amount, frequency);

            // Add the income to the member's list or handle it as needed
            ((Independent) member).addIncome(newIncome);

            JOptionPane.showMessageDialog(addIncomePanel, "Income saved successfully!");

            switchPanel(createEditMemberPanel(member.getName()));
        });

        // Action for Cancel Button
        cancelButton.addActionListener(e -> {
            switchPanel(createEditMemberPanel(member.getName()));
            JOptionPane.showMessageDialog(addIncomePanel, "Action cancelled.");
        });

        // Add buttons to the panel
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        addIncomePanel.add(buttonPanel, gbc);

        return addIncomePanel;
    }
    
    public JPanel createBudgetPanel() {
        JPanel budgetPanel = new JPanel();
        budgetPanel.setLayout(new BoxLayout(budgetPanel, BoxLayout.Y_AXIS));

        // Create the table for incomes
        String[] incomeColumnNames = {"Source", "Amount", "Frequency", "Monthly Amount"};
        DefaultTableModel incomeTableModel = new DefaultTableModel(incomeColumnNames, 0);

        for (Income income : household.getIncomes()) {
            Object[] incomeData = {income.getSource(), income.getAmount(), income.getFrequency(), income.calculateMonthlyAmount()};
            incomeTableModel.addRow(incomeData);
        }

        JTable incomeTable = new JTable(incomeTableModel);
        JScrollPane incomeScrollPane = new JScrollPane(incomeTable);
        incomeScrollPane.setPreferredSize(new Dimension(500, 150));
        JPanel incomePanel = new JPanel();
        incomePanel.setBorder(BorderFactory.createTitledBorder("Household Incomes"));
        incomePanel.add(incomeScrollPane);

        // Create the table for expenses (only shared attributes)
        String[] expenseColumnNames = {"Expense Name", "Amount", "Date Due", "Recurring"};
        DefaultTableModel expenseTableModel = new DefaultTableModel(expenseColumnNames, 0);

        for (Expense expense : household.getExpenses()) {
            Object[] expenseData = {expense.getName(), expense.getAmount(), expense.getDateDue(), expense.isRecurring()};
            expenseTableModel.addRow(expenseData);
        }

        JTable expenseTable = new JTable(expenseTableModel);
        JScrollPane expenseScrollPane = new JScrollPane(expenseTable);
        expenseScrollPane.setPreferredSize(new Dimension(500, 150));
        JPanel expensePanel = new JPanel();
        expensePanel.setBorder(BorderFactory.createTitledBorder("Household Expenses"));
        expensePanel.add(expenseScrollPane);

        // Add both panels (incomes and expenses) to the main budget panel
        budgetPanel.add(incomePanel);
        budgetPanel.add(Box.createVerticalStrut(10));  // Add some space between the two tables
        budgetPanel.add(expensePanel);

        return budgetPanel;
    }

    public JPanel createSummaryPanel() {
        // Create the panel with GridBagLayout
        JPanel summaryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

        // Title Label
        JLabel titleLabel = new JLabel("Budget Summary", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across 2 columns
        gbc.anchor = GridBagConstraints.CENTER;
        summaryPanel.add(titleLabel, gbc);

        // Options Panel for rule and summary type selection
        JPanel optionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints optionsGbc = new GridBagConstraints();
        optionsGbc.insets = new Insets(5, 5, 5, 5);
        optionsGbc.fill = GridBagConstraints.HORIZONTAL;

        // Savings Rule Selection
        JLabel ruleLabel = new JLabel("Select Savings Rule:");
        optionsGbc.gridx = 0;
        optionsGbc.gridy = 0;
        optionsPanel.add(ruleLabel, optionsGbc);

        String[] rules = {"50-30-20", "80-20"};
        JComboBox<String> ruleComboBox = new JComboBox<>(rules);
        ruleComboBox.setPreferredSize(new Dimension(120, 25));
        optionsGbc.gridx = 1;
        optionsPanel.add(ruleComboBox, optionsGbc);

        // Summary Type Selection
        JLabel summaryTypeLabel = new JLabel("Select Summary Type:");
        optionsGbc.gridx = 0;
        optionsGbc.gridy = 1;
        optionsPanel.add(summaryTypeLabel, optionsGbc);

        String[] summaryTypes = {"Monthly", "Yearly"};
        JComboBox<String> summaryTypeComboBox = new JComboBox<>(summaryTypes);
        summaryTypeComboBox.setPreferredSize(new Dimension(120, 25));
        optionsGbc.gridx = 1;
        optionsPanel.add(summaryTypeComboBox, optionsGbc);

        // Add the options panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        summaryPanel.add(optionsPanel, gbc);

        // Generate Button
        JButton generateButton = new JButton("Generate Summary");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        summaryPanel.add(generateButton, gbc);

        // Text area for displaying the summary
        JTextArea summaryTextArea = new JTextArea(10, 30);
        summaryTextArea.setEditable(false);
        summaryTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        summaryTextArea.setLineWrap(true);
        summaryTextArea.setWrapStyleWord(true);
        summaryTextArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        summaryTextArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(summaryTextArea);
        scrollPane.setPreferredSize(new Dimension(350, 150));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        summaryPanel.add(scrollPane, gbc);

        // Action Listener for the Generate Button
        generateButton.addActionListener(e -> {
            String selectedRule = (String) ruleComboBox.getSelectedItem();
            String selectedSummaryType = (String) summaryTypeComboBox.getSelectedItem();

            Budget budget = new Budget(household, selectedRule);

            // Generate the selected summary
            if ("Monthly".equals(selectedSummaryType)) {
                summaryTextArea.setText("MONTHLY SUMMARY\n\n" +
                        "Total Income: " + budget.getHousehold().calculateMonthlyIncome() + "\n" +
                        "Total Expenses: " + budget.getHousehold().calculateMonthlyExpense() + "\n" +
                        "Savings: " + budget.getHousehold().getSavings().getAmountSaved() + "\n" +
                        "Needs (50%): " + (budget.getHousehold().calculateMonthlyIncome() * 0.50) + "\n" +
                        "Wants (30%): " + (budget.getHousehold().calculateMonthlyIncome() * 0.30));
            } else if ("Yearly".equals(selectedSummaryType)) {
                summaryTextArea.setText("YEARLY SUMMARY\n\n" +
                        "Total Income: " + budget.getHousehold().calculateYearlyIncome() + "\n" +
                        "Total Expenses: " + budget.getHousehold().calculateYearlyExpense() + "\n" +
                        "Savings: " + (budget.getHousehold().getSavings().getAmountSaved() * 12) + "\n" +
                        "Needs (50%): " + (budget.getHousehold().calculateYearlyIncome() * 0.50) + "\n" +
                        "Wants (30%): " + (budget.getHousehold().calculateYearlyIncome() * 0.30));
            }
        });

        return summaryPanel;
    }
            
    public static void main(String[] args) {  
        new GUI();
    }
}