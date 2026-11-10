package model;

import java.io.Serializable;

public class Experience implements Serializable {
    private static final long serialVersionUID = 1L;

    private String role = "";
    private String company = "";
    private String duration = "";
    private String description = "";

    public Experience() {}

    public Experience(String role, String company, String duration, String description) {
        this.role = role != null ? role.trim() : "";
        this.company = company != null ? company.trim() : "";
        this.duration = duration != null ? duration.trim() : "";
        this.description = description != null ? description.trim() : "";
    }

    public String getRole() { return role; }
    public String getCompany() { return company; }
    public String getDuration() { return duration; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return role + (company.isEmpty() ? "" : " @ " + company) + (duration.isEmpty() ? "" : " [" + duration + "]");
    }
}
