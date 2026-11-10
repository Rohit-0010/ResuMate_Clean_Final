package ui;

import model.Education;
import model.ResumeData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EducationPanel extends JPanel {
    private final ResumeData resumeData;
    private final Runnable onChangeCallback;
    private final DefaultListModel<Education> listModel = new DefaultListModel<>();
    private final JList<Education> educationList = new JList<>(listModel);

    public EducationPanel(ResumeData resumeData, Runnable onChangeCallback) {
        this.resumeData = resumeData;
        this.onChangeCallback = onChangeCallback;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(14, 14, 14, 14));

        educationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        educationList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        educationList.setFixedCellHeight(26);

        JScrollPane listScroller = new JScrollPane(educationList);
        listScroller.setBorder(BorderFactory.createTitledBorder("Academic Qualifications"));
        add(listScroller, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 4));
        controlPanel.setBackground(Color.WHITE);
        JButton addButton = new JButton("+ Add Qualification");
        JButton deleteButton = new JButton("Remove Selected");

        addButton.addActionListener(e -> showAddDialog());
        deleteButton.addActionListener(e -> {
            int selected = educationList.getSelectedIndex();
            if (selected != -1) {
                listModel.remove(selected);
                resumeData.removeEducation(selected);
                if (onChangeCallback != null) onChangeCallback.run();
            } else {
                JOptionPane.showMessageDialog(this, "Select an entry to remove.", "Selection Required", JOptionPane.WARNING_MESSAGE);
            }
        });

        controlPanel.add(addButton);
        controlPanel.add(deleteButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public void reloadFromModel() {
        listModel.clear();
        for (Education edu : resumeData.getEducations()) listModel.addElement(edu);
    }

    private void showAddDialog() {
        JTextField institutionField = new JTextField("Chandigarh Engineering College");
        JTextField degreeField = new JTextField("B.Tech");
        JTextField fieldOfStudyField = new JTextField("Computer Science & Engineering");
        JTextField startYearField = new JTextField("2023");
        JTextField endYearField = new JTextField("2027");
        JTextField gradeField = new JTextField("9.54 SGPA");

        JPanel panel = new JPanel(new GridLayout(6, 2, 6, 6));
        panel.add(new JLabel("Institution / University:")); panel.add(institutionField);
        panel.add(new JLabel("Degree / Program:")); panel.add(degreeField);
        panel.add(new JLabel("Field of Study:")); panel.add(fieldOfStudyField);
        panel.add(new JLabel("Start Year:")); panel.add(startYearField);
        panel.add(new JLabel("End Year:")); panel.add(endYearField);
        panel.add(new JLabel("Score / GPA:")); panel.add(gradeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Education Record", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (institutionField.getText().trim().isEmpty() || degreeField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Institution and Degree are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Education edu = new Education(
                    institutionField.getText(), degreeField.getText(), fieldOfStudyField.getText(),
                    startYearField.getText(), endYearField.getText(), gradeField.getText()
            );
            listModel.addElement(edu);
            resumeData.addEducation(edu);
            if (onChangeCallback != null) onChangeCallback.run();
        }
    }
}
