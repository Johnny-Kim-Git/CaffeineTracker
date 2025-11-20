package com.example.caffeinetracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Repository = Single source of truth for all database access.
 *
 * It exposes DAOs so UI / algorithm code can simply do:
 * Repository repo = Repository.getInstance(context);
 * repo.coffeeDao.getAllCoffees();
 */
public class Repository {

    private static Repository instance;

    private final SQLiteDatabase db;

    // --- DAOs ---
    public final UserDao userDao;
    public final UserProfileDao userProfileDao;
    public final CoffeeDao coffeeDao;
    public final BottleSpecDao bottleSpecDao;
    public final ConsumptionDao consumptionDao;
    public final HealthSnapshotDao healthSnapshotDao;
    public final SleepSessionDao sleepSessionDao;

    // Private constructor ensures SINGLETON
    private Repository(Context context) {
        AppDatabaseHelper helper = new AppDatabaseHelper(context.getApplicationContext());
        this.db = helper.getWritableDatabase();

        // Instantiate DAOs using the SAME database
        userDao = new UserDao(db);
        userProfileDao = new UserProfileDao(db);
        coffeeDao = new CoffeeDao(db);  // uses its own helper internally—fine for read use
        bottleSpecDao = new BottleSpecDao(db);
        consumptionDao = new ConsumptionDao(db);
        healthSnapshotDao = new HealthSnapshotDao(db);
        sleepSessionDao = new SleepSessionDao(db);
    }

    /**
     * Returns the single Repository instance.
     */
    public static synchronized Repository getInstance(Context context) {
        if (instance == null) {
            instance = new Repository(context);
        }
        return instance;
    }

    /**
     * Get raw db if algorithm needs direct queries.
     */
    public SQLiteDatabase getDb() {
        return db;
    }
}
