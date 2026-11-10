package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResumeData implements Serializable {
    private static final long serialVersionUID = 1L;

    private PersonalDetails personalDetails = new PersonalDetails();
    private final List<Education> educations = new ArrayList<>();
    private final List<Experience> experiences = new ArrayList<>();
    private final List<String> skills = new ArrayList<>();
    private String chosenTheme = "Modern Slate";

    public ResumeData() {}

    public PersonalDetails getPersonalDetails() { return personalDetails; }
    public void setPersonalDetails(PersonalDetails p) { this.personalDetails = p != null ? p : new PersonalDetails(); }

    public List<Education> getEducations() { return Collections.unmodifiableList(educations); }
    public void addEducation(Education edu) { if (edu != null) educations.add(edu); }
    public void removeEducation(int idx) { if (idx >= 0 && idx < educations.size()) educations.remove(idx); }
    public void clearEducations() { educations.clear(); }

    public List<Experience> getExperiences() { return Collections.unmodifiableList(experiences); }
    public void addExperience(Experience exp) { if (exp != null) experiences.add(exp); }
    public void removeExperience(int idx) { if (idx >= 0 && idx < experiences.size()) experiences.remove(idx); }
    public void clearExperiences() { experiences.clear(); }

    public List<String> getSkills() { return Collections.unmodifiableList(skills); }
    public void addSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty() && !skills.contains(skill.trim())) {
            skills.add(skill.trim());
        }
    }
    public void removeSkill(int idx) { if (idx >= 0 && idx < skills.size()) skills.remove(idx); }
    public void clearSkills() { skills.clear(); }

    public String getChosenTheme() { return chosenTheme; }
    public void setChosenTheme(String theme) { this.chosenTheme = theme != null ? theme : "Modern Slate"; }

    public void loadFrom(ResumeData other) {
        if (other == null) return;
        this.personalDetails = other.personalDetails;
        this.educations.clear();
        this.educations.addAll(other.educations);
        this.experiences.clear();
        this.experiences.addAll(other.experiences);
        this.skills.clear();
        this.skills.addAll(other.skills);
        this.chosenTheme = other.chosenTheme;
    }
}
