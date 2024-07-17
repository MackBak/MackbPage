package mackb.nl.mackbpage.business.model;

public class Pilot {
    private String fullName;
    private int customerId;
    private int iRating;

    public Pilot(String fullName, int customerId, int iRating) {
        this.fullName = fullName;
        this.customerId = customerId;
        this.iRating = iRating;
    }

    // Getters and Setters

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getiRating() {
        return iRating;
    }

    public void setiRating(int iRating) {
        this.iRating = iRating;
    }
}
