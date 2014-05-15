package HighsoreService;

import models.QuizGame;
import play.Logger;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by willi on 5/14/14.
 */
public class HighscoreServiceImpl {

    public String sendGame(QuizGame game)
            throws SOAPException, IOException {
        SOAPMessage soapMessage = RequestGenerator.generateForQuizAndUserKey(game, "test");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapMessage.writeTo(out);
        System.out.println("==========================");
        Logger.error("HALLO");
        Logger.error(out.toString());
        return "";
    }

}
