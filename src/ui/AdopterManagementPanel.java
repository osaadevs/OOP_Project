package ui;


import crud.UserCRUD;
import model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class AdopterManagementPanel extends JPanel {
   private JTable table;
   private DefaultTableModel model;


   private JTextField usernameField, contactField, passwordField;
   private JButton btnAddUpdate, btnDelete, btnClear;
   private int selectedUserId = -1;


   public AdopterManagementPanel() {
       setLayout(new BorderLayout());
       setBackground(StyleTheme.BG_COLOR);


       // --- 1. Form Panel ---
       JPanel formPanel = new JPanel(new GridBagLayout());
       formPanel.setBackground(StyleTheme.BG_COLOR);
       formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));


       GridBagConstraints gbc = new GridBagConstraints();
       gbc.insets = new Insets(10, 15, 10, 15);
       gbc.fill = GridBagConstraints.HORIZONTAL;


       JLabel title = new JLabel("Manage Adopters");
       title.setFont(StyleTheme.HEADER_FONT);
       title.setForeground(StyleTheme.MAIN_COLOR);
       gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
       formPanel.add(title, gbc);


       // Row 1
       gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
       formPanel.add(createLabel("Username:"), gbc);
       gbc.gridx = 1;
       usernameField = new JTextField(15);
       StyleTheme.styleField(usernameField);
       formPanel.add(usernameField, gbc);


       gbc.gridx = 2;
       formPanel.add(createLabel("Contact No:"), gbc);
       gbc.gridx = 3;
       contactField = new JTextField(15);
       StyleTheme.styleField(contactField);
       formPanel.add(contactField, gbc);


       // Row 2
       gbc.gridy = 2; gbc.gridx = 0;
       formPanel.add(createLabel("Set Password:"), gbc);
       gbc.gridx = 1;
       passwordField = new JTextField(15);
       StyleTheme.styleField(passwordField);
       formPanel.add(passwordField, gbc);


       // Buttons
       JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
       btnPanel.setBackground(StyleTheme.BG_COLOR);


       btnAddUpdate = new JButton("Add Adopter");
       StyleTheme.stylePrimaryButton(btnAddUpdate);
       btnAddUpdate.addActionListener(e -> handleAddOrUpdate());
       btnPanel.add(btnAddUpdate);


       btnClear = new JButton("Clear");
       StyleTheme.styleSecondaryButton(btnClear);
       btnClear.addActionListener(e -> clearForm());
       btnPanel.add(btnClear);


       gbc.gridx = 2; gbc.gridy = 2; gbc.gridwidth = 2;
       formPanel.add(btnPanel, gbc);


       add(formPanel, BorderLayout.NORTH);


       // --- 2. Table ---
       String[] cols = {"ID", "Username", "Contact"};
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


       // --- 3. Delete ---
       JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
       bottomPanel.setBackground(StyleTheme.BG_COLOR);


       btnDelete = new JButton("Delete Adopter");
       StyleTheme.styleDangerButton(btnDelete);
       btnDelete.setBackground(StyleTheme.DISABLED_GRAY);
       btnDelete.setEnabled(false);
       btnDelete.addActionListener(e -> handleDelete());
       bottomPanel.add(btnDelete);


       add(bottomPanel, BorderLayout.SOUTH);


       loadData();
   }


   private void populateForm() {
       int row = table.getSelectedRow();
       if (row != -1) {
           selectedUserId = (int) model.getValueAt(row, 0);
           usernameField.setText((String) model.getValueAt(row, 1));
           contactField.setText((String) model.getValueAt(row, 2));


           passwordField.setText(""); passwordField.setEnabled(false);


           btnAddUpdate.setText("Update Adopter");
           btnAddUpdate.setBackground(StyleTheme.SUCCESS_GREEN);


           btnDelete.setEnabled(true);
           btnDelete.setBackground(StyleTheme.ERROR_RED);
       }
   }


   private void handleAddOrUpdate() {
       String user = usernameField.getText();
       String contact = contactField.getText();


       if(user.isEmpty() || contact.isEmpty()) {
           JOptionPane.showMessageDialog(this, "Please fill in Username and Contact.");
           return;
       }


       if (selectedUserId == -1) {
           // ADD MODE
           String pass = passwordField.getText();
           if(pass.isEmpty()) {
               JOptionPane.showMessageDialog(this, "Password is required."); return;
           }
           if (UserCRUD.createUser(user, pass, "Adopter", contact) != null) {
               JOptionPane.showMessageDialog(this, "Adopter Added!"); refreshTable();
           } else {
               JOptionPane.showMessageDialog(this, "Error adding user.");
           }
       } else {
           // UPDATE MODE
           if (UserCRUD.updateUser(selectedUserId, user, contact)) {
               JOptionPane.showMessageDialog(this, "Details Updated!"); refreshTable();
           } else {
               JOptionPane.showMessageDialog(this, "Update Failed.");
           }
       }
   }


   private void handleDelete() {
       if (selectedUserId == -1) return;
       if (JOptionPane.showConfirmDialog(this, "Delete this Adopter?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
           if (UserCRUD.deleteUser(selectedUserId)) {
               refreshTable();
           }
       }
   }


   private void refreshTable() { loadData(); clearForm(); }


   private void clearForm() {
       selectedUserId = -1;
       usernameField.setText(""); contactField.setText("");
       passwordField.setText(""); passwordField.setEnabled(true);
       table.clearSelection();
       btnAddUpdate.setText("Add Adopter");
       btnAddUpdate.setBackground(StyleTheme.MAIN_COLOR);
       btnDelete.setEnabled(false);
       btnDelete.setBackground(StyleTheme.DISABLED_GRAY);
   }


   private void loadData() {
       model.setRowCount(0);
       List<User> list = UserCRUD.getUsersByRole("Adopter");
       for (User u : list) {
           model.addRow(new Object[]{u.getId(), u.getUsername(), u.getContact()});
       }
   }


   private JLabel createLabel(String text) {
       JLabel l = new JLabel(text);
       l.setFont(StyleTheme.SUB_FONT);
       l.setForeground(StyleTheme.DARK_GRAY);
       return l;
   }
}
