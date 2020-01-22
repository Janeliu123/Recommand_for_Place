import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Home")

/*
	Home class uses the printHtml Function of Utilities class and prints the Header,LeftNavigationBar,
	Content,Footer of Game Speed Application.
*/

public class Home extends HttpServlet {
    String err_msg;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
        String TOMCAT_HOME = System.getProperty("catalina.home");
        String separator = System.getProperty("file.separator");
        System.out.println(TOMCAT_HOME+separator+"webapps");
//      try recommend
        Recommend(request,response);
    }
    protected void Recommend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
        HttpSession session=request.getSession();
        HashMap<String,String> prodRecmMap = readOutputFile("output");
        HashMap<String,String> userRecmMap = readOutputFile("user_output");
        Object username = session.getAttribute("username");
        utility.printHtml("Header.html");
        if(username != null){
            MySqlDataStoreUtilities myDB = new MySqlDataStoreUtilities();
            //for user-user similar (use surprise package to find similar user)
            //and recommend the place that this user recommend
            pw.print("<!-- for recommend similar users-->");
            HashMap<String, ArrayList<Review>> hm = MongoDBDataStoreUtilities.selectReview();
            for(String user: userRecmMap.keySet()) {
                if(user.equals(username)){
                    String recommend_users = userRecmMap.get(user);
                    recommend_users=recommend_users.replace("[","");
                    recommend_users=recommend_users.replace("]","");
                    recommend_users=recommend_users.replace("\"", " ");
                    ArrayList<String> userList = new ArrayList<String>(Arrays.asList(recommend_users.split(",")));
                    int i = 0;
                    for(String re: userList){
                        re=re.replace("'","");
                        re=re.replace(" ","");
                        System.out.println(re);
                        System.out.println(hm.containsKey(re));
                        if(hm.containsKey(re)){
                            for(Review r: hm.get(re.toString())){
                                pw.print("<input type='hidden' id='user_"+ i +"' name='user_"+ i +"' value=" + re + ">");
                                pw.print("<input type='hidden' id='similar_"+ i +"' name='similar_"+ i +"' value=" + r.getBusinessId() + ">");
                                i++;
                                if(i>=1) break;
                            }
                        }
                        if(i>=3) break;
                        else continue;
                    }
                }
            }
            HashMap<String, SearchHistory> history = myDB.selectSearchHistory();
            SearchHistory sh = history.get(username);
            if(sh !=null){
                String item = sh.getItem();
                String loc = sh.getLocation();
                pw.print("<input type='hidden' id='his_item' name='his_item' value='"+item+"'>"+
                        "<input type='hidden' id='his_loc' name='his_loc' value='"+loc+"'>");
                //for guess you like (use surprise to predicte)
                pw.print("<!-- for guess you like-->");
                for(String user: prodRecmMap.keySet()) {
                    if(user.equals(username)){
                        String products = prodRecmMap.get(user);
                        products=products.replace("[","");
                        products=products.replace("]","");
                        products=products.replace("\"", " ");
                        ArrayList<String> productsList = new ArrayList<String>(Arrays.asList(products.split(",")));
                        int i = 0;
                        for(String pro: productsList){
                            pw.print("<input type='hidden' id='guess_"+ i +"' name='guess_"+ i +"' value=" + pro + ">");
                            i++;
                        }
                    }
                }
                utility.printHtml("Content.html");
                utility.printHtml("Recommend.html");
                utility.printHtml("Footer.html");
            }else{
                utility.printHtml("Header.html");
                utility.printHtml("Content.html");
                utility.printHtml("Recommend_first.html");
                utility.printHtml("Footer.html");
            }
        }else{
            utility.printHtml("Header.html");
            utility.printHtml("Content.html");
            utility.printHtml("Footer.html");
        }
    }

    public HashMap<String, String> readOutputFile(String filename) {

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        HashMap<String, String> prodRecmMap = new HashMap<String, String>();
        try {
            String TOMCAT_HOME = System.getProperty("catalina.home");
            String separator = System.getProperty("file.separator");
            br = new BufferedReader(new FileReader(new File(TOMCAT_HOME+separator+"webapps"+separator+"Recommender_for_Social_Places"+separator+"data"+separator+filename+".csv")));
//            br = new BufferedReader(new FileReader(new File("/Users/janeliu/Desktop/2019fall/csp584/final/Recommander-for-Social-Places/web/data/"+filename+".csv")));
//            br = new BufferedReader(new FileReader(new File("C:\\Users\\popco\\Recommander-for-Social-Places\\web\\data\\"+filename+".csv")));

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] prod_recm = line.split(cvsSplitBy, 2);
                prodRecmMap.put(prod_recm[0], prod_recm[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prodRecmMap;
    }
}
