
package crud;


import config.DBConnection;
import model.Pet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PetCRUD {


   // --- CREATE ---
   public static boolean addPet(String name, String type, String breed, int age, int shelterId) {
       String sql = "INSERT INTO pets (name, type, breed, age, shelter_id, status) VALUES (?, ?, ?, ?, ?, 'Available')";
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setString(1, name);
           pstmt.setString(2, type);
           pstmt.setString(3, breed);
           pstmt.setInt(4, age);
           pstmt.setInt(5, shelterId);
           return pstmt.executeUpdate() > 0;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }


   // --- READ ---
   public static List<Pet> getAllPets() {
       List<Pet> pets = new ArrayList<>();
       String sql = "SELECT * FROM pets";
       try (Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
           while (rs.next()) {
               pets.add(new Pet(
                       rs.getInt("pet_id"),
                       rs.getString("name"),
                       rs.getString("type"),
                       rs.getString("breed"),
                       rs.getInt("age"),
                       rs.getInt("shelter_id"),
                       rs.getString("status")
               ));
           }
       } catch (SQLException e) { e.printStackTrace(); }
       return pets;
   }


   // --- UPDATE ---
   public static boolean updatePet(int petId, String name, String type, String breed, int age) {
       String sql = "UPDATE pets SET name = ?, type = ?, breed = ?, age = ? WHERE pet_id = ?";
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setString(1, name);
           pstmt.setString(2, type);
           pstmt.setString(3, breed);
           pstmt.setInt(4, age);
           pstmt.setInt(5, petId);
           return pstmt.executeUpdate() > 0;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }


   // --- DELETE ---
   public static boolean deletePet(int petId) {
       String sql = "DELETE FROM pets WHERE pet_id = ?";
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setInt(1, petId);
           return pstmt.executeUpdate() > 0;
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }
   }
}
