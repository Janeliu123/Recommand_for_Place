import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@WebServlet("/MySqlDataStoreUtilities")

public class MySqlDataStoreUtilities {

    static Connection conn = null;

    public static void getConnection() throws IOException {
        String mysql_user = "";
        String mysql_password = "";
        String mysql_db_name = "";

        String TOMCAT_HOME = System.getProperty("catalina.home");
        String separator = System.getProperty("file.separator");
        FileInputStream inputStream = new FileInputStream(TOMCAT_HOME+separator+"webapps"+separator+"Recommender_for_Social_Places"+separator+"config.txt");
//        FileInputStream inputStream = new FileInputStream("/Users/janeliu/Desktop/2019fall/csp584/final/Recommander-for-Social-Places/web/config.txt");
//        FileInputStream inputStream = new FileInputStream("C:\\Users\\popco\\Recommander-for-Social-Places\\web\\config.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String str = null;
        while((str = bufferedReader.readLine()) != null)
        {
            String[] config = str.split("=");
            if(config[0].equals("MYSQL_DB_NAME"))
                mysql_db_name = config[1];
            else if(config[0].equals("MYSQL_PASSWORD"))
                mysql_password = config[1];
            else if(config[0].equals("MYSQL_USER"))
                mysql_user = config[1];
        }
        //close
        inputStream.close();
        bufferedReader.close();

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+mysql_db_name+"?useUnicode=true&characterEncoding=utf8", mysql_user, mysql_password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static boolean insertUser(String username, String password, String repassword,String gender, String zipcode) {
        try {

            getConnection();
            String insertIntoCustomerRegisterQuery = "INSERT INTO user(username,password,repassword,gender,zipcode) "
                            + "VALUES (?,?,?,?,?);";

            PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, repassword);
            pst.setString(4, gender);
            pst.setString(5, zipcode);
            //pst.setInt(6, price);
            pst.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }
    public static boolean insertPreference(String[] checkbox, String username){
        try{
            getConnection();
            String insertIntoPreferenceQuery = "INSERT INTO preference(American,Indian,Asian,Halal,Mexican,bars,museum,park,shopping,coffee,username)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement pst = conn.prepareStatement(insertIntoPreferenceQuery);
            String[] option = {"American","Indian","Asian","Halal","Mexican","bars","museum","park","shopping","coffee"};

            for(int i =0;i<option.length;i++) {
                pst.setString(i + 1, "0");
                for (int j = 0; j < checkbox.length; j++) {
                    if (option[i].equals(checkbox[j])) {
                        pst.setString(i + 1, "1");
                        break;
                    }
                }

            }
            pst.setString(11, username);
            pst.execute();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }


    public static HashMap<String, User> selectUser() {
        HashMap<String, User> hm = new HashMap<String, User>();
        try {
            getConnection();
            Statement stmt = conn.createStatement();
            String selectCustomerQuery = "select * from user";
            ResultSet rs = stmt.executeQuery(selectCustomerQuery);
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("gender"), rs.getString("zipcode"));
                hm.put(rs.getString("username"), user);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return hm;
    }

    public static boolean insertSearchHistory(String username, String item, String location) {
        try {
            getConnection();
            String insertIntoSearchHIstoryQuery = "INSERT INTO SearchHistory(username,item,location) "
                    + "VALUES (?,?,?);";

            PreparedStatement pst = conn.prepareStatement(insertIntoSearchHIstoryQuery);
            pst.setString(1, username);
            pst.setString(2, item);
            pst.setString(3, location);
            pst.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    public static HashMap<String, SearchHistory> selectSearchHistory() {
        HashMap<String, SearchHistory> hm = new HashMap<>();
        try {
            getConnection();
            Statement stmt = conn.createStatement();
            String selectHistoryQuery = "select * from SearchHistory";
            ResultSet rs = stmt.executeQuery(selectHistoryQuery);
            while (rs.next()) {
                SearchHistory S_H = new SearchHistory(rs.getString("username"), rs.getString("item"), rs.getString("location"));
                hm.put(rs.getString("username"), S_H);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return hm;
    }
    public static boolean updateSearchHistory(String username, String item, String location) {
        try {
            getConnection();
            String updateHistoreQurey = "UPDATE SearchHistory SET item=?,location=? where username =?;";

            PreparedStatement pst = conn.prepareStatement(updateHistoreQurey);

            pst.setString(1, item);
            pst.setString(2, location);
            pst.setString(3, username);
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
