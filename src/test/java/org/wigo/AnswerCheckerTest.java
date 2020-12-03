package org.wigo;

import org.junit.Assert;
import org.junit.Test;
import org.wigo.fileManager.FileReader;

import java.util.ArrayList;
import java.util.List;

public class AnswerCheckerTest {


    @Test
    public void setAnswerCheckerTest() {
        AnswerChecker answerChecker = AnswerChecker.setAnswerCheckerByAnswerFile("/data/answer1.txt");
        List<String> answerList = answerChecker.getAnswerList();

        for (String str : answerList) {
            System.out.println("[" + str + "]");
        }

    }

    @Test
    public void checkAllCorrect() {
        List<String> answer = new ArrayList<>();
        List<String> sheet = new ArrayList<>();
        answer.add("안녕하세요");
        answer.add("반갑습니다.");
        answer.add("전 조승현 입니다.");

        sheet.add("안녕하세요");
        sheet.add("반갑습니다.");
        sheet.add("전 조승현 입니다.");



        AnswerChecker answerChecker = AnswerChecker.setAnswerCheckerByAnswerList(answer);
        System.out.println(answerChecker.checkAnswer(sheet));

    }

    @Test
    public void getExampleFileTest() {
        List<String> exampleFiles = AnswerChecker.getExampleFiles();

        for(String str : exampleFiles) {
            System.out.println(str);
        }
    }

    @Test
    public void checkAnswerTest() {
        AnswerChecker answerChecker = AnswerChecker.setAnswerCheckerByAnswerFile("/data/answer3.txt");
        FileReader fileReader = new FileReader("/data/submit1_3.txt");
        List<String> submitList = fileReader.getSplitFileByLine();

        System.out.println(answerChecker.checkAnswer(submitList));
    }

}
