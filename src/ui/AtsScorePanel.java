package ui;

import model.ResumeData;
import service.AtsScorerService;
import service.AtsScorerService.AtsScoreReport;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class AtsScorePanel extends JPanel {
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel scoreLabel = new JLabel("ATS Strength: 0%");
    private final JLabel ratingLabel = new JLabel("Ready for evaluation");
    private final JButton auditButton = new JButton("Run ATS Audit");
    private AtsScoreReport lastReport;

    public AtsScorePanel() {
        setLayout(new BorderLayout(14, 0));
        setBackground(new Color(248, 250, 252));
        setBorder(new CompoundBorder(
                new MatteBorder(1, 0, 0, 0, new Color(226, 232, 240)),
                new EmptyBorder(8, 14, 8, 14)
        ));

        JPanel leftPanel = new JPanel(new GridLayout(2, 1, 1, 1));
        leftPanel.setOpaque(false);
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        ratingLabel.setForeground(new Color(100, 116, 139));
        leftPanel.add(scoreLabel);
        leftPanel.add(ratingLabel);

        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(190, 20));
        progressBar.setFont(new Font("Segoe UI", Font.BOLD, 10));

        auditButton.setFont(new Font("Segoe UI", Font.BOLD, 11));
        auditButton.addActionListener(e -> showAuditDialog());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);
        rightPanel.add(progressBar);
        rightPanel.add(auditButton);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    public void updateScore(ResumeData data) {
        lastReport = AtsScorerService.evaluate(data);
        progressBar.setValue(lastReport.score);
        scoreLabel.setText("ATS Completeness Score: " + lastReport.score + "%");
        ratingLabel.setText("Status: " + lastReport.rating);

        if (lastReport.score >= 80) {
            progressBar.setForeground(new Color(16, 185, 129));
        } else if (lastReport.score >= 50) {
            progressBar.setForeground(new Color(245, 158, 11));
        } else {
            progressBar.setForeground(new Color(239, 68, 68));
        }
    }

    private void showAuditDialog() {
        if (lastReport == null) return;
        StringBuilder sb = new StringBuilder();
        sb.append("ATS Completeness Score: ").append(lastReport.score).append("%\n");
        sb.append("Benchmark Status: ").append(lastReport.rating).append("\n\n");
        if (lastReport.feedback.isEmpty()) {
            sb.append("All primary ATS benchmark criteria are fully satisfied!");
        } else {
            sb.append("Optimization Recommendations:\n");
            for (String f : lastReport.feedback) sb.append("* ").append(f).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "ATS Strength & Keyword Audit", JOptionPane.INFORMATION_MESSAGE);
    }
}
