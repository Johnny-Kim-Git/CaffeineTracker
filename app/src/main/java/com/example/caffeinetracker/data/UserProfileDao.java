package com.example.caffeinetracker.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.caffeinetracker.models.UserProfile;

public class UserProfileDao {

    private final SQLiteDatabase db;
    public static final String TABLE = AppDatabaseHelper.TABLE_USER_PROFILE;

    public UserProfileDao(SQLiteDatabase db) {
        this.db = db;
    }

    // Upsert (insert or replace by userId)
    public long upsert(UserProfile p) {
        ContentValues v = new ContentValues();
        v.put("userId", p.userId);
        v.put("gender", p.gender);
        v.put("age", p.age);
        v.put("heightCm", p.heightCm);
        v.put("weightKg", p.weightKg);
        v.put("caffeineSensitivity", p.caffeineSensitivity);
        // replace will update if same PK
        return db.replace(TABLE, null, v);
    }

    // Get single profile for a user
    public UserProfile getForUser(long userId) {
        String[] cols = {
                "userId", "gender", "age", "heightCm",
                "weightKg", "caffeineSensitivity"
        };
        Cursor c = db.query(TABLE, cols, "userId = ?",
                new String[]{String.valueOf(userId)}, null, null, null);
        try {
            if (c.moveToFirst()) {
                UserProfile p = new UserProfile();
                p.userId = c.getLong(0);
                p.gender = c.getString(1);
                p.age = c.getInt(2);
                p.heightCm = c.getDouble(3);
                p.weightKg = c.getDouble(4);
                p.caffeineSensitivity = c.getString(5);
                return p;
            }
            return null;
        } finally {
            c.close();
        }
    }
}
