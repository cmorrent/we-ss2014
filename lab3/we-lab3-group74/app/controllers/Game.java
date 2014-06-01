package controllers;

import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;
import play.mvc.Http.*;
import play.cache.*;
import model.Users;
import at.ac.tuwien.big.we14.lab2.api.*;
import at.ac.tuwien.big.we14.lab2.api.impl.*;

/**
 * Created by willi on 5/7/14.
 */
public class Game extends Controller {

	private static QuizGame game;
	private static QuizFactory factory;
	private static int currentRoundCount = 0;

    @Security.Authenticated(Secured.class)
    public static Result getIndex(){
        return ok(index.render());
    }

    
    
    @Security.Authenticated(Secured.class)
    public static Result getQuestion() {
    	if(currentRoundCount == 0) {
    		factory = new PlayQuizFactory("conf/data.de.json", (Users) Cache.get("user")); 
			game = factory.createQuizGame();
			game.startNewRound();
			
			return ok(quiz.render(game));
    	}
    	return TODO;
    }
    
    public static Result startRound(){
    	return ok(index.render());
    }
  /*  
    @Security.Authenticated(Secured.class)
    public static Result answerQuestion(User player, List<Choice> choices, int time){
    	
    	game.answerCurrentQuestion(player, choices, time);
    	if (game.isRoundOver()){
    		return ok(roundover.render());
    	}
    	else {
    		return ok(quiz.render(game));
    	} 	
    }
  */  
    
    @Security.Authenticated(Secured.class)
    public static Result answerQuestion(){
    	if (game.isRoundOver()){
    		return ok(roundover.render(game));
    	}
    	else {
    		return ok(quiz.render(game));
    	}
    }
    
   
    
    public static Result getRoundOver(){
    	
    	return ok(roundover.render(game));
    }
    
    
    public static Result goToNextPage(){
    	return ok(index.render());
    }
    
    
    
}
