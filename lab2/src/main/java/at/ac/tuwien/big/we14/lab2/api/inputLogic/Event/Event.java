package at.ac.tuwien.big.we14.lab2.api.inputLogic.Event;

import java.util.List;

/**
 * Created by willi on 4/19/14.
 */
public class Event {
    private EventType eventType;
    private int questionId;
    private List<Integer> choiceIds;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public List<Integer> getChoiceIds() {
        return choiceIds;
    }

    public void setChoiceIds(List<Integer> choiceIds) {
        this.choiceIds = choiceIds;
    }
}
