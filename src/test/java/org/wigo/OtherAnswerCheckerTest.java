package org.wigo;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OtherAnswerCheckerTest {
    String filePath = "./data/answer.txt";
    String[] answerSheet = {"apple.", "orange.", "banana.", "melon."};
    String[] splitterSheetWrongCount3 = {"apple.", "orange.b", "anana.", "melo", "n."};
    String[] splitterSheetWrongCount4 = {"apple.orange.b", "anana.", "melo", "n."};
    String[] notSameStringWithAnswerSheet = {"apple.", "ornage.", "banana.", "melon."};
    String[] sameStringWithAnswerSheet = {"app  le.", "orange.    ", "ban", "a na.", "me  lon."};


    @Test
    public void testOtherAnswerCheckerInitWithFileName() {



        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))){
            for (String str : answerSheet) {
                bw.write(str + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        OtherAnswerChecker otherAnswerChecker = new OtherAnswerChecker("answer");

        int index = 0;
        for (String answer : answerSheet) {
            Assert.assertEquals(answer, otherAnswerChecker.getAnswerSheet()[index++]);

        }
    }

    @Test
    public void testInputSplitterSheet() {
        OtherAnswerChecker otherAnswerChecker = new OtherAnswerChecker("answer");
        otherAnswerChecker.initSplitterSheet(splitterSheetWrongCount3);

        int index = 0;
        for (String sheet : splitterSheetWrongCount3) {
            Assert.assertEquals(sheet, otherAnswerChecker.getSplitterSheet().get(index++));
        }

    }

    @Test
    public void testAnswerCheck() {
        OtherAnswerChecker otherAnswerChecker = new OtherAnswerChecker("answer");
        otherAnswerChecker.initSplitterSheet(splitterSheetWrongCount3);

        CheckResult checkResult = otherAnswerChecker.answerCheck();

        Assert.assertEquals(3, checkResult.getWrongCount());


        otherAnswerChecker.initSplitterSheet(splitterSheetWrongCount4);

        checkResult = otherAnswerChecker.answerCheck();

        Assert.assertEquals(4, checkResult.getWrongCount());
    }

    @Test
    public void testInvalidSplitterSheet() {
        boolean sameStringFlag = true;
        boolean notSameStringFlag = false;
        OtherAnswerChecker otherAnswerChecker = new OtherAnswerChecker("answer");

        try {
            otherAnswerChecker.initSplitterSheet(sameStringWithAnswerSheet);
        } catch (RuntimeException e) {
            sameStringFlag = false;
        }

        try {
            otherAnswerChecker.initSplitterSheet(notSameStringWithAnswerSheet);
        } catch (RuntimeException e) {
            notSameStringFlag = true;
        }

        Assert.assertTrue(sameStringFlag);
        Assert.assertTrue(notSameStringFlag);

    }
}
