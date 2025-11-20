package com.example.caffeinetracker.models;

public class BottleSpec {
    public long bottleSpecId;        // PK
    public String label;             // "Grande", "Tall", "Small mug", ...
    public double volumeMl;          // e.g. 100.0
    public double caffeineMgPerServing; // e.g. 100.0
    public long coffeeId;            // FK -> Coffee.coffeeId

    public BottleSpec() {}

    public BottleSpec(long bottleSpecId, String label,
                      double volumeMl, double caffeineMgPerServing,
                      long coffeeId) {
        this.bottleSpecId = bottleSpecId;
        this.label = label;
        this.volumeMl = volumeMl;
        this.caffeineMgPerServing = caffeineMgPerServing;
        this.coffeeId = coffeeId;
    }
}
