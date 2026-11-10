package ui;

import model.ResumeData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SkillsPanel extends JPanel {
    private final ResumeData resumeData;
    private final Runnable onChangeCallback;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> skillsList = new JList<>(listModel);
    private final JTextField skillInputField = new JTextField(18);

    public SkillsPanel(ResumeData resumeData, Runnable onChangeCallback) {
        this.resumeData = resumeData;
        this.onChangeCallback = onChangeCallback;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(14, 14, 14, 14));

        skillsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        skillsList.setFont(new Font("Segoe UI", Font.BOLD, 12));
        skillsList.setFixedCellHeight(26);

        JScrollPane listScroller = new JScrollPane(skillsList);
        listScroller.setBorder(BorderFactory.createTitledBorder("Technical & Soft Skills"));
        add(listScroller, BorderLayout.CENTER);

        JPanel inputBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        inputBar.setBackground(Color.WHITE);
        inputBar.add(new JLabel("Skill Name:"));
        skillInputField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        inputBar.add(skillInputField);
        JButton addButton = new JButton("+ Add Skill");
        JButton deleteButton = new JButton("Remove Selected");

        addButton.addActionListener(e -> addSkillAction());
        skillInputField.addActionListener(e -> addSkillAction());

        deleteButton.addActionListener(e -> {
            int selected = skillsList.getSelectedIndex();
            if (selected != -1) {
                listModel.remove(selected);
                resumeData.removeSkill(selected);
                if (onChangeCallback != null) onChangeCallback.run();
            }
        });

        inputBar.add(addButton);
        inputBar.add(deleteButton);
        add(inputBar, BorderLayout.SOUTH);
    }

    public void reloadFromModel() {
        listModel.clear();
        for (String s : resumeData.getSkills()) listModel.addElement(s);
    }

    private void addSkillAction() {
        String skill = skillInputField.getText().trim();
        if (!skill.isEmpty()) {
            if (!listModel.contains(skill)) {
                listModel.addElement(skill);
                resumeData.addSkill(skill);
                skillInputField.setText("");
                if (onChangeCallback != null) onChangeCallback.run();
            } else {
                JOptionPane.showMessageDialog(this, "Skill already added.", "Duplicate", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
