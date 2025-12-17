package ui;

import crud.UserCRUD;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AuthFrame extends JFrame {
    public static final Dimension FRAME_SIZE = new Dimension(900, 600);

    // Panels
    private final JPanel cardPanel;    // Right side (Forms)
    private final CardLayout cardLayout;
    private final JPanel brandPanel;   // Left side (Images)
    private final CardLayout brandLayout;

    // Window Drag Variables
    private int pX, pY;

    public AuthFrame() {
        setTitle("PawPrint Welcome");
        setSize(FRAME_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());

        // --- Window Drag Logic ---
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                pX = me.getX();
                pY = me.getY();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent me) {
                setLocation(getLocation().x + me.getX() - pX, getLocation().y + me.getY() - pY);
            }
        });

        // --- Left Side: Images ---
        brandLayout = new CardLayout();
        brandPanel = new JPanel(brandLayout);
        brandPanel.setPreferredSize(new Dimension(350, 600));

        // Load Images (Ensure images are in your 'src' folder)
        JPanel loginImgPanel = new JPanel(new BorderLayout());
        loginImgPanel.setBackground(StyleTheme.MAIN_COLOR);
        loginImgPanel.add(createImageLabel("/resources/login cat.jpeg"));
        brandPanel.add(loginImgPanel, "IMG_LOGIN");

        JPanel regImgPanel = new JPanel(new BorderLayout());
        regImgPanel.setBackground(StyleTheme.MAIN_COLOR);
        regImgPanel.add(createImageLabel("/resources/RegisterDog.png"));
        brandPanel.add(regImgPanel, "IMG_REGISTER");

        // --- Right Side: Forms ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(StyleTheme.BG_COLOR);

        cardPanel.add(createLoginPanel(), "LOGIN");
        cardPanel.add(createRegisterPanel(), "REGISTER");

        add(brandPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        // Show Defaults
        cardLayout.show(cardPanel, "LOGIN");
        brandLayout.show(brandPanel, "IMG_LOGIN");
    }

    private JPanel createLoginPanel() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(StyleTheme.BG_COLOR);

        // Close Button (Pinned to TOP RIGHT)
        JPanel header = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        header.setBackground(StyleTheme.BG_COLOR);
        header.add(createCloseButton());
        mainPanel.add(header, BorderLayout.NORTH);

        // The Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(StyleTheme.BG_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 40, 10, 40);
        gbc.fill = GridBagConstraints.HORIZONTAL; //  Stretch to fill width
        gbc.weightx = 1.0; //  Use all available horizontal space

        // Title
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblTitle = new JLabel("Welcome Back");
        lblTitle.setFont(StyleTheme.HEADER_FONT);
        lblTitle.setForeground(StyleTheme.MAIN_COLOR);
        formPanel.add(lblTitle, gbc);

        // Username
        gbc.gridy = 1;
        formPanel.add(createLabel("Username"), gbc);

        gbc.gridy = 2;
        JTextField userField = new JTextField();
        StyleTheme.styleField(userField);
        formPanel.add(userField, gbc);

        // Password
        gbc.gridy = 3;
        formPanel.add(createLabel("Password"), gbc);

        gbc.gridy = 4;
        JPasswordField passField = new JPasswordField();
        StyleTheme.styleField(passField);
        formPanel.add(passField, gbc);

        // Login Button
        gbc.gridy = 5;
        gbc.insets = new Insets(30, 40, 10, 40);
        JButton loginBtn = new JButton("LOGIN");
        StyleTheme.stylePrimaryButton(loginBtn);

        loginBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            if(user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter all fields.");
                return;
            }
            User loggedInUser = UserCRUD.login(user, pass);
            if (loggedInUser != null) openDashboard(loggedInUser);
            else JOptionPane.showMessageDialog(this, "Invalid credentials.");
        });
        formPanel.add(loginBtn, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        //  Sign Up Link
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(StyleTheme.BG_COLOR);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton linkBtn = createLinkButton("Don't have an account? Sign Up");
        linkBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, "REGISTER");
            brandLayout.show(brandPanel, "IMG_REGISTER");
        });
        footer.add(linkBtn);

        mainPanel.add(footer, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createRegisterPanel() {
        // Same layout as Login
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(StyleTheme.BG_COLOR);

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        header.setBackground(StyleTheme.BG_COLOR);
        header.add(createCloseButton());
        mainPanel.add(header, BorderLayout.NORTH);

        // Center Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(StyleTheme.BG_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 40, 5, 40); // Tighter vertical, wide horizontal
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Title
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblTitle = new JLabel("Create Account");
        lblTitle.setFont(StyleTheme.HEADER_FONT);
        lblTitle.setForeground(StyleTheme.MAIN_COLOR);
        formPanel.add(lblTitle, gbc);

        // 1. Username
        gbc.gridy = 1; formPanel.add(createLabel("Username"), gbc);
        gbc.gridy = 2;
        JTextField userField = new JTextField();
        StyleTheme.styleField(userField);
        formPanel.add(userField, gbc);

        // 2. User Full Name
        gbc.gridy = 3; formPanel.add(createLabel("Full Name"), gbc);
        gbc.gridy = 4;
        JTextField nameField = new JTextField();
        StyleTheme.styleField(nameField);
        formPanel.add(nameField, gbc);

        // 2. Contact Number
        gbc.gridy = 5; formPanel.add(createLabel("Contact No:"), gbc);
        gbc.gridy = 6;
        JTextField contactField = new JTextField();
        StyleTheme.styleField(contactField);
        formPanel.add(contactField, gbc);

        // 3. Password
        gbc.gridy = 7; formPanel.add(createLabel("Password"), gbc);
        gbc.gridy = 8;
        JPasswordField passField = new JPasswordField();
        StyleTheme.styleField(passField);
        formPanel.add(passField, gbc);

        // 4. Role Selection
        gbc.gridy = 9; formPanel.add(createLabel("I am a:"), gbc);
        gbc.gridy = 10;
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Adopter", "ShelterWorker"});
        roleBox.setBackground(Color.WHITE); // Standard White background
        roleBox.setFont(StyleTheme.FIELD_FONT);
        // Note: No StyleTheme.styleComboBox(roleBox) here, keeping traditional look
        formPanel.add(roleBox, gbc);

        // 5. Register Button
        gbc.gridy = 11; gbc.insets = new Insets(20, 40, 10, 40);
        JButton regBtn = new JButton("REGISTER & LOGIN");
        StyleTheme.stylePrimaryButton(regBtn);

        regBtn.addActionListener(e -> {
            String u = userField.getText().trim();
            String fn = nameField.getText().trim();
            String pwd = new String(passField.getPassword()).trim();
            String contact = contactField.getText().trim();
            String r = (String) roleBox.getSelectedItem();

            if(u.isEmpty() || fn.isEmpty() || pwd.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
                return;
            }

            // Contact Validation
            if (!contact.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Contact number must be exactly 10 digits.");
                return;
            }

            // Password Validation
            if (pwd.length() < 8) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long.");
                return;
            }
            if (!pwd.matches(".*[A-Z].*") && !pwd.matches(".*\\d.*")) {
                JOptionPane.showMessageDialog(this, "Password must contain at least one Capital Letter OR a Number.");
                return;
            }
            User user = UserCRUD.createUser(u, fn, pwd, r, contact);

            if(user != null) {
                JOptionPane.showMessageDialog(this, "Welcome to PawPrint!");
                openDashboard(user);
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed. Username taken.");
            }
        });
        formPanel.add(regBtn, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(StyleTheme.BG_COLOR);
        footer.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton linkBtn = createLinkButton("Already have an account? Login");
        linkBtn.addActionListener(e -> {
            cardLayout.show(cardPanel, "LOGIN");
            brandLayout.show(brandPanel, "IMG_LOGIN");
        });
        footer.add(linkBtn);

        mainPanel.add(footer, BorderLayout.SOUTH);

        return mainPanel;
    }

    // --- Helper Methods ---
    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(StyleTheme.SUB_FONT);
        l.setForeground(StyleTheme.HINT_COLOR);
        return l;
    }

    private JLabel createCloseButton() {
        JLabel close = new JLabel("X");
        StyleTheme.styleCloseLabel(close);
        close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        return close;
    }

    private JButton createLinkButton(String text) {
        JButton btn = new JButton(text);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFont(StyleTheme.SUB_FONT);
        btn.setForeground(StyleTheme.HINT_COLOR);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JLabel createImageLabel(String path) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage();
                Image resized = img.getScaledInstance(400, 600, Image.SCALE_SMOOTH);
                return new JLabel(new ImageIcon(resized));
            } else {
                return new JLabel("<html><div style='text-align:center; color:white;'>Image Missing<br>" + path + "</div></html>", SwingConstants.CENTER);
            }
        } catch (Exception e) {
            return new JLabel("Error");
        }
    }

    private void openDashboard(User user) {
        new DashboardFrame(user).setVisible(true);
        this.dispose();
    }
}