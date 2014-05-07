package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.authentication;

/**
 * Created by willi on 5/7/14.
 */
public class UserManagement extends Controller {

    public static Result getAuthentication(){
        return ok(authentication.render(""));
    }
}
