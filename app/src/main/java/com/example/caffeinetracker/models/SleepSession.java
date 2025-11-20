package com.example.caffeinetracker.models;

public class SleepSession {
    public long sleepId;        // PK
    public long userId;         // FK -> User.userId

    public int day;             // experimental day (1..10)
    public int sleepScore;      // 1-10 (higher = better)
    public int remMin;          // REM_min
    public int coreMin;         // core_min (light/core sleep)
    public int deepMin;         // deep_min (slow-wave sleep)
    public int totalSleepMin;   // total_sleep_min (REM + core + deep)
    public String sleepNotes;   // text notes, can be empty

    public SleepSession() {}

    public SleepSession(long sleepId, long userId, int day,
                        int sleepScore, int remMin, int coreMin,
                        int deepMin, int totalSleepMin,
                        String sleepNotes) {
        this.sleepId = sleepId;
        this.userId = userId;
        this.day = day;
        this.sleepScore = sleepScore;
        this.remMin = remMin;
        this.coreMin = coreMin;
        this.deepMin = deepMin;
        this.totalSleepMin = totalSleepMin;
        this.sleepNotes = sleepNotes;
    }
}
