package HighsoreService.SimpleHighscoreService;

import HighsoreService.*;
import models.QuizGame;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import play.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;

/**
 * Created by willi on 5/14/14.
 */
public class SimpleHighscoreService implements HighscoreService{

    private HighscoreServiceConfig highscoreServiceConfig;

    public SimpleHighscoreService(HighscoreServiceConfig highscoreServiceConfig){
        this.highscoreServiceConfig = highscoreServiceConfig;
    }


    @Override
    public String sendGameAndReciveUUID(QuizGame game)
            throws HighscoreServiceException, SOAPException, InvalidUserDataException {
        SOAPMessage requestSoapMessage = SimpleRequestGenerator.generateForQuizAndUserKey(game, highscoreServiceConfig.getUserKey());
        SOAPMessage responseSoapMessage = sendRequestReciveResult(requestSoapMessage);
        return parseResponse(responseSoapMessage);
    }


    private SOAPMessage sendRequestReciveResult(SOAPMessage requestSOAPMessage)
            throws SOAPException {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            String url = highscoreServiceConfig.getUrl();
            return soapConnection.call(requestSOAPMessage, url);

    }


    private String parseResponse(SOAPMessage responseSOAPMessage)
            throws SOAPException, HighscoreServiceException {
        SOAPBody soapBody = responseSOAPMessage.getSOAPBody();
        String uuidContent = soapBody.getTextContent();

        checkError(responseSOAPMessage);

        return uuidContent;
    }


    private void checkError(SOAPMessage responseSoapMessage)
            throws HighscoreServiceException, SOAPException {
        SOAPBody soapBody = responseSoapMessage.getSOAPBody();

        Iterator<SOAPElement> bodyElements = soapBody.getChildElements();
        if(bodyElements.hasNext()){
            SOAPElement childElement = bodyElements.next();
            if(childElement.getLocalName().equals("Fault")) convertErrorToException(childElement);
        }
    }


    private void convertErrorToException(SOAPElement errorBody)
            throws HighscoreServiceException {
        int faultCode = 0;
        String reason = "";
        String detail = "";

        NodeList errorCodeElements = errorBody.getElementsByTagName("Code");
        if(errorCodeElements.getLength() > 0){
            faultCode = Integer.parseInt(errorCodeElements.item(0).getTextContent());
        }

        NodeList errorReasonElements = errorBody.getElementsByTagName("Reason");
        if(errorReasonElements.getLength() > 0){
            reason = errorReasonElements.item(0).getTextContent();
        }

        NodeList errorDetailElements = errorBody.getElementsByTagName("Detail");
        if(errorDetailElements.getLength() > 0){
            detail = errorDetailElements.item(0).getTextContent();
        }

        throw new HighscoreServiceException(detail, reason, faultCode);
    }


    /*
    private void logRequest(SOAPMessage soapResponse){
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            Source sourceContent = soapResponse.getSOAPPart().getContent();
            System.out.print("\nResponse SOAP Message = ");
            StreamResult result = new StreamResult(out);
            transformer = transformerFactory.newTransformer();
            transformer.transform(sourceContent, result);
            Logger.error(out.toString());
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
    */

}
