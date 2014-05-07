package controllers;

import model.Users;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.authentication;

import javax.persistence.NoResultException;

/**
 * Created by willi on 5/7/14.
 */
public class UserManagement extends Controller {

    public static Result getAuthentication(){
        return ok(authentication.render(""));
    }

    public static Result postRegistrationData(){
        return ok("TEST");
    }

    private static boolean usernameExists(String username){
        if(getUserForUsername(username) != null){
            return true;
        }else{
            return false;
        }
    }


    @Transactional
    private static Users getUserForUsername(String username){
        try{
            return (Users) JPA.em().createNamedQuery("Users.findByUsername")
                    .setParameter("name", username)
                    .getSingleResult();
        }catch(NoResultException ex){
            return null;
        }
    }

}
