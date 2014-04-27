package at.ac.tuwien.big.we14.lab2.api.domain;

import java.util.List;

/**
 * Created by willi on 4/9/14.
 */
public class Game {
    private List<Round> rounds;

    private GameStatus gameStatus;

    private Round actualRound;
    
    private String player1Name;
    private String player2Name;


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
    
    public void setPlayer1Name(String name) {
    	this.player1Name = name;
    }
    
    public String getPlayer1Name() {
    	return player1Name;
    }
    
    public void setPlayer2Name(String name) {
    	this.player2Name = name;
    }
    
    public String getPlayer2Name() {
    	return player2Name;
    }

    public int getPlayer1WonRounds(){
        int count = 0;
        for (Round round : this.rounds){
            if(round.getRoundStatus() == RoundStatus.closed_player1Won) {
                count++;
            }
        }
        return count;
    }

    public int getPlayer2WonRounds(){
        int count = 0;
        for (Round round : this.rounds){
            if(round.getRoundStatus() == RoundStatus.closed_player2Won) {
                count++;
            }
        }
        return count;
    }
}
