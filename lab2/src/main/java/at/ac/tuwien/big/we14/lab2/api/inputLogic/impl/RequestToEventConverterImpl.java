package at.ac.tuwien.big.we14.lab2.api.inputLogic.impl;

import at.ac.tuwien.big.we14.lab2.api.inputLogic.Event.Event;
import at.ac.tuwien.big.we14.lab2.api.inputLogic.Event.EventType;
import at.ac.tuwien.big.we14.lab2.api.inputLogic.InvalidInputException;
import at.ac.tuwien.big.we14.lab2.api.inputLogic.RequestToEventConverter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by willi on 4/19/14.
 */
public class RequestToEventConverterImpl implements RequestToEventConverter{

    public final String eventStringQuestionsAnswered = "action_question";

    public final String eventStringStart = "action_start";

    public final String eventStringNextQuestionFromRoundComplete = "action_nextQuestionRoundComplete";

    public final String eventStringGotoFinish = "action_gotoFinish";


    @Override
    public Event render(HttpServletRequest request) throws InvalidInputException {
        if(request.getParameter("action") == null){
            return Event.initWithEventType(EventType.idle);
        }else{
            return getActionEventFromRequest(request);
        }
    }


    public Event getActionEventFromRequest(HttpServletRequest request) throws InvalidInputException {
        String action = request.getParameter("action");

        if(action.equals(eventStringQuestionsAnswered)){
            return getAnswerEventFromRequest(request);
        }else if(action.equals(eventStringStart)){
            return Event.initWithEventType(EventType.start);
        }else if(action.equals(eventStringNextQuestionFromRoundComplete)){
            return Event.initWithEventType(EventType.gotoNextQuestionFromRoundComplete);
        }else if(action.equals(eventStringGotoFinish)){
           return Event.initWithEventType(EventType.gotoFinish);
        }else{
            throw new InvalidInputException("Invalid action: " + action);
        }
    }


    public Event getAnswerEventFromRequest(HttpServletRequest request)
            throws InvalidInputException {

        Event e = new Event();
        int questionId = parseIntFromParamerterString(request.getParameter("question_id"));
        List<Integer> answerIds = getAnswerIdListFromRequest(request);

        e.setEventType(EventType.answer);
        e.setChoiceIds(answerIds);
        e.setQuestionId(questionId);

        return e;
    }


    public List<Integer> getAnswerIdListFromRequest(HttpServletRequest request)
            throws InvalidInputException {

        int questionCount = parseIntFromParamerterString(request.getParameter("question_id"));
        List<Integer> answerList = new ArrayList<Integer>();

        for(int i = 0; i < questionCount; i++){
            String paramKey = "option" + i;
            String paramValueString = request.getParameter(paramKey);
            if(paramValueString != null){
                answerList.add(parseIntFromParamerterString(paramValueString));
            }
        }

        return answerList;
    }


    public int parseIntFromParamerterString(String pString) throws InvalidInputException {
        try{
            return Integer.parseInt(pString);
        }catch(NumberFormatException ex){
            throw new InvalidInputException();
        }
    }
}
