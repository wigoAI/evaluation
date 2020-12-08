package org.wigo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SplitterAnswerChecker implements AnswerChecker {
    private final String[] answerSheet;
    private final Integer[] answerSplitPoints;
    private final Integer[] answerNonSplitPoints;
    private final String answerStr;

    private List<String> splitterSheet;

    public SplitterAnswerChecker(String fileName) {
        this.answerSheet = getSheetByFile(fileName)
                .stream().map(sheet -> sheet.replace(" ", ""))
                .toArray(String[]::new);

        List<Integer> answerSplitPoints =  getSplitPoints(Arrays.asList(answerSheet.clone()));
        Integer[] tmpAnswerSplitPoints = new Integer[answerSplitPoints.size()];
        for (int i = 0; i < tmpAnswerSplitPoints.length; i++) {
            tmpAnswerSplitPoints[i] = answerSplitPoints.get(i);
        }
        this.answerSplitPoints = tmpAnswerSplitPoints;


        StringBuilder answerSheetString = new StringBuilder();
        Arrays.stream(this.answerSheet).map(sheet -> sheet = sheet.replace(" ", ""))
                .forEach(answerSheetString::append);
        this.answerStr = answerSheetString.toString();

        this.answerNonSplitPoints = IntStream.range(1, answerStr.length())
                .filter(point -> !answerSplitPoints.contains(point)).boxed().toArray(Integer[]::new);


    }

    public void initSplitterSheet(String[] splitterSheet) {
        initSplitterSheet(Arrays.asList(splitterSheet.clone()));
    }

    public void initSplitterSheet(String fileName) {
        List<String> splitterSheet = getSheetByFile(fileName);
        initSplitterSheet(splitterSheet);
    }

    public void initSplitterSheet(List<String> splitterSheet) {
        if (!isValidSplitterSheet(splitterSheet)) {
            throw new RuntimeException("Invalid splitter sheet");
        }

        this.splitterSheet = splitterSheet;
    }

    private boolean isValidSplitterSheet(List<String> sheets) {
        StringBuilder sheetString = new StringBuilder();

        sheets.stream().map(sheet -> sheet = sheet.replace(" ", ""))
                .forEach(sheetString::append);

        return answerStr.equals(sheetString.toString());
    }

    @Override
    public CheckResult answerCheck() {
        List<Integer> answerSplitPoints = new ArrayList(Arrays.asList(this.answerSplitPoints));
        List<Integer> answerNonSplitPoints = new ArrayList(Arrays.asList(this.answerNonSplitPoints));
        List<Integer> splitterSplitPoints = getSplitPoints(splitterSheet);
        List<Integer> splitterNonSplitPoints = IntStream.range(1, answerStr.length())
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

        return new CheckResult(truePositive, trueNegative, falseNegative, falsePositive);
    }


    private List<Integer> getSplitPoints(List<String> sheets) {

        List<Integer> sheetSplitPoints = new LinkedList<>();

        int previousSplitPoint = 0;
        for (int i = 0; i < sheets.size() - 1 ; i++) {
            previousSplitPoint += sheets.get(i).length();
            sheetSplitPoints.add(previousSplitPoint);
        }


        return sheetSplitPoints;
    }


    private List<String> getSheetByFile(String fileName) {
        List<String> sheet = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./data/" + fileName + ".txt"), StandardCharsets.UTF_8))) {

            while (true) {
                String line = br.readLine();
                if (line == null) { break; }
                sheet.add(line);
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

        return sheet;
    }

    public String[] getAnswerSheet() { return answerSheet; }
    public List<String> getSplitterSheet() { return splitterSheet; }
}
