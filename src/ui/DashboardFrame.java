package ui;


import model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class DashboardFrame extends JFrame {
    public JPanel contentPanel;
    private final User currentUser;
    private int pX, pY; // For dragging the window


    public DashboardFrame(User user) {
        this.currentUser = user;
        setSize(1200, 700);
        setTitle("PawPrint Dashboard - " + user.getRole());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());


        // Window Drag Logic
        JPanel header = setupHeader();
        header.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                pX = me.getX();
                pY = me.getY();
            }
        });
        header.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent me) {
                setLocation(getLocation().x + me.getX() - pX, getLocation().y + me.getY() - pY);
            }
        });
        add(header, BorderLayout.NORTH);


        // Sidebar
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(StyleTheme.DARK_GRAY);
        sidebar.setPreferredSize(new Dimension(240, 600));


        // Sidebar Navigation Container
        JPanel navSection = new JPanel(new GridLayout(0, 1, 0, 10)); // Stack buttons with gaps
        navSection.setBackground(StyleTheme.DARK_GRAY);
        navSection.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));


        addRoleButtons(navSection);
        sidebar.add(navSection, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);


        // Main Content Area
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(StyleTheme.BG_COLOR);


        JLabel welcome = new JLabel("Welcome, " + user.getUsername(), SwingConstants.CENTER);
        welcome.setFont(StyleTheme.HEADER_FONT);
        welcome.setForeground(StyleTheme.DARK_GRAY);
        contentPanel.add(welcome, BorderLayout.CENTER);


        add(contentPanel, BorderLayout.CENTER);
    }


    private JPanel setupHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(StyleTheme.MAIN_COLOR);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));


        // Title
        JLabel title = new JLabel("  PawPrint Dashboard: " + currentUser.getRole());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerPanel.add(title, BorderLayout.WEST);


        // Right Side Controls
        JPanel controls = new JPanel(new GridBagLayout());
        controls.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.CENTER;


        // Logout Button
        JButton logoutBtn = new JButton("Logout");
        StyleTheme.styleHeaderLogoutButton(logoutBtn);
        gbc.insets = new Insets(0, 0, 0, 25);


        logoutBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if(choice == JOptionPane.YES_OPTION) {
                new AuthFrame().setVisible(true); // Return to Login
                this.dispose();
            }
        });
        controls.add(logoutBtn, gbc);


        // Close 'X' Button
        JLabel closeLabel = new JLabel("X");
        StyleTheme.styleCloseLabel(closeLabel);
        closeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        closeLabel.setForeground(Color.WHITE);


        closeLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { System.exit(0); }
            public void mouseEntered(MouseEvent e) { closeLabel.setForeground(Color.RED); }
            public void mouseExited(MouseEvent e) { closeLabel.setForeground(Color.WHITE); }
        });


        gbc.insets = new Insets(0, 0, 0, 0);
        controls.add(closeLabel, gbc);


        headerPanel.add(controls, BorderLayout.EAST);
        return headerPanel;
    }


    private void addRoleButtons(JPanel sidebar) {
        String role = currentUser.getRole();

        if (role.equalsIgnoreCase("Admin")) {
            // Member 4's Work
            JButton btnStaff = createNavButton("Manage Staff");
            btnStaff.addActionListener(e -> loadPanel(new WorkerManagementPanel()));
            sidebar.add(btnStaff);


            JButton btnAdopter = createNavButton("Manage Adopters");
            btnAdopter.addActionListener(e -> loadPanel(new AdopterManagementPanel()));
            sidebar.add(btnAdopter);


            // Member 3's Work
            JButton btnPets = createNavButton("Manage Pets");
            btnPets.addActionListener(e -> loadPanel(new PetManagementPanel()));
            sidebar.add(btnPets);


            // Member 5's Work (Review Side)
            JButton btnApps = createNavButton("Adoption Requests");
            btnApps.addActionListener(e -> loadPanel(new ApplicationPanel(1)));
            sidebar.add(btnApps);


            JButton btnRehome = createNavButton("Rehome Requests");
            btnRehome.addActionListener(e -> loadPanel(new RehomeReviewPanel()));
            sidebar.add(btnRehome);
        }
        else if (role.equalsIgnoreCase("ShelterWorker")) {
            // Workers have a subset of Admin features
            JButton btnPets = createNavButton("Manage Pets");
            btnPets.addActionListener(e -> loadPanel(new PetManagementPanel()));
            sidebar.add(btnPets);


            JButton btnApps = createNavButton("Adoption Requests");
            btnApps.addActionListener(e -> loadPanel(new ApplicationPanel(1)));
            sidebar.add(btnApps);


            JButton btnRehome = createNavButton("Rehome Requests");
            btnRehome.addActionListener(e -> loadPanel(new RehomeReviewPanel()));
            sidebar.add(btnRehome);
        }
        else {
            // Member 5's Work (Adopter Side)
            JButton btnFind = createNavButton("Find a Pet");
            // Pass UserID and Dummy Shelter ID (1)
            btnFind.addActionListener(e -> loadPanel(new AdopterPanel(currentUser.getId(), 1)));
            sidebar.add(btnFind);


            JButton btnMyApps = createNavButton("My Applications");
            btnMyApps.addActionListener(e -> loadPanel(new AdopterStatusPanel(currentUser.getId())));
            sidebar.add(btnMyApps);


            JButton btnRehome = createNavButton("Rehome My Pet");
            btnRehome.addActionListener(e -> loadPanel(new RehomePanel(currentUser.getId())));
            sidebar.add(btnRehome);
        }
    }



    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        StyleTheme.styleNavButton(btn);
        return btn;
    }


    // Method going to be used to swap panels
    public void loadPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
