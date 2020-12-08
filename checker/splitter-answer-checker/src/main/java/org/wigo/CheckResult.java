package org.wigo;

/**
 * 성능 평가 결과
 *
 *
 *
 */
public class CheckResult {

    private final double p; // 전체 참인 수
    private final double f; // 전체 거짓인 수

    private final double truePositive; // 참을 참이라고 한 횟수
    private final double trueNegative; // 거짓을 거짓이라고 한 횟수
    private final double falseNegative; // 참을 거짓이라고 한 횟수
    private final double falsePositive; // 거짓을 참이라고 한 횟수

    private final double accuracy; // 정확도, 제대로 분류된 데이터의 비율 (TP + TN) / (P + N)

    private final double precision; // 정밀도, 예측한 정답 중 실제 정답인 것 TP / (TP + FP) 모델 관점
    private final double recall; // 재현율, 실제 정답 중 예측에 성공한 것 TP / (TP + FN) 데이터 관점, 참인 정답이 적을 때 유효

    private final double f1Score; // precision과 recall의 조화평균, 2 / (1 / precision) + (1 / recall) 0에서 1사이의 값을 가지며 높을수록 좋음, recall과 precision의 조화 평균균

//   private final double errorRate; // 오류율, 전체 데이터 중 잘못 분류된 비율 (FN + FP) / (P + N)
//    private final double sensitivity; // 민감도, 모델이 얼마나 정확하게 Positive 값을 찾아내는지 TP / P


    public CheckResult(int truePositive, int trueNegative, int falseNegative, int falsePositive) {
        this.p = truePositive + falseNegative;
        this.f = trueNegative + falsePositive;

        this.truePositive = truePositive;
        this.trueNegative = trueNegative;
        this.falseNegative = falseNegative;
        this.falsePositive = falsePositive;

        this.precision = this.truePositive / (this.truePositive + this.falsePositive);
        this.recall = this.truePositive / (this.truePositive + this.falseNegative);
        this.f1Score = 2 / ((1 / this.precision) + (1 / this.recall));
        this.accuracy = (this.truePositive + this.trueNegative) / (this.p + this.f);

    }


    public double getP() {
        return p;
    }
    public double getF() {
        return f;
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
}
