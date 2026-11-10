package ui;

import model.PersonalDetails;
import model.ResumeData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PersonalDetailsPanel extends JPanel {
    private final JTextField nameField = new JTextField();
    private final JTextField roleField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JTextField locationField = new JTextField();
    private final JTextField linkedInField = new JTextField();
    private final JTextField gitHubField = new JTextField();
    private final JTextArea summaryArea = new JTextArea(4, 20);

    private final ResumeData resumeData;
    private final Runnable onChangeCallback;
    private boolean isUpdating = false;

    public PersonalDetailsPanel(ResumeData resumeData, Runnable onChangeCallback) {
        this.resumeData = resumeData;
        this.onChangeCallback = onChangeCallback;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(14, 14, 14, 14));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        int row = 0;
        addField(formPanel, c, row++, "Full Name:", nameField);
        addField(formPanel, c, row++, "Target Role / Headline:", roleField);
        addField(formPanel, c, row++, "Email Address:", emailField);
        addField(formPanel, c, row++, "Phone Number:", phoneField);
        addField(formPanel, c, row++, "Current Location:", locationField);
        addField(formPanel, c, row++, "LinkedIn URL:", linkedInField);
        addField(formPanel, c, row++, "GitHub Profile:", gitHubField);

        c.gridx = 0;
        c.gridy = row++;
        c.weightx = 0.0;
        c.anchor = GridBagConstraints.NORTHWEST;
        JLabel sumLbl = new JLabel("Professional Summary:");
        sumLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        formPanel.add(sumLbl, c);

        c.gridx = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);
        summaryArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        summaryArea.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane sumScroll = new JScrollPane(summaryArea);
        sumScroll.setPreferredSize(new Dimension(280, 85));
        formPanel.add(sumScroll, c);

        add(new JScrollPane(formPanel), BorderLayout.CENTER);

        DocumentListener docListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { sync(); }
            @Override public void removeUpdate(DocumentEvent e) { sync(); }
            @Override public void changedUpdate(DocumentEvent e) { sync(); }
        };

        nameField.getDocument().addDocumentListener(docListener);
        roleField.getDocument().addDocumentListener(docListener);
        emailField.getDocument().addDocumentListener(docListener);
        phoneField.getDocument().addDocumentListener(docListener);
        locationField.getDocument().addDocumentListener(docListener);
        linkedInField.getDocument().addDocumentListener(docListener);
        gitHubField.getDocument().addDocumentListener(docListener);
        summaryArea.getDocument().addDocumentListener(docListener);
    }

    private void addField(JPanel panel, GridBagConstraints c, int row, String label, JTextField field) {
        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lbl, c);

        c.gridx = 1;
        c.weightx = 1.0;
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(field, c);
    }

    public void reloadFromModel() {
        isUpdating = true;
        PersonalDetails p = resumeData.getPersonalDetails();
        nameField.setText(p.getFullName());
        roleField.setText(p.getTargetRole());
        emailField.setText(p.getEmail());
        phoneField.setText(p.getPhone());
        locationField.setText(p.getLocation());
        linkedInField.setText(p.getLinkedIn());
        gitHubField.setText(p.getGitHub());
        summaryArea.setText(p.getSummary());
        isUpdating = false;
    }

    private void sync() {
        if (isUpdating) return;
        PersonalDetails p = resumeData.getPersonalDetails();
        p.setFullName(nameField.getText());
        p.setTargetRole(roleField.getText());
        p.setEmail(emailField.getText());
        p.setPhone(phoneField.getText());
        p.setLocation(locationField.getText());
        p.setLinkedIn(linkedInField.getText());
        p.setGitHub(gitHubField.getText());
        p.setSummary(summaryArea.getText());

        if (onChangeCallback != null) onChangeCallback.run();
    }
}
