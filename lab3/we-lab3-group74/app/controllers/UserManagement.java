package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.authentication;
import views.html.registration;
import static play.data.Form.*;
import play.data.*;

/**
 * Created by willi on 5/7/14.
 */
public class UserManagement extends Controller {

    public static Result getAuthentication(){
        return ok(authentication.render(""));
    }

    public static Result getRegistration(){
        return ok(registration.render(""));
    }

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
}

