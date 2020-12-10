package org.moara.evaluation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * TODO 1. 좀 더 간단하게 변경
 *      2. 해당 평가자를 상속받게 하기
 */
public class EvaluatorImpl implements Evaluator {

    private final Integer[] positives;
    private final Integer[] negatives;

    private Integer[] sheets;
    private boolean isInitSheets = false;

    public EvaluatorImpl(Integer[] positives, Integer[] negatives) {
        this.positives = positives;
        this.negatives = negatives;
    }

    public EvaluatorImpl(int[] positives, int[] negatives) {

        this.positives = new Integer[positives.length];
        this.negatives = new Integer[negatives.length];

        for (int i = 0; i < positives.length; i++) {
            this.positives[i] = positives[i];
        }
        for (int i = 0; i < negatives.length; i++) {
            this.negatives[i] = negatives[i];
        }

    }

    @Override
    public void initSheet(int[] sheets) {
        this.sheets = new Integer[sheets.length];

        for (int i = 0; i < sheets.length; i++) {
            this.sheets[i] = sheets[i];
        }
        isInitSheets = true;
    }

    @Override
    public Evaluation answerCheck() {
        if (!isInitSheets) {
            throw new RuntimeException("Need to init sheets");
        }

        List<Integer> answerSplitPoints = new ArrayList(Arrays.asList(this.positives));
        List<Integer> answerNonSplitPoints = new ArrayList(Arrays.asList(this.negatives));
        List<Integer> splitterSplitPoints = new ArrayList(Arrays.asList(this.sheets));
        List<Integer> splitterNonSplitPoints = IntStream.range(0, this.positives.length + this.negatives.length + 1)
                .filter(point -> !splitterSplitPoints.contains(point)).boxed().collect(Collectors.toList());

        List<Integer> truePositivePoints = answerSplitPoints.stream()
                .filter(splitterSplitPoints::contains).collect(Collectors.toList());
        List<Integer> trueNegativePoints = answerNonSplitPoints.stream()
                .filter(splitterNonSplitPoints::contains).collect(Collectors.toList());
        List<Integer> falseNegativePoints = answerSplitPoints.stream()
                .filter(splitterNonSplitPoints::contains).collect(Collectors.toList());
        List<Integer> falsePositivePoints = answerNonSplitPoints.stream()
                .filter(splitterSplitPoints::contains).collect(Collectors.toList());

        int truePositive = truePositivePoints.size();
        int trueNegative = trueNegativePoints.size();
        int falseNegative = falseNegativePoints.size();
        int falsePositive = falsePositivePoints.size();

        return new Evaluation(truePositive, trueNegative, falseNegative, falsePositive);
    }

}
