import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;

/* 
	Users class contains class variables id,name,password.

	Users class has a constructor with Arguments name, String password.
	  
	Users  class contains getters and setters for id,name,password.

*/

public class SearchHistory implements Serializable{
    private String username;
    private String item;
    private String location;


    public SearchHistory(String username, String item, String location) {
        this.username=username;
        this.item=item;
        this.location=location;

    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}