package org.example.codestats;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

@Service(Service.Level.PROJECT)
public final class CodeStatsService {

    private final Project project;

    public CodeStatsService(Project project) {
        this.project = project;
    }

    public static CodeStatsService getInstance(Project project) {
        return project.getService(CodeStatsService.class);
    }

    public void updateStatistics(Editor editor) {
        ApplicationManager.getApplication().runReadAction(() -> {
            Editor selectedEditor = FileEditorManager.getInstance(project).getSelectedTextEditor();
            if (editor != selectedEditor) {
                return;
            }

            PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);
            PsiFile psiFile = psiDocumentManager.getPsiFile(editor.getDocument());
            if (!(psiFile instanceof PsiJavaFile)) {
                ApplicationManager.getApplication().invokeLater(() -> StatsPanel.getInstance(project).updateStats(null));
                return;
            }

            Document document = editor.getDocument();
            CodeStatistics stats = calculateStatistics(psiFile, document);

            ApplicationManager.getApplication().invokeLater(() -> StatsPanel.getInstance(project).updateStats(stats));
        });
    }

    private CodeStatistics calculateStatistics(PsiFile psiFile, Document document) {
        CodeStatistics stats = new CodeStatistics();

        psiFile.accept(new JavaRecursiveElementWalkingVisitor() {
            @Override
            public void visitMethod(@NotNull PsiMethod method) {
                super.visitMethod(method);
                if (method.isConstructor()) {
                    return; // Skip constructors
                }
                stats.incrementMethodCount();
                stats.addMethodLineCount(countLinesInElement(method, document));
            }

            @Override
            public void visitClass(@NotNull PsiClass aClass) {
                super.visitClass(aClass);
                if (aClass.isInterface()) {
                    stats.incrementInterfaceCount();
                } else {
                    stats.incrementClassCount();
                }
            }

            @Override
            public void visitField(@NotNull PsiField field) {
                super.visitField(field);
                stats.incrementVariableCount();
            }

            @Override
            public void visitLocalVariable(@NotNull PsiLocalVariable variable) {
                super.visitLocalVariable(variable);
                stats.incrementVariableCount();
            }

            @Override
            public void visitComment(@NotNull PsiComment comment) {
                super.visitComment(comment);
                stats.incrementCommentCount();
            }
        });

        stats.setTotalLines(document.getLineCount());

        return stats;
    }

    private int countLinesInElement(PsiElement element, Document document) {
        int startOffset = element.getTextRange().getStartOffset();
        int endOffset = element.getTextRange().getEndOffset();

        int startLine = document.getLineNumber(startOffset);
        int endLine = document.getLineNumber(endOffset);

        return endLine - startLine + 1;
    }
}
