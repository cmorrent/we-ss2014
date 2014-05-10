package controllers;

import model.Users;
import play.api.i18n.Lang;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.i18n.Messages;
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
        return ok(authentication.render(Form.form(Users.class), ""));
    }


    public static Result getRegistration(){
        return ok(registration.render(Form.form(Users.class), ""));
    }


    @Transactional
    public static Result saveUser(){
        Form<Users> form = Form.form(Users.class).bindFromRequest();

        if(form.hasErrors()){
            return badRequest(registration.render(form, ""));
        }else{
            Users user = form.get();
            if(!user.getPassword().equals(user.getPasswordConfirm())){
                String errorMessage = Messages.get("registration.errormessage.passwordConfirmationInvalid");
                return badRequest(registration.render(form, errorMessage));
            }else if(usernameExists(user.getName())) {
                String errorMessage = Messages.get("registration.errormessage.usernameAllreadyExists");
                return badRequest(registration.render(form, errorMessage));
            }else{
                JPA.em().persist(user);
                return redirect(routes.UserManagement.getAuthentication());
            }
        }
    }

    public static Result authenticate() {
        //Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        return ok("TEST");
    }


    /*s
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

    @Transactional
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

