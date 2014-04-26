package at.ac.tuwien.big.we14.lab2.api.service.impl;

import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.domain.Game;
import at.ac.tuwien.big.we14.lab2.api.domain.GameStatus;
import at.ac.tuwien.big.we14.lab2.api.service.GameService;

import java.util.List;

/**
 * Created by willi on 4/23/14.
 */
public class GameServiceImpl implements GameService{
    @Override
    public Game createNewGameWithRandomQuestions() {
        Game g = new Game();
        g.setGameStatus(GameStatus.not_started);

        //TODO: Implemente init settings
        return g;
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
}
