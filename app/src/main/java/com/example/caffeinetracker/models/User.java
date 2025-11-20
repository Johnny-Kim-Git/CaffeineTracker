package com.example.caffeinetracker.models;

public class User {
    public long userId;        // PK
    public String email;       // unique
    public String username;    // unique
    public String passwordHash;
    public String createdAt;   // ISO 8601 string (e.g. "2025-11-21T13:45:00Z")

    public User() {}

    public User(long userId, String email, String username,
                String passwordHash, String createdAt) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }
}
