import config.DBConnection;
import ui.AuthFrame;
import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AuthFrame().setVisible(true);
        });
    }
}
