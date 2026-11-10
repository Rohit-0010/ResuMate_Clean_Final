package service;

import model.Education;
import model.Experience;
import model.PersonalDetails;
import model.ResumeData;

public class SampleDataLoader {

    public static void loadDemoData(ResumeData resume) {
        PersonalDetails p = resume.getPersonalDetails();
        p.setFullName("Aman Sharma");
        p.setTargetRole("Systems Engineer / Backend Architect");
        p.setEmail("aman.sharma.dev@gmail.com");
        p.setPhone("+91 98765 43210");
        p.setLocation("Chandigarh, India");
        p.setLinkedIn("linkedin.com/in/aman-dev");
        p.setGitHub("github.com/aman-core");
        p.setSummary("High-rigor software engineer specializing in low-latency systems architecture, concurrent data pipelines, and object-oriented core design. Experienced in developing deterministic backend services and micro-optimizations.");

        resume.clearExperiences();
        resume.addExperience(new Experience(
                "Systems Engineering Intern",
                "Apex Distributed Cloud",
                "June 2025 - August 2025",
                "* Engineered a multi-threaded log aggregation daemon in Java processing 18,000 req/sec.\n* Optimized cache lookup latency by 28% utilizing custom lock-free memory rings.\n* Authored defensive unit test suites achieving 96% branch coverage on core services."
        ));
        resume.addExperience(new Experience(
                "Backend Architecture Lead",
                "Campus Tech Systems",
                "Jan 2025 - May 2025",
                "* Designed a scalable RESTful service handling student admissions data with zero downtime.\n* Implemented atomic transaction locks preventing race conditions under peak server loads."
        ));

        resume.clearEducations();
        resume.addEducation(new Education(
                "Chandigarh Engineering College (CEC), CGC",
                "Bachelor of Technology (B.Tech)",
                "Computer Science and Engineering",
                "2023",
                "2027",
                "9.54 SGPA"
        ));
        resume.addEducation(new Education(
                "Delhi Public School",
                "Senior Secondary (CBSE Class XII)",
                "Science & Mathematics",
                "2021",
                "2023",
                "94.2%"
        ));

        resume.clearSkills();
        resume.addSkill("Java 17 (Swing & Core)");
        resume.addSkill("C++20 & RAII");
        resume.addSkill("Data Structures & Algorithms");
        resume.addSkill("Multithreading & Concurrency");
        resume.addSkill("Object-Oriented Design Patterns");
        resume.addSkill("Memory Management");
        resume.addSkill("REST APIs & Microservices");
        resume.addSkill("Git & Systems Tooling");

        resume.setChosenTheme("Modern Slate");
    }
}
