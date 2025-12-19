package model;

import java.time.LocalDateTime;

public class Notification {
    private int notificationID;
    private User user;
    private String message;
    private LocalDateTime date;
    private boolean read;

    public Notification(int notificationID, User user, String message) {
        this.notificationID = notificationID;
        this.user = user;
        this.message = message;
        this.date = LocalDateTime.now();
        this.read = false;
    }

    public void showNotification() {
        System.out.println("[" + date + "] " + message);
    }

    public void markAsRead() {
        this.read = true;
    }

    // Getters and Setters
    public int getNotificationID() { return notificationID; }
    public User getUser() { return user; }
    public String getMessage() { return message; }
    public LocalDateTime getDate() { return date; }
    public boolean isRead() { return read; }
}