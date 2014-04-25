package at.ac.tuwien.big.we14.lab2.api.service.impl;

import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.domain.Game;
import at.ac.tuwien.big.we14.lab2.api.service.GameService;

import java.util.List;

/**
 * Created by willi on 4/23/14.
 */
public class GameServiceImpl implements GameService{
    @Override
    public Game createNewGameWithRandomQuestions() {
        return null;
    }

    @Override
    public void startGame(Game game) {

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
        return false;
    }

    @Override
    public boolean checkRoundComplete(Game game) {
        return false;
    }

    @Override
    public boolean checkFinish(Game game) {
        return false;
    }
}
