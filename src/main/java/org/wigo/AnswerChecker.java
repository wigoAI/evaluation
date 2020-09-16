package org.wigo;


import org.wigo.fileManager.FileReader;

import java.util.ArrayList;
import java.util.List;

/**
 * 정답체크클래스
 * 줄바꿈으로 나눠진 정답 파일로 초기화한다.
 * 또는 리스트 형태의 정답 데이터로 초기화
 * 생성자로 접근하지 않고 인스턴스를 반환하는 메서드로 접근
 *
 * */
public class AnswerChecker {
    private List<String> answerList = new ArrayList<>();
    private int sizeOfAnswerList;


    private AnswerChecker(List<String> answerList) {
        this.answerList = answerList;
        this.sizeOfAnswerList = answerList.size();
    }
    private AnswerChecker(FileReader fileReader) {
        this(fileReader.getSplitFileByLine());
    }
    public static AnswerChecker setAnswerCheckerByAnswerFile(String answerFilePath) {
        FileReader fileReader = new FileReader(answerFilePath);
        return new AnswerChecker(fileReader);
    }

    public static AnswerChecker setAnswerCheckerByAnswerList(List<String> answerList) {
        return new AnswerChecker(answerList);
    }


    // 정답 체크 메서드
    public float checkAnswer(List<String> submittedSheet) {

        int sheetIndex = 0;
        int answerIndex = 0;
        float unCorrectCnt = 0;
        float correctCnt = 0;

        // 제출지 임시 저장소
        // 구분점을 지났을 때 사용
        Sheet sheet = new Sheet();

        while(answerIndex < this.getSizeOfAnswerList()) {

            String answerSheet = this.answerList.get(answerIndex).replace(" ", "");
            String submitSheet = submittedSheet.get(sheetIndex).replace(" ", "");


            // sheet 초기화
            System.out.println(sheet.initSheet(answerSheet, submitSheet));


            // 정답
            if( sheet.isCorrect() ) {
//                System.out.println("Correct!" );

                // 온전한 문장으로 나눴을 때 정답으로 인정한다.
                // 임시 정답지를 사용하지 않았다면 온전한 정답을 맞춘 것이다.
                // 임시 정답지는 올바르지 않은 문장 구분자를 사용하여 답보다 짧게 나누었을 때
                // 사용하기 때문이다.
                if(sheet.isTmpAnswerEmpty()) {
                    correctCnt++;
//                    System.out.println("and correct count!");
                }

            // 구분점을 지나쳤을 떄
            } else if(sheet.isSubmitContainsAnswer()){
//                System.out.println("pass splitter");


                unCorrectCnt++;

                // 구분점으로 임의로 자르기
                sheet.setTmpSubmit();

            // 잘못된 문장 구분점을 적용했을 때
            } else if(sheet.isAnswerContainsSubmit()){
//                System.out.println("unCorrect splitter");
                // 답안 문장은 잘문 구분된 문장을 포함하고 있을것이다.
                // 혹시 모르니 체크

                unCorrectCnt++;

                // 구분점을 임의로 자른다.
                sheet.setTmpAnswer();


            // 길이만 같고 답이 아닐 때, 혹은 길이가 긴 쪽이 짧은쪽을 포함 안할 때
            } else {
                /**
                 * 예외사항 생각할 것
                 *
                 * */
                System.out.println("something is wrong");
                break;

            }

            if(sheet.isAnswerContainsSubmit()){
                sheet.setTmpSubmit("");
                // 다음 문장 구분으로 이동
                sheetIndex++;
            }
            if(sheet.isSubmitContainsAnswer()) {
                sheet.setTmpAnswer("");
                // 다음 정답지로 이동
                answerIndex++;
            }


        }

        System.out.println("Answer List Size : " + this.sizeOfAnswerList
                + "\nWrong split count : " + unCorrectCnt
                + "\nCorrect split count : " + correctCnt);

        // 정확도 0.0 ~ 1.0
        return correctCnt / this.sizeOfAnswerList;
    }

    public static List<String> getExampleFiles() {
        List<String> exampleFiles = new ArrayList<>();

        for(int i = 1 ; i <= 3 ; i++) {
            FileReader fileReader = new FileReader("/data/data" + i + ".txt");
            exampleFiles.add(fileReader.getFile());
        }

        return exampleFiles;
    }

    public List<String> getAnswerList() {
        return this.answerList;
    }

    public int getSizeOfAnswerList() {
        return this.sizeOfAnswerList;
    }


}
