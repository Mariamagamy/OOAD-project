package model;

import java.time.LocalDate;

public class Semester {
    private String termName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String registrationStatus;

    public Semester(String termName, LocalDate startDate, LocalDate endDate) {
        this.termName = termName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationStatus = "Closed"; // Default
    }

    public String getSemesterInfo() {
        return String.format("Term: %s\nStart Date: %s\nEnd Date: %s\nRegistration: %s",
                termName, startDate, endDate, registrationStatus);
    }

    public void openRegistration() {
        registrationStatus = "Open";
    }

    public void closeRegistration() {
        registrationStatus = "Closed";
    }

    // Getters and Setters
    public String getTermName() { return termName; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getRegistrationStatus() { return registrationStatus; }
}