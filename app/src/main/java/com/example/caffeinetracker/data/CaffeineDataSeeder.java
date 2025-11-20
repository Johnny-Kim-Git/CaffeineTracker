package com.example.caffeinetracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CaffeineDataSeeder {

    private static final String TAG = "CaffeineDataSeeder";

    public static void seedFromAssetsIfEmpty(Context context, SQLiteDatabase db) {
        // If we already have some health data, assume seeding done
        long count = DatabaseUtils.queryNumEntries(db, AppDatabaseHelper.TABLE_HEALTH_SNAPSHOT);
        if (count > 0) {
            Log.d(TAG, "Database already seeded, skipping");
            return;
        }

        Map<String, Long> subjectToUserId = new HashMap<>();

        try {
            seedSleepFromDayCsv(context, db, subjectToUserId);
            seedHealthFromTimeCsv(context, db, subjectToUserId);
            Log.d(TAG, "Seeding completed");
        } catch (IOException e) {
            Log.e(TAG, "Error seeding database", e);
        }
    }

    // ---------- Day.csv -> SleepSession ----------

    private static void seedSleepFromDayCsv(Context context,
                                            SQLiteDatabase db,
                                            Map<String, Long> subjectToUserId) throws IOException {

        AssetManager am = context.getAssets();
        try (InputStream is = am.open("Day.csv");
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line = reader.readLine(); // header
            if (line == null) return;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] cols = line.split(",", -1);
                if (cols.length < 8) continue;

                String subject = cols[0].trim();          // S01
                int day = parseIntSafe(cols[1]);
                int sleepScore = parseIntSafe(cols[2]);
                int remMin = parseIntSafe(cols[3]);
                int coreMin = parseIntSafe(cols[4]);
                int deepMin = parseIntSafe(cols[5]);
                int totalSleepMin = parseIntSafe(cols[6]);
                String sleepNotes = cols[7].trim();

                long userId = getOrCreateUser(db, subjectToUserId, subject);

                ContentValues v = new ContentValues();
                v.put("userId", userId);
                v.put("day", day);
                v.put("sleep_score", sleepScore);
                v.put("REM_min", remMin);
                v.put("core_min", coreMin);
                v.put("deep_min", deepMin);
                v.put("total_sleep_min", totalSleepMin);
                v.put("sleep_notes", sleepNotes);

                db.insert(AppDatabaseHelper.TABLE_SLEEP_SESSION, null, v);
            }
        }
    }

    // ---------- Time.csv -> HealthSnapshot ----------

    private static void seedHealthFromTimeCsv(Context context,
                                              SQLiteDatabase db,
                                              Map<String, Long> subjectToUserId) throws IOException {

        AssetManager am = context.getAssets();
        try (InputStream is = am.open("Time.csv");
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line = reader.readLine(); // header
            if (line == null) return;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] cols = line.split(",", -1);
                if (cols.length < 11) continue;

                String subject = cols[0].trim();
                int day = parseIntSafe(cols[1]);
                String timepoint = cols[2].trim();     // T0, T30, ...
                double mood = parseDoubleSafe(cols[3]);
                double stress = parseDoubleSafe(cols[4]);
                double hrvMs = parseDoubleSafe(cols[5]);
                int hrBpm = parseIntSafe(cols[6]);
                String tag = cols[7].trim();
                String condition = cols[8].trim();     // "caffeine" or "control"
                String session = cols[9].trim();       // "AM", "PM", or empty
                String intakePattern = cols[10].trim();// fast/slow/split fast/...

                long userId = getOrCreateUser(db, subjectToUserId, subject);

                ContentValues v = new ContentValues();
                v.put("userId", userId);
                v.put("day", day);
                v.put("timepoint", timepoint);
                v.put("mood", mood);
                v.put("stress", stress);
                v.put("HRV_ms", hrvMs);
                v.put("HR_bpm", hrBpm);
                v.put("tag", tag);
                v.put("condition", condition);
                v.put("session", session);
                v.put("intake_pattern", intakePattern);

                db.insert(AppDatabaseHelper.TABLE_HEALTH_SNAPSHOT, null, v);
            }
        }
    }

    // ---------- helpers ----------

    // Each subject (S01, S02, ...) becomes one row in User
    private static long getOrCreateUser(SQLiteDatabase db,
                                        Map<String, Long> cache,
                                        String subject) {

        if (cache.containsKey(subject)) {
            return cache.get(subject);
        }

        // Simple demo identity: email/username from subject
        String email = subject.toLowerCase() + "@example.com";
        String username = subject.toLowerCase();
        String passwordHash = "demo";  // placeholder
        String createdAt = "2025-01-01T00:00:00Z";

        ContentValues v = new ContentValues();
        v.put("email", email);
        v.put("username", username);
        v.put("passwordHash", passwordHash);
        v.put("createdAt", createdAt);

        long userId = db.insert(AppDatabaseHelper.TABLE_USER, null, v);
        cache.put(subject, userId);
        return userId;
    }

    private static int parseIntSafe(String s) {
        try {
            if (s == null || s.trim().isEmpty()) return 0;
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static double parseDoubleSafe(String s) {
        try {
            if (s == null || s.trim().isEmpty()) return 0.0;
            return Double.parseDouble(s.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
