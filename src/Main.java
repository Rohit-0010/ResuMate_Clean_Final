import ui.ResumeFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            ResumeFrame frame = new ResumeFrame();
            frame.setVisible(true);
        });
    }
}
