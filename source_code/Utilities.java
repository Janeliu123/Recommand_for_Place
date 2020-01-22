

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebServlet("/Utilities")

/*
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.
	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.
*/

public class Utilities extends HttpServlet{
    HttpServletRequest req;
    PrintWriter pw;
    String url;
    HttpSession session;

    public Utilities(HttpServletRequest req, PrintWriter pw){
        this.req = req;
        this.pw = pw;
        this.url = this.getFullURL();
        this.session = req.getSession(true);
    }

	/*  Printhtml Function gets the html file name as function Argument,
		If the html file name is Header.html then It gets Username from session variables.
		Account ,Cart Information ang Logout Options are Displayed*/

    public void printHtml(String file) {
        String result = HtmlToString(file);
        if (file == "Header.html") {
            if (session.getAttribute("username") != null) {
                String username = session.getAttribute("username").toString();
                result = result + " <a class='nav-link' href='Logout'>Logout</a></li></ul></div></nav></div></div></div></div></div>";
                pw.print(result);
            } else {
                result = result +" <a class='nav-link' href='Login'>Login</a></li></ul></div></nav></div></div></div></div></div>";
                pw.print(result);
            }
        }else{
            pw.print(result);
        }
    }
    /*  getFullURL Function - Reconstructs the URL user request  */

    public String getFullURL() {
        String scheme = req.getScheme();
        String serverName = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getContextPath();
        StringBuffer url = new StringBuffer();
        url.append(scheme).append("://").append(serverName);

        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        url.append("/");
        return url.toString();
    }

    /*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
    public String HtmlToString(String file) {
        String result = null;
        try {
            String webPage = url + file;
            URL url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            result = sb.toString();
        }
        catch (Exception e) {
        }
        return result;
    }

    /*  logout Function removes the username , usertype attributes from the session variable*/

    public void logout(){
        session.removeAttribute("username");
        session.removeAttribute("zipcode");
    }

    /*  logout Function checks whether the user is loggedIn or Not*/

    public boolean isLoggedin(){
       return session.getAttribute("username") != null;
    }
}
