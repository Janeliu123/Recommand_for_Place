import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/WriteReview")

public class WriteReview extends HttpServlet{
    private String error_msg;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        review(request, response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);

    }


    protected void review(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request, pw);
            if (!utility.isLoggedin()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("login_msg", "Please Login to Write a Review");
                response.sendRedirect("Login");
                return;
            }

            String BusinessId = request.getParameter("BusinessId");
            HttpSession session = request.getSession();
            String userName = session.getAttribute("username").toString();

            utility.printHtml("Header.html");

            pw.print("<section class=\"slider-100 d-flex align-items-center\">\n" +
                    "    <!-- <img src=\"images/slider.jpg\" class=\"img-fluid\" alt=\"#\"> -->\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"col-md-12\">\n" +
                    "            <div class=\"col-md-12\">");
            pw.print("<form name ='WriteReview' action='SubmitReview' method='post'>");

            pw.print("<input id='BusinessId' name='BusinessId' type='hidden' value='" + BusinessId + "'>");
            pw.print("<input id='userName' name='userName' type='hidden' value='" + userName + "'>");
            pw.print("<div class=\"col-md-6\" name=\"username\" style=\"color:violet\">" + userName + "</div>");
            pw.print("<div class=\"col-md-12\">\n" +
                    "                        <table class=\"col-md-6\">\n" +
                    "                            <tr>\n" +
                    "                                <td style=\"color:violet;width:40%\">Rating</td>\n" +
                    "                                <td style=\"width:60%\">\n" +
                    "                                    <select name='reviewrating'>\n" +
                    "                                    <option value='1' selected>1</option>\n" +
                    "                                    <option value='2'>2</option>\n" +
                    "                                    <option value='3'>3</option>\n" +
                    "                                    <option value='4'>4</option>\n" +
                    "                                    <option value='5'>5</option>\n" +
                    "                                </td>\n" +
                    "                            </tr>\n" +
                    "                        </table>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"col-md-12\">\n" +
                    "                        <textarea name=\"reviewtext\" class=\"\" placeholder=\"Write your review here.\" maxlength=\"5000\" style=\"height: 200px;width:600px\"></textarea>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"col-md-12\">\n" +


                    "                        <button type='submit' class=\"btn btn-outline-danger\" name='SubmitReview' value='SubmitReview'>SubmitReview</button>\n" +
                    "                    </div>\n" +
                    "                </form>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</section>\n" +
                    "</body>\n" +
                    "<script typet=\"text/javascript\" src=\"http://libs.baidu.com/jquery/1.9.1/jquery.min.js\"></script>\n" +

                    "</html>");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
