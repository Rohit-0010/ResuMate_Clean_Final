package exporter;

import model.Education;
import model.Experience;
import model.PersonalDetails;
import model.ResumeData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class TextResumeExporter implements ResumeExporter {

    @Override
    public String getFormatName() { return "Plain Text (.txt)"; }

    @Override
    public String getFileExtension() { return "txt"; }

    @Override
    public void export(ResumeData resume, File destinationFile) throws IOException {
        PersonalDetails p = resume.getPersonalDetails();
        StringBuilder sb = new StringBuilder();

        sb.append("=".repeat(60)).append("\n");
        sb.append(p.getFullName().toUpperCase()).append("\n");
        if (!p.getTargetRole().isEmpty()) sb.append(p.getTargetRole().toUpperCase()).append("\n");
        sb.append("=".repeat(60)).append("\n");

        if (!p.getEmail().isEmpty()) sb.append("Email: ").append(p.getEmail()).append(" | ");
        if (!p.getPhone().isEmpty()) sb.append("Phone: ").append(p.getPhone()).append(" | ");
        if (!p.getLocation().isEmpty()) sb.append("Location: ").append(p.getLocation());
        sb.append("\n");
        if (!p.getLinkedIn().isEmpty()) sb.append("LinkedIn: ").append(p.getLinkedIn()).append("\n");
        if (!p.getGitHub().isEmpty()) sb.append("GitHub: ").append(p.getGitHub()).append("\n\n");

        if (!p.getSummary().isEmpty()) {
            sb.append("--- PROFESSIONAL SUMMARY ---\n").append(p.getSummary()).append("\n\n");
        }

        if (!resume.getExperiences().isEmpty()) {
            sb.append("--- WORK EXPERIENCE & PROJECTS ---\n");
            for (Experience exp : resume.getExperiences()) {
                sb.append("* ").append(exp.getRole()).append(" at ").append(exp.getCompany());
                if (!exp.getDuration().isEmpty()) sb.append(" (").append(exp.getDuration()).append(")");
                sb.append("\n");
                if (!exp.getDescription().isEmpty()) sb.append("  ").append(exp.getDescription().replace("\n", "\n  ")).append("\n");
                sb.append("\n");
            }
        }

        if (!resume.getEducations().isEmpty()) {
            sb.append("--- EDUCATION ---\n");
            for (Education edu : resume.getEducations()) {
                sb.append("* ").append(edu.getDegree());
                if (!edu.getFieldOfStudy().isEmpty()) sb.append(" in ").append(edu.getFieldOfStudy());
                sb.append(" | ").append(edu.getInstitution()).append(" (").append(edu.getStartYear()).append(" - ").append(edu.getEndYear()).append(")");
                if (!edu.getGradeOrGpa().isEmpty()) sb.append(" [Score: ").append(edu.getGradeOrGpa()).append("]");
                sb.append("\n");
            }
            sb.append("\n");
        }

        if (!resume.getSkills().isEmpty()) {
            sb.append("--- SKILLS ---\n").append(String.join(", ", resume.getSkills())).append("\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destinationFile), StandardCharsets.UTF_8))) {
            writer.write(sb.toString());
        }
    }
}
