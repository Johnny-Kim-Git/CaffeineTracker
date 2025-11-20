package com.example.caffeinetracker.models;

public class HealthSnapshot {
    public long snapshotId;        // PK
    public long userId;            // FK -> User.userId

    public int day;                // experimental day (1..10)
    public String timepoint;       // "T0", "T30", "T90", "T120", "T180", "night23"

    public double mood;            // 1.0 - 10.0 (higher = better)
    public double stress;          // 1.0 - 10.0 (higher = more stressed)
    public double hrvMs;           // HRV_ms, usually 15-150
    public double hrBpm;           // HR_bpm, usually 45-110

    public String tag;             // extra notes (nullable)
    public String condition;       // "caffeine" / "control"
    public String session;         // "AM", "PM", or empty
    public String intakePattern;   // "fast", "slow", "split fast", "split slow", "empty"

    public HealthSnapshot() {}

    public HealthSnapshot(long snapshotId, long userId, int day,
                          String timepoint, double mood, double stress,
                          double hrvMs, double hrBpm, String tag,
                          String condition, String session, String intakePattern) {
        this.snapshotId = snapshotId;
        this.userId = userId;
        this.day = day;
        this.timepoint = timepoint;
        this.mood = mood;
        this.stress = stress;
        this.hrvMs = hrvMs;
        this.hrBpm = hrBpm;
        this.tag = tag;
        this.condition = condition;
        this.session = session;
        this.intakePattern = intakePattern;
    }
}
