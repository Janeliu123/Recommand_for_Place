import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
@WebServlet("/Login")



public class Login extends HttpServlet {
    private String error_msg;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

		/* User Information(username,password,usertype) is obtained from HttpServletRequest,
		Based on the Type of user(customer,retailer,manager) respective hashmap is called and the username and
		password are validated and added to session variable and display Login Function is called */

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HashMap<String, User> hm=new HashMap<String, User>();

        try {
            hm = MySqlDataStoreUtilities.selectUser();
        } catch(Exception e) {


        }
        User user = hm.get(username);
        if(user!=null)
        {
            String user_password = user.getPassword();
            if (password.equals(user_password))
            {
                HttpSession session = request.getSession(true);
                session.setAttribute("username", user.getName());
                session.setAttribute("zipcode", user.getZipcode());
                response.sendRedirect("Home");
            }
        }
        displayLogin(request, response, pw, true);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        displayLogin(request, response, pw, false);
    }

	/*   Username,Password,Usertype information are Obtained from HttpServletRequest variable and validates whether
		 the User Credential Already Exists or else User Details are Added to the Users HashMap */

    /*  displayRegistration function displays the Registration page of New User */

    protected void displayLogin(HttpServletRequest request,
                                       HttpServletResponse response, PrintWriter pw, boolean error)
            throws ServletException, IOException {
        Utilities utility = new Utilities(request, pw);
        String err_msg = "Wrong username or password, Please input again!!!!!!";
        utility.printHtml("background-upper.html");
        pw.print("<div class='slider-title_box'>" +
                "                        <div class='row'>" +
                "                            <div class='col-md-12'>" +
                "                                <div class='slider-content_wrap'>" +
                "                                    <h1>Register to Discover great places</h1>" +
                "                                </div>" +
                "                            </div>");
        pw.print("</div>" +
                "                            <div class='col-md-12'>" +
                "                                <div class='slider-content_wrap'>");
        if (error)
            pw.print("<h4 style='color:violet'>"+err_msg+"</h4>");
        HttpSession session = request.getSession(true);
        if(session.getAttribute("login_msg")!=null){
            pw.print("<h4 style='color:violet'>"+session.getAttribute("login_msg")+"</h4>");
            session.removeAttribute("login_msg");
        }
        utility.printHtml("login-part.html");
        utility.printHtml("background-lower.html");

    }
}
