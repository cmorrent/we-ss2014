package HighsoreService;

import controllers.Quiz;
import models.QuizGame;
import models.QuizUser;

import javax.xml.soap.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by willi on 5/14/14.
 */
public class RequestGenerator {

    QuizGame quizGame;

    String userKey;

    SOAPMessage soapMessage;

    SOAPBody soapBody;

    private final String XML_BIG_DATA_NS = "http://big.tuwien.ac.at/we/highscore/data";

    private final String XML_BIG_DATA_PREFIX = "data";

    public static SOAPMessage generateForQuizAndUserKey(QuizGame quizGame, String userKey)
            throws SOAPException {
        RequestGenerator requestGenerator = new RequestGenerator(quizGame, userKey);
        return requestGenerator.generate();
    }


    private RequestGenerator(QuizGame quizGame, String userKey){
        this.quizGame = quizGame;
        this.userKey = userKey;
    }


    private SOAPMessage generate()
            throws SOAPException {
        setUpSOAPMessageAndBody();
        attachHighscoreRequest();
        soapMessage.saveChanges();
        return soapMessage;
    }


    private void setUpSOAPMessageAndBody()
            throws SOAPException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        this.soapMessage = messageFactory.createMessage();
        this.soapBody = soapMessage.getSOAPBody();
        soapBody.addNamespaceDeclaration("data", XML_BIG_DATA_NS);
    }


    private void attachHighscoreRequest()
            throws SOAPException {
        SOAPElement highScoreRequestElement = soapBody.addChildElement("HighScoreRequest", XML_BIG_DATA_PREFIX);
        attachUserKey(highScoreRequestElement);
        attachQuiz(highScoreRequestElement);
    }


    private SOAPElement attachUserKey(SOAPElement parent)
            throws SOAPException {
        SOAPElement soapElement = parent.addChildElement("UserKey", XML_BIG_DATA_PREFIX);
        soapElement.addTextNode(userKey);
        return parent;
    }


    private SOAPElement attachQuiz(SOAPElement parent)
            throws SOAPException {
        SOAPElement quizElement = parent.addChildElement("quiz");
        attachUsers(quizElement);
        return parent;
    }


    private SOAPElement attachUsers(SOAPElement parent)
            throws SOAPException {
        SOAPElement usersElement = parent.addChildElement("users");
        attachWinner(usersElement);
        attachLooser(usersElement);
        return usersElement;
    }


    private SOAPElement attachWinner(SOAPElement parent)
            throws SOAPException {
        QuizUser quizUser = quizGame.getWinner();
        SOAPElement userElement = attachUser(parent, quizUser);
        userElement.setAttribute("name", "winner");
        return userElement;
    }


    private SOAPElement attachLooser(SOAPElement parent)
            throws SOAPException {
        QuizUser quizUser = getGameLooser();
        SOAPElement userElement = attachUser(parent, quizUser);
        userElement.setAttribute("name", "looser");
        return userElement;
    }


    private QuizUser getGameLooser(){
        QuizUser winner = quizGame.getWinner();
        List<QuizUser> quizUsers = quizGame.getPlayers();
        quizUsers.remove(quizUsers);
        return quizUsers.get(0);
    }


    private SOAPElement attachUser(SOAPElement parent, QuizUser quizUser)
            throws SOAPException {
        SOAPElement userElement = parent.addChildElement("user");
        userElement.setAttribute("gender", getGenderStringForUser(quizUser));

        SOAPElement passwordElement = userElement.addChildElement("password");

        SOAPElement firstnameElement = userElement.addChildElement("firstname");
        firstnameElement.addTextNode(getFirstnameForUser(quizUser));

        SOAPElement lastnameElement = userElement.addChildElement("lastname");
        lastnameElement.addTextNode(getLastnaeForUser(quizUser));

        SOAPElement birthdateElement = userElement.addChildElement("birthdate");
        birthdateElement.addTextNode(getDateStringForUser(quizUser));
        return userElement;
    }


    private String getFirstnameForUser(QuizUser quizUser){
        if(quizUser.getFirstName() != null){
            return quizUser.getFirstName();
        }else{
            return "";
        }
    }


    private String getLastnaeForUser(QuizUser quizUser){
        if(quizUser.getLastName() != null){
            return quizUser.getLastName();
        }else{
            return "";
        }
    }


    private String getGenderStringForUser(QuizUser quizUser){
        if(quizUser.getGender() != null){
            return quizUser.getGender().toString();
        }else{
            return null;
        }
    }


    private String getDateStringForUser(QuizUser quizUser){
        if(quizUser.getBirthDate() != null){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(quizUser.getBirthDate());
        }else{
            return "";
        }
    }
}
