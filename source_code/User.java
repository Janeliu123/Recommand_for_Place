import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;


@WebServlet("/User")

/* 
	Users class contains class variables id,name,password.

	Users class has a constructor with Arguments name, String password.
	  
	Users  class contains getters and setters for id,name,password.

*/

public class User implements Serializable{
    private int id;
    private String name;
    private String password;
    private String gender;
    private String zipcode;
    private int price;


    public User(String name, String password, String gender, String zipcode) {
        this.name=name;
        this.password=password;
        this.gender=gender;
        this.zipcode=zipcode;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }


}
