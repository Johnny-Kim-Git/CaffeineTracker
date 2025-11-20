package com.example.caffeinetracker.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.caffeinetracker.models.HealthSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HealthSnapshotDao {

    private final SQLiteDatabase db;
    public static final String TABLE = AppDatabaseHelper.TABLE_HEALTH_SNAPSHOT;

    public HealthSnapshotDao(SQLiteDatabase db) {
        this.db = db;
    }

    public long insert(HealthSnapshot h) {
        ContentValues v = new ContentValues();
        v.put("userId", h.userId);
        v.put("day", h.day);
        v.put("timepoint", h.timepoint);
        v.put("mood", h.mood);
        v.put("stress", h.stress);
        v.put("HRV_ms", h.hrvMs);
        v.put("HR_bpm", h.hrBpm);
        v.put("tag", h.tag);
        v.put("condition", h.condition);
        v.put("session", h.session);
        v.put("intake_pattern", h.intakePattern);
        return db.insert(TABLE, null, v);
    }

    public List<HealthSnapshot> getForUserAndDay(long userId, int day) {
        List<HealthSnapshot> list = new ArrayList<>();
        String[] cols = {
                "snapshotId", "userId", "day", "timepoint",
                "mood", "stress", "HRV_ms", "HR_bpm",
                "tag", "condition", "session", "intake_pattern"
        };
        Cursor c = db.query(TABLE, cols,
                "userId = ? AND day = ?",
                new String[]{String.valueOf(userId), String.valueOf(day)},
                null, null, "timepoint ASC");
        try {
            while (c.moveToNext()) {
                HealthSnapshot h = new HealthSnapshot();
                h.snapshotId = c.getLong(0);
                h.userId = c.getLong(1);
                h.day = c.getInt(2);
                h.timepoint = c.getString(3);
                h.mood = c.getDouble(4);
                h.stress = c.getDouble(5);
                h.hrvMs = c.getDouble(6);
                h.hrBpm = c.getDouble(7);
                h.tag = c.getString(8);
                h.condition = c.getString(9);
                h.session = c.getString(10);
                h.intakePattern = c.getString(11);
                list.add(h);
            }
        } finally {
            c.close();
        }
        return list;
    }

    public List<HealthSnapshot> getAllForUser(long userId) {
        List<HealthSnapshot> list = new ArrayList<>();
        String[] cols = {
                "snapshotId", "userId", "day", "timepoint",
                "mood", "stress", "HRV_ms", "HR_bpm",
                "tag", "condition", "session", "intake_pattern"
        };
        Cursor c = db.query(TABLE, cols,
                "userId = ?", new String[]{String.valueOf(userId)},
                null, null, "day ASC, timepoint ASC");
        try {
            while (c.moveToNext()) {
                HealthSnapshot h = new HealthSnapshot();
                h.snapshotId = c.getLong(0);
                h.userId = c.getLong(1);
                h.day = c.getInt(2);
                h.timepoint = c.getString(3);
                h.mood = c.getDouble(4);
                h.stress = c.getDouble(5);
                h.hrvMs = c.getDouble(6);
                h.hrBpm = c.getDouble(7);
                h.tag = c.getString(8);
                h.condition = c.getString(9);
                h.session = c.getString(10);
                h.intakePattern = c.getString(11);
                list.add(h);
            }
        } finally {
            c.close();
        }
        return list;
    }
}
