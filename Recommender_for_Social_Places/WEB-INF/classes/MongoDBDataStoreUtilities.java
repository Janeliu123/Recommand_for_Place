
import com.mongodb.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MongoDBDataStoreUtilities
{
    static DBCollection myReviews;
    public static DBCollection getConnection() throws IOException {
        String mongodb_name = "";
        String mongodb_collection = "";
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
            if(config[0].equals("MONGODB_NAME"))
                mongodb_name = config[1];
            else if(config[0].equals("MONGODB_COLLECTION"))
                mongodb_collection = config[1];
        }
        //close
        inputStream.close();
        bufferedReader.close();
        MongoClient mongo;
        mongo = new MongoClient("localhost", 27017);

        DB db = mongo.getDB(mongodb_name);
        myReviews= db.getCollection(mongodb_collection);
        return myReviews;
    }


    public static String insertReview(String businessId,String username,int rating,String reviewtext)
    {
        try
        {
            getConnection();
            BasicDBObject doc = new BasicDBObject().
                    append("userName", username).
                    append("businessId", businessId).
                    append("reviewrating", rating).
                    append("reviewtext", reviewtext);
            myReviews.insert(doc);
            return "Successfull";
        }
        catch(Exception e)
        {
            return "UnSuccessfull";
        }

    }

    public static HashMap<String, ArrayList<Review>> selectReview() {
        HashMap<String, ArrayList<Review>> reviews = null;
        try {
            getConnection();
            DBCursor cursor = myReviews.find();
            reviews = new HashMap<String, ArrayList<Review>>();
            while (cursor.hasNext()) {
                BasicDBObject obj = (BasicDBObject) cursor.next();

                if (!reviews.containsKey(obj.getString("userName"))) {
                    ArrayList<Review> arr = new ArrayList<Review>();
                    reviews.put(obj.getString("userName"), arr);
                }
                ArrayList<Review> listReview = reviews.get(obj.getString("userName"));
                Review review = new Review(obj.getString("userName"), obj.getString("businessId"),
                                             obj.getInt("reviewrating"), obj.getString("reviewtext"));
                //add to review hashmap
                listReview.add(review);
            }
            return reviews;
        } catch (Exception e) {
            reviews = null;
            return reviews;
        }
    }
}

