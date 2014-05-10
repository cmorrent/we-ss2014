package controllers;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by willi on 10.05.14.
 */
public class Secured extends Security.Authenticator {
    @Override
    public String getUsername(Http.Context ctx) {
        return ctx.session().get("user");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return redirect(routes.UserManagement.authenticate());
    }
}
