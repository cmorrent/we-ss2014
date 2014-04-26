package at.ac.tuwien.big.we14.lab2.api.domain;

import at.ac.tuwien.big.we14.lab2.api.Category;

import java.util.List;

/**
 * Created by willi on 4/9/14.
 */
public class Round {

    private Category category;

    private List<Answer> answers;

    private Answer actualAnswer;

    private RoundStatus roundStatus;

    private int playedQuestionCount;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public RoundStatus getRoundStatus() {
        return roundStatus;
    }

    public void setRoundStatus(RoundStatus roundStatus) {
        this.roundStatus = roundStatus;
    }

    public int getPlayedQuestionCount() {
        return playedQuestionCount;
    }

    public void setPlayedQuestionCount(int playedQuestionCount) {
        this.playedQuestionCount = playedQuestionCount;
    }

    public Answer getActualAnswer() {
        return actualAnswer;
    }

    public void setActualAnswer(Answer actualAnswer) {
        this.actualAnswer = actualAnswer;
    }
}
