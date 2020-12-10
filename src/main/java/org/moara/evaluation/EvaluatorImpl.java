package org.moara.evaluation;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EvaluatorImpl implements Evaluator {

    private final int[] positives;
    private final int[] negatives;

    private int[] sheets;
    private boolean isInitSheets = false;

    public EvaluatorImpl(int[] positives, int[] negatives) {
        this.positives = positives;
        this.negatives = negatives;

    }

    public void initSheet(int[] sheets) {
        this.sheets = sheets;
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
        List<Integer> splitterNonSplitPoints = IntStream.range(1, this.positives.length + this.negatives.length)
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
