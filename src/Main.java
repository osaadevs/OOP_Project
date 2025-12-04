import config.DBConnection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        if(DBConnection.getConnection() != null) {
            System.out.println("Connection Successful");
        } else {
            System.out.println("Connection Failed");
        }


    }
}