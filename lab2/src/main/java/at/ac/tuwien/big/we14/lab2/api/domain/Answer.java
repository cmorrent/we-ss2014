package at.ac.tuwien.big.we14.lab2.api.domain;

import at.ac.tuwien.big.we14.lab2.api.Question;

import java.util.Date;

/**
 * Created by willi on 4/9/14.
 */
public class Answer {
    private Question question;

    private AnswerStatus player1AnswerStatus;

    private AnswerStatus player2AnswerStatus;

    private int player1AnswerTimeInSeconds;

    private int player2AnswerTimeInSeconds;

    private Date timeStamp;


    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public AnswerStatus getPlayer1AnswerStatus() {
        return player1AnswerStatus;
    }

    public void setPlayer1AnswerStatus(AnswerStatus player1AnswerStatus) {
        this.player1AnswerStatus = player1AnswerStatus;
    }

    public AnswerStatus getPlayer2AnswerStatus() {
        return player2AnswerStatus;
    }

    public void setPlayer2AnswerStatus(AnswerStatus player2AnswerStatus) {
        this.player2AnswerStatus = player2AnswerStatus;
    }

    public int getPlayer1AnswerTimeInSeconds() {
        return player1AnswerTimeInSeconds;
    }

    public void setPlayer1AnswerTimeInSeconds(int player1AnswerTimeInSeconds) {
        this.player1AnswerTimeInSeconds = player1AnswerTimeInSeconds;
    }

    public int getPlayer2AnswerTimeInSeconds() {
        return player2AnswerTimeInSeconds;
    }

    public void setPlayer2AnswerTimeInSeconds(int player2AnswerTimeInSeconds) {
        this.player2AnswerTimeInSeconds = player2AnswerTimeInSeconds;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
