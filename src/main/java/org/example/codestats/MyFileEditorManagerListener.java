package org.example.codestats;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class MyFileEditorManagerListener implements FileEditorManagerListener {

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        Project project = event.getManager().getProject();
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor != null) {
            CodeStatsService.getInstance(project).updateStatistics(editor);
        } else {
            ApplicationManager.getApplication().invokeLater(() -> StatsPanel.getInstance(project).updateStats(null));
        }
    }
}
