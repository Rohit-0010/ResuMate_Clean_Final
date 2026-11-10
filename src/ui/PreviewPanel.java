package ui;

import exporter.HtmlResumeExporter;
import model.ResumeData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PreviewPanel extends JPanel {
    private final JTextPane previewPane = new JTextPane();

    public PreviewPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(241, 245, 249));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        previewPane.setEditable(false);
        previewPane.setContentType("text/html");
        previewPane.setBackground(Color.WHITE);
        previewPane.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(previewPane);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updatePreview(ResumeData data) {
        String htmlContent = HtmlResumeExporter.generateHtml(data, false);
        previewPane.setText(htmlContent);
        previewPane.setCaretPosition(0);
    }
}
