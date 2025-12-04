import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DashboardFrameF extends JFrame {

    // Define custom colors matching the Login/Register Frames
    private Color mainColor = new Color(74, 134, 232); // Soft Blue
    private Color darkGray = new Color(50, 50, 50);   // Dark Nav background
    private Color bgColor = new Color(255, 255, 255); // White for main content
    
    private JPanel mainContentPanel; 
    private String userRole;

    // The constructor now accepts the user's role
    public DashboardFrameF(String role) {
        this.userRole = role; // Store the user role
        
        // 1. Basic Frame Setup
        setTitle("Pet Adoption System - Dashboard (" + role + ")");
        setSize(1200, 700); 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true); 
        setLayout(new BorderLayout()); 

        // 2. Top Header Panel
        setupHeader();

        // 3. Side Navigation Panel (Role-Based)
        setupSideNavigation(role);

        // 4. Main Content Area
        setupMainContentArea();
        
        // Show the initial panel based on role
        if (role.equals("Admin")) {
             showPanel("Home (Admin)");
        } else {
             showPanel("Home");
        }

        setVisible(true);
    }
    
    // --- Setup Methods ---
    
    private void setupHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(mainColor);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60)); 
        headerPanel.setLayout(new BorderLayout());

        JLabel appTitle = new JLabel("  PawPrint Dashboard: " + userRole + " View");
        appTitle.setForeground(Color.WHITE);
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(appTitle, BorderLayout.WEST);

        // Right side of header: Logout and Close
        JPanel headerRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        headerRightPanel.setOpaque(false); 
        
        JButton logoutBtn = new JButton("Logout");
        styleHeaderButton(logoutBtn);
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(DashboardFrameF.this, 
                                 "Are you sure you want to log out?", "Logout", 
                                 JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                // Assumes you have a LoginFrame class
                // You must ensure the LoginFrame class is accessible.
                new LoginFrame().setVisible(true); 
            }
        });
        headerRightPanel.add(logoutBtn);

        JLabel closeLabel = new JLabel("X "); 
        closeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        closeLabel.setForeground(Color.WHITE);
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        headerRightPanel.add(closeLabel);

        headerPanel.add(headerRightPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void setupSideNavigation(String role) {
        JPanel navPanel = new JPanel();
        navPanel.setBackground(darkGray); 
        navPanel.setPreferredSize(new Dimension(220, getHeight()));
        navPanel.setLayout(new GridBagLayout()); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;    
        gbc.insets = new Insets(10, 10, 10, 10);     
        gbc.weightx = 1.0;                          
        gbc.anchor = GridBagConstraints.NORTH; 

        // --- Role-Based Navigation Logic ---
        
        if (role.equalsIgnoreCase("Admin")) {
            navPanel.add(createNavButton("Home (Admin)"), gbc);
            navPanel.add(createNavButton("Manage Pets"), gbc);
            navPanel.add(createNavButton("Manage Users"), gbc);
            navPanel.add(createNavButton("Review Applications"), gbc);
        
        } else if (role.equalsIgnoreCase("Adopter")) {
            navPanel.add(createNavButton("Home"), gbc);
            navPanel.add(createNavButton("Available Pets"), gbc);
            navPanel.add(createNavButton("My Applications"), gbc);
            navPanel.add(createNavButton("Profile"), gbc);
            
        } else { // Generic User (e.g., Staff/Volunteer)
            navPanel.add(createNavButton("Home"), gbc);
            navPanel.add(createNavButton("Available Pets"), gbc);
            navPanel.add(createNavButton("Pet Intake"), gbc); // For adding new animals
        }

        // This pushes all buttons to the top
        gbc.weighty = 1.0; 
        navPanel.add(Box.createVerticalGlue(), gbc);

        add(navPanel, BorderLayout.WEST);
    }

    private void setupMainContentArea() {
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new CardLayout()); 
        mainContentPanel.setBackground(darkGray); 
        add(mainContentPanel, BorderLayout.CENTER);

        // --- Add all content panels needed by any user ---
        
        // Admin Content
        mainContentPanel.add(createContentPanel("Welcome, Administrator! Manage all users and data."), "Home (Admin)");
        mainContentPanel.add(createContentPanel("Manage Pets Panel: View, Add, Edit Animals."), "Manage Pets");
        mainContentPanel.add(createContentPanel("Manage Users Panel: Edit and Deactivate Accounts."), "Manage Users");
        mainContentPanel.add(createContentPanel("Review Applications Panel: Approve or Deny Adoptions."), "Review Applications");
        
        // Adopter/General User Content
        mainContentPanel.add(createContentPanel("Welcome! Find your new best friend."), "Home");
        mainContentPanel.add(createContentPanel("Available Pets Panel: Browse adoptable animals."), "Available Pets");
        mainContentPanel.add(createContentPanel("My Applications Panel: Track your pending requests."), "My Applications");
        mainContentPanel.add(createContentPanel("Profile Panel: Update personal information."), "Profile");
        mainContentPanel.add(createContentPanel("Pet Intake Panel: Add new animals to the system."), "Pet Intake");
    }

    // --- Helper Methods (Styling and Functionality) ---

    // Styles the top header buttons (Logout)
    private void styleHeaderButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false); 
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setForeground(mainColor);
                button.setContentAreaFilled(true);
                button.setBackground(Color.WHITE); 
            }
            public void mouseExited(MouseEvent evt) {
                button.setForeground(Color.WHITE);
                button.setContentAreaFilled(false);
            }
        });
    }

    // Creates and styles navigation buttons, adding card layout functionality
    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(darkGray);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT); 
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        button.addActionListener(e -> showPanel(text));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(80, 80, 80));
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(darkGray);
            }
        });
        return button;
    }

    // Switches the visible panel in the main content area
    private void showPanel(String panelName) {
        CardLayout cl = (CardLayout) (mainContentPanel.getLayout());
        cl.show(mainContentPanel, panelName);
    }

    // Creates simple placeholder panels
    private JPanel createContentPanel(String content) {
        JPanel panel = new JPanel(new GridBagLayout()); 
        panel.setBackground(bgColor);
        JLabel label = new JLabel(content);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        label.setForeground(darkGray);
        panel.add(label);
        return panel;
    }
    
    public static void main(String[] args) {
        // --- Test Scenarios ---
        // new DashboardFrame("Admin");
        // new DashboardFrame("Adopter");
        new DashboardFrameF("User"); 
    }
}