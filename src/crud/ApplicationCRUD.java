package crud;


import config.DBConnection;
import model.AdoptionApplication;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ApplicationCRUD {


   // 1. Submit a new application (User)
   public static boolean submitApp(int userId, int petId) {
       if (hasApplied(userId, petId)) return false; // Prevent duplicates


       String sql = "INSERT INTO adoption_applications (user_id, pet_id, status) VALUES (?, ?, 'Pending')";
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setInt(1, userId);
           pstmt.setInt(2, petId);
           return pstmt.executeUpdate() > 0;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }


   // Helper: Check for duplicates
   private static boolean hasApplied(int userId, int petId) {
       String sql = "SELECT * FROM adoption_applications WHERE user_id = ? AND pet_id = ? AND status = 'Pending'";
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setInt(1, userId);
           pstmt.setInt(2, petId);
           ResultSet rs = pstmt.executeQuery();
           return rs.next();
       } catch (SQLException e) { return false; }
   }


   // 2. Get All Applications (For Admin/Worker)
   public static List<AdoptionApplication> getAllApps() {
       List<AdoptionApplication> list = new ArrayList<>();
       String sql = "SELECT a.app_id, a.user_id, u.username, a.pet_id, p.name AS pet_name, a.status " +
               "FROM adoption_applications a " +
               "JOIN users u ON a.user_id = u.user_id " +
               "JOIN pets p ON a.pet_id = p.pet_id " +
               "WHERE a.status = 'Pending'";


       try (Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
           while (rs.next()) {
               list.add(new AdoptionApplication(
                       rs.getInt("app_id"),
                       rs.getInt("user_id"),
                       rs.getString("username"),
                       rs.getInt("pet_id"),
                       rs.getString("pet_name"),
                       rs.getString("status")
               ));
           }
       } catch (SQLException e) { e.printStackTrace(); }
       return list;
   }


   // 3. Get Applications for a Specific User (For Adopter Status Panel)
   public static List<AdoptionApplication> getAppsByUserId(int userId) {
       List<AdoptionApplication> list = new ArrayList<>();
       String sql = "SELECT a.app_id, a.user_id, u.username, a.pet_id, p.name AS pet_name, a.status " +
               "FROM adoption_applications a " +
               "JOIN users u ON a.user_id = u.user_id " +
               "JOIN pets p ON a.pet_id = p.pet_id " +
               "WHERE a.user_id = ?";


       try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setInt(1, userId);
           ResultSet rs = pstmt.executeQuery();
           while (rs.next()) {
               list.add(new AdoptionApplication(
                       rs.getInt("app_id"),
                       rs.getInt("user_id"),
                       rs.getString("username"),
                       rs.getInt("pet_id"),
                       rs.getString("pet_name"),
                       rs.getString("status")
               ));
           }
       } catch (SQLException e) { e.printStackTrace(); }
       return list;
   }


   // 4. Update Status (Approve/Reject) - With Safe Connection Handling
   public static boolean updateStatus(int appId, String newStatus) {
       boolean updateSuccess = false;
       String sql = "UPDATE adoption_applications SET status = ? WHERE app_id = ?";


       // 1. Run the Application Update
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setString(1, newStatus);
           pstmt.setInt(2, appId);
           updateSuccess = pstmt.executeUpdate() > 0;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }


       // 2. If successful AND Approved, update Pet status independently
       if (updateSuccess && "Approved".equalsIgnoreCase(newStatus)) {
           updatePetStatus(appId);
       }


       return updateSuccess;
   }


   private static void updatePetStatus(int appId) {
       String sql = "UPDATE pets SET status = 'Adopted' WHERE pet_id = " +
               "(SELECT pet_id FROM adoption_applications WHERE app_id = ?)";
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setInt(1, appId);
           pstmt.executeUpdate();
       } catch (SQLException e) { e.printStackTrace(); }
   }
}
