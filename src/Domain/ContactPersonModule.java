package Domain;

public class ContactPersonModule {
    private int speakerID;
    private String name;
    private String email;

    //Constructor
    public ContactPersonModule(int speakerID, String name, String email) {
        this.speakerID = speakerID;
        this.name = name;
        this.email = email;
    }
    
    //Getters and Setters
    public int getSpeakerID() {
        return speakerID;
    }

    public void setSpeakerID(int speakerID) {
        this.speakerID = speakerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
