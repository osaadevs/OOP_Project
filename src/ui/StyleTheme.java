package ui;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StyleTheme {

    // --- COLORS  ---
    public static final Color MAIN_COLOR = new Color(74, 134, 232); // Soft Blue
    public static final Color DARK_GRAY  = new Color(50, 50, 50);   // Sidebar
    public static final Color BG_COLOR   = new Color(255, 255, 255); // White
    public static final Color HINT_COLOR = new Color(150, 150, 150); // Gray Text
    public static final Color ERROR_RED  = new Color(231, 76, 60);
    public static final Color SUCCESS_GREEN = new Color(46, 204, 113);
    public static final Color DISABLED_GRAY = new Color(200, 200, 200);

    // --- FONTS  ---
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font SUB_FONT    = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FIELD_FONT  = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font BTN_FONT    = new Font("Segoe UI", Font.BOLD, 14);


    public static void styleField(JTextField field) {
        field.setFont(FIELD_FONT);
        field.setBackground(BG_COLOR);
        field.setForeground(Color.BLACK);
        // Create the bottom-only border
        field.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 1, 0, MAIN_COLOR), // Bottom line
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Internal padding
        ));
    }

    // Action button
    public static void stylePrimaryButton(JButton btn) {
        btn.setBackground(MAIN_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setFont(BTN_FONT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btn.setOpaque(true);

        // Hover Effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                // If the button is currently Green (Success mode), darken the green
                if (btn.getBackground().equals(SUCCESS_GREEN)) {
                    btn.setBackground(new Color(39, 174, 96));
                } else {
                    btn.setBackground(new Color(50, 100, 200));
                }
            }
            public void mouseExited(MouseEvent e) {
                // Revert to original color based on text
                if (btn.getText().contains("Update")) {
                    btn.setBackground(SUCCESS_GREEN);
                } else {
                    btn.setBackground(MAIN_COLOR);
                }
            }
        });
    }

    //clear cancel button
    public static void styleSecondaryButton(JButton btn) {
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setForeground(Color.BLACK);
        btn.setFont(BTN_FONT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);


        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(200, 200, 200));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.LIGHT_GRAY);
            }
        });
    }

    // Navbar button
    public static void styleNavButton(JButton btn) {
        btn.setBackground(DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 10));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effect
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(80, 80, 80)); // Lighter Gray
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(DARK_GRAY);
            }
        });
    }


    // Close button
    public static void styleCloseLabel(JLabel lbl) {
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        lbl.setForeground(MAIN_COLOR);
        lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));


        lbl.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 20));


        lbl.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                lbl.setForeground(Color.RED);
            }
            public void mouseExited(MouseEvent e) {
                lbl.setForeground(MAIN_COLOR);
            }
        });

    }
    // Logout Button
    public static void styleHeaderLogoutButton(JButton btn) {
        btn.setForeground(Color.WHITE);
        btn.setBackground(MAIN_COLOR);
        btn.setFont(BTN_FONT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        // White Border
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btn.setPreferredSize(new Dimension(80, 30));

        // Hover Effect: Turn White with Blue Text
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(MAIN_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(MAIN_COLOR);
                btn.setForeground(Color.WHITE);
            }
        });
    }

    // Table styling
    public static void styleTable(JTable table) {
        // 1. Row Styling
        table.setRowHeight(35);
        table.setFont(FIELD_FONT);
        table.setGridColor(new Color(230, 230, 230));
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(232, 240, 254)); // Light Blue selection
        table.setSelectionForeground(Color.BLACK);

        // 2. Header Styling (Remove 3D look)
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setFont(BTN_FONT);
        header.setBackground(MAIN_COLOR);
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setPreferredSize(new Dimension(0, 40)); // Taller header
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, MAIN_COLOR));

        // 3. Center Align Cells
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<table.getColumnCount(); i++){
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public static void styleDangerButton(JButton btn) {
        btn.setBackground(ERROR_RED);
        btn.setForeground(Color.WHITE);
        btn.setFont(BTN_FONT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setOpaque(true);

        // Hover Effect: Darker Red
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled()) {
                    btn.setBackground(new Color(200, 60, 50)); // Darker Red
                }
            }
            public void mouseExited(MouseEvent e) {
                if (btn.isEnabled()) {
                    btn.setBackground(ERROR_RED);
                }
            }
        });
    }

}