package org.example.codestats;

public class CodeStatistics {
    private int totalLines;
    private int methodCount;
    private int classCount;
    private int totalMethodLines;
    private int variableCount;
    private int commentCount;
    private int interfaceCount;

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public void incrementMethodCount() {
        methodCount++;
    }

    public void incrementClassCount() {
        classCount++;
    }

    public void addMethodLineCount(int lines) {
        totalMethodLines += lines;
    }

    public void incrementVariableCount() {
        variableCount++;
    }

    public int getVariableCount() {
        return variableCount;
    }

    public void incrementCommentCount() {
        commentCount++;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getMethodCount() {
        return methodCount;
    }

    public int getClassCount() {
        return classCount;
    }

    public int getAverageMethodLines() {
        return methodCount > 0 ? totalMethodLines / methodCount : 0;
    }

    public void incrementInterfaceCount() {
        interfaceCount++;
    }

    public int getInterfaceCount() {
        return interfaceCount;
    }
}
