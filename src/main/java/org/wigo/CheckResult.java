package org.wigo;

public class CheckResult {
    private final int correctCount;
    private final int wrongCount;

    public CheckResult(int correctCount, int wrongCount) {
        this.correctCount = correctCount;
        this.wrongCount = wrongCount;
    }
    public int getWrongCount() {
        return this.wrongCount;
    }
}
