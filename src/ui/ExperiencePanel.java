package ui;

import model.Experience;
import model.ResumeData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ExperiencePanel extends JPanel {
    private final ResumeData resumeData;
    private final Runnable onChangeCallback;
    private final DefaultListModel<Experience> listModel = new DefaultListModel<>();
    private final JList<Experience> expList = new JList<>(listModel);

    public ExperiencePanel(ResumeData resumeData, Runnable onChangeCallback) {
        this.resumeData = resumeData;
        this.onChangeCallback = onChangeCallback;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(14, 14, 14, 14));

        expList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        expList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        expList.setFixedCellHeight(26);

        JScrollPane listScroller = new JScrollPane(expList);
        listScroller.setBorder(BorderFactory.createTitledBorder("Work Experiences & Projects"));
        add(listScroller, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 4));
        controlPanel.setBackground(Color.WHITE);
        JButton addButton = new JButton("+ Add Experience / Project");
        JButton deleteButton = new JButton("Remove Selected");

        addButton.addActionListener(e -> showAddDialog());
        deleteButton.addActionListener(e -> {
            int selected = expList.getSelectedIndex();
            if (selected != -1) {
                listModel.remove(selected);
                resumeData.removeExperience(selected);
                if (onChangeCallback != null) onChangeCallback.run();
            } else {
                JOptionPane.showMessageDialog(this, "Select an experience to remove.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            }
        });

        controlPanel.add(addButton);
        controlPanel.add(deleteButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public void reloadFromModel() {
        listModel.clear();
        for (Experience exp : resumeData.getExperiences()) listModel.addElement(exp);
    }

    private void showAddDialog() {
        JTextField roleField = new JTextField();
        JTextField companyField = new JTextField();
        JTextField durationField = new JTextField();
        JTextArea descArea = new JTextArea(5, 24);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JPanel panel = new JPanel(new BorderLayout(6, 6));
        JPanel topGrid = new JPanel(new GridLayout(3, 2, 6, 6));
        topGrid.add(new JLabel("Job Title / Project:")); topGrid.add(roleField);
        topGrid.add(new JLabel("Company / Organization:")); topGrid.add(companyField);
        topGrid.add(new JLabel("Duration (e.g. June 2025 - Present):")); topGrid.add(durationField);

        panel.add(topGrid, BorderLayout.NORTH);
        JPanel descPanel = new JPanel(new BorderLayout(4, 4));
        descPanel.add(new JLabel("Responsibilities & Impact (Tip: use * bullet points):"), BorderLayout.NORTH);
        descPanel.add(new JScrollPane(descArea), BorderLayout.CENTER);
        panel.add(descPanel, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Experience / Project", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (roleField.getText().trim().isEmpty() || companyField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title and Company cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Experience exp = new Experience(roleField.getText(), companyField.getText(), durationField.getText(), descArea.getText());
            listModel.addElement(exp);
            resumeData.addExperience(exp);
            if (onChangeCallback != null) onChangeCallback.run();
        }
    }
}
