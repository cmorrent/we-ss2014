package at.ac.tuwien.big.we14.lab2.api.inputLogic;

import at.ac.tuwien.big.we14.lab2.api.inputLogic.Event.Event;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by willi on 4/19/14.
 */
public interface RequestToEventConverter {

    public Event render(HttpServletRequest request)
        throws InvalidInputException;

}
