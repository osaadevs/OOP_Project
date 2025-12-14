package crud;


import config.DBConnection;
import model.RehomeRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RehomeCRUD {


   // 1. Submit Request (For Member 5 later)
   public static boolean submitRequest(int userId, String petName, String petType, String petBreed, int age, String reason) {
       String sql = "INSERT INTO rehome_requests (user_id, pet_name, pet_type, pet_breed, age, reason, status) " +
               "VALUES (?, ?, ?, ?, ?, ?, 'Pending')";
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setInt(1, userId);
           pstmt.setString(2, petName);
           pstmt.setString(3, petType);
           pstmt.setString(4, petBreed);
           pstmt.setInt(5, age);
           pstmt.setString(6, reason);
           return pstmt.executeUpdate() > 0;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }


   // 2. Get All Requests (For RehomeReviewPanel)
   public static List<RehomeRequest> getAllRequests() {
       List<RehomeRequest> list = new ArrayList<>();
       String sql = "SELECT r.*, u.username FROM rehome_requests r " +
               "JOIN users u ON r.user_id = u.user_id " +
               "WHERE r.status = 'Pending'";
       try (Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
           while (rs.next()) {
               list.add(new RehomeRequest(
                       rs.getInt("req_id"),
                       rs.getInt("user_id"),
                       rs.getString("username"),
                       rs.getString("pet_name"),
                       rs.getString("pet_type"),
                       rs.getString("pet_breed"),
                       rs.getInt("age"),
                       rs.getString("reason"),
                       rs.getString("status")
               ));
           }
       } catch (SQLException e) { e.printStackTrace(); }
       return list;
   }


   // 3. Get User Specific Requests (For Member 5 later)
   public static List<RehomeRequest> getRequestsByUserId(int userId) {
       List<RehomeRequest> list = new ArrayList<>();
       String sql = "SELECT r.*, u.username FROM rehome_requests r " +
               "JOIN users u ON r.user_id = u.user_id " +
               "WHERE r.user_id = ?";
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setInt(1, userId);
           ResultSet rs = pstmt.executeQuery();
           while (rs.next()) {
               list.add(new RehomeRequest(
                       rs.getInt("req_id"),
                       rs.getInt("user_id"),
                       rs.getString("username"),
                       rs.getString("pet_name"),
                       rs.getString("pet_type"),
                       rs.getString("pet_breed"),
                       rs.getInt("age"),
                       rs.getString("reason"),
                       rs.getString("status")
               ));
           }
       } catch (SQLException e) { e.printStackTrace(); }
       return list;
   }


   // 4. Update Status & Auto-Copy
   public static boolean updateStatus(int reqId, String status) {
      
       if ("Approved".equalsIgnoreCase(status)) {
           if (!copyToPetsTable(reqId)) {
               System.out.println("Error copying pet to inventory.");
               return false;
           }
       }


       String sql = "UPDATE rehome_requests SET status = ? WHERE req_id = ?";
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setString(1, status);
           pstmt.setInt(2, reqId);
           return pstmt.executeUpdate() > 0;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }


   private static boolean copyToPetsTable(int reqId) {
     
       String sql = "INSERT INTO pets (name, type, breed, age, shelter_id, status) " +
               "SELECT pet_name, pet_type, pet_breed, age, 1, 'Available' " +
               "FROM rehome_requests WHERE req_id = ?";
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setInt(1, reqId);
           return pstmt.executeUpdate() > 0;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }
}
