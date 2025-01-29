package dailymessservice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AttendancePage {

    @SuppressWarnings("unused")
    public static void showAttendancePage(String userEmail) {
        JFrame frame = new JFrame("Daily Mess Service - Attendance");
        frame.setSize(800, 600);

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Fetch user's full name and username
        String fullName = getFullName(userEmail);

        // Top panel for user name and logout button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding for the top panel

        JLabel userNameLabel = new JLabel(fullName + " (" + userEmail + ")");
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userNameLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setMargin(new Insets(5, 15, 5, 15)); // Add padding inside the Logout button
        logoutButton.addActionListener(e -> {
            frame.dispose(); // Close current frame
            Login.showLogin(); // Redirect to login page (assuming you have a LoginPage class)
        });

        topPanel.add(userNameLabel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.NORTH);

        // Main panel for content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding for the main panel

        JLabel welcomeLabel = new JLabel("Welcome to Attendance Page");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel dishPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel dishLabel = new JLabel("Select Dish:");
        dishLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JComboBox<Dish> dishComboBox = new JComboBox<>();
        dishComboBox.setPreferredSize(new Dimension(300, 30)); // Increased width
        loadDishes(dishComboBox);

        JButton presentButton = new JButton("Mark Present");
        presentButton.setPreferredSize(new Dimension(150, 30));
        dishPanel.add(dishLabel);
        dishPanel.add(dishComboBox);
        dishPanel.add(presentButton);

        JLabel statusLabel = new JLabel("Status: ");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel totalLabel = new JLabel("Monthly Total: ");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JTextField totalTextField = new JTextField();
        totalTextField.setPreferredSize(new Dimension(200, 30));
        totalTextField.setEditable(false);
        totalTextField.setFont(new Font("Arial", Font.BOLD, 16));
        totalPanel.add(totalLabel);
        totalPanel.add(totalTextField);

        String[] columnNames = { "Dish", "Price", "Date", "Time" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Load table data and calculate monthly total
        loadDishDetails(userEmail, tableModel, totalTextField);

        presentButton.addActionListener(e -> {
            Dish selectedDish = (Dish) dishComboBox.getSelectedItem();
            if (selectedDish != null) {
                markAttendance(userEmail, selectedDish.getDishId(), true, statusLabel);
                loadDishDetails(userEmail, tableModel, totalTextField); // Refresh table and total
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a dish.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(welcomeLabel);
        mainPanel.add(dishPanel);
        mainPanel.add(statusLabel);
        mainPanel.add(totalPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        mainPanel.add(tableScrollPane);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Method to fetch user's full name from the database
    private static String getFullName(String userEmail) {
        String query = "SELECT full_name FROM Users WHERE email_id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userEmail);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("full_name");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching user details: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return "Unknown User"; // Default if not found
    }

    private static void loadDishes(JComboBox<Dish> dishComboBox) {
        String query = "SELECT * FROM Dishes Where Is_Active = true";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            dishComboBox.removeAllItems(); // Clear previous items
            while (rs.next()) {
                int dishId = rs.getInt("dish_id");
                String dishName = rs.getString("dish_name");
                String price = rs.getString("price");
                dishComboBox.addItem(new Dish(dishId, dishName, price));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading dishes: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void loadDishDetails(String userEmail, DefaultTableModel tableModel, JTextField totalTextField) {
        String query = """
                    SELECT d.dish_name, d.price, DATE(a.marked_at) AS date_only, TIME(a.marked_at) AS time_only
                    FROM Attendance a
                    JOIN Dishes d ON a.dish_id = d.dish_id
                    WHERE a.user_email = ? AND MONTH(a.marked_at) = MONTH(CURRENT_DATE())
                    ORDER BY a.marked_at DESC
                """;

        String totalQuery = """
                    SELECT SUM(d.price) AS monthly_total
                    FROM Attendance a
                    JOIN Dishes d ON a.dish_id = d.dish_id
                    WHERE a.user_email = ? AND MONTH(a.marked_at) = MONTH(CURRENT_DATE())
                """;

        try (Connection conn = DBConnection.getConnection()) {

            // Load table data
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, userEmail);

                try (ResultSet rs = stmt.executeQuery()) {
                    tableModel.setRowCount(0); // Clear existing rows
                    while (rs.next()) {
                        String dishName = rs.getString("dish_name");
                        double price = rs.getDouble("price");
                        Date date = rs.getDate("date_only");
                        Time time = rs.getTime("time_only");

                        tableModel.addRow(new Object[] { dishName, price, date, time });
                    }
                }
            }

            // Calculate monthly total
            try (PreparedStatement stmt = conn.prepareStatement(totalQuery)) {
                stmt.setString(1, userEmail);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        double monthlyTotal = rs.getDouble("monthly_total");
                        totalTextField.setText(String.format("Rs. %.2f", monthlyTotal));
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading dish details: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void markAttendance(String userEmail, int dishId, boolean isPresent, JLabel statusLabel) {
        String query = "INSERT INTO Attendance (user_email, dish_id, is_present, marked_at) VALUES (?, ?, ?, NOW())";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, userEmail);
            stmt.setInt(2, dishId);
            stmt.setBoolean(3, isPresent);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                statusLabel.setText(
                        "Status: Attendance marked as " + (isPresent ? "Present" : "Absent"));
            } else {
                statusLabel.setText("Status: Failed to mark attendance.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error marking attendance: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    static class Dish {
        private final int dishId;
        private final String dishName;
        private final String price;

        public Dish(int dishId, String dishName, String price) {
            this.dishId = dishId;
            this.dishName = dishName;
            this.price = price;
        }

        public int getDishId() {
            return dishId;
        }

        @Override
        public String toString() {
            return dishName + " - Rs." + price;
        }
    }
}