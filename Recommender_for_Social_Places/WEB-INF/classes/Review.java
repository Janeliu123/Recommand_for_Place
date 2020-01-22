import java.io.Serializable;

public class Review implements Serializable {
    private String userName;
    private String BusinessId;
    private int reviewrating;
    private String reviewtext;

    public Review(String userName, String BusinessId, int reviewrating, String reviewtext) {
        this.userName = userName;
        this.BusinessId = BusinessId;
        this.reviewrating = reviewrating;
        this.reviewtext = reviewtext;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String BusinessId) {
        this.BusinessId = BusinessId;
    }

    public int getreviewrating() {
        return reviewrating;
    }

    public void setreviewrating(int reviewrating) {
        this.reviewrating = reviewrating;
    }

    public String getreviewtext() {
        return reviewtext;
    }

    public void setreviewtext(String reviewtext) {
        this.reviewtext = reviewtext;
    }

}