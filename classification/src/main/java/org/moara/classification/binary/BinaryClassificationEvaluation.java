package org.moara.classification.binary;

/**
 * 이항 분류 모델 성능 평가
 *
 * @author wjrmffldrhrl
 */
public class BinaryClassificationEvaluation {

    private final double allPositive;   // 전체 positive
    private final double allNegative;   // 전체 negative

    private final double truePositive;  // 참을 참이라고 한 횟수
    private final double trueNegative;  // 거짓을 거짓이라고 한 횟수
    private final double falseNegative; // 참을 거짓이라고 한 횟수
    private final double falsePositive; // 거짓을 참이라고 한 횟수


    private final double accuracy;      // 정확도, 제대로 분류된 데이터의 비율 (TP + TN) / (P + N)
    private final double errorRate;     // 오류율, 잘못 분류한 데이터의 비율 (FN + FP) / (P + N) , 1 - Accuracy

    private final double precision;     // 정밀도, 예측한 정답 중 실제 정답인 것 TP / (TP + FP) 모델 관점
    private final double sensitivity;   // 재현율, 실제 정답 중 예측에 성공한 것 TP / (TP + FN) 데이터 관점, 참인 정답이 적을 때 유효
    private final double specificity;   //  it measures how much a classifier can recognize negative examples  TN / (TN + FP)

    private final double f1Score;       // precision과 sensitivity의 조화평균, 2 / (1 / precision) + (1 / sensitivity) 0에서 1사이의 값을 가지며 높을수록 좋음, sensitivity과 precision의 조화 평균
    private final double geometricMean; // 균형 정확도, 참에 대한 정확도와, 거짓에 대한 정확도를 따로 분류해 기하 평균을 구함
                                        //  sqrt((TP / (TP + FN)) * (TN / (FP + TN))) == sqrt(sensitivity * specificity);

//    private final double geometricMean2; //  sqrt(sensitivity * precision);

    /**
     * TP, TN, FN, FP의 값을 초기화 하면 해당 모델의 정확도 측정치들이 계산된다.
     *
     *
     * @param truePositive 참을 참으로 예상한 비율
     * @param trueNegative 거짓을 거짓으로 예상한 비율
     * @param falseNegative 참을 거짓으로 예상한 비율
     * @param falsePositive 거짓을 참으로 예상한 비율
     */
    public BinaryClassificationEvaluation(double truePositive, double falsePositive,  double falseNegative,  double trueNegative) {
        this.allPositive = truePositive + falseNegative;
        this.allNegative = trueNegative + falsePositive;

        this.truePositive = truePositive;
        this.trueNegative = trueNegative;
        this.falseNegative = falseNegative;
        this.falsePositive = falsePositive;

        this.accuracy = (this.truePositive + this.trueNegative) / (this.allPositive + this.allNegative);
        this.errorRate = (this.falseNegative + this.falsePositive) / (this.allPositive + this.allNegative);

        this.precision = this.truePositive / (this.truePositive + this.falsePositive);
        this.sensitivity = this.truePositive / (this.truePositive + this.falseNegative);
        this.specificity = this.trueNegative / (this.trueNegative + this.falsePositive);

        this.f1Score = 2 / ((1 / this.precision) + (1 / this.sensitivity));
        this.geometricMean = Math.sqrt(this.sensitivity * this.specificity);

    }

    public BinaryClassificationEvaluation(int truePositive, int falsePositive,  int falseNegative,  int trueNegative) {
        this((double) truePositive, (double) falsePositive, (double) falseNegative, (double) trueNegative);

    }
    /**
     * TP, TN, FN, FP의 값을 초기화 하면 해당 모델의 정확도 측정치들이 계산된다.
     *
     *
     * @param confusionMatrix
     */
    public BinaryClassificationEvaluation(int[][] confusionMatrix) {
        this(confusionMatrix[0][0], confusionMatrix[0][1], confusionMatrix[1][0] , confusionMatrix[1][1]);
    }


    public double getAllPositive() {
        return allPositive;
    }
    public double getAllNegative() {
        return allNegative;
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
    public double getSensitivity() {
        return sensitivity;
    }
    public double getSpecificity() {
        return specificity;
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
