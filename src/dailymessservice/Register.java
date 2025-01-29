package dailymessservice;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register {

    @SuppressWarnings("unused")
    public static void showRegister() {
        JFrame frame = new JFrame("Daily Mess Service - Register");
        frame.setSize(500, 450);

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);

        JLabel headingLabel = new JLabel("Welcome to Daily Mess Service!");
        headingLabel.setBounds(50, 20, 400, 40); // Positioning the label
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Styling: font size and bold
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
        frame.add(headingLabel);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(50, 70, 150, 30);
        JTextField nameField = new JTextField();
        nameField.setBounds(200, 70, 200, 30);

        JLabel mobileLabel = new JLabel("Mobile No:");
        mobileLabel.setBounds(50, 120, 150, 30);
        JTextField mobileField = new JTextField();
        mobileField.setBounds(200, 120, 200, 30);

        JLabel emailLabel = new JLabel("Email ID:");
        emailLabel.setBounds(50, 170, 150, 30);
        JTextField emailField = new JTextField();
        emailField.setBounds(200, 170, 200, 30);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 220, 150, 30);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 220, 200, 30);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(50, 270, 150, 30);
        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(200, 270, 200, 30);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(100, 320, 100, 30);

        JButton loginButton = new JButton("Go to Login");
        loginButton.setBounds(250, 320, 120, 30);

        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String mobile = mobileField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Validate inputs
            if (name.isEmpty() || mobile.isEmpty() || email.isEmpty() || password.isEmpty()
                    || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Register user directly with database
                if (registerUser(name, email, password, mobile)) {
                    JOptionPane.showMessageDialog(frame, "Registration successful!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    Login.showLogin(); // Redirect to login after successful registration
                } else {
                    JOptionPane.showMessageDialog(frame, "Error registering user. Please try again.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginButton.addActionListener(e -> {
            frame.dispose(); // Close the register window
            Login.showLogin(); // Open the login window
        });

        frame.add(headingLabel);
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(mobileLabel);
        frame.add(mobileField);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(confirmPasswordLabel);
        frame.add(confirmPasswordField);
        frame.add(registerButton);
        frame.add(loginButton);

        frame.setVisible(true);
    }

    private static boolean registerUser(String name, String email, String password, String mobile) {
        String query = "INSERT INTO Users (full_name, email_id, password, mobile_no) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, mobile);

            return stmt.executeUpdate() > 0; // Returns true if user was successfully registered
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error registering user: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false; // Registration failed
    }
}