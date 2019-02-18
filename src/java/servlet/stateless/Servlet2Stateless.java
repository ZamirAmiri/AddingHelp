/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.stateless;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.HTMLFilter;

/**
 *
 * @author zamir
 */
@WebServlet(name = "Servlet", urlPatterns = {"/servlet"})
public class Servlet2Stateless extends HttpServlet {
    
    // Using injection for Stateless session bean is still thread-safe since
    // the ejb container will route every request to different
    // bean instances. However, for Stateful session beans the
    // dependency on the bean must be declared at the type level

    @EJB
    private StatelessSessionBean sless;

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {

            out.println("<HTML> <HEAD> <TITLE> Servlet Output </TITLE> </HEAD> <BODY BGCOLOR=white>");
            out.println("<CENTER> <FONT size=+1> Servlet2Stateless:: Please enter your name </FONT> </CENTER> <p> ");
            out.println("<form method=\"POST\">");
            out.println("<TABLE>");
            out.println("<tr><td>Name: </td>");
            out.println("<td><input type=\"text\" name=\"name\"> </td>");
            out.println("</tr><tr><td></td>");
            out.println("<td><input type=\"submit\" name=\"sub\"> </td>");
            out.println("</tr>");
            out.println("</TABLE>");
            out.println("</form>");
            String val = req.getParameter("name");
            
            if ((val != null) && (val.trim().length() > 0)) {
                out.println("<FONT size=+1 color=red> Greeting from StatelessSessionBean: </FONT>"
                            + HTMLFilter.filter(sless.sayHello(val)) + "<br>");
            }
            out.println("</BODY> </HTML> ");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("webclient servlet test failed");
            throw new ServletException(ex);
        }
    }
}
