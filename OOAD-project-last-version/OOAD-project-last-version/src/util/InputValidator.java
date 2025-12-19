package util;

import java.util.regex.Pattern;

public class InputValidator {

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidCourseCode(String courseCode) {
        String codeRegex = "^[A-Z]{2,4}\\d{3}$";
        Pattern pattern = Pattern.compile(codeRegex);
        return courseCode != null && pattern.matcher(courseCode).matches();
    }

    public static boolean isValidTimeFormat(String time) {
        String timeRegex = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
        Pattern pattern = Pattern.compile(timeRegex);
        return time != null && pattern.matcher(time).matches();
    }

    public static boolean isPositiveInteger(String str) {
        try {
            int num = Integer.parseInt(str);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}