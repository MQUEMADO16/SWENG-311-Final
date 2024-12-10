package gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        // Add Menu Items to Menus
        menu.add(menuItemSave);
        menu.add(menuItemExit);
        householdMenu.add(menuItemEditHousehold);
        householdMenu.add(menuItemLoadHousehold);
        budgetMenu.add(menuItemViewBudget);

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
            // Implement the functionality to view the budget
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
        JLabel monthlyIncomeValue = new JLabel("$" + String.format("%.2f", household.getMonthlyIncome()));

        // Monthly Expense
        JLabel monthlyExpenseLabel = new JLabel("Monthly Expense: ");
        JLabel monthlyExpenseValue = new JLabel("$" + String.format("%.2f", household.getMonthlyExpense()));

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

        // Additional fields for dependent and independent
        JLabel weeklyAllowanceLabel = new JLabel("Weekly Allowance:");
        JTextField weeklyAllowanceField = new JTextField(20);
        weeklyAllowanceField.setVisible(false); // Hide initially

        JLabel incomeLabel = new JLabel("Income:");
        JComboBox<String> incomeComboBox = new JComboBox<>();
        incomeComboBox.setVisible(false); // Hide initially

        JLabel weeklyDiscretionarySpendLabel = new JLabel("Weekly Discretionary Spend:");
        JTextField weeklyDiscretionarySpendField = new JTextField(20);
        weeklyDiscretionarySpendField.setVisible(false); // Hide initially

        // Expenses dropdown for both
        JLabel expensesLabel = new JLabel("Expense:");
        JComboBox<String> expensesComboBox = new JComboBox<>();
        for (Expense expense : household.getExpenses()) {
            expensesComboBox.addItem(expense.getName());
        }

        // Expense buttons
        JButton addExpenseButton = new JButton("Add Expense");
        JButton editExpenseButton = new JButton("Edit Expense");
        JButton removeExpenseButton = new JButton("Remove Expense");

        // Income buttons (initially hidden)
        JButton addIncomeButton = new JButton("Add Income");
        JButton editIncomeButton = new JButton("Edit Income");
        JButton removeIncomeButton = new JButton("Remove Income");
        addIncomeButton.setVisible(false);
        editIncomeButton.setVisible(false);
        removeIncomeButton.setVisible(false);

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

        gbc.gridx = 0;
        gbc.gridy = 3;
        addMemberPanel.add(weeklyAllowanceLabel, gbc);

        gbc.gridx = 1;
        addMemberPanel.add(weeklyAllowanceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        addMemberPanel.add(weeklyDiscretionarySpendLabel, gbc);

        gbc.gridx = 1;
        addMemberPanel.add(weeklyDiscretionarySpendField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        addMemberPanel.add(incomeLabel, gbc);

        gbc.gridx = 1;
        addMemberPanel.add(incomeComboBox, gbc);

        // Expenses
        gbc.gridx = 0;
        gbc.gridy = 6;
        addMemberPanel.add(expensesLabel, gbc);

        gbc.gridx = 1;
        addMemberPanel.add(expensesComboBox, gbc);

        // Expense buttons panel
        JPanel expenseButtonPanel = new JPanel(new FlowLayout());
        expenseButtonPanel.add(addExpenseButton);
        expenseButtonPanel.add(editExpenseButton);
        expenseButtonPanel.add(removeExpenseButton);

        gbc.gridx = 1;
        gbc.gridy = 7;
        addMemberPanel.add(expenseButtonPanel, gbc);

        // Income buttons panel (hidden initially)
        JPanel incomeButtonPanel = new JPanel(new FlowLayout());
        incomeButtonPanel.add(addIncomeButton);
        incomeButtonPanel.add(editIncomeButton);
        incomeButtonPanel.add(removeIncomeButton);

        gbc.gridx = 1;
        gbc.gridy = 8;
        addMemberPanel.add(incomeButtonPanel, gbc);

        // Panel for main action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 1;
        gbc.gridy = 9;
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
                        newMember = new Independent(name, age, 0); // Default weeklyDiscretionarySpend = 0
                        // Handle income and weeklyDiscretionarySpend
                    } else if (memberType.equals("Dependent")) {
                        newMember = new Dependent(name, age, 0); // Default weeklyAllowance = 0
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

        // Listener to change fields visibility based on selected member type
        memberTypeCombo.addActionListener(e -> {
            if ("Independent".equals(memberTypeCombo.getSelectedItem())) {
                weeklyAllowanceField.setVisible(false);
                incomeComboBox.setVisible(true); // Show income dropdown
                weeklyDiscretionarySpendField.setVisible(true);
                addIncomeButton.setVisible(true); // Show income buttons
                editIncomeButton.setVisible(true);
                removeIncomeButton.setVisible(true);
            } else {
                weeklyAllowanceField.setVisible(true); // Show weekly allowance for Dependent
                incomeComboBox.setVisible(false);
                weeklyDiscretionarySpendField.setVisible(false);
                addIncomeButton.setVisible(false); // Hide income buttons
                editIncomeButton.setVisible(false);
                removeIncomeButton.setVisible(false);
            }
        });

        // Action listeners for the expense buttons
        addExpenseButton.addActionListener(e -> {
            // Add logic to show a dialog or panel to add an expense
            // TO BE IMPLEMENTED
        });

        editExpenseButton.addActionListener(e -> {
            // Add logic to show a dialog or panel to edit selected expense
            String selectedExpense = (String) expensesComboBox.getSelectedItem();
            if (selectedExpense != null) {
                // Edit selected expense
                // TO BE IMPLEMENTED
            }
        });

        removeExpenseButton.addActionListener(e -> {
            String selectedExpense = (String) expensesComboBox.getSelectedItem();
            if (selectedExpense != null) {
                // Remove selected expense from the list and the member
                HouseholdMember member = household.getMember(nameField.getText());
                member.removeExpense(selectedExpense);
                // Update expensesComboBox by removing the selected expense
                expensesComboBox.removeItem(selectedExpense);
                JOptionPane.showMessageDialog(addMemberPanel, "Expense removed successfully!");
            }
        });
        
        // Action listeners for the income buttons
        addIncomeButton.addActionListener(e -> {
            // Add logic to show a dialog or panel to add an expense
            // TO BE IMPLEMENTED
        });

        editIncomeButton.addActionListener(e -> {
            // Add logic to show a dialog or panel to edit selected expense
            String selectedExpense = (String) expensesComboBox.getSelectedItem();
            if (selectedExpense != null) {
                // Edit selected expense
                // TO BE IMPLEMENTED
            }
        });

        removeIncomeButton.addActionListener(e -> {
            String selectedIncome = (String) incomeComboBox.getSelectedItem();
            if (selectedIncome != null) {
                // Remove selected expense from the list and the member
                Independent member = (Independent) household.getMember(nameField.getText());
                member.removeIncome(selectedIncome);
                // Update expensesComboBox by removing the selected expense
                expensesComboBox.removeItem(selectedIncome);
                JOptionPane.showMessageDialog(addMemberPanel, "Income removed successfully!");
            }
        });

        return addMemberPanel;
    }

    public JPanel createEditMemberPanel(String memberName) {
        HouseholdMember selectedMember = household.getMember(memberName);

        JPanel editMemberPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components

        // Labels and text fields for member details
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        nameField.setText(selectedMember.getName());

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(20);
        ageField.setText(String.valueOf(selectedMember.getAge()));

        JLabel memberTypeLabel = new JLabel("Member Type:");
        JComboBox<String> memberTypeCombo = new JComboBox<>(new String[]{"Independent", "Dependent"});
        memberTypeCombo.setSelectedItem(selectedMember instanceof Independent ? "Independent" : "Dependent");

        // Layout for adding components
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

        gbc.gridx = 0;
        gbc.gridy = 2;
        editMemberPanel.add(memberTypeLabel, gbc);

        gbc.gridx = 1;
        editMemberPanel.add(memberTypeCombo, gbc);

        // Save and Cancel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Save Changes");
        JButton cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        editMemberPanel.add(buttonPanel, gbc);

        // Action listener for the save button
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String memberType = (String) memberTypeCombo.getSelectedItem();

            if (!name.isEmpty() && !ageText.isEmpty()) {
                try {
                    int age = Integer.parseInt(ageText);

                    // Update the member based on selected type
                    if (memberType.equals("Independent") && !(selectedMember instanceof Independent)) {
                        Independent updatedMember = new Independent(name, age, 0); // Default weeklyDiscretionSpend = 0
                        household.removeMember(selectedMember);
                        household.addMember(updatedMember);
                    } else if (memberType.equals("Dependent") && !(selectedMember instanceof Dependent)) {
                        Dependent updatedMember = new Dependent(name, age, 0); // Default weeklyAllowance = 0
                        household.removeMember(selectedMember);
                        household.addMember(updatedMember);
                    } else {
                        // If no type change, just update the details
                        selectedMember.setName(name);
                        selectedMember.setAge(age);
                    }

                    JOptionPane.showMessageDialog(editMemberPanel, "Member details updated successfully!");
                    switchPanel(createHouseholdEditPanel());  // Return to the household edit panel
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(editMemberPanel, "Please enter a valid age.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(editMemberPanel, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action listener for the cancel button
        cancelButton.addActionListener(e -> {
            switchPanel(createHouseholdEditPanel());  // Return to the household edit panel without saving
        });

        return editMemberPanel;
    }

    public static void main(String[] args) {
        new GUI();
    }
}