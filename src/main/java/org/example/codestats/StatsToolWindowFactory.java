package org.example.codestats;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.Content;
import org.jetbrains.annotations.NotNull;

public class StatsToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        StatsPanel statsPanel = StatsPanel.getInstance(project);
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(statsPanel.getContent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
