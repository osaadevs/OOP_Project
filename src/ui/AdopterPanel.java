package ui;

import crud.PetCRUD;
import crud.ApplicationCRUD;
import model.Pet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdopterPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private int currentUserId;

    public AdopterPanel(int userId, int shelterId) { 
        this.currentUserId = userId;
        setLayout(new BorderLayout());
        setBackground(StyleTheme.BG_COLOR);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        header.setBackground(StyleTheme.BG_COLOR);
        JLabel title = new JLabel("Find Your Furry Friend");
        title.setFont(StyleTheme.HEADER_FONT);
        title.setForeground(StyleTheme.MAIN_COLOR);
        header.add(title);
        add(header, BorderLayout.NORTH);

        String[] cols = {"ID", "Name", "Type", "Breed", "Age", "Status"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        StyleTheme.styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        scrollPane.getViewport().setBackground(StyleTheme.BG_COLOR);
        add(scrollPane, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        btnPanel.setBackground(StyleTheme.BG_COLOR);
        JButton adoptBtn = new JButton("Request to Adopt");
        StyleTheme.stylePrimaryButton(adoptBtn);

        adoptBtn.addActionListener(e -> handleAdoptionRequest());
        btnPanel.add(adoptBtn);
        add(btnPanel, BorderLayout.SOUTH);

        loadAvailablePets();
    }

    private void handleAdoptionRequest() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a pet."); return; }

        int petId = (int) model.getValueAt(row, 0);
        String petName = (String) model.getValueAt(row, 1);

        int confirm = JOptionPane.showConfirmDialog(this, "Submit application for " + petName + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // 1. Submit the app
            if (ApplicationCRUD.submitApp(currentUserId, petId)) {
                JOptionPane.showMessageDialog(this, "Application Submitted!");
                
                // [CRITICAL FIX HERE] 
                // You must reload the data so the table updates and removes the pet!
                loadAvailablePets(); 
                
            } else {
                JOptionPane.showMessageDialog(this, "You have already applied for this pet.");
            }
        }
    }

    private void loadAvailablePets() {
        model.setRowCount(0);
        
        // If you created the new method in PetCRUD, use PetCRUD.getAvailablePets()
        // If not, this existing logic works fine too because of the 'if' check below
        List<Pet> pets = PetCRUD.getAllPets(); 
        
        for (Pet p : pets) {
            // This check ensures that if the DB status changed to 'Pending',
            // it will NOT be added to the table here.
            if ("Available".equalsIgnoreCase(p.getStatus())) {
                model.addRow(new Object[]{p.getId(), p.getName(), p.getType(), p.getBreed(), p.getAge(), p.getStatus()});
            }
        }
    }
}