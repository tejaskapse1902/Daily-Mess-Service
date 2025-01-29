package dailymessservice;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class AdminDashboard {

    @SuppressWarnings("unused")
    public static void showDashboard() {
        JFrame frame = new JFrame("Admin Dashboard - Daily Mess Service");
        frame.setSize(1400, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Center the frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // Side Panel (Navigation)
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(230, 230, 250)); // Light lavender background
        sidePanel.setPreferredSize(new Dimension(200, 800));

        // Buttons for Navigation
        JButton todayEntriesButton = new JButton("Today's Entries");
        JButton monthlyEntriesButton = new JButton("Monthly Entries");
        JButton userProfileButton = new JButton("User Details");
        JButton manageDishesButton = new JButton("Manage Dishes");
        JButton logoutButton = new JButton("Logout");

        // Styling Buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(180, 40);
        todayEntriesButton.setFont(buttonFont);
        todayEntriesButton.setPreferredSize(buttonSize);

        monthlyEntriesButton.setFont(buttonFont);
        monthlyEntriesButton.setPreferredSize(buttonSize);

        userProfileButton.setFont(buttonFont);
        userProfileButton.setPreferredSize(buttonSize);

        manageDishesButton.setFont(buttonFont);
        manageDishesButton.setPreferredSize(buttonSize);

        logoutButton.setFont(buttonFont);
        logoutButton.setPreferredSize(buttonSize);

        todayEntriesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        monthlyEntriesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        userProfileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        manageDishesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidePanel.add(Box.createRigidArea(new Dimension(0, 50)));
        sidePanel.add(todayEntriesButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(monthlyEntriesButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(userProfileButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(manageDishesButton);
        sidePanel.add(Box.createVerticalGlue());
        sidePanel.add(logoutButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 50)));

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel todayentries = TodayEntries();
        JPanel monthlyentries = MonthlyEntries();
        JPanel userdetails = UserDetails();
        JPanel managedishes = ManageDishes();

        tabbedPane.addTab("Today's Entries", todayentries);
        tabbedPane.addTab("Monthly Entries", monthlyentries);
        tabbedPane.addTab("User Details", userdetails);
        tabbedPane.addTab("Manage Dishes", managedishes);

        // Add Action Listeners for Navigation Buttons
        todayEntriesButton.addActionListener(e -> tabbedPane.setSelectedIndex(0));
        monthlyEntriesButton.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        userProfileButton.addActionListener(e -> tabbedPane.setSelectedIndex(2));
        manageDishesButton.addActionListener(e -> tabbedPane.setSelectedIndex(3));

        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Logged out successfully!");
            frame.dispose(); // Close the dashboard
            Login.showLogin(); // Show login screen
        });

        // Add Components to Frame
        frame.add(sidePanel, BorderLayout.WEST);
        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static JPanel TodayEntries() {
        // Main Panel with BorderLayout and padding
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // Title Label with Styling
        JLabel dashboardLabel = new JLabel("Dashboard - Today's Entries", SwingConstants.CENTER);
        dashboardLabel.setFont(new Font("Arial", Font.BOLD, 22));
        dashboardLabel.setForeground(new Color(0, 123, 255)); // Set a blue color for the title
        dashboardLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Add space below the title
        panel.add(dashboardLabel, BorderLayout.NORTH);

        // Table for displaying today's entries
        JTable dashboardTable = new JTable(new DefaultTableModel(new Object[][] {}, new String[] {
                "Email ID", "Full Name", "Dish", "Price", "Time"
        }));
        dashboardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        dashboardTable.getTableHeader().setBackground(new Color(200, 221, 242)); // Light blue header background
        dashboardTable.setFont(new Font("Arial", Font.PLAIN, 14));
        dashboardTable.setRowHeight(30); // Increased row height for better readability
        dashboardTable.setSelectionBackground(new Color(173, 216, 230)); // Light blue selection background
        dashboardTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Single row selection

        JScrollPane scrollPane = new JScrollPane(dashboardTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Today's Attendance Entries")); // Add border to table

        panel.add(scrollPane, BorderLayout.CENTER);

        // Footer Panel for Total Entries
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel totalEntriesLabel = new JLabel("Total Entries: 0");
        totalEntriesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalEntriesLabel.setForeground(Color.BLUE); // Blue color for the total label

        footerPanel.add(totalEntriesLabel);
        panel.add(footerPanel, BorderLayout.SOUTH);

        // Load data into the table and update total entries dynamically
        loadDashboardData(dashboardTable, totalEntriesLabel);

        return panel;
    }

    @SuppressWarnings("unused")
    private static JPanel MonthlyEntries() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title Label
        JLabel monthlyLabel = new JLabel("Dashboard - Monthly Entries", SwingConstants.CENTER);
        monthlyLabel.setFont(new Font("Arial", Font.BOLD, 20));
        monthlyLabel.setForeground(new Color(0, 123, 255)); // Set a blue color for the title
        monthlyLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(monthlyLabel, BorderLayout.NORTH);

        // Controls Panel
        JPanel controlsPanel = new JPanel(new GridBagLayout());
        controlsPanel.setBorder(BorderFactory.createTitledBorder("Search by Email"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel searchLabel = new JLabel("Enter Email ID:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField searchField = new JTextField(20);

        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));

        JLabel totalLabel = new JLabel("Total: ₹0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(Color.BLUE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        controlsPanel.add(searchLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        controlsPanel.add(searchField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        controlsPanel.add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        controlsPanel.add(totalLabel, gbc);

        // Add Generate Bill Button after Total Label
        JButton downloadBillButton = new JButton("Download Bill");
        downloadBillButton.setPreferredSize(new Dimension(120, 30));

        gbc.gridx = 0;
        gbc.gridy = 2; // Place it below the Total Label
        gbc.gridwidth = 3; // Span across all columns
        gbc.anchor = GridBagConstraints.CENTER;
        controlsPanel.add(downloadBillButton, gbc);

        panel.add(controlsPanel, BorderLayout.CENTER);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Monthly Dish Entries"));

        JTable monthlyTable = new JTable(new DefaultTableModel(new Object[][] {}, new String[] {
                "Dish", "Price", "Date", "Time"
        }));
        monthlyTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        monthlyTable.setFont(new Font("Arial", Font.PLAIN, 14));
        monthlyTable.setRowHeight(25);
        monthlyTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(monthlyTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(tablePanel, BorderLayout.SOUTH);

        // Search Button Action
        searchButton.addActionListener(e -> {
            String email = searchField.getText().trim();
            if (!email.isEmpty()) {
                loadMonthlyDishes(email, monthlyTable);
                double total = loadMonthlyDishesTotal(email, monthlyTable);
                totalLabel.setText("Total: ₹" + String.format("%.2f", total));
            } else {
                JOptionPane.showMessageDialog(panel, "Please enter a valid email ID.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        //
        downloadBillButton.addActionListener(e -> {

            String email = searchField.getText().trim();
            if (!email.isEmpty()) {
                try {
                    String filePath = email + "_MonthlyBill.pdf";
                    BillPDFGenerator bill = new BillPDFGenerator();
                    bill.downloadBill(email, filePath);
                    JOptionPane.showMessageDialog(null, "Bill Downloaded Successfully in " + filePath + "", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Error generating bill: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Please enter a valid email ID.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    @SuppressWarnings("unused")
    private static JPanel UserDetails() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title Label
        JLabel monthlyLabel = new JLabel("Dashboard - User Profile", SwingConstants.CENTER);
        monthlyLabel.setFont(new Font("Arial", Font.BOLD, 20));
        monthlyLabel.setForeground(new Color(0, 123, 255)); // Set a blue color for the title
        monthlyLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        mainPanel.add(monthlyLabel, BorderLayout.NORTH);

        // Left Panel (Email Search Panel)
        JPanel emailPanel = new JPanel(new GridBagLayout());
        emailPanel.setBorder(BorderFactory.createTitledBorder("Search by Email"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel searchLabel = new JLabel("Enter Email ID:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField searchField = new JTextField(20);

        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));

        gbc.gridx = 0;
        gbc.gridy = 0;
        emailPanel.add(searchLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        emailPanel.add(searchField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        emailPanel.add(searchButton, gbc);

        // Right Panel (User Details Panel)
        JPanel userDetailsPanel = new JPanel(new GridBagLayout());
        userDetailsPanel.setBorder(BorderFactory.createTitledBorder("User Details"));

        // GridBagConstraints for alignment
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create labels and value fields
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel nameField = new JLabel(" ");
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel emailField = new JLabel(" ");
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel phoneLabel = new JLabel("Phone No:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel phoneField = new JLabel(" ");
        phoneField.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel dateLabel = new JLabel("Start Date:");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel dateField = new JLabel(" ");
        dateField.setFont(new Font("Arial", Font.PLAIN, 16));

        // Add Name row
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3; // Set weight for label column
        userDetailsPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7; // Set weight for value column
        userDetailsPanel.add(nameField, gbc);

        // Add Email row
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        userDetailsPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        userDetailsPanel.add(emailField, gbc);

        // Add Phone No row
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        userDetailsPanel.add(phoneLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        userDetailsPanel.add(phoneField, gbc);

        // Add Start Date row
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        userDetailsPanel.add(dateLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.7;
        userDetailsPanel.add(dateField, gbc);

        // Split Pane (Email Panel and User Details Panel)
        JSplitPane topSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, emailPanel, userDetailsPanel);
        topSplitPane.setDividerLocation(500);

        // Table Panel (100% Width)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("User Dish Entries"));

        JTable monthlyTable = new JTable(new DefaultTableModel(new Object[][] {}, new String[] {
                "Dish", "Price", "Date", "Time"
        }));
        monthlyTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        monthlyTable.setFont(new Font("Arial", Font.PLAIN, 14));
        monthlyTable.setRowHeight(25);
        monthlyTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(monthlyTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add Split Pane and Table Panel to Main Panel
        mainPanel.add(topSplitPane, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);

        // Search Button Action
        searchButton.addActionListener(e -> {
            String email = searchField.getText().trim();
            if (!email.isEmpty()) {

                User getUserDetails = userAllDish(email, monthlyTable);
                // Update User Details Panel with fetched data
                nameField.setText(getUserDetails.getFullName());
                emailField.setText(getUserDetails.getEmailId());
                phoneField.setText(getUserDetails.getMobileNo());
                dateField.setText(getUserDetails.getCreatedAt());
            } else {
                JOptionPane.showMessageDialog(mainPanel, "Please enter a valid email ID.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return mainPanel;
    }

    @SuppressWarnings("unused")
    private static JPanel ManageDishes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title Label
        JLabel crudLabel = new JLabel("Dashboard - Manage Dishes", SwingConstants.CENTER);
        crudLabel.setFont(new Font("Arial", Font.BOLD, 20));
        crudLabel.setForeground(new Color(0, 123, 255)); // Set a blue color for the title
        crudLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(crudLabel, BorderLayout.NORTH);

        // Controls Panel
        JPanel controls = new JPanel(new GridBagLayout());
        controls.setBorder(BorderFactory.createTitledBorder("Dish Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel dishNameLabel = new JLabel("Dish Name:");
        JTextField dishNameField = new JTextField(15);

        JLabel dishPriceLabel = new JLabel("Price:");
        JTextField dishPriceField = new JTextField(15);

        JButton addButton = new JButton("Add Dish");
        JButton updateButton = new JButton("Update Dish");
        JButton deleteButton = new JButton("Delete Dish");

        gbc.gridx = 0;
        gbc.gridy = 0;
        controls.add(dishNameLabel, gbc);

        gbc.gridx = 1;
        controls.add(dishNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        controls.add(dishPriceLabel, gbc);

        gbc.gridx = 1;
        controls.add(dishPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        controls.add(buttonPanel, gbc);

        panel.add(controls, BorderLayout.CENTER);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Dish List"));

        JTable crudTable = new JTable(new DefaultTableModel(new Object[][] {}, new String[] { "Dish Name", "Price" }));
        crudTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        crudTable.setFont(new Font("Arial", Font.PLAIN, 15));
        crudTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(crudTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(tablePanel, BorderLayout.SOUTH);

        // Event Listeners for CRUD operations
        addButton.addActionListener(e -> addDish(dishNameField, dishPriceField, crudTable));
        updateButton.addActionListener(e -> updateDish(dishNameField, dishPriceField, crudTable));
        deleteButton.addActionListener(e -> deleteDish(dishNameField, dishPriceField, crudTable));

        crudTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = crudTable.getSelectedRow();
            if (selectedRow >= 0) {
                dishNameField.setText(crudTable.getValueAt(selectedRow, 0).toString());
                dishPriceField.setText(crudTable.getValueAt(selectedRow, 1).toString());
            }
        });

        loadAllDishes(crudTable); // Load dishes initially
        return panel;
    }

    // Method to load dashboard data (current date attendance)
    private static void loadDashboardData(JTable table, JLabel totalEntriesLabel) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String query = """
                SELECT u.email_id, u.full_name, d.dish_name, d.price, a.marked_at
                FROM Attendance a
                JOIN Users u ON a.user_email = u.email_id
                JOIN Dishes d ON a.dish_id = d.dish_id
                WHERE DATE(a.marked_at) = CURRENT_DATE()
                ORDER BY a.marked_at DESC
                """;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            model.setRowCount(0); // Clear existing rows

            int totalEntries = 0;

            while (rs.next()) {
                String email = rs.getString("email_id");
                String fullName = rs.getString("full_name");
                String dish = rs.getString("dish_name");
                double price = rs.getDouble("price");
                Timestamp timestamp = rs.getTimestamp("marked_at");
                String time = timestamp != null ? timestamp.toLocalDateTime().toLocalTime().toString() : "";

                model.addRow(new Object[] { email, fullName, dish, price, time });
                totalEntries++;
            }

            // Update total entries label
            totalEntriesLabel.setText("Total Entries: " + totalEntries);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading dashboard data: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to load monthly dishes for a specific email_id
    private static void loadMonthlyDishes(String email, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String query = """
                SELECT d.dish_name, d.price, a.marked_at
                FROM Attendance a
                JOIN Dishes d ON a.dish_id = d.dish_id
                WHERE a.user_email = ? AND MONTH(a.marked_at) = MONTH(CURRENT_DATE())
                ORDER BY a.marked_at DESC
                """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                String dishName = rs.getString("dish_name");
                double price = rs.getDouble("price");
                Timestamp timestamp = rs.getTimestamp("marked_at");
                String time = timestamp != null ? timestamp.toLocalDateTime().toLocalTime().toString() : "";

                model.addRow(new Object[] { dishName, price, timestamp, time });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading monthly dishes: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to add a dish
    private static void addDish(JTextField dishNameField, JTextField dishPriceField, JTable table) {
        String dishName = dishNameField.getText().trim();
        String priceText = dishPriceField.getText().trim();

        if (dishName.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Dish Name and Price are required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            String query = "INSERT INTO Dishes (dish_name, price) VALUES (?, ?)";

            try (Connection conn = DBConnection.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, dishName);
                stmt.setDouble(2, price);
                stmt.executeUpdate();
                loadAllDishes(table); // Reload the dishes table
                JOptionPane.showMessageDialog(null, "Dish added successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                dishNameField.setText("");
                dishPriceField.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price value.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding dish: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update a dish
    private static void updateDish(JTextField dishNameField, JTextField dishPriceField, JTable table) {
        String dishName = dishNameField.getText().trim();
        String priceText = dishPriceField.getText().trim();

        if (dishName.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Dish Name and Price are required.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            String query = "UPDATE Dishes SET price = ? WHERE dish_name = ?";

            try (Connection conn = DBConnection.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setDouble(1, price);
                stmt.setString(2, dishName);
                stmt.executeUpdate();
                loadAllDishes(table); // Reload the dishes table
                JOptionPane.showMessageDialog(null, "Dish price updated successfully.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                dishNameField.setText("");
                dishPriceField.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price value.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating dish: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to delete a dish
    private static void deleteDish(JTextField dishNameField, JTextField dishPriceField, JTable table) {
        String dishName = dishNameField.getText().trim();

        if (dishName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Dish Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this dish?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            String query = "UPDATE Dishes SET Is_Active = false WHERE dish_name = ?";
            try (Connection conn = DBConnection.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, dishName);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    loadAllDishes(table); // Reload the dishes table
                    JOptionPane.showMessageDialog(null, "Dish deleted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    dishNameField.setText("");
                    dishPriceField.setText("");

                } else {
                    JOptionPane.showMessageDialog(null, "Dish not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error deleting dish: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to load all dishes for CRUD operations
    private static void loadAllDishes(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String query = "SELECT dish_name, price FROM Dishes where Is_Active = true";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                String dishName = rs.getString("dish_name");
                double price = rs.getDouble("price");
                model.addRow(new Object[] { dishName, price });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading dishes: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static double loadMonthlyDishesTotal(String email, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String query = """
                SELECT d.dish_name, d.price, a.marked_at
                FROM Attendance a
                JOIN Dishes d ON a.dish_id = d.dish_id
                WHERE a.user_email = ? AND MONTH(a.marked_at) = MONTH(CURRENT_DATE())
                ORDER BY a.marked_at DESC
                """;

        double total = 0; // To calculate total price

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                String dishName = rs.getString("dish_name");
                double price = rs.getDouble("price");
                Timestamp timestamp = rs.getTimestamp("marked_at");
                String time = timestamp != null ? timestamp.toLocalDateTime().toLocalTime().toString() : "";

                // Add the price to the total
                total += price;

                model.addRow(new Object[] { dishName, price, timestamp.toLocalDateTime().toLocalDate(), time });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading monthly dishes: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return total;
    }

    // Method to load monthly dishes for a specific email_id
    private static User userAllDish(String email, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String query = """
                SELECT d.dish_name, d.price, a.marked_at
                FROM Attendance a
                JOIN Dishes d ON a.dish_id = d.dish_id
                WHERE a.user_email = ?
                    ORDER BY a.marked_at DESC
                    """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            model.setRowCount(0); // Clear existing rows

            while (rs.next()) {
                String dishName = rs.getString("dish_name");
                double price = rs.getDouble("price");
                Timestamp timestamp = rs.getTimestamp("marked_at");
                String time = timestamp != null ? timestamp.toLocalDateTime().toLocalTime().toString() : "";

                model.addRow(new Object[] { dishName, price, timestamp, time });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading monthly dishes: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        String getUserQuery = "SELECT * FROM Users WHERE email_id = ?";
        String fullName = null, mobileNo = null, startDate = null;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(getUserQuery)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            // Check if ResultSet has data
            if (rs.next()) {
                fullName = rs.getString("full_name");
                mobileNo = rs.getString("mobile_no");
                // Retrieve and format the date
                java.sql.Date date = rs.getDate("created_at");
                startDate = date.toString(); // Converts to 'yyyy-MM-dd
            } else {
                JOptionPane.showMessageDialog(null, "User not found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading User Details: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Return the User object
        return new User(fullName, email, mobileNo, startDate);

    }

}

class BillPDFGenerator {

    public void downloadBill(String email, String filePath) {

        User user = new User();
        user.getUser(email);

        BillDataFetcher.BillData billData = BillDataFetcher.fetchBillData(email);

        String name = user.getFullName();
        String mobile = user.getMobileNo();
        String joiningDate = user.getCreatedAt();

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Title
            com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            com.itextpdf.text.Paragraph titleMain = new Paragraph("Daily Mess Service", titleFont);
            titleMain.setAlignment(Element.ALIGN_CENTER);

            com.itextpdf.text.Paragraph title = new Paragraph("Monthly Bill", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(titleMain);
            document.add(title);
            document.add(new Paragraph("\n"));

            // User Information
            com.itextpdf.text.Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Name: " + name, infoFont));
            document.add(new Paragraph("Mobile No.: " + mobile, infoFont));
            document.add(new Paragraph("Email Id: " + email, infoFont));
            document.add(new Paragraph("Joining Date: " + joiningDate, infoFont));
            document.add(new Paragraph("\n"));

            // Table for Daily Dishes and Prices
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Table Headers
            com.itextpdf.text.Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            table.addCell(new PdfPCell(new Phrase("Sr. No.", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Dish", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Price (Rs.)", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Date", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Time", headerFont)));

            // Table Data
            com.itextpdf.text.Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            for (int i = 0; i < billData.table.size(); i++) {
                String[] row = billData.table.get(i); // Each row contains [Sr. No., Dish Name, Price, Date, Time]
                table.addCell(new PdfPCell(new Phrase(String.valueOf(i + 1), dataFont))); // Sr. No.
                table.addCell(new PdfPCell(new Phrase(row[0], dataFont))); // Dish Name
                table.addCell(new PdfPCell(new Phrase(row[1], dataFont))); // Price
                table.addCell(new PdfPCell(new Phrase(row[2], dataFont))); // Date
                table.addCell(new PdfPCell(new Phrase(row[3], dataFont))); // Time
            }

            // Add Table to Document
            document.add(table);

            // Add Total Amount and Month
            com.itextpdf.text.Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Total Amount: ₹" + billData.totalAmount, totalFont));
            document.add(new Paragraph("Bill Month: " + billData.month, totalFont));

            document.close();
            System.out.println("Bill PDF generated successfully at: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class User {

    private String fullName;
    private String emailId;
    private String mobileNo;
    private boolean isActive;
    private String createdAt;

    // Default Constructor
    public User() {
    }

    // Parameterized Constructor
    public User(String fullName, String emailId, String mobileNo,
            boolean isActive, String createdAt) {

        this.fullName = fullName;
        this.emailId = emailId;
        this.mobileNo = mobileNo;
        this.isActive = isActive;
        this.createdAt = createdAt;

    }

    public User(String fullName, String emailId, String mobileNo, String createdAt) {

        this.fullName = fullName;
        this.emailId = emailId;
        this.mobileNo = mobileNo;
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User [fullName=" + fullName + ", emailId=" + emailId
                + ", mobileNo=" + mobileNo + ", isActive=" + isActive
                + ", createdAt=" + createdAt + "]";
    }

    public void getUser(String email) {
        String query = "SELECT * FROM Users where email_id = ?;";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                this.fullName = rs.getString("full_name");
                this.mobileNo = rs.getString("mobile_no");
                this.emailId = rs.getString("email_id");
                java.sql.Date date = rs.getDate("created_at");
                this.createdAt = date.toString(); // Converts to 'yyyy-MM-dd
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading monthly dishes: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

class BillDataFetcher {

    public static class BillData {
        public List<String[]> table;
        public double totalAmount;
        public String month;

        public BillData(List<String[]> table, double totalAmount, String month) {
            this.table = table;
            this.totalAmount = totalAmount;
            this.month = month;
        }
    }

    public static BillData fetchBillData(String emailId) {
        List<String[]> table = new ArrayList<>();
        double totalAmount = 0;
        String month = "";

        String query = """
                SELECT d.dish_name, d.price, a.marked_at, MONTHNAME(marked_at) AS bill_month
                FROM Attendance a
                JOIN Dishes d ON a.dish_id = d.dish_id
                WHERE a.user_email = ? AND MONTH(a.marked_at) = MONTH(CURRENT_DATE())
                ORDER BY a.marked_at DESC
                """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, emailId);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                String dishName = resultSet.getString("dish_name");
                double dishPrice = resultSet.getDouble("price");
                Timestamp timestamp = resultSet.getTimestamp("marked_at");
                String time = timestamp != null ? timestamp.toLocalDateTime().toLocalTime().toString() : "";

                month = resultSet.getString("bill_month");

                // Add row to table
                table.add(new String[] { dishName, String.valueOf(dishPrice),
                        String.valueOf(timestamp.toLocalDateTime().toLocalDate()), time });
                totalAmount += dishPrice; // Calculate total
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new BillData(table, totalAmount, month);
    }
}