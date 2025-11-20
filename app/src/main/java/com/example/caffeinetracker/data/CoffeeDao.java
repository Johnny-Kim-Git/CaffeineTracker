package com.example.caffeinetracker.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.caffeinetracker.models.Coffee;

import java.util.ArrayList;
import java.util.List;

public class CoffeeDao {

    private final SQLiteDatabase db;
    public static final String TABLE = AppDatabaseHelper.TABLE_COFFEE;

    public CoffeeDao(SQLiteDatabase db) {
        this.db = db;
    }

    // ---------- Create ----------
    public long insert(Coffee coffee) {
        ContentValues values = new ContentValues();
        values.put("name", coffee.name);
        values.put("brandName", coffee.brandName);
        values.put("flavor", coffee.flavor);
        return db.insert(TABLE, null, values);
    }

    // ---------- Read ----------
    public List<Coffee> getAllCoffees() {
        List<Coffee> result = new ArrayList<>();

        Cursor cursor = db.query(
                TABLE,
                null,
                null,
                null,
                null,
                null,
                "brandName ASC, name ASC"
        );

        try {
            int idxId        = cursor.getColumnIndexOrThrow("coffeeId");
            int idxName      = cursor.getColumnIndexOrThrow("name");
            int idxBrandName = cursor.getColumnIndexOrThrow("brandName");
            int idxFlavor    = cursor.getColumnIndexOrThrow("flavor");

            while (cursor.moveToNext()) {
                Coffee c = new Coffee();
                c.coffeeId = cursor.getLong(idxId);
                c.name = cursor.getString(idxName);
                c.brandName = cursor.getString(idxBrandName);
                c.flavor = cursor.isNull(idxFlavor) ? null : cursor.getString(idxFlavor);
                result.add(c);
            }
        } finally {
            cursor.close();
        }

        return result;
    }

    public Coffee getById(long id) {
        Cursor cursor = db.query(
                TABLE,
                null,
                "coffeeId = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        try {
            if (cursor.moveToFirst()) {
                Coffee c = new Coffee();
                c.coffeeId = cursor.getLong(cursor.getColumnIndexOrThrow("coffeeId"));
                c.name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                c.brandName = cursor.getString(cursor.getColumnIndexOrThrow("brandName"));
                c.flavor = cursor.getString(cursor.getColumnIndexOrThrow("flavor"));
                return c;
            }
            return null;
        } finally {
            cursor.close();
        }
    }

    public List<Coffee> getByBrand(String brandName) {
        List<Coffee> list = new ArrayList<>();

        Cursor cursor = db.query(
                TABLE,
                null,
                "brandName = ?",
                new String[]{brandName},
                null,
                null,
                "name ASC"
        );

        try {
            while (cursor.moveToNext()) {
                Coffee c = new Coffee();
                c.coffeeId = cursor.getLong(cursor.getColumnIndexOrThrow("coffeeId"));
                c.name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                c.brandName = cursor.getString(cursor.getColumnIndexOrThrow("brandName"));
                c.flavor = cursor.getString(cursor.getColumnIndexOrThrow("flavor"));
                list.add(c);
            }
        } finally {
            cursor.close();
        }

        return list;
    }

    // ---------- Update ----------
    public int update(Coffee coffee) {
        ContentValues values = new ContentValues();
        values.put("name", coffee.name);
        values.put("brandName", coffee.brandName);
        values.put("flavor", coffee.flavor);

        return db.update(
                TABLE,
                values,
                "coffeeId = ?",
                new String[]{String.valueOf(coffee.coffeeId)}
        );
    }

    // ---------- Delete ----------
    public int delete(long id) {
        return db.delete(
                TABLE,
                "coffeeId = ?",
                new String[]{String.valueOf(id)}
        );
    }
}
