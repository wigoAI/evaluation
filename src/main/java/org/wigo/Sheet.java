package org.wigo;

public class Sheet {
    private String answer;
    private String submit;
    private String tmpAnswer;
    private String tmpSubmit;


    public Sheet() {
        this.answer = "";
        this.submit = "";
        this.tmpAnswer = "";
        this.tmpSubmit = "";
    }

    public String initSheet(String answer, String submit) {
        String result = "Answer : ";

        if(this.tmpAnswer.length() == 0) {
            this.answer = answer;
            result += this.answer;
        } else {
            this.answer = this.tmpAnswer;
        }

        result += "\nSubmit : ";

        if(this.tmpSubmit.length() == 0) {
            this.submit = submit;
            result += this.submit;

        } else {
            this.submit = this.tmpSubmit;
        }

        return result + "\n";
    }


    public void setTmpAnswer() {
        this.tmpAnswer = this.answer.substring(
             this.answer.indexOf(this.submit) + this.submit.length()
        ).replace(" ", "");
    }
    public void setTmpSubmit() {
        this.tmpSubmit = this.submit.substring(
                this.submit.indexOf(this.answer) + this.answer.length()
        ).replace(" ", "");
    }
    public void setTmpAnswer(String tmpAnswer) {
        this.tmpAnswer = tmpAnswer;
    }
    public void setTmpSubmit(String tmpSubmit) {
        this.tmpSubmit = tmpSubmit;
    }


    public boolean isSubmitContainsAnswer() {
        return this.submit.contains(this.answer);
    }
    public boolean isAnswerContainsSubmit() {
        return this.answer.contains(this.submit);
    }
    public boolean isTmpAnswerEmpty() {
        if(this.tmpAnswer.length() == 0)
            return true;
        else
            return false;
    }
    public boolean isTmpSubmitEmpty() {
        if(this.tmpSubmit.length() == 0)
            return true;
        else
            return false;
    }
    public boolean isCorrect() {
        if(this.answer.equals(submit))
            return true;
        else
            return false;
    }
}
