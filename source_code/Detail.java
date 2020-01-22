
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Detail")


public class Detail extends HttpServlet {
    private String error_msg;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);

        displayList(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();


        /* From the HttpServletRequest variable name,type,maker and acessories information are obtained.*/

        Utilities utility = new Utilities(request, pw);
//        String category = request.getParameter("category");
    }

    /* displayList Function shows the stores that load from yelp API.*/

    protected void displayList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
        if(!utility.isLoggedin()){
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to search");
            response.sendRedirect("Login");
            return;
        }
        /* From the HttpServletRequest variable name,type,maker and acessories information are obtained.*/
        HttpSession session=request.getSession();
        String userName = session.getAttribute("username").toString();
        String zipcode = session.getAttribute("zipcode").toString();
        String from = request.getParameter("from");
        if(from.equals("google")){
            String latitude = request.getParameter("latitude");
            String longitude = request.getParameter("longitude");
            String bus_name = request.getParameter("term");
            System.out.println(bus_name);
            utility.printHtml("ListHeader.html");
            pw.print("<input id='from' name='from' type='hidden' value='google'>\n"+
                    "<!--    get location from google-->\n" +
                    "<input id='latitude' name='latitude' type='hidden' value='" + latitude + "'></input>\n"+
                    "<input id='longitude' name='longitude' type='hidden' value='" + longitude + "'></input>\n"+
                    "<input id='bus_name' name='bus_name' type='hidden' value='" + bus_name + "'></input>\n");
            utility.printHtml("DetailContent.html");
            utility.printHtml("DetailFooter.html");

        }else{
            String id = request.getParameter("id");
            utility.printHtml("ListHeader.html");
            pw.print("<input id='from' name='from' type='hidden' value='yelp'>\n"+
                    "<!--    get business id-->\n" +
                    "<input id='BusinessId' name='BusinessId' type='hidden' value='" + id + "'></input>\n");
            utility.printHtml("DetailContent.html");
            utility.printHtml("DetailFooter.html");
        }
    }
}


