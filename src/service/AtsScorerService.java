package service;

import model.Experience;
import model.PersonalDetails;
import model.ResumeData;

import java.util.ArrayList;
import java.util.List;

public class AtsScorerService {

    public static class AtsScoreReport {
        public final int score;
        public final String rating;
        public final List<String> feedback;

        public AtsScoreReport(int score, String rating, List<String> feedback) {
            this.score = score;
            this.rating = rating;
            this.feedback = feedback;
        }
    }

    public static AtsScoreReport evaluate(ResumeData resume) {
        int score = 0;
        List<String> feedback = new ArrayList<>();
        PersonalDetails p = resume.getPersonalDetails();

        if (!p.getFullName().trim().isEmpty()) score += 10;
        else feedback.add("Add your Full Name (+10%)");

        if (!p.getEmail().trim().isEmpty() && p.getEmail().contains("@")) score += 5;
        else feedback.add("Provide a valid Email Address (+5%)");

        if (!p.getPhone().trim().isEmpty()) score += 5;
        else feedback.add("Provide a contact Phone Number (+5%)");

        if (!p.getLinkedIn().trim().isEmpty() || !p.getGitHub().trim().isEmpty()) score += 5;
        else feedback.add("Add a professional profile link (+5%)");

        String summary = p.getSummary().trim();
        if (summary.length() >= 75) score += 15;
        else if (summary.length() > 0) {
            score += 8;
            feedback.add("Expand Summary: Aim for at least 75 characters for better ATS keywords (+7%)");
        } else {
            feedback.add("Add a Professional Summary (+15%)");
        }

        if (!resume.getExperiences().isEmpty()) {
            score += 15;
            int verbs = 0;
            String[] actionVerbs = {"engineered", "built", "developed", "architected", "optimized", "implemented", "managed", "created", "designed"};
            for (Experience exp : resume.getExperiences()) {
                String d = exp.getDescription().toLowerCase();
                for (String v : actionVerbs) {
                    if (d.contains(v)) verbs++;
                }
            }
            if (verbs >= 2) score += 10;
            else feedback.add("Use high-impact action verbs (e.g. engineered, optimized, developed) (+10%)");
        } else {
            feedback.add("Add at least one Work / Project Experience (+25%)");
        }

        if (!resume.getEducations().isEmpty()) score += 15;
        else feedback.add("Add academic background under Education (+15%)");

        int skillCount = resume.getSkills().size();
        if (skillCount >= 6) score += 20;
        else if (skillCount >= 3) {
            score += 12;
            feedback.add("Add at least 6 technical skills for higher ATS ranking (+8%)");
        } else if (skillCount > 0) score += 6;
        else feedback.add("Add your Technical Skills (+20%)");

        String rating;
        if (score >= 85) rating = "Excellent (Ready for Submission)";
        else if (score >= 65) rating = "Good (Minor polishing recommended)";
        else if (score >= 40) rating = "Fair (Incomplete sections)";
        else rating = "Needs Work (Add required fields)";

        return new AtsScoreReport(score, rating, feedback);
    }
}
