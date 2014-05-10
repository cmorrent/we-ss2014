package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;
import play.mvc.Http.*;

/**
 * Created by willi on 5/7/14.
 */
public class Game  extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result getIndex(){
        return ok(index.render());
    }



}
