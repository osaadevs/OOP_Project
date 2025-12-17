package model;

public class User {
    private final int id;
    private final String username;
    private final String fullname;
    private final String role;
    private final String contact;

    public User(int id, String username, String fullname, String role, String contact) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.role = role;
        this.contact= contact;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getRole() {
        return role;
    }

    public String getContact() {
        return contact;
    }
}

