package com.librarytask;

public class Patron {
    private int patronId;
    private String name;
    private String email;
    private String password;
    private String role;

    public Patron(int patronId, String name, String email, String password, String role) {
        this.patronId = patronId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getPatronId() {
        return patronId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    // Other methods like updateProfile, viewTransactions, etc.
}
