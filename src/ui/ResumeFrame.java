package ui;

import exporter.HtmlResumeExporter;
import exporter.MarkdownResumeExporter;
import exporter.ResumeExporter;
import exporter.TextResumeExporter;
import model.ResumeData;
import service.ProfileSerializerService;
import service.SampleDataLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ResumeFrame extends JFrame {
    private final ResumeData resumeData = new ResumeData();
    private final PreviewPanel previewPanel = new PreviewPanel();
    private final AtsScorePanel atsScorePanel = new AtsScorePanel();

    private PersonalDetailsPanel personalDetailsPanel;
    private EducationPanel educationPanel;
    private ExperiencePanel experiencePanel;
    private SkillsPanel skillsPanel;
    private JComboBox<String> themeDropdown;

    public ResumeFrame() {
        super("ResuMate Studio Pro - Desktop Resume Engineering Environment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 780);
        setMinimumSize(new Dimension(960, 660));
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(520);
        splitPane.setResizeWeight(0.44);
        splitPane.setBorder(null);

        Runnable updateCallback = () -> {
            previewPanel.updatePreview(resumeData);
            atsScorePanel.updateScore(resumeData);
        };

        personalDetailsPanel = new PersonalDetailsPanel(resumeData, updateCallback);
        educationPanel = new EducationPanel(resumeData, updateCallback);
        experiencePanel = new ExperiencePanel(resumeData, updateCallback);
        skillsPanel = new SkillsPanel(resumeData, updateCallback);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabbedPane.addTab("  Profile  ", personalDetailsPanel);
        tabbedPane.addTab("  Education  ", educationPanel);
        tabbedPane.addTab("  Experience & Projects  ", experiencePanel);
        tabbedPane.addTab("  Skills  ", skillsPanel);

        splitPane.setLeftComponent(tabbedPane);
        splitPane.setRightComponent(previewPanel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(splitPane, BorderLayout.CENTER);
        centerPanel.add(atsScorePanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        initTopToolBar(updateCallback);

        SampleDataLoader.loadDemoData(resumeData);
        refreshAllPanels();
        updateCallback.run();
    }

    private void initTopToolBar(Runnable updateCallback) {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(248, 250, 252));
        toolBar.setBorder(new EmptyBorder(6, 12, 6, 12));

        JLabel brandLabel = new JLabel("ResuMate Studio  ");
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        brandLabel.setForeground(new Color(15, 23, 42));
        toolBar.add(brandLabel);

        toolBar.add(new JLabel("Theme: "));
        themeDropdown = new JComboBox<>(new String[]{"Modern Slate", "Nordic Teal", "Crimson Formal", "Graphite Dark"});
        themeDropdown.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        themeDropdown.setMaximumSize(new Dimension(140, 28));
        themeDropdown.addActionListener(e -> {
            resumeData.setChosenTheme((String) themeDropdown.getSelectedItem());
            updateCallback.run();
        });
        toolBar.add(themeDropdown);
        toolBar.addSeparator(new Dimension(12, 20));

        JButton demoBtn = new JButton("Load Sample Profile");
        demoBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        demoBtn.addActionListener(e -> {
            SampleDataLoader.loadDemoData(resumeData);
            refreshAllPanels();
            updateCallback.run();
        });
        toolBar.add(demoBtn);

        JButton saveBtn = new JButton("Save Draft (.res)");
        saveBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        saveBtn.addActionListener(e -> saveProjectFile());
        toolBar.add(saveBtn);

        JButton loadBtn = new JButton("Open Draft (.res)");
        loadBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loadBtn.addActionListener(e -> loadProjectFile(updateCallback));
        toolBar.add(loadBtn);

        toolBar.addSeparator(new Dimension(14, 20));

        JButton exportHtmlBtn = new JButton("Export HTML (PDF Print)");
        exportHtmlBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        exportHtmlBtn.setBackground(new Color(37, 99, 235));
        exportHtmlBtn.addActionListener(e -> executeExport(new HtmlResumeExporter()));
        toolBar.add(exportHtmlBtn);

        JButton exportMdBtn = new JButton("Export Markdown");
        exportMdBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        exportMdBtn.addActionListener(e -> executeExport(new MarkdownResumeExporter()));
        toolBar.add(exportMdBtn);

        JButton exportTxtBtn = new JButton("Export Text");
        exportTxtBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        exportTxtBtn.addActionListener(e -> executeExport(new TextResumeExporter()));
        toolBar.add(exportTxtBtn);

        add(toolBar, BorderLayout.NORTH);
    }

    private void refreshAllPanels() {
        personalDetailsPanel.reloadFromModel();
        educationPanel.reloadFromModel();
        experiencePanel.reloadFromModel();
        skillsPanel.reloadFromModel();
        if (themeDropdown != null) {
            themeDropdown.setSelectedItem(resumeData.getChosenTheme());
        }
    }

    private void saveProjectFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save ResuMate Project File");
        chooser.setFileFilter(new FileNameExtensionFilter("ResuMate Project (*.res)", "res"));
        chooser.setSelectedFile(new File("MyResume.res"));

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File target = chooser.getSelectedFile();
            if (!target.getName().toLowerCase().endsWith(".res")) {
                target = new File(target.getAbsolutePath() + ".res");
            }
            try {
                ProfileSerializerService.saveProfile(resumeData, target);
                JOptionPane.showMessageDialog(this, "Project saved successfully!", "Saved", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to save: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadProjectFile(Runnable updateCallback) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open ResuMate Project File");
        chooser.setFileFilter(new FileNameExtensionFilter("ResuMate Project (*.res)", "res"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                ResumeData loaded = ProfileSerializerService.loadProfile(chooser.getSelectedFile());
                resumeData.loadFrom(loaded);
                refreshAllPanels();
                updateCallback.run();
                JOptionPane.showMessageDialog(this, "Project loaded successfully!", "Loaded", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to load project: " + ex.getMessage(), "Corrupted File", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void executeExport(ResumeExporter exporter) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export As " + exporter.getFormatName());
        fileChooser.setFileFilter(new FileNameExtensionFilter(exporter.getFormatName(), exporter.getFileExtension()));

        String defaultName = resumeData.getPersonalDetails().getFullName().isEmpty() ?
                "Resume." + exporter.getFileExtension() :
                resumeData.getPersonalDetails().getFullName().replaceAll("\\s+", "_") + "_Resume." + exporter.getFileExtension();
        fileChooser.setSelectedFile(new File(defaultName));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getName().toLowerCase().endsWith("." + exporter.getFileExtension())) {
                fileToSave = new File(fileToSave.getAbsolutePath() + "." + exporter.getFileExtension());
            }

            try {
                exporter.export(resumeData, fileToSave);
                JOptionPane.showMessageDialog(this,
                        "Export complete!\nSaved to: " + fileToSave.getAbsolutePath(),
                        "Export Complete",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Failed to export: " + ex.getMessage(),
                        "I/O Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
