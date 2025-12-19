package service;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private List<User> users;
    private User currentUser;

    public AuthService() {
        this.users = new ArrayList<>();
        this.currentUser = null;
    }

    public boolean login(String email, String password) {
        for (User user : users) {
            if (user.login(email, password)) {
                currentUser = user;
                System.out.println("Login successful. Welcome, " + user.getName() + "!");
                return true;
            }
        }
        System.out.println("Invalid email or password.");
        return false;
    }

    public void logout() {
        if (currentUser != null) {
            currentUser.logout();
            currentUser = null;
        }
    }

    public void registerUser(User user) {
        users.add(user);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Get all users in the system
     * Used by administrator to view all registered users
     * @return List of all User objects in the system
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users); // Return a copy to prevent modification
    }

    /**
     * Remove a user from the system by email
     * Administrator function to delete user accounts
     * @param email The email of the user to remove
     * @return true if user was removed, false if not found
     */
    public boolean removeUser(String email) {
        User userToRemove = null;

        // Find user by email
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                userToRemove = user;
                break;
            }
        }

        if (userToRemove == null) {
            System.out.println("Error: User with email " + email + " not found");
            return false;
        }

        // Prevent removal of current logged in user
        if (currentUser != null && currentUser.getEmail().equalsIgnoreCase(email)) {
            System.out.println("Error: Cannot remove currently logged in user");
            return false;
        }

        boolean removed = users.remove(userToRemove);
        if (removed) {
            System.out.println("User " + userToRemove.getName() + " removed successfully.");
        }
        return removed;
    }

    /**
     * Find a user by email address
     * @param email The email to search for
     * @return User object if found, null otherwise
     */
    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Register a new student (public registration)
     * This can be used for self-registration by students
     * @param name Full name of student
     * @param email Email address (must be unique)
     * @param password Password
     * @param level Academic level (1-4)
     * @param major Major field of study
     * @return true if registration successful, false otherwise
     */
    public boolean registerNewStudent(String name, String email, String password, int level, String major) {
        // Check if email already exists
        if (findUserByEmail(email) != null) {
            System.out.println("Error: Email " + email + " is already registered");
            return false;
        }

        // Validate input
        if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            System.out.println("Error: All fields are required");
            return false;
        }

        // Generate new user ID
        int newId = users.size() + 1;

        // Create and register new student
        Student newStudent = new Student(newId, name, email, password, level, major);
        users.add(newStudent);
        System.out.println("Student " + name + " registered successfully.");
        return true;
    }

    /**
     * Register a new instructor (admin function only)
     * @param name Full name of instructor
     * @param email Email address
     * @param password Password
     * @param department Department name
     * @return true if successful, false otherwise
     */
    public boolean registerNewInstructor(String name, String email, String password, String department) {
        if (findUserByEmail(email) != null) {
            System.out.println("Error: Email " + email + " is already registered");
            return false;
        }

        int newId = users.size() + 1;
        Instructor newInstructor = new Instructor(newId, name, email, password, department);
        users.add(newInstructor);
        System.out.println("Instructor " + name + " registered successfully.");
        return true;
    }


}