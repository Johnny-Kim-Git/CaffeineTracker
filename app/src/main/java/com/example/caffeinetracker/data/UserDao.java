package com.example.caffeinetracker.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.caffeinetracker.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private final SQLiteDatabase db;

    public static final String TABLE = AppDatabaseHelper.TABLE_USER;

    public UserDao(SQLiteDatabase db) {
        this.db = db;
    }

    // Create
    public long insert(User user) {
        ContentValues values = new ContentValues();
        values.put("email", user.email);
        values.put("username", user.username);
        values.put("passwordHash", user.passwordHash);
        values.put("createdAt", user.createdAt);
        return db.insert(TABLE, null, values);
    }

    // Read by id
    public User getById(long userId) {
        String[] cols = {"userId", "email", "username", "passwordHash", "createdAt"};
        Cursor c = db.query(TABLE, cols, "userId = ?",
                new String[]{String.valueOf(userId)}, null, null, null);
        try {
            if (c.moveToFirst()) {
                User u = new User();
                u.userId = c.getLong(0);
                u.email = c.getString(1);
                u.username = c.getString(2);
                u.passwordHash = c.getString(3);
                u.createdAt = c.getString(4);
                return u;
            }
            return null;
        } finally {
            c.close();
        }
    }

    // Read by email (for login)
    public User getByEmail(String email) {
        String[] cols = {"userId", "email", "username", "passwordHash", "createdAt"};
        Cursor c = db.query(TABLE, cols, "email = ?",
                new String[]{email}, null, null, null);
        try {
            if (c.moveToFirst()) {
                User u = new User();
                u.userId = c.getLong(0);
                u.email = c.getString(1);
                u.username = c.getString(2);
                u.passwordHash = c.getString(3);
                u.createdAt = c.getString(4);
                return u;
            }
            return null;
        } finally {
            c.close();
        }
    }

    // Get all users (probably just 1 in your app, but still)
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String[] cols = {"userId", "email", "username", "passwordHash", "createdAt"};
        Cursor c = db.query(TABLE, cols, null, null, null, null, "userId ASC");
        try {
            while (c.moveToNext()) {
                User u = new User();
                u.userId = c.getLong(0);
                u.email = c.getString(1);
                u.username = c.getString(2);
                u.passwordHash = c.getString(3);
                u.createdAt = c.getString(4);
                list.add(u);
            }
        } finally {
            c.close();
        }
        return list;
    }
}
