package com.example.caffeinetracker.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.caffeinetracker.models.Consumption;

import java.util.ArrayList;
import java.util.List;

public class ConsumptionDao {

    private final SQLiteDatabase db;
    public static final String TABLE = AppDatabaseHelper.TABLE_CONSUMPTION;

    public ConsumptionDao(SQLiteDatabase db) {
        this.db = db;
    }

    public long insert(Consumption cns) {
        ContentValues v = new ContentValues();
        v.put("userId", cns.userId);
        v.put("bottleSpecId", cns.bottleSpecId);
        v.put("consumptionTime", cns.consumptionTime);
        v.put("servings", cns.servings);
        v.put("caffeineMgCached", cns.caffeineMgCached);
        return db.insert(TABLE, null, v);
    }

    public List<Consumption> getForUser(long userId) {
        List<Consumption> list = new ArrayList<>();
        String[] cols = {
                "consumptionId", "userId", "bottleSpecId",
                "consumptionTime", "servings", "caffeineMgCached"
        };
        Cursor c = db.query(TABLE, cols, "userId = ?",
                new String[]{String.valueOf(userId)}, null, null,
                "consumptionTime DESC");
        try {
            while (c.moveToNext()) {
                Consumption x = new Consumption();
                x.consumptionId = c.getLong(0);
                x.userId = c.getLong(1);
                x.bottleSpecId = c.getLong(2);
                x.consumptionTime = c.getString(3);
                x.servings = c.getDouble(4);
                x.caffeineMgCached = c.getDouble(5);
                list.add(x);
            }
        } finally {
            c.close();
        }
        return list;
    }
}
