import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// Import the centralized theme class
import PetAdoptionTheme; 

public class LoginFrame extends JFrame {

    // Removed repeated color definitions. We will use PetAdoptionTheme instead.

    public LoginFrame() {
        // 1. Basic Frame Setup
        setTitle("Pet Adoption System - Login");
        setSize(800, 500);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true); // Removes the default Title Bar for a modern look
        setLayout(null); // Using Absolute layout for precise design control
        
        // --- Left Panel Content (Branding and Image) ---
        
        // **Note:** Your image loading assumes the image is in the classpath.
        // If Logo 2.png is in the same directory as this class, this is correct.

        JLabel titleLabel = new JLabel("PawPrint");
        titleLabel.setForeground(PetAdoptionTheme.PRIMARY_BLUE);
        titleLabel.setFont(PetAdoptionTheme.FONT_HEADER); // Using FONT_HEADER (24pt)
        titleLabel.setBounds(60, 100, 250, 50);
        add(titleLabel);

        JLabel sloganLabel = new JLabel("Find your furry friend today");
        sloganLabel.setForeground(PetAdoptionTheme.TEXT_DARK); // Used TEXT_DARK (black)
        sloganLabel.setFont(PetAdoptionTheme.FONT_SUBTITLE); // Using FONT_SUBTITLE (18pt)
        sloganLabel.setBounds(65, 150, 250, 30);
        add(sloganLabel);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/Logo 2.png"));
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(350, 500, Image.SCALE_SMOOTH);
        
        JLabel label = new JLabel(new ImageIcon(resized));
        label.setBounds(0,0,350,500);
        add(label);
        
        
     // 3. Right Panel (Login Form)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(PetAdoptionTheme.BACKGROUND_WHITE); // Using theme color
        rightPanel.setBounds(350, 0, 450, 500);
        rightPanel.setLayout(null);
        

        // Close Button 'X' (Since we removed the title bar)
        JLabel closeLabel = new JLabel("X");
        closeLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Arial is fine here
        closeLabel.setForeground(PetAdoptionTheme.PRIMARY_BLUE); // Using theme color
        closeLabel.setBounds(410, 10, 30, 30);
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        rightPanel.add(closeLabel);

        // "Sign Up" Heading
        JLabel loginText = new JLabel("Welcome Back");
        loginText.setFont(PetAdoptionTheme.FONT_TITLE); // Using FONT_TITLE
        loginText.setForeground(PetAdoptionTheme.PRIMARY_BLUE);
        loginText.setBounds(40, 60, 300, 50);
        rightPanel.add(loginText);
        
        JLabel paw = new JLabel("🐾");
        paw.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32)); // Emoji font is specific
        paw.setBounds(285, 65, 300, 50);
        paw.setForeground(PetAdoptionTheme.PRIMARY_BLUE);
        rightPanel.add(paw);


        // Username Label & Field
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(PetAdoptionTheme.FONT_BODY); // Using FONT_BODY
        userLabel.setForeground(PetAdoptionTheme.TEXT_HINT_GRAY);
        userLabel.setBounds(40, 140, 100, 30);
        rightPanel.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(40, 170, 350, 40);
        userField.setFont(PetAdoptionTheme.FONT_BODY); 
        // Remove default border and add a bottom line only
        userField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PetAdoptionTheme.PRIMARY_BLUE));
        userField.setBackground(PetAdoptionTheme.BACKGROUND_WHITE);
        rightPanel.add(userField);

        // Password Label & Field
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(PetAdoptionTheme.FONT_BODY);
        passLabel.setForeground(PetAdoptionTheme.TEXT_HINT_GRAY);
        passLabel.setBounds(40, 230, 100, 30);
        rightPanel.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(40, 260, 350, 40);
        passField.setFont(PetAdoptionTheme.FONT_BODY);
        passField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PetAdoptionTheme.PRIMARY_BLUE));
        passField.setBackground(PetAdoptionTheme.BACKGROUND_WHITE);
        rightPanel.add(passField);

        // Login Button
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(40, 340, 120, 45);
        loginBtn.setBackground(PetAdoptionTheme.PRIMARY_BLUE);
        loginBtn.setForeground(PetAdoptionTheme.TEXT_WHITE);
        loginBtn.setFont(PetAdoptionTheme.FONT_BUTTON);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        loginBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                // Hover color is now defined locally but you could add a DARKER_BLUE constant 
                // to PetAdoptionTheme if you use it in many places.
                loginBtn.setBackground(new Color(50, 100, 200)); 
            }
            public void mouseExited(MouseEvent evt) {
                loginBtn.setBackground(PetAdoptionTheme.PRIMARY_BLUE);
            }
        });
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ... (Auth/Navigation Logic Remains the Same) ...
                String username = userField.getText();
                char[] password = passField.getPassword();
                
                // --- SIMULATED SUCCESS ---
                if (username.equals("admin") && new String(password).equals("123")) {
                    String userRole = "Admin"; 
                    dispose(); 
                    // NOTE: DashboardFrameF should be renamed to DashboardFrame 
                    // to match the class name used in the previous examples.
                    new DashboardFrameF(userRole).setVisible(true); 
                    
                } else if (username.equals("adopter") && new String(password).equals("123")) {
                    String userRole = "Adopter";
                    dispose(); 
                    new DashboardFrameF(userRole).setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, 
                                                  "Invalid Username or Password.", 
                                                  "Login Error", 
                                                  JOptionPane.ERROR_MESSAGE);
                }
                
                passField.setText("");
            }
        });
        rightPanel.add(loginBtn);

        // Link to Register Frame
        JLabel registerLink = new JLabel("Don't have an account? Sign Up");
        registerLink.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        registerLink.setForeground(PetAdoptionTheme.TEXT_HINT_GRAY);
        registerLink.setBounds(40, 400, 250, 30);
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        registerLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); 
                // NOTE: RegistationFrame should be renamed to RegisterFrame 
                // to match the class name used in the previous examples.
                new RegistationFrame().setVisible(true); 
            }
        });
        rightPanel.add(registerLink);

        add(rightPanel);
        setVisible(true);
    }
    

    public static void main(String[] args) {
        new LoginFrame();
    }
}