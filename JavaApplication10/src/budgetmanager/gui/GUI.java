package budgetmanager.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements LoginListener {

    static JFrame mainFrame;
    public GUI() {
        new Login(this);
    }

    @Override
    public void onLoginSuccess(String username) {
        // Proceed to the main GUI
        createMainFrame(username);
    }

    public void createMainFrame(String username) {
        mainFrame = new JFrame("Budget Management System");
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setBounds(100, 100, 200, 30);

        JLabel userLabel = new JLabel(username);
        userLabel.setBounds(10, 10, 50, 20);

        mainFrame.setLayout(null);
        mainFrame.add(welcomeLabel);
        mainFrame.setSize(400, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new GUI();
    }
}