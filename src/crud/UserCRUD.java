package crud;

import config.DBConnection;
import model.User;
import utils.PasswordUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserCRUD {

    //CREATE
    public static User createUser(String username, String password, String role, String contact) {
        String sql = "INSERT INTO users (username, password, role, contact) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, username);
            pstmt.setString(2, PasswordUtils.hashPassword(password));
            pstmt.setString(3, role);
            pstmt.setString(4, contact);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return new User(rs.getInt(1), username, role, contact);
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // [FIX] Specifically catch duplicate usernames here!
            System.out.println("Error: Username '" + username + "' already exists.");
            return null; // Return null so the UI knows it failed
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Return null for other errors too
        }
        return null;
    }

    // UPDATE
    public static boolean updateUser(int userId, String username, String contact) {
        String sql = "UPDATE users SET username = ?, contact = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, contact);
            pstmt.setInt(3, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE
    public static boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("contact")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    //LOGIN
    public static User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, PasswordUtils.hashPassword(password));

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("role"), rs.getString("contact"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<User> getUsersByRole(String role) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, role);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("contact")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}