package model;

public class Administrator extends User {
    private String adminRole;
    private RegistrationRules registrationRules;

    public Administrator(int userID, String name, String email, String password, String adminRole) {
        super(userID, name, email, password, "ADMIN");
        this.adminRole = adminRole;
        this.registrationRules = new RegistrationRules(18, false, false);
    }

    public void manageCourses() {
        System.out.println("Managing courses...");
    }

    public void manageUsers() {
        System.out.println("Managing users...");
    }

    public void setRegistrationRules(RegistrationRules rules) {
        this.registrationRules = rules;
    }

    public void openRegistration(String term) {
        System.out.println("Registration opened for term: " + term);
    }

    public void closeRegistration(String term) {
        System.out.println("Registration closed for term: " + term);
    }

    // Getters and Setters
    public String getAdminRole() { return adminRole; }
    public RegistrationRules getRegistrationRules() { return registrationRules; }
}