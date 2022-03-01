package Domain;

public class SpeakerWebcast {
    private int speakerID;
    private String email;
    private String name;

    //Constructor
    public SpeakerWebcast(int speakerID, String email, String name) {
        this.speakerID = speakerID;
        this.email = email;
        this.name = name;
    }
    
    //Getters and Setters
    public int getSpeakerID() {
        return speakerID;
    }

    public void setSpeakerID(int speakerID) {
        this.speakerID = speakerID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
