package dailymessservice;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    @SuppressWarnings("unused")
    public static void showLogin() {
        // Create the frame
        JFrame frame = new JFrame("Daily Mess Service - Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // Add components
        JLabel headingLabel = new JLabel("Login to Daily Mess Service", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headingLabel.setBounds(10, 20, 380, 30);

        JLabel emailLabel = new JLabel("Email ID:");
        emailLabel.setBounds(50, 80, 80, 25);

        JTextField emailField = new JTextField();
        emailField.setBounds(140, 80, 200, 25);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 120, 80, 25);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(140, 120, 200, 25);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(70, 180, 100, 30);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(200, 180, 100, 30);

        // Add action listeners
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Email and Password cannot be empty.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DBConnection.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(
                            "SELECT full_name, role FROM Users WHERE email_id = ? AND password = ?")) {
                stmt.setString(1, email);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String fullName = rs.getString("full_name");
                        String role = rs.getString("role");

                        JOptionPane.showMessageDialog(frame, "Login successful! Welcome, " + fullName + ".",
                                "Success", JOptionPane.INFORMATION_MESSAGE);

                        frame.dispose();

                        // Redirect based on role
                        if ("admin".equalsIgnoreCase(role)) {
                            AdminDashboard.showDashboard();
                        } else if ("user".equalsIgnoreCase(role)) {
                            AttendancePage.showAttendancePage(email);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Unrecognized role.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid credentials.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            frame.dispose();
            Register.showRegister();
        });

        // Add components to frame
        frame.add(headingLabel);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(registerButton);

        // Display frame
        frame.setVisible(true);
    }
}
