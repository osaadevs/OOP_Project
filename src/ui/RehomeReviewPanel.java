package ui;


import crud.RehomeCRUD;
import model.RehomeRequest;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class RehomeReviewPanel extends JPanel {
   private JTable table;
   private DefaultTableModel model;


   public RehomeReviewPanel() {
       setLayout(new BorderLayout());
       setBackground(StyleTheme.BG_COLOR);


       JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
       header.setBackground(StyleTheme.BG_COLOR);
       JLabel title = new JLabel("Review Rehome Requests");
       title.setFont(StyleTheme.HEADER_FONT);
       title.setForeground(StyleTheme.MAIN_COLOR);
       header.add(title);
       add(header, BorderLayout.NORTH);


       String[] cols = {"Req ID", "Owner", "Pet Name", "Type", "Breed", "Age", "Reason"};
       model = new DefaultTableModel(cols, 0) {
           @Override
           public boolean isCellEditable(int row, int col) { return false; }
       };
       table = new JTable(model);
       StyleTheme.styleTable(table);


       JScrollPane scrollPane = new JScrollPane(table);
       scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
       scrollPane.getViewport().setBackground(StyleTheme.BG_COLOR);
       add(scrollPane, BorderLayout.CENTER);


       JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
       btnPanel.setBackground(StyleTheme.BG_COLOR);


       JButton btnAccept = new JButton("Accept");
       StyleTheme.stylePrimaryButton(btnAccept);
       btnAccept.setBackground(StyleTheme.SUCCESS_GREEN);


       JButton btnReject = new JButton("Reject");
       StyleTheme.styleDangerButton(btnReject);


       btnAccept.addActionListener(e -> processRequest("Approved"));
       btnReject.addActionListener(e -> processRequest("Rejected"));


       btnPanel.add(btnAccept);
       btnPanel.add(btnReject);
       add(btnPanel, BorderLayout.SOUTH);


       loadData();
   }


   private void processRequest(String status) {
       int row = table.getSelectedRow();
       if (row == -1) { JOptionPane.showMessageDialog(this, "Please select a request."); return; }
       int reqId = (int) model.getValueAt(row, 0);


       if (RehomeCRUD.updateStatus(reqId, status)) {
           String msg = status.equals("Approved")
                   ? "Request Approved!\nThe pet has been AUTOMATICALLY added to 'Manage Pets'."
                   : "Request Rejected.";
           JOptionPane.showMessageDialog(this, msg);
           loadData();
       } else {
           JOptionPane.showMessageDialog(this, "Error updating status.");
       }
   }


   private void loadData() {
       model.setRowCount(0);
       List<RehomeRequest> list = RehomeCRUD.getAllRequests();
       for (RehomeRequest r : list) {
           model.addRow(new Object[]{r.getReqId(), r.getUsername(), r.getPetName(), r.getPetType(), r.getPetBreed(), r.getAge(), r.getReason()});
       }
   }
}
