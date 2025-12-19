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

    /**
     * Helper method to get valid integer input from user
     * @param prompt Message to display to user
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return Valid integer input
     */
    private int getValidIntegerInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }

            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }


    private void showStudentMenu(Student student) {
        while (authService.getCurrentUser() != null && authService.getCurrentUser().getRole().equals("STUDENT")) {
            System.out.println("\n=== Student Menu ===");
            System.out.println("1. Browse Courses");
            System.out.println("2. Register for Course");
            System.out.println("3. View My Schedule");
            System.out.println("4. Submit Special Request");
            System.out.println("5. View Notifications");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");

            try {
                String input = scanner.nextLine().trim();


                if (input.isEmpty()) {
                    System.out.println("Please enter a number (1-6).");
                    continue;
                }

                int choice = Integer.parseInt(input);

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
                        System.out.println("Logged out successfully.");
                        return; // خروج من الدالة
                    default:
                        System.out.println("Invalid option. Please choose 1-6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
            }
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

        List<Registration> registrations = student.getRegistrations();

        if (registrations.isEmpty()) {
            System.out.println("No registered courses.");
            return;
        }

        System.out.println("Registered Courses:");
        System.out.println("=".repeat(50));
        System.out.println(String.format("%-20s %-25s %-15s",
                "Course", "Schedule", "Status"));
        System.out.println("=".repeat(50));

        for (Registration reg : registrations) {
            if (reg.getStatus().equals("registered")) {
                CourseOffering offering = reg.getCourseOffering();
                System.out.println(String.format("%-20s %-25s %-15s",
                        offering.getCourse().getTitle(),
                        offering.getSchedule().getSchedule(),
                        reg.getStatus()));
            }
        }

        System.out.println("=".repeat(50));
        System.out.println("Total registered courses: " +
                registrations.stream().filter(r -> r.getStatus().equals("registered")).count());
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
        while (authService.getCurrentUser() != null && authService.getCurrentUser().getRole().equals("INSTRUCTOR")) {
            System.out.println("\n=== Instructor Menu ===");
            System.out.println("1. View My Courses");
            System.out.println("2. View Pending Requests");
            System.out.println("3. Approve Request");
            System.out.println("4. Reject Request");
            System.out.println("5. View Notifications");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");

            try {
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Please enter a number (1-6).");
                    continue;
                }

                int choice = Integer.parseInt(input);

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
                        System.out.println("Logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid option. Please choose 1-6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
            }
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
        System.out.println("4. Add New Course");
        System.out.println("5. Remove Course");
        System.out.println("6. View All Users");
        System.out.println("7. Add New User");
        System.out.println("8. Remove User");
        System.out.println("9. View System Status");
        System.out.println("10. Logout");
        System.out.print("Choose an option: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    manageRegistrationRules(admin);
                    break;
                case 2:
                    admin.openRegistration("Fall 2024");
                    System.out.println("Registration opened for Fall 2024");
                    break;
                case 3:
                    admin.closeRegistration("Fall 2024");
                    System.out.println("Registration closed for Fall 2024");
                    break;
                case 4:
                    addNewCourse();
                    break;
                case 5:
                    removeCourse();
                    break;
                case 6:
                    viewAllUsers();
                    break;
                case 7:
                    addNewUser();
                    break;
                case 8:
                    removeUser();
                    break;
                case 9:
                    viewSystemStatus();
                    break;
                case 10:
                    authService.logout();
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-10.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
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

    /**
     * Administrator function: Add a new course to the system
     * Prompts for course details and creates new course
     */
    private void addNewCourse() {
        System.out.println("\n=== Add New Course ===");

        System.out.print("Enter Course Code (e.g., CS101): ");
        String courseCode = scanner.nextLine().trim();

        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter Credits: ");
        int credits;
        try {
            credits = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid credits. Must be a number.");
            return;
        }

        System.out.print("Enter Description: ");
        String description = scanner.nextLine().trim();

        // Call CourseService to add the course
        Course newCourse = courseService.addCourse(courseCode, title, credits, description);
        if (newCourse != null) {
            System.out.println("Course added successfully!");
        }
    }

    /**
     * Administrator function: Remove a course from the system
     */
    private void removeCourse() {
        System.out.println("\n=== Remove Course ===");

        // Show available courses
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        System.out.println("Available Courses:");
        for (Course course : courses) {
            System.out.println("- " + course.getCourseCode() + ": " + course.getTitle());
        }

        System.out.print("\nEnter Course Code to remove: ");
        String courseCode = scanner.nextLine().trim();

        System.out.print("Are you sure you want to remove " + courseCode + "? (yes/no): ");
        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("yes")) {
            boolean removed = courseService.removeCourse(courseCode);
            if (removed) {
                System.out.println("Course removed successfully.");
            }
        } else {
            System.out.println("Operation cancelled.");
        }
    }

    /**
     * Administrator function: View all users in the system
     */
    private void viewAllUsers() {
        System.out.println("\n=== All System Users ===");

        List<User> users = authService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users in the system.");
            return;
        }

        System.out.println(String.format("%-5s %-20s %-25s %-15s %-15s",
                "ID", "Name", "Email", "Role", "Details"));
        System.out.println("-".repeat(85));

        for (User user : users) {
            String details = "";
            if (user instanceof Student) {
                Student s = (Student) user;
                details = "Level: " + s.getLevel() + ", Major: " + s.getMajor();
            } else if (user instanceof Instructor) {
                Instructor i = (Instructor) user;
                details = "Dept: " + i.getDepartment();
            } else if (user instanceof Administrator) {
                Administrator a = (Administrator) user;
                details = "Role: " + a.getAdminRole();
            }

            System.out.println(String.format("%-5d %-20s %-25s %-15s %-15s",
                    user.getUserID(), user.getName(), user.getEmail(), user.getRole(), details));
        }
        System.out.println("Total users: " + users.size());
    }

    /**
     * Administrator function: Add a new user to the system
     */
    private void addNewUser() {
        System.out.println("\n=== Add New User ===");
        System.out.println("1. Add Student");
        System.out.println("2. Add Instructor");
        System.out.println("3. Add Administrator");
        System.out.print("Choose user type: ");

        try {
            int userType = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine().trim();

            boolean success = false;

            switch (userType) {
                case 1: // Student
                    System.out.print("Enter Level (1-4): ");
                    int level = Integer.parseInt(scanner.nextLine().trim());

                    System.out.print("Enter Major: ");
                    String major = scanner.nextLine().trim();

                    // Call AuthService to register new student
                    success = authService.registerNewStudent(name, email, password, level, major);
                    break;

                case 2: // Instructor
                    System.out.print("Enter Department: ");
                    String department = scanner.nextLine().trim();

                    // Call AuthService to register new instructor
                    success = authService.registerNewInstructor(name, email, password, department);
                    break;

                case 3: // Administrator
                    System.out.print("Enter Admin Role: ");
                    String adminRole = scanner.nextLine().trim();

                    // For admin, create directly
                    int newId = authService.getAllUsers().size() + 1;
                    Administrator newAdmin = new Administrator(newId, name, email, password, adminRole);
                    authService.registerUser(newAdmin);
                    success = true;
                    System.out.println("Administrator " + name + " added successfully.");
                    break;

                default:
                    System.out.println("Invalid user type.");
                    return;
            }

            if (success) {
                System.out.println("User added successfully!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers where required.");
        }
    }

    /**
     * Administrator function: Remove a user from the system
     */
    private void removeUser() {
        System.out.println("\n=== Remove User ===");

        // Show all users
        List<User> users = authService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users in the system.");
            return;
        }

        System.out.println("Current Users:");
        for (User user : users) {
            System.out.println("- " + user.getEmail() + " (" + user.getName() + ", " + user.getRole() + ")");
        }

        System.out.print("\nEnter user email to remove: ");
        String email = scanner.nextLine().trim();

        // Prevent self-removal
        User currentUser = authService.getCurrentUser();
        if (currentUser != null && currentUser.getEmail().equalsIgnoreCase(email)) {
            System.out.println("Error: You cannot remove your own account while logged in.");
            return;
        }

        System.out.print("Are you sure you want to remove user " + email + "? (yes/no): ");
        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("yes")) {
            boolean removed = authService.removeUser(email);
            if (removed) {
                System.out.println("User removed successfully.");
            }
        } else {
            System.out.println("Operation cancelled.");
        }
    }

}