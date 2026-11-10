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

public class HtmlResumeExporter implements ResumeExporter {

    @Override
    public String getFormatName() {
        return "Clean Printable HTML (.html)";
    }

    @Override
    public String getFileExtension() {
        return "html";
    }

    @Override
    public void export(ResumeData resume, File destinationFile) throws IOException {
        String html = generateHtml(resume, true);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destinationFile), StandardCharsets.UTF_8))) {
            writer.write(html);
        }
    }

    public static String generateHtml(ResumeData resume, boolean isFullDocument) {
        PersonalDetails p = resume.getPersonalDetails();
        String theme = resume.getChosenTheme();

        String headerColor = "#0f172a";
        String accentColor = "#2563eb";
        String fontFace = "Arial, sans-serif";

        if ("Nordic Teal".equalsIgnoreCase(theme)) {
            headerColor = "#042f2e";
            accentColor = "#0d9488";
        } else if ("Crimson Formal".equalsIgnoreCase(theme)) {
            headerColor = "#450a0a";
            accentColor = "#b91c1c";
            fontFace = "Georgia, serif";
        } else if ("Graphite Dark".equalsIgnoreCase(theme)) {
            headerColor = "#18181b";
            accentColor = "#4b5563";
        }

        StringBuilder sb = new StringBuilder();

        if (isFullDocument) {
            sb.append("<!DOCTYPE html>\n<html><head><meta charset='UTF-8'>\n");
            sb.append("<title>").append(escape(p.getFullName())).append(" - Resume</title>\n");
            sb.append("<style>\n");
            sb.append("body { font-family: ").append(fontFace).append("; background-color: #f8fafc; color: #1e293b; padding: 25px; margin: 0; }\n");
            sb.append(".card { max-width: 820px; margin: 0 auto; background-color: #ffffff; padding: 40px; border-radius: 6px; box-shadow: 0 4px 10px rgba(0,0,0,0.06); }\n");
            sb.append("@media print { body { background-color: #ffffff; padding: 0; } .card { box-shadow: none; padding: 0; } }\n");
            sb.append("</style></head><body>\n<div class='card'>\n");
        } else {
            sb.append("<html><body style='font-family: ").append(fontFace).append("; color: #1e293b; background-color: #ffffff; margin: 10px;'>");
        }

        // Header Table
        sb.append("<table width='100%' cellpadding='0' cellspacing='0' style='border-bottom: 2px solid ").append(accentColor).append("; margin-bottom: 12px;'>");
        sb.append("<tr><td style='padding-bottom: 8px;'>");
        sb.append("<font size='5' color='").append(headerColor).append("'><b>")
          .append(escape(p.getFullName().isEmpty() ? "YOUR FULL NAME" : p.getFullName().toUpperCase()))
          .append("</b></font><br>");

        if (!p.getTargetRole().isEmpty()) {
            sb.append("<font size='3' color='").append(accentColor).append("'><b>")
              .append(escape(p.getTargetRole()))
              .append("</b></font><br>");
        }

        sb.append("<font size='2' color='#64748b'>");
        boolean hasContact = false;
        if (!p.getEmail().isEmpty()) { sb.append(escape(p.getEmail())); hasContact = true; }
        if (!p.getPhone().isEmpty()) { if (hasContact) sb.append(" &nbsp;|&nbsp; "); sb.append(escape(p.getPhone())); hasContact = true; }
        if (!p.getLocation().isEmpty()) { if (hasContact) sb.append(" &nbsp;|&nbsp; "); sb.append(escape(p.getLocation())); hasContact = true; }
        if (!p.getLinkedIn().isEmpty()) { if (hasContact) sb.append("<br>"); sb.append("LinkedIn: ").append(escape(p.getLinkedIn())); hasContact = true; }
        if (!p.getGitHub().isEmpty()) { if (!p.getLinkedIn().isEmpty()) sb.append(" &nbsp;|&nbsp; "); sb.append("GitHub: ").append(escape(p.getGitHub())); }
        sb.append("</font>");
        sb.append("</td></tr></table>");

        // Summary
        if (!p.getSummary().isEmpty()) {
            sb.append("<table width='100%' cellpadding='2' cellspacing='0' style='margin-bottom: 10px;'>");
            sb.append("<tr><td bgcolor='#f1f5f9' style='border-left: 3px solid ").append(accentColor).append("; padding: 3px 6px;'>");
            sb.append("<font size='2' color='").append(headerColor).append("'><b>PROFESSIONAL SUMMARY</b></font>");
            sb.append("</td></tr>");
            sb.append("<tr><td style='padding-top: 4px;'>");
            sb.append("<font size='2' color='#334155'>").append(escape(p.getSummary())).append("</font>");
            sb.append("</td></tr></table>");
        }

        // Experience
        if (!resume.getExperiences().isEmpty()) {
            sb.append("<table width='100%' cellpadding='2' cellspacing='0' style='margin-bottom: 10px;'>");
            sb.append("<tr><td bgcolor='#f1f5f9' style='border-left: 3px solid ").append(accentColor).append("; padding: 3px 6px;'>");
            sb.append("<font size='2' color='").append(headerColor).append("'><b>WORK EXPERIENCE & PROJECTS</b></font>");
            sb.append("</td></tr>");
            sb.append("<tr><td style='padding-top: 6px;'>");

            for (Experience exp : resume.getExperiences()) {
                sb.append("<table width='100%' cellpadding='0' cellspacing='0' style='margin-bottom: 8px;'>");
                sb.append("<tr>");
                sb.append("<td align='left'><font size='2' color='#0f172a'><b>").append(escape(exp.getRole())).append("</b> - <i>").append(escape(exp.getCompany())).append("</i></font></td>");
                sb.append("<td align='right'><font size='2' color='#64748b'><b>").append(escape(exp.getDuration())).append("</b></font></td>");
                sb.append("</tr>");
                if (!exp.getDescription().isEmpty()) {
                    sb.append("<tr><td colspan='2' style='padding-top: 2px;'>");
                    String descHtml = escape(exp.getDescription()).replace("\n", "<br>");
                    sb.append("<font size='2' color='#334155'>").append(descHtml).append("</font>");
                    sb.append("</td></tr>");
                }
                sb.append("</table>");
            }

            sb.append("</td></tr></table>");
        }

        // Education
        if (!resume.getEducations().isEmpty()) {
            sb.append("<table width='100%' cellpadding='2' cellspacing='0' style='margin-bottom: 10px;'>");
            sb.append("<tr><td bgcolor='#f1f5f9' style='border-left: 3px solid ").append(accentColor).append("; padding: 3px 6px;'>");
            sb.append("<font size='2' color='").append(headerColor).append("'><b>EDUCATION</b></font>");
            sb.append("</td></tr>");
            sb.append("<tr><td style='padding-top: 6px;'>");

            for (Education edu : resume.getEducations()) {
                sb.append("<table width='100%' cellpadding='0' cellspacing='0' style='margin-bottom: 6px;'>");
                sb.append("<tr>");
                sb.append("<td align='left'><font size='2' color='#0f172a'><b>").append(escape(edu.getDegree()));
                if (!edu.getFieldOfStudy().isEmpty()) sb.append(" in ").append(escape(edu.getFieldOfStudy()));
                sb.append("</b> - ").append(escape(edu.getInstitution())).append("</font></td>");
                sb.append("<td align='right'><font size='2' color='#64748b'><b>").append(escape(edu.getStartYear())).append(" - ").append(escape(edu.getEndYear())).append("</b></font></td>");
                sb.append("</tr>");
                if (!edu.getGradeOrGpa().isEmpty()) {
                    sb.append("<tr><td colspan='2'><font size='1' color='#475569'>Score: ").append(escape(edu.getGradeOrGpa())).append("</font></td></tr>");
                }
                sb.append("</table>");
            }

            sb.append("</td></tr></table>");
        }

        // Skills
        if (!resume.getSkills().isEmpty()) {
            sb.append("<table width='100%' cellpadding='2' cellspacing='0' style='margin-bottom: 10px;'>");
            sb.append("<tr><td bgcolor='#f1f5f9' style='border-left: 3px solid ").append(accentColor).append("; padding: 3px 6px;'>");
            sb.append("<font size='2' color='").append(headerColor).append("'><b>TECHNICAL SKILLS & COMPETENCIES</b></font>");
            sb.append("</td></tr>");
            sb.append("<tr><td style='padding-top: 6px;'>");
            sb.append("<font size='2' color='#1e293b'>");

            StringBuilder skillList = new StringBuilder();
            for (String s : resume.getSkills()) {
                if (skillList.length() > 0) skillList.append(" &nbsp;*&nbsp; ");
                skillList.append("<b>").append(escape(s)).append("</b>");
            }
            sb.append(skillList.toString());

            sb.append("</font></td></tr></table>");
        }

        if (isFullDocument) {
            sb.append("</div></body></html>");
        } else {
            sb.append("</body></html>");
        }

        return sb.toString();
    }

    private static String escape(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace(""", "&quot;")
                   .replace("'", "&#39;");
    }
}
