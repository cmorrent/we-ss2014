package at.ac.tuwien.big.we14.lab2.api.service;

import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.domain.Game;
import at.ac.tuwien.big.we14.lab2.api.impl.ServletQuizFactory;

import java.util.List;

/**
 * Created by willi on 4/9/14.
 */
public interface GameService {

    public Game createNewGameWithRandomQuestions();

    public void resetGame(Game game);

    public void startGame(Game game);

    public void updateGameWithChoices(Game game, List<Integer> choices);

    public Question getActualQuestion(Game game);

    public boolean checkNotStarted(Game game);

    public boolean checkRoundComplete(Game game);

    public boolean checkFinish(Game game);
}
