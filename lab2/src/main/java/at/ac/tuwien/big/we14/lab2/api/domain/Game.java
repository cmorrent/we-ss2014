package at.ac.tuwien.big.we14.lab2.api.domain;

import java.util.List;

/**
 * Created by willi on 4/9/14.
 */
public class Game {
    private List<Round> rounds;

    private GameStatus gameStatus;

    private Round actualRound;


    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Round getActualRound() {
        return actualRound;
    }

    public void setActualRound(Round actualRound) {
        this.actualRound = actualRound;
    }
}
