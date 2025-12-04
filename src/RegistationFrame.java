import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistationFrame extends JFrame {

    // Define custom colors matching the Login Frame
    private Color mainColor = new Color(74, 134, 232); // Soft Blue
    private Color bgColor = new Color(255, 255, 255); // White
    private Color grayColor = new Color(150, 150, 150); // Gray for text hints

    public RegistationFrame() {
        // 1. Basic Frame Setup
        setTitle("Pet Adoption System - Registation");
        setSize(800, 550); // Slightly larger to accommodate more fields
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true); // Modern look
        setLayout(null);

        JLabel titleLabel = new JLabel("Join Our Family");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setBounds(60, 100, 250, 50);
        add(titleLabel);

        JLabel sloganLabel = new JLabel("Start your adoption journey today.");
        sloganLabel.setForeground(new Color(220, 220, 220));
        sloganLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        sloganLabel.setBounds(65, 150, 250, 30);
        add(sloganLabel);
        
       

        // 3. Right Panel (Registration Form)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(bgColor);
        rightPanel.setBounds(350, 0, 450, 550);
        rightPanel.setLayout(null);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/RegisterDog.png"));
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(350, 580, Image.SCALE_SMOOTH);
        
        JLabel label = new JLabel(new ImageIcon(resized));
        label.setBounds(0,0,350,550);
        add(label);

        // Close Button 'X'
        JLabel closeLabel = new JLabel("X");
        closeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        closeLabel.setForeground(mainColor);
        closeLabel.setBounds(410, 10, 30, 30);
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        rightPanel.add(closeLabel);

        // "Create Account" Heading
        JLabel RegistationText = new JLabel("Create Account");
        RegistationText.setFont(new Font("Segoe UI", Font.BOLD, 30));
        RegistationText.setForeground(mainColor);
        RegistationText.setBounds(40, 50, 300, 50);
        rightPanel.add(RegistationText);

        // --- Input Fields ---

        int yStart = 120;
        int yGap = 70; // Vertical spacing between fields

        // 1. Username Field
        addInput(rightPanel, "Username", 40, yStart);
        
        // 2. Email Field
        addInput(rightPanel, "Email Address", 40, yStart + yGap);

        // 3. Password Field
        addPasswordInput(rightPanel, "Password", 40, yStart + 2 * yGap);

        String[] roles = {"Adopter", "Admin", "User"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(40, yStart + 3 * yGap + 25, 390, 40);
        roleComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        // Customizing JComboBox for flat design is trickier. 
        // We'll remove the border and set a background color.
        roleComboBox.setBackground(new Color(245, 245, 245)); // Light background
        roleComboBox.setBorder(BorderFactory.createLineBorder(mainColor, 1)); // Thin border
        
        rightPanel.add(roleComboBox);

        // Registation Button
        JButton RegistationBtn = new JButton("Registation");
        RegistationBtn.setBounds(40, yStart + 4 * yGap + 30, 150, 45);
        RegistationBtn.setBackground(mainColor);
        RegistationBtn.setForeground(Color.WHITE);
        RegistationBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        RegistationBtn.setFocusPainted(false);
        RegistationBtn.setBorderPainted(false);
        RegistationBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rightPanel.add(RegistationBtn);

        // Link back to Login Frame
        JLabel loginLink = new JLabel("Already have an account? Login");
        loginLink.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginLink.setForeground(grayColor);
        loginLink.setBounds(40, yStart + 5 * yGap + 30, 250, 30);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Logic to open Login frame would go here
        
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 1. Hide the current RegisterFrame
                dispose(); 
                
                // 2. Create and show the new LoginFrame
                // NOTE: You must ensure the LoginFrame class exists in your project
                new LoginFrame().setVisible(true); 
            }
        });
        rightPanel.add(loginLink);

        add(rightPanel);
        setVisible(true);
    }

    // Helper method to create standard JTextFields
    private void addInput(JPanel panel, String labelText, int x, int y) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(grayColor);
        label.setBounds(x, y, 150, 30);
        panel.add(label);

        JTextField field = new JTextField();
        field.setBounds(x, y + 25, 390, 40);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, mainColor)); // Matte Border
        panel.add(field);
    }

    // Helper method to create JPasswordFields
    private void addPasswordInput(JPanel panel, String labelText, int x, int y) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(grayColor);
        label.setBounds(x, y, 150, 30);
        panel.add(label);

        JPasswordField field = new JPasswordField();
        field.setBounds(x, y + 25, 390, 40);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, mainColor)); // Matte Border
        panel.add(field);
    }

    public static void main(String[] args) {
        new RegistationFrame();
    }
}