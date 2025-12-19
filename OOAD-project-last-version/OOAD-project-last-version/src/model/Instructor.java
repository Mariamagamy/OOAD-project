package model;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends User {
    private String department;
    private List<CourseOffering> courseOfferings;
    private List<SpecialRequest> pendingRequests;

    public Instructor(int userID, String name, String email, String password, String department) {
        super(userID, name, email, password, "INSTRUCTOR");
        this.department = department;
        this.courseOfferings = new ArrayList<>();
        this.pendingRequests = new ArrayList<>();
    }

    public List<CourseOffering> viewCourses() {
        return courseOfferings;
    }

    public List<Student> viewStudents(CourseOffering offering) {
        List<Student> students = new ArrayList<>();
        // Implementation would query registrations
        return students;
    }

    public void approveRequest(int requestID) {
        for (SpecialRequest request : pendingRequests) {
            if (request.getRequestID() == requestID) {
                request.setStatus("approved");
                break;
            }
        }
    }

    public void rejectRequest(int requestID) {
        for (SpecialRequest request : pendingRequests) {
            if (request.getRequestID() == requestID) {
                request.setStatus("rejected");
                break;
            }
        }
    }

    public void addCourseOffering(CourseOffering offering) {
        courseOfferings.add(offering);
    }

    public void addSpecialRequest(SpecialRequest request) {
        pendingRequests.add(request);
    }

    // Getters and Setters
    public String getDepartment() { return department; }
    public List<SpecialRequest> getPendingRequests() { return pendingRequests; }

}