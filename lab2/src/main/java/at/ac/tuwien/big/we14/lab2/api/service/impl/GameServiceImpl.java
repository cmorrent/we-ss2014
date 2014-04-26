package at.ac.tuwien.big.we14.lab2.api.service.impl;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we14.lab2.api.domain.*;
import at.ac.tuwien.big.we14.lab2.api.impl.ServletQuizFactory;
import at.ac.tuwien.big.we14.lab2.api.service.GameService;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by willi on 4/23/14.
 */
public class GameServiceImpl implements GameService{

    Logger log = Logger.getLogger(getClass().getName());
    private List<Category> categories;

    @Override
    public Game createNewGameWithRandomQuestions() {
        Game g = new Game();
        g.setGameStatus(GameStatus.not_started);

        //TODO: Implemente init settings
        return g;
    }

    @Override
    public void startGame(Game game) {
        game.setGameStatus(GameStatus.open);

        Round round = game.getRounds().get(0);
        round.setRoundStatus(RoundStatus.open);
        game.setActualRound(round);

        Answer answer = round.getAnswers().get(0);
        answer.setTimeStamp(new Date());

    }

    @Override
    public void updateGameWithChoices(Game game, List<Integer> choices) {

    }

    @Override
    public Question getActualQuestion(Game game) {
        return null;
    }

    @Override
    public boolean checkNotStarted(Game game) {
        if(game.getGameStatus() == GameStatus.not_started){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean checkRoundComplete(Game game) {
        return false;
    }

    @Override
    public boolean checkFinish(Game game) {
        return false;
    }


    public List<Category> getCategories() {
        return categories;
    }


    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
