package com.example.caffeinetracker.models;

public class UserProfile {
    public long userId;             // PK + FK -> User.userId
    public String gender;           // "Male", "Female", "Other", "PreferNotToSay"
    public int age;
    public double heightCm;
    public double weightKg;
    public String caffeineSensitivity;  // "Low", "Normal", "High"

    public UserProfile() {}

    public UserProfile(long userId, String gender, int age,
                       double heightCm, double weightKg,
                       String caffeineSensitivity) {
        this.userId = userId;
        this.gender = gender;
        this.age = age;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.caffeineSensitivity = caffeineSensitivity;
    }
}
