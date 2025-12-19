package model;

import java.util.ArrayList;
import java.util.List;

public class CourseOffering {
    private int offeringID;
    private Course course;
    private Instructor instructor;
    private Semester semester;
    private Schedule schedule;
    private int capacity;
    private int currentEnrollment;
    private List<Registration> registrations;

    public CourseOffering(int offeringID, Course course, Instructor instructor,
                          Semester semester, Schedule schedule, int capacity) {
        this.offeringID = offeringID;
        this.course = course;
        this.instructor = instructor;
        this.semester = semester;
        this.schedule = schedule;
        this.capacity = capacity;
        this.currentEnrollment = 0;
        this.registrations = new ArrayList<>();

        // Add this offering to instructor's list
        instructor.addCourseOffering(this);
    }

    public String getOfferingInfo() {
        return String.format("Offering ID: %d\nCourse: %s\nInstructor: %s\nSchedule: %s\nCapacity: %d/%d",
                offeringID, course.getTitle(), instructor.getName(),
                schedule.getSchedule(), currentEnrollment, capacity);
    }

    public void increaseEnrollment() {
        if (currentEnrollment < capacity) {
            currentEnrollment++;
        }
    }

    public void decreaseEnrollment() {
        if (currentEnrollment > 0) {
            currentEnrollment--;
        }
    }

    public boolean checkAvailability() {
        return currentEnrollment < capacity;
    }

    public void addRegistration(Registration registration) {
        registrations.add(registration);
        increaseEnrollment();
    }

    // Getters and Setters
    public int getOfferingID() { return offeringID; }
    public Course getCourse() { return course; }
    public Instructor getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public Schedule getSchedule() { return schedule; }
    public int getCapacity() { return capacity; }
    public int getCurrentEnrollment() { return currentEnrollment; }
}