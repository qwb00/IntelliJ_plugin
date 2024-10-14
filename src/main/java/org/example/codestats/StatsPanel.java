package org.example.codestats;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;

@Service(Service.Level.PROJECT)
public final class StatsPanel {

    private final JPanel panel;
    private final JLabel statsLabel;

    public StatsPanel() {
        panel = new JPanel(new BorderLayout());
        statsLabel = new JLabel("Code statistics will appear here.");
        panel.add(statsLabel, BorderLayout.NORTH);
    }

    public static StatsPanel getInstance(Project project) {
        return project.getService(StatsPanel.class);
    }

    public void updateStats(CodeStatistics stats) {
        String text;
        if (stats == null) {
            text = "No file selected.";
        } else {
            text = String.format("<html>"
                            + "Total lines: %d<br>"
                            + "Classes: %d<br>"
                            + "Interfaces: %d<br>"
                            + "Methods: %d<br>"
                            + "Variables: %d<br>"
                            + "Comments: %d<br>"
                            + "Average method length: %d lines<br>"
                            + "</html>",
                    stats.getTotalLines(),
                    stats.getClassCount(),
                    stats.getInterfaceCount(),
                    stats.getMethodCount(),
                    stats.getVariableCount(),
                    stats.getCommentCount(),
                    stats.getAverageMethodLines());
        }
        statsLabel.setText(text);
    }

    public JPanel getContent() {
        return panel;
    }
}
