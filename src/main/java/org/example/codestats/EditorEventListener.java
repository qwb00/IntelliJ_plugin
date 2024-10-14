package org.example.codestats;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.event.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiTreeChangeEvent;
import com.intellij.psi.PsiTreeChangeListener;
import org.jetbrains.annotations.NotNull;

public class EditorEventListener implements EditorFactoryListener {

    private static final Key<Disposable> LISTENER_DISPOSABLE = new Key<>("LISTENER_DISPOSABLE");

    @Override
    public void editorCreated(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();
        Project project = editor.getProject();
        if (project == null) {
            return;
        }
        Disposable disposable = Disposer.newDisposable();
        editor.putUserData(LISTENER_DISPOSABLE, disposable);
        CodeStatsService.getInstance(project).updateStatistics(editor);
        editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void documentChanged(@NotNull DocumentEvent e) {
                CodeStatsService.getInstance(project).updateStatistics(editor);
            }
        }, disposable);

        PsiManager psiManager = PsiManager.getInstance(project);
        PsiTreeChangeListener psiTreeChangeListener = new PsiTreeChangeListener() {
            @Override
            public void childAdded(@NotNull PsiTreeChangeEvent event) {
                CodeStatsService.getInstance(project).updateStatistics(editor);
            }

            @Override
            public void childRemoved(@NotNull PsiTreeChangeEvent event) {
                CodeStatsService.getInstance(project).updateStatistics(editor);
            }

            @Override
            public void childReplaced(@NotNull PsiTreeChangeEvent event) {
                CodeStatsService.getInstance(project).updateStatistics(editor);
            }

            @Override
            public void childrenChanged(@NotNull PsiTreeChangeEvent event) {
                CodeStatsService.getInstance(project).updateStatistics(editor);
            }

            @Override public void propertyChanged(@NotNull PsiTreeChangeEvent event) {}
            @Override public void childMoved(@NotNull PsiTreeChangeEvent event) {}
            @Override public void beforeChildAddition(@NotNull PsiTreeChangeEvent event) {}
            @Override public void beforeChildRemoval(@NotNull PsiTreeChangeEvent event) {}
            @Override public void beforeChildReplacement(@NotNull PsiTreeChangeEvent event) {}
            @Override public void beforeChildMovement(@NotNull PsiTreeChangeEvent event) {}
            @Override public void beforeChildrenChange(@NotNull PsiTreeChangeEvent event) {}
            @Override public void beforePropertyChange(@NotNull PsiTreeChangeEvent event) {}
        };
        psiManager.addPsiTreeChangeListener(psiTreeChangeListener, disposable);
    }

    @Override
    public void editorReleased(@NotNull EditorFactoryEvent event) {
        Editor editor = event.getEditor();

        Disposable disposable = editor.getUserData(LISTENER_DISPOSABLE);
        if (disposable != null) {
            Disposer.dispose(disposable);
        }
    }
}