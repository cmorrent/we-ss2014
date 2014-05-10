package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        if(!userIsAuthenticated()){
            return redirect(routes.UserManagement.getAuthentication());
        }else{
            //TODO: Implement
            return ok(index.render());
        }
        //return ok(authentication.render("Business Informatics Group Quiz - Login"));
    }

    private static Boolean userIsAuthenticated(){
        return false;
    }

}
