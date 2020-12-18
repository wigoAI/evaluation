package org.moara.classification.multinomial;

public class MultinomialClassificationEvaluation {

    private final int itemNum;

    // 각 항목에 해당하는 값
    private final double allPositive; // 전체 참인 수
    private final double allNegative; // 전체 거짓인 수

    private final double[] truePositive; // 참을 참이라고 한 횟수
    private final double[] trueNegative; // 거짓을 거짓이라고 한 횟수
    private final double[] falseNegative; // 참을 거짓이라고 한 횟수
    private final double[] falsePositive; // 거짓을 참이라고 한 횟수

    // 다항 분류에서의 평가 지표는 각 항목당 해당 수치를 모두 더한 뒤 항목 수로 나누면 된다.
    private final double accuracy; // 정확도, 제대로 분류된 데이터의 비율 (TP + TN) / (P + N)
    private final double errorRate; // 오류율, 잘못 분류한 데이터의 비율 (FN + FP) / (P + N) , 1 - Accuracy

    private final double precision; // 정밀도, 예측한 정답 중 실제 정답인 것 TP / (TP + FP) 모델 관점
    private final double specificity; //  it measures how much a classifier can recognize negative examples  TN / (TN + FP)
    private final double sensitivity; // 재현율, 실제 정답 중 예측에 성공한 것 TP / (TP + FN) 데이터 관점, 참인 정답이 적을 때 유효

    private final double f1Score; // precision과 recall의 조화평균, 2 / (1 / precision) + (1 / recall) 0에서 1사이의 값을 가지며 높을수록 좋음, recall과 precision의 조화 평균
    private final double geometricMean; // 균형 정확도, 참에 대한 정확도와, 거짓에 대한 정확도를 따로 분류해 기하 평균을 구함 sqrt((TP / (TP + FN)) * (TN / (FP + TN)))


    /**
     * 분류 모델의 오차행렬을 통해 평가 지표를 측정한다.
     * @param confusionMatrix 오차행렬
     */
    public MultinomialClassificationEvaluation(int[][] confusionMatrix) {

        if (confusionMatrix.length < 2 ||
                confusionMatrix.length != confusionMatrix[0].length) {
            throw new RuntimeException("Invalid confusion matrix");
        }

        this.itemNum = confusionMatrix.length;

         this.truePositive = new double[this.itemNum] ; // 참을 참이라고 한 횟수
         this.trueNegative = new double[this.itemNum] ; // 거짓을 거짓이라고 한 횟수
         this.falseNegative = new double[this.itemNum] ; // 참을 거짓이라고 한 횟수
         this.falsePositive = new double[this.itemNum] ; // 거짓을 참이라고 한 횟수

        double p = 0;
        double n = 0;
        // 실제 값에 대한 모델의 분류를 순서대로
        for (int i = 0; i < this.itemNum; i++) {
            for (int j = 0; j < this.itemNum ; j++) {
                int value = confusionMatrix[i][j];

                if (i == j) {
                    this.truePositive[j] = value;
                    p += value;
                } else {
                    n += value;
                }

                for (int k = 0; k < this.itemNum; k++) {
                    if ( k != i && k != j ) {
                        this.trueNegative[k] += value;
                    }
                    if (j == k && i != k) {
                        this.falsePositive[k] += value;
                    }

                    if (i == k && j != k) {
                        this.falseNegative[k] += value;
                    }
                }
            }
        }

        this.allPositive = p;
        this.allNegative = n;
        this.accuracy = p / (p + n);
        this.errorRate = 1 - this.accuracy;

        double precision = 0;
        double sensitivity = 0;
        double specificity = 0;
        for (int i = 0; i < this.itemNum; i++) {

            precision += this.truePositive[i] / (this.truePositive[i] + this.falsePositive[i]);

            sensitivity += this.truePositive[i] / (this.truePositive[i] + this.falseNegative[i]);

            specificity += this.trueNegative[i] / (this.trueNegative[i] + this.falsePositive[i]);
        }

        this.precision = precision / this.itemNum;
        this.sensitivity = sensitivity / this.itemNum;
        this.specificity = specificity / this.itemNum;


        this.f1Score = 2 / ((1 / this.precision) + (1 / this.sensitivity));
        this.geometricMean = Math.sqrt(this.sensitivity * this.specificity);

    }

    public double getAccuracy() {
        return this.accuracy;
    }
    public double getPrecision() {
        return this.precision;
    }
    private double getSensitivity() {
        return this.sensitivity;
    }
    public double getErrorRate() {
        return errorRate;
    }
    public int getItemNum() {
        return itemNum;
    }
    public double getAllPositive() {
        return allPositive;
    }
    public double getAllNegative() {
        return allNegative;
    }
    public double[] getTruePositive() {
        return truePositive;
    }
    public double[] getTrueNegative() {
        return trueNegative;
    }
    public double[] getFalseNegative() {
        return falseNegative;
    }
    public double[] getFalsePositive() {
        return falsePositive;
    }
    public double getSpecificity() {
        return specificity;
    }
    public double getF1Score() {
        return f1Score;
    }
    public double getGeometricMean() {
        return geometricMean;
    }


}
