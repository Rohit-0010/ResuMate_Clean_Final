package model;

import java.io.Serializable;

public class Education implements Serializable {
    private static final long serialVersionUID = 1L;

    private String institution = "";
    private String degree = "";
    private String fieldOfStudy = "";
    private String startYear = "";
    private String endYear = "";
    private String gradeOrGpa = "";

    public Education() {}

    public Education(String institution, String degree, String fieldOfStudy, String startYear, String endYear, String gradeOrGpa) {
        this.institution = institution != null ? institution.trim() : "";
        this.degree = degree != null ? degree.trim() : "";
        this.fieldOfStudy = fieldOfStudy != null ? fieldOfStudy.trim() : "";
        this.startYear = startYear != null ? startYear.trim() : "";
        this.endYear = endYear != null ? endYear.trim() : "";
        this.gradeOrGpa = gradeOrGpa != null ? gradeOrGpa.trim() : "";
    }

    public String getInstitution() { return institution; }
    public String getDegree() { return degree; }
    public String getFieldOfStudy() { return fieldOfStudy; }
    public String getStartYear() { return startYear; }
    public String getEndYear() { return endYear; }
    public String getGradeOrGpa() { return gradeOrGpa; }

    @Override
    public String toString() {
        String title = degree + (fieldOfStudy.isEmpty() ? "" : " in " + fieldOfStudy);
        return title + (institution.isEmpty() ? "" : " - " + institution) + " (" + startYear + " - " + endYear + ")";
    }
}
