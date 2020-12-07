package org.wigo;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AnswerCheckerTest {
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

        AnswerChecker answerChecker = new AnswerChecker("answer");

        int index = 0;
        for (String answer : answerSheet) {
            Assert.assertEquals(answer, answerChecker.getAnswerSheet()[index++]);

        }
    }

    @Test
    public void testInputSplitterSheet() {
        AnswerChecker answerChecker = new AnswerChecker("answer");
        answerChecker.initSplitterSheet(splitterSheetWrongCount3);

        int index = 0;
        for (String sheet : splitterSheetWrongCount3) {
            Assert.assertEquals(sheet, answerChecker.getSplitterSheet().get(index++));
        }

    }

    @Test
    public void testAnswerCheck() {
        AnswerChecker answerChecker = new AnswerChecker("answer");
        answerChecker.initSplitterSheet(splitterSheetWrongCount3);

        CheckResult checkResult = answerChecker.answerCheck();

        Assert.assertEquals(2, checkResult.getTruePositive(), 0.001);
        Assert.assertEquals(20, checkResult.getTrueNegative(), 0.001);
        Assert.assertEquals(2, checkResult.getFalsePositive(), 0.001);
        Assert.assertEquals(1, checkResult.getFalseNegative(), 0.001);
        System.out.println("F1 score : " + checkResult.getF1Score());
        System.out.println("Accuracy : " + checkResult.getAccuracy());
        System.out.println("Precision : " + checkResult.getPrecision());
        System.out.println("Recall : " + checkResult.getRecall());
        System.out.println();

        answerChecker.initSplitterSheet(splitterSheetWrongCount4);

        checkResult = answerChecker.answerCheck();

        Assert.assertEquals(1, checkResult.getTruePositive(), 0.001);
        Assert.assertEquals(20, checkResult.getTrueNegative(), 0.001);
        Assert.assertEquals(2, checkResult.getFalsePositive(), 0.001);
        Assert.assertEquals(2, checkResult.getFalseNegative(), 0.001);

        System.out.println("F1 score : " + checkResult.getF1Score());
        System.out.println("Accuracy : " + checkResult.getAccuracy());
        System.out.println("Precision : " + checkResult.getPrecision());
        System.out.println("Recall : " + checkResult.getRecall());
        System.out.println();
        answerChecker.initSplitterSheet(splitterSheetWrongCount5);

        checkResult = answerChecker.answerCheck();

//        Assert.assertEquals(1, checkResult.getTruePositive(), 0.001);
//        Assert.assertEquals(20, checkResult.getTrueNegative(), 0.001);
//        Assert.assertEquals(2, checkResult.getFalsePositive(), 0.001);
//        Assert.assertEquals(2, checkResult.getFalseNegative(), 0.001);

        System.out.println("F1 score : " + checkResult.getF1Score());
        System.out.println("Accuracy : " + checkResult.getAccuracy());
        System.out.println("Precision : " + checkResult.getPrecision());
        System.out.println("Recall : " + checkResult.getRecall());
        System.out.println();


        answerChecker.initSplitterSheet(splitterSheetWrongCount6);
        checkResult = answerChecker.answerCheck();

//        Assert.assertEquals(1, checkResult.getTruePositive(), 0.001);
//        Assert.assertEquals(20, checkResult.getTrueNegative(), 0.001);
//        Assert.assertEquals(2, checkResult.getFalsePositive(), 0.001);
//        Assert.assertEquals(2, checkResult.getFalseNegative(), 0.001);

        System.out.println("F1 score : " + checkResult.getF1Score());
        System.out.println("Accuracy : " + checkResult.getAccuracy());
        System.out.println("Precision : " + checkResult.getPrecision());
        System.out.println("Recall : " + checkResult.getRecall());
        System.out.println();
    }



    @Test
    public void testInvalidSplitterSheet() {
        boolean sameStringFlag = true;
        boolean notSameStringFlag = false;
        AnswerChecker answerChecker = new AnswerChecker("answer");

        try {
            answerChecker.initSplitterSheet(sameStringWithAnswerSheet);
        } catch (RuntimeException e) {
            sameStringFlag = false;
        }

        try {
            answerChecker.initSplitterSheet(notSameStringWithAnswerSheet);
        } catch (RuntimeException e) {
            notSameStringFlag = true;
        }

        Assert.assertTrue(sameStringFlag);
        Assert.assertTrue(notSameStringFlag);

    }



}
