
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/Search")


public class Search extends HttpServlet {
    private String error_msg;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
        if(!utility.isLoggedin())
        {
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to add items to cart");
            response.sendRedirect("Login");
            return;
        }
        addSearchHistory(request, response);
        displayList(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();


        /* From the HttpServletRequest variable name,type,maker and acessories information are obtained.*/

        Utilities utility = new Utilities(request, pw);
    }
    protected void addSearchHistory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
        HttpSession session=request.getSession();
        String username = session.getAttribute("username").toString();
        String category = request.getParameter("category");
        String location = request.getParameter("location");
        HashMap<String, SearchHistory> hm=new HashMap();
        //get the user details from file
        try
        {
            hm= MySqlDataStoreUtilities.selectSearchHistory();
        }
        catch(Exception e)
        {
        }
        // if the user already exist show error that already exist
        /*create a user object and store details into hashmap store the user hashmap into file  */
        if(hm.containsKey(username)){
            MySqlDataStoreUtilities.updateSearchHistory(username,category,location);
        } else{
            MySqlDataStoreUtilities.insertSearchHistory(username,category,location);
        }
    }

    /* displayList Function shows the stores that load from yelp API.*/

    protected void displayList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
        if(!utility.isLoggedin()){
            HttpSession session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to add items to cart");
            response.sendRedirect("Login");
            return;
        }
        /* From the HttpServletRequest variable name,type,maker and acessories information are obtained.*/
        String category = request.getParameter("category");
        String location = request.getParameter("location");
        System.out.println(category+location);
        utility.printHtml("ListHeader.html");
        pw.print("<input type='hidden' value='"+ category +"' id='category' name='category'>" +
                "<input type='hidden' value='"+ location +"' id='location' name='location'>");
        utility.printHtml("List.html");
        utility.printHtml("ListFooter.html");
    }
}
