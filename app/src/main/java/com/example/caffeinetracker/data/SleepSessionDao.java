package com.example.caffeinetracker.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.caffeinetracker.models.SleepSession;

import java.util.ArrayList;
import java.util.List;

public class SleepSessionDao {

    private final SQLiteDatabase db;
    public static final String TABLE = AppDatabaseHelper.TABLE_SLEEP_SESSION;

    public SleepSessionDao(SQLiteDatabase db) {
        this.db = db;
    }

    public long insert(SleepSession s) {
        ContentValues v = new ContentValues();
        v.put("userId", s.userId);
        v.put("day", s.day);
        v.put("sleep_score", s.sleepScore);
        v.put("REM_min", s.remMin);
        v.put("core_min", s.coreMin);
        v.put("deep_min", s.deepMin);
        v.put("total_sleep_min", s.totalSleepMin);
        v.put("sleep_notes", s.sleepNotes);
        return db.insert(TABLE, null, v);
    }

    public SleepSession getForUserAndDay(long userId, int day) {
        String[] cols = {
                "sleepId", "userId", "day", "sleep_score",
                "REM_min", "core_min", "deep_min",
                "total_sleep_min", "sleep_notes"
        };
        Cursor c = db.query(TABLE, cols,
                "userId = ? AND day = ?",
                new String[]{String.valueOf(userId), String.valueOf(day)},
                null, null, null);
        try {
            if (c.moveToFirst()) {
                SleepSession s = new SleepSession();
                s.sleepId = c.getLong(0);
                s.userId = c.getLong(1);
                s.day = c.getInt(2);
                s.sleepScore = c.getInt(3);
                s.remMin = c.getInt(4);
                s.coreMin = c.getInt(5);
                s.deepMin = c.getInt(6);
                s.totalSleepMin = c.getInt(7);
                s.sleepNotes = c.getString(8);
                return s;
            }
            return null;
        } finally {
            c.close();
        }
    }

    public List<SleepSession> getForUser(long userId) {
        List<SleepSession> list = new ArrayList<>();
        String[] cols = {
                "sleepId", "userId", "day", "sleep_score",
                "REM_min", "core_min", "deep_min",
                "total_sleep_min", "sleep_notes"
        };
        Cursor c = db.query(TABLE, cols,
                "userId = ?", new String[]{String.valueOf(userId)},
                null, null, "day ASC");
        try {
            while (c.moveToNext()) {
                SleepSession s = new SleepSession();
                s.sleepId = c.getLong(0);
                s.userId = c.getLong(1);
                s.day = c.getInt(2);
                s.sleepScore = c.getInt(3);
                s.remMin = c.getInt(4);
                s.coreMin = c.getInt(5);
                s.deepMin = c.getInt(6);
                s.totalSleepMin = c.getInt(7);
                s.sleepNotes = c.getString(8);
                list.add(s);
            }
        } finally {
            c.close();
        }
        return list;
    }
}
