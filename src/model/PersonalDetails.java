package model;

import java.io.Serializable;

public class PersonalDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fullName = "";
    private String email = "";
    private String phone = "";
    private String location = "";
    private String targetRole = "";
    private String summary = "";
    private String linkedIn = "";
    private String gitHub = "";

    public PersonalDetails() {}

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName != null ? fullName.trim() : ""; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email != null ? email.trim() : ""; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone != null ? phone.trim() : ""; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location != null ? location.trim() : ""; }

    public String getTargetRole() { return targetRole; }
    public void setTargetRole(String targetRole) { this.targetRole = targetRole != null ? targetRole.trim() : ""; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary != null ? summary.trim() : ""; }

    public String getLinkedIn() { return linkedIn; }
    public void setLinkedIn(String linkedIn) { this.linkedIn = linkedIn != null ? linkedIn.trim() : ""; }

    public String getGitHub() { return gitHub; }
    public void setGitHub(String gitHub) { this.gitHub = gitHub != null ? gitHub.trim() : ""; }
}
