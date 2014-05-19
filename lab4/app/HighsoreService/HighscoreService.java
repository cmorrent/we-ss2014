package HighsoreService;

import models.QuizGame;

import javax.xml.soap.SOAPException;

/**
 * Created by willi on 5/16/14.
 */
public interface HighscoreService {

    public String sendGameAndReciveUUID(QuizGame game)
            throws SOAPException, HighscoreServiceException, InvalidUserDataException;
}
