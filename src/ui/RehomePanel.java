package ui;


import crud.RehomeCRUD;
import javax.swing.*;
import java.awt.*;


public class RehomePanel extends JPanel {
   private JTextField nameField, typeField, breedField, ageField;
   private JTextArea reasonArea;
   private int currentUserId;


   public RehomePanel(int userId) {
       this.currentUserId = userId;
       setLayout(new BorderLayout());
       setBackground(StyleTheme.BG_COLOR);


       JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
       header.setBackground(StyleTheme.BG_COLOR);
       JLabel title = new JLabel("Rehome Your Pet");
       title.setFont(StyleTheme.HEADER_FONT);
       title.setForeground(StyleTheme.MAIN_COLOR);
       header.add(title);
       add(header, BorderLayout.NORTH);


       JPanel wrapper = new JPanel(new GridBagLayout());
       wrapper.setBackground(StyleTheme.BG_COLOR);
       JPanel formPanel = new JPanel(new GridBagLayout());
       formPanel.setBackground(StyleTheme.BG_COLOR);
       GridBagConstraints gbc = new GridBagConstraints();
       gbc.insets = new Insets(10, 10, 10, 10);
       gbc.fill = GridBagConstraints.HORIZONTAL;


       gbc.gridx = 0; gbc.gridy = 0; formPanel.add(createLabel("Pet Name:"), gbc);
       gbc.gridx = 1; nameField = new JTextField(20); StyleTheme.styleField(nameField); formPanel.add(nameField, gbc);


       gbc.gridx = 0; gbc.gridy = 1; formPanel.add(createLabel("Type:"), gbc);
       gbc.gridx = 1; typeField = new JTextField(20); StyleTheme.styleField(typeField); formPanel.add(typeField, gbc);


       gbc.gridx = 0; gbc.gridy = 2; formPanel.add(createLabel("Breed:"), gbc);
       gbc.gridx = 1; breedField = new JTextField(20); StyleTheme.styleField(breedField); formPanel.add(breedField, gbc);


       gbc.gridx = 0; gbc.gridy = 3; formPanel.add(createLabel("Age:"), gbc);
       gbc.gridx = 1; ageField = new JTextField(5); StyleTheme.styleField(ageField); formPanel.add(ageField, gbc);


       gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.NORTHWEST; formPanel.add(createLabel("Reason:"), gbc);
       gbc.gridx = 1; reasonArea = new JTextArea(4, 20); reasonArea.setFont(StyleTheme.FIELD_FONT);
       reasonArea.setBorder(BorderFactory.createLineBorder(StyleTheme.MAIN_COLOR));
       formPanel.add(new JScrollPane(reasonArea), gbc);


       gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.EAST;
       JButton submitBtn = new JButton("Submit Request");
       StyleTheme.stylePrimaryButton(submitBtn);
       submitBtn.addActionListener(e -> handleSubmit());
       formPanel.add(submitBtn, gbc);


       wrapper.add(formPanel);
       add(wrapper, BorderLayout.CENTER);
   }


   private void handleSubmit() {
       String name = nameField.getText(); String type = typeField.getText(); String breed = breedField.getText(); String ageText = ageField.getText(); String reason = reasonArea.getText();
       if (name.isEmpty() || type.isEmpty() || breed.isEmpty() || ageText.isEmpty()) {
           JOptionPane.showMessageDialog(this, "Please fill all fields."); return;
       }
       try {
           int age = Integer.parseInt(ageText);
           if (RehomeCRUD.submitRequest(currentUserId, name, type, breed, age, reason)) {
               JOptionPane.showMessageDialog(this, "Request Sent!");
               nameField.setText(""); typeField.setText(""); breedField.setText(""); ageField.setText(""); reasonArea.setText("");
           } else JOptionPane.showMessageDialog(this, "Error submitting.");
       } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Age must be a number."); }
   }
   private JLabel createLabel(String text) { JLabel l = new JLabel(text); l.setFont(StyleTheme.SUB_FONT); l.setForeground(StyleTheme.DARK_GRAY); return l; }
}
