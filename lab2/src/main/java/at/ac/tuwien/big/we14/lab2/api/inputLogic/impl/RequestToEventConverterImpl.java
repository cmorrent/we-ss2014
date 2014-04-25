package at.ac.tuwien.big.we14.lab2.api.inputLogic.impl;

import at.ac.tuwien.big.we14.lab2.api.inputLogic.Event.Event;
import at.ac.tuwien.big.we14.lab2.api.inputLogic.Event.EventType;
import at.ac.tuwien.big.we14.lab2.api.inputLogic.InvalidInputException;
import at.ac.tuwien.big.we14.lab2.api.inputLogic.RequestToEventConverter;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by willi on 4/19/14.
 */
public class RequestToEventConverterImpl implements RequestToEventConverter{
    @Override
    public Event render(HttpServletRequest request) throws InvalidInputException {
        if(request.getParameter("action") == null){
            return getIdleEvent();
        }else{
            return getActionEventFromRequest(request);
        }
    }

    public Event getIdleEvent(){
        Event e = new Event();
        e.setEventType(EventType.idle);
        return e;
    }

    public Event getActionEventFromRequest(HttpServletRequest request){
        String action = request.getParameter("action");
        //TODO: Implement
        return null;
    }
}
