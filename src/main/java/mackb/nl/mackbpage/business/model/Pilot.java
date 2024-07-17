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

    public int getCustomerId() {
        return customerId;
    }

    public int getiRating() {
        return iRating;
    }

}