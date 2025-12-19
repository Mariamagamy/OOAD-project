package model;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private int level;
    private String major;
    private List<Registration> registrations;
    private List<SpecialRequest> specialRequests;

    public Student(int userID, String name, String email, String password, int level, String major) {
        super(userID, name, email, password, "STUDENT");
        this.level = level;
        this.major = major;
        this.registrations = new ArrayList<>();
        this.specialRequests = new ArrayList<>();
    }

    public List<CourseOffering> browseCourses(List<CourseOffering> allOfferings) {
        return allOfferings;
    }

    public boolean registerCourse(CourseOffering offering) {
        Registration registration = new Registration(
                registrations.size() + 1,
                this,
                offering,
                "pending"
        );
        registrations.add(registration);
        return true;
    }

    public boolean dropCourse(int registrationID) {
        return registrations.removeIf(r -> r.getRegistrationID() == registrationID);
    }

    public Schedule viewSchedule() {
        // Simplified implementation
        return new Schedule("Mon, Wed, Fri", "10:00", "11:00");
    }

    public boolean submitSpecialRequest(CourseOffering offering, String reason) {
        SpecialRequest request = new SpecialRequest(
                specialRequests.size() + 1,
                this,
                offering,
                reason,
                "pending"
        );
        specialRequests.add(request);
        return true;
    }

    // Getters and Setters
    public int getLevel() { return level; }
    public String getMajor() { return major; }
    public List<Registration> getRegistrations() { return registrations; }
    public List<SpecialRequest> getSpecialRequests() { return specialRequests; }
}