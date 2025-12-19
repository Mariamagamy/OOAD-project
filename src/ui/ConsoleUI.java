package ui;

import model.*;
import service.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;
    private AuthService authService;
    private CourseService courseService;
    private RegistrationService registrationService;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.authService = new AuthService();
        this.courseService = new CourseService();
        this.registrationService = new RegistrationService(courseService);
        initializeUsers();
        initializeCourseOfferings();
    }

    private void initializeUsers() {
        // Create sample users
        Student student1 = new Student(1, "John Doe", "john@univ.edu", "password", 3, "Computer Science");
        Instructor instructor1 = new Instructor(2, "Dr. Smith", "smith@univ.edu", "password", "Computer Science");
        Administrator admin1 = new Administrator(3, "Admin User", "admin@univ.edu", "password", "System Admin");

        authService.registerUser(student1);
        authService.registerUser(instructor1);
        authService.registerUser(admin1);
    }

    private void initializeCourseOfferings() {
        // Get existing courses
        List<Course> courses = courseService.getAllCourses();

        if (courses.size() >= 3) {
            Course cs101 = courses.get(0);
            Course cs201 = courses.get(1);
            Course cs301 = courses.get(2);

            // Create instructor
            Instructor instructor = (Instructor) authService.getCurrentUser();
            if (instructor == null) {
                instructor = new Instructor(2, "Dr. Smith", "smith@univ.edu", "password", "Computer Science");
            }

            // Create semester
            Semester fall2024 = new Semester("Fall 2024",
                    LocalDate.of(2024, 9, 1),
                    LocalDate.of(2024, 12, 15));
            fall2024.openRegistration();

            // Create schedules
            Schedule schedule1 = new Schedule("Mon, Wed, Fri", "09:00", "10:00");
            Schedule schedule2 = new Schedule("Tue, Thu", "11:00", "12:30");
            Schedule schedule3 = new Schedule("Mon, Wed", "14:00", "15:30");

            // Create course offerings
            CourseOffering offering1 = new CourseOffering(1, cs101, instructor, fall2024, schedule1, 30);
            CourseOffering offering2 = new CourseOffering(2, cs201, instructor, fall2024, schedule2, 25);
            CourseOffering offering3 = new CourseOffering(3, cs301, instructor, fall2024, schedule3, 20);

            courseService.addCourseOffering(offering1);
            courseService.addCourseOffering(offering2);
            courseService.addCourseOffering(offering3);
        }
    }

    public void start() {
        System.out.println("=== Course Registration System ===");

        while (true) {
            if (!authService.isLoggedIn()) {
                showLoginMenu();
            } else {
                User currentUser = authService.getCurrentUser();
                System.out.println("\nWelcome, " + currentUser.getName() + "!");

                switch (currentUser.getRole()) {
                    case "STUDENT":
                        showStudentMenu((Student) currentUser);
                        break;
                    case "INSTRUCTOR":
                        showInstructorMenu((Instructor) currentUser);
                        break;
                    case "ADMIN":
                        showAdminMenu((Administrator) currentUser);
                        break;
                }
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n=== Login ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (authService.login(email, password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }

    private void showStudentMenu(Student student) {
        System.out.println("\n=== Student Menu ===");
        System.out.println("1. Browse Courses");
        System.out.println("2. Register for Course");
        System.out.println("3. View My Schedule");
        System.out.println("4. Submit Special Request");
        System.out.println("5. View Notifications");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                browseCourses();
                break;
            case 2:
                registerForCourse(student);
                break;
            case 3:
                viewStudentSchedule(student);
                break;
            case 4:
                submitSpecialRequest(student);
                break;
            case 5:
                viewNotifications(student);
                break;
            case 6:
                authService.logout();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void browseCourses() {
        System.out.println("\n=== Available Courses ===");
        List<CourseOffering> offerings = courseService.getCourseOfferings();

        for (CourseOffering offering : offerings) {
            System.out.println("ID: " + offering.getOfferingID());
            System.out.println(offering.getOfferingInfo());
            System.out.println("------------------------");
        }
    }

    private void registerForCourse(Student student) {
        System.out.println("\n=== Register for Course ===");
        browseCourses();

        System.out.print("Enter course offering ID: ");
        int offeringID = Integer.parseInt(scanner.nextLine());

        RegistrationService.RegistrationResult result =
                registrationService.registerCourse(student, offeringID);

        System.out.println(result.getMessage());
    }

    private void viewStudentSchedule(Student student) {
        System.out.println("\n=== My Schedule ===");
        Schedule schedule = student.viewSchedule();
        System.out.println("Schedule: " + schedule.getSchedule());

        System.out.println("\nRegistered Courses:");
        for (Registration reg : student.getRegistrations()) {
            if (reg.getStatus().equals("registered")) {
                System.out.println("- " + reg.getCourseOffering().getCourse().getTitle() +
                        " (" + reg.getCourseOffering().getSchedule().getSchedule() + ")");
            }
        }
    }

    private void submitSpecialRequest(Student student) {
        System.out.println("\n=== Submit Special Request ===");
        browseCourses();

        System.out.print("Enter course offering ID: ");
        int offeringID = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter reason for request: ");
        String reason = scanner.nextLine();

        boolean success = registrationService.submitSpecialRequest(student, offeringID, reason);

        if (success) {
            System.out.println("Special request submitted successfully.");
        } else {
            System.out.println("Failed to submit special request.");
        }
    }

    private void viewNotifications(User user) {
        System.out.println("\n=== Notifications ===");
        List<Notification> notifications = registrationService.getUserNotifications(user);

        if (notifications.isEmpty()) {
            System.out.println("No notifications.");
        } else {
            for (Notification notification : notifications) {
                notification.showNotification();
            }
        }
    }

    private void showInstructorMenu(Instructor instructor) {
        System.out.println("\n=== Instructor Menu ===");
        System.out.println("1. View My Courses");
        System.out.println("2. View Pending Requests");
        System.out.println("3. Approve Request");
        System.out.println("4. Reject Request");
        System.out.println("5. View Notifications");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                viewInstructorCourses(instructor);
                break;
            case 2:
                viewPendingRequests(instructor);
                break;
            case 3:
                processRequest(instructor, true);
                break;
            case 4:
                processRequest(instructor, false);
                break;
            case 5:
                viewNotifications(instructor);
                break;
            case 6:
                authService.logout();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void viewInstructorCourses(Instructor instructor) {
        System.out.println("\n=== My Courses ===");
        List<CourseOffering> courses = instructor.viewCourses();

        for (CourseOffering offering : courses) {
            System.out.println(offering.getOfferingInfo());
            System.out.println("------------------------");
        }
    }

    private void viewPendingRequests(Instructor instructor) {
        System.out.println("\n=== Pending Requests ===");
        List<SpecialRequest> requests = instructor.getPendingRequests();

        for (SpecialRequest request : requests) {
            if (request.getStatus().equals("pending")) {
                System.out.println(request.getRequestInfo());
                System.out.println("------------------------");
            }
        }
    }

    private void processRequest(Instructor instructor, boolean approve) {
        viewPendingRequests(instructor);

        System.out.print("Enter request ID: ");
        int requestID = Integer.parseInt(scanner.nextLine());

        if (approve) {
            instructor.approveRequest(requestID);
            System.out.println("Request approved.");
        } else {
            instructor.rejectRequest(requestID);
            System.out.println("Request rejected.");
        }
    }

    private void showAdminMenu(Administrator admin) {
        System.out.println("\n=== Administrator Menu ===");
        System.out.println("1. Manage Registration Rules");
        System.out.println("2. Open Registration");
        System.out.println("3. Close Registration");
        System.out.println("4. View System Status");
        System.out.println("5. Logout");
        System.out.print("Choose an option: ");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                manageRegistrationRules(admin);
                break;
            case 2:
                admin.openRegistration("Fall 2024");
                break;
            case 3:
                admin.closeRegistration("Fall 2024");
                break;
            case 4:
                viewSystemStatus();
                break;
            case 5:
                authService.logout();
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void manageRegistrationRules(Administrator admin) {
        System.out.println("\n=== Current Registration Rules ===");
        System.out.println(admin.getRegistrationRules().getRules());

        System.out.println("\nUpdate Rules:");
        System.out.print("Max Credits: ");
        int maxCredits = Integer.parseInt(scanner.nextLine());

        System.out.print("Allow Conflicts (true/false): ");
        boolean allowConflicts = Boolean.parseBoolean(scanner.nextLine());

        System.out.print("Allow Prerequisite Override (true/false): ");
        boolean allowPrereqOverride = Boolean.parseBoolean(scanner.nextLine());

        RegistrationRules newRules = new RegistrationRules(maxCredits, allowConflicts, allowPrereqOverride);
        admin.setRegistrationRules(newRules);

        System.out.println("Rules updated successfully.");
    }

    private void viewSystemStatus() {
        System.out.println("\n=== System Status ===");
        System.out.println("Total Courses: " + courseService.getAllCourses().size());
        System.out.println("Total Course Offerings: " + courseService.getCourseOfferings().size());
        System.out.println("Total Users: 3 (sample data)");
    }
}