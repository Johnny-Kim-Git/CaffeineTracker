package com.example.caffeinetracker.models;

public class Consumption {
    public long consumptionId;   // PK
    public long userId;          // FK -> User.userId
    public long bottleSpecId;    // FK -> BottleSpec.bottleSpecId

    public String consumptionTime; // ISO datetime string
    public double servings;        // e.g. 0.5, 1.0, 1.5
    public double caffeineMgCached; // precomputed caffeine (mg)

    public Consumption() {}

    public Consumption(long consumptionId, long userId, long bottleSpecId,
                       String consumptionTime, double servings,
                       double caffeineMgCached) {
        this.consumptionId = consumptionId;
        this.userId = userId;
        this.bottleSpecId = bottleSpecId;
        this.consumptionTime = consumptionTime;
        this.servings = servings;
        this.caffeineMgCached = caffeineMgCached;
    }
}
