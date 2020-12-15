package org.moara.evaluation;

/**
 * 성능 평가 결과
 *
 *
 *
 */
public class Evaluation {

    private final double p; // 전체 참인 수
    private final double N; // 전체 거짓인 수

    private final double truePositive; // 참을 참이라고 한 횟수
    private final double trueNegative; // 거짓을 거짓이라고 한 횟수
    private final double falseNegative; // 참을 거짓이라고 한 횟수
    private final double falsePositive; // 거짓을 참이라고 한 횟수

    private final double accuracy; // 정확도, 제대로 분류된 데이터의 비율 (TP + TN) / (P + N)
    private final double errorRate; // 오류율, 잘못 분류한 데이터의 비율 (FN + FP) / (P + N) , 1 - Accuracy

    private final double precision; // 정밀도, 예측한 정답 중 실제 정답인 것 TP / (TP + FP) 모델 관점
    private final double recall; // 재현율, 실제 정답 중 예측에 성공한 것 TP / (TP + FN) 데이터 관점, 참인 정답이 적을 때 유효

    private final double f1Score; // precision과 recall의 조화평균, 2 / (1 / precision) + (1 / recall) 0에서 1사이의 값을 가지며 높을수록 좋음, recall과 precision의 조화 평균

    private final double geometricMean; // 균형 정확도, 참에 대한 정확도와, 거짓에 대한 정확도를 따로 분류해 기하 평균을 구함 sqrt((TP / (TP + FP)) * (TN / (FN + TN)))


    public Evaluation(int truePositive, int trueNegative, int falseNegative, int falsePositive) {
        this.p = truePositive + falseNegative;
        this.N = trueNegative + falsePositive;

        this.truePositive = truePositive;
        this.trueNegative = trueNegative;
        this.falseNegative = falseNegative;
        this.falsePositive = falsePositive;

        this.precision = this.truePositive / (this.truePositive + this.falsePositive);
        this.recall = this.truePositive / (this.truePositive + this.falseNegative);
        this.f1Score = 2 / ((1 / this.precision) + (1 / this.recall));
        this.accuracy = (this.truePositive + this.trueNegative) / (this.p + this.N);
        this.errorRate = (this.falseNegative + this.falsePositive) / (this.p + this.N);
        this.geometricMean = Math.sqrt((this.truePositive / (this.truePositive + this.falsePositive)) * (this.trueNegative / (this.falseNegative + this.trueNegative)));

    }

    @Override
    public String toString() {
        return  "Positive = " + p +
                ", Negative = " + N +
                ", True Positive = " + truePositive +
                ", True Negative = " + trueNegative +
                ", False Negative = " + falseNegative +
                ", False Positive = " + falsePositive +
                ", Accuracy = " + accuracy +
//                ", ErrorRate=" + errorRate +
                ", Precision = " + precision +
                ", Recall = " + recall +
                ", F1 Score = " + f1Score +
                ", Geometric Mean = " + geometricMean;
    }

    public double getP() {
        return p;
    }
    public double getN() {
        return N;
    }
    public double getTruePositive() {
        return truePositive;
    }
    public double getTrueNegative() {
        return trueNegative;
    }
    public double getFalseNegative() {
        return falseNegative;
    }
    public double getFalsePositive() {
        return falsePositive;
    }
    public double getPrecision() {
        return precision;
    }
    public double getRecall() {
        return recall;
    }
    public double getF1Score() {
        return f1Score;
    }
    public double getAccuracy() {
        return accuracy;
    }
    public double getErrorRate() {
        return errorRate;
    }
    public double getGeometricMean() {
        return geometricMean;
    }
}
