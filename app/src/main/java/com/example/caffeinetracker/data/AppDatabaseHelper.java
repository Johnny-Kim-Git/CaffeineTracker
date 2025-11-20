package com.example.caffeinetracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabaseHelper extends SQLiteOpenHelper {

    // --- DB meta ---
    private static final String DB_NAME = "caffeine_tracker.db";
    private static final int DB_VERSION = 1;

    // --- Table names ---
    public static final String TABLE_USER           = "User";
    public static final String TABLE_USER_PROFILE   = "UserProfile";
    public static final String TABLE_COFFEE         = "Coffee";
    public static final String TABLE_BOTTLE_SPEC    = "BottleSpec";
    public static final String TABLE_CONSUMPTION    = "Consumption";
    public static final String TABLE_HEALTH_SNAPSHOT= "HealthSnapshot";
    public static final String TABLE_SLEEP_SESSION  = "SleepSession";

    public AppDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        // Very important if you use FOREIGN KEY constraints
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // USER (login identity)
        db.execSQL(
                "CREATE TABLE " + TABLE_USER + " (" +
                        "userId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "email TEXT NOT NULL UNIQUE, " +
                        "username TEXT NOT NULL UNIQUE, " +
                        "passwordHash TEXT NOT NULL, " +
                        "createdAt TEXT NOT NULL" +          // ISO 8601 datetime string
                        ");"
        );

        // USER PROFILE (1-to-1 with User)
        db.execSQL(
                "CREATE TABLE " + TABLE_USER_PROFILE + " (" +
                        "userId INTEGER PRIMARY KEY, " +     // also FK to User
                        "gender TEXT, " +                    // we'll treat enums as TEXT
                        "age INTEGER, " +
                        "heightCm REAL, " +
                        "weightKg REAL, " +
                        "caffeineSensitivity TEXT, " +
                        "FOREIGN KEY(userId) REFERENCES " + TABLE_USER + "(userId) " +
                        "ON DELETE CASCADE" +
                        ");"
        );

        // COFFEE (brand folded in here: brandName column)
        db.execSQL(
                "CREATE TABLE " + TABLE_COFFEE + " (" +
                        "coffeeId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +             // e.g. Latte, Americano
                        "brandName TEXT NOT NULL, " +        // e.g. Starbucks
                        "flavor TEXT" +                      // optional (Matcha, Vanilla…)
                        ");"
        );

        // BOTTLE SPEC (size + caffeine per serving)
        db.execSQL(
                "CREATE TABLE " + TABLE_BOTTLE_SPEC + " (" +
                        "bottleSpecId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "label TEXT NOT NULL, " +            // e.g. Grande, Tall
                        "volumeMl REAL NOT NULL, " +         // 100.0
                        "caffeineMgPerServing REAL NOT NULL, " + // 100.0
                        "coffeeId INTEGER NOT NULL, " +
                        "FOREIGN KEY(coffeeId) REFERENCES " + TABLE_COFFEE + "(coffeeId) " +
                        "ON DELETE CASCADE" +
                        ");"
        );

        // CONSUMPTION (each drinking event)
        db.execSQL(
                "CREATE TABLE " + TABLE_CONSUMPTION + " (" +
                        "consumptionId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "userId INTEGER NOT NULL, " +
                        "bottleSpecId INTEGER NOT NULL, " +
                        "consumptionTime TEXT NOT NULL, " +      // ISO datetime
                        "servings REAL NOT NULL, " +             // e.g. 0.5, 1.0, 1.5
                        "caffeineMgCached REAL, " +              // pre-computed mg
                        "FOREIGN KEY(userId) REFERENCES " + TABLE_USER + "(userId) " +
                        "ON DELETE CASCADE, " +
                        "FOREIGN KEY(bottleSpecId) REFERENCES " + TABLE_BOTTLE_SPEC + "(bottleSpecId) " +
                        "ON DELETE CASCADE" +
                        ");"
        );

        // HEALTH SNAPSHOT  (rows from Time.csv)
        db.execSQL(
                "CREATE TABLE " + TABLE_HEALTH_SNAPSHOT + " (" +
                        "snapshotId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "userId INTEGER NOT NULL, " +
                        "day INTEGER NOT NULL, " +
                        "timepoint TEXT NOT NULL, " +
                        "mood REAL, " +
                        "stress REAL, " +
                        "HRV_ms REAL, " +
                        "HR_bpm INTEGER, " +
                        "tag TEXT, " +
                        "condition TEXT, " +
                        "session TEXT, " +
                        "intake_pattern TEXT, " +
                        "FOREIGN KEY(userId) REFERENCES " + TABLE_USER + "(userId) " +
                        "ON DELETE CASCADE" +
                        ");"
        );


        // SLEEP SESSION (rows from Day.csv)
        db.execSQL(
                "CREATE TABLE " + TABLE_SLEEP_SESSION + " (" +
                        "sleepId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "userId INTEGER NOT NULL, " +
                        "day INTEGER NOT NULL, " +
                        "sleep_score INTEGER, " +
                        "REM_min INTEGER, " +
                        "core_min INTEGER, " +
                        "deep_min INTEGER, " +
                        "total_sleep_min INTEGER, " +
                        "sleep_notes TEXT, " +
                        "FOREIGN KEY(userId) REFERENCES " + TABLE_USER + "(userId) " +
                        "ON DELETE CASCADE" +
                        ");"
        );

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SLEEP_SESSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEALTH_SNAPSHOT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONSUMPTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOTTLE_SPEC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COFFEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

}
