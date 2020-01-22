
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/SubmitReview")

public class SubmitReview extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        storeReview(request, response);
        updateRecommend();
    }
    protected void storeReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request, pw);
            if (!utility.isLoggedin()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("login_msg", "Please login first");
                response.sendRedirect("Login");
                return;
            }

            HttpSession session = request.getSession();
            String userName = session.getAttribute("username").toString();

            String BusinessId = request.getParameter("BusinessId");
            String rating = request.getParameter("reviewrating");
            String reviewtext = request.getParameter("reviewtext");


            String ms=MongoDBDataStoreUtilities.insertReview(BusinessId, userName, Integer.parseInt(rating), reviewtext);
            utility.printHtml("Header.html");

            pw.print("<section class=\"slider-100 d-flex align-items-center\">\n" +
                    "    <!-- <img src=\"images/slider.jpg\" class=\"img-fluid\" alt=\"#\"> -->\n" +
                    "        <h2 class=\"col-md-12\" style=\"color:violet\" font size=\"3\">Thank you for you review</h2>" +
                    "<h2 class=\"col-md-12\" style=\"color:violet\" font size=\"3\">"+ms+"</h2>" +
                    "</section></body></html>\n");


        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    protected void updateRecommend(){
        Process proc;
        try {
            String TOMCAT_HOME = System.getProperty("catalina.home");
            String separator = System.getProperty("file.separator");
            proc = Runtime.getRuntime().exec("jupyter nbconvert --to script --execute --stdout "+TOMCAT_HOME+separator+"webapps"+separator+"Recommender_for_Social_Places"+separator+"data"+separator+"RecommendPlace.ipynb | python");
//            proc = Runtime.getRuntime().exec("jupyter nbconvert --to script --execute --stdout /Users/janeliu/Desktop/2019fall/csp584/final/Recommander-for-Social-Places/web/data/RecommendPlace.ipynb | python");
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
