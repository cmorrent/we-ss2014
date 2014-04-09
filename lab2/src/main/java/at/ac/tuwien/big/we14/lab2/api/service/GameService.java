package at.ac.tuwien.big.we14.lab2.api.service;

import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.domain.Game;

/**
 * Created by willi on 4/9/14.
 */
public interface GameService {
    public Game createNewGameWithRandomQuestions();

    public void updateGameWithChoice(Game game, int choice);

    public Question getActualQuestion(Game game);
}
