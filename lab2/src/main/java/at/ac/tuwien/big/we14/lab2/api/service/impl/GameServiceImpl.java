package at.ac.tuwien.big.we14.lab2.api.service.impl;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Choice;
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
        
        g.setPlayer1Name("Spieler");
        g.setPlayer2Name("Computer");

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
        // check if answer is correct
        List<Choice> correctChoices = getActualQuestion(game).getCorrectChoices();
        boolean correct = true;
        if(choices.size() != getActualQuestion(game).getCorrectChoices().size()){
            correct = false;
        }
        else {
            for (Choice correctChoice : correctChoices){
                if (!choices.contains(correctChoice.getId())){
                    correct = false;
                    break;
                }
            }
        }

        if(correct){
            game.getActualRound().getActualAnswer().setPlayer1AnswerStatus(AnswerStatus.answered_correct);
        }
        else {
            game.getActualRound().getActualAnswer().setPlayer1AnswerStatus(AnswerStatus.answered_failed);
        }

        // answer Player 2
        double rnd = Math.random();
        if (rnd < 0.5){
            game.getActualRound().getActualAnswer().setPlayer2AnswerStatus(AnswerStatus.answered_failed);
        }
        else {
            game.getActualRound().getActualAnswer().setPlayer2AnswerStatus(AnswerStatus.answered_correct);
        }

        if (checkRoundComplete(game)){
            //TODO check who won the round and set correct status

            // TODO check if game ended
        }



    }

    public int getRoundsWonPlayer1(Game game){
        int roundsWon = 0;
        for (Round round : game.getRounds()){
            if (round.getRoundStatus() == RoundStatus.closed_player1Won){
                roundsWon++;
            }
        }
        return roundsWon;
    }

    public int getRoundsWonPlayer2(Game game){
        int roundsWon = 0;
        for (Round round : game.getRounds()){
            if (round.getRoundStatus() == RoundStatus.closed_player2Won){
                roundsWon++;
            }
        }
        return roundsWon;
    }

    @Override
    public Question getActualQuestion(Game game) {
        return game.getActualRound().getActualAnswer().getQuestion();

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

       return game.getActualRound().getAnswers().indexOf(game.getActualRound().getActualAnswer()) >= 2;



    }

    @Override
    public boolean checkFinish(Game game) {
        if (game.getRounds().indexOf(game.getActualRound()) >= 4 && game.getActualRound().getRoundStatus() != RoundStatus.open){
            return true;
        }
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
