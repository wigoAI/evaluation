package org.wigo;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SplitterAnswerCheckerTest {
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

        SplitterAnswerChecker splitterAnswerChecker = new SplitterAnswerChecker("answer");

        int index = 0;
        for (String answer : answerSheet) {
            Assert.assertEquals(answer, splitterAnswerChecker.getAnswerSheet()[index++]);

        }
    }

    @Test
    public void testInputSplitterSheet() {
        SplitterAnswerChecker splitterAnswerChecker = new SplitterAnswerChecker("answer");
        splitterAnswerChecker.initSplitterSheet(splitterSheetWrongCount3);

        int index = 0;
        for (String sheet : splitterSheetWrongCount3) {
            Assert.assertEquals(sheet, splitterAnswerChecker.getSplitterSheet().get(index++));
        }

    }

    @Test
    public void testAnswerCheck() {
        SplitterAnswerChecker splitterAnswerChecker = new SplitterAnswerChecker("answer");
        splitterAnswerChecker.initSplitterSheet(splitterSheetWrongCount3);

        CheckResult checkResult = splitterAnswerChecker.answerCheck();

        Assert.assertEquals(2, checkResult.getTruePositive(), 0.001);
        Assert.assertEquals(20, checkResult.getTrueNegative(), 0.001);
        Assert.assertEquals(2, checkResult.getFalsePositive(), 0.001);
        Assert.assertEquals(1, checkResult.getFalseNegative(), 0.001);

        splitterAnswerChecker.initSplitterSheet(splitterSheetWrongCount4);

        checkResult = splitterAnswerChecker.answerCheck();

        Assert.assertEquals(1, checkResult.getTruePositive(), 0.001);
        Assert.assertEquals(20, checkResult.getTrueNegative(), 0.001);
        Assert.assertEquals(2, checkResult.getFalsePositive(), 0.001);
        Assert.assertEquals(2, checkResult.getFalseNegative(), 0.001);

        splitterAnswerChecker.initSplitterSheet(splitterSheetWrongCount5);

        checkResult = splitterAnswerChecker.answerCheck();

        Assert.assertEquals(3, checkResult.getTruePositive(), 0.001);
        Assert.assertEquals(0, checkResult.getTrueNegative(), 0.001);
        Assert.assertEquals(22, checkResult.getFalsePositive(), 0.001);
        Assert.assertEquals(0, checkResult.getFalseNegative(), 0.001);

        splitterAnswerChecker.initSplitterSheet(splitterSheetWrongCount6);
        checkResult = splitterAnswerChecker.answerCheck();

        Assert.assertEquals(0, checkResult.getTruePositive(), 0.001);
        Assert.assertEquals(22, checkResult.getTrueNegative(), 0.001);
        Assert.assertEquals(0, checkResult.getFalsePositive(), 0.001);
        Assert.assertEquals(3, checkResult.getFalseNegative(), 0.001);

    }

    @Test
    public void testInvalidSplitterSheet() {
        boolean sameStringFlag = true;
        boolean notSameStringFlag = false;
        SplitterAnswerChecker splitterAnswerChecker = new SplitterAnswerChecker("answer");

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
