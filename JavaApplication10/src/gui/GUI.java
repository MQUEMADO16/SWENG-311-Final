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
            switchPanel(createHouseholdPanel());
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

    public JPanel createHouseholdPanel() {
        // Create the panel for entering the household name
        JPanel householdPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add spacing between components

        // Label and text field for the household name
        JLabel householdNameLabel = new JLabel("Edit Household Name:");
        JTextField householdNameField = new JTextField(20);
        JButton submitButton = new JButton("Submit");

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        householdPanel.add(householdNameLabel, gbc);

        gbc.gridx = 1;
        householdPanel.add(householdNameField, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;
        householdPanel.add(submitButton, gbc);

        // Action listener for the submit button
        submitButton.addActionListener(e -> {
            String householdName = householdNameField.getText().trim();

            if (!householdName.isEmpty()) {
                household.setHouseholdName(householdName);  // Apply the name to the household object
                JOptionPane.showMessageDialog(householdPanel, "Household name updated successfully!");
                switchPanel(createMenuPanel(username));
            } else {
                JOptionPane.showMessageDialog(householdPanel, "Please enter a valid household name.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return householdPanel;
    }

    public static void main(String[] args) {
        new GUI();
    }
}