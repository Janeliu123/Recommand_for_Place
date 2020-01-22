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
@WebServlet("/Registration")



public class Registration extends HttpServlet {
    private String error_msg;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displayRegistration(request, response, pw, false);
    }

	/*   Username,Password,Usertype information are Obtained from HttpServletRequest variable and validates whether
		 the User Credential Already Exists or else User Details are Added to the Users HashMap */


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String gender = request.getParameter("gender");
        String zipcode = request.getParameter("zipcode");

        String[] checkbox = request.getParameterValues("checkbox");
        error_msg="";


        if(utility.isLoggedin()) {
            error_msg = "You have already logged in.";
        }
        //if password and repassword does not match show error message
        else if(!password.equals(repassword))
        {
            error_msg = "Passwords doesn't match!Try again...";
        }
        else if(checkbox.length==0){
            error_msg= "Please choose some preferences to let us know more about you..";
        }
        else
        {
            HashMap<String, User> hm=new HashMap<String, User>();
            //get the user details from file
            try
            {
                hm= MySqlDataStoreUtilities.selectUser();
            }
            catch(Exception e)
            {

            }
            // if the user already exist show error that already exist
            /*create a user object and store details into hashmap store the user hashmap into file  */
            if(hm.containsKey(username))
                error_msg = "Username already exist.Try again...";
            else
            {
				/*create a user object and store details into hashmap
				store the user hashmap into file  */
                User user = new User(username,password,gender,zipcode);
                MySqlDataStoreUtilities.insertUser(username,password,repassword,gender,zipcode);
                MySqlDataStoreUtilities.insertPreference(checkbox,username);
                HttpSession session = request.getSession(true);
                updateUserSimilar();
                session.setAttribute("login_msg", "Your account has been created. Please login");
                if(!utility.isLoggedin()){
                    session.setAttribute("login_msg", "Your account has been created. Please login");
                    response.sendRedirect("Login"); return;
                } else {
                    //response.sendRedirect("CustomerCreated"); return;
                }
            }
        }
        displayRegistration(request, response, pw, true);
    }

    /*  displayRegistration function displays the Registration page of New User */

    protected void displayRegistration(HttpServletRequest request,
                                       HttpServletResponse response, PrintWriter pw, boolean error)
            throws ServletException, IOException {
        Utilities utility = new Utilities(request, pw);
        utility.printHtml("background-upper.html");
        pw.print("<div class='slider-title_box'>" +
                "                        <div class='row'>" +
                "                            <div class='col-md-12'>" +
                "                                <div class='slider-content_wrap'>" +
                "                                </div>" +
                "                            </div>");
        pw.print("</div>" +
                "                            <div class='col-md-12'>" +
                "                                <div class='slider-content_wrap'>");
        if (error)
            pw.print("<h4 style='color:violet'>"+error_msg+"</h4>");
        utility.printHtml("register-part.html");
        utility.printHtml("background-lower.html");
    }

    protected void updateUserSimilar(){
        Process proc;
        try {
//            String TOMCAT_HOME = System.getProperty("catalina.home");
//            String separator = System.getProperty("file.separator");
//            proc = Runtime.getRuntime().exec("jupyter nbconvert --to script --execute --stdout "+TOMCAT_HOME+separator+"webapps"+separator+"Recommander_for_Social_Places"+separator+"data"+separator+"UserSimilar.ipynb | python");
            proc = Runtime.getRuntime().exec("jupyter nbconvert --to script --execute --stdout /Users/janeliu/Desktop/2019fall/csp584/final/Recommander-for-Social-Places/web/data/UserSimilar.ipynb | python");

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
