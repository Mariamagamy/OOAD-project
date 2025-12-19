package model;

public class RegistrationRules {
    private int maxCredits;
    private boolean allowConflicts;
    private boolean allowPrereqOverride;

    public RegistrationRules(int maxCredits, boolean allowConflicts, boolean allowPrereqOverride) {
        this.maxCredits = maxCredits;
        this.allowConflicts = allowConflicts;
        this.allowPrereqOverride = allowPrereqOverride;
    }

    public String getRules() {
        return String.format("Maximum Credits: %d\nAllow Conflicts: %s\nAllow Prerequisite Override: %s",
                maxCredits, allowConflicts, allowPrereqOverride);
    }

    // Getters and Setters
    public int getMaxCredits() { return maxCredits; }
    public boolean isAllowConflicts() { return allowConflicts; }
    public boolean isAllowPrereqOverride() { return allowPrereqOverride; }

    public void setMaxCredits(int maxCredits) { this.maxCredits = maxCredits; }
    public void setAllowConflicts(boolean allowConflicts) { this.allowConflicts = allowConflicts; }
    public void setAllowPrereqOverride(boolean allowPrereqOverride) { this.allowPrereqOverride = allowPrereqOverride; }
}