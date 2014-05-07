package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
        //return ok(authentication.render("Business Informatics Group Quiz - Login"));
    }

}
