package ui;


import crud.ApplicationCRUD;
import model.AdoptionApplication;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class ApplicationPanel extends JPanel {
   private JTable table;
   private DefaultTableModel model;


   public ApplicationPanel(int shelterId) {
       setLayout(new BorderLayout());
       setBackground(StyleTheme.BG_COLOR);


       JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
       header.setBackground(StyleTheme.BG_COLOR);
       JLabel title = new JLabel("Review Adoption Applications");
       title.setFont(StyleTheme.HEADER_FONT);
       title.setForeground(StyleTheme.MAIN_COLOR);
       header.add(title);
       add(header, BorderLayout.NORTH);


       String[] cols = {"App ID", "Applicant", "Pet Name", "Current Status"};
       model = new DefaultTableModel(cols, 0) {
           @Override public boolean isCellEditable(int row, int col) { return false; }
       };
       table = new JTable(model);
       StyleTheme.styleTable(table);


       JScrollPane scrollPane = new JScrollPane(table);
       scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
       scrollPane.getViewport().setBackground(StyleTheme.BG_COLOR);
       add(scrollPane, BorderLayout.CENTER);


       JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
       btnPanel.setBackground(StyleTheme.BG_COLOR);
       JButton btnApprove = new JButton("Approve Adoption");
       StyleTheme.stylePrimaryButton(btnApprove); btnApprove.setBackground(StyleTheme.SUCCESS_GREEN);
       JButton btnReject = new JButton("Reject");
       StyleTheme.styleDangerButton(btnReject);


       btnApprove.addActionListener(e -> processApp("Approved"));
       btnReject.addActionListener(e -> processApp("Rejected"));
       btnPanel.add(btnApprove); btnPanel.add(btnReject);
       add(btnPanel, BorderLayout.SOUTH);


       loadData();
   }


   private void processApp(String status) {
       int row = table.getSelectedRow();
       if (row == -1) { JOptionPane.showMessageDialog(this, "Please select an application."); return; }


       String currentStatus = (String) model.getValueAt(row, 3);
       if (!"Pending".equalsIgnoreCase(currentStatus)) {
           JOptionPane.showMessageDialog(this, "Application already processed."); return;
       }


       int appId = (int) model.getValueAt(row, 0);
       if (ApplicationCRUD.updateStatus(appId, status)) {
           JOptionPane.showMessageDialog(this, "Application " + status + "!"); loadData();
       } else JOptionPane.showMessageDialog(this, "Error.");
   }


   private void loadData() {
       model.setRowCount(0);
       List<AdoptionApplication> list = ApplicationCRUD.getAllApps();
       for (AdoptionApplication a : list) model.addRow(new Object[]{a.getAppId(), a.getUserName(), a.getPetName(), a.getStatus()});
   }
}
