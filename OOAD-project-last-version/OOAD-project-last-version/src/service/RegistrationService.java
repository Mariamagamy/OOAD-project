package service;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationService {
    private CourseService courseService;
    private List<Registration> allRegistrations;
    private List<SpecialRequest> specialRequests;
    private List<Notification> notifications;

    public RegistrationService(CourseService courseService) {
        this.courseService = courseService;
        this.allRegistrations = new ArrayList<>();
        this.specialRequests = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public RegistrationResult registerCourse(Student student, int offeringID) {
        CourseOffering offering = courseService.findOfferingById(offeringID);

        if (offering == null) {
            return new RegistrationResult(false, "Course offering not found.");
        }

        // Check prerequisites
        if (!courseService.checkPrerequisites(student, offering.getCourse())) {
            sendNotification(student, "Registration failed: Prerequisites not met for " +
                    offering.getCourse().getTitle());
            return new RegistrationResult(false, "Prerequisites not met.");
        }

        // Check capacity
        if (!offering.checkAvailability()) {
            sendNotification(student, "Registration failed: No capacity available for " +
                    offering.getCourse().getTitle());
            return new RegistrationResult(false, "No capacity available.");
        }

        // Check schedule conflicts
        if (courseService.checkScheduleConflict(student, offering)) {
            sendNotification(student, "Registration failed: Schedule conflict for " +
                    offering.getCourse().getTitle());
            return new RegistrationResult(false, "Schedule conflict.");
        }

        // Create registration
        Registration registration = new Registration(
                allRegistrations.size() + 1,
                student,
                offering,
                "registered"
        );

        allRegistrations.add(registration);
        offering.addRegistration(registration);
        student.registerCourse(offering);

        sendNotification(student, "Successfully registered for " + offering.getCourse().getTitle());
        return new RegistrationResult(true, "Registration successful!");
    }

    public boolean dropCourse(Student student, int registrationID) {
        for (Registration reg : student.getRegistrations()) {
            if (reg.getRegistrationID() == registrationID) {
                reg.setStatus("dropped");
                reg.getCourseOffering().decreaseEnrollment();
                sendNotification(student, "Successfully dropped " +
                        reg.getCourseOffering().getCourse().getTitle());
                return true;
            }
        }
        return false;
    }

    public boolean submitSpecialRequest(Student student, int offeringID, String reason) {
        CourseOffering offering = courseService.findOfferingById(offeringID);
        if (offering == null) return false;

        SpecialRequest request = new SpecialRequest(
                specialRequests.size() + 1,
                student,
                offering,
                reason,
                "pending"
        );

        specialRequests.add(request);
        offering.getInstructor().addSpecialRequest(request);
        student.submitSpecialRequest(offering, reason);

        sendNotification(student, "Special request submitted for " + offering.getCourse().getTitle());
        sendNotification(offering.getInstructor(), "New special request from " + student.getName());

        return true;
    }

    private void sendNotification(User user, String message) {
        Notification notification = new Notification(
                notifications.size() + 1,
                user,
                message
        );
        notifications.add(notification);
        notification.showNotification();
    }

    public List<Notification> getUserNotifications(User user) {
        List<Notification> userNotifications = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getUser().getUserID() == user.getUserID()) {
                userNotifications.add(notification);
            }
        }
        return userNotifications;
    }

    // Helper class for registration results
    public static class RegistrationResult {
        private boolean success;
        private String message;

        public RegistrationResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
    }

    /**
     * Get all students registered in a specific course offering
     * This method queries all registrations and returns students
     * who are successfully registered in the given course
     * @param offeringID The ID of the course offering to check
     * @return List of Student objects registered in the course
     */
    public List<Student> getStudentsInCourse(int offeringID) {
        List<Student> students = new ArrayList<>();

        // Loop through all registrations in the system
        for (Registration reg : allRegistrations) {
            // Check if registration is for the requested course AND status is registered
            if (reg.getCourseOffering().getOfferingID() == offeringID &&
                    reg.getStatus().equals("registered")) {
                students.add(reg.getStudent());
            }
        }

        return students;
    }

    /**
     * Get course offering by ID
     * Helper method to find a course offering
     * @param offeringID The ID to search for
     * @return CourseOffering object or null if not found
     */
    public CourseOffering getCourseOffering(int offeringID) {
        for (CourseOffering offering : courseService.getCourseOfferings()) {
            if (offering.getOfferingID() == offeringID) {
                return offering;
            }
        }
        return null;
    }
}