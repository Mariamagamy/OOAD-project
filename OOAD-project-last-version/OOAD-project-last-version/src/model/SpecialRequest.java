package model;

public class SpecialRequest {
    private int requestID;
    private Student student;
    private CourseOffering courseOffering;
    private String reason;
    private String status;

    public SpecialRequest(int requestID, Student student, CourseOffering courseOffering,
                          String reason, String status) {
        this.requestID = requestID;
        this.student = student;
        this.courseOffering = courseOffering;
        this.reason = reason;
        this.status = status;
    }

    public String getRequestInfo() {
        return String.format("Request ID: %d\nStudent: %s\nCourse: %s\nReason: %s\nStatus: %s",
                requestID, student.getName(),
                courseOffering.getCourse().getTitle(), reason, status);
    }

    // Getters and Setters
    public int getRequestID() { return requestID; }
    public Student getStudent() { return student; }
    public CourseOffering getCourseOffering() { return courseOffering; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}