package org.wigo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OtherAnswerChecker {
    private final String[] answerSheet;
    private final String answerSheetString;
    private List<String> splitterSheet;
    public OtherAnswerChecker(String fileName) {
        List<String> answerSheet = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./data/" + fileName + ".txt"), StandardCharsets.UTF_8))) {


            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                answerSheet.add(line);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        this.answerSheet = answerSheet.toArray(new String[0]);
        answerSheetString = linkSheetList(this.answerSheet);
    }

    public String[] getAnswerSheet() {
        return answerSheet;
    }

    public void initSplitterSheet(String[] splitterSheet) {
        if (!answerSheetString.equals(linkSheetList(splitterSheet))) {
            throw new RuntimeException("Invalid splitter sheet");
        }

        this.splitterSheet = Arrays.asList(splitterSheet.clone());
    }

    public List<String> getSplitterSheet() {
        return splitterSheet;
    }

    public CheckResult answerCheck() {

        StringBuilder answerStr = new StringBuilder();
        List<Integer> answerSplitPoints = new LinkedList<>();
        int previousSplitPoint = 0;
        for (String answer : answerSheet) {
            answerStr.append(answerStr);
            previousSplitPoint += answer.length();
            answerSplitPoints.add(previousSplitPoint);
        }

        StringBuilder splitterStr = new StringBuilder();
        List<Integer> splitterSplitPoints = new LinkedList<>();
        previousSplitPoint = 0;
        for (String splitter : splitterSheet) {
            splitterStr.append(splitterStr);
            previousSplitPoint += splitter.length();
            splitterSplitPoints.add(previousSplitPoint);
        }

        List<Integer> correctSplitPoints = new ArrayList<>();
        answerSplitPoints.forEach(item -> {
            if(splitterSplitPoints.contains(item))
                correctSplitPoints.add(item);
        });

        int correctCount = correctSplitPoints.size();

        answerSplitPoints.removeIf(correctSplitPoints::contains);
        splitterSplitPoints.removeIf(correctSplitPoints::contains);

        int wrongCount = answerSplitPoints.size() + splitterSplitPoints.size();

        return new CheckResult(correctCount, wrongCount);
    }

    private String linkSheetList(String[] sheetList) {
        StringBuilder linkSheet = new StringBuilder();
        for (String sheet : sheetList) {
            linkSheet.append(sheet);
        }

        return linkSheet.toString().replace(" ", "");
    }
}
