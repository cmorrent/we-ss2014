package at.ac.tuwien.big.we14.lab2.servlet;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we14.lab2.api.QuizFactory;
import at.ac.tuwien.big.we14.lab2.api.domain.DisplayPageEnum;
import at.ac.tuwien.big.we14.lab2.api.domain.Game;
import at.ac.tuwien.big.we14.lab2.api.domain.GameStatus;
import at.ac.tuwien.big.we14.lab2.api.impl.ServletQuizFactory;
import at.ac.tuwien.big.we14.lab2.api.inputLogic.Event.Event;
import at.ac.tuwien.big.we14.lab2.api.inputLogic.Event.EventType;
import at.ac.tuwien.big.we14.lab2.api.inputLogic.InvalidInputException;
import at.ac.tuwien.big.we14.lab2.api.inputLogic.RequestToEventConverter;
import at.ac.tuwien.big.we14.lab2.api.inputLogic.impl.RequestToEventConverterImpl;
import at.ac.tuwien.big.we14.lab2.api.service.GameService;
import at.ac.tuwien.big.we14.lab2.api.service.RoundService;
import at.ac.tuwien.big.we14.lab2.api.service.impl.GameServiceImpl;
import at.ac.tuwien.big.we14.lab2.api.service.impl.RoundServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BigQuizServlet extends HttpServlet{

    private Logger log = Logger.getLogger(this.getClass().getName());
    private RequestToEventConverter requestToEventConverter;
    private GameService gameService;
    private RoundService roundService;


    public void init(){
        requestToEventConverter = new RequestToEventConverterImpl();
        roundService = new RoundServiceImpl();
        initGameService();
    }


    public void initGameService(){
        GameServiceImpl gs = new GameServiceImpl();

        ServletContext servletContext = getServletContext();
        QuizFactory factory = new ServletQuizFactory(servletContext);
        QuestionDataProvider provider = factory.createQuestionDataProvider();
        List<Category> categories = provider.loadCategoryData();
        gs.setCategories(categories);

        gs.setRoundService(roundService);

        gameService = gs;
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        DisplayPageEnum displayPageEnum = null;

        try {
            Event event = requestToEventConverter.render(request);
            Game game = getGameFromContext(request);
            executeEventOnGame(event, game);
            displayPageEnum = getDisplayPageForEventAndGame(event, game);
        } catch (InvalidInputException e) {
            displayPageEnum = DisplayPageEnum.error;
        }

        log.log(Level.SEVERE, "Dispatch to page:" + getPageName(displayPageEnum));
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(getPageName(displayPageEnum));
        dispatcher.forward(request, response);
    }


    private void executeEventOnGame(Event event, Game game){
        if(event.getEventType() == EventType.start){
            gameService.startGame(game);
        }else if (event.getEventType() == EventType.answer){
            executeAnserEventOnGame(event, game);
        }else if (event.getEventType() == EventType.restart){
            executeRestartEventOnGame(event, game);
        }
    }
    

    private void executeAnserEventOnGame(Event event, Game game){
        if(event.getQuestionId() == gameService.getActualQuestion(game).getId()){
            List<Integer> choices = event.getChoiceIds();
            gameService.updateGameWithChoices(game, choices);
        }
    }


    private void executeRestartEventOnGame(Event event, Game game){
        if(game.getGameStatus() != GameStatus.open){
            gameService.resetGame(game);
        }
    }


    private Game getGameFromContext(HttpServletRequest request){
        Game game = null;
        HttpSession session = request.getSession();

        if(session.getAttribute("game") != null){
           game = (Game)session.getAttribute("game");
        }else{
           game = gameService.createNewGameWithRandomQuestions();
           session.setAttribute("game", game);
        }

        return game;
    }


    private DisplayPageEnum getDisplayPageForEventAndGame(Event event, Game game){
        if(gameService.checkNotStarted(game)){
            return DisplayPageEnum.start;
        }else if(gameService.checkFinish(game)){
            return getDisplayPageForFinishedGame(event);
        }else if(gameService.checkRoundComplete(game)){
            return DisplayPageEnum.roundcomplete;
        }else{
            return DisplayPageEnum.question;
        }
    }


    private DisplayPageEnum getDisplayPageForFinishedGame(Event event){
        if(event.getEventType() == EventType.gotoFinish){
            return DisplayPageEnum.finish;
        }else{
            return DisplayPageEnum.roundcomplete;
        }
    }


    private String getPageName(DisplayPageEnum displayPage){
        return  "/" + displayPage.toString() + ".jsp";
    }

}