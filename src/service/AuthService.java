package service;

import model.User;
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
}