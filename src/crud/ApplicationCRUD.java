package crud;


import config.DBConnection;
import model.AdoptionApplication;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ApplicationCRUD {


   // 1. Submit a new application (User)
public static boolean submitApp(int userId, int petId) {

        // if (hasApplied(userId, petId)) return false; 

        Connection conn = null;
        PreparedStatement pstmtApp = null;
        PreparedStatement pstmtPet = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // START TRANSACTION

            // Task A: Create the Application
            String sqlApp = "INSERT INTO adoption_applications (user_id, pet_id, status) VALUES (?, ?, 'Pending')";
            pstmtApp = conn.prepareStatement(sqlApp);
            pstmtApp.setInt(1, userId);
            pstmtApp.setInt(2, petId);
            int rows1 = pstmtApp.executeUpdate();

            // Task B: Lock the Pet (Change Status to Pending)
            String sqlPet = "UPDATE pets SET status = 'Pending' WHERE pet_id = ?";
            pstmtPet = conn.prepareStatement(sqlPet);
            pstmtPet.setInt(1, petId);
            int rows2 = pstmtPet.executeUpdate();

            // If BOTH tasks succeeded, save changes
            if (rows1 > 0 && rows2 > 0) {
                conn.commit(); 
                System.out.println("Success! Application created AND Pet locked.");
                return true;
            } else {
                conn.rollback(); // Undo if one failed
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
                if (conn != null) conn.close();
            } catch (SQLException e) { e.printStackTrace(); }
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


// 4. Update Application Status (Approve/Reject)
   // [UPDATED] Now handles "Releasing" the pet back to Available if Rejected
   public static boolean updateStatus(int appId, String newStatus) {
       Connection conn = null;
       PreparedStatement pstmtApp = null;
       PreparedStatement pstmtPet = null;
       boolean success = false;

       // 1. Get the Pet ID first (We need to know WHICH pet to release)
       int petId = getPetIdByAppId(appId);
       if (petId == -1) return false; // Safety check

       String sqlApp = "UPDATE adoption_applications SET status = ? WHERE app_id = ?";
       
       try {
           conn = DBConnection.getConnection();
           conn.setAutoCommit(false); // Start Transaction

           // Step 1: Update Application Status (e.g., to "Rejected")
           pstmtApp = conn.prepareStatement(sqlApp);
           pstmtApp.setString(1, newStatus);
           pstmtApp.setInt(2, appId);
           int row1 = pstmtApp.executeUpdate();

           // Step 2: Handle Pet Status Change
           int row2 = 0;
           if ("Rejected".equalsIgnoreCase(newStatus)) {
               // IF REJECTED: Pet becomes 'Available' again (Reappear in search)
               String sqlPet = "UPDATE pets SET status = 'Available' WHERE pet_id = ?";
               pstmtPet = conn.prepareStatement(sqlPet);
               pstmtPet.setInt(1, petId);
               row2 = pstmtPet.executeUpdate();
           } 
           else if ("Approved".equalsIgnoreCase(newStatus)) {
               // IF APPROVED: Pet becomes 'Adopted'
               String sqlPet = "UPDATE pets SET status = 'Adopted' WHERE pet_id = ?";
               pstmtPet = conn.prepareStatement(sqlPet);
               pstmtPet.setInt(1, petId);
               row2 = pstmtPet.executeUpdate();
           }
           else {
               // If status is just updated to something else, we count it as success
               row2 = 1; 
           }

           if (row1 > 0 && row2 > 0) {
               conn.commit();
               success = true;
           } else {
               conn.rollback();
           }

       } catch (SQLException e) {
           e.printStackTrace();
           try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
       } finally {
           try {
               if (conn != null) conn.setAutoCommit(true);
               if (conn != null) conn.close();
               if (pstmtApp != null) pstmtApp.close();
               if (pstmtPet != null) pstmtPet.close();
           } catch (SQLException e) { e.printStackTrace(); }
       }
       return success;
   }

   // [REQUIRED HELPER] Add this method to the bottom of the class
   private static int getPetIdByAppId(int appId) {
       String sql = "SELECT pet_id FROM adoption_applications WHERE app_id = ?";
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setInt(1, appId);
           ResultSet rs = pstmt.executeQuery();
           if (rs.next()) return rs.getInt("pet_id");
       } catch (SQLException e) { e.printStackTrace(); }
       return -1;
   }}
