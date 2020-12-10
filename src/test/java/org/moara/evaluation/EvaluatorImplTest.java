package org.moara.evaluation;

import org.junit.Test;

public class EvaluatorImplTest {

    @Test
    public void testEvaluatorImpl() {
        int[] positive = { 5, 7};
        int[] negative = {0, 1, 2, 3, 4, 6, 8, 9};
        int[] sheets = {2, 5};
        Evaluator evaluator = new EvaluatorImpl(positive, negative);

        evaluator.initSheet(sheets);

        Evaluation evaluation = evaluator.answerCheck();

        System.out.println(evaluation.getTruePositive());
        System.out.println(evaluation.getFalseNegative());
        System.out.println(evaluation.getTrueNegative());
        System.out.println(evaluation.getFalsePositive());
    }
}
