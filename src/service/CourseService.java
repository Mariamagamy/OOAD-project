package service;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private List<Course> courses;
    private List<CourseOffering> courseOfferings;

    public CourseService() {
        this.courses = new ArrayList<>();
        this.courseOfferings = new ArrayList<>();
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Create sample courses
        Course cs101 = new Course("CS101", "Introduction to Programming", 3,
                "Basic programming concepts using Java");
        Course cs201 = new Course("CS201", "Data Structures", 3,
                "Fundamental data structures and algorithms");
        Course cs301 = new Course("CS301", "Software Engineering", 3,
                "Software development methodologies");

        cs201.addPrerequisite(cs101);
        cs301.addPrerequisite(cs201);

        courses.add(cs101);
        courses.add(cs201);
        courses.add(cs301);
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public List<CourseOffering> getCourseOfferings() {
        return courseOfferings;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addCourseOffering(CourseOffering offering) {
        courseOfferings.add(offering);
        offering.getCourse().addOffering(offering);
    }

    public CourseOffering findOfferingById(int offeringID) {
        for (CourseOffering offering : courseOfferings) {
            if (offering.getOfferingID() == offeringID) {
                return offering;
            }
        }
        return null;
    }

    public boolean checkPrerequisites(Student student, Course course) {
        for (Course prereq : course.getPrerequisites()) {
            boolean hasPrereq = false;
            for (Registration reg : student.getRegistrations()) {
                if (reg.getCourseOffering().getCourse().getCourseCode().equals(prereq.getCourseCode())
                        && reg.getStatus().equals("registered")) {
                    hasPrereq = true;
                    break;
                }
            }
            if (!hasPrereq) {
                return false;
            }
        }
        return true;
    }

    public boolean checkScheduleConflict(Student student, CourseOffering newOffering) {
        for (Registration reg : student.getRegistrations()) {
            if (reg.getStatus().equals("registered")) {
                Schedule existingSchedule = reg.getCourseOffering().getSchedule();
                Schedule newSchedule = newOffering.getSchedule();
                if (existingSchedule.conflictsWith(newSchedule)) {
                    return true;
                }
            }
        }
        return false;
    }
}