package HighsoreService;

import models.QuizGame;
import play.Logger;

import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by willi on 5/14/14.
 */
public class HighscoreServiceImpl {

    public String sendGame(QuizGame game)
            throws SOAPException, IOException {
        SOAPMessage soapMessage = RequestGenerator.generateForQuizAndUserKey(game, "rkf4394dwqp49x");

        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
        String url = "http://playground.big.tuwien.ac.at:8080/highscore/PublishHighScoreService";
        SOAPMessage soapResponse = soapConnection.call(soapMessage, url);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            Source sourceContent = soapResponse.getSOAPPart().getContent();
            System.out.print("\nResponse SOAP Message = ");
            StreamResult result = new StreamResult(out);
            transformer = transformerFactory.newTransformer();
            transformer.transform(sourceContent, result);
            Logger.error(out.toString());
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();

        } catch (TransformerException e) {
            e.printStackTrace();
        }



        /**
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapMessage.writeTo(out);
        System.out.println("==========================");
        Logger.error("HALLO");

         **/
        return "";
    }

}
