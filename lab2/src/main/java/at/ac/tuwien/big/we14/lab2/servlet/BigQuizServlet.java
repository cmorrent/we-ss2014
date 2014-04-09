package at.ac.tuwien.big.we14.lab2.servlet;

import at.ac.tuwien.big.we14.lab2.api.domain.DisplayPageEnum;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class BigQuizServlet extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        /*
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>Hello World</h1>");
        out.println("Your sessionid is: " + session.getId());
        out.println("</body>");
        out.println("</html>");
        */
        DisplayPageEnum displayPageEnum = DisplayPageEnum.start;

        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher(getPageName(displayPageEnum));
        dispatcher.forward(request, response);

    }

    private String getPageName(DisplayPageEnum displayPage){
        return  "/" + displayPage.toString() + ".jsp";
    }

}