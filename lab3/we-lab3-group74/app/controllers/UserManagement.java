package controllers;

import model.Users;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.authentication;
import views.html.registration;
import static play.data.Form.*;
import play.data.*;

import javax.persistence.NoResultException;

/**
 * Created by willi on 5/7/14.
 */
public class UserManagement extends Controller {


    public static Result getAuthentication(){
        return ok(authentication.render());
    }


    public static Result getRegistration(){
        return ok(registration.render(Form.form(Users.class)));
    }


    public static Result saveUser(){
        Form<Users> form = Form.form(Users.class).bindFromRequest();
        Users user = form.get();
        return ok("TEST" + user.getName());
    }
    /*s
    public static Result authenticate() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        return ok();
    }

    public String validate() {
        if (User.authenticate(email, password) == null) {
            return "Invalid user or password";
        }
        return null;
    }

    public static class Login {

        public String username;
        public String password;
    }


    public static Result login() {
        return ok(
                login.render(Form.form(Login.class))
        );
    }
    */

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

