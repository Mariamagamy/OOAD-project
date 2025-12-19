package model;

public class Schedule {
    private String days;
    private String startTime;
    private String endTime;

    public Schedule(String days, String startTime, String endTime) {
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getSchedule() {
        return String.format("%s: %s - %s", days, startTime, endTime);
    }

    public boolean conflictsWith(Schedule other) {
        return this.days.equals(other.days) &&
                this.startTime.equals(other.startTime);
    }

    // Getters and Setters
    public String getDays() { return days; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
}