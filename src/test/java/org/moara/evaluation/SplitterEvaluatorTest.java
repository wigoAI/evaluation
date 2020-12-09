package org.moara.evaluation;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SplitterEvaluatorTest {
    String filePath = "./data/answer.txt";

    String[] answerSheet = {"apple.", "orange.", "banana.", "melon."};
    String[] splitterSheetWrongCount3 = {"apple.", "orange.b", "anana.", "melo", "n."};
    String[] splitterSheetWrongCount4 = {"apple.orange.b", "anana.", "melo", "n."};
    String[] splitterSheetWrongCount5 = {"a", "p", "p", "l", "e", ".", "o", "r", "a", "n", "g", "e", ".", "b", "a", "n", "a", "n", "a", ".", "m", "e", "l", "o", "n", "."};
    String[] splitterSheetWrongCount6 = {"apple.orange.banana.melon."};
    String[] notSameStringWithAnswerSheet = {"apple.", "ornage.", "banana.", "melon."};
    String[] sameStringWithAnswerSheet = {"app  le.", "orange.    ", "ban", "a na.", "me  lon."};


    @Test
    public void testAnswerCheckerInitWithFileName() {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            for (String str : answerSheet) {
                bw.write(str + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SplitterEvaluator splitterAnswerChecker = new SplitterEvaluator("answer");

        int index = 0;
        for (String answer : answerSheet) {
            Assert.assertEquals(answer, splitterAnswerChecker.getAnswerSheet()[index++]);

        }
    }

    @Test
    public void testInputSplitterSheet() {
        SplitterEvaluator splitterAnswerChecker = new SplitterEvaluator("answer");
        splitterAnswerChecker.initSplitterSheet(splitterSheetWrongCount3);

        int index = 0;
        for (String sheet : splitterSheetWrongCount3) {
            Assert.assertEquals(sheet, splitterAnswerChecker.getSplitterSheet().get(index++));
        }

    }

    @Test
    public void testAnswerCheck() {
        SplitterEvaluator splitterAnswerChecker = new SplitterEvaluator("answer");
        splitterAnswerChecker.initSplitterSheet(splitterSheetWrongCount3);

        Evaluation evaluation = splitterAnswerChecker.answerCheck();

        Assert.assertEquals(2, evaluation.getTruePositive(), 0.001);
        Assert.assertEquals(20, evaluation.getTrueNegative(), 0.001);
        Assert.assertEquals(2, evaluation.getFalsePositive(), 0.001);
        Assert.assertEquals(1, evaluation.getFalseNegative(), 0.001);

        splitterAnswerChecker.initSplitterSheet(splitterSheetWrongCount4);

        evaluation = splitterAnswerChecker.answerCheck();

        Assert.assertEquals(1, evaluation.getTruePositive(), 0.001);
        Assert.assertEquals(20, evaluation.getTrueNegative(), 0.001);
        Assert.assertEquals(2, evaluation.getFalsePositive(), 0.001);
        Assert.assertEquals(2, evaluation.getFalseNegative(), 0.001);

        splitterAnswerChecker.initSplitterSheet(splitterSheetWrongCount5);

        evaluation = splitterAnswerChecker.answerCheck();

        Assert.assertEquals(3, evaluation.getTruePositive(), 0.001);
        Assert.assertEquals(0, evaluation.getTrueNegative(), 0.001);
        Assert.assertEquals(22, evaluation.getFalsePositive(), 0.001);
        Assert.assertEquals(0, evaluation.getFalseNegative(), 0.001);

        splitterAnswerChecker.initSplitterSheet(splitterSheetWrongCount6);
        evaluation = splitterAnswerChecker.answerCheck();

        Assert.assertEquals(0, evaluation.getTruePositive(), 0.001);
        Assert.assertEquals(22, evaluation.getTrueNegative(), 0.001);
        Assert.assertEquals(0, evaluation.getFalsePositive(), 0.001);
        Assert.assertEquals(3, evaluation.getFalseNegative(), 0.001);

    }

    @Test
    public void testInvalidSplitterSheet() {
        boolean sameStringFlag = true;
        boolean notSameStringFlag = false;
        SplitterEvaluator splitterAnswerChecker = new SplitterEvaluator("answer");

        try {
            splitterAnswerChecker.initSplitterSheet(sameStringWithAnswerSheet);
        } catch (RuntimeException e) {
            sameStringFlag = false;
        }

        try {
            splitterAnswerChecker.initSplitterSheet(notSameStringWithAnswerSheet);
        } catch (RuntimeException e) {
            notSameStringFlag = true;
        }

        Assert.assertTrue(sameStringFlag);
        Assert.assertTrue(notSameStringFlag);
    }
}
