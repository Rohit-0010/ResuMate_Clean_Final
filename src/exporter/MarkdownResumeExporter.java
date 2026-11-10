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

public class MarkdownResumeExporter implements ResumeExporter {

    @Override
    public String getFormatName() { return "Markdown (.md)"; }

    @Override
    public String getFileExtension() { return "md"; }

    @Override
    public void export(ResumeData resume, File destinationFile) throws IOException {
        PersonalDetails p = resume.getPersonalDetails();
        StringBuilder sb = new StringBuilder();

        sb.append("# ").append(p.getFullName().isEmpty() ? "Candidate Name" : p.getFullName()).append("\n");
        if (!p.getTargetRole().isEmpty()) sb.append("### *").append(p.getTargetRole()).append("*\n\n");
        else sb.append("\n");

        sb.append("**Contact:** ");
        StringBuilder lb = new StringBuilder();
        if (!p.getEmail().isEmpty()) lb.append("[").append(p.getEmail()).append("](mailto:").append(p.getEmail()).append(")");
        if (!p.getPhone().isEmpty()) { if (lb.length() > 0) lb.append(" | "); lb.append(p.getPhone()); }
        if (!p.getLocation().isEmpty()) { if (lb.length() > 0) lb.append(" | "); lb.append(p.getLocation()); }
        if (!p.getLinkedIn().isEmpty()) { if (lb.length() > 0) lb.append(" | "); lb.append("[LinkedIn](").append(p.getLinkedIn()).append(")"); }
        if (!p.getGitHub().isEmpty()) { if (lb.length() > 0) lb.append(" | "); lb.append("[GitHub](").append(p.getGitHub()).append(")"); }
        sb.append(lb.toString()).append("\n\n---\n\n");

        if (!p.getSummary().isEmpty()) {
            sb.append("## Professional Summary\n\n").append(p.getSummary()).append("\n\n");
        }

        if (!resume.getExperiences().isEmpty()) {
            sb.append("## Experience & Projects\n\n");
            for (Experience exp : resume.getExperiences()) {
                sb.append("### ").append(exp.getRole()).append(" - ").append(exp.getCompany()).append("\n");
                sb.append("*").append(exp.getDuration()).append("*\n\n");
                if (!exp.getDescription().isEmpty()) sb.append(exp.getDescription()).append("\n\n");
            }
        }

        if (!resume.getEducations().isEmpty()) {
            sb.append("## Education\n\n");
            for (Education edu : resume.getEducations()) {
                sb.append("### ").append(edu.getDegree());
                if (!edu.getFieldOfStudy().isEmpty()) sb.append(" in ").append(edu.getFieldOfStudy());
                sb.append("\n**").append(edu.getInstitution()).append("** (").append(edu.getStartYear()).append(" - ").append(edu.getEndYear()).append(")");
                if (!edu.getGradeOrGpa().isEmpty()) sb.append(" * ").append(edu.getGradeOrGpa());
                sb.append("\n\n");
            }
        }

        if (!resume.getSkills().isEmpty()) {
            sb.append("## Skills\n\n").append(String.join(" * ", resume.getSkills())).append("\n");
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destinationFile), StandardCharsets.UTF_8))) {
            writer.write(sb.toString());
        }
    }
}
