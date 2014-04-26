package at.ac.tuwien.big.we14.lab2.api.service.impl;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we14.lab2.api.domain.*;
import at.ac.tuwien.big.we14.lab2.api.impl.ServletQuizFactory;
import at.ac.tuwien.big.we14.lab2.api.service.GameService;
import at.ac.tuwien.big.we14.lab2.api.service.RoundService;
import at.ac.tuwien.big.we14.lab2.tools.RandomPoolSelector;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;

import javax.servlet.ServletContext;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by willi on 4/23/14.
 */
public class GameServiceImpl implements GameService{

    public final int CATEGORIES_PER_GAME_COUNT = 5;

    Logger log = Logger.getLogger(getClass().getName());

    RandomPoolSelector<Category> randomPoolSelector = new RandomPoolSelector<Category>();

    RoundService roundService;

    private List<Category> categories;

    @Override
    public Game createNewGameWithRandomQuestions() {
        Game g = new Game();
        List<Category> randomCategories = randomPoolSelector.getRandomPoolWithSizeForList(CATEGORIES_PER_GAME_COUNT, categories);
        List<Round> rounds = roundService.createRoundsWithAnswersFromCategories(randomCategories);

        g.setGameStatus(GameStatus.not_started);
        g.setRounds(rounds);
        g.setActualRound(rounds.get(0));

        //log.log(Level.SEVERE, g.getActualRound().getCategory().getName());

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


    public void setRoundService(RoundService roundService) {
        this.roundService = roundService;
    }
}
