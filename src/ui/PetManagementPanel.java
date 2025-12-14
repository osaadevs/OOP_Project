package ui;


import crud.PetCRUD;
import model.Pet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class PetManagementPanel extends JPanel {
   private JTable table;
   private DefaultTableModel model;
   private JTextField nameField, typeField, breedField, ageField;
   private JButton btnAddUpdate, btnDelete, btnClear;
   private int selectedPetId = -1;


   public PetManagementPanel() {
       setLayout(new BorderLayout());
       setBackground(StyleTheme.BG_COLOR);


       // --- Form Panel ---
       JPanel formPanel = new JPanel(new GridBagLayout());
       formPanel.setBackground(StyleTheme.BG_COLOR);
       formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));


       GridBagConstraints gbc = new GridBagConstraints();
       gbc.insets = new Insets(10, 15, 10, 15);
       gbc.fill = GridBagConstraints.HORIZONTAL;


       JLabel title = new JLabel("Manage Pets");
       title.setFont(StyleTheme.HEADER_FONT);
       title.setForeground(StyleTheme.MAIN_COLOR);
       gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
       formPanel.add(title, gbc);


       gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
       formPanel.add(createLabel("Pet Name:"), gbc);
       gbc.gridx = 1;
       nameField = new JTextField(15);
       StyleTheme.styleField(nameField);
       formPanel.add(nameField, gbc);


       gbc.gridx = 2; formPanel.add(createLabel("Type:"), gbc);
       gbc.gridx = 3;
       typeField = new JTextField(15);
       StyleTheme.styleField(typeField);
       formPanel.add(typeField, gbc);


       gbc.gridy = 2; gbc.gridx = 0;
       formPanel.add(createLabel("Breed:"), gbc);
       gbc.gridx = 1;
       breedField = new JTextField(15);
       StyleTheme.styleField(breedField);
       formPanel.add(breedField, gbc);


       gbc.gridx = 2; formPanel.add(createLabel("Age:"), gbc);
       gbc.gridx = 3;
       ageField = new JTextField(5);
       StyleTheme.styleField(ageField);
       formPanel.add(ageField, gbc);


       // Buttons
       JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
       btnPanel.setBackground(StyleTheme.BG_COLOR);


       btnAddUpdate = new JButton("Add Pet");
       StyleTheme.stylePrimaryButton(btnAddUpdate);
       btnAddUpdate.addActionListener(e -> handleAddOrUpdate());
       btnPanel.add(btnAddUpdate);


       btnClear = new JButton("Clear");
       StyleTheme.styleSecondaryButton(btnClear);
       btnClear.addActionListener(e -> clearForm());
       btnPanel.add(btnClear);


       gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
       gbc.insets = new Insets(20, 15, 10, 15);
       formPanel.add(btnPanel, gbc);
       add(formPanel, BorderLayout.NORTH);


       // --- Table ---
       String[] cols = {"ID", "Name", "Type", "Breed", "Age", "Status"};
       model = new DefaultTableModel(cols, 0) {
           @Override
           public boolean isCellEditable(int row, int col) { return false; }
       };
       table = new JTable(model);
       StyleTheme.styleTable(table);
       table.getSelectionModel().addListSelectionListener(e -> {
           if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) populateForm();
       });


       JScrollPane scrollPane = new JScrollPane(table);
       scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
       scrollPane.getViewport().setBackground(StyleTheme.BG_COLOR);
       add(scrollPane, BorderLayout.CENTER);


       // --- Delete ---
       JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
       bottomPanel.setBackground(StyleTheme.BG_COLOR);
       btnDelete = new JButton("Delete Pet");
       StyleTheme.styleDangerButton(btnDelete);
       btnDelete.setBackground(StyleTheme.DISABLED_GRAY);
       btnDelete.setEnabled(false);
       btnDelete.addActionListener(e -> handleDelete());
       bottomPanel.add(btnDelete);
       add(bottomPanel, BorderLayout.SOUTH);


       loadPets();
   }


   private void populateForm() {
       int row = table.getSelectedRow();
       if (row != -1) {
           selectedPetId = (int) model.getValueAt(row, 0);
           nameField.setText((String) model.getValueAt(row, 1));
           typeField.setText((String) model.getValueAt(row, 2));
           breedField.setText((String) model.getValueAt(row, 3));
           ageField.setText(String.valueOf(model.getValueAt(row, 4)));
           btnAddUpdate.setText("Update Pet");
           btnAddUpdate.setBackground(StyleTheme.SUCCESS_GREEN);
           btnDelete.setEnabled(true);
           btnDelete.setBackground(StyleTheme.ERROR_RED);
       }
   }


   private void handleAddOrUpdate() {
       String name = nameField.getText();
       String type = typeField.getText();
       String breed = breedField.getText();
       String ageText = ageField.getText();
       if (name.isEmpty() || type.isEmpty() || breed.isEmpty() || ageText.isEmpty()) {
           JOptionPane.showMessageDialog(this, "Please fill all fields."); return;
       }
       try {
           int age = Integer.parseInt(ageText);
           if (selectedPetId == -1) {
               if (PetCRUD.addPet(name, type, breed, age, 1)) {
                   JOptionPane.showMessageDialog(this, "Pet Added!"); refreshTable();
               } else JOptionPane.showMessageDialog(this, "Error adding pet.");
           } else {
               if (PetCRUD.updatePet(selectedPetId, name, type, breed, age)) {
                   JOptionPane.showMessageDialog(this, "Pet Updated!"); refreshTable();
               } else JOptionPane.showMessageDialog(this, "Error updating pet.");
           }
       } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Age must be a number."); }
   }


   private void handleDelete() {
       if (selectedPetId == -1) return;
       if (JOptionPane.showConfirmDialog(this, "Delete this pet?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
           if (PetCRUD.deletePet(selectedPetId)) {
               JOptionPane.showMessageDialog(this, "Pet Deleted."); refreshTable();
           }
       }
   }


   private void refreshTable() { loadPets(); clearForm(); }
   private void clearForm() {
       selectedPetId = -1; nameField.setText(""); typeField.setText(""); breedField.setText(""); ageField.setText("");
       table.clearSelection(); btnAddUpdate.setText("Add Pet"); btnAddUpdate.setBackground(StyleTheme.MAIN_COLOR);
       btnDelete.setEnabled(false); btnDelete.setBackground(StyleTheme.DISABLED_GRAY);
   }
   private void loadPets() {
       model.setRowCount(0);
       List<Pet> pets = PetCRUD.getAllPets();
       for (Pet p : pets) model.addRow(new Object[]{p.getId(), p.getName(), p.getType(), p.getBreed(), p.getAge(), p.getStatus()});
   }
   private JLabel createLabel(String text) {
       JLabel l = new JLabel(text); l.setFont(StyleTheme.SUB_FONT); l.setForeground(StyleTheme.DARK_GRAY); return l;
   }
}
