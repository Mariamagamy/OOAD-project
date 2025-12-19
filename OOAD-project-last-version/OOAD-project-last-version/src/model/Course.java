package model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseCode;
    private String title;
    private int credits;
    private String description;
    private List<CourseOffering> offerings;
    private List<Course> prerequisites;

    public Course(String courseCode, String title, int credits, String description) {
        this.courseCode = courseCode;
        this.title = title;
        this.credits = credits;
        this.description = description;
        this.offerings = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
    }

    public String getCourseInfo() {
        return String.format("Course Code: %s\nTitle: %s\nCredits: %d\nDescription: %s",
                courseCode, title, credits, description);
    }

    public void addOffering(CourseOffering offering) {
        offerings.add(offering);
    }

    public void addPrerequisite(Course prerequisite) {
        prerequisites.add(prerequisite);
    }

    // Getters and Setters
    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getDescription() { return description; }
    public List<CourseOffering> getOfferings() { return offerings; }
    public List<Course> getPrerequisites() { return prerequisites; }
}