package gui;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Login {

    // Temp list of users
    private ArrayList<String> usernames = new ArrayList<>(Arrays.asList("mbq5051", "krm6367", "okb5114"));
    private LoginListener listener;

    static JFrame loginFrame;
    static JLabel titleLabel, loginLabel, usernameLabel, passwordLabel;
    static JTextField usernameField;
    static JPasswordField passwordField;
    static JButton submitButton;
    static JCheckBox passwordVisibleBox;

    public Login(LoginListener listener) {
        this.listener = listener;
        createLoginFrame();
    }

    public void createLoginFrame() {

        // Initialize components
        loginFrame = new JFrame("Login");

        titleLabel = new JLabel("Budget Management System");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), titleLabel.getFont().getStyle(), 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        loginLabel = new JLabel("Login");
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");

        usernameField = new JTextField(15);
        usernameField.setPreferredSize(new Dimension(300, 20));

        passwordField = new JPasswordField(15);
        passwordField.setPreferredSize(new Dimension(300, 20));
        passwordField.setEchoChar('●'); // Set default echo character

        submitButton = new JButton("Submit");
        passwordVisibleBox = new JCheckBox("Show Password");

        // Event handlers
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().toLowerCase();
                String password = new String(passwordField.getPassword());

                if (usernames.contains(username) && password.equals("test")) { // Temp password
                    if (listener != null) {
                        listener.onLoginSuccess(username); // Notify listener of successful login
                    }
                    loginFrame.dispose(); // Close login frame
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid credentials. Try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        passwordVisibleBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (passwordVisibleBox.isSelected()) {
                    passwordField.setEchoChar((char) 0); // Show password
                } else {
                    passwordField.setEchoChar('●'); // Hide password
                }
            }
        });

        // Set layout
        loginFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding between components

        // Add components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginFrame.add(titleLabel, gbc);

        gbc.insets = new Insets(30, 5, 5, 5);

        gbc.gridy = 1;
        loginFrame.add(loginLabel, gbc);

        gbc.insets = new Insets(15, 5, 5, 5);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        loginFrame.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        loginFrame.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        loginFrame.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        loginFrame.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_START;
        loginFrame.add(passwordVisibleBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        loginFrame.add(submitButton, gbc);

        // Finalize frame
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setSize(600, 500);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }
}
