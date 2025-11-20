package com.example.caffeinetracker.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.caffeinetracker.models.BottleSpec;

import java.util.ArrayList;
import java.util.List;

public class BottleSpecDao {

    private final SQLiteDatabase db;
    public static final String TABLE = AppDatabaseHelper.TABLE_BOTTLE_SPEC;

    public BottleSpecDao(SQLiteDatabase db) {
        this.db = db;
    }

    public long insert(BottleSpec b) {
        ContentValues v = new ContentValues();
        v.put("label", b.label);
        v.put("volumeMl", b.volumeMl);
        v.put("caffeineMgPerServing", b.caffeineMgPerServing);
        v.put("coffeeId", b.coffeeId);
        return db.insert(TABLE, null, v);
    }

    public BottleSpec getById(long id) {
        String[] cols = {"bottleSpecId", "label", "volumeMl", "caffeineMgPerServing", "coffeeId"};
        Cursor c = db.query(TABLE, cols, "bottleSpecId = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        try {
            if (c.moveToFirst()) {
                BottleSpec b = new BottleSpec();
                b.bottleSpecId = c.getLong(0);
                b.label = c.getString(1);
                b.volumeMl = c.getDouble(2);
                b.caffeineMgPerServing = c.getDouble(3);
                b.coffeeId = c.getLong(4);
                return b;
            }
            return null;
        } finally {
            c.close();
        }
    }

    public List<BottleSpec> getForCoffee(long coffeeId) {
        List<BottleSpec> list = new ArrayList<>();
        String[] cols = {"bottleSpecId", "label", "volumeMl", "caffeineMgPerServing", "coffeeId"};
        Cursor c = db.query(TABLE, cols, "coffeeId = ?",
                new String[]{String.valueOf(coffeeId)}, null, null, "label ASC");
        try {
            while (c.moveToNext()) {
                BottleSpec b = new BottleSpec();
                b.bottleSpecId = c.getLong(0);
                b.label = c.getString(1);
                b.volumeMl = c.getDouble(2);
                b.caffeineMgPerServing = c.getDouble(3);
                b.coffeeId = c.getLong(4);
                list.add(b);
            }
        } finally {
            c.close();
        }
        return list;
    }
}
