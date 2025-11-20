package com.example.caffeinetracker.models;

public class Coffee {
    public long coffeeId;      // PK
    public String name;        // "Latte", "Americano", ...
    public String brandName;   // "Starbucks", "Costa", ...
    public String flavor;      // "Matcha", "Vanilla", can be null

    public Coffee() {}

    public Coffee(long coffeeId, String name,
                  String brandName, String flavor) {
        this.coffeeId = coffeeId;
        this.name = name;
        this.brandName = brandName;
        this.flavor = flavor;
    }
}
