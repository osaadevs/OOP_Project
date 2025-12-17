package ui;


import crud.ApplicationCRUD;
import crud.RehomeCRUD;
import model.AdoptionApplication;
import model.RehomeRequest;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class AdopterStatusPanel extends JPanel {
   private int currentUserId;


   public AdopterStatusPanel(int userId) {
       this.currentUserId = userId;
       setLayout(new BorderLayout());
       setBackground(StyleTheme.BG_COLOR);


       JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
       header.setBackground(StyleTheme.BG_COLOR);
       JLabel title = new JLabel("My Activity History");
       title.setFont(StyleTheme.HEADER_FONT);
       title.setForeground(StyleTheme.MAIN_COLOR);
       header.add(title);
       add(header, BorderLayout.NORTH);


       JTabbedPane tabbedPane = new JTabbedPane();
       tabbedPane.setFont(StyleTheme.BTN_FONT);
       tabbedPane.addTab("Adoption Applications", createAdoptionTablePanel());
       tabbedPane.addTab("Rehome Requests", createRehomeTablePanel());
       add(tabbedPane, BorderLayout.CENTER);
   }


   private JPanel createAdoptionTablePanel() {
       JPanel panel = new JPanel(new BorderLayout());
       panel.setBackground(StyleTheme.BG_COLOR);
       String[] cols = {"App ID", "Pet Name", "Status"};
       DefaultTableModel model = new DefaultTableModel(cols, 0) {
           @Override public boolean isCellEditable(int row, int col) { return false; }
       };
       JTable table = new JTable(model);
       StyleTheme.styleTable(table);
       table.getColumnModel().getColumn(2).setCellRenderer(new StatusCellRenderer());


       List<AdoptionApplication> list = ApplicationCRUD.getAppsByUserId(currentUserId);
       for (AdoptionApplication a : list) model.addRow(new Object[]{a.getAppId(), a.getPetName(), a.getStatus()});


       JScrollPane scroll = new JScrollPane(table);
       scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
       scroll.getViewport().setBackground(StyleTheme.BG_COLOR);
       panel.add(scroll, BorderLayout.CENTER);
       return panel;
   }


   private JPanel createRehomeTablePanel() {
       JPanel panel = new JPanel(new BorderLayout());
       panel.setBackground(StyleTheme.BG_COLOR);
       String[] cols = {"Req ID", "Pet Name", "Breed", "Status"};
       DefaultTableModel model = new DefaultTableModel(cols, 0) {
           @Override public boolean isCellEditable(int row, int col) { return false; }
       };
       JTable table = new JTable(model);
       StyleTheme.styleTable(table);
       table.getColumnModel().getColumn(3).setCellRenderer(new StatusCellRenderer());


       List<RehomeRequest> list = RehomeCRUD.getRequestsByUserId(currentUserId);
       for (RehomeRequest r : list) model.addRow(new Object[]{r.getReqId(), r.getPetName(), r.getPetBreed(), r.getStatus()});


       JScrollPane scroll = new JScrollPane(table);
       scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
       scroll.getViewport().setBackground(StyleTheme.BG_COLOR);
       panel.add(scroll, BorderLayout.CENTER);
       return panel;
   }


   private class StatusCellRenderer extends DefaultTableCellRenderer {
       public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
           Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
           String status = (String) value;
           if ("Approved".equalsIgnoreCase(status) || "Accepted".equalsIgnoreCase(status)) {
               c.setForeground(new Color(46, 204, 113)); setFont(getFont().deriveFont(Font.BOLD));
           } else if ("Rejected".equalsIgnoreCase(status)) c.setForeground(new Color(231, 76, 60));
           else c.setForeground(Color.BLACK);
           setHorizontalAlignment(JLabel.CENTER);
           return c;
       }
   }
}
