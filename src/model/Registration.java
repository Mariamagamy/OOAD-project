package model;

import java.time.LocalDateTime;

public class Registration {
    private int registrationID;
    private Student student;
    private CourseOffering courseOffering;
    private String status;
    private LocalDateTime timestamp;

    public Registration(int registrationID, Student student, CourseOffering courseOffering, String status) {
        this.registrationID = registrationID;
        this.student = student;
        this.courseOffering = courseOffering;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getRegistrationInfo() {
        return String.format("Registration ID: %d\nStudent: %s\nCourse: %s\nStatus: %s\nTime: %s",
                registrationID, student.getName(),
                courseOffering.getCourse().getTitle(), status, timestamp);
    }

    // Getters and Setters
    public int getRegistrationID() { return registrationID; }
    public Student getStudent() { return student; }
    public CourseOffering getCourseOffering() { return courseOffering; }
    public String getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public void setStatus(String status) { this.status = status; }
}