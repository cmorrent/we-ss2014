package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        /*
        if(!userIsAuthenticated()){
            return redirect(routes.UserManagement.getAuthentication());
        }else{
            return redirect(routes.Game.getIndex());
        }
        */
        //return ok(authentication.render("Business Informatics Group Quiz - Login"));
        return redirect(routes.Game.getIndex());
    }


    private static Boolean userIsAuthenticated(){
        return false;
    }

}
