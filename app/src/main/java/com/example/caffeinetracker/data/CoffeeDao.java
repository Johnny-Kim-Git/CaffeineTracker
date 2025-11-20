package com.example.caffeinetracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.caffeinetracker.models.Coffee;

import java.util.ArrayList;
import java.util.List;

public class CoffeeDao {

    private final AppDatabaseHelper dbHelper;

    public CoffeeDao(Context context) {
        // Use application context to avoid leaking an activity
        this.dbHelper = new AppDatabaseHelper(context.getApplicationContext());
    }

    // ---------- Read ----------

    public List<Coffee> getAllCoffees() {
        List<Coffee> result = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    AppDatabaseHelper.TABLE_COFFEE,    // table
                    null,                               // all columns
                    null,
                    null,
                    null,
                    null,
                    "brandName ASC, name ASC"           // order by
            );

            int idxId        = cursor.getColumnIndexOrThrow("coffeeId");
            int idxName      = cursor.getColumnIndexOrThrow("name");
            int idxBrandName = cursor.getColumnIndexOrThrow("brandName");
            int idxFlavor    = cursor.getColumnIndexOrThrow("flavor");

            while (cursor.moveToNext()) {
                Coffee c = new Coffee();
                c.coffeeId   = cursor.getLong(idxId);
                c.name       = cursor.getString(idxName);
                c.brandName  = cursor.getString(idxBrandName);
                c.flavor     = cursor.isNull(idxFlavor) ? null : cursor.getString(idxFlavor);
                result.add(c);
            }
        } finally {
            if (cursor != null) cursor.close();
            // Don't close dbHelper here; it will be reused.
        }

        return result;
    }

    // Get all coffees for a specific brand (optional helper)
    public List<Coffee> getCoffeesByBrand(String brandName) {
        List<Coffee> result = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    AppDatabaseHelper.TABLE_COFFEE,
                    null,
                    "brandName = ?",
                    new String[]{ brandName },
                    null,
                    null,
                    "name ASC"
            );

            int idxId        = cursor.getColumnIndexOrThrow("coffeeId");
            int idxName      = cursor.getColumnIndexOrThrow("name");
            int idxBrandName = cursor.getColumnIndexOrThrow("brandName");
            int idxFlavor    = cursor.getColumnIndexOrThrow("flavor");

            while (cursor.moveToNext()) {
                Coffee c = new Coffee();
                c.coffeeId   = cursor.getLong(idxId);
                c.name       = cursor.getString(idxName);
                c.brandName  = cursor.getString(idxBrandName);
                c.flavor     = cursor.isNull(idxFlavor) ? null : cursor.getString(idxFlavor);
                result.add(c);
            }
        } finally {
            if (cursor != null) cursor.close();
        }

        return result;
    }

    // ---------- Create ----------

    public long insertCoffee(Coffee coffee) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", coffee.name);
        values.put("brandName", coffee.brandName);
        values.put("flavor", coffee.flavor);

        // returns new row ID or -1 on error
        return db.insert(AppDatabaseHelper.TABLE_COFFEE, null, values);
    }

    // ---------- Update ----------

    public int updateCoffee(Coffee coffee) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", coffee.name);
        values.put("brandName", coffee.brandName);
        values.put("flavor", coffee.flavor);

        return db.update(
                AppDatabaseHelper.TABLE_COFFEE,
                values,
                "coffeeId = ?",
                new String[]{ String.valueOf(coffee.coffeeId) }
        );
    }

    // ---------- Delete ----------

    public int deleteCoffee(long coffeeId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.delete(
                AppDatabaseHelper.TABLE_COFFEE,
                "coffeeId = ?",
                new String[]{ String.valueOf(coffeeId) }
        );
    }
}
